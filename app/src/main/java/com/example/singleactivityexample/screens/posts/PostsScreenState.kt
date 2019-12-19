package com.example.singleactivityexample.screens.posts

import com.example.singleactivityexample.base.ScreenPartialState
import com.example.singleactivityexample.base.ScreenState
import com.example.singleactivityexample.model.Post

data class PostsScreenState(
    var posts: List<Post> = emptyList(),
    var loading: Boolean = false,
    var errorMessage: String? = null
) : ScreenState()

sealed class PostsScreenPartialState(reducer: (PostsScreenState) -> PostsScreenState) :
    ScreenPartialState<PostsScreenState>(reducer) {

    data class LoadingState(val isLoading: Boolean) : PostsScreenPartialState({ state ->
        state.apply { loading = isLoading }
    })

    data class PostsLoadedState(val newPosts: List<Post>) : PostsScreenPartialState({ state ->
        state.apply {
            posts = newPosts
        }
    })

    data class ErrorState(val error: String) : PostsScreenPartialState({ state ->
        state.apply {
            errorMessage = error
        }
    })
}

