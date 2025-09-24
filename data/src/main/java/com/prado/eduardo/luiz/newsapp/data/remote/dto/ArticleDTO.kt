package com.prado.eduardo.luiz.newsapp.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleDTO(
    @Json(name = "source")
    val source: SourceDTO?,
    @Json(name = "author")
    val author: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "urlToImage")
    val urlToImage: String?,
    @Json(name = "publishedAt")
    val publishedAt: String?,
    @Json(name = "content")
    val content: String?
)
