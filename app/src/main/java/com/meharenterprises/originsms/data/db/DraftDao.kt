package com.meharenterprises.originsms.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface DraftDao {
    @Upsert
    suspend fun saveDraft(entity: DraftEntity)

    @Query("SELECT * FROM drafts WHERE threadId = :threadId")
    suspend fun getDraft(threadId: Long): DraftEntity?

    @Query("SELECT * FROM drafts")
    suspend fun getAllDrafts(): List<DraftEntity>

    @Delete
    suspend fun deleteDraft(entity: DraftEntity)

    @Query("DELETE FROM drafts WHERE threadId = :threadId")
    suspend fun clearDraft(threadId: Long)
}
