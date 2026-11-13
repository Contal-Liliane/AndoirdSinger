package com.example.projetsinger

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingScreen(
    onBack: () -> Unit,
    onArtistClick: (String) -> Unit
) {

    val viewModel: SpotifyViewModel = viewModel()

    val albums by viewModel.albums.collectAsState()
    val erreur by viewModel.erreur.collectAsState()
    val chargement by viewModel.chargement.collectAsState()

    val paysage =
        LocalConfiguration.current.orientation ==
                Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(Unit) {
        viewModel.loadNewReleases()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("🔥 Dernières sorties")
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

            when {

                chargement -> LoadingScreen()

                erreur -> ErrorScreen()

                albums.isEmpty() -> EmptyScreen()

                else -> {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(
                            if (paysage) 2 else 1
                        ),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        items(albums) { album ->

                            Card(
                                onClick = {
                                    album.artists
                                        .firstOrNull()
                                        ?.id
                                        ?.let { id ->
                                            onArtistClick(id)
                                        }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 4.dp
                                    )
                            ) {

                                androidx.compose.foundation.layout.Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {

                                    Text(
                                        text = "💿 ${album.name}",
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = album.artists.joinToString {
                                            it.name
                                        },
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    album.release_date?.let {
                                        Text(
                                            text = "📅 ${formatDate(it)}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}