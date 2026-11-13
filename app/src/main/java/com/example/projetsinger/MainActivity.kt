
package com.example.projetsinger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.projetsinger.ui.theme.ProjetSingerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ProjetSingerTheme {

                val backStack = rememberNavBackStack(Home)

                NavDisplay(
                    backStack = backStack,
                    onBack = {
                        if (backStack.size > 1) {
                            backStack.removeLastOrNull()
                        }
                    },
                    entryProvider = entryProvider {

                        entry<Home> {
                            HomeScreen(
                                onNavigateToForgottenMusic = {
                                    backStack.add(ForgottenMusic)
                                },
                                onNavigateToOldArtists = {
                                    backStack.add(OldArtists)
                                },
                                onNavigateToTrending = {
                                    backStack.add(Trending)
                                },
                                onNavigateToTrack = { id ->
                                    backStack.add(TrackDetail(id))
                                },
                                onNavigateToArtist = { id ->
                                    backStack.add(ArtistDetail(id))
                                }
                            )
                        }

                        entry<ForgottenMusic> {

                            ForgottenMusicScreen(

                                onBack = {
                                    backStack.removeLastOrNull()
                                },

                                onTrackClick = { id ->
                                    backStack.add(
                                        TrackDetail(id)
                                    )
                                }
                            )
                        }

                        entry<OldArtists> {

                            OldArtistsScreen(

                                onBack = {
                                    backStack.removeLastOrNull()
                                },

                                onArtistClick = { id ->
                                    backStack.add(
                                        ArtistDetail(id)
                                    )
                                }
                            )
                        }

                        entry<Trending> {

                            TrendingScreen(

                                onBack = {
                                    backStack.removeLastOrNull()
                                },

                                onArtistClick = { id ->
                                    backStack.add(
                                        ArtistDetail(id)
                                    )
                                }
                            )
                        }

                        entry<TrackDetail> { key ->

                            TrackDetailScreen(

                                trackId = key.id,

                                onBack = {
                                    backStack.removeLastOrNull()
                                },

                                onArtistClick = { id ->
                                    backStack.add(
                                        ArtistDetail(id)
                                    )
                                }
                            )
                        }

                        entry<ArtistDetail> { key ->
                            ArtistDetailScreen(
                                artistId = key.id,
                                onBack = {
                                    backStack.removeLastOrNull()
                                }
                            )
                        }
                    }
                )
            }
        }
    }
}
