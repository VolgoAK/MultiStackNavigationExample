package com.example.singleactivityexample.screens.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.singleactivityexample.domain.NewsApi
import com.example.singleactivityexample.model.Post
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class PostsViewModel(
    private val api: NewsApi
) : ViewModel() {

    val postsLiveData = flow { emit(api.getAllPosts()) }
        .map { PostsScreenState.PostsLoadedState(it) }
        .onStart { PostsScreenState.LoadingState }
        .asLiveData()

    sealed class PostsScreenState {
        object LoadingState: PostsScreenState()
        data class PostsLoadedState(val posts: List<Post>): PostsScreenState()
        data class ErrorState(val error: String): PostsScreenState()
    }
}