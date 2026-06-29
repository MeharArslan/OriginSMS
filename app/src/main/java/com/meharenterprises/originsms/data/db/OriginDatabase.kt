package com.meharenterprises.originsms.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ThreadLockEntity::class, BlockedNumberEntity::class, DraftEntity::class],
    version = 1,
    exportSchema = false
)
abstract class OriginDatabase : RoomDatabase() {

    abstract fun threadLockDao(): ThreadLockDao
    abstract fun blockedNumberDao(): BlockedNumberDao
    abstract fun draftDao(): DraftDao

    companion object {
        @Volatile private var instance: OriginDatabase? = null

        fun getInstance(context: Context): OriginDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    OriginDatabase::class.java,
                    "origin_sms.db"
                ).build().also { instance = it }
            }
    }
}
