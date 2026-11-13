package com.example.projetsinger

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class SpotifyViewModel : ViewModel() {

    private val repository = SpotifyRepository()

    val results = MutableStateFlow<List<Track>>(emptyList())
    val albums = MutableStateFlow<List<Album>>(emptyList())
    val artistes = MutableStateFlow<List<Artist>>(emptyList())

    // Résultats de la barre de recherche (accueil)
    val searchTracks = MutableStateFlow<List<Track>>(emptyList())
    val searchArtists = MutableStateFlow<List<Artist>>(emptyList())

    // Écrans de détails
    val track = MutableStateFlow<Track?>(null)
    val artist = MutableStateFlow<Artist?>(null)
    val artistAlbums = MutableStateFlow<List<Album>>(emptyList())

    val erreur = MutableStateFlow(false)
    val chargement = MutableStateFlow(false)

    private fun lancer(action: suspend () -> Unit) {

        viewModelScope.launch {

            chargement.value = true
            erreur.value = false

            try {
                action()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("SPOTIFY", "Erreur : ${e.message}")
                erreur.value = true
            } finally {
                chargement.value = false
            }
        }
    }

    // Recherche titres + artistes (barre de recherche de l'accueil)
    fun searchAll(query: String) {

        if (query.isBlank()) return

        lancer {
            val (tracks, artists) = repository.searchAll(query.trim())
            searchTracks.value = tracks
            searchArtists.value = artists
        }
    }

    // Efface la recherche (bouton ✖)
    fun clearSearch() {
        searchTracks.value = emptyList()
        searchArtists.value = emptyList()
        erreur.value = false
    }

    // Musiques oubliées
    fun loadForgottenMusic() {

        if (results.value.isNotEmpty()) return

        lancer {
            val offset = Random.nextInt(0, 90)
            results.value = repository
                .searchTrack("year:2010", offset)
                .shuffled()
        }
    }

    // Artistes du passé
    fun loadOldArtists() {

        if (artistes.value.isNotEmpty()) return

        lancer {
            val offset = Random.nextInt(0, 90)
            artistes.value = repository
                .searchTrack("year:2005", offset)
                .mapNotNull { it.artists.firstOrNull() }
                .filter { it.id != null }
                .distinctBy { it.id }
                .shuffled()
        }
    }

    // Top du moment = dernières sorties
    fun loadNewReleases() {

        if (albums.value.isNotEmpty()) return

        lancer {
            albums.value = repository.getNewReleases()
        }
    }

    // Détails d'une musique
    fun loadTrack(id: String) {

        if (track.value?.id == id) return

        lancer {
            track.value = repository.getTrack(id)
        }
    }

    // Détails d'un artiste + ses albums
    fun loadArtist(id: String) {

        if (artist.value?.id == id) return

        lancer {
            artist.value = repository.getArtist(id)

            // Si les albums échouent, on affiche quand même l'artiste
            artistAlbums.value = try {
                repository.getArtistAlbums(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("SPOTIFY", "Albums artiste : ${e.message}")
                emptyList()
            }
        }
    }
}