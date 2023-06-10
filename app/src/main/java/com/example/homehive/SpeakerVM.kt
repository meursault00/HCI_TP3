package com.example.homehive

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpeakerVM : ViewModel() {
    private val _uiState = MutableStateFlow(SpeakerUIState())
    val uiState : StateFlow<SpeakerUIState> = _uiState.asStateFlow()

    fun incrementVolume(){
        _uiState.update{currentState ->
            currentState.copy(volume = (uiState.value.volume + 1))
        }
    }
    fun decrementVolume(){
        _uiState.update{currentState ->
            currentState.copy(volume = (uiState.value.volume + 1))
        }
    }

    fun setVolume( newVolume : Int ){
        _uiState.update{currentState ->
            currentState.copy(volume = newVolume)
        }
    }

    fun play(){
        _uiState.update{currentState ->
            currentState.copy(state = "playing")
        }
    }
    fun pause(){
        _uiState.update{currentState ->
            currentState.copy(state = "paused")
        }
    }
    fun stop(){
        _uiState.update{currentState ->
            currentState.copy(state = "stopped")
        }
    }

    fun nextSong(){
        _uiState.update{currentState ->
            currentState.copy(song = "nextsong")      //?????????
        }
    }

    fun previousSong(){
        _uiState.update{currentState ->
            currentState.copy(song = "nextsong")      //?????????
        }
    }

    fun setGenre(newGenre : String){
        _uiState.update{currentState ->
            currentState.copy(genre = newGenre)
        }
    }

}