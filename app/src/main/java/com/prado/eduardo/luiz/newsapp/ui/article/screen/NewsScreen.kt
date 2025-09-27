package com.prado.eduardo.luiz.newsapp.ui.article.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.prado.eduardo.luiz.newsapp.R
import com.prado.eduardo.luiz.newsapp.navigation.AppRoute
import com.prado.eduardo.luiz.newsapp.ui.article.ArticleIntent
import com.prado.eduardo.luiz.newsapp.ui.article.ArticleUIState
import com.prado.eduardo.luiz.newsapp.ui.article.ArticleViewModel
import com.prado.eduardo.luiz.newsapp.ui.theme.NewsAppTheme
import com.prado.eduardo.luiz.newsapp.ui.theme.SpacingMiddle
import com.prado.eduardo.luiz.newsapp.ui.theme.SpacingNormal
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    article: AppRoute.Article
) {
    val viewModel: ArticleViewModel = koinViewModel()
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.publish(
            ArticleIntent.LoadArticle(
                author = article.author,
                title = article.title,
                description = article.description,
                url = article.url,
                urlToImage = article.urlToImage,
                publishedAt = article.publishedAt,
                content = article.content
            )
        )
    }

    Column(modifier = modifier) {
        ArticleScreenContent(
            state = state.value,
            onPublish = { intent -> viewModel.publish(intent) }
        )
    }
}

@Composable
private fun ArticleScreenContent(
    state: ArticleUIState,
    onPublish: (ArticleIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding()
            .clickable { }
            .padding(start = 18.dp, end = 18.dp, bottom = 32.dp)
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Column {
            state.urlToImage.let { imageUrl ->
                Box(
                    contentAlignment = Alignment.BottomStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(184.dp)
                ) {
                    if (LocalInspectionMode.current) {
                        Image(
                            painter = painterResource(id = R.drawable.image_placeholder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        AsyncImage(
                            model = imageUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.image_placeholder)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(
                        start = SpacingMiddle,
                        end = SpacingMiddle
                    )
            ) {
                Row(
                    modifier = Modifier.padding(top = SpacingMiddle),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height(18.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = state.author,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier =
                            Modifier
                                .padding(start = SpacingNormal)
                                .weight(1f)
                    )
                }
                Column {
                    Row(
                        modifier = Modifier.padding(top = SpacingNormal)
                    ) {
                        Column {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = state.title,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                    if (state.description.isEmpty().not()) {
                        Row(
                            modifier = Modifier
                                .padding(top = SpacingMiddle)
                                .height(IntrinsicSize.Min)
                        ) {
                            Text(
                                text = state.description,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f, true),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(top = SpacingMiddle))
            }
        }
    }
}

//preview for ArticleScreenContent
@PreviewLightDark
@Composable
private fun ArticleScreenContentPreview() {
    NewsAppTheme {
        ArticleScreenContent(
            state = ArticleUIState(
                isLoading = false,
                author = "John Doe",
                title = "Sample Article Title",
                description = "This is a sample description of the article. It provides a brief overview of the content.",
                urlToImage = "https://via.placeholder.com/150"
            ),
            onPublish = {}
        )
    }
}
