package com.example.homehive

// Creo un constructor para el estado, getters y setters son autogenerados
data class FridgeUIState (
    // Llamado a la API
    val power: Boolean = false,                 // on, off
    val fridgeTemperature : Int = 4,            // [ 2, 8 ]
    val freezerTemperature : Int = -14,         // [ -20, -8 ]
    val mode : String = "normal"                // normal, fiesta o vacaciones
)


