package com.example.homehive.states

data class BlindsUIState(
    val status: String = "closed",                 // Opening, Closing, Open, Closed
    val level: Int = 0,                            // [0, 100]
    val position : Int = 0,                    // [0, 100]
)