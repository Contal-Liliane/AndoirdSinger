package com.example.projetsinger

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OldArtistsScreen(
    onBack: () -> Unit,
    onArtistClick: (String) -> Unit
) {

    val viewModel: SpotifyViewModel = viewModel()

    val artistes by viewModel.artistes.collectAsState()
    val erreur by viewModel.erreur.collectAsState()
    val chargement by viewModel.chargement.collectAsState()

    val paysage =
        LocalConfiguration.current.orientation ==
                Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(Unit) {
        viewModel.loadOldArtists()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("⭐ Artistes du passé")
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

                artistes.isEmpty() -> EmptyScreen()

                else -> {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(
                            if (paysage) 2 else 1
                        ),
                        modifier = Modifier.fillMaxSize()
                    ) {

                        items(artistes) { artiste ->

                            Card(
                                onClick = {
                                    artiste.id?.let {
                                        onArtistClick(it)
                                    }
                                },
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFFFF3E0)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    )
                            ) {

                                Text(
                                    text = "⭐ ${artiste.name}",
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}