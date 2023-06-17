package com.example.homehive.states

import com.example.homehive.network.routineModels.NetworkActions

data class RoutineUIState(
    val id: String,
    val name: String,
    val actions: ArrayList<NetworkActions>
)

data class ActionUIState(
    val deviceId: String,
    val deviceName: String,
    val actionName: String,
    val params: List<String>
)
