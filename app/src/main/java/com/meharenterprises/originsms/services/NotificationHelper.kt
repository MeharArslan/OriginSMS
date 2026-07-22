package com.meharenterprises.originsms.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.graphics.drawable.IconCompat
import com.meharenterprises.originsms.OriginSmsApp
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.ContactsHelper
import com.meharenterprises.originsms.data.db.OriginDatabase
import com.meharenterprises.originsms.lock.LockUnlockActivity
import com.meharenterprises.originsms.receivers.NotificationActionReceiver
import com.meharenterprises.originsms.ui.thread.ThreadActivity
import kotlinx.coroutines.runBlocking

class NotificationHelper(private val context: Context) {

    private val manager = context.getSystemService(NotificationManager::class.java)

    // Store last 5 messages per thread for MessagingStyle
    private val messageHistory = mutableMapOf<Long, ArrayDeque<Pair<String, Long>>>()

    fun showIncomingMessageNotification(
        address: String,
        displayName: String,
        body: String,
        threadId: Long
    ) {
        try {
            if (com.meharenterprises.originsms.ui.thread.ThreadActivity.activeThreadId == threadId) return
        } catch (_: Exception) {}
                val lockState = try { kotlinx.coroutines.runBlocking(kotlinx.coroutines.Dispatchers.IO) { OriginDatabase.getInstance(context).threadLockDao().getForThread(threadId) } } catch(_:Exception){null}
        val isLocked = lockState?.isLocked == true
        val isHidden = lockState?.isHidden == true
        if (isHidden) return

        val muteUntil = lockState?.muteUntilMillis ?: 0L
        val isMuted = lockState?.isMuted == true &&
            (muteUntil == -1L || muteUntil > System.currentTimeMillis())
        if (isMuted) return

        val title  = if (isLocked) context.getString(R.string.app_name) else displayName
        val content = if (isLocked) context.getString(R.string.notif_locked_chat_content) else body

        data class MsgEntry(val body: String, val date: Long, val isSent: Boolean)
        val history: List<MsgEntry> = try {
            val cur = context.contentResolver.query(
                android.provider.Telephony.Sms.CONTENT_URI,
                arrayOf(android.provider.Telephony.Sms.BODY, android.provider.Telephony.Sms.DATE, android.provider.Telephony.Sms.TYPE),
                "${android.provider.Telephony.Sms.THREAD_ID} = ? AND ${android.provider.Telephony.Sms.READ} = 0 AND ${android.provider.Telephony.Sms.TYPE} = ${android.provider.Telephony.Sms.MESSAGE_TYPE_INBOX}", arrayOf(threadId.toString()),
                "${android.provider.Telephony.Sms.DATE} ASC")
            val msgs = mutableListOf<MsgEntry>()
            cur?.use { while (it.moveToNext()) {
                val b=it.getColumnIndex(android.provider.Telephony.Sms.BODY)
                val d=it.getColumnIndex(android.provider.Telephony.Sms.DATE)
                val t=it.getColumnIndex(android.provider.Telephony.Sms.TYPE)
                if (b>=0&&d>=0) msgs.add(MsgEntry(if(isLocked)context.getString(R.string.notif_locked_chat_content) else it.getString(b), it.getLong(d), t>=0&&it.getInt(t)==android.provider.Telephony.Sms.MESSAGE_TYPE_SENT))
            }}
            msgs.reversed()
        } catch (_: Exception) { listOf(MsgEntry(content, System.currentTimeMillis(), false)) }

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
                putExtra(ThreadActivity.EXTRA_DISPLAY_NAME, displayName)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
        val contentPendingIntent = PendingIntent.getActivity(
            context, threadId.toInt(), contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // --- Point 4: Contact avatar as large icon ---
        val contactAvatar = loadContactAvatar(address)

        // --- Point 3 & 4: MessagingStyle with sender name + avatar ---
        val personBuilder = Person.Builder().setName(title)
        if (contactAvatar != null) {
            personBuilder.setIcon(IconCompat.createWithBitmap(contactAvatar))
        }
        val person = personBuilder.build()

        val messagingStyle = NotificationCompat.MessagingStyle(
            Person.Builder().setName("You").build()
        )
        messagingStyle.conversationTitle = null // no group title for 1:1

        // Add last 5 messages to MessagingStyle
        history.forEach { e -> messagingStyle.addMessage(NotificationCompat.MessagingStyle.Message(e.body, e.date, if(e.isSent) null else person)) }

        val builder = NotificationCompat.Builder(context, OriginSmsApp.CHANNEL_MESSAGES)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(messagingStyle)
            .setAutoCancel(true)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setGroup("thread_$threadId")

        // Large icon = contact avatar
        if (contactAvatar != null) {
            builder.setLargeIcon(contactAvatar)
        }

        // --- Point 4: Smart reply suggestions ---
        if (!isLocked) {
            val smartReplies = arrayOf("OK", "Thanks!", "On my way", "Can't talk now", "👍")
            smartReplies.forEach { reply ->
                val replyIntent = Intent(context, NotificationActionReceiver::class.java).apply {
                    action = NotificationActionReceiver.ACTION_QUICK_REPLY
                    putExtra(NotificationActionReceiver.EXTRA_THREAD_ID, threadId)
                    putExtra(NotificationActionReceiver.EXTRA_ADDRESS, address)
                    putExtra(NotificationActionReceiver.KEY_QUICK_REPLY, reply)
                }
                // Smart reply as inline action chip
            }
            builder.addAction(buildMarkReadAction(threadId))
            builder.addAction(buildQuickReplyAction(threadId, address))
        }

        manager.notify(threadId.toInt(), builder.build())
    }

    private fun loadContactAvatar(address: String): Bitmap? {
        return try {
            val contact = ContactsHelper(context).resolve(address)
            val photoUri = contact.photoUri ?: return null
            context.contentResolver.openInputStream(Uri.parse(photoUri))?.use { stream ->
                BitmapFactory.decodeStream(stream)
            }
        } catch (_: Exception) { null }
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
        return NotificationCompat.Action.Builder(
            0, context.getString(R.string.notif_action_mark_read), pendingIntent
        ).build()
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
        return NotificationCompat.Action.Builder(
            0, context.getString(R.string.notif_action_reply), pendingIntent
        ).addRemoteInput(remoteInput).build()
    }

    fun cancel(threadId: Long) {
        messageHistory.remove(threadId) // clear history when dismissed
        manager.cancel(threadId.toInt())
        try { manager.cancel("group_sms", 0) } catch (_: Exception) {}
    }
}
