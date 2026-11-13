package com.example.projetsinger

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailScreen(
    trackId: String,
    onBack: () -> Unit,
    onArtistClick: (String) -> Unit
) {

    val viewModel = viewModel<SpotifyViewModel>()

    val track by viewModel.track.collectAsState()
    val erreur by viewModel.erreur.collectAsState()

    val paysage =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(trackId) {
        viewModel.loadTrack(trackId)
    }

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text("🎵 Détails de la musique") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("⬅ Retour")
                    }
                }
            )
        }

    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            val t = track

            when {

                erreur -> ErrorScreen()

                t == null -> LoadingScreen()

                else -> {

                    val infos = listOfNotNull(
                        t.album?.name?.let { "Album" to it },
                        t.album?.release_date?.let { "Date de sortie" to formatDate(it) },
                        t.duration_ms?.let { "Durée" to formatDuree(it) },
                        t.track_number?.let { "Piste n°" to it.toString() },
                        t.album?.total_tracks?.let { "Pistes de l'album" to it.toString() },
                        t.explicit?.let { "Contenu explicite" to if (it) "Oui" else "Non" },
                        t.popularity?.let { "Popularité" to "$it/100" }
                    )

                    if (paysage) {

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            Column(
                                modifier = Modifier
                                    .weight(0.4f)
                                    .fillMaxHeight()
                                    .verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                CoverImage(
                                    url = t.album?.images?.firstOrNull()?.url,
                                    taille = 160.dp
                                )

                                Text(
                                    text = t.name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center
                                )

                                SpotifyButton(t.external_urls?.spotify)
                            }

                            LazyColumn(
                                modifier = Modifier
                                    .weight(0.6f)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                item {
                                    InfosCard(infos)
                                }

                                listeArtistes(
                                    artists = t.artists,
                                    onArtistClick = onArtistClick
                                )
                            }
                        }

                    } else {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            item {
                                CoverImage(url = t.album?.images?.firstOrNull()?.url)
                            }

                            item {
                                Text(
                                    text = t.name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center
                                )
                            }

                            item {
                                InfosCard(infos)
                            }

                            item {
                                SpotifyButton(t.external_urls?.spotify)
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun LazyListScope.listeArtistes(
    artists: List<Artist>,
    onArtistClick: (String) -> Unit
) {

    if (artists.isEmpty()) return

    item {
        Text(
            text = "Artistes",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }

    items(artists) { artiste ->

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = artiste.id != null,
            onClick = {
                artiste.id?.let {
                    onArtistClick(it)
                }
            }
        ) {
            Text("⭐ ${artiste.name}")
        }
    }
}