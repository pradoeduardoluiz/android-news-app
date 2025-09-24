package com.prado.eduardo.luiz.newsapp.ui.news

sealed interface NewsIntent {
    data object GetNews : NewsIntent
}
