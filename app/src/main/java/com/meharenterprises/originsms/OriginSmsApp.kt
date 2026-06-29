package com.meharenterprises.originsms

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.meharenterprises.originsms.data.db.OriginDatabase
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Application entry point.
 * Sets up the notification channel and provides a single lazily-created
 * Room database instance for the whole process.
 *
 * Also installs a global uncaught-exception handler that writes the full
 * stack trace to a plain-text file under the app's external files directory.
 * This is a deliberate diagnostic aid for environments without adb/logcat
 * access (e.g. debugging directly on-device via Termux) — it does not
 * affect normal operation and only activates on an actual crash.
 */
class OriginSmsApp : Application() {

    val database: OriginDatabase by lazy { OriginDatabase.getInstance(this) }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
        installCrashLogger()
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

    private fun installCrashLogger() {
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                writeCrashToFile(throwable)
            } catch (_: Exception) {
                // Never let the crash logger itself crash the crash handler.
            }
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }

    private fun writeCrashToFile(throwable: Throwable) {
        val stackTraceWriter = StringWriter()
        throwable.printStackTrace(PrintWriter(stackTraceWriter))

        val timestamp = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US).format(Date())
        val externalDir = getExternalFilesDir(null) ?: filesDir
        val crashFile = File(externalDir, "crash_$timestamp.txt")
        crashFile.writeText(stackTraceWriter.toString())
    }

    companion object {
        const val CHANNEL_MESSAGES = "channel_messages"
    }
}
