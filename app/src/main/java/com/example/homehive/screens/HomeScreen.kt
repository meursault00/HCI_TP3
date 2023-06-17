package com.example.homehive.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homehive.DeviceViewModelMap
import com.example.homehive.boxes.BlindsBox
import com.example.homehive.boxes.DoorBox
import com.example.homehive.boxes.FridgeBox
import com.example.homehive.boxes.OvenBox
import com.example.homehive.boxes.RoutineBox
import com.example.homehive.boxes.SpeakerBox
import com.example.homehive.boxes.TapBox
import com.example.homehive.network.deviceModels.NetworkResult
import com.example.homehive.viewmodels.BlindsVM
import com.example.homehive.viewmodels.DevicesVM
import com.example.homehive.viewmodels.FridgeVM
import com.example.homehive.viewmodels.OvenVM
import com.example.homehive.viewmodels.RoutineVM
import com.example.homehive.viewmodels.RoutinesVM
import com.example.homehive.viewmodels.SpeakerVM
import com.example.homehive.viewmodels.TapVM

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
    devicesVM: DevicesVM,
    routinesVM: RoutinesVM
) {
    devicesVM.fetchDevices()
    val devicesState by devicesVM.uiState.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding ?: PaddingValues())
    ) {

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(150.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        ){
            item(
                span = StaggeredGridItemSpan.FullLine
            ){
                LazyRoutineRow(routinesVM = routinesVM)
            }

            devicesState.devices?.result?.forEach { device ->
                        item {
                            val viewModel = remember(device.id) {
                                when (device.type?.name) {
                                    "oven" -> {
                                        DeviceViewModelMap.map.getOrPut(device.id.toString()) {
                                            OvenVM(
                                                device.id,
                                                device.state?.status,
                                                device.state?.temperature,
                                                device.state?.grill,
                                                device.state?.heat,
                                                device.state?.convection,
                                                devicesVM
                                            )
                                        }
                                    }

                                    "refrigerator" -> {
                                        DeviceViewModelMap.map.getOrPut(device.id.toString()) {
                                            FridgeVM(
                                                device.id,
                                                device.state?.temperature,
                                                device.state?.freezerTemperature,
                                                device.state?.mode,
                                                devicesVM
                                            )
                                        }
                                    }

                                    "faucet" -> {
                                        DeviceViewModelMap.map.getOrPut(device.id.toString()) {
                                            TapVM(
                                                device.id,
                                                device.state?.status,
                                                devicesVM
                                            )
                                        }
                                    }

                                    "blinds" -> {
                                        DeviceViewModelMap.map.getOrPut(device.id.toString()) {
                                            BlindsVM(
                                                device.id,
                                                device.state?.status,
                                                device.state?.level,
                                                device.state?.currentLevel,
                                                devicesVM
                                            )
                                        }
                                    }

                                    "speaker" -> {
                                        DeviceViewModelMap.map.getOrPut(device.id.toString()) {
                                            SpeakerVM(
                                                device.id,
                                                device.state?.status,
                                                device.state?.volume,
                                                device.state?.song,
                                                device.state?.genre,
                                                devicesVM
                                            )
                                        }
                                    }
                                    // Add more cases for other device types
                                    else -> null
                                }
                            }

                            when (device.type?.name) {
                                "oven" -> {
                                    (viewModel as? OvenVM)?.let {
                                        OvenBox(onClick = {
                                            navController.navigate("devices/oven/" + device.id.toString())
                                        }, ovenVM = it)
                                    }
                                }

                                "refrigerator" -> {
                                    (viewModel as? FridgeVM)?.let {
                                        FridgeBox(
                                            onClick = {},
                                            fridgeVM = it
                                        )
                                    }
                                }

                                "faucet" -> {
                                    (viewModel as? TapVM)?.let { TapBox(onClick = {}, tapVM = it) }
                                }

                                "blinds" -> {
                                    (viewModel as? BlindsVM)?.let {
                                        BlindsBox(
                                            onClick = {},
                                            blindsVM = it
                                        )
                                    }
                                }

                                "speaker" -> {
                                    (viewModel as? SpeakerVM)?.let {
                                        SpeakerBox(onClick = {
                                            navController.navigate("devices/speaker/" + device.id.toString())
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

            item(
                span = StaggeredGridItemSpan.FullLine
            ){
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        /*
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyRoutineRow(routinesVM = routinesVM)

            LazyVerticalStaggeredDevicesGrid(navController = navController, devicesVM = devicesVM)
        }*/
    }
}


@Composable
fun LazyRoutineRow(routinesVM: RoutinesVM){
    routinesVM.fetchRoutines()
    val routinesViewModelMap = remember { mutableMapOf<String, RoutineVM>() }
    val routinesState by routinesVM.uiState.collectAsState()
    LazyRow(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        routinesState.routines?.result?.forEach { routine ->
            item {
                val routineVM = routinesViewModelMap.getOrPut(routine.id.toString()) {
                    RoutineVM(
                        routine.id ?: "",
                        routine.name ?: "",
                        routine.actions,
                        routinesVM
                    )
                }
                RoutineBox(routineVM = routineVM)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyVerticalStaggeredDevicesGrid(navController: NavController,devicesVM: DevicesVM){
    devicesVM.fetchDevices()
    val devicesState by devicesVM.uiState.collectAsState()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        devicesState.devices?.result?.forEach { device ->
            item {
                val viewModel = remember(device.id) {
                    when (device.type?.name) {
                        "oven" -> {
                            DeviceViewModelMap.map.getOrPut(device.id.toString()) {
                                OvenVM(
                                    device.id,
                                    device.state?.status,
                                    device.state?.temperature,
                                    device.state?.grill,
                                    device.state?.heat,
                                    device.state?.convection,
                                    devicesVM
                                )
                            }
                        }

                        "refrigerator" -> {
                            DeviceViewModelMap.map.getOrPut(device.id.toString()) {
                                FridgeVM(
                                    device.id,
                                    device.state?.temperature,
                                    device.state?.freezerTemperature,
                                    device.state?.mode,
                                    devicesVM
                                )
                            }
                        }

                        "faucet" -> {
                            DeviceViewModelMap.map.getOrPut(device.id.toString()) {
                                TapVM(
                                    device.id,
                                    device.state?.status,
                                    devicesVM
                                )
                            }
                        }

                        "blinds" -> {
                            DeviceViewModelMap.map.getOrPut(device.id.toString()) {
                                BlindsVM(
                                    device.id,
                                    device.state?.status,
                                    device.state?.level,
                                    device.state?.currentLevel,
                                    devicesVM
                                )
                            }
                        }

                        "speaker" -> {
                            DeviceViewModelMap.map.getOrPut(device.id.toString()) {
                                SpeakerVM(
                                    device.id,
                                    device.state?.status,
                                    device.state?.volume,
                                    device.state?.song,
                                    device.state?.genre,
                                    devicesVM
                                )
                            }
                        }
                        // Add more cases for other device types
                        else -> null
                    }
                }

                when (device.type?.name) {
                    "oven" -> {
                        (viewModel as? OvenVM)?.let {
                            OvenBox(onClick = {
                                navController.navigate("devices/oven/" + device.id.toString())
                            }, ovenVM = it)
                        }
                    }

                    "refrigerator" -> {
                        (viewModel as? FridgeVM)?.let {
                            FridgeBox(
                                onClick = {},
                                fridgeVM = it
                            )
                        }
                    }

                    "faucet" -> {
                        (viewModel as? TapVM)?.let { TapBox(onClick = {}, tapVM = it) }
                    }

                    "blinds" -> {
                        (viewModel as? BlindsVM)?.let {
                            BlindsBox(
                                onClick = {},
                                blindsVM = it
                            )
                        }
                    }

                    "speaker" -> {
                        (viewModel as? SpeakerVM)?.let {
                            SpeakerBox(onClick = {
                                navController.navigate("devices/speaker/" + device.id.toString())
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