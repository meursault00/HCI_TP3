package com.example.homehive.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homehive.network.routineModels.NetworkActions
import com.example.homehive.states.RoutineUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoutineVM(
    routineID: String?,
    routineName: String?,
    routineActions: ArrayList<NetworkActions>,
    val routinesVM: RoutinesVM
) : ViewModel()  {
    private val _uiState = MutableStateFlow(
        RoutineUIState(
            id = routineID ?: "",
            name = routineName ?: "",
            actions = routineActions
        )
    )
    val uiState: StateFlow<RoutineUIState> = _uiState.asStateFlow()

    fun executeRoutine(){
        // routinesVM.executeRoutine(routineID);
    }
}