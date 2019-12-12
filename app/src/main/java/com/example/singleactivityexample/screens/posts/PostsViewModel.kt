package com.example.singleactivityexample.screens.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.example.singleactivityexample.domain.NewsApi
import com.example.singleactivityexample.model.Post
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class PostsViewModel(
    private val api: NewsApi
) : ViewModel() {

    private val refreshLiveData = MutableLiveData<Boolean>().apply {
        value = true
    }

    val stateLiveData = refreshLiveData.switchMap {
        flow { emit(api.getAllPosts()) }
            .map<List<Post>, PostsScreenState> { PostsScreenState.PostsLoadedState(it) }
            .onStart { emit(PostsScreenState.LoadingState) }
            .asLiveData()
    }

    fun refreshData() {
        refreshLiveData.value = true
    }

    sealed class PostsScreenState {
        object LoadingState : PostsScreenState()
        data class PostsLoadedState(val posts: List<Post>) : PostsScreenState()
        data class ErrorState(val error: String) : PostsScreenState()
    }
}