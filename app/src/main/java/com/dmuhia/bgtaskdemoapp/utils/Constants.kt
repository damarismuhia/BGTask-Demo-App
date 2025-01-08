package com.dmuhia.bgtaskdemoapp.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val ONE_TIME_WORK_REQUEST = "ONE_TIME_WORK_REQUEST"
const val PERIODIC_WORK_REQUEST = "PERIODIC_WORK_REQUEST"
const val QUOTE_TAG = "QUOTE-TAG"
fun formatTimestampToDMY(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM, yyyy | h:mm a", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}
 fun createNotification(context: Context, notificationTitle:String, notContent:String) : Notification {
    val channelId = "main-channel-id"
    val channelName = "main-channel-name"
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_notification_overlay)
        .setContentTitle(notificationTitle)
        .setContentText(notContent)
        .setOngoing(false)
        .setAutoCancel(false)
    val notificationManager = context.getSystemService(NotificationManager::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)

    }
    return builder.build()
}

class Constants {
    companion object {
        const val KEY_IMAGE_URI = ""

    }

}
