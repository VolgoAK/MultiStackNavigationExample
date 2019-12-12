package com.example.singleactivityexample.navigation

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Navigator {
    private val cicerone by lazy { Cicerone.create() }

    fun setNavigator(navigator: SupportAppNavigator) {
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    fun removeNavigator() {
        cicerone.navigatorHolder.removeNavigator()
    }

    fun navigateTo(screen: SupportAppScreen) {
        cicerone.router.navigateTo(screen)
    }

    fun backTo(screen: SupportAppScreen) {
        cicerone.router.backTo(screen)
    }

    fun setRootScreen(screen: SupportAppScreen) {
        cicerone.router.newRootScreen(screen)
    }
}