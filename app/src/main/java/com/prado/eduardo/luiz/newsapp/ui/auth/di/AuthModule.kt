package com.prado.eduardo.luiz.newsapp.ui.auth.di

import androidx.fragment.app.FragmentActivity
import com.prado.eduardo.luiz.newsapp.ui.auth.AuthViewModel
import com.prado.eduardo.luiz.newsapp.ui.auth.BiometricAuthManager
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    factory { (activity: FragmentActivity) ->
        BiometricAuthManager(activity)
    }
    viewModel { (biometricAuthManager: BiometricAuthManager) ->
        AuthViewModel(
            biometricAuthManager = biometricAuthManager,
            dispatcher = get()
        )
    }
}
