package com.meharenterprises.originsms.services

import android.app.Service
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.OriginSmsApp

/**
 * Foreground service that handles MMS WAP-push PDU download/parsing.
 * MMS retrieval involves a network round-trip to the carrier's MMSC, which
 * doesn't reliably complete inside a BroadcastReceiver's short execution window,
 * so the work is handed off here.
 *
 * Note: full WAP-push PDU parsing (M-Notification.ind -> M-Retrieve.conf) and
 * APN-based MMSC HTTP retrieval is carrier-specific; this service provides the
 * lifecycle scaffold and notification, with the parsing/HTTP step left as the
 * integration point for the chosen MMS transport library.
 */
class MmsDownloadForegroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIF_ID, buildProgressNotification())
        val pushData = intent?.getByteArrayExtra(EXTRA_PUSH_DATA)
        if (pushData != null) {
            // Integration point: parse the WSP/WAP push PDU here and fetch the
            // full MMS from the carrier MMSC, then persist parts via Telephony.Mms.
        }
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf(startId)
        return START_NOT_STICKY
    }

    private fun buildProgressNotification(): android.app.Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)
            if (manager.getNotificationChannel(OriginSmsApp.CHANNEL_MESSAGES) == null) {
                manager.createNotificationChannel(
                    NotificationChannel(
                        OriginSmsApp.CHANNEL_MESSAGES,
                        getString(R.string.notif_channel_messages),
                        NotificationManager.IMPORTANCE_LOW
                    )
                )
            }
        }
        return NotificationCompat.Builder(this, OriginSmsApp.CHANNEL_MESSAGES)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.app_name))
            .setProgress(0, 0, true)
            .setOngoing(true)
            .build()
    }

    companion object {
        const val EXTRA_PUSH_DATA = "extra_push_data"
        private const val NOTIF_ID = 9001
    }
}
