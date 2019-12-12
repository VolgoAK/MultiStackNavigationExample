package com.example.singleactivityexample.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val id: Long,
    val title: String,
    val body: String,
    val userId: Int
): Parcelable