package com.redfrogec.credigo.backgroundscheduler

expect class LocalNotificationManager {
    fun hasPermission() : Boolean
    suspend fun requestPermission(): Boolean
    fun showNotification(title: String, body: String)
}