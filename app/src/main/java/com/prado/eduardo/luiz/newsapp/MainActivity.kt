package com.prado.eduardo.luiz.newsapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.prado.eduardo.luiz.newsapp.navigation.Navigator
import com.prado.eduardo.luiz.newsapp.ui.NewsApp
import com.prado.eduardo.luiz.newsapp.ui.auth.AuthViewModel
import com.prado.eduardo.luiz.newsapp.ui.auth.BiometricAuthManager
import com.prado.eduardo.luiz.newsapp.ui.auth.screen.AuthScreen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : FragmentActivity() {

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isAuthenticated by remember { mutableStateOf(false) }
            if (!isAuthenticated) {
                val biometricAuthManager = remember { BiometricAuthManager(this@MainActivity) }
                val authViewModel: AuthViewModel by viewModel { parametersOf(biometricAuthManager) }
                val state = authViewModel.uiState.collectAsStateWithLifecycle()
                AuthScreen(
                    state = state.value,
                    onPublish = { intent -> authViewModel.publish(intent) },
                    onAuthenticationSuccess = { isAuthenticated = true }
                )
            } else {
                val navController = rememberNavController()
                navigator.bind(navController, lifecycleScope)
                NewsApp(navController)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        navigator.unbind()
    }
}
