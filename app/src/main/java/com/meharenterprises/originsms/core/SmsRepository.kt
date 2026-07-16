package com.meharenterprises.originsms.core

import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Telephony
import android.telephony.SmsManager
import com.meharenterprises.originsms.data.db.OriginDatabase
import com.meharenterprises.originsms.receivers.DeliveryStatusReceiver
import com.meharenterprises.originsms.receivers.SendStatusReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Central repository for all SMS read/write operations.
 * Reads go through the system Telephony ContentProvider (the single source of truth
 * for message data — required so other apps continue to see consistent data if the
 * default-SMS-app role ever changes hands). Writes use SmsManager for sending and
 * the provider for persisting sent/draft copies.
 */
class SmsRepository(private val context: Context) {

    private val contactsHelper = ContactsHelper(context)
    private val database: OriginDatabase by lazy { OriginDatabase.getInstance(context) }

    // ---------------------------------------------------------------------
    // Conversation list
    // ---------------------------------------------------------------------

    suspend fun getConversations(): List<ConversationSummary> = withContext(Dispatchers.IO) {
        val results = mutableListOf<ConversationSummary>()

        val projection = arrayOf(
            Telephony.Threads._ID,
            Telephony.Threads.RECIPIENT_IDS,
            Telephony.Threads.SNIPPET,
            Telephony.Threads.DATE,
            Telephony.Threads.READ,
            Telephony.Threads.MESSAGE_COUNT
        )

        context.contentResolver.query(
            Telephony.Threads.CONTENT_URI,
            projection,
            null, null,
            "${Telephony.Threads.DATE} DESC"
        )?.use { cursor ->
            val idIdx = cursor.getColumnIndex(Telephony.Threads._ID)
            val snippetIdx = cursor.getColumnIndex(Telephony.Threads.SNIPPET)
            val dateIdx = cursor.getColumnIndex(Telephony.Threads.DATE)
            val readIdx = cursor.getColumnIndex(Telephony.Threads.READ)

            while (cursor.moveToNext()) {
                val threadId = cursor.getLong(idIdx)
                val address = getAddressForThread(threadId) ?: continue
                val contact = contactsHelper.resolve(address)
                val lockState = database.threadLockDao().getForThread(threadId)
                val unreadCount = getUnreadCountForThread(threadId)

                results.add(
                    ConversationSummary(
                        threadId = threadId,
                        address = address,
                        displayName = contact.displayName,
                        snippet = if (snippetIdx >= 0) cursor.getString(snippetIdx).orEmpty() else "",
                        dateMillis = if (dateIdx >= 0) cursor.getLong(dateIdx) else 0L,
                        isRead = if (readIdx >= 0) cursor.getInt(readIdx) == 1 else true,
                        isLocked = lockState?.isLocked == true,
                        isHidden = lockState?.isHidden == true,
                        unreadCount = unreadCount,
                        contactPhotoUri = contact.photoUri
                    )
                )
            }
        }
        results
    }

    private fun getAddressForThread(threadId: Long): String? {
        context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(Telephony.Sms.ADDRESS),
            "${Telephony.Sms.THREAD_ID} = ?",
            arrayOf(threadId.toString()),
            "${Telephony.Sms.DATE} DESC LIMIT 1"
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val idx = cursor.getColumnIndex(Telephony.Sms.ADDRESS)
                if (idx >= 0) return cursor.getString(idx)
            }
        }
        return null
    }

    private fun getUnreadCountForThread(threadId: Long): Int {
        context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(Telephony.Sms._ID),
            "${Telephony.Sms.THREAD_ID} = ? AND ${Telephony.Sms.READ} = 0",
            arrayOf(threadId.toString()),
            null
        )?.use { return it.count }
        return 0
    }

    // ---------------------------------------------------------------------
    // Messages within a thread
    // ---------------------------------------------------------------------

    suspend fun getMessages(threadId: Long): List<Message> = withContext(Dispatchers.IO) {
        val messages = mutableListOf<Message>()
        messages.addAll(getSmsMessages(threadId))
        messages.addAll(getMmsMessages(threadId))
        messages.sortBy { it.dateMillis }
        messages
    }

    private fun getSmsMessages(threadId: Long): List<Message> {
        val messages = mutableListOf<Message>()
        val projection = arrayOf(
            Telephony.Sms._ID,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.BODY,
            Telephony.Sms.DATE,
            Telephony.Sms.DATE_SENT,
            Telephony.Sms.TYPE,
            Telephony.Sms.READ
        )

        context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            projection,
            "${Telephony.Sms.THREAD_ID} = ?",
            arrayOf(threadId.toString()),
            "${Telephony.Sms.DATE} ASC"
        )?.use { cursor ->
            val idIdx = cursor.getColumnIndex(Telephony.Sms._ID)
            val addrIdx = cursor.getColumnIndex(Telephony.Sms.ADDRESS)
            val bodyIdx = cursor.getColumnIndex(Telephony.Sms.BODY)
            val dateIdx = cursor.getColumnIndex(Telephony.Sms.DATE)
            val dateSentIdx = cursor.getColumnIndex(Telephony.Sms.DATE_SENT)
            val typeIdx = cursor.getColumnIndex(Telephony.Sms.TYPE)
            val readIdx = cursor.getColumnIndex(Telephony.Sms.READ)

            while (cursor.moveToNext()) {
                val smsType = if (typeIdx >= 0) cursor.getInt(typeIdx) else Telephony.Sms.MESSAGE_TYPE_INBOX
                messages.add(
                    Message(
                        id = cursor.getLong(idIdx),
                        threadId = threadId,
                        address = if (addrIdx >= 0) cursor.getString(addrIdx).orEmpty() else "",
                        body = if (bodyIdx >= 0) cursor.getString(bodyIdx).orEmpty() else "",
                        dateMillis = if (dateIdx >= 0) cursor.getLong(dateIdx) else 0L,
                        dateSentMillis = if (dateSentIdx >= 0) cursor.getLong(dateSentIdx) else 0L,
                        type = MessageType.SMS,
                        box = mapSmsBox(smsType),
                        isRead = if (readIdx >= 0) cursor.getInt(readIdx) == 1 else true
                    )
                )
            }
        }
        return messages
    }

    /**
     * MMS storage in the Telephony provider is split across three tables:
     * Mms (the message row + date/box), Addr (sender/recipient addresses
     * keyed by message id), and Part (each attachment, including a text/plain
     * part that holds the body text for MMS with a text component). This
     * mirrors how every default SMS app reads MMS content.
     */
    private fun getMmsMessages(threadId: Long): List<Message> {
        val messages = mutableListOf<Message>()
        val projection = arrayOf(
            Telephony.Mms._ID,
            Telephony.Mms.DATE,
            Telephony.Mms.DATE_SENT,
            Telephony.Mms.MESSAGE_BOX,
            Telephony.Mms.READ
        )

        context.contentResolver.query(
            Telephony.Mms.CONTENT_URI,
            projection,
            "${Telephony.Mms.THREAD_ID} = ?",
            arrayOf(threadId.toString()),
            "${Telephony.Mms.DATE} ASC"
        )?.use { cursor ->
            val idIdx = cursor.getColumnIndex(Telephony.Mms._ID)
            val dateIdx = cursor.getColumnIndex(Telephony.Mms.DATE)
            val dateSentIdx = cursor.getColumnIndex(Telephony.Mms.DATE_SENT)
            val boxIdx = cursor.getColumnIndex(Telephony.Mms.MESSAGE_BOX)
            val readIdx = cursor.getColumnIndex(Telephony.Mms.READ)

            while (cursor.moveToNext()) {
                val mmsId = cursor.getLong(idIdx)
                val mmsBox = if (boxIdx >= 0) cursor.getInt(boxIdx) else Telephony.Mms.MESSAGE_BOX_INBOX
                // Telephony.Mms.DATE is stored in seconds, unlike Sms.DATE which is milliseconds.
                val dateSeconds = if (dateIdx >= 0) cursor.getLong(dateIdx) else 0L
                val dateSentSeconds = if (dateSentIdx >= 0) cursor.getLong(dateSentIdx) else 0L

                val parts = getMmsParts(mmsId)
                val textBody = parts.filterIsInstance<MmsPart.Text>().joinToString(" ") { it.text }
                val attachments = parts.filterIsInstance<MmsPart.Binary>().map {
                    Attachment(partId = it.partId, contentType = it.contentType, uri = it.uri, fileName = it.fileName)
                }
                val senderAddress = getMmsSenderAddress(mmsId)

                messages.add(
                    Message(
                        id = mmsId,
                        threadId = threadId,
                        address = senderAddress,
                        body = textBody,
                        dateMillis = dateSeconds * 1000L,
                        dateSentMillis = dateSentSeconds * 1000L,
                        type = MessageType.MMS,
                        box = mapMmsBox(mmsBox),
                        isRead = if (readIdx >= 0) cursor.getInt(readIdx) == 1 else true,
                        attachments = attachments
                    )
                )
            }
        }
        return messages
    }

    private sealed class MmsPart {
        data class Text(val text: String) : MmsPart()
        data class Binary(val partId: Long, val contentType: String, val uri: String, val fileName: String?) : MmsPart()
    }

    private fun getMmsParts(mmsId: Long): List<MmsPart> {
        val parts = mutableListOf<MmsPart>()
        val partUri = Uri.parse("content://mms/part")
        val projection = arrayOf("_id", "ct", "text", "name", "cl")

        context.contentResolver.query(
            partUri,
            projection,
            "mid = ?",
            arrayOf(mmsId.toString()),
            null
        )?.use { cursor ->
            val idIdx = cursor.getColumnIndex("_id")
            val ctIdx = cursor.getColumnIndex("ct")
            val textIdx = cursor.getColumnIndex("text")
            val nameIdx = cursor.getColumnIndex("name")
            val clIdx = cursor.getColumnIndex("cl")

            while (cursor.moveToNext()) {
                val partId = if (idIdx >= 0) cursor.getLong(idIdx) else continue
                val contentType = if (ctIdx >= 0) cursor.getString(ctIdx).orEmpty() else ""

                when {
                    contentType.startsWith("text/") -> {
                        val text = if (textIdx >= 0) cursor.getString(textIdx) else null
                        if (!text.isNullOrBlank()) parts.add(MmsPart.Text(text))
                    }
                    contentType.startsWith("image/") || contentType.startsWith("video/") || contentType.startsWith("audio/") -> {
                        val fileName = if (nameIdx >= 0) cursor.getString(nameIdx) else if (clIdx >= 0) cursor.getString(clIdx) else null
                        val partContentUri = Uri.parse("content://mms/part/$partId")
                        parts.add(MmsPart.Binary(partId, contentType, partContentUri.toString(), fileName))
                    }
                }
            }
        }
        return parts
    }

    private fun getMmsSenderAddress(mmsId: Long): String {
        val addrUri = Uri.parse("content://mms/$mmsId/addr")
        context.contentResolver.query(
            addrUri,
            arrayOf("address", "type"),
            null, null, null
        )?.use { cursor ->
            val addrIdx = cursor.getColumnIndex("address")
            val typeIdx = cursor.getColumnIndex("type")
            while (cursor.moveToNext()) {
                // PduHeaders.FROM = 137 — the sender's address row in the addr table.
                val type = if (typeIdx >= 0) cursor.getInt(typeIdx) else -1
                if (type == 137 && addrIdx >= 0) {
                    return cursor.getString(addrIdx).orEmpty()
                }
            }
        }
        return ""
    }

    private fun mapMmsBox(mmsBox: Int): MessageBox = when (mmsBox) {
        Telephony.Mms.MESSAGE_BOX_INBOX -> MessageBox.INBOX
        Telephony.Mms.MESSAGE_BOX_SENT -> MessageBox.SENT
        Telephony.Mms.MESSAGE_BOX_DRAFTS -> MessageBox.DRAFT
        Telephony.Mms.MESSAGE_BOX_OUTBOX -> MessageBox.OUTBOX
        Telephony.Mms.MESSAGE_BOX_FAILED -> MessageBox.FAILED
        else -> MessageBox.INBOX
    }

    private fun mapSmsBox(smsType: Int): MessageBox = when (smsType) {
        Telephony.Sms.MESSAGE_TYPE_INBOX -> MessageBox.INBOX
        Telephony.Sms.MESSAGE_TYPE_SENT -> MessageBox.SENT
        Telephony.Sms.MESSAGE_TYPE_DRAFT -> MessageBox.DRAFT
        Telephony.Sms.MESSAGE_TYPE_OUTBOX -> MessageBox.OUTBOX
        Telephony.Sms.MESSAGE_TYPE_FAILED -> MessageBox.FAILED
        Telephony.Sms.MESSAGE_TYPE_QUEUED -> MessageBox.QUEUED
        else -> MessageBox.INBOX
    }

    // ---------------------------------------------------------------------
    // Sending
    // ---------------------------------------------------------------------

    /**
     * Sends an SMS, splitting into multipart segments if needed, and writes a
     * SENT-box copy to the provider so it appears immediately in the thread.
     * As default SMS app, this app is responsible for persisting its own sent messages.
     */
    fun sendSms(destinationAddress: String, body: String, threadId: Long?) {
        val smsManager = context.getSystemService(SmsManager::class.java)
        val parts = smsManager.divideMessage(body)

        val sentIntents = ArrayList<PendingIntent>()
        val deliveredIntents = ArrayList<PendingIntent>()

        for (i in parts.indices) {
            val sentIntent = Intent(context, SendStatusReceiver::class.java).apply {
                action = ACTION_SMS_SENT
                putExtra(EXTRA_PART_INDEX, i)
            }
            sentIntents.add(
                PendingIntent.getBroadcast(
                    context, (System.currentTimeMillis() % 100000).toInt() + i,
                    sentIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            val deliveredIntent = Intent(context, DeliveryStatusReceiver::class.java).apply {
                action = ACTION_SMS_DELIVERED
            }
            deliveredIntents.add(
                PendingIntent.getBroadcast(
                    context, (System.currentTimeMillis() % 100000).toInt() + i + 500,
                    deliveredIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        }

        smsManager.sendMultipartTextMessage(destinationAddress, null, parts, sentIntents, deliveredIntents)

        val values = ContentValues().apply {
            put(Telephony.Sms.ADDRESS, destinationAddress)
            put(Telephony.Sms.BODY, body)
            put(Telephony.Sms.DATE, System.currentTimeMillis())
            put(Telephony.Sms.READ, 1)
            put(Telephony.Sms.TYPE, Telephony.Sms.MESSAGE_TYPE_SENT)
            threadId?.let { put(Telephony.Sms.THREAD_ID, it) }
        }
        context.contentResolver.insert(Telephony.Sms.CONTENT_URI, values)
    }

    /**
     * Sends an MMS containing one or more attachment URIs plus optional text.
     * Builds a real MMS PDU (M-Send.req) via MmsPduBuilder and hands it to
     * SmsManager.sendMultimediaMessage, which is the standard Android API used
     * by every default messaging app since Android 5.0 — it manages the actual
     * carrier MMSC HTTP transaction, APN routing, and roaming considerations
     * internally so this app doesn't need carrier-specific networking code.
     */
    fun sendMms(destinationAddress: String, body: String, attachmentUris: List<Uri>, threadId: Long?) {
        val pduFile = java.io.File(context.cacheDir, "send_${System.currentTimeMillis()}.dat")
        val pduBytes = MmsPduBuilder.buildSendRequest(
            context = context,
            destinationAddress = destinationAddress,
            text = body,
            attachmentUris = attachmentUris
        )
        pduFile.writeBytes(pduBytes)
        val pduUri = androidx.core.content.FileProvider.getUriForFile(
            context, "${context.packageName}.fileprovider", pduFile
        )

        val sentIntent = Intent(context, SendStatusReceiver::class.java).apply { action = ACTION_MMS_SENT }
        val sentPendingIntent = PendingIntent.getBroadcast(
            context, (System.currentTimeMillis() % 100000).toInt(),
            sentIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val smsManager = context.getSystemService(SmsManager::class.java)
        smsManager.sendMultimediaMessage(context, pduUri, null, null, sentPendingIntent)

        // Persist our own sent copy so it appears immediately; the system MMS
        // tables get the authoritative copy once the MMSC transaction completes,
        // but the optimistic local row keeps the thread UI responsive meanwhile.
        val values = ContentValues().apply {
            put(Telephony.Sms.ADDRESS, destinationAddress)
            put(Telephony.Sms.BODY, body)
            put(Telephony.Sms.DATE, System.currentTimeMillis())
            put(Telephony.Sms.READ, 1)
            put(Telephony.Sms.TYPE, Telephony.Sms.MESSAGE_TYPE_SENT)
            threadId?.let { put(Telephony.Sms.THREAD_ID, it) }
        }
        context.contentResolver.insert(Telephony.Sms.CONTENT_URI, values)

    suspend fun markThreadRead(threadId: Long) = withContext(Dispatchers.IO) {
        val values = ContentValues().apply { put(Telephony.Sms.READ, 1) }
        context.contentResolver.update(
            Telephony.Sms.CONTENT_URI,
            values,
            "${Telephony.Sms.THREAD_ID} = ? AND ${Telephony.Sms.READ} = 0",
            arrayOf(threadId.toString())
        )
    }

    suspend fun deleteThread(threadId: Long) = withContext(Dispatchers.IO) {
        context.contentResolver.delete(
            Telephony.Sms.CONTENT_URI,
            "${Telephony.Sms.THREAD_ID} = ?",
            arrayOf(threadId.toString())
        )
        database.threadLockDao().clear(threadId)
    }

    suspend fun deleteMessage(messageId: Long) = withContext(Dispatchers.IO) {
        context.contentResolver.delete(
            Telephony.Sms.CONTENT_URI,
            "${Telephony.Sms._ID} = ?",
            arrayOf(messageId.toString())
        )
    }

}

const val ACTION_SMS_SENT = "com.meharenterprises.originsms.SMS_SENT"
const val ACTION_SMS_DELIVERED = "com.meharenterprises.originsms.SMS_DELIVERED"
const val ACTION_MMS_SENT = "com.meharenterprises.originsms.MMS_SENT"
const val EXTRA_PART_INDEX = "part_index"
