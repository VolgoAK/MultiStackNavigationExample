package com.example.singleactivityexample.screens.post

import com.example.singleactivityexample.base.ScreenPartialState
import com.example.singleactivityexample.base.ScreenState
import com.example.singleactivityexample.model.Comment
import com.example.singleactivityexample.model.Post

sealed class PostPartialState(reducer: (PostScreenState) -> PostScreenState) :
    ScreenPartialState<PostScreenState>(reducer) {

    data class Loading(
        val isLoading: Boolean
    ) : PostPartialState({ state -> state.apply { loading = isLoading } })

    data class PostWithComment(
        val newPost: Post,
        val newComments: List<Comment>
    ) : PostPartialState({ state ->
        state.apply {
            post = newPost
            comments = newComments
        }
    })

    data class ErrorState(val errorMessage: String) : PostPartialState({ state ->
        state.apply {
            error = errorMessage
        }
    })
}

data class PostScreenState(
    var post: Post? = null,
    var comments: List<Comment> = emptyList(),
    var error: String? = null,
    var loading: Boolean = false
) : ScreenState()