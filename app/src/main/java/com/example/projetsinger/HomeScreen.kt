package com.example.projetsinger

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("🎵 Projet Singer")
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),

            verticalArrangement = Arrangement.spacedBy(
                20.dp,
                Alignment.CenterVertically
            ),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8E44AD)
                ),

                onClick = {
                    navController.navigate(
                        Routes.ForgottenMusic.route
                    )
                }
            ) {

                Text("🎵 Musiques oubliées")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE67E22)
                ),

                onClick = {
                    navController.navigate(
                        Routes.OldArtists.route
                    )
                }
            ) {

                Text("⭐ Artistes du passé")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE74C3C)
                ),

                onClick = {
                    navController.navigate(
                        Routes.Trending.route
                    )
                }
            ) {

                Text("🔥 Top du moment")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1DB954)
                ),

                onClick = {
                    navController.navigate(
                        Routes.Search.route
                    )
                }
            ) {

                Text("🔍 Recherche Spotify")
            }
        }
    }
}