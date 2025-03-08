package com.devs.filmzy.src

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.devs.filmzy.src.navigations.Navigation
import com.devs.filmzy.src.theme.FilmzyTheme
import com.devs.filmzy.src.utils.NavigationManager
import com.devs.filmzy.src.viewModels.movie.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigation = rememberNavController()
            val genreViewModel: GenreViewModel = hiltViewModel()

            LaunchedEffect(Unit) {
                genreViewModel.fetch() // fetch genre
                navigationManager.navigation = navigation
            }

            FilmzyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
                ) {
                    Navigation(navigation)
                }
            }
        }
    }
}