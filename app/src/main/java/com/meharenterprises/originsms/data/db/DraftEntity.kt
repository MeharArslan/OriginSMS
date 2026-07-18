package com.meharenterprises.originsms.data.db
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "drafts")
data class DraftEntity(@PrimaryKey val threadId: Long, val text: String, val updatedAtMillis: Long)
