package com.example.homehive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homehive.network.RetrofitClient
import com.example.homehive.states.DevicesUIState
import kotlinx.coroutines.Job
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
                apiService.getAllDevices()
            }.onSuccess { response ->
                _uiState.update { it.copy(
                    devices = response.body(),
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
}