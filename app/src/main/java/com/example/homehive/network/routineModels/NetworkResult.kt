package com.example.homehive.network.routineModels

import com.google.gson.annotations.SerializedName


data class NetworkResult (

  @SerializedName("id"      ) var id      : String?            = null,
  @SerializedName("name"    ) var name    : String?            = null,
  @SerializedName("actions" ) var actions : ArrayList<NetworkActions> = arrayListOf(),
  // @SerializedName("meta"    ) var meta    : Meta?              = Meta()

)