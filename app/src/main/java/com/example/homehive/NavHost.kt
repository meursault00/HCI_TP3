package com.example.homehive


import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homehive.screens.BlindsScreen
import com.example.homehive.screens.HelpScreen
import com.example.homehive.screens.HomeScreen
import com.example.homehive.screens.OvenScreen
import com.example.homehive.screens.RoutinesScreen
import com.example.homehive.screens.SettingsScreen
import com.example.homehive.screens.SpeakerScreen
import com.example.homehive.screens.TapScreen
import com.example.homehive.screens.TestScreen
import com.example.homehive.viewmodels.DevicesVM
import com.example.homehive.viewmodels.FridgeVM
import com.example.homehive.viewmodels.OvenVM
import com.example.homehive.viewmodels.RoutinesVM
import com.example.homehive.viewmodels.SpeakerVM


@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "routines",
) {

    // Creacion de Singleton VM para los Dipositivos y Fetch de Devices
    
    val devicesVM = remember { DevicesVM() }
    val devicesState by devicesVM.uiState.collectAsState()
    LaunchedEffect(Unit) {
        devicesVM.fetchDevices()
    }
    
    val devices = devicesState.devices
    SideEffect {
        println("List of devices: $devices")
    }    
    
    // Creacion de Singleton VM para las Rutinas y Fetch de Routines
    
    val routinesVM = remember { RoutinesVM() }
    val routinesState by routinesVM.uiState.collectAsState()
    LaunchedEffect(Unit) {
        routinesVM.fetchRoutines()
    }
    
    val routines = routinesState.routines
    SideEffect {
        println("List of devices: $routines")
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------


    val uri = "http://www.example.com"
    val secureUri = "https://www.example.com"

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("test") {
            App(navController = navController) { navController, innerPadding ->
                TestScreen(navController = navController, innerPadding = innerPadding, devicesVM = viewModel())
            }
        }
        composable("routines") {
            App(navController = navController) { navController, innerPadding ->
                RoutinesScreen(navController = navController, innerPadding = innerPadding, routinesVM)
            }
        }
        composable("home") {
            App(navController = navController) { navController, innerPadding ->
                HomeScreen(navController = navController, innerPadding = innerPadding , devicesVM = devicesVM , routinesVM = routinesVM)
            }
        }
        composable("settings") {
            println("List of devices: $devices")
            App(navController = navController) { navController, innerPadding ->
                SettingsScreen(navController = navController, innerPadding = innerPadding)
            }
        }
        composable("help") {
            println("List of devices: $devices")
            App(navController = navController) { navController, innerPadding ->
                TestScreen(navController = navController, innerPadding = innerPadding, devicesVM = viewModel())
            }
        }
//        composable(
//            "devices/{devicename}/{id}",
//        ) {}
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
        composable("devices/speaker/{id}") {
            App(navController = navController) { navController, innerPadding ->
                SpeakerScreen(navController = navController, innerPadding = innerPadding)
            }
        }
        composable(
            "devices/oven/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            App(navController = navController) { navController, innerPadding ->
                val deviceViewModelMap = DeviceViewModelMap.map
                val viewModel = deviceViewModelMap[id.toString()]
                val ovenVM = viewModel as? OvenVM
                OvenScreen(navController = navController, innerPadding = innerPadding, ovenVM = ovenVM!!)
            }
        }
        composable(
            "devices/speaker/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            App(navController = navController) { navController, innerPadding ->
                val deviceViewModelMap = DeviceViewModelMap.map
                val viewModel = deviceViewModelMap[id.toString()]
                val speakerVM = viewModel as? SpeakerVM
                SpeakerScreen(navController = navController, innerPadding = innerPadding, speakerVM = speakerVM!!)
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

