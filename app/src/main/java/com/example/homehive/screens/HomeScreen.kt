package com.example.homehive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homehive.boxes.BlindsBox
import com.example.homehive.boxes.DoorBox
import com.example.homehive.boxes.FridgeBox
import com.example.homehive.boxes.OvenBox
import com.example.homehive.boxes.SpeakerBox
import com.example.homehive.boxes.TapBox
import com.example.homehive.network.deviceModels.NetworkResult
import com.example.homehive.viewmodels.DevicesVM
import com.example.homehive.viewmodels.FridgeVM

@Composable
fun HandleDevice(device: NetworkResult, navController: NavController) {

}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
    devicesVM: DevicesVM
) {
    val devicesState by devicesVM.uiState.collectAsState()

    Box(
        modifier = Modifier.padding(innerPadding ?: PaddingValues())
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            devicesState.devices?.result?.forEach { device ->
                item{
                    when (device.type?.name) {
                        "oven" -> {
                            OvenBox(onClick = {
                                navController.navigate("devices/oven/1234")
                            })
                        }

                        "refrigerator" -> {
                            FridgeBox(onClick = {})
                        }

                        "faucet" -> {
                            TapBox(onClick = {})
                        }

                        "blinds" -> {
                            BlindsBox(onClick = {})
                        }

                        "speaker" -> {
                            SpeakerBox(onClick = {
                                navController.navigate("devices/speaker/1234")
                            })
                        }
                        // Add more cases for other device types
                        else -> {
                            // Handle unknown device types if necessary
                        }
                    }
                }
            }
        }
    }
}




