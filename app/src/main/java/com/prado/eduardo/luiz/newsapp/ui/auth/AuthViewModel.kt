package com.prado.eduardo.luiz.newsapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prado.eduardo.luiz.newsapp.common.dispatcher.DispatchersProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val biometricAuthManager: BiometricAuthManager,
    private val dispatcher: DispatchersProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUIState())
    val uiState: StateFlow<AuthUIState> = _uiState.asStateFlow()

    fun publish(intention: AuthIntent) {
        viewModelScope.launch(dispatcher.io) {
            when (intention) {
                is AuthIntent.Initialize -> initializeAuth()
                is AuthIntent.Retry -> retry()
            }
        }
    }


    private fun initializeAuth() {
        viewModelScope.launch {
            if (!biometricAuthManager.isBiometricAvailable()) {
                _uiState.update { it.copy(authState = AuthState.Authenticated) }
            } else {
                _uiState.update { it.copy(authState = AuthState.RequiresBiometric) }
                performBiometricAuth()
            }
        }
    }

    private fun retry() {
        viewModelScope.launch {
            _uiState.update { it.copy(authState = AuthState.Loading) }
            performBiometricAuth()
        }
    }

    private suspend fun performBiometricAuth() {
        when (biometricAuthManager.authenticate()) {
            BiometricAuthManager.BiometricResult.Success -> {
                _uiState.update { it.copy(authState = AuthState.Authenticated) }
            }

            BiometricAuthManager.BiometricResult.Failed,
            BiometricAuthManager.BiometricResult.Error -> {
                _uiState.update { it.copy(authState = AuthState.AuthenticationFailed) }
            }

            BiometricAuthManager.BiometricResult.NotAvailable -> {
                _uiState.update { it.copy(authState = AuthState.Authenticated) }
            }
        }
    }
}
