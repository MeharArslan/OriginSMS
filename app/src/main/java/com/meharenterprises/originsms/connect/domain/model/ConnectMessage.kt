package com.meharenterprises.originsms.connect.domain.model

enum class MessageType { TEXT, IMAGE, VIDEO, AUDIO, DOCUMENT, DELETED }
enum class MessageStatus { SENDING, SENT, DELIVERED, READ, FAILED }

data class ConnectMessage(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val type: MessageType = MessageType.TEXT,
    val body: String = "",
    val mediaUrl: String? = null,
    val mediaLocalPath: String? = null,
    val replyToId: String? = null,
    val status: MessageStatus = MessageStatus.SENDING,
    val createdAt: Long = System.currentTimeMillis(),
    val editedAt: Long? = null,
    val isStarred: Boolean = false,
    val isDeletedForMe: Boolean = false
)
