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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homehive.LoadingAnimation
import com.example.homehive.R
import com.example.homehive.boxes.RoutineBox
import com.example.homehive.viewmodels.RoutineVM
import com.example.homehive.viewmodels.RoutinesVM
import com.example.homehive.viewmodels.isDarkTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoutinesScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
    routinesVM: RoutinesVM
) {



    val routinesState by routinesVM.uiState.collectAsState()
    val routinesViewModelMap = remember { mutableMapOf<String, RoutineVM>() }
    var popupControl by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    LaunchedEffect(Unit){
        try{
            routinesVM.fetchRoutines()
        } catch (e: Exception){
            Log.e("HomeScreen", "Error fetching devices and routines")
        }
    }
    if(routinesState.isLoading){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding ?: PaddingValues()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation(circleColor = if(isDarkTheme.value) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.loading_routines),
                color = if(isDarkTheme.value) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.secondary,
            )

        }
    }
    else{
        if(routinesState.routines?.result?.isNotEmpty() == null){
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
                            text = stringResource(id = R.string.no_routines) + "\n"
                                    + "Error: " + routinesState.message.toString() + "\n" + "\n"
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
        else{
            Box(
                modifier = Modifier
                    .padding(innerPadding ?: PaddingValues())
            ) {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = { routinesVM.fetchRoutines()},
                    indicator = { state, refreshTrigger ->
                        SwipeRefreshIndicator(
                            state = state,
                            refreshTriggerDistance = refreshTrigger,
                            contentColor = MaterialTheme.colorScheme.background,
                            backgroundColor = MaterialTheme.colorScheme.secondary
                        )
                    }
                ) {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(150.dp),
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
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
            }
        }

    }

}

