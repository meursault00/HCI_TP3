package com.example.homehive.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homehive.states.BlindsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BlindsVM(
    deviceID: String?,
    initialStatus: String?,
    level : Int?,
    initialPosition: Int?,
    val devicesVM: DevicesVM
) : ViewModel() {
    private val _uiState = MutableStateFlow(BlindsUIState(
        id = deviceID ?: "",
        status = initialStatus ?: "",
        level = level ?: 0,
        position = initialPosition ?: 0
    ))
    val uiState: StateFlow<BlindsUIState> = _uiState.asStateFlow()

    fun toggleBlinds() {
        _uiState.update { currentState ->
            val newStatus = if (currentState.status == "opening") "closing" else "opening"
            currentState.copy(status = newStatus)
        }
        val action = if (uiState.value.status == "opening") "open" else "close"
        devicesVM.editADevice(uiState.value.id, action, listOf())
    }


    fun setPosition( newPosition : Int ){
        _uiState.update{currentState ->
            currentState.copy(position = newPosition)
        }
        devicesVM.editADevice(uiState.value.id, "setLevel", listOf(newPosition))
    }
}