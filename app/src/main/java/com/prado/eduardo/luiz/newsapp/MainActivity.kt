package com.prado.eduardo.luiz.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.prado.eduardo.luiz.newsapp.navigation.Navigator
import com.prado.eduardo.luiz.newsapp.ui.NewsApp
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            navigator.bind(navController, lifecycleScope)
            NewsApp(navController)
        }
    }

    override fun onStop() {
        super.onStop()
        navigator.unbind()
    }
}
