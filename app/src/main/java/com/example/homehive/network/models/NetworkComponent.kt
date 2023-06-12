package com.example.homehive.network.models

import com.google.gson.annotations.SerializedName


data class NetworkComponent (
    @SerializedName("__name"    ) var _name    : String? = null,
    @SerializedName("props"     ) var props    : NetworkProps?  = NetworkProps(),
    @SerializedName("__hmrId"   ) var _hmrId   : String? = null,
    @SerializedName("__scopeId" ) var _scopeId : String? = null,
    @SerializedName("__file"    ) var _file    : String? = null
)