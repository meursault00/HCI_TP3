package com.example.homehive.viewmodels

import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homehive.R
import com.example.homehive.UpdateMap
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

    fun closeBlinds() {
        Log.d("Putaku", "closeBlinds called")
        _uiState.update { currentState ->
            currentState.copy(status = "closing")
        }
        devicesVM.editADevice(uiState.value.id, "close", listOf())
        polling()
        HistoryStack.push("${uiState.value.name}: toggled to close")
    }

    fun openBlinds() {
        Log.d("Putaku", "openBlinds called")
        _uiState.update { currentState ->
            currentState.copy(status = "opening")
        }
        devicesVM.editADevice(uiState.value.id, "open", listOf())
        polling()
        HistoryStack.push("${uiState.value.name}: toggled to open")
    }

    fun toggleBlinds() {
        Log.d("Putaku before change", uiState.value.status)
        if (uiState.value.status == "opening" || uiState.value.status == "opened") {
            closeBlinds()
        } else {
            openBlinds()
        }
        Log.d("Putaku after change", uiState.value.status)
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

    fun conditionalRecomposition(){
        if (UpdateMap.map[uiState.value.id] == true){
            Log.d("debug","Syncing ${uiState.value.name}")
            sync()
            UpdateMap.map[uiState.value.id] = false
        }
    }
}