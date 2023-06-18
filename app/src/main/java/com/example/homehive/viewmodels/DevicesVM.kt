package com.example.homehive.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homehive.network.ArrayValue
import com.example.homehive.network.RetrofitClient
import com.example.homehive.states.DevicesUIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DevicesVM : ViewModel() {

    private val _uiState = MutableStateFlow(DevicesUIState())
    val uiState: StateFlow<DevicesUIState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null

    fun dismissMessage() {
        _uiState.update { it.copy(message = null) }
    }


    fun fetchDevices() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService?.getAllDevices() ?: throw Exception("API Service is null")
            }.onSuccess { response ->
                delay(2000)
                _uiState.update { it.copy(
                    devices = response.body(),
                    isLoading = false
                ) }
            }.onFailure { e ->
                Log.d("homehivestatus", "Failure: Inside Failure Block \nError message  ${e.message}")
                delay(2000)
                _uiState.update { it.copy(
                    message = e.message,
                    isLoading = false
                ) }
            }
        }
    }
    fun fetchADevice(id : String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService?.getADevice(id) ?: throw Exception("API Service is null")
            }.onSuccess { response ->
                Log.d("homehivestatus", "Success: Inside Success Block")
                _uiState.update { it.copy(
                    // Deberia retornar el objeto del device
                    // devices = response.body(),
                    isLoading = false
                ) }
            }.onFailure { e ->
                Log.d("homehivestatus", "Failure: Inside Failure Block \nError message  ${e.message}")
                _uiState.update { it.copy(
                    message = e.message,
                    isLoading = false
                ) }
            }
        }
    }


    //fun editADevice(id, action, params)
    fun editADevice(id: String, action: String, params: List<Any>) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                when {
                    // Acciones que no requieren parametros
                    action in listOf("play", "stop", "pause", "resume", "nextSong", "previousSong", "getPlaylist", "open", "close",
                        "turnOn", "turnOff") -> {
                        Log.d("homehivestatus", "No Parameters")
                        apiService?.updateADeviceNull(id, action, params)
                    }

                    // Acciones que requieren parametros String
                    action in listOf("setGenre", "setHeat", "setGrill", "setConvection", "setMode") -> {
                        Log.d("homehivestatus", "String Parameters")
                        val stringParams = params.mapNotNull { it as? String }
                        apiService?.updateADeviceString(id, action, stringParams)
                    }

                    // Acciones que requieren parametros Int
                    action in listOf("setVolume", "setLevel", "setTemperature", "setFreezerTemperature") -> {
                        Log.d("homehivestatus", "Int Parameters")
                        val intParams = params.mapNotNull { it as? Int }
                        apiService?.updateADeviceInt(id, action, intParams)
                    }

                    // Acciones que no requieren parametros mixtos
                    action == "dispense" -> {
                        Log.d("homehivestatus", "Mixed Parameters")
                        apiService?.updateADeviceMixed(id, action, params)
                    }

                    else -> {
                        Log.d("homehivestatus", "Error: unsupported action")
                        throw IllegalArgumentException("Unsupported action: $action")
                    }
                } ?: throw Exception("API Service is null")
            }.onSuccess { response ->
                Log.d("homehivestatus", "Apí Call was Successful")
                Log.d("debug", response)
                // Opcion 1 : llamar a la fetchAllDevices que va a sobrescribir la lista de dispositivos pero esta vez con el update que se hizo
                // en esta misma funcion
                // Opcion 2 : Hacer un find sobre la lista con el ID del dispositivo y actualizarlo localmente, esto puede ser bastante tedioso
                // en la version web lo haciamos pero es bastante paja
                // Es necesario? El UIState no se carrea el update local?
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure { e ->
                Log.d("debug", "Api Call was Unsuccessful \nError message  ${e.message}")
                _uiState.update { it.copy(message = e.message, isLoading = false) }
            }
        }
    }
}