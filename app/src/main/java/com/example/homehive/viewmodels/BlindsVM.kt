package com.example.homehive.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homehive.states.BlindsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BlindsVM : ViewModel() {
    private val _uiState = MutableStateFlow(BlindsUIState())
    val uiState : StateFlow<BlindsUIState> = _uiState.asStateFlow()

    fun setOpen(){
        _uiState.update{currentState ->
            currentState.copy(status = "opening")
        }
    }
    fun setClose(){
        _uiState.update{currentState ->
            currentState.copy(status = "closing")
        }
    }

    fun setPosition( newPosition : Int ){
        _uiState.update{currentState ->
            currentState.copy(position = newPosition)
        }
    }
}