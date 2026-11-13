
package com.example.projetsinger

import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.isSuccess
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.IOException

class SpotifyRepository {

    private val client = SpotifyHttpClient.client

    private val clientId = BuildConfig.SPOTIFY_CLIENT_ID
    private val clientSecret = BuildConfig.SPOTIFY_CLIENT_SECRET

    private var cachedToken: String? = null
    private var tokenExpiration = 0L

    private val mutex = Mutex()

    private suspend fun getAccessToken(
        forcer: Boolean = false
    ): String = mutex.withLock {

        val existing = cachedToken

        if (!forcer &&
            existing != null &&
            System.currentTimeMillis() < tokenExpiration
        ) {
            return@withLock existing
        }

        Log.d("SPOTIFY", "Demande d'un nouveau token")

        val encoded = Base64.encodeToString(
            "$clientId:$clientSecret".toByteArray(),
            Base64.NO_WRAP
        )

        val response = client.submitForm(
            url = "${SpotifyHttpClient.AUTH_URL}api/token",
            formParameters = Parameters.build {
                append("grant_type", "client_credentials")
            }
        ) {
            header("Authorization", "Basic $encoded")
        }

        if (!response.status.isSuccess()) {
            throw IOException(
                "Token refusé (code ${response.status.value}) : " +
                        response.bodyAsText()
            )
        }

        val body: TokenResponse = response.body()

        cachedToken = body.access_token

        tokenExpiration = System.currentTimeMillis() +
                (body.expires_in - 60).coerceAtLeast(0) * 1000L

        body.access_token
    }

    suspend fun verifierConnexion() {
        getAccessToken(forcer = true)
    }

    private suspend inline fun <reified T : Any> apiGet(
        path: String,
        token: String,
        crossinline parameters: io.ktor.client.request.HttpRequestBuilder.() -> Unit = {}
    ): T {

        val response = client.get(
            "${SpotifyHttpClient.API_URL}$path"
        ) {
            header("Authorization", "Bearer $token")
            parameters()
        }

        Log.d("SPOTIFY", "Code $path : ${response.status.value}")

        if (!response.status.isSuccess()) {
            throw IOException(
                "Requête $path échouée " +
                        "(code ${response.status.value}) : " +
                        response.bodyAsText()
            )
        }

        return response.body()
    }

    suspend fun searchTrack(
        query: String,
        offset: Int = 0
    ): List<Track> {

        Log.d("SPOTIFY", "Recherche : $query (offset $offset)")

        val token = getAccessToken()

        val body: SearchResponse = apiGet(
            path = "v1/search",
            token = token
        ) {
            parameter("q", query)
            parameter("type", "track")
            parameter("limit", 10)
            parameter("offset", offset)
        }

        return body.tracks?.items ?: emptyList()
    }

    suspend fun searchAll(
        query: String
    ): Pair<List<Track>, List<Artist>> {

        Log.d("SPOTIFY", "Recherche globale : $query")

        val token = getAccessToken()

        val body: SearchResponse = apiGet(
            path = "v1/search",
            token = token
        ) {
            parameter("q", query)
            parameter("type", "track,artist")
            parameter("limit", 5)
        }

        return Pair(
            body.tracks?.items ?: emptyList(),
            body.artists?.items ?: emptyList()
        )
    }

    suspend fun getNewReleases(): List<Album> {

        val token = getAccessToken()

        val body: SearchResponse = apiGet(
            path = "v1/search",
            token = token
        ) {
            parameter("q", "tag:new")
            parameter("type", "album")
            parameter("limit", 10)
        }

        return body.albums?.items ?: emptyList()
    }

    suspend fun getTrack(id: String): Track {

        val token = getAccessToken()

        return apiGet(
            path = "v1/tracks/$id",
            token = token
        )
    }

    suspend fun getArtist(id: String): Artist {

        val token = getAccessToken()

        return apiGet(
            path = "v1/artists/$id",
            token = token
        )
    }

    suspend fun getArtistAlbums(id: String): List<Album> {

        val token = getAccessToken()

        val body: AlbumsResponse = apiGet(
            path = "v1/artists/$id/albums",
            token = token
        ) {
            parameter("include_groups", "album,single")
            parameter("limit", 10)
        }

        return body.items
    }
}
