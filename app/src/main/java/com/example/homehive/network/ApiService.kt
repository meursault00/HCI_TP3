package com.example.homehive.network

import com.example.homehive.network.models.NetworkDevicesList
import com.example.homehive.network.models.NetworkResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @GET("/devices")
    suspend fun getAllDevices(): Response<NetworkDevicesList>

    @GET("/devices")
    suspend fun getADevice(@Query("deviceId")deviceId: String): Response<NetworkResult>

//    @GET("/routines")
//    suspend fun getAllRoutines(): Response<NetworkRoutinesList>
//
//    @GET("/routines")
//    suspend fun getARoutine(@Query("routineId")routineId: String): Response<NetworkRoutinesList>

//    @PUT("/routines")
    //execute de routines (PUT)
}