package com.meharenterprises.originsms.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.meharenterprises.originsms.services.MmsDownloadForegroundService

/**
 * MANDATORY for default SMS app role.
 * Registered for android.provider.Telephony.WAP_PUSH_DELIVER with MIME type
 * application/vnd.wap.mms-message. Delegates the actual PDU download/parsing
 * to a foreground service since MMS retrieval is a network operation that
 * can outlive the broadcast receiver's execution window.
 */
class MmsWapPushReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pushData = intent.getByteArrayExtra("data") ?: return
        val serviceIntent = Intent(context, MmsDownloadForegroundService::class.java).apply {
            putExtra(MmsDownloadForegroundService.EXTRA_PUSH_DATA, pushData)
        }
        context.startForegroundService(serviceIntent)
    }
}
