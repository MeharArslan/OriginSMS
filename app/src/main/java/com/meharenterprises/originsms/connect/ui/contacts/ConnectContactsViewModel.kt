package com.meharenterprises.originsms.connect.ui.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.connect.domain.model.ConnectConversation
import com.meharenterprises.originsms.connect.domain.model.ConnectUser
import com.meharenterprises.originsms.connect.domain.repository.ContactRepository
import com.meharenterprises.originsms.connect.domain.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectContactsViewModel @Inject constructor(
    private val contactRepo: ContactRepository,
    private val messageRepo: MessageRepository
) : ViewModel() {

    val contacts: StateFlow<List<ConnectUser>> =
        contactRepo.getRegisteredContacts()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun syncContacts(phones: List<String>) {
        viewModelScope.launch { contactRepo.syncContacts(phones) }
    }

    fun openOrCreateConversation(participantId: String, onResult: (ConnectConversation) -> Unit) {
        viewModelScope.launch {
            messageRepo.getOrCreateConversation(participantId).fold(
                onSuccess = { onResult(it) },
                onFailure = { /* handle error */ }
            )
        }
    }
}

class ConnectContactsAdapter(
    private val onChat: (ConnectUser) -> Unit
) : ListAdapter<ConnectUser, ConnectContactsAdapter.VH>(DIFF) {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val avatar: ImageView = v.findViewById(R.id.imgAvatar)
        val name: TextView = v.findViewById(R.id.txtName)
        val phone: TextView = v.findViewById(R.id.txtPhone)
        val onlineDot: View = v.findViewById(R.id.viewOnline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_connect_contact, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val user = getItem(position)
        holder.name.text = user.displayName
        holder.phone.text = user.phone
        holder.onlineDot.visibility = if (user.isOnline) View.VISIBLE else View.GONE
        if (user.avatarUrl != null) {
            holder.avatar.load(user.avatarUrl) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_person)
            }
        } else {
            holder.avatar.setImageResource(R.drawable.ic_person)
        }
        holder.itemView.setOnClickListener { onChat(user) }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<ConnectUser>() {
            override fun areItemsTheSame(a: ConnectUser, b: ConnectUser) = a.id == b.id
            override fun areContentsTheSame(a: ConnectUser, b: ConnectUser) = a == b
        }
    }
}
