package com.prado.eduardo.luiz.newsapp.navigation

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
sealed interface AppRoute {
    @Serializable
    @Keep
    data object Home : AppRoute

    @Serializable
    @Keep
    data class Article(
        val author: String,
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val publishedAt: String,
        val content: String
    ) : AppRoute
}
