package com.prado.eduardo.luiz.newsapp.data.di

import com.prado.eduardo.luiz.domain.repository.NewsRepository
import com.prado.eduardo.luiz.domain.util.ErrorMapper
import com.prado.eduardo.luiz.newsapp.data.BuildConfig
import com.prado.eduardo.luiz.newsapp.data.repository.NewsRepositoryImpl
import com.prado.eduardo.luiz.newsapp.data.util.ErrorMapperImpl
import org.koin.dsl.module

val dataModule = module {
    single<NewsRepository> {
        NewsRepositoryImpl(
            newsService = get(),
            sources = BuildConfig.SOURCES,
            apiKey = BuildConfig.NEWS_API_KEY
        )
    }
    single<ErrorMapper> { ErrorMapperImpl() }
}
