package com.meharenterprises.originsms.connect.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ConversationEntity::class, MessageEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ConnectDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
    abstract fun messageDao(): ConnectMessageDao
    abstract fun userDao(): ConnectUserDao

    companion object {
        @Volatile private var INSTANCE: ConnectDatabase? = null

        fun getInstance(context: Context): ConnectDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, ConnectDatabase::class.java, "connect.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
