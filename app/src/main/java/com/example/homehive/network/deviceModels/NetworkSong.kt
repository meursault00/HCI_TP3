package com.example.homehive.network.deviceModels

import com.google.gson.annotations.SerializedName

data class NetworkSong (
    @SerializedName("title"         ) var title      : String?    = " ",
    @SerializedName("artist"        ) var artist     : String?    = " ",
    @SerializedName("album"         ) var album      : String?    = " ",
    @SerializedName("duration"      ) var duration   : String?    = "0:00",
    @SerializedName("progress"      ) var progress   : String?    = "0:00"
)