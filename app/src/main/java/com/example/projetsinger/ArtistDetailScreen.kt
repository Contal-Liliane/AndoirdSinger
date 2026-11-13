
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
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistDetailScreen(
    artistId: String,
    onBack: () -> Unit,
    viewModel: SpotifyViewModel = viewModel()
) {
    val artist by viewModel.artist.collectAsState()
    val albums by viewModel.artistAlbums.collectAsState()
    val erreur by viewModel.erreur.collectAsState()

    val paysage =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(artistId) {
        viewModel.loadArtist(artistId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("⭐ Détails de l'artiste")
                },
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
            val a = artist

            when {
                erreur -> ErrorScreen()

                a == null -> LoadingScreen()

                else -> {
                    val infos = listOfNotNull(
                        a.genres
                            ?.takeIf { it.isNotEmpty() }
                            ?.let { "Genres" to it.joinToString(", ") },

                        a.followers?.total
                            ?.let {
                                "Followers" to NumberFormat.getInstance().format(it)
                            },

                        a.popularity
                            ?.let { "Popularité" to "$it/100" }
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
                                    url = a.images?.firstOrNull()?.url,
                                    rond = true,
                                    taille = 160.dp
                                )

                                Text(
                                    text = a.name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center
                                )

                                SpotifyButton(a.external_urls?.spotify)
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

                                listeAlbums(albums)
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
                                CoverImage(
                                    url = a.images?.firstOrNull()?.url,
                                    rond = true
                                )
                            }

                            item {
                                Text(
                                    text = a.name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center
                                )
                            }

                            item {
                                InfosCard(infos)
                            }

                            listeAlbums(albums)

                            item {
                                SpotifyButton(a.external_urls?.spotify)
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun LazyListScope.listeAlbums(
    albums: List<Album>
) {
    if (albums.isEmpty()) return

    item {
        Text(
            text = "💿 Albums et singles",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }

    items(albums) { album ->
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "💿 ${album.name}",
                    style = MaterialTheme.typography.titleMedium
                )

                val sousTitre = listOfNotNull(
                    album.album_type
                        ?.replaceFirstChar { it.uppercase() },

                    album.release_date
                        ?.let { formatDate(it) }
                ).joinToString(" • ")

                if (sousTitre.isNotEmpty()) {
                    Text(
                        text = sousTitre,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
