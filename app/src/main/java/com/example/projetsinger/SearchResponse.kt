package com.example.projetsinger

data class SearchResponse(
    val tracks: TracksResponse?,
    val albums: AlbumsResponse?,
    val artists: ArtistsResponse?
)

data class ArtistsResponse(
    val items: List<Artist>
)