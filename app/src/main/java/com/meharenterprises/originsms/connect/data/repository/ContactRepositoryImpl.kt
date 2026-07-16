package com.meharenterprises.originsms.connect.data.repository

import com.meharenterprises.originsms.connect.data.local.db.ConnectDatabase
import com.meharenterprises.originsms.connect.data.local.db.UserEntity
import com.meharenterprises.originsms.connect.data.remote.api.ConnectApiService
import com.meharenterprises.originsms.connect.data.remote.api.ContactSyncRequest
import com.meharenterprises.originsms.connect.data.remote.socket.ConnectSocketClient
import com.meharenterprises.originsms.connect.domain.model.ConnectUser
import com.meharenterprises.originsms.connect.domain.repository.AuthRepository
import com.meharenterprises.originsms.connect.domain.repository.ContactRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepositoryImpl @Inject constructor(
    private val api: ConnectApiService,
    private val db: ConnectDatabase,
    private val auth: AuthRepository,
    private val socket: ConnectSocketClient
) : ContactRepository {

    override fun getRegisteredContacts(): Flow<List<ConnectUser>> =
        db.userDao().observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun syncContacts(phoneNumbers: List<String>): Result<List<ConnectUser>> =
        runCatching {
            val token = auth.getAccessToken() ?: throw Exception("Not authenticated")
            val resp = api.syncContacts(token, ContactSyncRequest(phoneNumbers))
            if (!resp.isSuccessful) throw Exception(resp.errorBody()?.string())
            val users = resp.body()!!.registered
            db.userDao().upsertAll(users.map { u ->
                UserEntity(u.id, u.phone, u.displayName, u.avatarUrl, u.isOnline, u.lastSeen)
            })
            users.map { ConnectUser(it.id, it.phone, it.displayName, it.avatarUrl, it.isOnline, it.lastSeen) }
        }

    override suspend fun getUserProfile(userId: String): Result<ConnectUser> = runCatching {
        val token = auth.getAccessToken() ?: throw Exception("Not authenticated")
        val resp = api.getUserProfile(token, userId)
        if (!resp.isSuccessful) throw Exception(resp.errorBody()?.string())
        val u = resp.body()!!
        ConnectUser(u.id, u.phone, u.displayName, u.avatarUrl, u.isOnline, u.lastSeen)
    }

    override suspend fun updateProfile(displayName: String, avatarPath: String?): Result<ConnectUser> =
        runCatching {
            val token = auth.getAccessToken() ?: throw Exception("Not authenticated")
            val body = buildMap<String, String> {
                put("displayName", displayName)
                avatarPath?.let { put("avatarPath", it) }
            }
            val resp = api.updateProfile(token, body)
            if (!resp.isSuccessful) throw Exception(resp.errorBody()?.string())
            val u = resp.body()!!
            ConnectUser(u.id, u.phone, u.displayName, u.avatarUrl, u.isOnline, u.lastSeen)
        }

    override fun observeOnlineStatus(userId: String): Flow<Boolean> =
        socket.online.filter { it.userId == userId }.map { it.isOnline }
}

fun UserEntity.toDomain() = ConnectUser(
    id = id, phone = phone, displayName = displayName,
    avatarUrl = avatarUrl, isOnline = isOnline, lastSeen = lastSeen
)
