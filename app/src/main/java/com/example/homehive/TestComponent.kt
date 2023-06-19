package com.example.homehive

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

