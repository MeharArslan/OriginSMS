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
    val lockedAtMillis: Long = 0L
)
