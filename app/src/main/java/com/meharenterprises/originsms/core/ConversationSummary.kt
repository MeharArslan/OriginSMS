package com.meharenterprises.originsms.core

/**
 * Lightweight view-model representation of a single row in the conversation list.
 * Built by joining Telephony.Threads/Sms data with our local lock-state metadata.
 */
data class ConversationSummary(
    val threadId: Long,
    val address: String,
    val displayName: String,
    val snippet: String,
    val dateMillis: Long,
    val isRead: Boolean,
    val isLocked: Boolean,
    val isHidden: Boolean,
    val unreadCount: Int,
    val contactPhotoUri: String? = null
)
