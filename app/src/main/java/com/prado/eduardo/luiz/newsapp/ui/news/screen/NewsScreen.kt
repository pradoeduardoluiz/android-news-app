package com.prado.eduardo.luiz.newsapp.ui.news.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prado.eduardo.luiz.newsapp.ui.component.CardLarge
import com.prado.eduardo.luiz.newsapp.ui.component.CardSmall
import com.prado.eduardo.luiz.newsapp.ui.news.NewsIntent
import com.prado.eduardo.luiz.newsapp.ui.news.NewsUIState
import com.prado.eduardo.luiz.newsapp.ui.news.NewsViewModel
import com.prado.eduardo.luiz.newsapp.ui.theme.NewsAppTheme
import com.prado.eduardo.luiz.newsapp.ui.theme.SpacingMiddle
import com.prado.eduardo.luiz.newsapp.ui.theme.SpacingNormal
import com.prado.eduardo.luiz.newsapp.ui.theme.SpacingSmall
import com.prado.eduardo.luiz.newsapp.ui.theme.SpacingVeryLarge
import com.valentinilk.shimmer.shimmer
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen(modifier: Modifier = Modifier) {
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
private fun NewsScreenContent(
    state: NewsUIState,
    onPublish: (NewsIntent) -> Unit
) {
    if (state.isLoading) {
        LoadingScreen()
    } else {
        LazyColumn {
            itemsIndexed(state.articles) { index, article ->
                if (index % 3 == 0) {
                    CardLarge(
                        author = article.author,
                        title = article.title,
                        description = article.description,
                        imageUrl = article.urlToImage,
                        isCaptionVisible = true,
                        onClick = { /* TODO */ }
                    )
                } else {
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
private fun LoadingScreen() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .shimmer(),
        ) {
            for (i in 1..10) {
                Column(
                    modifier = Modifier
                        .padding(
                            start = SpacingMiddle,
                            top = SpacingMiddle,
                            end = SpacingMiddle
                        )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .background(MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                        Box(
                            modifier = Modifier
                                .padding(start = SpacingNormal)
                                .weight(1f)
                        )
                    }
                    Column {
                        Row(
                            modifier = Modifier.padding(top = SpacingNormal)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(76.dp)
                                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = SpacingNormal)
                                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
                                        .height(10.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = SpacingNormal,
                                            end = SpacingNormal,
                                            top = SpacingSmall
                                        )
                                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
                                        .height(10.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .width(150.dp)
                                        .padding(
                                            start = SpacingNormal,
                                            end = SpacingNormal,
                                            top = SpacingSmall
                                        )
                                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
                                        .height(10.dp)
                                )
                            }
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = SpacingVeryLarge),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun LoadingScreenPreview() {
    NewsAppTheme {
        LoadingScreen()
    }
}
