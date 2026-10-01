package com.example.projetsinger

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController
) {

    val viewModel = viewModel<SpotifyViewModel>()

    var recherche by remember {
        mutableStateOf("")
    }

    val results by viewModel.results.collectAsState()

    val erreur by viewModel.erreur.collectAsState()

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text("🔍 Recherche Spotify")
                }
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Button(
                onClick = {
                    navController.popBackStack()
                }
            ) {

                Text("⬅ Retour")
            }

            if (erreur) {

                Text(
                    text = "❌ Connexion à Spotify échouée",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            OutlinedTextField(
                value = recherche,
                onValueChange = {
                    recherche = it
                },
                label = {
                    Text("Recherche")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.search(recherche)
                }
            ) {

                Text("Rechercher")
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            LazyColumn {

                items(results) { track ->

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE8F5E9)
                        ),

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
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