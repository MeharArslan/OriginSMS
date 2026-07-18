package com.meharenterprises.originsms.connect.ui.chat

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.*
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
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.connect.domain.model.ConnectMessage
import com.meharenterprises.originsms.connect.domain.model.MessageType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ConnectChatActivity : AppCompatActivity() {

    private val viewModel: ConnectChatViewModel by viewModels()
    private lateinit var adapter: ConnectMessageAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var editMessage: EditText
    private var typingJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_chat)

        val conversationId = intent.getStringExtra("CONVERSATION_ID") ?: run { finish(); return }
        val participantName = intent.getStringExtra("PARTICIPANT_NAME") ?: ""
        val participantAvatar = intent.getStringExtra("PARTICIPANT_AVATAR")

        viewModel.init(conversationId)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = participantName
        toolbar.setNavigationOnClickListener { finish() }

        // Avatar in toolbar
        val imgAvatar = toolbar.menu // handled via toolbar subtitle for simplicity

        adapter = ConnectMessageAdapter(currentUserId = viewModel.currentUserId)
        recycler = findViewById(R.id.recyclerMessages)
        recycler.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }
        recycler.adapter = adapter

        editMessage = findViewById(R.id.editMessage)
        val btnSend = findViewById<View>(R.id.btnSend)
        val txtTyping = findViewById<TextView>(R.id.txtTypingIndicator)

        btnSend.setOnClickListener {
            val text = editMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                viewModel.sendMessage(text)
                editMessage.setText("")
            }
        }

        editMessage.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: android.text.Editable?) {
                typingJob?.cancel()
                viewModel.sendTyping(true)
                typingJob = lifecycleScope.launch {
                    delay(2000)
                    viewModel.sendTyping(false)
                }
            }
        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.messages.collect { messages ->
                        adapter.submitList(messages)
                        if (messages.isNotEmpty()) recycler.scrollToPosition(messages.size - 1)
                    }
                }
                launch {
                    viewModel.isTyping.collect { typing ->
                        txtTyping.visibility = if (typing) View.VISIBLE else View.GONE
                        txtTyping.text = "$participantName is typing..."
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.sendTyping(false)
        viewModel.markRead()
    }
}

class ConnectMessageAdapter(
    private val currentUserId: String
) : ListAdapter<ConnectMessage, RecyclerView.ViewHolder>(DIFF) {

    companion object {
        private const val VIEW_SENT = 1
        private const val VIEW_RECEIVED = 2
        val DIFF = object : DiffUtil.ItemCallback<ConnectMessage>() {
            override fun areItemsTheSame(a: ConnectMessage, b: ConnectMessage) = a.id == b.id
            override fun areContentsTheSame(a: ConnectMessage, b: ConnectMessage) = a == b
        }
    }

    override fun getItemViewType(position: Int) =
        if (getItem(position).senderId == currentUserId) VIEW_SENT else VIEW_RECEIVED

    inner class SentVH(v: View) : RecyclerView.ViewHolder(v) {
        val txtBody: TextView = v.findViewById(R.id.txtBody)
        val txtTime: TextView = v.findViewById(R.id.txtTime)
        val txtStatus: TextView = v.findViewById(R.id.txtStatus)
    }

    inner class ReceivedVH(v: View) : RecyclerView.ViewHolder(v) {
        val txtBody: TextView = v.findViewById(R.id.txtBody)
        val txtTime: TextView = v.findViewById(R.id.txtTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_SENT)
            SentVH(inflater.inflate(R.layout.item_connect_message_sent, parent, false))
        else
            ReceivedVH(inflater.inflate(R.layout.item_connect_message_received, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = getItem(position)
        val timeStr = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(msg.createdAt))
        val displayBody = if (msg.type == MessageType.DELETED) "🚫 This message was deleted" else msg.body

        when (holder) {
            is SentVH -> {
                holder.txtBody.text = displayBody
                holder.txtTime.text = timeStr
                holder.txtStatus.text = when (msg.status.name) {
                    "SENDING" -> "⏳"
                    "SENT" -> "✓"
                    "DELIVERED" -> "✓✓"
                    "READ" -> "✓✓" // blue via color
                    "FAILED" -> "✗"
                    else -> ""
                }
            }
            is ReceivedVH -> {
                holder.txtBody.text = displayBody
                holder.txtTime.text = timeStr
            }
        }
    }
}
