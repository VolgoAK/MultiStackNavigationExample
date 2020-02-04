package com.example.singleactivityexample.model

data class Album(
    val userId: Long,
    val id: Long,
    val title: String
)

data class AlbumWithPhotos(
    val album: Album,
    var photos: List<Photo>,
    val user: User?
)