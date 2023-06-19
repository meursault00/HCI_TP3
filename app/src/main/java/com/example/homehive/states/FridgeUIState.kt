package com.example.homehive.states

// Creo un constructor para el estado, getters y setters son autogenerados
data class FridgeUIState (
    // Llamado a la API
    val id: String = "",
    val name: String = "",
    val temperature: Int = 4,            // [2, 8]
    val freezerTemperature: Int = 0,     // [-20, -8]
    val mode: String = "default"               // default, party, vacation
)



