package com.example.homehive


import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@Composable
fun MyAppNavHost(
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
        composable("routines") {

            App(navController) { RoutinesScreen(navController = navController) }
        }
        composable("home") {
            App(navController) { HomeScreen(navController = navController) }
        }
//        composable(
//            "devices/{devicename}/{id}",
//        ) {}
        composable("devices/fridge/1234") {
            App(navController) { FridgeScreen(navController = navController)}
        }
        composable("devices/tap/1234") {
            App(navController) { TapScreen(navController = navController)}
        }
        composable("devices/blinds/1234") {
            App(navController) { BlindsScreen(navController = navController) }
        }
        composable("devices/speaker/1234") {
            App(navController) { SpeakerScreen(navController = navController) }
        }
        composable("devices/oven/1234") {
            App(navController) { OvenScreen(navController = navController)}
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
