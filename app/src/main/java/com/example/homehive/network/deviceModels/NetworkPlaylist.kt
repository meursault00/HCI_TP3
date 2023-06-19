package com.example.homehive.network.deviceModels

import com.google.gson.annotations.SerializedName

class NetworkPlaylist {
    @SerializedName("result" ) var result : ArrayList<NetworkPlaylistData> = arrayListOf()
}