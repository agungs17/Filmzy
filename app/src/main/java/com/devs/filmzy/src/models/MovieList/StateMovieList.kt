package com.devs.filmzy.src.models.MovieList

data class StateMovieList(
    val results: List<Movie> = emptyList(),
    val page: Int = 1,
    val total_pages: Int = 0,
    val total_results: Int = 0,
    val loading: Boolean = true,
    val error: Boolean = false
)