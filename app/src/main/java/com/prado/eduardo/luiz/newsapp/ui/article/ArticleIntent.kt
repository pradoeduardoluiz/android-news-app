package com.prado.eduardo.luiz.newsapp.ui.article

sealed interface ArticleIntent {
    data class LoadArticle(
        val author: String,
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val publishedAt: String,
        val content: String
    ) : ArticleIntent
}
