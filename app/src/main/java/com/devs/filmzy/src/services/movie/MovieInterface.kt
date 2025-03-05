package com.devs.filmzy.src.services.movie

import com.devs.filmzy.src.models.GenreList.StateGenreList
import com.devs.filmzy.src.models.MovieDetail.MovieDetail
import com.devs.filmzy.src.models.MovieList.StateMovieList
import com.devs.filmzy.src.services.ConfigApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInterface {
    // genre
    @GET("genre/movie/list?")
    suspend fun getGenreService (
        @Query("language") language: String = ConfigApi.LANGUAGE_ID
    ): Response<StateGenreList>

    // movie now playing list
    @GET("movie/now_playing?")
    suspend fun getNowPlayingMovieService (
        @Query("page") page:Int,
        @Query("language") language: String = ConfigApi.LANGUAGE_ID,
        @Query("region") region: String = ""
    ): Response<StateMovieList>

    // movie discovery list
    @GET("discover/movie?")
    suspend fun getDiscoveryMovieService (
        @Query("page") page:Int,
        @Query("include_adult") adult:Boolean = ConfigApi.INCLUDE_ADULT,
        @Query("include_video") video:Boolean = ConfigApi.INCLUDE_VIDEO,
        @Query("language") language: String = ConfigApi.LANGUAGE_ID,
        @Query("sort_by") sortBy: String = "vote_count.desc"
    ): Response<StateMovieList>

    @GET("movie/{movieId}?")
    suspend fun getDetailMovieService (
        @Path("movieId") movieId:Int,
        @Query("language") language: String = ConfigApi.LANGUAGE_ID
    ): Response<MovieDetail>
}