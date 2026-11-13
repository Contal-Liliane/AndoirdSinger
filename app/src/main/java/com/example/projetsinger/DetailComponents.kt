package com.example.projetsinger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// 3:07
fun formatDuree(ms: Long): String {
    val total = ms / 1000
    return "%d:%02d".format(total / 60, total % 60)
}

// 2010-05-12 -> 12/05/2010
fun formatDate(date: String): String {
    val p = date.split("-")
    return when (p.size) {
        3 -> "${p[2]}/${p[1]}/${p[0]}"
        2 -> "${p[1]}/${p[0]}"
        else -> date
    }
}

@Composable
fun CoverImage(
    url: String?,
    rond: Boolean = false,
    taille: Dp = 220.dp
) {

    val forme = if (rond) CircleShape else RoundedCornerShape(16.dp)

    if (url == null) {

        Box(
            modifier = Modifier
                .size(taille)
                .clip(forme)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text("🎵", fontSize = 64.sp)
        }

    } else {

        AsyncImage(
            model = url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(taille)
                .clip(forme)
        )
    }
}

@Composable
fun InfoRow(label: String, valeur: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.4f)
        )

        Text(
            text = valeur,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.6f)
        )
    }
}

@Composable
fun InfosCard(infos: List<Pair<String, String>>) {

    if (infos.isEmpty()) return

    Card(modifier = Modifier.fillMaxWidth()) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            infos.forEach { (label, valeur) ->
                InfoRow(label, valeur)
            }
        }
    }
}

@Composable
fun SpotifyButton(url: String?) {

    if (url == null) return

    val uriHandler = LocalUriHandler.current

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { uriHandler.openUri(url) }
    ) {
        Text("▶ Ouvrir dans Spotify")
    }
}