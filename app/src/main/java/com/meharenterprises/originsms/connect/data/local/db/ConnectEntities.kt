package com.meharenterprises.originsms.connect.data.local.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// ── Entities ──

@Entity(tableName = "connect_conversations")
data class ConversationEntity(
    @PrimaryKey val id: String,
    val participantId: String,
    val participantName: String,
    val participantAvatar: String?,
    val lastMessage: String = "",
    val lastMessageTime: Long = 0L,
    val unreadCount: Int = 0,
    val isOnline: Boolean = false,
    val isPinned: Boolean = false,
    val isArchived: Boolean = false,
    val isMuted: Boolean = false
)

@Entity(tableName = "connect_messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val conversationId: String,
    val senderId: String,
    val type: String = "TEXT",
    val body: String = "",
    val mediaUrl: String? = null,
    val mediaLocalPath: String? = null,
    val replyToId: String? = null,
    val status: String = "SENDING",
    val createdAt: Long = System.currentTimeMillis(),
    val editedAt: Long? = null,
    val isStarred: Boolean = false,
    val isDeletedForMe: Boolean = false
)

@Entity(tableName = "connect_users")
data class UserEntity(
    @PrimaryKey val id: String,
    val phone: String,
    val displayName: String,
    val avatarUrl: String?,
    val isOnline: Boolean = false,
    val lastSeen: Long = 0L
)

// ── DAOs ──

@Dao
interface ConversationDao {
    @Query("SELECT * FROM connect_conversations WHERE isArchived = 0 ORDER BY isPinned DESC, lastMessageTime DESC")
    fun observeConversations(): Flow<List<ConversationEntity>>

    @Query("SELECT * FROM connect_conversations WHERE id = :id")
    suspend fun getById(id: String): ConversationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(conversation: ConversationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(conversations: List<ConversationEntity>)

    @Query("UPDATE connect_conversations SET unreadCount = 0 WHERE id = :conversationId")
    suspend fun markRead(conversationId: String)

    @Query("UPDATE connect_conversations SET lastMessage = :body, lastMessageTime = :time WHERE id = :id")
    suspend fun updateLastMessage(id: String, body: String, time: Long)

    @Query("UPDATE connect_conversations SET isOnline = :isOnline WHERE participantId = :userId")
    suspend fun updateOnlineStatus(userId: String, isOnline: Boolean)
}

@Dao
interface ConnectMessageDao {
    @Query("SELECT * FROM connect_messages WHERE conversationId = :conversationId AND isDeletedForMe = 0 ORDER BY createdAt ASC")
    fun observeMessages(conversationId: String): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(messages: List<MessageEntity>)

    @Query("UPDATE connect_messages SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: String, status: String)

    @Query("UPDATE connect_messages SET body = :body, editedAt = :editedAt WHERE id = :id")
    suspend fun editMessage(id: String, body: String, editedAt: Long)

    @Query("UPDATE connect_messages SET type = 'DELETED', body = '' WHERE id = :id")
    suspend fun deleteForEveryone(id: String)

    @Query("UPDATE connect_messages SET isDeletedForMe = 1 WHERE id = :id")
    suspend fun deleteForMe(id: String)

    @Query("UPDATE connect_messages SET isStarred = :starred WHERE id = :id")
    suspend fun starMessage(id: String, starred: Boolean)

    @Query("SELECT * FROM connect_messages WHERE isStarred = 1 ORDER BY createdAt DESC")
    fun observeStarredMessages(): Flow<List<MessageEntity>>
}

@Dao
interface ConnectUserDao {
    @Query("SELECT * FROM connect_users ORDER BY displayName ASC")
    fun observeAll(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(users: List<UserEntity>)

    @Query("SELECT * FROM connect_users WHERE id = :id")
    suspend fun getById(id: String): UserEntity?
}
