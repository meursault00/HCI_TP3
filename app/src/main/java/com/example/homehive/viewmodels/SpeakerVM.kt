package com.example.homehive.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homehive.network.deviceModels.NetworkSong
import com.example.homehive.states.SpeakerUIState
import com.example.homehive.states.TapUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpeakerVM(
    deviceID : String?,
    initialStatus: String?,
    initialVolume: Int?,
    initialSong: NetworkSong?,
    initialGenre: String?,
    val devicesVM: DevicesVM
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        SpeakerUIState(
            id = deviceID ?: "",
            status = initialStatus ?: "closed",
            volume = initialVolume ?: 0,
            song = initialSong ?: NetworkSong(),
            genre = initialGenre ?: "idk",
        )
    )
    val uiState: StateFlow<SpeakerUIState> = _uiState.asStateFlow()

    fun incrementVolume(){
        _uiState.update{currentState ->
            currentState.copy(volume = (uiState.value.volume + 1))
        }
        devicesVM.editADevice(uiState.value.id, "setVolume", listOf(uiState.value.volume + 1))
    }
    fun decrementVolume(){
        _uiState.update{currentState ->
            currentState.copy(volume = (uiState.value.volume - 1))
        }
        devicesVM.editADevice(uiState.value.id, "setVolume", listOf(uiState.value.volume - 1))
    }

    fun setVolume( newVolume : Int ){
        _uiState.update{currentState ->
            currentState.copy(volume = newVolume)
        }
        devicesVM.editADevice(uiState.value.id, "setVolume", listOf(newVolume))
    }

    fun play(){
        _uiState.update{currentState ->
            currentState.copy(status = "playing")
        }
        devicesVM.editADevice(uiState.value.id, "play", listOf())
    }
    fun resume(){
        _uiState.update{currentState ->
            currentState.copy(status = "playing")
        }
        devicesVM.editADevice(uiState.value.id, "resume", listOf())
    }
    fun pause(){
        _uiState.update{currentState ->
            currentState.copy(status = "paused")
        }
        devicesVM.editADevice(uiState.value.id, "pause", listOf())
    }
    fun stop(){
        _uiState.update{currentState ->
            currentState.copy(status = "stopped")
        }
        devicesVM.editADevice(uiState.value.id, "stop", listOf())
    }

    fun nextSong(){
        _uiState.update{currentState ->
            currentState.copy(song = NetworkSong())      //?????????
        }
        devicesVM.editADevice(uiState.value.id, "nextSong", listOf())
    }

    fun previousSong(){
        _uiState.update{currentState ->
            currentState.copy(song = NetworkSong())      //?????????
        }
        devicesVM.editADevice(uiState.value.id, "previousSong", listOf())
    }

    fun setGenre(newGenre : String){
        _uiState.update{currentState ->
            currentState.copy(genre = newGenre)
        }
        devicesVM.editADevice(uiState.value.id, "setGenre", listOf(newGenre))
    }

}