package com.example.homehive

data class OvenUIState(
    val power: Boolean = false,                 // on, off
    val ovenTemperature : Int = 90,             // [ 90, 230 ]
    val heatMode : String = "apagado",          // convencional, abajo, arriba
    val grillMode : String = "apagado",         // apagado, economico, completo
    val convectionMode : String = "apagado"     // apagado, economico, convencional
)

