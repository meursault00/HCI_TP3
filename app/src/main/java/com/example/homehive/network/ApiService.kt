package com.example.homehive.network

import com.example.homehive.network.deviceModels.NetworkDevicesList
import com.example.homehive.network.deviceModels.NetworkResult
import com.example.homehive.network.routineModels.NetworkRoutinesList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

data class ArrayValue(
    val values: List<Any>
)



interface ApiService {
    @GET("devices/")
    suspend fun getAllDevices(): Response<NetworkDevicesList>

    @GET("devices/{deviceId}")
    suspend fun getADevice(@Path("deviceId")deviceId: String): Response<NetworkResult>

    @PUT("/api/devices/{deviceId}/{actionName}")
    @JvmSuppressWildcards
    suspend fun updateADeviceNull(
        @Path("deviceId") deviceId: String,
        @Path("actionName") actionName: String,
        @Body requestBody: List<Any>
    ): String

    @PUT("/api/devices/{deviceId}/{actionName}")
    suspend fun updateADeviceString(
        @Path("deviceId") deviceId: String,
        @Path("actionName") actionName: String,
        @Body requestBody: List<String>
    ): String

    @PUT("/api/devices/{deviceId}/{actionName}")
    suspend fun updateADeviceInt(
        @Path("deviceId") deviceId: String,
        @Path("actionName") actionName: String,
        @Body requestBody: List<Int>
    ): String

    @PUT("/api/devices/{deviceId}/{actionName}")
    @JvmSuppressWildcards
    suspend fun updateADeviceMixed(
        @Path("deviceId") deviceId: String,
        @Path("actionName") actionName: String,
        @Body requestBody: List<Any>
    ): String

    @PUT("/api/routines/{routineId}/execute")
    suspend fun executeARoutine(
        @Path("routineId") routineId: String
    ) : String

    @GET("/api/routines")
    suspend fun getAllRoutines(): Response<NetworkRoutinesList>

    @GET("/api/routines/{routineId}")
    suspend fun getARoutine(@Path("routineId")routineId: String): Response<NetworkRoutinesList>



}