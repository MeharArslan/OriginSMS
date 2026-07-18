package com.meharenterprises.originsms.connect.domain.model

sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(val userId: String, val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
    val expiresAt: Long
)
