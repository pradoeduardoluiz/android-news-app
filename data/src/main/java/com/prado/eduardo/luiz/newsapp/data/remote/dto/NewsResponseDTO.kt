package com.prado.eduardo.luiz.newsapp.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponseDTO(
    @Json(name = "status")
    val status: String?,
    @Json(name = "totalResults")
    val totalResults: Int?,
    @Json(name = "articles")
    val articles: List<ArticleDTO>?
)
