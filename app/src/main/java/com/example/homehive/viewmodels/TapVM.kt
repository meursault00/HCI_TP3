package com.example.homehive.viewmodels


import androidx.lifecycle.ViewModel
import com.example.homehive.states.TapUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TapVM : ViewModel() {
    private val _uiState = MutableStateFlow(TapUIState())
    val uiState : StateFlow<TapUIState> = _uiState.asStateFlow()

    fun setOpen(){
        _uiState.update{currentState ->
            currentState.copy(state = "open")
        }
    }
    fun setClose(){
        _uiState.update{currentState ->
            currentState.copy(state = "closed")
        }
    }

    fun dispense( amount : Int, unit : String ){
        _uiState.update{currentState ->
            currentState.copy(state = "open")
        }

        //el amount y unit se pasan directo a api, no hay variables locales para estas
    }
}