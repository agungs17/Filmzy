package com.devs.filmzy.src.viewModels.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GenreViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val state = repository.genreState

    fun fetch() {
        if (state.value.loading && state.value.genres.isNotEmpty()) return
        viewModelScope.launch {
            repository.fetchGenres()
        }
    }
}

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val state = repository.nowPlayingState

    fun fetch() {
        if (state.value.loading && state.value.results.isNotEmpty()) return
        viewModelScope.launch {
            repository.fetchNowPlayingMovies()
        }
    }
}

@HiltViewModel
class DiscoveryMovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val state = repository.discoveryState

    fun fetch() {
        if (state.value.loading && state.value.results.isNotEmpty()) return
        viewModelScope.launch {
            repository.fetchDiscoveryMovies()
        }
    }
}

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val state = repository.movieDetailState

    fun fetch(movieId : Int) {
        viewModelScope.launch {
            repository.fetchMovieDetail(movieId)
        }
    }

    fun reset() {
        viewModelScope.launch {
            repository.resetMovieDetail()
        }
    }
}