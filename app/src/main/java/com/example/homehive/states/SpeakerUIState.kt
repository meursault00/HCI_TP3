package com.example.homehive.states

data class SpeakerUIState(
    val status: String = "stopped",      // stopped, playing, paused
    val volume: Int = 0,                 // [0, 10]
    val song: String = "",
    val genre: String = "",
)