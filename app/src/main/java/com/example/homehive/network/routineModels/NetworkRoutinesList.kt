package com.example.homehive.network.routineModels

import com.google.gson.annotations.SerializedName


data class NetworkRoutinesList (

  @SerializedName("result" ) var result : ArrayList<NetworkResult> = arrayListOf()

)