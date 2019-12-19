package com.example.singleactivityexample.screens.albums

import com.example.singleactivityexample.base.ScreenPartialState
import com.example.singleactivityexample.base.ScreenState
import com.example.singleactivityexample.model.Album

data class AlbumsScreenState(
    var albums: List<Album> = emptyList(),
    var loading: Boolean = false,
    var error: String? = null
): ScreenState()

sealed class AlbumScreenPartialState(reducer: (AlbumsScreenState) -> AlbumsScreenState)
    : ScreenPartialState<AlbumsScreenState>(reducer) {

    data class Loading(val isLoading: Boolean): AlbumScreenPartialState({ state ->
        state.apply { loading = isLoading }
    })

    data class Albums(val newAlbums: List<Album>): AlbumScreenPartialState({state ->
        state.apply { albums = newAlbums }
    })

    data class Error(val errorMessage: String): AlbumScreenPartialState({ state ->
        state.apply { error = errorMessage }
    })
}