package com.example.projetsinger

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingScreen(
    navController: NavController
) {

    val viewModel = viewModel<SpotifyViewModel>()

    val results by viewModel.results.collectAsState()

    val erreur by viewModel.erreur.collectAsState()

    val chargement by viewModel.chargement.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.search("year:2026")
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("🔥 Top du moment")
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
                        .padding(paddingValues),

                    verticalArrangement =
                        Arrangement.spacedBy(8.dp)

                ) {

                    items(
                        results.shuffled().take(5)
                    ) { track ->

                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 4.dp
                                )
                        ) {

                            Text(
                                text = "🎵 ${track.name}",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }


    }
}