package com.prado.eduardo.luiz.newsapp.ui.article

data class ArticleUIState(
    val isLoading: Boolean = true,
    val title: String = "",
    val description: String = "",
    val author: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: String = "",
    val content: String = "",
)
