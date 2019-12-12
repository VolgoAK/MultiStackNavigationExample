package com.example.singleactivityexample.extensions

import android.view.View

fun View.setVisibility(visible: Boolean) {
    if (visible) makeVisible() else makeGone()
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}