package com.example.singleactivityexample

import android.app.Application
import com.example.singleactivityexample.di.appModule
import com.example.singleactivityexample.di.containerModule
import com.example.singleactivityexample.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.MESSAGE
import timber.log.Timber
import java.util.logging.Logger

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            logger(object : org.koin.core.logger.Logger() {
                override fun log(level: Level, msg: MESSAGE) {
                    Timber.d("TESTING $msg")
                }
            })
            androidContext(this@App)
            modules(listOf(appModule, viewModelModule, containerModule))
        }

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}