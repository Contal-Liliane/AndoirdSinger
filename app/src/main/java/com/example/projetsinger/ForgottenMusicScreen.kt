package com.example.projetsinger

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgottenMusicScreen(
    navController: NavController
) {

    val viewModel = viewModel<SpotifyViewModel>()

    val results by viewModel.results.collectAsState()

    val erreur by viewModel.erreur.collectAsState()

    val chargement by
    viewModel.chargement.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.search("year:2010")
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("🎵 Musiques oubliées")
                },

                navigationIcon = {

                    TextButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text("⬅")
                    }
                }
            )
        }

    ) { paddingValues ->

        when {

            chargement -> {

                LoadingScreen()
            }

            erreur -> {

                ErrorScreen()
            }

            else -> {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {

                    items(
                        results.shuffled().take(10)
                    ) { track ->

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFEDE7F6)
                            ),

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 8.dp
                                )
                        ) {

                            Text(
                                text = "🎵 ${track.name}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }


    }
}