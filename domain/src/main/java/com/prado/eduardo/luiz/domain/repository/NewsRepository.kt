package com.prado.eduardo.luiz.domain.repository

import com.prado.eduardo.luiz.domain.model.ArticleModel

interface NewsRepository {
    suspend fun getArticle(): Result<List<ArticleModel>>
}
