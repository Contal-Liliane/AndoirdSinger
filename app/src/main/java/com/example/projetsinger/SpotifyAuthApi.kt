package com.example.projetsinger

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface SpotifyAuthApi {

    @Headers(
        "Content-Type: application/x-www-form-urlencoded"
    )
    @FormUrlEncoded
    @POST("api/token")
    suspend fun getToken(
        @Header("Authorization") authorization: String,
        @Field("grant_type") grantType: String
    ): Response<TokenResponse>
}