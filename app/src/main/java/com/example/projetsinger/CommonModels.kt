package com.example.projetsinger

data class SpotifyImage(
    val url: String,
    val height: Int? = null,
    val width: Int? = null
)

data class Followers(
    val total: Int? = null
)

data class ExternalUrls(
    val spotify: String? = null
)