package com.example.homehive.screens

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homehive.DeviceViewModelMap
import com.example.homehive.LoadingAnimation
import com.example.homehive.R
import com.example.homehive.UpdateMap
import com.example.homehive.boxes.BlindsBox
import com.example.homehive.boxes.FridgeBox
import com.example.homehive.boxes.GenericDropdownMenu
import com.example.homehive.boxes.OvenBox
import com.example.homehive.boxes.RoutineBox
import com.example.homehive.boxes.SpeakerBox
import com.example.homehive.boxes.TapBox
import com.example.homehive.viewmodels.BlindsVM
import com.example.homehive.viewmodels.DevicesVM
import com.example.homehive.viewmodels.FridgeVM
import com.example.homehive.viewmodels.OvenVM
import com.example.homehive.viewmodels.RoutineVM
import com.example.homehive.viewmodels.RoutinesVM
import com.example.homehive.viewmodels.SpeakerVM
import com.example.homehive.viewmodels.TapVM
import com.example.homehive.viewmodels.isDarkTheme
import com.example.homehive.viewmodels.isShowRoutines
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

enum class SortingType(private val labelResId: Int) {
    NAME_ASCENDING(R.string.name_ascending),
    NAME_DESCENDING(R.string.name_descending),
    TYPE_ASCENDING(R.string.type_ascending),
    TYPE_DESCENDING(R.string.type_descending),
    POWER_ASCENDING(R.string.power_ascending),
    POWER_DESCENDING(R.string.power_descending);

    fun getLabel(context: Context): String {
        return context.getString(labelResId)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
    devicesVM: DevicesVM,
    routinesVM: RoutinesVM
) {

    val context = LocalContext.current

    val devicesState by devicesVM.uiState.collectAsState()
    val routinesState by routinesVM.uiState.collectAsState()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val currentSorting = remember { mutableStateOf(SortingType.NAME_ASCENDING) }
    val sortingTypes: List<String> = SortingType.values().map { it.getLabel(context) }
    val expanded = remember { mutableStateOf(false) }
    val showLoader = remember {
        mutableStateOf(true)
    }

    loadDevicesAndRoutines(devicesVM =  devicesVM, routinesVM =  routinesVM)

    Log.d("takutaku",devicesState.devices.toString())

    if((devicesState.isLoading || routinesState.isLoading) && showLoader.value){
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
                if(devicesState.isLoading && routinesState.isLoading){
                    stringResource(id = R.string.loading_routines_and_devices)
                } else if(devicesState.isLoading){
                    stringResource(id = R.string.loading_devices)
                } else {
                    stringResource(id = R.string.loading_routines)
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
                    routinesVM.fetchRoutines()
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
                else if(devicesState.devices?.result?.isNotEmpty() == true){
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(170.dp),
                    ){
                        if(routinesState.routines?.result?.isNotEmpty() == true && isShowRoutines.value){
                            item(
                                span = StaggeredGridItemSpan.FullLine
                            ){
                                LazyRoutineRow(routinesVM = routinesVM)
                            }
                        }
                        item(
                            span = StaggeredGridItemSpan.FullLine
                        ){
                            Row(
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp)
                            ){
                                Text(
                                    text = stringResource(id = R.string.order_by),
                                    color =  if(isDarkTheme.value) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.secondary,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                        .align(Alignment.CenterVertically)
                                )

                                Surface(
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(170.dp)
                                        .clickable { expanded.value = !expanded.value }
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(15.dp),
                                    shadowElevation = 16.dp
                                ){
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .padding(start = 10.dp, end = 10.dp)
                                            .fillMaxWidth()
                                    ) {
                                        GenericDropdownMenu(
                                            items = sortingTypes,
                                            selectedItem = currentSorting.value.getLabel(context),
                                            onItemSelected = {
                                                currentSorting.value = SortingType.values()[sortingTypes.indexOf(it)]
                                            },
                                            expanded = expanded,
                                        )
                                        Icon(
                                            painter = if(expanded.value){
                                                painterResource(id = R.drawable.upicon)
                                            }else{
                                                painterResource(id = R.drawable.downicon)

                                            },
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onPrimary,
                                        )
                                    }
                                }


                            }
                        }

                        val sortedDevices = when (currentSorting.value) {
                            SortingType.NAME_ASCENDING -> devicesState.devices?.result?.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name!! })
                            SortingType.NAME_DESCENDING -> devicesState.devices?.result?.sortedWith(compareByDescending(String.CASE_INSENSITIVE_ORDER) { it.name!! })
                            SortingType.TYPE_ASCENDING -> devicesState.devices?.result?.sortedBy { it.type?.name  }
                            SortingType.TYPE_DESCENDING -> devicesState.devices?.result?.sortedByDescending { it.type?.name  }
                            SortingType.POWER_ASCENDING -> devicesState.devices?.result?.sortedByDescending { it.type?.powerUsage  }
                            SortingType.POWER_DESCENDING -> devicesState.devices?.result?.sortedBy { it.type?.powerUsage  }
                            else -> devicesState.devices?.result
                        }

                        sortedDevices?.forEach { device ->
                            UpdateMap.map.putIfAbsent(device.id.toString(), false)

                            item {
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
fun LazyRoutineRow(routinesVM: RoutinesVM){
    val routinesViewModelMap = remember { mutableMapOf<String, RoutineVM>() }
    val routinesState by routinesVM.uiState.collectAsState()
    if(routinesState.routines?.result?.isEmpty() == true){
        return
    }
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
                RoutineBox(routineVM = routineVM, isMinimalist = true)
            }
        }
    }
}



@Composable
fun loadDevicesAndRoutines(devicesVM: DevicesVM, routinesVM: RoutinesVM){
    try{
        devicesVM.fetchDevices()
        routinesVM.fetchRoutines()
    } catch (e: Exception){
        Log.e("HomeScreen", "Error fetching devices and routines")
    }
}