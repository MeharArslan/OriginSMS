package com.meharenterprises.originsms.data.db
import androidx.room.*
@Dao
interface StarredMessageDao {
    @Query("SELECT * FROM starred_messages ORDER BY starredAtMillis DESC")
    suspend fun getAll(): List<StarredMessageEntity>
    @Query("SELECT messageId FROM starred_messages")
    suspend fun getAllIds(): List<Long>
    @Query("SELECT COUNT(*) FROM starred_messages WHERE messageId = :messageId")
    suspend fun isStarred(messageId: Long): Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun star(entity: StarredMessageEntity)
    @Query("DELETE FROM starred_messages WHERE messageId = :messageId")
    suspend fun unstar(messageId: Long)
}
