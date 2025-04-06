package com.devs.filmzy.src.viewModels.person

// import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devs.filmzy.src.models.PersonList.StatePersonList
import com.devs.filmzy.src.viewModels.movie.PersonListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val repository: PersonListRepository,
//    savedStateHandle: SavedStateHandle
) : ViewModel() {

//    private val movieId: String? = savedStateHandle["movieId"] // didapat dari NavHost (Navigation)
    private val _state = MutableStateFlow(StatePersonList())
    val state = _state.asStateFlow()

//    init {
//        movieId?.let { fetch(it) }
//    }

     fun fetch(movieId: String) {
        viewModelScope.launch {
            _state.value = repository.fetchPersonList(movieId)
        }
    }
}