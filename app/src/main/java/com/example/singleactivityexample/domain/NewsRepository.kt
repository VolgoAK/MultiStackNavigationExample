package com.example.singleactivityexample.domain

class NewsRepository(private  val api: NewsApi) {

    suspend fun getAllPosts() = api.getAllPosts()

    suspend fun getPostById(postId: Long) = api.getPostById(postId)

    suspend fun getPostComments(postId: Long) = api.getCommentsByPostId(postId)

    suspend fun getAllUsers() = api.getUsers()

    suspend fun getUserById(id: Long) = api.getUserById(id)
}