package com.example.homehive.network.routineModels

import com.google.gson.annotations.SerializedName


data class NetworkDevice (

  @SerializedName("id"   ) var id   : String? = null,
  @SerializedName("name" ) var name : String? = null,
  @SerializedName("type" ) var type : NetworkType?   = NetworkType(),
  // @SerializedName("meta" ) var meta : Meta?   = Meta()

)