package com.meharenterprises.originsms.core

enum class MessageType { SMS, MMS }
enum class MessageBox { INBOX, SENT, DRAFT, OUTBOX, FAILED, QUEUED }

data class Attachment(
    val partId: Long,
    val contentType: String,
    val uri: String,
    val fileName: String?
)

data class Message(
    val id: Long,
    val threadId: Long,
    val address: String,
    val body: String,
    val dateMillis: Long,
    val dateSentMillis: Long,
    val type: MessageType,
    val box: MessageBox,
    val isRead: Boolean,
    val attachments: List<Attachment> = emptyList(),
    val isStarred: Boolean = false
) {
    // For fake scheduled messages (id < 0), dateMillis IS the scheduled time
    fun scheduledTimeOrDate(): Long = dateMillis
}
