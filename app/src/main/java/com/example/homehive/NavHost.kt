package com.example.homehive


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homehive.screens.BlindsScreen
import com.example.homehive.screens.FridgeScreen
import com.example.homehive.screens.HomeScreen
import com.example.homehive.screens.OvenScreen
import com.example.homehive.screens.RoutinesScreen
import com.example.homehive.screens.SpeakerScreen
import com.example.homehive.screens.TapScreen
import com.example.homehive.screens.TestScreen

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "routines"
) {
    val uri = "http://www.example.com"
    val secureUri = "https://www.example.com"

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("test") {
            App(navController = navController) { navController, innerPadding ->
                TestScreen(navController = navController, innerPadding = innerPadding, viewModel = viewModel())
            }
        }
        composable("routines") {
            App(navController = navController) { navController, innerPadding ->
                RoutinesScreen(navController = navController, innerPadding = innerPadding)
            }
        }
        composable("home") {
            App(navController = navController) { navController, innerPadding ->
                HomeScreen(navController = navController, innerPadding = innerPadding)
            }
        }
//        composable(
//            "devices/{devicename}/{id}",
//        ) {}
        composable("devices/fridge/1234") {
            App(navController = navController) { navController, innerPadding ->
                FridgeScreen(navController = navController, innerPadding = innerPadding)
            }
        }
        composable("devices/tap/1234") {
            App(navController = navController) { navController, innerPadding ->
                TapScreen(navController = navController, innerPadding = innerPadding)
            }
        }
        composable("devices/blinds/1234") {
            App(navController = navController) { navController, innerPadding ->
                BlindsScreen(navController = navController, innerPadding = innerPadding)
            }
        }
        composable("devices/speaker/1234") {
            App(navController = navController) { navController, innerPadding ->
                SpeakerScreen(navController = navController, innerPadding = innerPadding)
            }
        }
        composable("devices/oven/1234") {
            App(navController = navController) { navController, innerPadding ->
                OvenScreen(navController = navController, innerPadding = innerPadding)
            }
        }
        composable(
            "other/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            deepLinks = listOf(
                navDeepLink { uriPattern = "$uri/other?id={id}" },
                navDeepLink { uriPattern = "$secureUri/other?id={id}" }
            )
        ) {
//            OtherScreen(it.arguments?.getInt("id"))
        }
    }
}
