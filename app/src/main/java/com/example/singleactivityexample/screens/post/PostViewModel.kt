package com.example.singleactivityexample.screens.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.singleactivityexample.domain.NewsApi
import com.example.singleactivityexample.domain.NewsRepository
import com.example.singleactivityexample.model.Comment
import com.example.singleactivityexample.model.Post
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

class PostViewModel(
    private val postId: Long,
    private val api: NewsRepository
) : ViewModel() {

    val stateLiveData = flow<PostScreenState> {
        Timber.d("TESTING start to load post")
        val post = viewModelScope.async { api.getPostById(postId) }
        Timber.d("TESTING start to load comments")
        val comments = viewModelScope.async { api.getPostComments(postId) }
        emit(PostScreenState.PostState(post.await(), comments.await()))
    }.onStart {
        emit(PostScreenState.LoadingState)
    }.catch { error ->
        emit(
            PostScreenState.ErrorState(error.message ?: "someError")
        )
    }.asLiveData()

    fun onWriteNewCommentClicked() {

    }

    sealed class PostScreenState {
        object LoadingState : PostScreenState()
        data class PostState(
            val post: Post,
            val comments: List<Comment>
        ) : PostScreenState()
        data class ErrorState(val error: String): PostScreenState()
    }
}