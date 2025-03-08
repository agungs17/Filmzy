package com.devs.filmzy.src.paging.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.devs.filmzy.src.models.MovieList.Movie
import com.devs.filmzy.src.repositories.MovieListRepository
import com.devs.filmzy.src.utils.anchorPositionClosestPage

class MoviePagingSource(
    private val repository: MovieListRepository,
    private val sortBy: String,
    private val withReleaseType: String = "",
    private val maxDate: String = "",
    private val minDate: String = "",
    private val withGenres: String = ""
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = repository.fetchMovieList(
                page = page,
                sortBy = sortBy,
                withReleaseType = withReleaseType,
                maxDate = maxDate,
                minDate =  minDate,
                withGenres = withGenres
            )
            LoadResult.Page(
                data = response.results, // resultnya formatnya StateMovieList
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.total_pages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return anchorPositionClosestPage(state)
    }
}
