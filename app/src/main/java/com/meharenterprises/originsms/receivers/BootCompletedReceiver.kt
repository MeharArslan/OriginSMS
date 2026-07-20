package com.meharenterprises.originsms.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * No-op placeholder to keep any future boot-time work (e.g. re-registering
 * scheduled message workers) centralized. Declared in the manifest so the
 * app can react to device restarts if needed later.
 */
class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        // Reserved for future scheduled-send / reminder re-registration.
    }
}

/**
 * Handles notification action buttons (Reply, Mark as read) so they work
 * without opening the app, matching standard Android messaging-app UX.
 */
class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_MARK_READ -> {
                val threadId = intent.getLongExtra(EXTRA_THREAD_ID, -1L)
                if (threadId != -1L) {
                    com.meharenterprises.originsms.core.SmsRepository(context).let { repo ->
                        kotlinx.coroutines.runBlocking(kotlinx.coroutines.Dispatchers.IO) { repo.markThreadRead(threadId) }
                    }
                }
                com.meharenterprises.originsms.services.NotificationHelper(context).cancel(threadId)
            }
            ACTION_QUICK_REPLY -> {
                val threadId = intent.getLongExtra(EXTRA_THREAD_ID, -1L)
                val address = intent.getStringExtra(EXTRA_ADDRESS) ?: return
                val replyText = androidx.core.app.RemoteInput.getResultsFromIntent(intent)
                    ?.getCharSequence(KEY_QUICK_REPLY)?.toString() ?: return
                com.meharenterprises.originsms.core.SmsRepository(context)
                    .sendSms(address, replyText, if (threadId != -1L) threadId else null)
                com.meharenterprises.originsms.services.NotificationHelper(context).cancel(threadId)
            }
        }
    }

    companion object {
        const val ACTION_MARK_READ = "com.meharenterprises.originsms.MARK_READ"
        const val ACTION_QUICK_REPLY = "com.meharenterprises.originsms.QUICK_REPLY"
        const val EXTRA_THREAD_ID = "extra_thread_id"
        const val EXTRA_ADDRESS = "extra_address"
        const val KEY_QUICK_REPLY = "key_quick_reply"
    }
}
