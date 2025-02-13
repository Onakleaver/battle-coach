package com.battlecoach.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.battlecoach.ui.screens.home.HomeScreen
import com.battlecoach.ui.screens.game.GameScreen
import com.battlecoach.ui.screens.training.TrainingScreen
import com.battlecoach.ui.screens.profile.ProfileScreen

@Composable
fun BattleCoachNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Game.route) {
            GameScreen(navController)
        }
        composable(Screen.Training.route) {
            TrainingScreen(navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Game : Screen("game")
    object Training : Screen("training")
    object Profile : Screen("profile")
} 