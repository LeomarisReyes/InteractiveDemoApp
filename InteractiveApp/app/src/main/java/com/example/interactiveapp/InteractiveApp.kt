package com.example.interactiveapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.feature.capitalizergenerator.CapitalizerGeneratorScreen
import com.example.feature.coreui.MenuScreen
import com.example.feature.passwordgenerator.RandomPasswordGeneratorScreen
import com.example.feature.presidents.details.PresidentDetailsScreen
import com.example.feature.presidents.list.PresidentListScreen

@Composable
fun InteractiveApp(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "Menu") {

        composable("Menu") {
            MenuScreen(navController = navController)
        }

        composable("RandomPasswordGenerator") {
            RandomPasswordGeneratorScreen()
        }

        composable("CapitalizerGenerator") {
            CapitalizerGeneratorScreen()
        }

        composable("PresidentList") {
            PresidentListScreen(navController = navController)
        }

        composable(
            "PresidentDetailsScreen/{presidentId}",
            arguments = listOf(
                navArgument("presidentId") { type = NavType.IntType}
            )
        ) { navBackStackEntry ->
            val presidentId = navBackStackEntry.arguments?.getInt("presidentId")
            PresidentDetailsScreen(
                presidentId = presidentId ?:0
            )
        }
    }
}