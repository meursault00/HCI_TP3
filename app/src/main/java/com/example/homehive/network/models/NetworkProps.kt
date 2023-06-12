package com.example.homehive.network.models


import com.google.gson.annotations.SerializedName


data class NetworkProps (
    @SerializedName("componentName" ) var componentName : NetworkComponentName? = NetworkComponentName(),
    @SerializedName("componentId"   ) var componentId   : NetworkComponentId?   = NetworkComponentId(),
    @SerializedName("componentRoom" ) var componentRoom : NetworkComponentRoom? = NetworkComponentRoom()
)