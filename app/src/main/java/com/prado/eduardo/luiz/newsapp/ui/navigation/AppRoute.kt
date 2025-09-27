package com.prado.eduardo.luiz.newsapp.ui.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable
    data object Home : AppRoute

    @Serializable
    data object Article : AppRoute
}
