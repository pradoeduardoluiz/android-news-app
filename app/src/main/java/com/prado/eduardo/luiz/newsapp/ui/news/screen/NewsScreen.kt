package com.prado.eduardo.luiz.newsapp.ui.news.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prado.eduardo.luiz.newsapp.ui.component.CardLarge
import com.prado.eduardo.luiz.newsapp.ui.component.CardSmall
import com.prado.eduardo.luiz.newsapp.ui.news.NewsIntent
import com.prado.eduardo.luiz.newsapp.ui.news.NewsUIState
import com.prado.eduardo.luiz.newsapp.ui.news.NewsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen(modifier: Modifier) {
    val viewModel: NewsViewModel = koinViewModel()
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.publish(NewsIntent.GetNews)
    }

    Column(modifier = modifier) {
        NewsScreenContent(
            state = state.value,
            onPublish = { viewModel::publish }
        )
    }
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
            itemsIndexed(state.articles) { index, article ->
                // each 3 card I would like to show the large card
                if (index % 3 == 0) {
                    // Large Card
                    CardLarge(
                        author = article.author,
                        title = article.title,
                        description = article.description,
                        imageUrl = article.urlToImage,
                        isCaptionVisible = true,
                        onClick = { /* TODO */ }
                    )
                } else {
                    // Small Card
                    CardSmall(
                        author = article.author,
                        title = article.title,
                        description = article.description,
                        imageUrl = article.urlToImage,
                        onClick = { /* TODO */ }
                    )
                }
            }
        }
    }
}

@Composable
fun NewsLoading() {
    CircularProgressIndicator()
}
