package com.meharenterprises.originsms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.ContactsHelper
import com.meharenterprises.originsms.data.db.OriginDatabase
import com.meharenterprises.originsms.data.db.StarredMessageEntity
import com.meharenterprises.originsms.ui.thread.ThreadActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StarredMessagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starred_messages)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Starred Messages"
        supportActionBar?.setDisplayShowTitleEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // Filter by threadId if opened from a specific thread
        val filterThreadId = intent.getLongExtra("FILTER_THREAD_ID", -1L)

        val recycler = findViewById<RecyclerView>(R.id.recyclerStarred)
        val empty = findViewById<TextView>(R.id.emptyStarred)
        recycler.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val allStarred = OriginDatabase.getInstance(this@StarredMessagesActivity)
                .starredMessageDao().getAll()

            // Filter per thread if needed
            val starred = if (filterThreadId != -1L) {
                allStarred.filter { it.threadId == filterThreadId }
            } else allStarred

            if (starred.isEmpty()) {
                empty.visibility = View.VISIBLE
                recycler.visibility = View.GONE
            } else {
                empty.visibility = View.GONE
                recycler.visibility = View.VISIBLE

                // Resolve contact names
                val contactsHelper = ContactsHelper(this@StarredMessagesActivity)
                val nameCache = mutableMapOf<String, String>()

                recycler.adapter = StarredAdapter(starred, contactsHelper, nameCache) { item ->
                    // Open thread and jump to specific message
                    val intent = android.content.Intent(this@StarredMessagesActivity, ThreadActivity::class.java).apply {
                        putExtra(ThreadActivity.EXTRA_THREAD_ID, item.threadId)
                        putExtra(ThreadActivity.EXTRA_ADDRESS, item.address)
                        putExtra(ThreadActivity.EXTRA_DISPLAY_NAME,
                            nameCache[item.address] ?: item.address)
                        putExtra(com.meharenterprises.originsms.ui.thread.ThreadActivity.EXTRA_HIGHLIGHT_MESSAGE_ID, item.messageId)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    inner class StarredAdapter(
        private val items: List<StarredMessageEntity>,
        private val contactsHelper: ContactsHelper,
        private val nameCache: MutableMap<String, String>,
        private val onClick: (StarredMessageEntity) -> Unit
    ) : RecyclerView.Adapter<StarredAdapter.VH>() {

        inner class VH(v: View) : RecyclerView.ViewHolder(v) {
            val txtBody: TextView = v.findViewById(R.id.txtStarredBody)
            val txtMeta: TextView = v.findViewById(R.id.txtStarredMeta)
            val txtContact: TextView? = v.findViewById(R.id.txtStarredContact)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            VH(LayoutInflater.from(parent.context).inflate(R.layout.item_starred_message, parent, false))

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = items[position]
            holder.txtBody.text = item.body

            val name = nameCache.getOrPut(item.address) {
                contactsHelper.resolve(item.address).displayName
            }
            val date = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()).format(Date(item.dateMillis))
            holder.txtMeta.text = "$date"
            holder.txtContact?.text = name

            holder.itemView.setOnClickListener { onClick(item) }
        }
    }
}
