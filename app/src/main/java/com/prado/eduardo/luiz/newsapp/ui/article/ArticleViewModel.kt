package com.prado.eduardo.luiz.newsapp.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prado.eduardo.luiz.newsapp.common.dispatcher.DispatchersProvider
import com.prado.eduardo.luiz.newsapp.navigation.NavigatorRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val dispatcher: DispatchersProvider,
    private val navigator: NavigatorRoute
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArticleUIState())
    val uiState = _uiState.asStateFlow()

    fun publish(intention: ArticleIntent) {
        viewModelScope.launch(dispatcher.io) {
            when (intention) {
                is ArticleIntent.LoadArticle -> loadArticle(intention)
            }
        }
    }

    private fun loadArticle(intention: ArticleIntent.LoadArticle) {
        _uiState.update {
            it.copy(
                isLoading = false,
                author = intention.author,
                title = intention.title,
                description = intention.description,
                url = intention.url,
                urlToImage = intention.urlToImage,
                publishedAt = intention.publishedAt,
                content = intention.content
            )
        }
    }
}
