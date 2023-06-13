package com.example.homehive.screens

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.homehive.MiniDisplay
import com.example.homehive.R
import com.example.homehive.viewmodels.DevicesVM

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
/*
@Composable
fun TestScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
    viewModel: DevicesVM,
    modifier: Modifier = Modifier) {

    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .padding(innerPadding ?: PaddingValues())
    ) {
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
                                Text("{list[index].id}")
//                            MiniDisplay(list[index])
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
*/
