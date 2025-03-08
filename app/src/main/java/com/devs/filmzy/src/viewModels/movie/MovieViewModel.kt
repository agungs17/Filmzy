package com.devs.filmzy.src.viewModels.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.devs.filmzy.src.models.MovieDetail.StateMovieDetail
import com.devs.filmzy.src.models.MovieList.StateMovieList
import com.devs.filmzy.src.repositories.GenreListRepository
import com.devs.filmzy.src.repositories.MovieDetailRepository
import com.devs.filmzy.src.repositories.MovieListRepository
import com.devs.filmzy.src.utils.Constants
import com.devs.filmzy.src.utils.getDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import javax.inject.Inject

// init akan terpanggil bila view model di buat, jadi baiknya pakai init bila single page saja (view Scope - contoh : DetailMovieView) atau di panggil sekali saja

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val repository: GenreListRepository
) : ViewModel() {

    val state = repository.genreState // langsung ambil dari repository (bersifat di simpan terus sepanjang app berjalan saja)

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

    private val _state = MutableStateFlow(StateMovieList())
    val state = _state.asStateFlow()

    init {
        fetch()
    }

    private fun fetch(page: Int = 1) {
        if (_state.value.loading && _state.value.results.isNotEmpty()) return
        viewModelScope.launch {
            _state.value = repository.fetchMovieList(
                page = page,
                withReleaseType = "${Constants.ReleaseType.THEATRICAL}|${Constants.ReleaseType.THEATRICAL_LIMITED}",
                maxDate = getDate(),
                minDate = getDate(LocalDate.now().minusMonths(1))
            )
        }
    }
}

@HiltViewModel
class DiscoveryMovieViewModel @Inject constructor(
    repository: MovieListRepository
) : ViewModel() {
    val state = repository.fetchPagingMovieList(sortBy = Constants.SortByType.VOTE_COUNT_DESC).cachedIn(viewModelScope)
}

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
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
            _state.value = movieDetailRepository.fetchMovieDetail(movieId)
        }
    }
}
