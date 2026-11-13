package com.example.projetsinger

data class Album(
    val id: String? = null,
    val name: String,
    val artists: List<Artist> = emptyList(),
    val images: List<SpotifyImage>? = null,
    val release_date: String? = null,
    val total_tracks: Int? = null,
    val album_type: String? = null,
    val external_urls: ExternalUrls? = null
)

data class AlbumsResponse(
    val items: List<Album>
)