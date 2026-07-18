package com.meharenterprises.originsms.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blocked_numbers")
data class BlockedNumberEntity(
    @PrimaryKey val normalizedNumber: String,
    val displayNumber: String,
    val blockedAtMillis: Long
)
