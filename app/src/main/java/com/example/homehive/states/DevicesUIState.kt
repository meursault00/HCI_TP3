package com.example.homehive.states

import com.example.homehive.network.deviceModels.NetworkDevicesList

data class DevicesUIState(
    val devices: NetworkDevicesList? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

val DevicesUIState.hasError: Boolean get() = message != null