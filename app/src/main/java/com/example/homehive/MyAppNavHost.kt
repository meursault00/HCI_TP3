package com.example.homehive


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    val uri = "http://www.example.com"
    val secureUri = "https://www.example.com"

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier) {
        composable("home") {
            HomeScreen(
                onNavigateToOtherScreen = { id -> navController.navigate("other/$id")}
            )
        }
        composable(
            "other/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/other?id={id}" },
                navDeepLink { uriPattern = "$secureUri/other?id={id}" })
        ) {
            OtherScreen(it.arguments?.getInt("id"))
        }
    }
}