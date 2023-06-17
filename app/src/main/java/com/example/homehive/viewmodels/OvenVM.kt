package com.example.homehive.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homehive.states.OvenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OvenVM(
    deviceID: String?,
    power: String?,
    ovenTemperature: Int?,
    grillMode: String?,
    heatMode: String?,
    convectionMode: String?,
    val devicesVM: DevicesVM
) : ViewModel() {
    private val _uiState = MutableStateFlow(OvenUIState(
        id = deviceID ?: "",
        power = power ?: "",
        ovenTemperature = ovenTemperature ?: 0,
        grillMode = grillMode ?: "",
        heatMode = heatMode ?: "",
        convectionMode = convectionMode ?: ""
    ))
    val uiState: StateFlow<OvenUIState> = _uiState.asStateFlow()

    fun togglePower() {
        _uiState.update { currentState ->
            val newPower = if (currentState.power == "on") "off" else "on"
            currentState.copy(power = newPower)
        }
        devicesVM.editADevice(uiState.value.id, "togglePower", listOf())
    }

    fun setOvenTemperature( newTemperature : Int ){
        _uiState.update{currentState ->
            currentState.copy(ovenTemperature = newTemperature)
        }
        devicesVM.editADevice(uiState.value.id, "setTemperature", listOf(newTemperature))
    }

    fun setHeatMode( newMode : String ){
        _uiState.update{currentState ->
            currentState.copy(heatMode =  newMode)
        }
        devicesVM.editADevice(uiState.value.id, "setMode", listOf(newMode))
    }

    fun setGrillMode( newMode : String ){
        _uiState.update{currentState ->
            currentState.copy(grillMode =  newMode)
        }
        devicesVM.editADevice(uiState.value.id, "setMode", listOf(newMode))
    }

    fun setConvectionMode( newMode : String ){
        _uiState.update{currentState ->
            currentState.copy(convectionMode =  newMode)
        }
        devicesVM.editADevice(uiState.value.id, "setMode", listOf(newMode))
    }
}