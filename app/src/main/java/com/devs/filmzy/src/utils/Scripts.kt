package com.devs.filmzy.src.utils

import androidx.navigation.NavController
import com.devs.filmzy.src.models.GenreList.Genre
import com.devs.filmzy.src.models.MovieList.Movie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() {
    var navigation: NavController? = null

    fun navigate(route: String) {
        navigation?.navigate(route)
    }

    fun goBack() {
        if (navigation?.previousBackStackEntry != null) navigation?.popBackStack()
    }
}

fun roundFloat (float: Float, format: String = "%.1f"): String {
    return String.format(format, float)
}

fun combineMovieGenre(movies: List<Movie>, genres: List<Genre>): List<Movie> {
    return movies.map { movie ->
        val mappedGenres = movie.genre_ids.mapNotNull { genreId ->
            genres.find { it.id == genreId }
        }
        movie.copy(genre = mappedGenres)
    }
}
