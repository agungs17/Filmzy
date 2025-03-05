package com.devs.filmzy.src.viewModels.misc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devs.filmzy.src.utils.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigateViewModel @Inject constructor(
    val navigation: NavigationManager
) : ViewModel()
