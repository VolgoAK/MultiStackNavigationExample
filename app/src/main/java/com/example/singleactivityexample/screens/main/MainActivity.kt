package com.example.singleactivityexample.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.singleactivityexample.R
import com.example.singleactivityexample.navigation.MyAppNavigator
import com.example.singleactivityexample.navigation.Navigator
import com.example.singleactivityexample.navigation.PostsScreen
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navigator by inject<Navigator>()

    private val navigatorHolder = MyAppNavigator(
        this,
        R.id.fragmentContainer
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            navigator.setRootScreen(PostsScreen())
        }
    }

    override fun onResume() {
        super.onResume()
        navigator.setNavigator(navigatorHolder)
    }

    override fun onPause() {
        super.onPause()
        navigator.removeNavigator()
    }
}
