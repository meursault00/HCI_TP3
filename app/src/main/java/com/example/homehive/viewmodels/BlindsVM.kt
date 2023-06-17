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

    fun setOpen(){
        _uiState.update{currentState ->
            currentState.copy(status = "opening")
        }
        devicesVM.editADevice(uiState.value.id, "open", listOf())

    }
    fun setClose(){
        _uiState.update{currentState ->
            currentState.copy(status = "closing")
        }
        devicesVM.editADevice(uiState.value.id, "close", listOf())
    }

    fun setPosition( newPosition : Int ){
        _uiState.update{currentState ->
            currentState.copy(position = newPosition)
        }
        devicesVM.editADevice(uiState.value.id, "setLevel", listOf(newPosition))
    }
}