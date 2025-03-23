package com.devs.filmzy.src.services.person

import com.devs.filmzy.src.models.PersonList.StatePersonList
import com.devs.filmzy.src.services.ConfigApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonInterface {
    @GET("movie/{movieId}/credits?")
    suspend fun getPersonListService (
        @Path("movieId") movieId:String,
        @Query("language") language: String = ConfigApi.LANGUAGE_ID
    ): Response<StatePersonList>

//    @GET("credit/{creditId}?")
//    suspend fun getPersonDetailService (
//        @Path("credit_id") creditId:String,
//        @Query("language") language: String = ConfigApi.LANGUAGE_ID
//    ): Response<PersonDetail>
}