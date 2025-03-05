package com.devs.filmzy.src.viewModels.movie

import com.devs.filmzy.src.services.ApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepository @Inject constructor(
    private val api: ApiInterface
) {

}