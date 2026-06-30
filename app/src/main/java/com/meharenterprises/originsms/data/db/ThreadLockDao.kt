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

    @Query("UPDATE thread_lock_state SET isArchived = :archived WHERE threadId = :threadId")
    suspend fun setArchived(threadId: Long, archived: Boolean)

    @Query("DELETE FROM thread_lock_state WHERE threadId = :threadId")
    suspend fun clear(threadId: Long)
}
