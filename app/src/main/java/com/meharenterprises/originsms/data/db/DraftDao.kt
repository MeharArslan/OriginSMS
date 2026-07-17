package com.meharenterprises.originsms.data.db
import androidx.room.*
@Dao
interface DraftDao {
    @Query("SELECT * FROM drafts WHERE threadId = :threadId LIMIT 1")
    suspend fun getDraft(threadId: Long): DraftEntity?
    @Query("SELECT * FROM drafts")
    suspend fun getAllDrafts(): List<DraftEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDraft(draft: DraftEntity)
    @Query("DELETE FROM drafts WHERE threadId = :threadId")
    suspend fun clearDraft(threadId: Long)
    @Delete
    suspend fun deleteDraft(draft: DraftEntity)
}
