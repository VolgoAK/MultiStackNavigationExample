package com.example.singleactivityexample.screens.post

import androidx.lifecycle.*
import com.example.singleactivityexample.domain.NewsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import kotlin.system.measureTimeMillis

class PostViewModel(
    private val postId: Long,
    private val api: NewsRepository
) : ViewModel() {

    private val screenState = PostScreenState()

    private val postsLoadingLiveData = flow<PostPartialState> {
        val post = viewModelScope.async { api.getPostById(postId) }
        val comments = viewModelScope.async { api.getPostComments(postId) }
        emit(PostPartialState.PostWithComment(post.await(), comments.await()))
    }.onStart {
        emit(PostPartialState.Loading)
    }.catch { error ->
        emit(
            PostPartialState.ErrorState(error.message ?: "someError")
        )
    }.asLiveData()

    val stateLiveData = Transformations.map( postsLoadingLiveData) {
        it.applyToState(screenState)
    }

    fun onWriteNewCommentClicked() {

    }

}