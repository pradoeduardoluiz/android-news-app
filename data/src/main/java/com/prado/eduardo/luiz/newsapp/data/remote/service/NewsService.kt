package com.prado.eduardo.luiz.newsapp.data.remote.service

import com.prado.eduardo.luiz.newsapp.data.remote.dto.NewsResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/top-headlines")
    suspend fun getArticles(
        @Query("source") source: String,
        @Query("apiKey") apiKey: String,
    ): Response<NewsResponseDTO>
}
