package com.example.singleactivityexample.screens.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.singleactivityexample.domain.NewsApi
import com.example.singleactivityexample.model.Comment
import com.example.singleactivityexample.model.Post
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class PostViewModel(
    private val post: Post,
    private val api: NewsApi
) : ViewModel() {

    val title = post.title

    val stateLiveData = flow<PostScreenState> {
        val comments = api.getCommentsByPostId(post.id)
        emit(PostScreenState.PostState(post, comments))
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