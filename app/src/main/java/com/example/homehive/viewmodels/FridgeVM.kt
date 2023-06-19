package com.example.homehive.viewmodels

import android.bluetooth.BluetoothClass.Device
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homehive.states.FridgeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Faltaria hacer la seccion de llamados a la API para coordinar actualizacion tanto local como remota

class FridgeVM(
    deviceID : String?,
    deviceName: String?,
    initialTemperature: Int?,
    initialFreezerTemperature: Int?,
    initialMode: String?,
    private val devicesVM: DevicesVM
) : ViewModel() {
    // Genero un proxy para que el estado pase a ser read only y no se puedan hacer accesos directos
    private val _uiState = MutableStateFlow(FridgeUIState(
        id = deviceID ?: "",
        name = deviceName ?: "",
        temperature = initialTemperature ?: -2,
        freezerTemperature = initialFreezerTemperature ?: -12,
        mode = initialMode ?: "party"
    ))
    val uiState: StateFlow<FridgeUIState> = _uiState.asStateFlow()

    fun sync(){
        viewModelScope.launch {
            val updatedDevice = devicesVM.fetchADevice(id = uiState.value.id)
            _uiState.update{currentState ->
                currentState.copy(
                    temperature = updatedDevice.result?.state?.temperature ?: 0,
                    freezerTemperature = updatedDevice.result?.state?.freezerTemperature ?: 0,
                    mode = updatedDevice.result?.state?.mode ?: "",
                )
            }
        }
    }

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
