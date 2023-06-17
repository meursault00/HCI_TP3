package com.example.homehive.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homehive.network.RetrofitClient
import com.example.homehive.states.RoutinesUIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoutinesVM : ViewModel() {

    private val _uiState = MutableStateFlow(RoutinesUIState())
    val uiState: StateFlow<RoutinesUIState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null

    fun dismissMessage() {
        _uiState.update { it.copy(message = null) }
    }

    fun fetchRoutines() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                Log.d("homehivestatus", " Llegue hasta aca")
                val apiService = RetrofitClient.getApiService()
                Log.d("homehivestatus", " Nunca llego hasta aca!")
                apiService?.getAllRoutines() ?: throw Exception("API Service is null")
            }.onSuccess { response ->
                Log.d("homehivestatus", "Success: Inside Success Block")
                _uiState.update { it.copy(
                    routines = response.body(),
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
    fun fetchARoutine(id : String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                Log.d("homehivestatus", " Llegue hasta aca")
                val apiService = RetrofitClient.getApiService()
                Log.d("homehivestatus", " Nunca llego hasta aca!")
                apiService?.getARoutine(id) ?: throw Exception("API Service is null")
            }.onSuccess { response ->
                Log.d("homehivestatus", "Success: Inside Success Block")
                _uiState.update { it.copy(
                    // deberia retornar la rutina que se pidio
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

    fun executeARoutine(id: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService?.executeARoutine(id)
            }.onSuccess { response ->
                Log.d("homehivestatus", "routine execute successful")
                if (response != null) {
                    Log.d("debug", response)
                }
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure { e ->
                Log.d("debug", "Api Call was Unsuccessful \nError message  ${e.message}")
                _uiState.update { it.copy(message = e.message, isLoading = false) }
            }
        }
    }
}