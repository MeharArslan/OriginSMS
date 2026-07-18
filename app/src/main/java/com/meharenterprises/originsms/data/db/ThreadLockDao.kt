package com.meharenterprises.originsms.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ThreadLockDao {

    @Upsert
    suspend fun upsert(entity: ThreadLockEntity)

    @Query("SELECT * FROM thread_lock_state WHERE threadId = :threadId")
    suspend fun getForThread(threadId: Long): ThreadLockEntity?

    @Query("SELECT * FROM thread_lock_state")
    suspend fun getAllLockStates(): List<ThreadLockEntity>

    @Query("SELECT * FROM thread_lock_state")
    fun observeAll(): Flow<List<ThreadLockEntity>>

    @Query("SELECT * FROM thread_lock_state WHERE isLocked = 1")
    suspend fun getAllLocked(): List<ThreadLockEntity>

    @Query("SELECT * FROM thread_lock_state WHERE isHidden = 1")
    suspend fun getAllHidden(): List<ThreadLockEntity>

    @Query("UPDATE thread_lock_state SET isLocked = :locked, lockedAtMillis = :ts WHERE threadId = :threadId")
    suspend fun setLocked(threadId: Long, locked: Boolean, ts: Long)

    @Query("UPDATE thread_lock_state SET isHidden = :hidden WHERE threadId = :threadId")
    suspend fun setHidden(threadId: Long, hidden: Boolean)

    @Query("UPDATE thread_lock_state SET isMuted = :muted WHERE threadId = :threadId")
    suspend fun setMuted(threadId: Long, muted: Boolean)

    @Query("UPDATE thread_lock_state SET isMuted = :muted, muteUntilMillis = :muteUntilMillis WHERE threadId = :threadId")
    suspend fun setMutedUntil(threadId: Long, muted: Boolean, muteUntilMillis: Long)

    @Query("UPDATE thread_lock_state SET isArchived = :archived WHERE threadId = :threadId")
    suspend fun setArchived(threadId: Long, archived: Boolean)

    @Query("UPDATE thread_lock_state SET autoUnhideAtMillis = :timestamp WHERE threadId = :threadId")
    suspend fun setAutoUnhideAt(threadId: Long, timestamp: Long)

    @Query("SELECT * FROM thread_lock_state WHERE deletedAtMillis > 0 ORDER BY deletedAtMillis DESC")
    suspend fun getTrashedThreads(): List<ThreadLockEntity>

    @Query("UPDATE thread_lock_state SET deletedAtMillis = :millis WHERE threadId = :threadId")
    suspend fun setDeletedAt(threadId: Long, millis: Long)

    @Query("SELECT * FROM thread_lock_state WHERE deletedAtMillis > 0 AND deletedAtMillis <= :cutoff")
    suspend fun getThreadsDueForPermanentDeletion(cutoff: Long): List<ThreadLockEntity>

    @Query("SELECT * FROM thread_lock_state WHERE dailyHideTimeMinutes >= 0")
    suspend fun getThreadsWithDailyHide(): List<ThreadLockEntity>

    @Query("UPDATE thread_lock_state SET dailyHideTimeMinutes = :minutes WHERE threadId = :threadId")
    suspend fun setDailyHideTime(threadId: Long, minutes: Int)

    @Query("SELECT * FROM thread_lock_state WHERE isHidden = 0 AND autoUnhideAtMillis > 0 AND autoUnhideAtMillis <= :now")
    suspend fun getThreadsDueForAutoHide(now: Long): List<ThreadLockEntity>

    @Query("SELECT * FROM thread_lock_state WHERE isHidden = 1 AND autoUnhideAtMillis > 0 AND autoUnhideAtMillis <= :now")
    suspend fun getThreadsDueForAutoUnhide(now: Long): List<ThreadLockEntity>

    @Query("SELECT * FROM thread_lock_state WHERE isMuted = 1 AND muteUntilMillis > 0 AND muteUntilMillis <= :now")
    suspend fun getThreadsDueForAutoUnmute(now: Long): List<ThreadLockEntity>

    @Query("DELETE FROM thread_lock_state WHERE threadId = :threadId")
    suspend fun clear(threadId: Long)
}
