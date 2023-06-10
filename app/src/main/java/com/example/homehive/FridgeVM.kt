package com.example.homehive

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Faltaria hacer la seccion de llamados a la API para coordinar actualizacion tanto local como remota

class FridgeVM : ViewModel() {
    // Genero un proxy para que el estado pase a ser read only y no se puedan hacer accesos directos
    private val _uiState = MutableStateFlow(FridgeUIState())
    val uiState : StateFlow<FridgeUIState> = _uiState.asStateFlow()

    fun togglePower(){
        _uiState.update{currentState ->
            currentState.copy(power = (!uiState.value.power))
        }
    }

    fun setFridgeTemperature( newTemperature : Int ){
        _uiState.update{currentState ->
            currentState.copy(fridgeTemperature = newTemperature)
        }
    }

    fun setFreezerTemperature( newTemperature : Int ){
        _uiState.update{currentState ->
            currentState.copy(freezerTemperature =  newTemperature)
        }
    }

    // No me acuerdo si era un String o no
    fun setFreezerTemperature( newMode : String ){
        _uiState.update{currentState ->
            currentState.copy(mode =  newMode)
        }
    }
}
