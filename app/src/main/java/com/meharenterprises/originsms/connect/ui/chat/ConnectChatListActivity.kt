package com.meharenterprises.originsms.connect.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.connect.domain.model.ConnectConversation
import com.meharenterprises.originsms.connect.ui.auth.ConnectAuthActivity
import com.meharenterprises.originsms.connect.ui.contacts.ConnectContactsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ConnectChatListActivity : AppCompatActivity() {

    private val viewModel: ConnectChatListViewModel by viewModels()
    private lateinit var adapter: ConversationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_chat_list)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Origin Connect"

        adapter = ConversationAdapter(
            currentUserId = viewModel.currentUserId ?: "",
            onClick = { conv ->
                startActivity(Intent(this, ConnectChatActivity::class.java).apply {
                    putExtra("CONVERSATION_ID", conv.id)
                    putExtra("PARTICIPANT_ID", conv.participantId)
                    putExtra("PARTICIPANT_NAME", conv.participantName)
                    putExtra("PARTICIPANT_AVATAR", conv.participantAvatar)
                })
            }
        )

        findViewById<RecyclerView>(R.id.recyclerConversations).also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

        findViewById<FloatingActionButton>(R.id.fabNewChat).setOnClickListener {
            startActivity(Intent(this, ConnectContactsActivity::class.java))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.conversations.collect { adapter.submitList(it) }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_connect_chat_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logout {
                    startActivity(Intent(this, ConnectAuthActivity::class.java))
                    finishAffinity()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class ConversationAdapter(
    private val currentUserId: String,
    private val onClick: (ConnectConversation) -> Unit
) : ListAdapter<ConnectConversation, ConversationAdapter.VH>(DIFF) {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val avatar: ImageView = v.findViewById(R.id.imgAvatar)
        val name: TextView = v.findViewById(R.id.txtName)
        val lastMsg: TextView = v.findViewById(R.id.txtLastMessage)
        val time: TextView = v.findViewById(R.id.txtTime)
        val badge: TextView = v.findViewById(R.id.txtBadge)
        val onlineDot: View = v.findViewById(R.id.viewOnline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_connect_conversation, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val conv = getItem(position)
        holder.name.text = conv.participantName
        holder.lastMsg.text = conv.lastMessage.ifEmpty { "Tap to chat" }
        holder.time.text = if (conv.lastMessageTime > 0)
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(conv.lastMessageTime)) else ""
        holder.badge.visibility = if (conv.unreadCount > 0) View.VISIBLE else View.GONE
        holder.badge.text = if (conv.unreadCount > 99) "99+" else conv.unreadCount.toString()
        holder.onlineDot.visibility = if (conv.isOnline) View.VISIBLE else View.GONE

        if (conv.participantAvatar != null) {
            holder.avatar.load(conv.participantAvatar) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_person)
            }
        } else {
            holder.avatar.setImageResource(R.drawable.ic_person)
        }

        holder.itemView.setOnClickListener { onClick(conv) }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<ConnectConversation>() {
            override fun areItemsTheSame(a: ConnectConversation, b: ConnectConversation) = a.id == b.id
            override fun areContentsTheSame(a: ConnectConversation, b: ConnectConversation) = a == b
        }
    }
}
