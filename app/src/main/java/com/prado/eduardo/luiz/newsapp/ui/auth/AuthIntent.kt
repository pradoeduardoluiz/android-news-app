package com.prado.eduardo.luiz.newsapp.ui.auth

sealed interface AuthIntent {
    data object Initialize : AuthIntent
    data object Retry : AuthIntent
}
