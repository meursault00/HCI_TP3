package com.example.homehive.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homehive.states.OvenUIState
import com.example.homehive.states.SettingsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsVM : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUIState())
    val uiState: StateFlow<SettingsUIState> = _uiState.asStateFlow()

    fun toggleLanguage() {
        _uiState.update { currentState ->
            currentState.copy(language = !currentState.language)
        }
    }

    fun toggleTheme() {
        _uiState.update { currentState ->
            currentState.copy(theme = !currentState.theme)
        }
    }
}
