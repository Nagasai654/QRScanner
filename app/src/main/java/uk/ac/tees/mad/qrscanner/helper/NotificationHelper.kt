package uk.ac.tees.mad.qrscanner.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import uk.ac.tees.mad.qrscanner.R

object NotificationHelper {
    private const val CHANNEL_ID = "notification_channel"
    private const val CHANNEL_NAME = "App Notifications"
    private const val NOTIFICATION_ID = 1

    fun showNotification(context: Context, title: String, message: String) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Channel for app notifications"
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_qr_code_scanner_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder)
    }
}