package com.example.homehive.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homehive.network.deviceModels.NetworkSong
import com.example.homehive.states.FridgeUIState
import com.example.homehive.states.TapUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TapVM(
    deviceID : String?,
    deviceName: String?,
    initialStatus: String?,
    val devicesVM: DevicesVM
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        TapUIState(
            id = deviceID ?: "",
            name = deviceName ?: "",
            status = initialStatus ?: "closed",
    )
    )
    val uiState: StateFlow<TapUIState> = _uiState.asStateFlow()

    fun sync(){
        viewModelScope.launch {
            val updatedDevice = devicesVM.fetchADevice(id = uiState.value.id)
            _uiState.update{currentState ->
                currentState.copy(
                    status = updatedDevice.result?.state?.status ?: "",
                )
            }
        }
    }

    fun setOpen(){
        _uiState.update{currentState ->
            currentState.copy(status = "opened")
        }
        devicesVM.editADevice(uiState.value.id, "open", listOf())
    }
    fun setClose(){
        _uiState.update{currentState ->
            currentState.copy(status = "closed")
        }
        devicesVM.editADevice(uiState.value.id, "close", listOf())
    }

    fun dispense( amount : Int, unit : String ){
        _uiState.update{currentState ->
            currentState.copy(status = "opened")
        }
        devicesVM.editADevice(uiState.value.id, "dispense", listOf(amount, unit))

        //el amount y unit se pasan directo a api, no hay variables locales para estas
    }

    fun polling() {
        val thread = Thread {
            while (uiState.value.status == "opening" || uiState.value.status == "closing" ) {
                Log.d("polling", "${_uiState.value}")
                Thread.sleep(1000)
                sync()
                Log.d("polling", "${_uiState.value}")

            }
        }
        thread.start()
    }
}