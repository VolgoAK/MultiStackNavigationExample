package com.example.singleactivityexample.domain

import com.example.singleactivityexample.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {

    @GET(value = "posts")
    suspend fun getAllPosts(): List<Post>

    @GET(value = "posts/{id}")
    suspend fun getPostById(@Path("id") id: Long): Post

    @GET(value = "comments")
    suspend fun getCommentsByPostId(@Query("postId") id: Long): List<Comment>

    @GET(value = "users")
    suspend fun getUsers(): List<User>

    @GET(value = "users/{id}")
    suspend fun getUserById(@Path("id") id: Long): User

    @GET(value = "albums")
    suspend fun getAllAlbums(): List<Album>

    @GET(value = "photos")
    suspend fun getPhotosByAlbumId(@Query("albumId") id: Long): List<Photo>
}