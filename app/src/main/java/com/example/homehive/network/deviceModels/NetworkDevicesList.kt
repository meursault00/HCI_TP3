package com.example.homehive.network.deviceModels

import com.google.gson.annotations.SerializedName


data class NetworkDevicesList (

    @SerializedName("result" ) var result : ArrayList<NetworkResult> = arrayListOf()

)
