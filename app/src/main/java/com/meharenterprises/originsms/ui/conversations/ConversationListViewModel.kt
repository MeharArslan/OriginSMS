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

    private var allLoadedConversations: List<ConversationSummary> = emptyList()
    private var currentSearchQuery: String = ""

    private val _conversations = MutableLiveData<List<ConversationSummary>>(emptyList())
    val conversations: LiveData<List<ConversationSummary>> = _conversations

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadConversations() {
        viewModelScope.launch {
            _isLoading.value = true
            val all = repository.getConversations()
            // Hidden and archived chats never surface in the main list — hidden
            // ones live in the PIN-gated vault, archived ones in their own view.
            allLoadedConversations = all.filter { !it.isHidden && !it.isArchived }
            applySearchFilter()
            _isLoading.value = false
        }
    }

    fun setSearchQuery(query: String) {
        currentSearchQuery = query
        applySearchFilter()
    }

    private fun applySearchFilter() {
        _conversations.value = if (currentSearchQuery.isBlank()) {
            allLoadedConversations
        } else {
            val q = currentSearchQuery.trim().lowercase()
            allLoadedConversations.filter {
                it.displayName.lowercase().contains(q) ||
                    it.address.lowercase().contains(q) ||
                    it.snippet.lowercase().contains(q)
            }
        }
    }

    private suspend fun upsertWithChange(threadId: Long, change: (ThreadLockEntity) -> ThreadLockEntity) {
        val existing = database.threadLockDao().getForThread(threadId) ?: ThreadLockEntity(threadId = threadId)
        database.threadLockDao().upsert(change(existing))
    }

    fun setLocked(threadId: Long, locked: Boolean) {
        viewModelScope.launch {
            upsertWithChange(threadId) {
                it.copy(isLocked = locked, lockedAtMillis = if (locked) System.currentTimeMillis() else 0L)
            }
            loadConversations()
        }
    }

    fun setHidden(threadId: Long, hidden: Boolean) {
        viewModelScope.launch {
            upsertWithChange(threadId) { it.copy(isHidden = hidden) }
            loadConversations()
        }
    }

    fun setMuted(threadIds: Set<Long>, muted: Boolean) {
        viewModelScope.launch {
            threadIds.forEach { id -> database.threadLockDao().setMuted(id, muted) }
            loadConversations()
        }
    }

    fun setArchived(threadIds: Set<Long>, archived: Boolean) {
        viewModelScope.launch {
            threadIds.forEach { id ->
                upsertWithChange(id) { it.copy(isArchived = archived) }
            }
            loadConversations()
        }
    }

    fun deleteThread(threadId: Long) {
        viewModelScope.launch {
            repository.deleteThread(threadId)
            loadConversations()
        }
    }

    fun deleteThreads(threadIds: Set<Long>) {
        viewModelScope.launch {
            threadIds.forEach { repository.deleteThread(it) }
            loadConversations()
        }
    }

    fun markRead(threadId: Long) {
        viewModelScope.launch {
            repository.markThreadRead(threadId)
            loadConversations()
        }
    }

    fun markReadMultiple(threadIds: Set<Long>) {
        viewModelScope.launch {
            threadIds.forEach { repository.markThreadRead(it) }
            loadConversations()
        }
    }

    suspend fun getHiddenConversations(): List<ConversationSummary> {
        return repository.getConversations().filter { it.isHidden }
    }

    suspend fun getArchivedConversations(): List<ConversationSummary> {
        return repository.getConversations().filter { it.isArchived && !it.isHidden }
    }
}
