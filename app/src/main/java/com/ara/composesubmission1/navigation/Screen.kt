package com.ara.composesubmission1.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailArtist : Screen("home/{artistId}") {
        fun createRoute(artistId: Int) = "home/$artistId"
    }
}
