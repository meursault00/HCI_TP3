package com.example.homehive.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homehive.network.RetrofitClient
import com.example.homehive.network.deviceModels.NetworkPlaylist
import com.example.homehive.network.deviceModels.NetworkUnaryDevice
import com.example.homehive.states.DevicesUIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class DevicesVM : ViewModel() {

    private val _uiState = MutableStateFlow(DevicesUIState())
    val uiState: StateFlow<DevicesUIState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null

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
    fun fetchADevice(id: String): NetworkUnaryDevice {
        fetchJob?.cancel()
        _uiState.update { it.copy(isLoading = true) }

        var toReturn: NetworkUnaryDevice? = null // Declare the variable to hold the result

        runBlocking {
            runCatching {
                val apiService = RetrofitClient.getApiService()
                val response = apiService?.getADevice(id) ?: throw Exception("API Service is null")
                response.body() ?: throw Exception("Empty response body")
            }.onSuccess { body ->
                _uiState.update { it.copy(isLoading = false) }
                toReturn = body // Assign the result to the variable
            }.onFailure { e ->
                _uiState.update { it.copy(message = e.message, isLoading = false) }
            }
        }

        return toReturn ?: NetworkUnaryDevice() // Return the result or an empty NetworkResult
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
                        apiService?.updateADeviceNull(id, action, params)
                    }

                    // Acciones que requieren parametros String
                    action in listOf("setGenre", "setHeat", "setGrill", "setConvection", "setMode") -> {
                        val stringParams = params.mapNotNull { it as? String }
                        apiService?.updateADeviceString(id, action, stringParams)
                    }

                    // Acciones que requieren parametros Int
                    action in listOf("setVolume", "setLevel", "setTemperature", "setFreezerTemperature") -> {
                        val intParams = params.mapNotNull { it as? Int }
                        apiService?.updateADeviceInt(id, action, intParams)
                    }

                    // Acciones que no requieren parametros mixtos
                    action == "dispense" -> {
                        apiService?.updateADeviceMixed(id, action, params)
                    }

                    else -> {
                        throw IllegalArgumentException("Unsupported action: $action")
                    }
                } ?: throw Exception("API Service is null")
            }.onSuccess { response ->
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure { e ->
                _uiState.update { it.copy(message = e.message, isLoading = false) }
            }
        }
    }

    fun fetchPlaylist(id: String): NetworkPlaylist{
        fetchJob?.cancel()
        _uiState.update { it.copy(isLoading = true) }

        var toReturn: NetworkPlaylist? = null // Declare the variable to hold the result

        runBlocking {
            runCatching {
                val apiService = RetrofitClient.getApiService()
                val response = apiService?.getPlaylist(id) ?: throw Exception("API Service is null")
                response.body() ?: throw Exception("Empty response body")
            }.onSuccess { body ->
                _uiState.update { it.copy(isLoading = false) }
                toReturn = body // Assign the result to the variable
                Log.d("fetchADeviceDebug", "Response: ${body.result}") // Add this line to log the response
            }.onFailure { e ->
                _uiState.update { it.copy(message = e.message, isLoading = false) }
            }
        }

        return toReturn ?: NetworkPlaylist() // Return the result or an empty NetworkResult
    }

    fun qDevices(): Int {
        return _uiState.value.devices?.result?.size ?: 0
    }
}