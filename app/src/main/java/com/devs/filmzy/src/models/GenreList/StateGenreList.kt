package com.devs.filmzy.src.models.GenreList

data class StateGenreList(
    val genres: List<Genre> = emptyList(),
    val loading: Boolean = true,
    val error: Boolean = false
)