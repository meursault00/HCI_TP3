package com.example.homehive

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.homehive.network.RetrofitClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class EventService : Service() {
    companion object {
        private const val TAG = "EventService"
        private const val DELAY_MILLIS: Long = 10000
    }

    private val gson = Gson()
    private lateinit var job: Job

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Starting service")

        job = GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                val events = fetchEvents()
                events?.forEach {
                    Log.d(TAG, "Broadcasting send notification intent (${it.deviceId})")
                    val intent2 = Intent().apply {
                        action = MyIntent.SHOW_NOTIFICATION
                        `package` = MyIntent.PACKAGE
                        putExtra(MyIntent.DEVICE_ID, it.deviceId)
                        putExtra(MyIntent.DEVICE_NAME, getDeviceName(it.deviceId))
                        putExtra(MyIntent.ACTION, it.event)
                    }
                    sendOrderedBroadcast(intent2, null)
                }
                delay(DELAY_MILLIS)
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private suspend fun getDeviceName(id: String) : String {
        runCatching {
            val apiService = RetrofitClient.getApiService()
            apiService?.getADevice(id)
        }.onSuccess { response ->
            val newDevice = response?.body()?.result

            return newDevice?.name.orEmpty()
        }
        return "Unknown device"
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "Stopping service")

        if (job.isActive)
            job.cancel()
    }

    private fun fetchEvents(): List<EventData>? {

        Log.d(TAG, "Fetching events...")
        val url = "${BuildConfig.API_BASE_URL}devices/events"
        val connection = (URL(url).openConnection() as HttpURLConnection).also {
            it.requestMethod = "GET"
            it.setRequestProperty("Accept", "application/json")
            it.setRequestProperty("Content-Type", "text/event-stream")
            it.doInput = true
        }

        val responseCode = connection.responseCode
        return if (responseCode == HttpURLConnection.HTTP_OK) {
            val stream = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            val response = StringBuffer()
            val eventList = arrayListOf<EventData>()
            while (stream.readLine().also { line = it } != null) {
                when {
                    line!!.startsWith("data:") -> {
                        response.append(line!!.substring(5))
                    }

                    line!!.isEmpty() -> {
                        Log.d(TAG, response.toString())
                        val event = gson.fromJson<EventData>(
                            response.toString(),
                            object : TypeToken<EventData?>() {}.type
                        )
                        eventList.add(event)
                        response.setLength(0)
                    }
                }
            }
            stream.close()
            Log.d(TAG, "New events found (${eventList.size})")
            eventList
        } else {
            Log.d(TAG, "No new events found")
            null
        }
    }
}