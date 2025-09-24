package com.prado.eduardo.luiz.newsapp.data.remote.service

import com.prado.eduardo.luiz.newsapp.data.remote.dto.NewsResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface NewsService {
    @GET("v2/top-headlines")
    suspend fun getArticles(): Response<NewsResponseDTO>
}
