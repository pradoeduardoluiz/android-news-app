package com.prado.eduardo.luiz.domain.di

import com.prado.eduardo.luiz.domain.usecase.GetArticlesUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetArticlesUseCase(repository = get()) }
}
