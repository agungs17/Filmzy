package com.devs.filmzy.src.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.devs.filmzy.src.models.GenreList.StateGenreList
import com.devs.filmzy.src.models.MovieDetail.StateMovieDetail
import com.devs.filmzy.src.models.MovieList.Movie
import com.devs.filmzy.src.models.MovieList.MovieListParams
import com.devs.filmzy.src.models.MovieList.StateMovieList
import com.devs.filmzy.src.paging.movie.MoviePagingSource
import com.devs.filmzy.src.services.ApiInterface
import com.devs.filmzy.src.utils.combineMovieGenre
import com.devs.filmzy.src.viewModels.global.GlobalRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

// >>> Genre List Repository
class GenreListRepository @Inject constructor(
    private val globalRepository: GlobalRepository,
    private val api: ApiInterface
) {
    suspend fun fetchGenres() {
        globalRepository.updateGlobalState { copy(genreState = StateGenreList(loading = true)) }
        try {
            val response = api.getGenreService()
            if (response.isSuccessful && response.body() != null) {
                globalRepository.updateGlobalState { copy(genreState = StateGenreList(
                    genres = response.body()!!.genres,
                    loading = false,
                    error = false
                ))}
            } else {
                globalRepository.updateGlobalState { copy(genreState = StateGenreList(error = true)) }
            }
        } catch (e: Exception) {
            globalRepository.updateGlobalState { copy(genreState = StateGenreList(error = true)) }
        }
    }
}

// >>> Movie List Repository
class MovieListRepository @Inject constructor(
    private val api: ApiInterface,
    private val globalRepository: GlobalRepository
) {
    suspend fun fetchMovieList(
        movieParams : MovieListParams
    ): StateMovieList {
        StateMovieList(loading = true)
        val genres = globalRepository.globalState.first { it.genreState.genres.isNotEmpty() }.genreState.genres
        return try {
            val response = api.getMovieListService(
                page = movieParams.page,
                sortBy = movieParams.sortBy,
                withReleaseType = movieParams.withReleaseType,
                maxDate = movieParams.maxDate,
                minDate =  movieParams.minDate,
                withGenres = movieParams.withGenres
            )
            if (response.isSuccessful && response.body() != null) {
                StateMovieList(
                    results = combineMovieGenre(response.body()!!.results, genres),
                    total_pages = response.body()!!.total_pages,
                    total_results = response.body()!!.total_results,
                    loading = false,
                    error = false
                )
            } else {
                StateMovieList(error = true)
            }
        } catch (e: Exception) {
            StateMovieList(error = true)
        }
    }
}

// >>> Movie List Paging Repository
class MoveListPagingRepository @Inject constructor(
    private val movieListRepository: MovieListRepository
) {
    fun fetchMovieListPaging(
        movieParams: MovieListParams
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieListRepository,
                    movieParams
                )
            }
        ).flow
    }
}

// >>> Movie Detail Repository
class MovieDetailRepository @Inject constructor(
    private val api: ApiInterface
) {
    suspend fun fetchMovieDetail(movieId: String) : StateMovieDetail {
        StateMovieDetail(loading = true)
        return try {
            val response = api.getDetailMovieService(movieId)
            if (response.isSuccessful && response.body() != null) {
                StateMovieDetail(
                    results = response.body(),
                    loading = false,
                    error = false
                )
            } else {
               StateMovieDetail(error = true)
            }
        } catch (e: Exception) {
            StateMovieDetail(error = true)
        }
    }
}
