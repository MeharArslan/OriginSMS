package com.meharenterprises.originsms.connect.domain.repository

import com.meharenterprises.originsms.connect.domain.model.AuthState
import com.meharenterprises.originsms.connect.domain.model.AuthTokens
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val authState: Flow<AuthState>
    suspend fun sendOtp(phone: String): Result<Unit>
    suspend fun verifyOtp(phone: String, otp: String, displayName: String): Result<AuthTokens>
    suspend fun refreshToken(): Result<AuthTokens>
    suspend fun logout()
    fun isAuthenticated(): Boolean
    fun getCurrentUserId(): String?
    fun getAccessToken(): String?
}
