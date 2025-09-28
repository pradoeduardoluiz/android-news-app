package com.prado.eduardo.luiz.newsapp.ui.auth.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prado.eduardo.luiz.newsapp.R
import com.prado.eduardo.luiz.newsapp.ui.auth.AuthIntent
import com.prado.eduardo.luiz.newsapp.ui.auth.AuthState
import com.prado.eduardo.luiz.newsapp.ui.auth.AuthUIState

@Composable
fun AuthScreen(
    state: AuthUIState,
    onPublish: (AuthIntent) -> Unit,
    onAuthenticationSuccess: () -> Unit
) {

    LaunchedEffect(state.authState) {
        if (state.authState is AuthState.Authenticated) {
            onAuthenticationSuccess()
        }
    }

    LaunchedEffect(Unit) {
        onPublish(AuthIntent.Initialize)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state.authState) {
            is AuthState.Loading -> {
                LoadingContent()
            }

            is AuthState.RequiresBiometric -> {
                BiometricPromptContent()
            }

            is AuthState.AuthenticationFailed -> {
                AuthenticationFailedContent(
                    onRetry = { onPublish(AuthIntent.Retry) }
                )
            }

            // This will be handled by LaunchedEffect above
            is AuthState.Authenticated -> Unit
        }
    }
}

@Composable
private fun LoadingContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.auth_initializing),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun BiometricPromptContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(32.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Fingerprint,
            contentDescription = stringResource(R.string.fingerprint_content_description),
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.auth_required_title),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.auth_required_message),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun AuthenticationFailedContent(onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(32.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Fingerprint,
            contentDescription = stringResource(R.string.fingerprint_content_description),
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.auth_failed_title),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.auth_failed_message),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.auth_try_again))
        }
    }
}
