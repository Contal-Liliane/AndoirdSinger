package com.example.projetsinger

data class Artist(
    val id: String? = null,
    val name: String,
    val genres: List<String>? = null,
    val images: List<SpotifyImage>? = null,
    val followers: Followers? = null,
    val popularity: Int? = null,
    val external_urls: ExternalUrls? = null
)

data class Track(
    val id: String? = null,
    val name: String,
    val artists: List<Artist> = emptyList(),
    val album: Album? = null,
    val duration_ms: Long? = null,
    val explicit: Boolean? = null,
    val popularity: Int? = null,
    val track_number: Int? = null,
    val external_urls: ExternalUrls? = null
)