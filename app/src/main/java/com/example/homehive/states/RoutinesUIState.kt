package com.example.homehive.states

import com.example.homehive.network.routineModels.NetworkRoutinesList

data class RoutinesUIState(
    val routines: NetworkRoutinesList? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

val RoutinesUIState.hasError: Boolean get() = message != null