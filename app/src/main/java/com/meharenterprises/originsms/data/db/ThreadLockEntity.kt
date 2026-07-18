package com.meharenterprises.originsms.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Stores the lock/visibility state for a conversation thread.
 * The threadId matches Telephony.Threads._ID from the system SMS provider —
 * actual message content is never duplicated here, only app-level metadata.
 */
@Entity(tableName = "thread_lock_state")
data class ThreadLockEntity(
    @PrimaryKey val threadId: Long,
    val isLocked: Boolean = false,
    val isHidden: Boolean = false,
    val lockedAtMillis: Long = 0L,
    val isMuted: Boolean = false,
    val isArchived: Boolean = false,
    val muteUntilMillis: Long = 0L,
    val autoUnhideAtMillis: Long = 0L,
    val dailyHideTimeMinutes: Int = -1,
    // Trash: >0 = deleted at this timestamp (auto-delete after 30 days), 0 = not deleted
    val deletedAtMillis: Long = 0L,
    // New chat after trash: messages before this timestamp are hidden until user restores
    val newChatStartMillis: Long = 0L
)
