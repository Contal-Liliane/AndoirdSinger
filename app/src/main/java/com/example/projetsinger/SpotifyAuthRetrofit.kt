package com.example.projetsinger

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpotifyAuthRetrofit {

    private const val BASE_URL = "https://accounts.spotify.com/"

    val api: SpotifyAuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyAuthApi::class.java)
    }
}