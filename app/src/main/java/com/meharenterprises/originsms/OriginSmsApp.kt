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
        com.meharenterprises.originsms.ui.ThemePreferenceManager(this).applyStoredMode()
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
        val crashText = stackTraceWriter.toString()
        val timestamp = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US).format(Date())
        val fileName = "OriginSMS_crash_$timestamp.txt"

        // Always write to the app-private external files dir first — this
        // location requires no permissions and works on every Android version,
        // so the write itself can never fail due to storage restrictions.
        val privateDir = getExternalFilesDir(null) ?: filesDir
        File(privateDir, fileName).writeText(crashText)

        // Additionally copy into the public Downloads folder via MediaStore,
        // which is the scoped-storage-compliant way to place a file somewhere
        // any file manager can browse without special permissions on
        // Android 10+ (direct File-based writes to getExternalStoragePublicDirectory
        // are blocked by the system once targetSdkVersion is 29 or higher).
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val values = android.content.ContentValues().apply {
                    put(android.provider.MediaStore.Downloads.DISPLAY_NAME, fileName)
                    put(android.provider.MediaStore.Downloads.MIME_TYPE, "text/plain")
                }
                val uri = contentResolver.insert(
                    android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI, values
                )
                if (uri != null) {
                    contentResolver.openOutputStream(uri)?.use { it.write(crashText.toByteArray()) }
                }
            } else {
                @Suppress("DEPRECATION")
                val legacyDownloads = android.os.Environment.getExternalStoragePublicDirectory(
                    android.os.Environment.DIRECTORY_DOWNLOADS
                )
                File(legacyDownloads, fileName).writeText(crashText)
            }
        } catch (_: Exception) {
            // The private-dir copy above already succeeded regardless, so a
            // failure here just means the public copy isn't available.
        }
    }

    companion object {
        const val CHANNEL_MESSAGES = "channel_messages"
    }
}
