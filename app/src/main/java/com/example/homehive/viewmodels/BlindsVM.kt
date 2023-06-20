package com.example.homehive.viewmodels

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homehive.R
import com.example.homehive.library.HistoryStack
import com.example.homehive.states.BlindsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BlindsVM(
    deviceID: String?,
    deviceName: String?,
    initialStatus: String?,
    level : Int?,
    initialPosition: Int?,
    val devicesVM: DevicesVM
) : ViewModel() {
    private val _uiState = MutableStateFlow(BlindsUIState(
        id = deviceID ?: "",
        name = deviceName ?: "",
        status = initialStatus ?: "",
        level = level ?: 0,
        position = initialPosition ?: 0
    ))
    val uiState: StateFlow<BlindsUIState> = _uiState.asStateFlow()

    fun sync(){
        viewModelScope.launch {
            val updatedDevice = devicesVM.fetchADevice(id = uiState.value.id)
            _uiState.update{currentState ->
                currentState.copy(
                    status = updatedDevice.result?.state?.status ?: "",
                    level = updatedDevice.result?.state?.level ?: 0,
                    position = updatedDevice.result?.state?.currentLevel ?: 0
                )
            }
        }
    }

    fun toggleBlinds() {
        _uiState.update { currentState ->
            val newStatus = if (currentState.status == "opening") "closing" else "opening"
            currentState.copy(status = newStatus)
        }
        val action = if (uiState.value.status == "opening") "open" else "close"
        devicesVM.editADevice(uiState.value.id, action, listOf())
        polling()
        HistoryStack.push("${uiState.value.name}: toggled to $action")
    }


    fun setPosition( newPosition : Int ){
        _uiState.update{currentState ->
            currentState.copy(position = newPosition)
        }
        devicesVM.editADevice(uiState.value.id, "setLevel", listOf(newPosition))
        polling()
        HistoryStack.push("${uiState.value.name}: set position to $newPosition")

    }

    fun checkPolling(){
        if ( uiState.value.status == "opening" || uiState.value.status == "closing" )
            polling()
    }

    fun polling() {
        val thread = Thread {
            while (uiState.value.status == "opening" || uiState.value.status == "closing" ) {
                Thread.sleep(1000)
                sync()
            }
        }
        thread.start()
    }
}