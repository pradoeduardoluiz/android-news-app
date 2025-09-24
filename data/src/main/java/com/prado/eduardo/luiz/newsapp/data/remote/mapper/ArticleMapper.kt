package com.prado.eduardo.luiz.newsapp.data.remote.mapper

import com.prado.eduardo.luiz.domain.model.ArticleModel
import com.prado.eduardo.luiz.newsapp.data.remote.dto.ArticleDTO

fun ArticleDTO.toModel() = ArticleModel(
    title = this.title.orEmpty(),
    author = this.author.orEmpty(),
    description = this.description.orEmpty(),
    url = this.url.orEmpty(),
    urlToImage = this.urlToImage.orEmpty(),
    publishedAt = this.publishedAt.orEmpty(),
    content = this.content.orEmpty()
)
