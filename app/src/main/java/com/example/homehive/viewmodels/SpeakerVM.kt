package com.example.homehive.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homehive.UpdateMap
import com.example.homehive.library.HistoryStack
import com.example.homehive.network.deviceModels.NetworkPlaylist
import com.example.homehive.network.deviceModels.NetworkSong
import com.example.homehive.states.SpeakerUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpeakerVM(
    deviceID : String?,
    deviceName: String?,
    initialStatus: String?,
    initialVolume: Int?,
    initialSong: NetworkSong?,
    initialGenre: String?,
    initialPlaylist : NetworkPlaylist,
    val devicesVM: DevicesVM
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        SpeakerUIState(
            id = deviceID ?: "",
            name = deviceName ?: "",
            status = initialStatus ?: "playing",
            volume = initialVolume ?: 0,
            song = initialSong ?: NetworkSong(),
            genre = initialGenre ?: "pop",
            playlist = initialPlaylist
        )
    )
    val uiState: StateFlow<SpeakerUIState> = _uiState.asStateFlow()

    @Volatile
    var isLoopActive = false

    fun sync(){
        viewModelScope.launch {
            val updatedDevice = devicesVM.fetchADevice(id = uiState.value.id)
            _uiState.update{currentState ->
                currentState.copy(
                    status = updatedDevice.result?.state?.status ?: "",
                    volume = updatedDevice.result?.state?.volume ?: 0,
                    song = updatedDevice.result?.state?.song ?: NetworkSong(),
                    genre = updatedDevice.result?.state?.genre ?: "",
                )
            }
        }
    }

    fun incrementVolume(){
        _uiState.update{currentState ->
            currentState.copy(volume = (uiState.value.volume + 1))
        }
        devicesVM.editADevice(uiState.value.id, "setVolume", listOf(uiState.value.volume + 1))
        HistoryStack.push("${uiState.value.name}: increased volume by 1")


    }
    fun decrementVolume(){
        _uiState.update{currentState ->
            currentState.copy(volume = (uiState.value.volume - 1))
        }
        devicesVM.editADevice(uiState.value.id, "setVolume", listOf(uiState.value.volume - 1))
        HistoryStack.push("${uiState.value.name}: decreased volume by 1")

    }

    fun setVolume( newVolume : Int ){
        _uiState.update{currentState ->
            currentState.copy(volume = newVolume)
        }
        devicesVM.editADevice(uiState.value.id, "setVolume", listOf(newVolume))
        HistoryStack.push("${uiState.value.name}: set volume to $newVolume")

    }

    fun play(){
        _uiState.update{currentState ->
            currentState.copy(status = "resume")
        }
        devicesVM.editADevice(uiState.value.id, "play", listOf())
        HistoryStack.push("${uiState.value.name}: started playing")

        //polling
        isLoopActive = true
        polling()
    }
    fun resume(){
        _uiState.update{currentState ->
            currentState.copy(status = "playing")
        }
        devicesVM.editADevice(uiState.value.id, "resume", listOf())
        HistoryStack.push("${uiState.value.name}: resumed playing")

        //polling
        isLoopActive = true
        polling()
    }
    fun pause(){
        _uiState.update{currentState ->
            currentState.copy(status = "paused")
        }
        devicesVM.editADevice(uiState.value.id, "pause", listOf())
        HistoryStack.push("${uiState.value.name}: paused playing")

        //polling
        isLoopActive = false
    }
    fun stop(){
        _uiState.update{currentState ->
            currentState.copy(status = "stopped", song = NetworkSong())
        }
        devicesVM.editADevice(uiState.value.id, "stop", listOf())
        HistoryStack.push("${uiState.value.name}: stopped playing")

        //polling
        isLoopActive = false
    }

    fun nextSong(){

        //inicialmente cargamos con loading
        _uiState.update{currentState ->
            currentState.copy(song = NetworkSong())      //carga con loading.. todos los strings
        }

        //pasamos a proxima cancion en api
        devicesVM.editADevice(uiState.value.id, "nextSong", listOf())
        HistoryStack.push("${uiState.value.name}: played next song")

        //actualizamos estado local con cancion que aparece en api

//            sync()

//        viewModelScope.launch {
//            val updatedDevice = devicesVM.fetchADevice(id = uiState.value.id)
//            _uiState.update{currentState ->
//                currentState.copy(
//                    song = updatedDevice.result?.state?.song ?: NetworkSong()
//                )
//            }
//        }

//        _uiState.update{currentState ->
//            currentState.copy(song = NetworkSong())      //?????????
//        }
    }

    fun previousSong(){
        _uiState.update{currentState ->
            currentState.copy(song = NetworkSong())
        }
        devicesVM.editADevice(uiState.value.id, "previousSong", listOf())
        HistoryStack.push("${uiState.value.name}: played previous song")

    }

    fun setGenre(newGenre : String){
        _uiState.update{currentState ->
            currentState.copy(genre = newGenre)
        }
        Log.d("debug", newGenre)
        devicesVM.editADevice(uiState.value.id, "setGenre", listOf(newGenre))
        Thread {
            Thread.sleep(100)
            _uiState.update { currentState ->
                currentState.copy(playlist = devicesVM.fetchPlaylist(uiState.value.id))
            }
        }.start()

        HistoryStack.push("${uiState.value.name}: set genre to $newGenre")
    }

    fun checkPolling(){
        if ( uiState.value.status == "playing" ){
            isLoopActive = true
            polling()
        }
    }

    fun polling() {
        val thread = Thread {
            while (isLoopActive) {
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