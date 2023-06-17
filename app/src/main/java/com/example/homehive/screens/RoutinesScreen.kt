package com.example.homehive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.homehive.boxes.RoutineBox
import com.example.homehive.viewmodels.RoutineVM
import com.example.homehive.viewmodels.RoutinesVM

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoutinesScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
    routinesVM: RoutinesVM
) {
    routinesVM.fetchRoutines()
    val routinesState by routinesVM.uiState.collectAsState()
    val routinesViewModelMap = remember { mutableMapOf<String, RoutineVM>() }
    var popupControl by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(innerPadding ?: PaddingValues())
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

