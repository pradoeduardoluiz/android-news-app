package com.prado.eduardo.luiz.newsapp.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prado.eduardo.luiz.domain.model.ArticleModel
import com.prado.eduardo.luiz.domain.usecase.GetArticlesUseCase
import com.prado.eduardo.luiz.domain.util.ErrorMapper
import com.prado.eduardo.luiz.newsapp.common.dispatcher.DispatchersProvider
import com.prado.eduardo.luiz.newsapp.navigation.AppRoute
import com.prado.eduardo.luiz.newsapp.navigation.NavigatorRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val dispatcher: DispatchersProvider,
    private val navigator: NavigatorRoute,
    private val errorMapper: ErrorMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUIState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<NewsUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun publish(intention: NewsIntent) {
        viewModelScope.launch(dispatcher.io) {
            when (intention) {
                is NewsIntent.GetNews -> getNews()
                is NewsIntent.OpenArticle -> openArticle(intention.article)
            }
        }
    }

    private suspend fun getNews() {
        _uiState.update { it.copy(isLoading = true) }
        getArticlesUseCase()
            .onSuccess { result ->
                _uiState.value = NewsUIState(articles = result, isLoading = false)
            }.onFailure { e ->
                _uiState.value = NewsUIState(isLoading = false)
                _uiEvent.emit(NewsUIEvent.ShowError(errorMapper.map(e)))
            }
    }

    private fun openArticle(article: ArticleModel) {
        val route = AppRoute.Article(
            author = article.author,
            title = article.title,
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            content = article.content,
            description = article.description
        )
        navigator.navigate(route)
    }
}
