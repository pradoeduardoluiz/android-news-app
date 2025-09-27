package com.prado.eduardo.luiz.newsapp.navigation

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.prado.eduardo.luiz.newsapp.common.dispatcher.DispatchersProvider
import kotlinx.coroutines.launch

abstract class Navigator : NavigatorBinder, NavigatorRoute

interface NavigatorBinder {
    fun bind(
        navController: NavController,
        lifecycleScope: LifecycleCoroutineScope
    )

    fun unbind()
}

interface NavigatorRoute {
    fun navigate(route: AppRoute)
    fun popUp()
}

class NavigatorImpl(
    private val dispatcher: DispatchersProvider
) : Navigator() {
    private var navController: NavController? = null
    private var lifecycleScope: LifecycleCoroutineScope? = null

    override fun bind(
        navController: NavController,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        this.navController = navController
        this.lifecycleScope = lifecycleScope
    }

    override fun unbind() {
        navController = null
        lifecycleScope = null
    }

    override fun navigate(route: AppRoute) {
        lifecycleScope?.launch(dispatcher.ui) {
            navController?.navigate(route)
        }
    }

    override fun popUp() {
        navController?.popBackStack()
    }
}
