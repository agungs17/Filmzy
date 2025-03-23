package com.devs.filmzy.src.models.PersonList

data class Crew(
    val adult: Boolean,
    override val credit_id: String,
    val department: String,
    val gender: Int,
    val id: Int,
    override val job: String,
    val known_for_department: String,
    override val name: String,
    val original_name: String,
    val popularity: Double,
    override val profile_path: String? = null
) : Person