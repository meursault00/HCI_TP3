package com.example.homehive.network.models

import com.google.gson.annotations.SerializedName


data class NetworkType (

    @SerializedName("id"         ) var id         : String? = null,
    @SerializedName("name"       ) var name       : String? = null,
    @SerializedName("powerUsage" ) var powerUsage : Int?    = null

)