package com.example.projetsinger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetsinger.ui.theme.ProjetSingerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            ProjetSingerTheme {

                val navController =
                    rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.Home.route
                ) {

                    composable(Routes.ForgottenMusic.route) {
                        ForgottenMusicScreen(navController)
                    }

                    composable(Routes.OldArtists.route) {
                        OldArtistsScreen(navController)
                    }

                    composable(Routes.Trending.route) {
                        TrendingScreen(navController)
                    }

                    composable(Routes.Search.route) {
                        SearchScreen(navController)
                    }
                    composable(Routes.Home.route) {
                        HomeScreen(navController)
                    }
                }
            }
        }
    }
}

sealed class Routes(val route: String) {

    object Home : Routes("home")

    object ForgottenMusic : Routes("forgotten_music")

    object OldArtists : Routes("old_artists")

    object Trending : Routes("trending")

    object Search : Routes("search")
}