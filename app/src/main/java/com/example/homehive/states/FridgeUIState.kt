package com.example.homehive.states

// Creo un constructor para el estado, getters y setters son autogenerados
data class FridgeUIState (
    // Llamado a la API
    val power: Boolean = false,                 // on, off
    val temperature : Int = 4,            // [ 2, 8 ]
    val freezerTemperature : Int = -14,         // [ -20, -8 ]
    val mode : String = "Normal"                // Normal, Fiesta o Vacaciones
)


