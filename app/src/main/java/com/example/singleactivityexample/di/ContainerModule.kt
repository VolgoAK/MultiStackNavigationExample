package com.example.singleactivityexample.di

import com.example.singleactivityexample.navigation.Navigator
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val SCOPE_NAME_PAGER = "pager_scope"

val containerModule = module {
    scope(named(SCOPE_NAME_PAGER)) {
        scoped {
            Navigator()
        }
    }
}