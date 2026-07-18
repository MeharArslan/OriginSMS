package com.meharenterprises.originsms.connect.domain.model

data class ConnectUser(
    val id: String,
    val phone: String,
    val displayName: String,
    val avatarUrl: String? = null,
    val isOnline: Boolean = false,
    val lastSeen: Long = 0L,
    val isRegistered: Boolean = true
)
