
package com.example.projetsinger

import com.google.gson.GsonBuilder
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson

object SpotifyHttpClient {

    val client = HttpClient(Android) {

        install(ContentNegotiation) {
            gson {
                setDateFormat("yyyy-MM-dd")
            }
        }

        expectSuccess = false
    }

    const val API_URL = "https://api.spotify.com/"
    const val AUTH_URL = "https://accounts.spotify.com/"
}
