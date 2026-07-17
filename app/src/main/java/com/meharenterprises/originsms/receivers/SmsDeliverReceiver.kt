package com.meharenterprises.originsms.receivers

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.meharenterprises.originsms.core.ContactsHelper
import com.meharenterprises.originsms.data.db.OriginDatabase
import com.meharenterprises.originsms.services.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * MANDATORY for default SMS app role.
 * Registered for android.provider.Telephony.SMS_DELIVER — only the current default
 * SMS app receives this broadcast (as opposed to SMS_RECEIVED, which any app can get).
 * Responsible for writing the incoming message into the system provider ourselves,
 * since being default means no one else will do it for us.
 *
 * Blocked-number lookup requires a Room query, which is asynchronous — goAsync()
 * extends the receiver's lifetime past onReceive() returning so that check can
 * complete (and the provider insert/notification follow) without the system
 * killing the receiver mid-operation, while still respecting the ~10s broadcast budget.
 */
class SmsDeliverReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_DELIVER_ACTION) return

        val messages = Telephony.Sms.Intents.getMessages(intent)
        if (messages.isEmpty()) return

        val sender = messages[0].originatingAddress ?: return
        val timestamp = messages[0].timestampMillis
        val fullBody = messages.joinToString(separator = "") { it.messageBody ?: "" }

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val normalized = ContactsHelper.normalize(sender)
                val isBlocked = OriginDatabase.getInstance(context).blockedNumberDao().isBlocked(normalized)

                if (!isBlocked) {
                    val values = ContentValues().apply {
                        put(Telephony.Sms.ADDRESS, sender)
                        put(Telephony.Sms.BODY, fullBody)
                        put(Telephony.Sms.DATE, timestamp)
                        put(Telephony.Sms.READ, 0)
                        put(Telephony.Sms.SEEN, 0)
                        put(Telephony.Sms.TYPE, Telephony.Sms.MESSAGE_TYPE_INBOX)
                    }
                    val insertedUri = context.contentResolver.insert(Telephony.Sms.CONTENT_URI, values)

                    val contactName = ContactsHelper(context).resolve(sender).displayName
                    NotificationHelper(context).showIncomingMessageNotification(
                        address = sender,
                        displayName = contactName,
                        body = fullBody,
                        threadId = extractThreadId(insertedUri, context)
                    )
                }
                // Blocked senders' messages are intentionally not written to the
                // provider at all — silently dropped, no trace, no notification.
            } finally {
                pendingResult.finish()
            }
        }
    }

    private fun extractThreadId(uri: android.net.Uri?, context: Context): Long {
        if (uri == null) return -1L
        context.contentResolver.query(uri, arrayOf(Telephony.Sms.THREAD_ID), null, null, null)?.use {
            if (it.moveToFirst()) {
                val idx = it.getColumnIndex(Telephony.Sms.THREAD_ID)
                if (idx >= 0) return it.getLong(idx)
            }
        }
        return -1L
    }
}
