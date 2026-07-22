package com.meharenterprises.originsms.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.ConversationSummary
import com.meharenterprises.originsms.core.SmsRepository
import com.meharenterprises.originsms.data.db.OriginDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TrashActivity : AppCompatActivity() {

    private lateinit var database: OriginDatabase
    private lateinit var adapter: TrashAdapter
    private val selectedIds = mutableSetOf<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trash)

        database = OriginDatabase.getInstance(this)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        adapter = TrashAdapter(selectedIds) { conv ->
            // Toggle selection
            if (selectedIds.contains(conv.threadId)) selectedIds.remove(conv.threadId)
            else selectedIds.add(conv.threadId)
            adapter.notifyDataSetChanged()
        }

        val recycler = findViewById<RecyclerView>(R.id.recyclerTrash)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        loadTrash()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_trash, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { finish(); true }

            R.id.action_restore_all -> {
                if (selectedIds.isEmpty()) {
                    android.widget.Toast.makeText(this, "Select one or more conversations", android.widget.Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            selectedIds.forEach { id -> database.threadLockDao().setDeletedAt(id, 0L) }
                        }
                        selectedIds.clear()
                        loadTrash()
                        android.widget.Toast.makeText(this@TrashActivity, "Restored", android.widget.Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }

            R.id.action_delete_forever -> {
                if (selectedIds.isEmpty()) {
                    android.widget.Toast.makeText(this, "Select one or more conversations", android.widget.Toast.LENGTH_SHORT).show()
                } else {
                    androidx.appcompat.app.AlertDialog.Builder(this)
                        .setTitle("Delete forever?")
                        .setMessage("${selectedIds.size} conversation(s) will be permanently deleted.")
                        .setPositiveButton("Delete") { _, _ ->
                            lifecycleScope.launch {
                                withContext(Dispatchers.IO) {
                                    selectedIds.forEach { id -> database.threadLockDao().clear(id) }
                                }
                                selectedIds.clear()
                                loadTrash()
                            }
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadTrash() {
        lifecycleScope.launch {
            val trashedThreadIds = withContext(Dispatchers.IO) {
                database.threadLockDao().getTrashedThreads()
            }
            val deletedAtMap = trashedThreadIds.associate { it.threadId to it.deletedAtMillis }
            val allConvs = withContext(Dispatchers.IO) {
                com.meharenterprises.originsms.core.SmsRepository(this@TrashActivity).getConversations()
            }
            val trashList = allConvs.filter { deletedAtMap.containsKey(it.threadId) }
                .sortedByDescending { deletedAtMap[it.threadId] }

            val emptyView = findViewById<android.widget.TextView>(R.id.emptyTrash)
            val recycler = findViewById<RecyclerView>(R.id.recyclerTrash)
            if (trashList.isEmpty()) {
                emptyView?.visibility = android.view.View.VISIBLE
                recycler?.visibility = android.view.View.GONE
            } else {
                emptyView?.visibility = android.view.View.GONE
                recycler?.visibility = android.view.View.VISIBLE
            }
            adapter.submitList(trashList)
            adapter.deletedAtMap = deletedAtMap
        }
    }

    inner class TrashAdapter(
        private val selectedIds: MutableSet<Long>,
        private val onClick: (ConversationSummary) -> Unit
    ) : ListAdapter<ConversationSummary, TrashAdapter.VH>(TRASH_DIFF) {

        var deletedAtMap: Map<Long, Long> = emptyMap()

        inner class VH(v: android.view.View) : RecyclerView.ViewHolder(v) {
            val avatar = v.findViewById<com.google.android.material.imageview.ShapeableImageView>(R.id.imgTrashAvatar)
            val checkmark = v.findViewById<android.widget.ImageView>(R.id.imgCheckmark)
            val overlay = v.findViewById<android.view.View>(R.id.selectionOverlay)
            val name = v.findViewById<android.widget.TextView>(R.id.txtTrashName)
            val count = v.findViewById<android.widget.TextView>(R.id.txtMsgCount)
            val days = v.findViewById<android.widget.TextView>(R.id.txtDaysLeft)
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): VH {
            val v = android.view.LayoutInflater.from(parent.context)
                .inflate(R.layout.item_trash, parent, false)
            return VH(v)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val conv = getItem(position)
            val isSelected = selectedIds.contains(conv.threadId)
            val deletedAt = deletedAtMap[conv.threadId] ?: 0L
            val daysLeft = if (deletedAt > 0) {
                val diff = 30 - ((System.currentTimeMillis() - deletedAt) / 86_400_000)
                diff.coerceAtLeast(0)
            } else 30L

            holder.name.text = conv.displayName

            // Get actual message count
            try {
                val cur = contentResolver.query(
                    android.provider.Telephony.Sms.CONTENT_URI, arrayOf("COUNT(*)"),
                    "${android.provider.Telephony.Sms.THREAD_ID} = ?",
                    arrayOf(conv.threadId.toString()), null)
                val mc = cur?.use { if (it.moveToFirst()) it.getInt(0) else 0 } ?: 0
                holder.count.text = if (mc == 1) "1 Message" else "$mc Messages"
            } catch (_: Exception) { holder.count.text = "Messages" }

            holder.days.text = if (daysLeft > 0) "$daysLeft Days Left" else "Expires Today"

            // Avatar: colored circle with initial
            val colors = listOf(0xFF6200EE, 0xFF03DAC5, 0xFFFF6B00, 0xFF4CAF50, 0xFFE91E63, 0xFF2196F3)
            val color = colors[conv.displayName.hashCode().and(0x7FFFFFFF) % colors.size].toInt()

            if (!conv.contactPhotoUri.isNullOrBlank()) {
                try {
                    holder.avatar.setImageURI(android.net.Uri.parse(conv.contactPhotoUri))
                } catch (_: Exception) {
                    holder.avatar.setImageDrawable(null)
                    holder.avatar.setBackgroundColor(color)
                }
            } else {
                holder.avatar.setImageDrawable(null)
                holder.avatar.setBackgroundColor(color)
            }

            // Selection state
            holder.checkmark.visibility = if (isSelected) android.view.View.VISIBLE else android.view.View.GONE
            holder.overlay.visibility = if (isSelected) android.view.View.VISIBLE else android.view.View.GONE

            holder.itemView.setOnClickListener { onClick(conv) }
        }
    }

    companion object {
        val TRASH_DIFF = object : DiffUtil.ItemCallback<ConversationSummary>() {
            override fun areItemsTheSame(a: ConversationSummary, b: ConversationSummary) = a.threadId == b.threadId
            override fun areContentsTheSame(a: ConversationSummary, b: ConversationSummary) = a == b
        }
    }
}