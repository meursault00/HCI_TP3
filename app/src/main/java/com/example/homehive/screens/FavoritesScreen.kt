package com.example.homehive.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homehive.DeviceViewModelMap
import com.example.homehive.library.FavoritesArray
import com.example.homehive.LoadingAnimation
import com.example.homehive.R
import com.example.homehive.boxes.BlindsBox
import com.example.homehive.boxes.FridgeBox
import com.example.homehive.boxes.OvenBox
import com.example.homehive.boxes.SpeakerBox
import com.example.homehive.boxes.TapBox
import com.example.homehive.viewmodels.BlindsVM
import com.example.homehive.viewmodels.DevicesVM
import com.example.homehive.viewmodels.FridgeVM
import com.example.homehive.viewmodels.OvenVM
import com.example.homehive.viewmodels.SpeakerVM
import com.example.homehive.viewmodels.TapVM
import com.example.homehive.viewmodels.isDarkTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
    devicesVM: DevicesVM
) {
    val devicesState by devicesVM.uiState.collectAsState()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    var showLoader = remember {
        mutableStateOf(true)
    }

    loadDevicesAndRoutines2(devicesVM =  devicesVM)
    if((devicesState.isLoading) && showLoader.value){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding ?: PaddingValues()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation(circleColor =  if(isDarkTheme.value) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text =
                if(devicesState.isLoading){
                    stringResource(id = R.string.loading_devices)
                } else {
                    stringResource(id = R.string.loading_devices)
                },
                color =  if(isDarkTheme.value) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.secondary,
            )

        }
    }
    else {
        showLoader.value = false

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding ?: PaddingValues())
        ) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    showLoader.value = true
                    devicesVM.fetchDevices()
                },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = MaterialTheme.colorScheme.background,
                        backgroundColor = MaterialTheme.colorScheme.secondary
                    )
                }
            ) {
                if(devicesState.devices?.result?.isNotEmpty() == null){
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        item(){
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 10.dp, end = 10.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.error_notice),
                                    contentDescription = "Error",
                                    tint = if(isDarkTheme.value) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(100.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.no_devices) + "\n"
                                            + "Error: " + devicesState.message.toString() + "\n" + "\n"
                                            + stringResource(id = R.string.check_connection) + "\n"
                                            + stringResource(id = R.string.try_refreshing)
                                    ,

                                    color =  if(isDarkTheme.value) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.secondary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                else if( devicesState.devices?.result?.isNotEmpty() == true){
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(170.dp),
                    ){
                        devicesState.devices?.result?.forEach { device ->
                            item {
                                //solo mostramos el device si se encuentra en el array de favs
                                if(FavoritesArray.array.contains(device.id)) {
                                    val viewModel = remember(device.id) {
                                        when (device.type?.name) {
                                            "oven" -> {
                                                DeviceViewModelMap.map.getOrPut(device.id.toString()) {
                                                    OvenVM(
                                                        device.id,
                                                        device.name,
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
                                                        device.name,
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
                                                        device.name,
                                                        device.state?.status,
                                                        devicesVM
                                                    )
                                                }
                                            }

                                            "blinds" -> {
                                                DeviceViewModelMap.map.getOrPut(device.id.toString()) {
                                                    BlindsVM(
                                                        device.id,
                                                        device.name,
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
                                                        device.name,
                                                        device.state?.status,
                                                        device.state?.volume,
                                                        device.state?.song,
                                                        device.state?.genre,
                                                        devicesVM.fetchPlaylist(device.id ?: ""),
                                                        // llamado a la api
                                                        // otra cuenta distinta para pasarle la playslist
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
                                            (viewModel as? TapVM)?.let {
                                                TapBox(
                                                    onClick = {},
                                                    tapVM = it
                                                )
                                            }
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
                }
                else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        item(){
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 10.dp, end = 10.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.add_device),
                                    contentDescription = "Error",
                                    tint = if(isDarkTheme.value) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(100.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.add_devices) + "\n",
                                    color =  if(isDarkTheme.value) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.secondary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun loadDevicesAndRoutines2(devicesVM: DevicesVM){

    LaunchedEffect(Unit){
        try{
            devicesVM.fetchDevices()
        } catch (e: Exception){
            Log.e("HomeScreen", "Error fetching devices")
        }
    }
}