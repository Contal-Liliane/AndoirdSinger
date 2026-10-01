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

    Column(
        modifier = Modifier.fillMaxSize(),

        verticalArrangement =
            Arrangement.Center,

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.licorne
            ),
            contentDescription = "Chargement",
            modifier = Modifier.size(250.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Je charge !",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}