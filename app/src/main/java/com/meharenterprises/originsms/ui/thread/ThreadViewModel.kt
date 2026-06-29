package com.meharenterprises.originsms.ui.thread

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.meharenterprises.originsms.core.Message
import com.meharenterprises.originsms.core.SmsRepository
import com.meharenterprises.originsms.data.db.DraftEntity
import com.meharenterprises.originsms.data.db.OriginDatabase
import kotlinx.coroutines.launch

class ThreadViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SmsRepository(application)
    private val database = OriginDatabase.getInstance(application)

    private val _messages = MutableLiveData<List<Message>>(emptyList())
    val messages: LiveData<List<Message>> = _messages

    private val _pendingAttachments = MutableLiveData<List<Uri>>(emptyList())
    val pendingAttachments: LiveData<List<Uri>> = _pendingAttachments

    private var currentThreadId: Long = -1L
    private var currentAddress: String = ""

    fun bind(threadId: Long, address: String) {
        currentThreadId = threadId
        currentAddress = address
        loadMessages()
    }

    fun loadMessages() {
        if (currentThreadId == -1L) return
        viewModelScope.launch {
            _messages.value = repository.getMessages(currentThreadId)
        }
    }

    fun addPendingAttachment(uri: Uri) {
        _pendingAttachments.value = (_pendingAttachments.value.orEmpty() + uri).distinct()
    }

    fun removePendingAttachment(uri: Uri) {
        _pendingAttachments.value = _pendingAttachments.value.orEmpty().filterNot { it == uri }
    }

    fun clearPendingAttachments() {
        _pendingAttachments.value = emptyList()
    }

    fun sendMessage(body: String) {
        if (currentAddress.isBlank()) return
        val attachments = _pendingAttachments.value.orEmpty()

        if (attachments.isEmpty()) {
            if (body.isBlank()) return
            repository.sendSms(currentAddress, body, if (currentThreadId != -1L) currentThreadId else null)
        } else {
            repository.sendMms(currentAddress, body, attachments, if (currentThreadId != -1L) currentThreadId else null)
        }

        clearPendingAttachments()
        viewModelScope.launch {
            database.draftDao().clearDraft(currentThreadId)
            loadMessages()
        }
    }

    fun deleteMessage(messageId: Long) {
        viewModelScope.launch {
            repository.deleteMessage(messageId)
            loadMessages()
        }
    }

    fun saveDraft(text: String) {
        if (currentThreadId == -1L) return
        viewModelScope.launch {
            if (text.isBlank()) {
                database.draftDao().clearDraft(currentThreadId)
            } else {
                database.draftDao().saveDraft(
                    DraftEntity(threadId = currentThreadId, text = text, updatedAtMillis = System.currentTimeMillis())
                )
            }
        }
    }

    suspend fun getDraft(threadId: Long): String? {
        return database.draftDao().getDraft(threadId)?.text
    }
}
