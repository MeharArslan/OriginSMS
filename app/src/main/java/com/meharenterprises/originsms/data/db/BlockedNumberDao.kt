package com.meharenterprises.originsms.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BlockedNumberDao {
    @Upsert
    suspend fun block(entity: BlockedNumberEntity)

    @Query("DELETE FROM blocked_numbers WHERE normalizedNumber = :number")
    suspend fun unblock(number: String)

    @Query("SELECT EXISTS(SELECT 1 FROM blocked_numbers WHERE normalizedNumber = :number)")
    suspend fun isBlocked(number: String): Boolean

    @Query("SELECT * FROM blocked_numbers ORDER BY blockedAtMillis DESC")
    fun observeAll(): Flow<List<BlockedNumberEntity>>
}

@Dao
interface DraftDao {
    @Upsert
    suspend fun saveDraft(entity: DraftEntity)

    @Query("SELECT * FROM drafts WHERE threadId = :threadId")
    suspend fun getDraft(threadId: Long): DraftEntity?

    @Delete
    suspend fun deleteDraft(entity: DraftEntity)

    @Query("DELETE FROM drafts WHERE threadId = :threadId")
    suspend fun clearDraft(threadId: Long)
}
