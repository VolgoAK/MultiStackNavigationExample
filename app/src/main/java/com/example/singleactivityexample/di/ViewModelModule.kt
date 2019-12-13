package com.example.singleactivityexample.di

import com.example.singleactivityexample.model.Post
import com.example.singleactivityexample.screens.posts.PostsViewModel
import com.example.singleactivityexample.screens.post.PostViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        PostsViewModel(
            get()
        )
    }
    viewModel { (postId: Long) -> PostViewModel(postId, get()) }
}