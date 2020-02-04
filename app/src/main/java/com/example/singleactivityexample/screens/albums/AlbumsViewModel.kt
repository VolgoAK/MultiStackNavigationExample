package com.example.singleactivityexample.screens.albums

import androidx.lifecycle.*
import com.example.singleactivityexample.domain.NewsRepository
import com.example.singleactivityexample.model.Album
import com.example.singleactivityexample.model.AlbumWithPhotos
import com.example.singleactivityexample.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class AlbumsViewModel(
    private val api: NewsRepository
) : ViewModel() {

    private val refreshLiveData = MutableLiveData<Boolean>().apply { value = true }
    private val albumsLiveData = refreshLiveData.switchMap {
        flow<AlbumScreenPartialState> {
            val albums = api.getAllAlbums()
            emit(
                AlbumScreenPartialState.Albums(albums.map { AlbumWithPhotos(it, emptyList(), null) })
                    .chain(AlbumScreenPartialState.Loading(false))
            )
        }
            .onStart { emit(AlbumScreenPartialState.Loading(true)) }
            .catch { emit(AlbumScreenPartialState.Error(it.message ?: "error")) }
            .asLiveData()
    }

    private val photosChannel = Channel<Long>(Channel.BUFFERED)
    private val readyPhotosChannel = Channel<Pair<Long, List<Photo>>>(Channel.BUFFERED)
    private val readyPhotosLD = readyPhotosChannel
        .consumeAsFlow()
        .asLiveData()

    private val state = AlbumsScreenState()
    val stateLiveData = MediatorLiveData<AlbumsScreenState>()

    private var latestLoadedPosition = 0
    private val latestLoadedChannel = Channel<Int>()


    init {
        //todo add room and subscribe to changes
        stateLiveData.addSource(albumsLiveData) {
            stateLiveData.value = it.applyToState(state)
        }

        viewModelScope.launch {
            photosChannel.consumeEach {
                launch {
                    val photos = api.getPhotosByAlbumId(it)
                    readyPhotosChannel.send(it to photos)
                }
            }
        }

        stateLiveData.addSource(readyPhotosLD) { photos ->
            val album = state.albums.find { it.album.id == photos.first }
            album?.photos = photos.second
            stateLiveData.value = state
        }

        viewModelScope.launch {
            latestLoadedChannel.consumeEach {
                if(it > latestLoadedPosition) {
                    val positions = latestLoadedPosition..it
                    positions.map { state.albums[it].album.id }
                        .forEach { photosChannel.offer(it) }

                    latestLoadedPosition = it
                }
            }
        }
    }

    fun onAlbumVisible(position: Int) {
        latestLoadedChannel.offer(position + 5)
    }
}