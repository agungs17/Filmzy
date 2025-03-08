package com.devs.filmzy.src.utils

import androidx.compose.ui.unit.dp

object Constants {
    object SortByType {
//        const val POPULARITY_DESC = "popularity.desc"
//        const val POPULARITY_ASC = "popularity.asc"
//        const val RELEASE_DATE_DESC = "release_date.desc"
//        const val RELEASE_DATE_ASC = "release_date.asc"
//        const val REVENUE_DESC = "revenue.desc"
//        const val REVENUE_ASC = "revenue.asc"
//        const val PRIMARY_RELEASE_DATE_DESC = "primary_release_date.desc"
//        const val PRIMARY_RELEASE_DATE_ASC = "primary_release_date.asc"
//        const val VOTE_AVERAGE_DESC = "vote_average.desc"
//        const val VOTE_AVERAGE_ASC = "vote_average.asc"
        const val VOTE_COUNT_DESC = "vote_count.desc"
//        const val VOTE_COUNT_ASC = "vote_count.asc"
    }

    object ReleaseType {
//        const val PREMIERE = 1
        const val THEATRICAL = 2
        const val THEATRICAL_LIMITED = 3
//        const val DIGITAL = 4
//        const val PHYSICAL = 5
//        const val TV = 6
    }

    val placeholderListHome = 3
    val nowShowingListHome = 6
    val heightImageBackdropDetailMovie = 220.dp

    object ConfigMovieCardHorizontalComponentImage {
        val round = 8.dp
        val width = 100.dp
        val height = 135.dp
    }

    object ConfigMovieCardVerticalComponentImage {
        val round = 8.dp
        val width = 130.dp
        val height = 190.dp
    }
}