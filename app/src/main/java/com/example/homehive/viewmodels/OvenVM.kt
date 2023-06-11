package com.example.homehive.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homehive.states.OvenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OvenVM : ViewModel() {
    private val _uiState = MutableStateFlow(OvenUIState())
    val uiState : StateFlow<OvenUIState> = _uiState.asStateFlow()

    fun togglePower(){
        _uiState.update{currentState ->
            currentState.copy(power = (!uiState.value.power))
        }
    }

    fun setOvenTemperature( newTemperature : Int ){
        _uiState.update{currentState ->
            currentState.copy(ovenTemperature = newTemperature)
        }
    }

    fun setheatMode( newHeatMode : String ){
        _uiState.update{currentState ->
            currentState.copy(heatMode =  newHeatMode)
        }
    }

    fun setGrillMode( newGrillMode : String ){
        _uiState.update{currentState ->
            currentState.copy(grillMode =  newGrillMode)
        }
    }

    fun setConvectionMode( newConvectionMode : String ){
        _uiState.update{currentState ->
            currentState.copy(convectionMode =  newConvectionMode)
        }
    }
}