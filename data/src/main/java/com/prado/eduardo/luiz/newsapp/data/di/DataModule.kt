package com.prado.eduardo.luiz.newsapp.data.di

import com.prado.eduardo.luiz.newsapp.data.remote.service.NewsService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://newsapi.org/"

val networkModule = module {
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(get(), get()) }
    single { provideNewsService(get()) }
}

private fun provideHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
}

private fun provideConverterFactory(): Converter.Factory = MoshiConverterFactory.create()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: Converter.Factory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

private fun provideNewsService(retrofit: Retrofit): NewsService =
    retrofit.create(NewsService::class.java)
