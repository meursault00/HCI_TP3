package com.example.homehive.screens

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homehive.MiniDisplay
import com.example.homehive.R
import com.example.homehive.sendCustomNotification
import com.example.homehive.states.hasError
import com.example.homehive.ui.theme.HomeHiveTheme
import com.example.homehive.viewmodels.DevicesVM
import com.example.homehive.viewmodels.RoutinesVM
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

//import com.example.homehive.TestComponent
//
//@Composable
//fun TestScreen(navController: NavController, innerPadding: PaddingValues?) {
//    Box(
//        modifier = Modifier
//            .padding(innerPadding ?: PaddingValues())
//    ) {
//        Text(text = "Danger : Testing Zone")
//
//    }
//}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
    devicesVM: DevicesVM = viewModel(),
    routinesVM: RoutinesVM = viewModel()

) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by devicesVM.uiState.collectAsState()

    val context = LocalContext.current // Obtain the context

    HomeHiveTheme() {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { paddingValues ->
            paddingValues
            Column() {
                Button(
                    onClick = {
                        devicesVM.fetchDevices()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Fetch All Devices",
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Button(
                    onClick = {
                        devicesVM.fetchADevice("04add9611b2cde4d")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Fetch A Device",
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Button(
                    onClick = {
                        routinesVM.fetchRoutines()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Fetch All Routines",
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Button(
                    onClick = {
                        routinesVM.fetchARoutine("0153972e3de51892")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Fetch A Routine",
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Text("Actualmente no actualiza nada hay que actualizar el codigo y ver en Logcat y buscar el tag okHttp")
                Button(
                    onClick = {
                        // Ejemplo para accion que no recibe parametros, ID de un Speaker
                        // devicesVM.editADevice("04add9611b2cde4d", "stop", listOf())

                        // Ejemplo para accion que recibe parametros String, ID de un Fridge
                        // devicesVM.editADevice("17af29013f750d0e", "setMode", listOf("party"))

                        // Ejemplo para accion que recibe parametros Int, ID de un Fridge
                        // devicesVM.editADevice("17af29013f750d0e", "setTemperature", listOf(-4))

                        // Ejemplo para accion que recibe parametros Mixtos, ID de un Tap
                        // devicesVM.editADevice("a7837dbd80d58abf", "dispense", listOf(10,"ml"))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Update A Device",
                        modifier = Modifier.padding(8.dp)
                    )
                }
                // --------------------------------------------------------------------------------------------------------------------------------------------

                Button(
                    onClick = {
                        val title = "Chrissy chan"
                        val content = "ChrisChrisChrisChrisChrisCHrisCHsriChrisChrisChrisChrisChrisCHrisCHsriChrisChrisChrisChrisChrisCHrisCHsri"
                        sendCustomNotification(context, title, content)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Send Notification",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }


            if (uiState.hasError) {
                val actionLabel = stringResource(R.string.dismiss)

                LaunchedEffect(true) {
                    val result = snackbarHostState.showSnackbar(
                        message = uiState.message!!,
                        actionLabel = actionLabel
                    )
                    when (result) {
                        SnackbarResult.Dismissed -> devicesVM.dismissMessage()
                        SnackbarResult.ActionPerformed -> devicesVM.dismissMessage()
                    }
                }
            }
        }
    }
}



@Composable
fun RefreshingComponent(
    navController: NavController,
    innerPadding: PaddingValues?,
    viewModel: DevicesVM,
    modifier: Modifier = Modifier) {

    val uiState by viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier
            .padding(innerPadding ?: PaddingValues())
    ) {
        Text("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaaaaaaaaaaaaaaaaaaaaaaaaaaAAAAAAAAAAAaaa")
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = { viewModel.fetchDevices() },
        ) {

            Column(
                modifier = modifier
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    if (uiState.isLoading)
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.loading),
                                fontSize = 16.sp
                            )
                        }
                    else {
//                    val list = uiState.devices?.data.orEmpty()
                        val list = uiState.devices?.result.orEmpty()
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 196.dp)
                        ) {
                            items(
                                count = list.size,
                                key = { index ->
                                    list[index].id.toString()
                                }
                            ) { index ->
                                Log.d("anashe", "anasheiiiii")
                                Text("{list[index].id}")
//                              MiniDisplay(list[index])
                            }
                        }
                    }
                }
                Button(
                    onClick = {
                        viewModel.fetchDevices()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.load_devices),
                        modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}