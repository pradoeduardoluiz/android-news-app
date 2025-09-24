package com.prado.eduardo.luiz.newsapp.di

import com.prado.eduardo.luiz.domain.di.domainModule
import com.prado.eduardo.luiz.newsapp.data.di.dataModule
import com.prado.eduardo.luiz.newsapp.data.di.networkModule
import org.koin.dsl.module

val presentationModule = module {
    // Presentation layer dependencies can be added here
}

val appModule = listOf(networkModule, dataModule, domainModule, presentationModule)
