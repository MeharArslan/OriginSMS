package com.meharenterprises.originsms

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.meharenterprises.originsms.data.db.OriginDatabase
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point.
 * Sets up the notification channel and provides a single lazily-created
 * Room database instance for the whole process.
 */
@HiltAndroidApp
class OriginSmsApp : Application() {

    val database: OriginDatabase by lazy { OriginDatabase.getInstance(this) }

    override fun attachBaseContext(base: android.content.Context) {
        super.attachBaseContext(base)
        installCrashHandler()
    }

    private fun installCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            try {
                val sw = java.io.StringWriter()
                throwable.printStackTrace(java.io.PrintWriter(sw))
                val intent = android.content.Intent(this, CrashActivity::class.java).apply {
                    putExtra(CrashActivity.EXTRA_TRACE, sw.toString())
                    addFlags(
                        android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                        android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
                    )
                }
                startActivity(intent)
            } catch (_: Exception) { }
            android.os.Process.killProcess(android.os.Process.myPid())
            kotlin.system.exitProcess(10)
        }
    }

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
