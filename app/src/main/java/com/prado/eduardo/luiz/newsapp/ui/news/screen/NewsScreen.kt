package com.prado.eduardo.luiz.newsapp.ui.news.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prado.eduardo.luiz.newsapp.ui.news.NewsIntent
import com.prado.eduardo.luiz.newsapp.ui.news.NewsUIState
import com.prado.eduardo.luiz.newsapp.ui.news.NewsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen() {
    val viewModel: NewsViewModel = koinViewModel()
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.publish(NewsIntent.GetNews)
    }

    NewsScreenContent(
        state = state.value,
        onPublish = { viewModel::publish }
    )
}

@Composable
fun NewsScreenContent(
    state: NewsUIState,
    onPublish: (NewsIntent) -> Unit
) {
    if (state.isLoading) {
        NewsLoading()
    } else {
        LazyColumn {
            items(state.articles, key = { it.title }) { article ->

            }
        }
    }
}

@Composable
fun NewsLoading() {
    TODO("Not yet implemented")
}
