package com.prado.eduardo.luiz.newsapp.data.repository

import com.prado.eduardo.luiz.domain.model.ArticleModel
import com.prado.eduardo.luiz.domain.repository.NewsRepository
import com.prado.eduardo.luiz.newsapp.data.remote.mapper.toModel
import com.prado.eduardo.luiz.newsapp.data.remote.service.NewsService
import retrofit2.HttpException

class NewsRepositoryImpl(
    private val newsService: NewsService,
    private val sources: String,
    private val apiKey: String
) : NewsRepository {

    override suspend fun getArticle(): Result<List<ArticleModel>> {
        return try {
            val response = newsService.getArticles(sources = sources, apiKey = apiKey)
            if (response.isSuccessful) {
                val articles = response.body()?.articles?.map { it.toModel() }.orEmpty()
                Result.success(articles)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
