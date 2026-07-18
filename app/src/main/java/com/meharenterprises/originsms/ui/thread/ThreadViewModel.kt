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
        // Mark conversation start time for new chats (threadId=-1)
        // This is used to filter out old deleted messages from reused threads
        newChatStartedAt = if (threadId == -1L) System.currentTimeMillis() else 0L
        loadMessages()
        loadPersistedScheduled()
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

    // Track when a new conversation was initiated (threadId=-1)
    private var newChatStartedAt: Long = 0L

    fun loadMessages() {
        viewModelScope.launch {
            if (currentThreadId == -1L) {
                // Record start time on first load of new chat
                if (newChatStartedAt == 0L) newChatStartedAt = System.currentTimeMillis()

                // Try to find thread after first message sent
                val threadId = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                    repository.getThreadIdForAddress(currentAddress)
                }
                if (threadId == -1L) {
                    _messages.value = emptyList()
                    return@launch
                }
                currentThreadId = threadId
            }

            val allMessages = repository.getMessages(currentThreadId)

            // Filter based on context:
            val filtered = if (newChatStartedAt > 0L) {
                // New chat — only show messages sent after conversation started
                // Old trashed messages remain hidden even after thread is untrashed
                val newMsgs = allMessages.filter { it.dateMillis >= newChatStartedAt - 10000 }
                if (newMsgs.isNotEmpty()) {
                    newChatStartedAt = 0L // Active conversation now
                    newMsgs
                } else {
                    emptyList()
                }
            } else {
                allMessages
            }

            // Mark starred messages
            val starredIds = database.starredMessageDao().getAllIds().toSet()
            _messages.value = filtered.map { it.copy(isStarred = starredIds.contains(it.id)) }
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

    // Scheduled message shown in chat as pending (not yet sent to network)
    data class ScheduledEntry(val text: String, val scheduledAtMillis: Long, val key: String)
    private val _scheduledEntries = mutableListOf<ScheduledEntry>()
    private val _scheduledLive = androidx.lifecycle.MutableLiveData<List<ScheduledEntry>>(emptyList())
    val scheduledEntries: androidx.lifecycle.LiveData<List<ScheduledEntry>> = _scheduledLive

    private fun scheduledPrefs() = getApplication<android.app.Application>()
        .getSharedPreferences("scheduled_msgs_${currentThreadId}", android.content.Context.MODE_PRIVATE)

    private fun loadPersistedScheduled() {
        val prefs = scheduledPrefs()
        val keys = prefs.getStringSet("keys", emptySet()) ?: return
        _scheduledEntries.clear()
        keys.forEach { key ->
            val text = prefs.getString("text_$key", null) ?: return@forEach
            val time = prefs.getLong("time_$key", 0L)
            if (time > System.currentTimeMillis()) {
                _scheduledEntries.add(ScheduledEntry(text, time, key))
            } else {
                // Time passed while app was closed — send now
                viewModelScope.launch { sendMessage(text) }
                prefs.edit().remove("text_$key").remove("time_$key").apply()
            }
        }
        prefs.edit().putStringSet("keys", _scheduledEntries.map { it.key }.toSet()).apply()
        _scheduledLive.value = _scheduledEntries.toList()
    }

    fun sendScheduledPlaceholder(text: String, scheduledAtMillis: Long, key: String) {
        _scheduledEntries.add(ScheduledEntry(text, scheduledAtMillis, key))
        _scheduledLive.value = _scheduledEntries.toList()
        // Persist
        val prefs = scheduledPrefs()
        val keys = (prefs.getStringSet("keys", emptySet()) ?: emptySet()) + key
        prefs.edit()
            .putStringSet("keys", keys)
            .putString("text_$key", text)
            .putLong("time_$key", scheduledAtMillis)
            .apply()
    }

    fun deleteScheduledPlaceholder(key: String) {
        _scheduledEntries.removeAll { it.key == key }
        _scheduledLive.value = _scheduledEntries.toList()
        // Remove from persistence
        val prefs = scheduledPrefs()
        val keys = ((prefs.getStringSet("keys", emptySet()) ?: emptySet()) - key)
        prefs.edit()
            .putStringSet("keys", keys)
            .remove("text_$key")
            .remove("time_$key")
            .apply()
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

            // If this was a new chat (currentThreadId was -1), find the thread
            // Android just created/reused and untrash it so new messages appear
            if (currentThreadId == -1L) {
                val resolvedId = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                    // Find thread Android just wrote the message to via ContentResolver
                    val cursor = getApplication<android.app.Application>().contentResolver.query(
                        android.provider.Telephony.Sms.CONTENT_URI,
                        arrayOf(android.provider.Telephony.Sms.THREAD_ID),
                        "${android.provider.Telephony.Sms.ADDRESS} = ?",
                        arrayOf(currentAddress),
                        "${android.provider.Telephony.Sms.DATE} DESC LIMIT 1"
                    )
                    val id = cursor?.use { if (it.moveToFirst()) it.getLong(0) else -1L } ?: -1L
                    id
                }
                if (resolvedId != -1L) {
                    currentThreadId = resolvedId
                    // Untrash this thread — user is actively chatting in it now
                    kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                        val state = database.threadLockDao().getForThread(resolvedId)
                        if (state?.deletedAtMillis != null && state.deletedAtMillis > 0L) {
                            // Mark when old conversation was trashed — hide messages before this
                            // newChatStartedAt already set to System.currentTimeMillis() at bind()
                            database.threadLockDao().upsert(state.copy(deletedAtMillis = 0L))
                        }
                    }
                }
            }

            loadMessages()
        }
    }

    fun toggleStar(message: com.meharenterprises.originsms.core.Message) {
        viewModelScope.launch {
            val dao = database.starredMessageDao()
            if (dao.isStarred(message.id) > 0) {
                dao.unstar(message.id)
            } else {
                dao.star(
                    com.meharenterprises.originsms.data.db.StarredMessageEntity(
                        messageId = message.id,
                        threadId = message.threadId,
                        address = message.address,
                        body = message.body,
                        dateMillis = message.dateMillis
                    )
                )
            }
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
            // Just delete the selected messages — do NOT move thread to trash
            // (trash is only for when user deletes the whole conversation)
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
