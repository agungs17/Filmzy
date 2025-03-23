package com.devs.filmzy.src.viewModels.global

import com.devs.filmzy.src.models.GenreList.StateGenreList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

// kumpulan state global
data class GlobalState(
    val genreState: StateGenreList = StateGenreList(),
)

@Singleton
class GlobalRepository @Inject constructor() {
    private val _globalState = MutableStateFlow(GlobalState())
    val globalState = _globalState.asStateFlow()

    fun updateGlobalState(update: GlobalState.() -> GlobalState) {
        _globalState.value = _globalState.value.update()
    }
}
