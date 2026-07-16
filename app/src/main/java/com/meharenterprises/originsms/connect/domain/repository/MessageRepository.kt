package com.meharenterprises.originsms.connect.domain.repository

import com.meharenterprises.originsms.connect.domain.model.ConnectMessage
import com.meharenterprises.originsms.connect.domain.model.ConnectConversation
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getConversations(): Flow<List<ConnectConversation>>
    fun getMessages(conversationId: String): Flow<List<ConnectMessage>>
    suspend fun sendMessage(conversationId: String, body: String, type: String = "TEXT"): Result<ConnectMessage>
    suspend fun editMessage(messageId: String, newBody: String): Result<ConnectMessage>
    suspend fun deleteMessageForEveryone(messageId: String): Result<Unit>
    suspend fun deleteMessageForMe(messageId: String): Result<Unit>
    suspend fun starMessage(messageId: String, starred: Boolean): Result<Unit>
    suspend fun markConversationRead(conversationId: String): Result<Unit>
    suspend fun getOrCreateConversation(participantId: String): Result<ConnectConversation>
    fun observeTyping(conversationId: String): Flow<Boolean>
    suspend fun sendTypingIndicator(conversationId: String, isTyping: Boolean)
}
