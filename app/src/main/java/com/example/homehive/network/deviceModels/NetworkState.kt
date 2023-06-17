package com.example.homehive.network.deviceModels

import com.google.gson.annotations.SerializedName


data class NetworkState (
    // speaker
    @SerializedName("song") var song :                              NetworkSong? = null,
    @SerializedName("status") var status :                          String? = null,
    @SerializedName("volume") var volume :                          Int?    = null,
    @SerializedName("genre" ) var genre  :                          String? = null,
    // blinds
    @SerializedName("level" ) var level  :                          Int? = null,
    @SerializedName("currentLevel") var currentLevel  :             Int? = null,
    // oven
    @SerializedName("temperature") var temperature  :               Int? = null,
    @SerializedName("heat" ) var heat  :                            String? = null,
    @SerializedName("grill" ) var grill  :                          String? = null,
    @SerializedName("convection" ) var convection  :                String? = null,
    // refrigerator
    @SerializedName("freezerTemperature") var freezerTemperature  : Int? = null,
    @SerializedName("mode" ) var mode  :                            String? = null,
)