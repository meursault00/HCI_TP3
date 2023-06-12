package com.example.homehive.network.models

import com.google.gson.annotations.SerializedName


data class NetworkComponentId (
    @SerializedName("required" ) var required : Boolean? = null
)
