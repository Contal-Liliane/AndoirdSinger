
package com.example.projetsinger

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data object ForgottenMusic : NavKey

@Serializable
data object OldArtists : NavKey

@Serializable
data object Trending : NavKey

@Serializable
data class TrackDetail(val id: String) : NavKey

@Serializable
data class ArtistDetail(val id: String) : NavKey
