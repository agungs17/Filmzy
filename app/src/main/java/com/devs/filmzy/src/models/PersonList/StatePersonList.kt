package com.devs.filmzy.src.models.PersonList

data class StatePersonList(
    val id: Int = 1,
    val cast: List<Cast> = emptyList(),
    val crew: List<Crew> = emptyList(),
    val loading: Boolean = true,
    val error: Boolean = false
)
