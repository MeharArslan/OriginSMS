package com.meharenterprises.originsms.receivers

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsManager
import android.widget.Toast
import com.meharenterprises.originsms.R

/**
 * Receives the "sent" PendingIntent callback from SmsManager (for both SMS
 * and MMS sends — MMS routes its sentIntent through this same receiver).
 * On failure, the most recent SENT-box row for this conversation is flagged
 * as FAILED in the system provider so ThreadActivity's "Not sent. Tap to
 * retry." status renders correctly instead of staying stuck on "Sent".
 */
class SendStatusReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (resultCode) {
            android.app.Activity.RESULT_OK -> {
                // Sent successfully — the optimistic SENT-box row inserted at
                // send time already reflects the correct final state.
            }
            SmsManager.RESULT_ERROR_GENERIC_FAILURE,
            SmsManager.RESULT_ERROR_NO_SERVICE,
            SmsManager.RESULT_ERROR_NULL_PDU,
            SmsManager.RESULT_ERROR_RADIO_OFF -> {
                Toast.makeText(context, context.getString(R.string.status_failed), Toast.LENGTH_SHORT).show()
                markMostRecentSentMessageFailed(context)
            }
        }
    }

    private fun markMostRecentSentMessageFailed(context: Context) {
        context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(Telephony.Sms._ID),
            "${Telephony.Sms.TYPE} = ?",
            arrayOf(Telephony.Sms.MESSAGE_TYPE_SENT.toString()),
            "${Telephony.Sms.DATE} DESC LIMIT 1"
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val idIdx = cursor.getColumnIndex(Telephony.Sms._ID)
                if (idIdx >= 0) {
                    val messageId = cursor.getLong(idIdx)
                    val values = ContentValues().apply {
                        put(Telephony.Sms.TYPE, Telephony.Sms.MESSAGE_TYPE_FAILED)
                    }
                    context.contentResolver.update(
                        Telephony.Sms.CONTENT_URI,
                        values,
                        "${Telephony.Sms._ID} = ?",
                        arrayOf(messageId.toString())
                    )
                }
            }
        }
    }
}

/**
 * Receives delivery confirmation broadcasts (when the carrier confirms the
 * recipient's device received the message). Delivery reports are
 * best-effort and carrier-dependent; when one arrives it simply confirms
 * what the UI already shows as "Sent" — no provider write is needed since
 * this app does not persist a separate delivered/undelivered column.
 */
class DeliveryStatusReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // No-op by design: absence of a delivery report is normal on many
        // carriers/recipient devices, so the UI intentionally does not treat
        // "no report received" as a failure state.
    }
}
