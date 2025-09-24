package com.prado.eduardo.luiz.newsapp.di

import com.prado.eduardo.luiz.domain.di.domainModule
import com.prado.eduardo.luiz.newsapp.common.dispatcher.AppDispatchersProvider
import com.prado.eduardo.luiz.newsapp.common.dispatcher.DispatchersProvider
import com.prado.eduardo.luiz.newsapp.data.di.dataModule
import com.prado.eduardo.luiz.newsapp.data.di.networkModule
import com.prado.eduardo.luiz.newsapp.ui.news.NewsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single<DispatchersProvider> { AppDispatchersProvider() }
    viewModel { NewsViewModel(getArticlesUseCase = get(), dispatcher = get()) }
}

val appModule = listOf(networkModule, dataModule, domainModule, presentationModule)
