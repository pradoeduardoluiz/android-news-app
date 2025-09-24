package com.prado.eduardo.luiz.newsapp.data.di

import com.prado.eduardo.luiz.domain.repository.NewsRepository
import com.prado.eduardo.luiz.newsapp.data.repository.NewsRepositoryImpl
import org.koin.dsl.module

private const val API_KEY = "38ea4eb3ca424da08552d9f13ae8d12f"

val dataModule = module {
    single<NewsRepository> {
        NewsRepositoryImpl(
            newsService = get(),
            source = "bbc-news",
            apiKey = API_KEY
        )
    }
}
