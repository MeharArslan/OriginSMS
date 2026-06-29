package com.meharenterprises.originsms.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.meharenterprises.originsms.core.SmsRepository

/**
 * MANDATORY for default SMS app role.
 * Handles RESPOND_VIA_MESSAGE intents fired when the user taps a quick-reply
 * action from the phone/call-screening UI (e.g. "Can't talk, sending a text").
 * Must complete its work and call stopSelf() without showing any UI.
 */
class HeadlessSmsSendService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { handleRespondViaMessage(it) }
        stopSelf(startId)
        return START_NOT_STICKY
    }

    private fun handleRespondViaMessage(intent: Intent) {
        val data = intent.data ?: return
        val body = intent.getStringExtra(android.content.Intent.EXTRA_TEXT) ?: return
        val destination = data.schemeSpecificPart?.split(";")?.firstOrNull() ?: return
        SmsRepository(applicationContext).sendSms(destination, body, threadId = null)
    }
}
