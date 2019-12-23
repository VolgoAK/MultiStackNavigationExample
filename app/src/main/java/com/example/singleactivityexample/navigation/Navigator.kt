package com.example.singleactivityexample.navigation

import ru.terrakok.cicerone.Cicerone
import timber.log.Timber

class Navigator {
    private val cicerone by lazy { Cicerone.create() }

    fun setNavigator(navigator: MyAppNavigator) {
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    fun removeNavigator() {
        cicerone.navigatorHolder.removeNavigator()
    }

    fun navigateTo(screen: MyAppScreen) {
        cicerone.router.navigateTo(screen)
    }

    fun backTo(screen: MyAppScreen) {
        cicerone.router.backTo(screen)
    }

    fun setRootScreen(screen: MyAppScreen) {
        cicerone.router.newRootScreen(screen)
    }
}