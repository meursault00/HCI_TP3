package com.example.homehive.states

import com.example.homehive.network.deviceModels.NetworkSong

data class SpeakerUIState(
    val id: String = "",
    val status: String = "stopped",      // stopped, playing, paused
    val volume: Int = 0,                 // [0, 10]
    val song: NetworkSong,
    val genre: String = "",
)