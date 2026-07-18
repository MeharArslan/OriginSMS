package com.meharenterprises.originsms.connect.domain.repository

import com.meharenterprises.originsms.connect.domain.model.ConnectUser
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun getRegisteredContacts(): Flow<List<ConnectUser>>
    suspend fun syncContacts(phoneNumbers: List<String>): Result<List<ConnectUser>>
    suspend fun getUserProfile(userId: String): Result<ConnectUser>
    suspend fun updateProfile(displayName: String, avatarPath: String?): Result<ConnectUser>
    fun observeOnlineStatus(userId: String): Flow<Boolean>
}
