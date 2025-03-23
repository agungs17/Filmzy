package com.devs.filmzy.src.viewModels.movie

import com.devs.filmzy.src.models.PersonList.StatePersonList
import com.devs.filmzy.src.services.ApiInterface
import javax.inject.Inject

class PersonListRepository @Inject constructor(
    private val api : ApiInterface
) {
    suspend fun fetchPersonList(movieId: String) : StatePersonList {
        StatePersonList(loading = true)
        return try {
            val response = api.getPersonListService(movieId)
            if (response.isSuccessful && response.body() != null) {
                StatePersonList(
                    id = response.body()!!.id,
                    cast = response.body()!!.cast,
                    crew = response.body()!!.crew,
                    loading = false,
                    error = false
                )
            } else {
                StatePersonList(error = true)
            }
        } catch (e: Exception) {
            StatePersonList(error = true)
        }
    }
}