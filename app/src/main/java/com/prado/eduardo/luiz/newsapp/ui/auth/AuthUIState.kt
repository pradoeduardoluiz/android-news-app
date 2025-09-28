package com.prado.eduardo.luiz.newsapp.ui.auth

data class AuthUIState(
    val authState: AuthState = AuthState.Loading,
)


sealed class AuthState {
    data object Loading : AuthState()
    data object Authenticated : AuthState()
    data object RequiresBiometric : AuthState()
    data object AuthenticationFailed : AuthState()
}
