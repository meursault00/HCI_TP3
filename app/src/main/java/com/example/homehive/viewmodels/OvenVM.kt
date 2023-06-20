package com.example.homehive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homehive.library.HistoryStack
import com.example.homehive.states.OvenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OvenVM(
    deviceID: String?,
    deviceName: String?,
    power: String?,
    ovenTemperature: Int?,
    grillMode: String?,
    heatMode: String?,
    convectionMode: String?,
    val devicesVM: DevicesVM
) : ViewModel() {
    private val _uiState = MutableStateFlow(OvenUIState(
        id = deviceID ?: "",
        name = deviceName ?: "",
        power = power ?: "",
        ovenTemperature = ovenTemperature ?: 0,
        grillMode = grillMode ?: "",
        heatMode = heatMode ?: "",
        convectionMode = convectionMode ?: ""
    ))

    val uiState: StateFlow<OvenUIState> = _uiState.asStateFlow()

    fun sync(){
        viewModelScope.launch {
            val updatedDevice = devicesVM.fetchADevice(id = uiState.value.id)
            _uiState.update{currentState ->
                currentState.copy(
                    power = updatedDevice.result?.state?.status ?: "",
                    ovenTemperature = updatedDevice.result?.state?.temperature ?: 0,
                    grillMode = updatedDevice.result?.state?.grill ?: "",
                    heatMode = updatedDevice.result?.state?.heat ?: "",
                    convectionMode = updatedDevice.result?.state?.convection ?: "",
                )
            }
        }
    }
    fun setPower(newPower: Boolean) {
        val deviceId = uiState.value.id
        val command = if (newPower) "turnOn" else "turnOff"
        val powerState = if (newPower) "on" else "off"

        devicesVM.editADevice(deviceId, command, listOf())

        _uiState.update { currentState ->
            currentState.copy(power = powerState)
        }
        HistoryStack.push("${uiState.value.name}: turned power $powerState")

    }

    fun togglePower() {
        val currentPower = uiState.value.power
        val newPower = currentPower == "off"

        setPower(newPower)
        HistoryStack.push("${uiState.value.name}: turned power $newPower")

    }


    // fun togglePower() {
    //      _uiState.update { currentState ->
    //           val newPower = if (currentState.power == "on") "off" else "on"
    //            currentState.copy(power = newPower)
    //       }
    //        devicesVM.editADevice(uiState.value.id, "turnOn", listOf())
    // }

    fun setOvenTemperature( newTemperature : Int ){
        _uiState.update{currentState ->
            currentState.copy(ovenTemperature = newTemperature)
        }
        devicesVM.editADevice(uiState.value.id, "setTemperature", listOf(newTemperature))
        HistoryStack.push("${uiState.value.name}: set temperature to $newTemperature")

    }

    fun setHeatMode( newMode : String ){
        _uiState.update{currentState ->
            currentState.copy(heatMode =  newMode)
        }
        devicesVM.editADevice(uiState.value.id, "setHeat", listOf(newMode))
        HistoryStack.push("${uiState.value.name}: set heat mode to $newMode")

    }

    fun setGrillMode( newMode : String ){
        _uiState.update{currentState ->
            currentState.copy(grillMode =  newMode)
        }
        devicesVM.editADevice(uiState.value.id, "setGrill", listOf(newMode))
        HistoryStack.push("${uiState.value.name}: set grill mode to $newMode")

    }

    fun setConvectionMode( newMode : String ){
        _uiState.update{currentState ->
            currentState.copy(convectionMode =  newMode)
        }
        devicesVM.editADevice(uiState.value.id, "setConvection", listOf(newMode))
        HistoryStack.push("${uiState.value.name}: set convection mode to $newMode")

    }
}