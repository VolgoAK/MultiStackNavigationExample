package com.example.singleactivityexample.screens.post

import com.example.singleactivityexample.model.Comment
import com.example.singleactivityexample.model.Post

sealed class PostPartialState(val reducer: (PostScreenState) -> PostScreenState) {

    fun applyToState(state: PostScreenState) = reducer(state)

    object Loading : PostPartialState({ state -> state.apply { loading = true } })

    data class PostWithComment(
        val newPost: Post,
        val newComments: List<Comment>
    ) : PostPartialState({ state ->
        state.apply {
            loading = false
            post = newPost
            comments = newComments
        }
    })

    data class ErrorState(val errorMessage: String) : PostPartialState({state ->
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
)