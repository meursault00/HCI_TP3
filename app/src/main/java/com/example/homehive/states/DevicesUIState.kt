package com.example.homehive.states

import com.example.homehive.network.models.NetworkDevicesList

data class DevicesUIState(
    val devices: NetworkDevicesList? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

val DevicesUIState.hasError: Boolean get() = message != null