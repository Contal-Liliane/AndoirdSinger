
package com.example.projetsinger

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToForgottenMusic: () -> Unit,
    onNavigateToOldArtists: () -> Unit,
    onNavigateToTrending: () -> Unit,
    onNavigateToTrack: (String) -> Unit,
    onNavigateToArtist: (String) -> Unit
) {
    val viewModel: SpotifyViewModel = viewModel()

    var recherche by rememberSaveable {
        mutableStateOf("")
    }

    var rechercheLancee by rememberSaveable {
        mutableStateOf(false)
    }

    val searchTracks by viewModel.searchTracks.collectAsState()
    val searchArtists by viewModel.searchArtists.collectAsState()
    val erreur by viewModel.erreur.collectAsState()
    val chargement by viewModel.chargement.collectAsState()

    val paysage =
        LocalConfiguration.current.orientation ==
                Configuration.ORIENTATION_LANDSCAPE

    fun lancerRecherche() {
        if (recherche.isNotBlank() && !chargement) {
            rechercheLancee = true
            viewModel.searchAll(recherche)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("🎵 Sporticorne")
                }
            )
        }
    ) { padding ->

        if (paysage) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Column(
                    modifier = Modifier
                        .weight(0.35f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(
                            id = R.drawable.licornus
                        ),
                        contentDescription = "Licornus",
                        modifier = Modifier.size(180.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(0.65f)
                        .fillMaxHeight()
                ) {

                    ZoneRecherche(
                        recherche = recherche,
                        onChange = { recherche = it },
                        lancee = rechercheLancee,
                        chargement = chargement,
                        compact = true,
                        onSearch = { lancerRecherche() },
                        onClear = {
                            recherche = ""
                            rechercheLancee = false
                            viewModel.clearSearch()
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {

                        if (rechercheLancee) {

                            Resultats(
                                tracks = searchTracks,
                                artists = searchArtists,
                                erreur = erreur,
                                chargement = chargement,
                                onNavigateToTrack = onNavigateToTrack,
                                onNavigateToArtist = onNavigateToArtist
                            )

                        } else {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(
                                        rememberScrollState()
                                    ),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                BoutonsMenu(
                                    onNavigateToForgottenMusic =
                                        onNavigateToForgottenMusic,
                                    onNavigateToOldArtists =
                                        onNavigateToOldArtists,
                                    onNavigateToTrending =
                                        onNavigateToTrending
                                )
                            }
                        }
                    }
                }
            }

        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
            ) {

                ZoneRecherche(
                    recherche = recherche,
                    onChange = { recherche = it },
                    lancee = rechercheLancee,
                    chargement = chargement,
                    compact = false,
                    onSearch = { lancerRecherche() },
                    onClear = {
                        recherche = ""
                        rechercheLancee = false
                        viewModel.clearSearch()
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    if (rechercheLancee) {

                        Resultats(
                            tracks = searchTracks,
                            artists = searchArtists,
                            erreur = erreur,
                            chargement = chargement,
                            onNavigateToTrack = onNavigateToTrack,
                            onNavigateToArtist = onNavigateToArtist
                        )

                    } else {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(
                                    rememberScrollState()
                                )
                                .padding(vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                20.dp,
                                Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Image(
                                painter = painterResource(
                                    id = R.drawable.licornus
                                ),
                                contentDescription = "Licornus",
                                modifier = Modifier.size(200.dp),
                                contentScale = ContentScale.Fit
                            )

                            BoutonsMenu(
                                onNavigateToForgottenMusic =
                                    onNavigateToForgottenMusic,
                                onNavigateToOldArtists =
                                    onNavigateToOldArtists,
                                onNavigateToTrending =
                                    onNavigateToTrending
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ZoneRecherche(
    recherche: String,
    onChange: (String) -> Unit,
    lancee: Boolean,
    chargement: Boolean,
    compact: Boolean,
    onSearch: () -> Unit,
    onClear: () -> Unit
) {

    val champ: @Composable (Modifier) -> Unit = { modifier ->

        OutlinedTextField(
            value = recherche,
            onValueChange = onChange,
            label = {
                Text("🔍 Artiste ou titre")
            },
            singleLine = true,
            modifier = modifier,
            trailingIcon = {
                if (lancee) {
                    TextButton(
                        onClick = onClear
                    ) {
                        Text("✖")
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                }
            )
        )
    }

    val bouton: @Composable (Modifier) -> Unit = { modifier ->

        Button(
            modifier = modifier,
            enabled = recherche.isNotBlank() && !chargement,
            onClick = onSearch
        ) {
            Text("Rechercher")
        }
    }

    if (compact) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            champ(
                Modifier.weight(1f)
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            bouton(Modifier)
        }

    } else {

        Column {

            champ(
                Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            bouton(
                Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun Resultats(
    tracks: List<Track>,
    artists: List<Artist>,
    erreur: Boolean,
    chargement: Boolean,
    onNavigateToTrack: (String) -> Unit,
    onNavigateToArtist: (String) -> Unit
) {

    when {

        chargement -> {
            LoadingScreen()
        }

        erreur -> {
            ErrorScreen()
        }

        tracks.isEmpty() && artists.isEmpty() -> {
            EmptyScreen()
        }

        else -> {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                if (artists.isNotEmpty()) {

                    item {

                        Text(
                            text = "⭐ Artistes",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(artists) { artiste ->

                        Card(
                            onClick = {
                                artiste.id?.let { id ->
                                    onNavigateToArtist(id)
                                }
                            },
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFF3E0)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {

                            Text(
                                text = "⭐ ${artiste.name}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                if (tracks.isNotEmpty()) {

                    item {

                        Text(
                            text = "🎵 Titres",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(tracks) { track ->

                        Card(
                            onClick = {
                                track.id?.let { id ->
                                    onNavigateToTrack(id)
                                }
                            },
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE8F5E9)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Text("🎵 ${track.name}")

                                if (track.artists.isNotEmpty()) {

                                    Text(
                                        text = track.artists.joinToString {
                                            it.name
                                        },
                                        style = MaterialTheme.typography.bodyMedium
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

@Composable
private fun BoutonsMenu(
    onNavigateToForgottenMusic: () -> Unit,
    onNavigateToOldArtists: () -> Unit,
    onNavigateToTrending: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8E44AD)
            ),
            onClick = onNavigateToForgottenMusic
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
            onClick = onNavigateToOldArtists
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
            onClick = onNavigateToTrending
        ) {

            Text("🔥 Dernières sorties")
        }
    }
}
