package com.example.projetsinger

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("v1/search")
    suspend fun searchTrack(
        @Header("Authorization") token: String,
        @Query("q") query: String,
        @Query("type") type: String = "track",
        @Query("limit") limit: Int = 10
    ): Response<SearchResponse>
}