package com.prado.eduardo.luiz.newsapp.ui.news

import com.prado.eduardo.luiz.domain.model.ArticleModel

data class NewsUIState(
    val isLoading: Boolean = true,
    val articles: List<ArticleModel> = emptyList(),
    val error: String? = null
)
