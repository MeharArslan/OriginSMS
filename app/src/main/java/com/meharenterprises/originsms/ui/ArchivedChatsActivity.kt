package com.meharenterprises.originsms.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.ConversationSummary
import com.meharenterprises.originsms.core.SmsRepository
import com.meharenterprises.originsms.data.db.OriginDatabase
import com.meharenterprises.originsms.ui.conversations.ConversationAdapter
import com.meharenterprises.originsms.ui.thread.ThreadActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArchivedChatsActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var emptyState: TextView
    private lateinit var adapter: ConversationAdapter
    private lateinit var repository: SmsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archived_chats)

        repository = SmsRepository(this)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        recycler = findViewById(R.id.recyclerArchived)
        emptyState = findViewById(R.id.emptyArchived)

        adapter = ConversationAdapter(
            onClick = { conv -> openOrUnarchive(conv) },
            onLongClick = { conv -> showOptions(conv) }
        )
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        loadArchived()
    }

    override fun onResume() {
        super.onResume()
        loadArchived()
    }

    private fun loadArchived() {
        lifecycleScope.launch {
            val archived = withContext(Dispatchers.IO) {
                repository.getConversations().filter { it.isArchived && !it.isHidden && !it.isDeleted }
            }
            adapter.submitList(archived)
            emptyState.visibility = if (archived.isEmpty()) View.VISIBLE else View.GONE
            recycler.visibility = if (archived.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun openOrUnarchive(conv: ConversationSummary) {
        val intent = Intent(this, ThreadActivity::class.java).apply {
            putExtra(ThreadActivity.EXTRA_THREAD_ID, conv.threadId)
            putExtra(ThreadActivity.EXTRA_ADDRESS, conv.address)
            putExtra(ThreadActivity.EXTRA_DISPLAY_NAME, conv.displayName)
        }
        startActivity(intent)
    }

    private fun showOptions(conv: ConversationSummary) {
        AlertDialog.Builder(this)
            .setTitle(conv.displayName)
            .setItems(arrayOf("Open chat", "Unarchive")) { _, index ->
                when (index) {
                    0 -> openOrUnarchive(conv)
                    1 -> {
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                val dao = OriginDatabase.getInstance(this@ArchivedChatsActivity).threadLockDao()
                                val existing = dao.getForThread(conv.threadId)
                                if (existing != null) dao.upsert(existing.copy(isArchived = false))
                            }
                            android.widget.Toast.makeText(this@ArchivedChatsActivity,
                                "${conv.displayName} unarchived", android.widget.Toast.LENGTH_SHORT).show()
                            loadArchived()
                        }
                    }
                }
            }
            .show()
    }
}
