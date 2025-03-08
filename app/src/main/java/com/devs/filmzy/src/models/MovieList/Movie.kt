package com.devs.filmzy.src.models.MovieList

import com.devs.filmzy.src.models.GenreList.Genre

data class Movie(
    val adult: Boolean = false,
    val backdrop_path: String? = null,
    val genre_ids: List<Int> = emptyList(),
    val genre: List<Genre> = emptyList(),
    val id: Int = 0,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double = 0.0,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Long = 0
)