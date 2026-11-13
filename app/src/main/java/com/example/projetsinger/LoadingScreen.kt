package com.example.projetsinger

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen() {

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // La licorne rétrécit si la hauteur disponible est faible (paysage)
        val taille = minOf(250.dp, maxHeight * 0.55f)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.licorne
                ),
                contentDescription = "Chargement",
                modifier = Modifier.size(taille),
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Je charge !",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}