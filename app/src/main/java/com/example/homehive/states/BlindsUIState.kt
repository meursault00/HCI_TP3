package com.example.homehive.states

data class BlindsUIState(
    val state: String = "closed",                 // opening, closing, open, closed
    val position : Int = 0,           // [0, 100]
)