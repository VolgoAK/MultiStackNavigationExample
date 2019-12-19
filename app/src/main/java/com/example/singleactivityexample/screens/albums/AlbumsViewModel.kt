package com.example.singleactivityexample.screens.albums

import androidx.lifecycle.*
import com.example.singleactivityexample.domain.NewsRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class AlbumsViewModel(
    private val api: NewsRepository
) : ViewModel() {

    private val refreshLiveData = MutableLiveData<Boolean>().apply { value = true }
    private val albumsLiveData = refreshLiveData.switchMap {
        flow<AlbumScreenPartialState> {
            emit(
                AlbumScreenPartialState.Albums(api.getAllAlbums())
                    .chain(AlbumScreenPartialState.Loading(false))
            )
        }
            .onStart { emit(AlbumScreenPartialState.Loading(true)) }
            .catch { emit(AlbumScreenPartialState.Error(it.message ?: "error")) }
            .asLiveData()
    }

    private val state = AlbumsScreenState()
    val stateLiveData = Transformations.map(albumsLiveData) {
        it.applyToState(state)
    }


}