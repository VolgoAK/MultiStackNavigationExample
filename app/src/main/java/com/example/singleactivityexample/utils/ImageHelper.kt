package com.example.singleactivityexample.utils

import com.example.singleactivityexample.R
import com.example.singleactivityexample.model.User
import kotlin.math.abs

private val images = listOf(
    R.drawable.family,
    R.drawable.fishes,
    R.drawable.home,
    R.drawable.insects,
    R.drawable.pets
)

fun User.getImage() = images[abs(this.hashCode()) % images.size]

fun User.getImageTag() = "userImage${this.hashCode()}"