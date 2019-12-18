package com.example.singleactivityexample.screens.posts

import com.example.singleactivityexample.model.Post
import com.example.singleactivityexample.screens.post.PostScreenState

data class PostsScreenState(
    var posts: List<Post> = emptyList(),
    var loading: Boolean = false,
    var errorMessage: String? = null
)

sealed class PostsScreenPartialState(val reducer: (PostsScreenState) -> PostsScreenState) {

    fun applyToState(state: PostsScreenState) = reducer(state)

    object LoadingState : PostsScreenPartialState({ state ->
        state.apply { loading = true }
    })

    data class PostsLoadedState(val newPosts: List<Post>) : PostsScreenPartialState({ state ->
        state.apply {
            loading = false
            posts = newPosts
        }
    })

    data class ErrorState(val error: String) : PostsScreenPartialState({ state ->
        state.apply {
            errorMessage = error
        }
    })
}