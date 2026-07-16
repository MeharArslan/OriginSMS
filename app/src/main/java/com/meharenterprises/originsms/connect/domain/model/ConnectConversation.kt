package com.meharenterprises.originsms.connect.domain.model

data class ConnectConversation(
    val id: String,
    val participantId: String,
    val participantName: String,
    val participantAvatar: String? = null,
    val lastMessage: String = "",
    val lastMessageTime: Long = 0L,
    val unreadCount: Int = 0,
    val isOnline: Boolean = false,
    val isPinned: Boolean = false,
    val isArchived: Boolean = false,
    val isMuted: Boolean = false,
    val isTyping: Boolean = false
)
