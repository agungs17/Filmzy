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

    // movie list
    @GET("discover/movie?")
    suspend fun getMovieListService (
        @Query("page") page:Int,
        @Query("sort_by") sortBy: String = "", // default popular.desc
        @Query("with_release_type") withReleaseType: String = "",
        @Query("release_date.gte") minDate: String = "",
        @Query("release_date.lte") maxDate: String = "",
        @Query("with_genres") withGenres: String = "",

        @Query("include_adult") adult:Boolean = ConfigApi.INCLUDE_ADULT,
        @Query("include_video") video:Boolean = ConfigApi.INCLUDE_VIDEO,
        @Query("language") language: String = ConfigApi.LANGUAGE_ID,
    ): Response<StateMovieList>

    @GET("movie/{movieId}?")
    suspend fun getDetailMovieService (
        @Path("movieId") movieId:String,

        @Query("language") language: String = ConfigApi.LANGUAGE_ID
    ): Response<MovieDetail>
}