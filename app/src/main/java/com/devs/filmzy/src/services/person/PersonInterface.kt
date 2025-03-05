package com.devs.filmzy.src.services.person

import com.devs.filmzy.src.models.PersonDetail.PersonDetail
import com.devs.filmzy.src.models.PersonList.StatePersonList
import com.devs.filmzy.src.services.ConfigApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonInterface {
    @GET("movie/{movieId}/credits?")
    suspend fun getPersonService (
        @Path("movieId") movieId:Int,
        @Query("language") language: String = ConfigApi.LANGUAGE_ID
    ): Response<StatePersonList>

    @GET("person/{personId}?")
    suspend fun getPersonDetailService (
        @Path("movieId") movieId:Int,
        @Query("language") language: String = ConfigApi.LANGUAGE_ID
    ): Response<PersonDetail>
}