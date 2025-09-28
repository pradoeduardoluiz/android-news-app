package com.prado.eduardo.luiz.newsapp.data.di

import com.prado.eduardo.luiz.domain.repository.NewsRepository
import com.prado.eduardo.luiz.newsapp.data.repository.NewsRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single<NewsRepository> {
        NewsRepositoryImpl(
            newsService = get(),
            sources = "bbc-news",
            apiKey = API_KEY
        )
    }
}
