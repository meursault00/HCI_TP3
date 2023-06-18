package com.example.homehive.network.deviceModels

import com.google.gson.annotations.SerializedName

data class NetworkUnaryDevice (

    @SerializedName("result" ) var result : NetworkResult? = NetworkResult()

)