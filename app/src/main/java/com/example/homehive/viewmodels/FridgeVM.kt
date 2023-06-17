package com.example.homehive.viewmodels

import android.bluetooth.BluetoothClass.Device
import androidx.lifecycle.ViewModel
import com.example.homehive.states.FridgeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Faltaria hacer la seccion de llamados a la API para coordinar actualizacion tanto local como remota

class FridgeVM(
    deviceID : String?,
    initialTemperature: Int?,
    initialFreezerTemperature: Int?,
    initialMode: String?,
    val devicesVM: DevicesVM
) : ViewModel() {
    // Genero un proxy para que el estado pase a ser read only y no se puedan hacer accesos directos
    private val _uiState = MutableStateFlow(FridgeUIState(
        id = deviceID ?: "",
        temperature = initialTemperature ?: -2,
        freezerTemperature = initialFreezerTemperature ?: -12,
        mode = initialMode ?: "party"
    ))
    val uiState: StateFlow<FridgeUIState> = _uiState.asStateFlow()

    fun setFridgeTemperature( newTemperature : Int ){
        _uiState.update{currentState ->
            currentState.copy(temperature = newTemperature)
        }
        devicesVM.editADevice(uiState.value.id, "setTemperature", listOf(newTemperature))
    }

    fun setFreezerTemperature( newTemperature : Int ){
        _uiState.update{currentState ->
            currentState.copy(freezerTemperature =  newTemperature)
        }
        devicesVM.editADevice(uiState.value.id, "setFreezerTemperature", listOf(newTemperature))
    }

    // No me acuerdo si era un String o no
    fun setMode( newMode : String ){
        _uiState.update{currentState ->
            currentState.copy(mode =  newMode)
        }
        devicesVM.editADevice(uiState.value.id, "setMode", listOf(newMode))
    }
}
