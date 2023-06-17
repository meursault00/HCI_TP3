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
import com.example.homehive.viewmodels.BlindsVM
import com.example.homehive.viewmodels.DevicesVM
import com.example.homehive.viewmodels.FridgeVM
import com.example.homehive.viewmodels.OvenVM
import com.example.homehive.viewmodels.SpeakerVM
import com.example.homehive.viewmodels.TapVM

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
    devicesVM.fetchDevices()
    val deviceViewModelMap = remember { mutableMapOf<String, ViewModel>() }
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
                item {

                //por cada item creamos un viewmodel especifico, lo
                    val viewModel = remember(device.id) {
                        when (device.type?.name) {
                            "oven" -> {
                                deviceViewModelMap.getOrPut(device.id.toString()) { OvenVM() }
                            }
                            "refrigerator" -> {
                                deviceViewModelMap.getOrPut(device.id.toString()) {
                                    FridgeVM(
                                        device.id,
                                        device.state?.temperature,
                                        device.state?.freezerTemperature,
                                        device.state?.mode,
                                        devicesVM,
                                    )
                                }
                            }
                            "faucet" -> {
                                 deviceViewModelMap.getOrPut(device.id.toString()) { TapVM() }
                            }

                            "blinds" -> {
                                deviceViewModelMap.getOrPut(device.id.toString()) { BlindsVM() }
                            }

                            "speaker" -> {
                                deviceViewModelMap.getOrPut(device.id.toString()) { SpeakerVM() }
                            }
                            // Add more cases for other device types
                            else -> {
                                // Handle unknown device types if necessary
                                null
                            }
                        }
                    }
                    when (device.type?.name) {
                        "oven" -> {
                            (viewModel as? OvenVM)?.let { OvenBox(onClick = {}, ovenVM = it)}
                        }
                        "refrigerator" -> {
                            (viewModel as? FridgeVM)?.let { FridgeBox(onClick = {}, fridgeVM = it) }
                        }
                        "faucet" -> {
                            (viewModel as? TapVM)?.let { TapBox(onClick = {}, tapVM = it) }
                        }
                        "blinds" -> {
                            (viewModel as? BlindsVM)?.let { BlindsBox(onClick = {}, blindsVM = it) }
                        }
                        "speaker" -> {
                            (viewModel as? SpeakerVM)?.let {
                                SpeakerBox(onClick = {
                                    navController.navigate("devices/speaker/1234")
                                }, speakerVM = it)
                            }
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




