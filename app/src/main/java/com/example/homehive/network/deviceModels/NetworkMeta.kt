package com.example.homehive.network.deviceModels

import com.google.gson.annotations.SerializedName


data class NetworkMeta (

    @SerializedName("component" ) var component : NetworkComponent? = NetworkComponent()

)
