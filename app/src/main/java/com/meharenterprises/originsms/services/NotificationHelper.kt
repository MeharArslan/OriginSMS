package com.meharenterprises.originsms.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.meharenterprises.originsms.OriginSmsApp
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.data.db.OriginDatabase
import com.meharenterprises.originsms.lock.LockUnlockActivity
import com.meharenterprises.originsms.receivers.NotificationActionReceiver
import com.meharenterprises.originsms.ui.thread.ThreadActivity
import kotlinx.coroutines.runBlocking

/**
 * Builds and shows the standard incoming-message notification, with Reply and
 * Mark-as-read quick actions. If the destination thread is locked, the message
 * body/sender are masked so the notification shade never leaks locked-chat
 * content — tapping it routes through the PIN/biometric unlock screen instead
 * of straight into the thread.
 */
class NotificationHelper(private val context: Context) {

    private val manager = context.getSystemService(NotificationManager::class.java)

    fun showIncomingMessageNotification(address: String, displayName: String, body: String, threadId: Long) {
        val lockState = runBlocking {
            OriginDatabase.getInstance(context).threadLockDao().getForThread(threadId)
        }
        val isLocked = lockState?.isLocked == true
        val isHidden = lockState?.isHidden == true

        // Hidden chats produce no visible notification at all by design.
        if (isHidden) return

        // Respect mute: either muted indefinitely (-1) or muted until a
        // future timestamp. If the mute window has already passed, treat
        // the chat as unmuted (the scheduled auto-unmute job will catch up
        // and clear the flag on its own next pass).
        val muteUntil = lockState?.muteUntilMillis ?: 0L
        val isMuted = lockState?.isMuted == true &&
            (muteUntil == -1L || muteUntil > System.currentTimeMillis())
        if (isMuted) return

        val title = if (isLocked) context.getString(R.string.app_name) else displayName
        val content = if (isLocked) context.getString(R.string.notif_locked_chat_content) else body

        val contentIntent = if (isLocked) {
            Intent(context, LockUnlockActivity::class.java).apply {
                putExtra(LockUnlockActivity.EXTRA_THREAD_ID, threadId)
                putExtra(LockUnlockActivity.EXTRA_ADDRESS, address)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        } else {
            Intent(context, ThreadActivity::class.java).apply {
                putExtra(ThreadActivity.EXTRA_THREAD_ID, threadId)
                putExtra(ThreadActivity.EXTRA_ADDRESS, address)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
        val contentPendingIntent = PendingIntent.getActivity(
            context, threadId.toInt(), contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, OriginSmsApp.CHANNEL_MESSAGES)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        // Quick actions are omitted for locked chats — replying from the shade
        // would bypass the PIN/biometric gate entirely.
        if (!isLocked) {
            builder.addAction(buildMarkReadAction(threadId))
            builder.addAction(buildQuickReplyAction(threadId, address))
        }

        manager.notify(threadId.toInt(), builder.build())
    }

    private fun buildMarkReadAction(threadId: Long): NotificationCompat.Action {
        val intent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = NotificationActionReceiver.ACTION_MARK_READ
            putExtra(NotificationActionReceiver.EXTRA_THREAD_ID, threadId)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context, threadId.toInt() + 1, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Action.Builder(0, context.getString(R.string.notif_action_mark_read), pendingIntent).build()
    }

    private fun buildQuickReplyAction(threadId: Long, address: String): NotificationCompat.Action {
        val remoteInput = RemoteInput.Builder(NotificationActionReceiver.KEY_QUICK_REPLY)
            .setLabel(context.getString(R.string.hint_type_message))
            .build()

        val intent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = NotificationActionReceiver.ACTION_QUICK_REPLY
            putExtra(NotificationActionReceiver.EXTRA_THREAD_ID, threadId)
            putExtra(NotificationActionReceiver.EXTRA_ADDRESS, address)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context, threadId.toInt() + 2, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        return NotificationCompat.Action.Builder(0, context.getString(R.string.notif_action_reply), pendingIntent)
            .addRemoteInput(remoteInput)
            .build()
    }

    fun cancel(threadId: Long) {
        manager.cancel(threadId.toInt())
    }
}
