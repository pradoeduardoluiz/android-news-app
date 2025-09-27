@file:OptIn(ExperimentalMaterial3Api::class)

package com.prado.eduardo.luiz.newsapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    NewsAppTheme {
        Scaffold(
            topBar = {
                Surface(
                    shadowElevation = 8.dp
                ) {
                    AppTopBar(
                        route = currentRoute.orEmpty(),
                        canNavigateBack = navController.previousBackStackEntry != null,
                        onBack = { navController.navigateUp() },
                        scrollBehavior = scrollBehavior
                    )
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
private fun AppTopBar(
    route: String,
    canNavigateBack: Boolean,
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    when {
        route.contains(AppRoute.Home::class.qualifiedName.toString()) -> {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier.size(140.dp, 22.dp),
                            painter = painterResource(R.drawable.ic_logo),
                            contentDescription = null
                        )
                        Spacer(Modifier.width(8.dp))
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }

        else -> {
            TopAppBar(
                title = {
                    Text("Article")
                },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                },
                actions = {},
                scrollBehavior = scrollBehavior
            )
        }
    }
}
