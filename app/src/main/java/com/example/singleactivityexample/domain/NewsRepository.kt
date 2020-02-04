package com.example.singleactivityexample.domain

import com.example.singleactivityexample.model.Photo
import kotlinx.coroutines.delay

class NewsRepository(private  val api: NewsApi) {

    suspend fun getAllPosts() = api.getAllPosts()

    suspend fun getPostById(postId: Long) = api.getPostById(postId)

    suspend fun getPostComments(postId: Long) = api.getCommentsByPostId(postId)

    suspend fun getAllUsers() = api.getUsers()

    suspend fun getUserById(id: Long) = api.getUserById(id)

    suspend fun getAllAlbums() = api.getAllAlbums()

    suspend fun getPhotosByAlbumId(id: Long) : List<Photo> {
        delay(5000 - id * 200)
        return api.getPhotosByAlbumId(id)
    }
}