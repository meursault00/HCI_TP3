package com.example.homehive

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.ui.theme.HomeHiveTheme
import com.example.homehive.viewmodels.DevicesVM

//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
//
//@Composable
//fun TestScreen(
//    viewModel: DevicesVM,
//    modifier: Modifier = Modifier) {
//
//    val uiState by viewModel.uiState.collectAsState()
//
//    SwipeRefresh(
//        state = rememberSwipeRefreshState(uiState.isLoading),
//        onRefresh = { viewModel.fetchDevices() },
//    ) {
//        Column(
//            modifier = modifier
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//            ) {
//                if (uiState.isLoading)
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = stringResource(R.string.loading),
//                            fontSize = 16.sp
//                        )
//                    }
//                else {
////                    val list = uiState.devices?.data.orEmpty()
//                    val list = uiState.devices?.result.orEmpty()
//                    LazyVerticalGrid(
//                        columns = GridCells.Adaptive(minSize = 196.dp)
//                    ) {
//                        items(
//                            count = list.size,
//                            key = { index ->
//                                list[index].id.toString()
//                            }
//                        ) { index ->
//                            MiniDisplay(list[index])
//                        }
//                    }
//                }
//            }
//            Button(
//                onClick = {
//                    viewModel.fetchDevices()
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Text(
//                    text = stringResource(R.string.load_devices),
//                    modifier = Modifier.padding(8.dp))
//            }
//        }
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun TestScreenPreview() {
//    HomeHiveTheme() {
//        TestScreen(viewModel = viewModel())
//    }
//}

