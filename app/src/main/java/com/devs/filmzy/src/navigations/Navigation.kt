package com.devs.filmzy.src.navigations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devs.filmzy.src.utils.NavigationManager
import com.devs.filmzy.src.viewModels.misc.NavigateViewModel
import com.devs.filmzy.src.views.DetailMovieView
import com.devs.filmzy.src.views.HomeView

@Composable
fun Navigation (
    navigation: NavHostController,
    navigationManager: NavigationManager = hiltViewModel<NavigateViewModel>().navigation,
) {
    // Handle Applink/Deeplink
    LaunchedEffect(Unit) {
        linkingHandle(navigationManager)
    }

    NavHost(navController = navigation, startDestination = "HomeView") {

        composable("HomeView") {
            HomeView()
        }

        composable("DetailMovieView/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
            DetailMovieView(movieId)
        }
    }
}

// can use to Applink/Deeplink?
fun linkingHandle (
    navigation : NavigationManager,
    linking: String? = null
) {
    if(linking != null) navigation.navigate(linking)
}

