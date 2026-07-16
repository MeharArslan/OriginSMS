package com.meharenterprises.originsms.connect.data.repository

import com.meharenterprises.originsms.connect.data.local.db.*
import com.meharenterprises.originsms.connect.data.remote.api.*
import com.meharenterprises.originsms.connect.data.remote.socket.ConnectSocketClient
import com.meharenterprises.originsms.connect.domain.model.*
import com.meharenterprises.originsms.connect.domain.repository.AuthRepository
import com.meharenterprises.originsms.connect.domain.repository.MessageRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepositoryImpl @Inject constructor(
    private val api: ConnectApiService,
    private val db: ConnectDatabase,
    private val socket: ConnectSocketClient,
    private val auth: AuthRepository
) : MessageRepository {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        // Listen to socket messages and persist to local DB
        scope.launch {
            socket.newMessage.collect { dto ->
                db.messageDao().upsert(dto.toEntity())
                db.conversationDao().updateLastMessage(dto.conversationId, dto.body, dto.createdAt)
            }
        }
        scope.launch {
            socket.messageStatus.collect { event ->
                db.messageDao().updateStatus(event.messageId, event.status)
            }
        }
        scope.launch {
            socket.online.collect { event ->
                db.conversationDao().updateOnlineStatus(event.userId, event.isOnline)
            }
        }
    }

    override fun getConversations(): Flow<List<ConnectConversation>> =
        db.conversationDao().observeConversations()
            .map { list -> list.map { it.toDomain() } }

    override fun getMessages(conversationId: String): Flow<List<ConnectMessage>> =
        db.messageDao().observeMessages(conversationId)
            .map { list -> list.map { it.toDomain() } }

    override suspend fun sendMessage(
        conversationId: String, body: String, type: String
    ): Result<ConnectMessage> = runCatching {
        val token = auth.getAccessToken() ?: throw Exception("Not authenticated")
        val resp = api.sendMessage(token, conversationId, SendMessageRequest(body, type))
        if (!resp.isSuccessful) throw Exception(resp.errorBody()?.string() ?: "Send failed")
        val msg = resp.body()!!
        db.messageDao().upsert(msg.toEntity())
        db.conversationDao().updateLastMessage(conversationId, body, msg.createdAt)
        msg.toDomain()
    }

    override suspend fun editMessage(messageId: String, newBody: String): Result<ConnectMessage> =
        runCatching {
            val token = auth.getAccessToken() ?: throw Exception("Not authenticated")
            val resp = api.editMessage(token, messageId, EditMessageRequest(newBody))
            if (!resp.isSuccessful) throw Exception(resp.errorBody()?.string())
            val msg = resp.body()!!
            db.messageDao().editMessage(messageId, newBody, msg.editedAt ?: System.currentTimeMillis())
            msg.toDomain()
        }

    override suspend fun deleteMessageForEveryone(messageId: String): Result<Unit> = runCatching {
        val token = auth.getAccessToken() ?: throw Exception("Not authenticated")
        val resp = api.deleteForEveryone(token, messageId)
        if (!resp.isSuccessful) throw Exception(resp.errorBody()?.string())
        db.messageDao().deleteForEveryone(messageId)
    }

    override suspend fun deleteMessageForMe(messageId: String): Result<Unit> = runCatching {
        val token = auth.getAccessToken() ?: throw Exception("Not authenticated")
        val resp = api.deleteForMe(token, messageId)
        if (!resp.isSuccessful) throw Exception(resp.errorBody()?.string())
        db.messageDao().deleteForMe(messageId)
    }

    override suspend fun starMessage(messageId: String, starred: Boolean): Result<Unit> = runCatching {
        val token = auth.getAccessToken() ?: throw Exception("Not authenticated")
        api.starMessage(token, messageId, StarRequest(starred))
        db.messageDao().starMessage(messageId, starred)
    }

    override suspend fun markConversationRead(conversationId: String): Result<Unit> = runCatching {
        val token = auth.getAccessToken() ?: throw Exception("Not authenticated")
        api.markRead(token, conversationId)
        db.conversationDao().markRead(conversationId)
    }

    override suspend fun getOrCreateConversation(participantId: String): Result<ConnectConversation> =
        runCatching {
            val token = auth.getAccessToken() ?: throw Exception("Not authenticated")
            val resp = api.createConversation(token, CreateConversationRequest(participantId))
            if (!resp.isSuccessful) throw Exception(resp.errorBody()?.string())
            val conv = resp.body()!!.toEntity()
            db.conversationDao().upsert(conv)
            conv.toDomain()
        }

    override fun observeTyping(conversationId: String): Flow<Boolean> =
        socket.typing
            .filter { it.conversationId == conversationId }
            .map { it.isTyping }

    override suspend fun sendTypingIndicator(conversationId: String, isTyping: Boolean) {
        socket.sendTyping(conversationId, isTyping)
    }
}

// Extension functions — DTO ↔ Entity ↔ Domain
fun MessageDto.toEntity() = MessageEntity(
    id = id, conversationId = conversationId, senderId = senderId,
    type = type, body = body, mediaUrl = mediaUrl, replyToId = replyToId,
    status = status, createdAt = createdAt, editedAt = editedAt, isStarred = isStarred
)
fun MessageDto.toDomain() = ConnectMessage(
    id = id, conversationId = conversationId, senderId = senderId,
    type = MessageType.valueOf(type.uppercase()),
    body = body, mediaUrl = mediaUrl, replyToId = replyToId,
    status = MessageStatus.valueOf(status.uppercase()),
    createdAt = createdAt, editedAt = editedAt, isStarred = isStarred
)
fun MessageEntity.toDomain() = ConnectMessage(
    id = id, conversationId = conversationId, senderId = senderId,
    type = runCatching { MessageType.valueOf(type.uppercase()) }.getOrDefault(MessageType.TEXT),
    body = body, mediaUrl = mediaUrl, replyToId = replyToId,
    status = runCatching { MessageStatus.valueOf(status.uppercase()) }.getOrDefault(MessageStatus.SENT),
    createdAt = createdAt, editedAt = editedAt, isStarred = isStarred,
    isDeletedForMe = isDeletedForMe
)
fun ConversationDto.toEntity() = ConversationEntity(
    id = id, participantId = participantId, participantName = participantName,
    participantAvatar = participantAvatar, lastMessage = lastMessage,
    lastMessageTime = lastMessageTime, unreadCount = unreadCount, isOnline = isOnline
)
fun ConversationEntity.toDomain() = ConnectConversation(
    id = id, participantId = participantId, participantName = participantName,
    participantAvatar = participantAvatar, lastMessage = lastMessage,
    lastMessageTime = lastMessageTime, unreadCount = unreadCount, isOnline = isOnline,
    isPinned = isPinned, isArchived = isArchived, isMuted = isMuted
)
