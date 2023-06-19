package com.example.homehive.network.deviceModels

import com.google.gson.annotations.SerializedName

data class NetworkPlaylistData (
    @SerializedName("title"         ) var title      : String?    = "Loading...",
    @SerializedName("artist"        ) var artist     : String?    = "Loading...",
    @SerializedName("album"         ) var album      : String?    = "Loading...",
    @SerializedName("duration"      ) var duration   : String?    = "0:00"
)