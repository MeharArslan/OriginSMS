package com.meharenterprises.originsms.connect.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meharenterprises.originsms.connect.data.remote.socket.ConnectSocketClient
import com.meharenterprises.originsms.connect.domain.model.ConnectConversation
import com.meharenterprises.originsms.connect.domain.repository.AuthRepository
import com.meharenterprises.originsms.connect.domain.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectChatListViewModel @Inject constructor(
    private val messageRepo: MessageRepository,
    private val authRepo: AuthRepository,
    private val socket: ConnectSocketClient
) : ViewModel() {

    val conversations: StateFlow<List<ConnectConversation>> =
        messageRepo.getConversations()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        connectSocket()
    }

    private fun connectSocket() {
        val token = authRepo.getAccessToken() ?: return
        viewModelScope.launch {
            socket.connect("https://your-vps-domain.com", token)
        }
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            socket.disconnect()
            authRepo.logout()
            onComplete()
        }
    }

    val currentUserId get() = authRepo.getCurrentUserId()
}
