package com.example.singleactivityexample.screens.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.singleactivityexample.domain.NewsApi
import com.example.singleactivityexample.model.Comment
import com.example.singleactivityexample.model.Post
import kotlinx.coroutines.flow.*

class PostViewModel(
    private val post: Post,
    private val api: NewsApi
) : ViewModel() {

    val title = post.title

    val state = liveData {
        emit(PostScreenState.LoadingState)
    }

    val stateLiveData = flow {
        emit(api.getCommentsByPostId(post.id))
    }.map<List<Comment>, PostScreenState> {
        PostScreenState.PostState(post, it)
    }.onStart {
        PostScreenState.LoadingState
    }.asLiveData()

    fun onWriteNewCommentClicked() {

    }

    sealed class PostScreenState {
        object LoadingState : PostScreenState()
        data class PostState(
            val post: Post,
            val comments: List<Comment>
        ) : PostScreenState()
    }
}