package com.example.homehive.network.routineModels

import com.google.gson.annotations.SerializedName


data class NetworkActions (

  @SerializedName("device"     ) var device     : NetworkDevice?    = NetworkDevice(),
  @SerializedName("actionName" ) var actionName : String?           = null,
  @SerializedName("params"     ) var params     : ArrayList<String> = arrayListOf(),
  // @SerializedName("meta"       ) var meta       : Meta?             = Meta()

)