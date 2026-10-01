package com.example.projetsinger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SpotifyViewModel : ViewModel() {

    private val repository =
        SpotifyRepository()

    val results =
        MutableStateFlow<List<Track>>(
            emptyList()
        )

    val erreur =
        MutableStateFlow(false)

    val chargement =
        MutableStateFlow(false)

    fun search(
        query: String
    ) {

        viewModelScope.launch {

            chargement.value = true

            erreur.value = false

            try {

                val tracks =
                    repository.searchTrack(query)

                results.value = tracks

                erreur.value =
                    tracks.isEmpty()

            } catch (e: Exception) {

                erreur.value = true
            }

            chargement.value = false
        }
    }
}