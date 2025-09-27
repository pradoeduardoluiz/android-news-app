package com.prado.eduardo.luiz.newsapp.ui.news

import com.prado.eduardo.luiz.domain.model.ArticleModel

sealed interface NewsIntent {
    data object GetNews : NewsIntent
    data class OpenArticle(val article: ArticleModel) : NewsIntent
}
