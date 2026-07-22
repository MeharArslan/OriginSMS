package com.meharenterprises.originsms.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
                lifecycleScope.launch {
                    val idsToRestore = if (selectedIds.isNotEmpty()) selectedIds.toSet()
                        else adapter.currentList.map { it.threadId }.toSet()
                    withContext(Dispatchers.IO) {
                        idsToRestore.forEach { id -> database.threadLockDao().setDeletedAt(id, 0L) }
                    }
                    val msg = if (selectedIds.isNotEmpty()) "Restored ${selectedIds.size} chat(s)" else "All chats restored"
                    selectedIds.clear()
                    loadTrash()
                    android.widget.Toast.makeText(this@TrashActivity, msg, android.widget.Toast.LENGTH_SHORT).show()
                }
                true
            }

            R.id.action_delete_forever -> {
                val idsToDelete = if (selectedIds.isNotEmpty()) selectedIds.toSet()
                    else adapter.currentList.map { it.threadId }.toSet()
                val msg = if (selectedIds.isNotEmpty()) "Delete ${selectedIds.size} selected chat(s) forever?"
                    else "Delete all ${idsToDelete.size} chat(s) forever?"
                androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Delete forever?")
                    .setMessage(msg)
                    .setPositiveButton("Delete") { _, _ ->
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                idsToDelete.forEach { id -> database.threadLockDao().clear(id) }
                            }
                            selectedIds.clear()
                            loadTrash()
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
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
                    holder.avatar.setBackgroundColor(color)
                } catch (_: Exception) {
                    setInitialAvatar(holder.avatar, conv.displayName, color)
                }
            } else {
                setInitialAvatar(holder.avatar, conv.displayName, color)
            }

            // Selection state
            holder.checkmark.visibility = if (isSelected) android.view.View.VISIBLE else android.view.View.GONE
            holder.overlay.visibility = if (isSelected) android.view.View.VISIBLE else android.view.View.GONE

            holder.itemView.setOnClickListener { onClick(conv) }
        }
    }

    private fun setInitialAvatar(
        view: com.google.android.material.imageview.ShapeableImageView,
        name: String, bgColor: Int
    ) {
        val initial = name.firstOrNull { it.isLetterOrDigit() }?.uppercaseChar()?.toString() ?: "?"
        val bitmap = android.graphics.Bitmap.createBitmap(96, 96, android.graphics.Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)
        val paint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG)
        paint.color = bgColor
        canvas.drawCircle(48f, 48f, 48f, paint)
        paint.color = android.graphics.Color.WHITE
        paint.textSize = 40f
        paint.typeface = android.graphics.Typeface.DEFAULT_BOLD
        paint.textAlign = android.graphics.Paint.Align.CENTER
        val metrics = paint.fontMetrics
        val y = 48f - (metrics.ascent + metrics.descent) / 2f
        canvas.drawText(initial, 48f, y, paint)
        view.setImageBitmap(bitmap)
    }

    companion object {
        val TRASH_DIFF = object : DiffUtil.ItemCallback<ConversationSummary>() {
            override fun areItemsTheSame(a: ConversationSummary, b: ConversationSummary) = a.threadId == b.threadId
            override fun areContentsTheSame(a: ConversationSummary, b: ConversationSummary) = a == b
        }
    }
}