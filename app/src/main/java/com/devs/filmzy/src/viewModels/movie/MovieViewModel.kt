package com.devs.filmzy.src.viewModels.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.devs.filmzy.src.models.MovieDetail.StateMovieDetail
import com.devs.filmzy.src.models.MovieList.MovieListParams
import com.devs.filmzy.src.models.MovieList.StateMovieList
import com.devs.filmzy.src.repositories.GenreListRepository
import com.devs.filmzy.src.repositories.MoveListPagingRepository
import com.devs.filmzy.src.repositories.MovieDetailRepository
import com.devs.filmzy.src.repositories.MovieListRepository
import com.devs.filmzy.src.viewModels.global.GlobalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

// init akan terpanggil bila view model di buat, jadi baiknya pakai init bila single page saja (view Scope - contoh : DetailMovieView) atau di panggil sekali saja

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val genreRepository: GenreListRepository,
    globalRepository: GlobalRepository
) : ViewModel() {

    val state = globalRepository.globalState.value.genreState // langsung ambil dari repository global (bersifat di simpan terus sepanjang app berjalan saja)

    fun fetch() {
        if (state.loading && state.genres.isNotEmpty()) return
        viewModelScope.launch {
            genreRepository.fetchGenres()
        }
    }
}

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieListRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StateMovieList())
    val state = _state.asStateFlow()

    fun fetch(movieParams : MovieListParams) {
        if (_state.value.loading && _state.value.results.isNotEmpty()) return
        viewModelScope.launch {
            _state.value = repository.fetchMovieList(movieParams)
        }
    }
}

@HiltViewModel
class MovieListPagingViewModel @Inject constructor(
    repository: MoveListPagingRepository
) : ViewModel() {
    private val _params = MutableStateFlow(MovieListParams())

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = _params.flatMapLatest { params ->
        repository.fetchMovieListPaging(params)
    }.cachedIn(viewModelScope)

    fun updateParams(newParams: MovieListParams) {
        _params.value = newParams
    }
}

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: String? = savedStateHandle["movieId"] // didapat dari NavHost (Navigation)
    private val _state = MutableStateFlow(StateMovieDetail())
    val state = _state.asStateFlow()

    init {
        movieId?.let { fetch(it) }
    }

    private fun fetch(movieId: String) {
        viewModelScope.launch {
            _state.value = repository.fetchMovieDetail(movieId)
        }
    }
}
