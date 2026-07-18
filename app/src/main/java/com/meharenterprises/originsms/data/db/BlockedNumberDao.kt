package com.meharenterprises.originsms.data.db

import androidx.room.Dao
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

    @Query("SELECT * FROM blocked_numbers WHERE normalizedNumber = :number LIMIT 1")
    suspend fun getByNumber(number: String): BlockedNumberEntity?
}

