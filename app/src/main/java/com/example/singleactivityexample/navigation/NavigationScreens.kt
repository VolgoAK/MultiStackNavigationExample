package com.example.singleactivityexample.navigation

import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class NavigationScreens {
    abstract fun getSupportScreen(): SupportAppScreen
}

object PostsNavigationScreen: NavigationScreens() {
    override fun getSupportScreen(): SupportAppScreen {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}