package com.devs.filmzy.src.models.MovieDetail

data class StateMovieDetail (
    val results: MovieDetail? = null,
    val loading: Boolean = true,
    val error: Boolean = false
)