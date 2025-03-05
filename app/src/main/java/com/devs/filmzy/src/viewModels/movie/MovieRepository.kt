package com.devs.filmzy.src.viewModels.movie

import com.devs.filmzy.src.models.GenreList.StateGenreList
import com.devs.filmzy.src.models.MovieDetail.StateMovieDetail
import com.devs.filmzy.src.models.MovieList.StateMovieList
import com.devs.filmzy.src.services.ApiInterface
import com.devs.filmzy.src.utils.combineMovieGenre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val api: ApiInterface
) {

    private val _genreState = MutableStateFlow(StateGenreList())
    val genreState = _genreState.asStateFlow()
    private var isFetchedGenre = false  // flag genre hanya di fetch sekali saja
    suspend fun fetchGenres() {
        // fetch di lakukan di MainActivity
        if (isFetchedGenre) return
        isFetchedGenre = true
        _genreState.value = StateGenreList(loading = true)
        try {
            val response = api.getGenreService()
            if (response.isSuccessful && response.body() != null) {
                _genreState.value = StateGenreList(
                    genres = response.body()!!.genres,
                    loading = false,
                    error = false
                )
            } else {
                _genreState.value = StateGenreList(error = true)
                isFetchedGenre = false
            }
        } catch (e: Exception) {
            _genreState.value = StateGenreList(error = true)
            isFetchedGenre = false
        }
    }

    private val _nowPlayingState = MutableStateFlow(StateMovieList())
    val nowPlayingState = _nowPlayingState.asStateFlow()
    suspend fun fetchNowPlayingMovies(page: Int = 1) {
        _nowPlayingState.value = _nowPlayingState.value.copy(loading = true)

        // menunggu genreState.result selesai terlebih dahulu
        val genres = genreState.first { it.genres.isNotEmpty() }.genres

        try {
            val response = api.getNowPlayingMovieService(page)
            if (response.isSuccessful && response.body() != null) {
                _nowPlayingState.value = StateMovieList(
                    results = combineMovieGenre(response.body()!!.results, genres),
                    total_pages = response.body()!!.total_pages,
                    total_results = response.body()!!.total_results,
                    loading = false,
                    error = false
                )
            } else {
                _nowPlayingState.value = StateMovieList(error = true)
            }
        } catch (e: Exception) {
            _nowPlayingState.value = StateMovieList(error = true)
        }
    }


    private val _discoveryState = MutableStateFlow(StateMovieList())
    val discoveryState = _discoveryState.asStateFlow()
    suspend fun fetchDiscoveryMovies(page: Int = 1) {
        _discoveryState.value = _discoveryState.value.copy(loading = true)

        // menunggu genreState.result selesai terlebih dahulu
        val genres = genreState.first { it.genres.isNotEmpty() }.genres

        try {
            val response = api.getDiscoveryMovieService(page)
            if (response.isSuccessful && response.body() != null) {
                _discoveryState.value = StateMovieList(
                    results = combineMovieGenre(response.body()!!.results, genres),
                    total_pages = response.body()!!.total_pages,
                    total_results = response.body()!!.total_results,
                    loading = false,
                    error = false
                )
            } else {
                _discoveryState.value = StateMovieList(error = true)
            }
        } catch (e: Exception) {
            _discoveryState.value = StateMovieList(error = true)
        }
    }

    private val _movieDetailState = MutableStateFlow(StateMovieDetail())
    val movieDetailState = _movieDetailState.asStateFlow()
    suspend fun fetchMovieDetail(movieId :Int){
        _movieDetailState.value = _movieDetailState.value.copy(loading = true)
        try {
            val response = api.getDetailMovieService(movieId)
            if (response.isSuccessful && response.body() != null) {
                _movieDetailState.value = StateMovieDetail(
                    results = response.body(),
                    loading = false,
                    error = false
                )
            } else {
                _movieDetailState.value = StateMovieDetail(error = true)
            }
        } catch (e: Exception) {
            _movieDetailState.value = StateMovieDetail(error = true)
        }
    }
    fun resetMovieDetail () {
        _movieDetailState.value = StateMovieDetail()
    }
}