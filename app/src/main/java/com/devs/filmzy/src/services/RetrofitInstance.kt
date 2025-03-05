package com.devs.filmzy.src.services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val client = OkHttpClient.Builder()
        .addInterceptor(Interceptor { c ->
            val request: Request = c.request().newBuilder()
                .addHeader("Authorization", "Bearer ${ConfigApi.API_KEY}")
                .addHeader("accept", "application/json")
                .build()
            c.proceed(request)
        })
        .build()

    val api: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(ConfigApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}