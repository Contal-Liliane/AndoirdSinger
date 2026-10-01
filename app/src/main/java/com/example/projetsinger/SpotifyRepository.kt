package com.example.projetsinger

import android.util.Base64
import android.util.Log

class SpotifyRepository {

    private val clientId = "6884d577223945fd89e6eb4819f4e2b6"

    private val clientSecret = "c4410ed800ac48478919239511f08e0d"

    suspend fun getAccessToken(): String? {

        Log.d("SPOTIFY", "Demande token")

        val credentials =
            "$clientId:$clientSecret"

        val encoded =
            Base64.encodeToString(
                credentials.toByteArray(),
                Base64.NO_WRAP
            )

        val response =
            SpotifyAuthRetrofit.api.getToken(
                authorization = "Basic $encoded",
                grantType = "client_credentials"
            )

        Log.d(
            "SPOTIFY",
            "Code token : ${response.code()}"
        )

        if (response.isSuccessful) {

            val token =
                response.body()?.access_token

            Log.d(
                "SPOTIFY",
                "Token obtenu : $token"
            )

            return token
        }

        return null
    }

    suspend fun searchTrack(
        query: String
    ): List<Track> {

        Log.d(
            "SPOTIFY",
            "Recherche : $query"
        )

        val token =
            getAccessToken()

        if (token == null) {

            Log.d(
                "SPOTIFY",
                "Token null"
            )

            return emptyList()
        }

        try {

            val response =
                RetrofitInstance.api.searchTrack(
                    token = "Bearer $token",
                    query = query
                )

            Log.d(
                "SPOTIFY",
                "Code recherche : ${response.code()}"
            )

            Log.d(
                "SPOTIFY",
                "Body : ${response.body()}"
            )

            if (response.isSuccessful) {

                return response.body()
                    ?.tracks
                    ?.items
                    ?: emptyList()
            }

        } catch (e: Exception) {

            Log.d(
                "SPOTIFY",
                "Erreur recherche : ${e.message}"
            )
        }

        return emptyList()
    }
}