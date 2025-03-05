package com.devs.filmzy.src.models.PersonDetail

data class StatePersonDetail(
    val results: PersonDetail? = null,
    val loading: Boolean = true,
    val error: Boolean = false
)