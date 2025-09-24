package com.prado.eduardo.luiz.domain.usecase

import com.prado.eduardo.luiz.domain.model.ArticleModel
import com.prado.eduardo.luiz.domain.repository.NewsRepository

class GetArticlesUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(): Result<List<ArticleModel>> {
        return repository.getArticle()
    }
}
