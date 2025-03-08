package com.devs.filmzy.src.models.MovieDetail

import com.devs.filmzy.src.models.GenreList.Genre

data class MovieDetail(
    val adult: Boolean = false,
    val backdrop_path: String? = null,
    val belongs_to_collection: Any? = null,
    val budget: Long = 0,
    val genres: List<Genre> = emptyList(),
    val homepage: String? = null,
    val id: Int = 0,
    val imdb_id: String? = null,
    val origin_country: List<String> = emptyList(),
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double = 0.0,
    val poster_path: String? = null,
    val production_companies: List<ProductionCompany> = emptyList(),
    val production_countries: List<ProductionCountry> = emptyList(),
    val release_date: String? = null,
    val revenue: Long = 0,
    val runtime: Int = 0,
    val spoken_languages: List<SpokenLanguage> = emptyList(),
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Long = 0,
)
