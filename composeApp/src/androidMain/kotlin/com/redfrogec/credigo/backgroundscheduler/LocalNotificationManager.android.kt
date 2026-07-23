package com.redfrogec.credigo.backgroundscheduler

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlin.random.Random

actual class LocalNotificationManager(
    private val context: Context
) {
    actual fun hasPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= 33) { // Android 13+
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Android 12 and below have implicit permission
        }
    }

    actual suspend fun requestPermission(): Boolean {
        if (hasPermission()) return true

        if (Build.VERSION.SDK_INT >= 33) {
            val activity = PlatformActivityProvider.currentActivity
            // Safety check: Ensure activity is valid and not finishing
            if (activity != null && !activity.isFinishing && !activity.isDestroyed) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    123 // Request Code
                )
                return false
            } else {
                println("LocalNotificationService: Activity is null or finishing, cannot request permissions")
                return false
            }
        }

        return true
    }

    actual fun showNotification(title: String, body: String) {
        val channelId = "CrediGo_channel_id"
        val notificationId = Random.nextInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "General Notifications"
            val descriptionText = "Default channel for app notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                try {
                    notify(notificationId, builder.build())
                } catch (e: SecurityException) {
                    println("Notification permission missing")
                }
            }
        }
    }
}