package com.prado.eduardo.luiz.newsapp.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prado.eduardo.luiz.domain.usecase.GetArticlesUseCase
import com.prado.eduardo.luiz.newsapp.common.dispatcher.DispatchersProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val dispatcher: DispatchersProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUIState())
    val uiState = _uiState.asStateFlow()

    fun publish(intention: NewsIntent) {
        viewModelScope.launch(dispatcher.io) {
            when (intention) {
                is NewsIntent.GetNews -> getNews()
            }
        }
    }

    private suspend fun getNews() {
        _uiState.update { it.copy(isLoading = true) }
        getArticlesUseCase()
            .onSuccess { result ->
                _uiState.value = NewsUIState(articles = result, isLoading = false)
            }.onFailure { e ->
                _uiState.value = NewsUIState(error = e.message, isLoading = false)
                e.printStackTrace()
            }
    }
}
