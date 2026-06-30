package com.meharenterprises.originsms.ui.thread

import android.app.Application
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Telephony
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
    var selectedSubscriptionId: Int? = null   // null = system default SIM

    /**
     * Watches the system SMS provider for any change (new incoming message,
     * status update on a sent message, etc.) and reloads this thread's
     * messages automatically — this is what makes send/receive feel
     * "real time" instead of only refreshing on screen re-entry.
     */
    private val smsContentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean) {
            loadMessages()
        }
    }
    private var observerRegistered = false

    fun bind(threadId: Long, address: String) {
        currentThreadId = threadId
        currentAddress = address
        loadMessages()
        registerContentObserver()
    }

    private fun registerContentObserver() {
        if (observerRegistered) return
        getApplication<Application>().contentResolver.registerContentObserver(
            Telephony.Sms.CONTENT_URI, true, smsContentObserver
        )
        getApplication<Application>().contentResolver.registerContentObserver(
            Telephony.Mms.CONTENT_URI, true, smsContentObserver
        )
        observerRegistered = true
    }

    override fun onCleared() {
        super.onCleared()
        if (observerRegistered) {
            getApplication<Application>().contentResolver.unregisterContentObserver(smsContentObserver)
            observerRegistered = false
        }
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
            repository.sendSms(currentAddress, body,
                if (currentThreadId != -1L) currentThreadId else null,
                selectedSubscriptionId)
        } else {
            repository.sendMms(currentAddress, body, attachments, if (currentThreadId != -1L) currentThreadId else null)
        }

        clearPendingAttachments()
        viewModelScope.launch {
            database.draftDao().clearDraft(currentThreadId)
            // The optimistic provider insert in SmsRepository is synchronous,
            // so the new message is already visible without waiting for the
            // ContentObserver round-trip — this keeps Send feeling instant.
            loadMessages()
        }
    }

    fun deleteMessage(messageId: Long) {
        viewModelScope.launch {
            repository.deleteMessage(messageId)
            loadMessages()
        }
    }

    fun deleteMessages(messageIds: Set<Long>) {
        viewModelScope.launch {
            messageIds.forEach { repository.deleteMessage(it) }
            loadMessages()
        }
    }

    fun retryMessage(message: Message) {
        sendMessage(message.body)
        viewModelScope.launch {
            repository.deleteMessage(message.id)
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
