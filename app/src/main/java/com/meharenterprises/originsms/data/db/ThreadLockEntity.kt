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
    val muteUntilMillis: Long = 0L,   // 0 = not muted, -1 = muted indefinitely, >0 = muted until this timestamp
    val autoUnhideAtMillis: Long = 0L  // 0 = no scheduled unhide
)
