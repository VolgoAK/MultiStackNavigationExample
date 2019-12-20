package com.example.singleactivityexample.base.nestendcontainer

import com.example.singleactivityexample.navigation.Navigator
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val SCOPE_PAGE_ONE = "scope_page_one"

val containerModule = module {
    scope(named(SCOPE_PAGE_ONE)) {
        scoped { Navigator() }
    }
}