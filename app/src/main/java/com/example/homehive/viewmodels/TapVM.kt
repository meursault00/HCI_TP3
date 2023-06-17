package com.example.homehive.viewmodels


import androidx.lifecycle.ViewModel
import com.example.homehive.states.FridgeUIState
import com.example.homehive.states.TapUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TapVM(
    deviceID : String?,
    initialState: String?,
    val devicesVM: DevicesVM
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        TapUIState(
        id = deviceID ?: "",
        state = initialState ?: "closed",
    )
    )
    val uiState: StateFlow<TapUIState> = _uiState.asStateFlow()

    fun setOpen(){
        _uiState.update{currentState ->
            currentState.copy(state = "open")
        }
        devicesVM.editADevice(uiState.value.id, "open", listOf())
    }
    fun setClose(){
        _uiState.update{currentState ->
            currentState.copy(state = "closed")
        }
        devicesVM.editADevice(uiState.value.id, "close", listOf())
    }

    fun dispense( amount : Int, unit : String ){
        _uiState.update{currentState ->
            currentState.copy(state = "open")
        }
        devicesVM.editADevice(uiState.value.id, "dispense", listOf(amount, unit))

        //el amount y unit se pasan directo a api, no hay variables locales para estas
    }
}