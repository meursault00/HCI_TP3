package com.example.homehive


import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ShowNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val deviceId: String? = intent.getStringExtra(MyIntent.DEVICE_ID)
        val deviceName: String? = intent.getStringExtra(MyIntent.DEVICE_NAME)
        val action: String? = intent.getStringExtra(MyIntent.ACTION)
        Log.d(TAG, "Show notification intent received {$deviceId)")

        showNotification(context, deviceId!!, deviceName!!, action!!)
    }

    private fun showNotification(context: Context, deviceId: String, deviceName: String, action: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MyIntent.DEVICE_ID, deviceId)
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, HomeHive.CHANNEL_ID)
            .setSmallIcon(R.drawable.notifications)
            .setContentTitle(deviceName)
            .setContentText(action)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(action))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        try {
            val notificationManager = NotificationManagerCompat.from(context)
            if (notificationManager.areNotificationsEnabled())
                notificationManager.notify(deviceId.hashCode(), builder.build())
        } catch (e: SecurityException) {
            Log.d(TAG, "Notification permission not granted $e")
        }
    }

    companion object {
        private const val TAG = "ShowNotificationReceiver"
    }
}