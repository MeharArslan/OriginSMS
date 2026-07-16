package com.meharenterprises.originsms.connect.data.remote.api

import retrofit2.Response
import retrofit2.http.*

// ── Request/Response DTOs ──

data class OtpRequest(val phone: String)
data class OtpResponse(val message: String)

data class VerifyOtpRequest(val phone: String, val otp: String, val displayName: String)
data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
    val expiresIn: Long
)

data class RefreshRequest(val refreshToken: String)

data class UserDto(
    val id: String,
    val phone: String,
    val displayName: String,
    val avatarUrl: String?,
    val isOnline: Boolean,
    val lastSeen: Long
)

data class ContactSyncRequest(val phones: List<String>)
data class ContactSyncResponse(val registered: List<UserDto>)

data class ConversationDto(
    val id: String,
    val participantId: String,
    val participantName: String,
    val participantAvatar: String?,
    val lastMessage: String,
    val lastMessageTime: Long,
    val unreadCount: Int,
    val isOnline: Boolean
)

data class MessageDto(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val type: String,
    val body: String,
    val mediaUrl: String?,
    val replyToId: String?,
    val status: String,
    val createdAt: Long,
    val editedAt: Long?,
    val isStarred: Boolean
)

data class SendMessageRequest(
    val body: String,
    val type: String = "TEXT",
    val replyToId: String? = null
)

data class EditMessageRequest(val body: String)
data class StarRequest(val starred: Boolean)

data class CreateConversationRequest(val participantId: String)

interface ConnectApiService {

    // Auth
    @POST("auth/send-otp")
    suspend fun sendOtp(@Body request: OtpRequest): Response<OtpResponse>

    @POST("auth/verify-otp")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): Response<AuthResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshRequest): Response<AuthResponse>

    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Unit>

    // Profile
    @GET("users/me")
    suspend fun getMyProfile(@Header("Authorization") token: String): Response<UserDto>

    @PUT("users/me")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    ): Response<UserDto>

    @GET("users/{userId}")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): Response<UserDto>

    // Contacts
    @POST("contacts/sync")
    suspend fun syncContacts(
        @Header("Authorization") token: String,
        @Body request: ContactSyncRequest
    ): Response<ContactSyncResponse>

    // Conversations
    @GET("conversations")
    suspend fun getConversations(
        @Header("Authorization") token: String
    ): Response<List<ConversationDto>>

    @POST("conversations")
    suspend fun createConversation(
        @Header("Authorization") token: String,
        @Body request: CreateConversationRequest
    ): Response<ConversationDto>

    @PUT("conversations/{conversationId}/read")
    suspend fun markRead(
        @Header("Authorization") token: String,
        @Path("conversationId") conversationId: String
    ): Response<Unit>

    // Messages
    @GET("conversations/{conversationId}/messages")
    suspend fun getMessages(
        @Header("Authorization") token: String,
        @Path("conversationId") conversationId: String,
        @Query("before") before: Long? = null,
        @Query("limit") limit: Int = 50
    ): Response<List<MessageDto>>

    @POST("conversations/{conversationId}/messages")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Path("conversationId") conversationId: String,
        @Body request: SendMessageRequest
    ): Response<MessageDto>

    @PUT("messages/{messageId}")
    suspend fun editMessage(
        @Header("Authorization") token: String,
        @Path("messageId") messageId: String,
        @Body request: EditMessageRequest
    ): Response<MessageDto>

    @DELETE("messages/{messageId}/everyone")
    suspend fun deleteForEveryone(
        @Header("Authorization") token: String,
        @Path("messageId") messageId: String
    ): Response<Unit>

    @DELETE("messages/{messageId}/me")
    suspend fun deleteForMe(
        @Header("Authorization") token: String,
        @Path("messageId") messageId: String
    ): Response<Unit>

    @PUT("messages/{messageId}/star")
    suspend fun starMessage(
        @Header("Authorization") token: String,
        @Path("messageId") messageId: String,
        @Body request: StarRequest
    ): Response<Unit>
}
