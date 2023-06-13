package com.example.homehive

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

// Create a notification channel
fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Log.d("HomeHiveLog","Notification Channel Created")
        val channelId = "HomeHiveHCI"
        val channelName = "HomeHive"
        val channelDescription = "Home Notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(channelId, channelName, importance)
        channel.description = channelDescription

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
public fun sendCustomNotification(context: Context, title: String, content: String) {
    Log.d("HomeHiveLog", "Sending Custom Notification")

    // Build the notification
    val builder = NotificationCompat.Builder(context, "HomeHiveHCI")
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(title)
        .setContentText(content)

    // Create a default PendingIntent
    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    // Set the default PendingIntent for the notification
    builder.setContentIntent(pendingIntent)

    // Get a unique notification ID
    val notificationId = System.currentTimeMillis().toInt()

    // Post the notification
    val notificationManager = NotificationManagerCompat.from(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Request permission or handle missing permission
        return
    }
    notificationManager.notify(notificationId, builder.build())
}

// Crea un default PendingIntent
private fun getDefaultPendingIntent(context: Context): PendingIntent {
    // TODO: Replace with your desired action
    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
    return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
}

// Todavia no esta en uso
@Composable
fun NotificationContent() {
    Log.d("HomeHiveLog","Notification Content Built")
    Column {
        Text(text = "Wubbadubbadubdub")
        Button(onClick = { /* Perform action */ }) {
            Text(text = "Please Kill Me")
        }
    }
}

// Todavia no esta en uso, no funciona correctamente
@Composable
fun PostNotification() {
    val context = LocalContext.current
    val notificationId = 1

    Log.d("HomeHiveLog","Posting Notification")

    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = {
                // Build the notification
                val builder = NotificationCompat.Builder(context, "channelId")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("Notification Title")
                    .setContentText("Notification Content")

                // Create a RemoteViews object and set its layout
                val remoteViews = RemoteViews(context.packageName, R.layout.notification_layout)
                remoteViews.setTextViewText(
                    R.id.notification_content,
                    "Default Notification Content"
                )
                remoteViews.setOnClickPendingIntent(
                    R.id.notification_button,
                    getDefaultPendingIntent(context)
                )

                // Set the custom view for the notification content
                builder.setCustomContentView(remoteViews)

                // Post the notification
                val notificationManager = NotificationManagerCompat.from(context)
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d("HomeHiveLog","Activate Notification Permission")
                    return@Button
                }
                notificationManager.notify(notificationId, builder.build())
            }
        ) {
            Text(text = "Post Notification")
        }
    }
}

