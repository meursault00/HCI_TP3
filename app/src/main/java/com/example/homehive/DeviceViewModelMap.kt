package com.example.homehive

import androidx.lifecycle.ViewModel

object DeviceViewModelMap {
    val map: MutableMap<String, ViewModel> = mutableMapOf()
}

object Globals {
    var updates: Int = 0
}

object UpdateMap {
    val map: MutableMap<String, Boolean> = mutableMapOf()
}