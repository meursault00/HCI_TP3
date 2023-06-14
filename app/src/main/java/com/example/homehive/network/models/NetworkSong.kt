package com.example.homehive.network.models

import com.google.gson.annotations.SerializedName

data class NetworkSong (
    @SerializedName("title"         ) var id         : String?    = null,
    @SerializedName("artist"        ) var name       : String?    = null,
    @SerializedName("album"         ) var album      : String?    = null,
    @SerializedName("duration"      ) var duration   : String?    = null,
    @SerializedName("progress"      ) var progress   : String?    = null
)