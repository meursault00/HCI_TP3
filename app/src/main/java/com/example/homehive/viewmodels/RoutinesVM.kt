package com.example.homehive.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homehive.Globals
import com.example.homehive.UpdateMap
import com.example.homehive.library.HistoryStack
import com.example.homehive.network.RetrofitClient
import com.example.homehive.states.RoutinesUIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoutinesVM(
    private val devicesVM : DevicesVM
) : ViewModel() {

    private val _uiState = MutableStateFlow(RoutinesUIState())
    val uiState: StateFlow<RoutinesUIState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null

    fun fetchRoutines() {
        _uiState.update { it.copy(isLoading = true) }
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService?.getAllRoutines() ?: throw Exception("API Service is null")
            }.onSuccess { response ->
                delay(2000)
                _uiState.update { it.copy(
                    routines = response.body(),
                    isLoading = false
                ) }
            }.onFailure { e ->
                delay(2000)
                _uiState.update { it.copy(
                    message = e.message,
                    isLoading = false
                ) }
            }
        }
    }
    fun fetchARoutine(id : String) {
        _uiState.update { it.copy(isLoading = true) }

        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService?.getARoutine(id) ?: throw Exception("API Service is null")
            }.onSuccess { response ->
                _uiState.update { it.copy(
                    // deberia retornar la rutina que se pidio
                    isLoading = false
                ) }
            }.onFailure { e ->
                _uiState.update { it.copy(
                    message = e.message,
                    isLoading = false
                ) }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun executeARoutine(id: String) {

        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService?.executeARoutine(id)
            }.onSuccess { response ->
                _uiState.update { it.copy(isLoading = false) }
                HistoryStack.push("Routine '${id}': got executed")

            }.onFailure { e ->
                _uiState.update { it.copy(message = e.message, isLoading = false) }
            }
        }
        UpdateMap.map.replaceAll { _, _ -> true }
        Log.d("debug", UpdateMap.map.toString())
    }
}