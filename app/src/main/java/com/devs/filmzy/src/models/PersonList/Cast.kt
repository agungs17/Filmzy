package com.devs.filmzy.src.models.PersonList

data class Cast(
    val adult: Boolean,
    val cast_id: Int,
    val character: String,
    override val credit_id: String,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    override val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double,
    override val profile_path: String? = null
) : Person {
    override val job: String get() = character
}
