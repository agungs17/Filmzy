package com.devs.filmzy.src.viewModels.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devs.filmzy.src.repositories.GenreListRepository
import com.devs.filmzy.src.repositories.MovieDetailRepository
import com.devs.filmzy.src.repositories.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GenreViewModel @Inject constructor(
    private val repository: GenreListRepository
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
    private val repository: MovieListRepository
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
    private val repository: MovieListRepository
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
    private val movieDetailRepository: MovieDetailRepository
) : ViewModel() {

    val state = movieDetailRepository.movieDetailState

    fun fetch(movieId : Int) {
        viewModelScope.launch {
            movieDetailRepository.fetchMovieDetail(movieId)
        }
    }

    fun reset() {
        viewModelScope.launch {
            movieDetailRepository.resetMovieDetail()
        }
    }
}