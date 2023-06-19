package com.example.homehive.states


data class OvenUIState(
    val id: String = "",
    val name: String = "",
    val power: String = "off",                 // on, off
    val ovenTemperature : Int = 90,             // [ 90, 230 ]
    val heatMode : String = "convencional",          // convencional, abajo, arriba
    val grillMode : String = "convencional",         // apagado, economico, completo
    val convectionMode : String = "apagado"     // apagado, economico, convencional
)

