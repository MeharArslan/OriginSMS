package com.meharenterprises.originsms.connect.data.repository

import com.meharenterprises.originsms.connect.data.local.db.ConnectTokenStore
import com.meharenterprises.originsms.connect.data.remote.api.*
import com.meharenterprises.originsms.connect.domain.model.AuthState
import com.meharenterprises.originsms.connect.domain.model.AuthTokens
import com.meharenterprises.originsms.connect.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: ConnectApiService,
    private val tokenStore: ConnectTokenStore
) : AuthRepository {

    private val _authState = MutableStateFlow<AuthState>(
        if (tokenStore.isTokenValid()) AuthState.Authenticated(
            tokenStore.getUserId()!!, tokenStore.getAccessToken()!!
        ) else AuthState.Unauthenticated
    )
    override val authState: Flow<AuthState> = _authState

    override suspend fun sendOtp(phone: String): Result<Unit> = runCatching {
        val resp = api.sendOtp(OtpRequest(phone))
        if (!resp.isSuccessful) throw Exception(resp.errorBody()?.string() ?: "Failed to send OTP")
    }

    override suspend fun verifyOtp(phone: String, otp: String, displayName: String): Result<AuthTokens> =
        runCatching {
            val resp = api.verifyOtp(VerifyOtpRequest(phone, otp, displayName))
            if (!resp.isSuccessful) throw Exception(resp.errorBody()?.string() ?: "OTP verification failed")
            val body = resp.body()!!
            val expiresAt = System.currentTimeMillis() + body.expiresIn * 1000
            tokenStore.saveTokens(body.accessToken, body.refreshToken, body.userId, expiresAt)
            val tokens = AuthTokens(body.accessToken, body.refreshToken, body.userId, expiresAt)
            _authState.value = AuthState.Authenticated(body.userId, body.accessToken)
            tokens
        }

    override suspend fun refreshToken(): Result<AuthTokens> = runCatching {
        val refresh = tokenStore.getRefreshToken() ?: throw Exception("No refresh token")
        val resp = api.refreshToken(RefreshRequest(refresh))
        if (!resp.isSuccessful) {
            tokenStore.clear()
            _authState.value = AuthState.Unauthenticated
            throw Exception("Session expired")
        }
        val body = resp.body()!!
        val expiresAt = System.currentTimeMillis() + body.expiresIn * 1000
        tokenStore.saveTokens(body.accessToken, body.refreshToken, body.userId, expiresAt)
        AuthTokens(body.accessToken, body.refreshToken, body.userId, expiresAt)
    }

    override suspend fun logout() {
        try {
            val token = tokenStore.getAccessToken()
            if (token != null) api.logout("Bearer $token")
        } catch (_: Exception) {}
        tokenStore.clear()
        _authState.value = AuthState.Unauthenticated
    }

    override fun isAuthenticated(): Boolean = tokenStore.isTokenValid()
    override fun getCurrentUserId(): String? = tokenStore.getUserId()
    override fun getAccessToken(): String? = tokenStore.getAccessToken()
        ?.let { "Bearer $it" }
}
