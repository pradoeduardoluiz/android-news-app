package com.prado.eduardo.luiz.newsapp.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.prado.eduardo.luiz.newsapp.data.BuildConfig
import com.prado.eduardo.luiz.newsapp.data.remote.service.NewsService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://newsapi.org/"

val networkModule = module {
    single { provideHttpClient(androidContext()) }
    single { provideConverterFactory() }
    single { provideRetrofit(get(), get()) }
    single { provideNewsService(get()) }
}

private fun provideHttpClient(context: Context): OkHttpClient {
    val builder = OkHttpClient.Builder()
    builder.readTimeout(60, TimeUnit.SECONDS)
    builder.connectTimeout(60, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) {
        builder.addInterceptor(
            ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )
    }
    return builder.build()
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
