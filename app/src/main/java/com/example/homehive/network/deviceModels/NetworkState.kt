package com.example.homehive.network.deviceModels

import com.google.gson.annotations.SerializedName


data class NetworkState (

    @SerializedName("status" ) var status : String? = null,
    @SerializedName("volume" ) var volume : Int?    = null,
    @SerializedName("genre"  ) var genre  : String? = null

)