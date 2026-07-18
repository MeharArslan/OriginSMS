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
        try {
            // Single query fetching ALL needed columns at once to avoid N+1 queries.
            // Previously this did 3 separate DB hits per thread (latest msg, contact,
            // unread count) — with 50 threads that was 150 queries. Now it is 1 query
            // for all SMS data + 1 bulk Room query for lock states = 2 total, regardless
            // of how many threads there are.
            val projection = arrayOf(
                Telephony.Sms.THREAD_ID,
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY,
                Telephony.Sms.DATE,
                Telephony.Sms.READ,
                Telephony.Sms.TYPE
            )
            // Pre-load ALL lock states in one Room query
            val allLockStates = database.threadLockDao().getAllLockStates()
                .associateBy { it.threadId }

            // Pre-load drafts from Room
            val allDrafts = try {
                database.draftDao().getAllDrafts().associateBy { it.threadId }
            } catch (_: Exception) { emptyMap() }

            // One SMS query sorted by date — we keep the first (latest) row per thread
            context.contentResolver.query(
                Telephony.Sms.CONTENT_URI,
                projection,
                null, null,
                "${Telephony.Sms.DATE} DESC"
            )?.use { cursor ->
                val seenThreadIds = mutableSetOf<Long>()
                // Track unread counts per thread in one pass
                val unreadCounts = mutableMapOf<Long, Int>()
                // First pass: collect unread counts
                val rows = mutableListOf<Array<Any?>>()
                val tidIdx = cursor.getColumnIndex(Telephony.Sms.THREAD_ID)
                val addrIdx = cursor.getColumnIndex(Telephony.Sms.ADDRESS)
                val bodyIdx = cursor.getColumnIndex(Telephony.Sms.BODY)
                val dateIdx = cursor.getColumnIndex(Telephony.Sms.DATE)
                val readIdx = cursor.getColumnIndex(Telephony.Sms.READ)
                if (tidIdx < 0) return@use

                while (cursor.moveToNext()) {
                    val threadId = cursor.getLong(tidIdx)
                    val read = if (readIdx >= 0) cursor.getInt(readIdx) else 1
                    if (read == 0) unreadCounts[threadId] = (unreadCounts[threadId] ?: 0) + 1
                    if (seenThreadIds.add(threadId)) {
                        // First row for this thread = latest message
                        rows.add(arrayOf(
                            threadId,
                            if (addrIdx >= 0) cursor.getString(addrIdx) else "",
                            if (bodyIdx >= 0) cursor.getString(bodyIdx) ?: "" else "",
                            if (dateIdx >= 0) cursor.getLong(dateIdx) else 0L,
                            read
                        ))
                    }
                }

                // Batch-resolve all addresses in one pass using the static cache.
                // Contacts already in cache (from a previous load) resolve instantly.
                // New contacts are looked up once and cached; subsequent loads are free.
                for (row in rows) {
                    val threadId = row[0] as Long
                    val address = (row[1] as? String).orEmpty()
                    if (address.isBlank()) continue
                    val body = (row[2] as? String).orEmpty()
                    val dateMillis = row[3] as Long
                    val isRead = (row[4] as Int) == 1
                    val lockState = allLockStates[threadId]

                    // Use cache-first lookup — if not cached yet, resolve will
                    // do one ContentProvider query and store result in static cache
                    val contact = contactsHelper.resolve(address)
                    results.add(
                        ConversationSummary(
                            threadId = threadId,
                            address = address,
                            displayName = contact.displayName,
                            snippet = body,
                            dateMillis = dateMillis,
                            isRead = isRead,
                            isLocked = lockState?.isLocked == true,
                            isHidden = lockState?.isHidden == true,
                            isMuted = lockState?.isMuted == true,
                            isArchived = lockState?.isArchived == true,
                            isDeleted = (lockState?.deletedAtMillis ?: 0L) > 0L,
                            unreadCount = unreadCounts[threadId] ?: 0,
                            contactPhotoUri = contact.photoUri,
                            draftText = allDrafts[threadId]?.text?.takeIf { it.isNotBlank() }
                        )
                    )
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("SmsRepo", "getConversations", e)
        }
        results
    }

    private data class LatestMessage(val address: String, val body: String, val dateMillis: Long)

    private fun getLatestMessageForThread(threadId: Long): LatestMessage? {
        context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(Telephony.Sms.ADDRESS, Telephony.Sms.BODY, Telephony.Sms.DATE),
            "${Telephony.Sms.THREAD_ID} = ?",
            arrayOf(threadId.toString()),
            "${Telephony.Sms.DATE} DESC LIMIT 1"
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val addrIdx = cursor.getColumnIndex(Telephony.Sms.ADDRESS)
                val bodyIdx = cursor.getColumnIndex(Telephony.Sms.BODY)
                val dateIdx = cursor.getColumnIndex(Telephony.Sms.DATE)
                val address = if (addrIdx >= 0) cursor.getString(addrIdx) else null
                if (!address.isNullOrBlank()) {
                    return LatestMessage(
                        address = address,
                        body = if (bodyIdx >= 0) cursor.getString(bodyIdx).orEmpty() else "",
                        dateMillis = if (dateIdx >= 0) cursor.getLong(dateIdx) else 0L
                    )
                }
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
     *
     * On dual-SIM devices, passing a subscriptionId routes the message through
     * that specific SIM's SmsManager instance; passing null uses the system's
     * current default SMS subscription.
     */
    fun sendSms(destinationAddress: String, body: String, threadId: Long?, subscriptionId: Int? = null) {
        val smsManager = if (subscriptionId != null && subscriptionId != -1) {
            SmsManager.getSmsManagerForSubscriptionId(subscriptionId)
        } else {
            context.getSystemService(SmsManager::class.java)
        }
        val parts = smsManager.divideMessage(body)

        val sentIntents = ArrayList<PendingIntent>()
        val deliveredIntents = ArrayList<PendingIntent>()

        for (i in parts.indices) {
            // A monotonically increasing counter guarantees a unique
            // PendingIntent requestCode per part, per send. The previous
            // implementation derived the requestCode from
            // (System.currentTimeMillis() % 100000), which could collide
            // when multiple messages were sent in quick succession — with
            // FLAG_UPDATE_CURRENT, a colliding requestCode silently reuses
            // an existing PendingIntent's extras instead of creating a new
            // one, which made the wrong message's sent/failed callback fire.
            val requestCode = nextPendingIntentRequestCode()

            val sentIntent = Intent(context, SendStatusReceiver::class.java).apply {
                action = ACTION_SMS_SENT
                putExtra(EXTRA_PART_INDEX, i)
            }
            sentIntents.add(
                PendingIntent.getBroadcast(
                    context, requestCode,
                    sentIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            val deliveredIntent = Intent(context, DeliveryStatusReceiver::class.java).apply {
                action = ACTION_SMS_DELIVERED
            }
            deliveredIntents.add(
                PendingIntent.getBroadcast(
                    context, nextPendingIntentRequestCode(),
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
            context, nextPendingIntentRequestCode(),
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
    }

    suspend fun markThreadRead(threadId: Long) = withContext(Dispatchers.IO) {
        val values = ContentValues().apply { put(Telephony.Sms.READ, 1) }
        context.contentResolver.update(
            Telephony.Sms.CONTENT_URI, values,
            "${Telephony.Sms.THREAD_ID} = ? AND ${Telephony.Sms.READ} = 0",
            arrayOf(threadId.toString())
        )
    }

    suspend fun markThreadUnread(threadId: Long) = withContext(Dispatchers.IO) {
        // Mark only the latest message as unread so the thread shows an unread indicator
        val latestId = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(Telephony.Sms._ID),
            "${Telephony.Sms.THREAD_ID} = ?",
            arrayOf(threadId.toString()),
            "${Telephony.Sms.DATE} DESC LIMIT 1"
        )?.use { c ->
            if (c.moveToFirst()) c.getLong(c.getColumnIndex(Telephony.Sms._ID)) else null
        }
        if (latestId != null) {
            val values = ContentValues().apply { put(Telephony.Sms.READ, 0) }
            context.contentResolver.update(
                Telephony.Sms.CONTENT_URI, values,
                "${Telephony.Sms._ID} = ?",
                arrayOf(latestId.toString())
            )
        }
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

    companion object {
        const val ACTION_SMS_SENT = "com.meharenterprises.originsms.SMS_SENT"
        const val ACTION_SMS_DELIVERED = "com.meharenterprises.originsms.SMS_DELIVERED"
        const val ACTION_MMS_SENT = "com.meharenterprises.originsms.MMS_SENT"
        const val EXTRA_PART_INDEX = "part_index"

        // Shared across all SmsRepository instances in the process so every
        // PendingIntent requestCode handed to SmsManager is guaranteed unique,
        // even across rapid successive sends.
        private val pendingIntentRequestCodeCounter = java.util.concurrent.atomic.AtomicInteger(0)

        private fun nextPendingIntentRequestCode(): Int = pendingIntentRequestCodeCounter.incrementAndGet()
    }
}
