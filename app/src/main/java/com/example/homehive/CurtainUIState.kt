package com.example.homehive

data class CurtainUIState(
    val state: String = "closed",                 // opening, closing, open, closed
    val position : Int = 0,           // [0, 100]
)