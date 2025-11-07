package com.android_task.scratch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android_task.scratch.ui.screens.activation.ActivationScreen
import com.android_task.scratch.ui.screens.card_root.CardRootScreen
import com.android_task.scratch.ui.screens.scratch.ScratchScreen

@Composable
fun NavigationStack() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(route = Screen.Main.route) {
            CardRootScreen(navController = navController)
        }
        composable(route = Screen.Scratch.route) {
            ScratchScreen(navController = navController)
        }
        composable(route = Screen.Activation.route) {
            ActivationScreen(navController = navController)
        }
    }
}

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object Scratch : Screen("scratch_screen")
    object Activation : Screen("activation_screen")
}
