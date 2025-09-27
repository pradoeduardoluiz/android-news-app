@file:OptIn(ExperimentalMaterial3Api::class)

package com.prado.eduardo.luiz.newsapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.prado.eduardo.luiz.newsapp.R
import com.prado.eduardo.luiz.newsapp.navigation.AppRoute
import com.prado.eduardo.luiz.newsapp.ui.article.screen.ArticleScreen
import com.prado.eduardo.luiz.newsapp.ui.news.screen.NewsScreen
import com.prado.eduardo.luiz.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsApp(
    navController: NavHostController
) {
    NewsAppTheme {
        Scaffold(
            topBar = {
                Surface(
                    shadowElevation = 8.dp
                ) {
                    HomeTopBar()
                }
            },
        ) { paddingValues ->
            NavHost(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                navController = navController,
                startDestination = AppRoute.Home,
            ) {
                composable<AppRoute.Home> { NewsScreen() }
                composable<AppRoute.Article> { backStackEntry ->
                    val article: AppRoute.Article = backStackEntry.toRoute()
                    ArticleScreen(article = article)
                }
            }
        }
    }
}

@Composable
fun HomeTopBar() {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(140.dp, 22.dp),
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = null
                )
            }
        },
    )
}

@PreviewLightDark
@Composable
fun HomeTopBarPreview() {
    NewsAppTheme {
        HomeTopBar()
    }
}
