package com.meharenterprises.originsms.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "starred_messages")
data class StarredMessageEntity(
    @PrimaryKey val messageId: Long,
    val threadId: Long,
    val address: String,
    val body: String,
    val dateMillis: Long,
    val starredAtMillis: Long = System.currentTimeMillis()
)
