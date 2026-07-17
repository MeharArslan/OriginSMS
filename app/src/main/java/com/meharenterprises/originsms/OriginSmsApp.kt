package com.meharenterprises.originsms

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.meharenterprises.originsms.data.db.OriginDatabase

/**
 * Application entry point.
 * Sets up the notification channel and provides a single lazily-created
 * Room database instance for the whole process.
 */
class OriginSmsApp : Application() {

    val database: OriginDatabase by lazy { OriginDatabase.getInstance(this) }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)
            val channel = NotificationChannel(
                CHANNEL_MESSAGES,
                getString(R.string.notif_channel_messages),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = getString(R.string.notif_channel_messages_desc)
                enableVibration(true)
            }
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_MESSAGES = "channel_messages"
    }
}
