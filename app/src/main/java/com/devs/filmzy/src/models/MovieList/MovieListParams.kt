package com.devs.filmzy.src.models.MovieList

data class MovieListParams (
    val page: Int = 1,
    val sortBy: String = "",
    val withReleaseType: String = "",
    val maxDate: String = "",
    val minDate: String = "",
    val withGenres: String = ""
)