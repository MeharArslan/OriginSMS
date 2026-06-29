package com.meharenterprises.originsms.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.meharenterprises.originsms.core.SmsRepository

/**
 * Some OEM dialers/launchers query specifically for a service named
 * RespondViaMessageService rather than the AOSP-conventional
 * HeadlessSmsSendService. Both are declared in the manifest with identical
 * RESPOND_VIA_MESSAGE intent filters to maximize compatibility across devices
 * when requesting the default SMS role.
 */
class RespondViaMessageService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { handleRespondViaMessage(it) }
        stopSelf(startId)
        return START_NOT_STICKY
    }

    private fun handleRespondViaMessage(intent: Intent) {
        val data = intent.data ?: return
        val body = intent.getStringExtra(Intent.EXTRA_TEXT) ?: return
        val destination = data.schemeSpecificPart?.split(";")?.firstOrNull() ?: return
        SmsRepository(applicationContext).sendSms(destination, body, threadId = null)
    }
}
