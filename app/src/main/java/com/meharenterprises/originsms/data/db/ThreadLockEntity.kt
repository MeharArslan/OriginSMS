package com.meharenterprises.originsms.data.db
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "thread_lock_state")
data class ThreadLockEntity(@PrimaryKey val threadId: Long, val isLocked: Boolean = false, val isHidden: Boolean = false, val lockedAtMillis: Long = 0L, val isMuted: Boolean = false, val isArchived: Boolean = false, val muteUntilMillis: Long = 0L, val autoUnhideAtMillis: Long = 0L, val dailyHideTimeMinutes: Int = -1, val deletedAtMillis: Long = 0L, val newChatStartMillis: Long = 0L)
