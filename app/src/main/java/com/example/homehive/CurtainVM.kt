package com.example.homehive

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CurtainVM : ViewModel() {
    private val _uiState = MutableStateFlow(CurtainUIState())
    val uiState : StateFlow<CurtainUIState> = _uiState.asStateFlow()

    fun setOpen(){
        _uiState.update{currentState ->
            currentState.copy(state = "opening")
        }
    }
    fun setClose(){
        _uiState.update{currentState ->
            currentState.copy(state = "closing")
        }
    }

    fun setPosition( newPosition : Int ){
        _uiState.update{currentState ->
            currentState.copy(position = newPosition)
        }
    }
}