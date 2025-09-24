package com.prado.eduardo.luiz.newsapp.data.di

import com.prado.eduardo.luiz.domain.repository.NewsRepository
import com.prado.eduardo.luiz.newsapp.data.remote.service.NewsService
import com.prado.eduardo.luiz.newsapp.data.repository.NewsRepositoryImpl
import org.koin.dsl.module

private const val API_KEY = "38ea4eb3ca424da08552d9f13ae8d12f"

val dataModule = module {
    single { provideNewsRepository(service = get(), source = get()) }
}

private fun provideNewsRepository(
    service: NewsService,
    source: String,
): NewsRepository {
    return NewsRepositoryImpl(
        newsService = service,
        source = source,
        apiKey = API_KEY
    )
}
