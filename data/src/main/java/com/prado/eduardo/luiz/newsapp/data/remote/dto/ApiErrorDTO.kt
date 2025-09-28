package com.prado.eduardo.luiz.newsapp.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiErrorDTO(
    @Json(name = "status")
    val status: String?,
    @Json(name = "code")
    val code: String?,
    @Json(name = "message")
    val message: String?
)
