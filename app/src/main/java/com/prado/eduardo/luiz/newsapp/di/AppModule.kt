package com.prado.eduardo.luiz.newsapp.di

import com.prado.eduardo.luiz.newsapp.data.di.dataModule
import com.prado.eduardo.luiz.newsapp.data.di.networkModule
import org.koin.dsl.module

val appModule = module {

} + networkModule + dataModule
