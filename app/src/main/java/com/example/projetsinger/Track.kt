package com.example.projetsinger

data class Artist(
    val name: String
)

data class Track(
    val name: String,
    val artists: List<Artist>
)