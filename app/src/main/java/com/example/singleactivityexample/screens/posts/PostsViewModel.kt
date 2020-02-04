package com.example.singleactivityexample.screens.posts

import androidx.lifecycle.*
import com.example.singleactivityexample.domain.NewsApi
import com.example.singleactivityexample.domain.NewsRepository
import com.example.singleactivityexample.model.Post
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class PostsViewModel(
    private val api: NewsRepository
) : ViewModel() {

    private val refreshLiveData = MutableLiveData<Boolean>()
    private val postsLoadingLiveData = refreshLiveData.switchMap {
        flow { emit(api.getAllPosts()) }
            .map<List<Post>, PostsScreenPartialState> {
                PostsScreenPartialState.PostsLoadedState(it)
                    .chain(PostsScreenPartialState.LoadingState(false))
            }
            .onStart { emit(PostsScreenPartialState.LoadingState(true)) }
            .catch { emit(PostsScreenPartialState.ErrorState(it.message ?: "error")) }
            .asLiveData()
    }

    val flow = flow { emit(api.getAllPosts()) }
        .map<List<Post>, PostsScreenPartialState> {
            PostsScreenPartialState.PostsLoadedState(it)
                .chain(PostsScreenPartialState.LoadingState(false))
        }
        .onStart { emit(PostsScreenPartialState.LoadingState(true)) }
        .catch { emit(PostsScreenPartialState.ErrorState(it.message ?: "error")) }

    private val state = PostsScreenState()
    val stateLiveData = Transformations.map(postsLoadingLiveData) {
        it.applyToState(state)
    }

    fun refreshData() {
        refreshLiveData.value = true
    }


}