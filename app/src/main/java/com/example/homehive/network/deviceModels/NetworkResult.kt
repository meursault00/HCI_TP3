package com.example.homehive.network.deviceModels

import com.google.gson.annotations.SerializedName


data class NetworkResult (

    @SerializedName("id"    ) var id    : String? = null,
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("type"  ) var type  : NetworkType?   = NetworkType(),
    @SerializedName("state" ) var state : NetworkState?  = NetworkState(),
    @SerializedName("meta"  ) var meta  : NetworkMeta?   = NetworkMeta()

)
