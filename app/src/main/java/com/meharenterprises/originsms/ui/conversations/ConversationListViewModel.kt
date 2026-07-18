package com.meharenterprises.originsms.ui.conversations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.meharenterprises.originsms.core.ConversationSummary
import com.meharenterprises.originsms.core.SmsRepository
import com.meharenterprises.originsms.data.db.OriginDatabase
import com.meharenterprises.originsms.data.db.ThreadLockEntity
import kotlinx.coroutines.launch

class ConversationListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SmsRepository(application)
    private val database = OriginDatabase.getInstance(application)

    private val _conversations = MutableLiveData<List<ConversationSummary>>(emptyList())
    val conversations: LiveData<List<ConversationSummary>> = _conversations

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadConversations() {
        viewModelScope.launch {
            _isLoading.value = true
            val all = repository.getConversations()
            // Hidden chats never surface in the main list — they only appear
            // inside the dedicated "Hidden chats" vault screen after authentication.
            _conversations.value = all.filter { !it.isHidden }
            _isLoading.value = false
        }
    }

    fun setLocked(threadId: Long, locked: Boolean) {
        viewModelScope.launch {
            val existing = database.threadLockDao().getForThread(threadId)
            database.threadLockDao().upsert(
                ThreadLockEntity(
                    threadId = threadId,
                    isLocked = locked,
                    isHidden = existing?.isHidden ?: false,
                    lockedAtMillis = if (locked) System.currentTimeMillis() else 0L
                )
            )
            loadConversations()
        }
    }

    fun setHidden(threadId: Long, hidden: Boolean) {
        viewModelScope.launch {
            val existing = database.threadLockDao().getForThread(threadId)
            database.threadLockDao().upsert(
                ThreadLockEntity(
                    threadId = threadId,
                    isLocked = existing?.isLocked ?: false,
                    isHidden = hidden,
                    lockedAtMillis = existing?.lockedAtMillis ?: 0L
                )
            )
            loadConversations()
        }
    }

    fun deleteThread(threadId: Long) {
        viewModelScope.launch {
            repository.deleteThread(threadId)
            loadConversations()
        }
    }

    fun markRead(threadId: Long) {
        viewModelScope.launch {
            repository.markThreadRead(threadId)
            loadConversations()
        }
    }

    suspend fun getHiddenConversations(): List<ConversationSummary> {
        return repository.getConversations().filter { it.isHidden }
    }
}
