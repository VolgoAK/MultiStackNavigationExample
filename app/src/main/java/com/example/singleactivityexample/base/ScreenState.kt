package com.example.singleactivityexample.base

abstract class ScreenState

abstract class ScreenPartialState<S : ScreenState>(val reducer: (S) -> S) {

    private var chainNext: ScreenPartialState<S>? = null

    open fun applyToState(state: S): S {
        return reducer(state).apply {
            chainNext?.applyToState(state)
        }
    }

    fun <T : ScreenPartialState<S>> chain(nextState: T): T {
        nextState.chainNext = this
        return nextState
    }
}