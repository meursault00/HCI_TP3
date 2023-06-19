package com.example.homehive.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.homehive.states.SettingsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

var isDarkTheme = mutableStateOf(false) // Global variable for theme

class SettingsVM : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUIState())
    val uiState: StateFlow<SettingsUIState> = _uiState.asStateFlow()

    fun toggleLanguage() {
        _uiState.update { currentState ->
            currentState.copy(language = !currentState.language)
        }
    }

}