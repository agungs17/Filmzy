package com.devs.filmzy.src.utils

import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.paging.PagingState
import com.devs.filmzy.src.models.GenreList.Genre
import com.devs.filmzy.src.models.MovieList.Movie
import com.devs.filmzy.src.models.misc.StyleConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() {
    var navigation: NavController? = null

    fun navigate(route: String) {
        navigation?.navigate(route)
    }

    fun canGoBack(): Boolean {
        return navigation?.previousBackStackEntry != null
    }

    private var isNavigatingBack = false
    fun goBack() {
        if (canGoBack() && !isNavigatingBack) {
            isNavigatingBack = true
            navigation?.popBackStack()

            // mencegah double click saat back
            CoroutineScope(Dispatchers.Main).launch {
                delay(500)
                isNavigatingBack = false
            }
        }
    }
}

fun styleConfig (type : String, bgColor: Color, textColor: Color) : StyleConfig {
    return when (type) {
        "Solid" -> {
            StyleConfig(
                bgColor,
                bgColor,
                textColor
            )
        }
        "Outline" -> {
            StyleConfig(
                bgColor,
                Color.Transparent,
                bgColor
            )
        }
        else -> {
//          Disabled
            StyleConfig(
                Color.LightGray,
                Color.LightGray,
                Color.Gray
            )
        }
    }
}

fun roundFloat (float: Float, format: String = "%.1f"): String {
    return String.format(Locale.US ,format, float)
}

fun combineMovieGenre(movies: List<Movie>, genres: List<Genre>): List<Movie> {
    return movies.map { movie ->
        val mappedGenres = movie.genre_ids.mapNotNull { genreId ->
            genres.find { it.id == genreId }
        }
        movie.copy(genre = mappedGenres)
    }
}

fun getDate(date: LocalDate = LocalDate.now(ZoneId.systemDefault()), format: String = "yyyy-MM-dd"): String {
    val formatter = DateTimeFormatter.ofPattern(format)
    return date.format(formatter)
}

// T bisa tipe data apa saja
fun <T : Any> anchorPositionClosestPage(state: PagingState<Int, T>): Int? {
    return state.anchorPosition?.let { anchor ->
        val closestPage = state.closestPageToPosition(anchor)
        closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
    }
}


