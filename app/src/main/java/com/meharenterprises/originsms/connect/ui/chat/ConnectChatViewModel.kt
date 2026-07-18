package com.meharenterprises.originsms.connect.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meharenterprises.originsms.connect.domain.model.ConnectMessage
import com.meharenterprises.originsms.connect.domain.repository.AuthRepository
import com.meharenterprises.originsms.connect.domain.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectChatViewModel @Inject constructor(
    private val messageRepo: MessageRepository,
    private val authRepo: AuthRepository
) : ViewModel() {

    private var conversationId: String = ""
    val currentUserId get() = authRepo.getCurrentUserId() ?: ""

    private val _messages = MutableStateFlow<List<ConnectMessage>>(emptyList())
    val messages: StateFlow<List<ConnectMessage>> = _messages

    val isTyping: StateFlow<Boolean> =
        MutableStateFlow(false) // populated after init

    private var typingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var typingJob: Job? = null

    fun init(conversationId: String) {
        this.conversationId = conversationId
        viewModelScope.launch {
            messageRepo.getMessages(conversationId).collect { _messages.value = it }
        }
        viewModelScope.launch {
            messageRepo.observeTyping(conversationId).collect {
                typingState.value = it
            }
        }
    }

    fun sendMessage(body: String) {
        viewModelScope.launch {
            messageRepo.sendMessage(conversationId, body)
        }
    }

    fun sendTyping(typing: Boolean) {
        viewModelScope.launch {
            messageRepo.sendTypingIndicator(conversationId, typing)
        }
    }

    fun markRead() {
        viewModelScope.launch {
            messageRepo.markConversationRead(conversationId)
        }
    }

    fun editMessage(messageId: String, newBody: String) {
        viewModelScope.launch { messageRepo.editMessage(messageId, newBody) }
    }

    fun deleteForEveryone(messageId: String) {
        viewModelScope.launch { messageRepo.deleteMessageForEveryone(messageId) }
    }

    fun deleteForMe(messageId: String) {
        viewModelScope.launch { messageRepo.deleteMessageForMe(messageId) }
    }

    fun starMessage(messageId: String, starred: Boolean) {
        viewModelScope.launch { messageRepo.starMessage(messageId, starred) }
    }
}
