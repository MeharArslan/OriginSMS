package com.meharenterprises.originsms.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [StarredMessageEntity::class, ThreadLockEntity::class, BlockedNumberEntity::class, DraftEntity::class],
    version = 7,
    exportSchema = false
)
abstract class OriginDatabase : RoomDatabase() {

    abstract fun threadLockDao(): ThreadLockDao
    abstract fun blockedNumberDao(): BlockedNumberDao
    abstract fun draftDao(): DraftDao
    abstract fun starredMessageDao(): StarredMessageDao

    companion object {
        @Volatile private var instance: OriginDatabase? = null

        fun getInstance(context: Context): OriginDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    OriginDatabase::class.java,
                    "origin_sms.db"
                )
                    // App-level metadata only (lock state, drafts, blocked numbers) —
                    // never actual message content, so a destructive migration on
                    // schema change just means the user re-locks/re-mutes chats
                    // after an update, with zero risk to real SMS/MMS data.
                    .fallbackToDestructiveMigration()
                    .build().also { instance = it }
            }
    }
}
