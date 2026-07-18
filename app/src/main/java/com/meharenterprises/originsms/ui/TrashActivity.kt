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
import com.meharenterprises.originsms.ui.thread.ThreadActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TrashActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var emptyState: TextView
    private lateinit var adapter: TrashAdapter
    private lateinit var database: OriginDatabase
    private lateinit var repository: SmsRepository

    private var deletedTimestamps: Map<Long, Long> = emptyMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trash)

        database = OriginDatabase.getInstance(this)
        repository = SmsRepository(this)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        findViewById<android.view.View>(R.id.btnRestoreAll).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Restore all chats?")
                .setMessage("All chats will be restored from Trash.")
                .setPositiveButton("Restore all") { _, _ ->
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            database.threadLockDao().getTrashedThreads().forEach { e ->
                                database.threadLockDao().upsert(e.copy(deletedAtMillis = 0L))
                            }
                        }
                        android.widget.Toast.makeText(this@TrashActivity, "All chats restored", android.widget.Toast.LENGTH_SHORT).show()
                        loadTrash()
                    }
                }
                .setNegativeButton(android.R.string.cancel, null).show()
        }

        findViewById<android.view.View>(R.id.btnDeleteAll).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete all permanently?")
                .setMessage("All chats in Trash will be permanently deleted. This cannot be undone.")
                .setPositiveButton("Delete all") { _, _ ->
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            database.threadLockDao().getTrashedThreads().forEach { e ->
                                repository.deleteThread(e.threadId)
                                database.threadLockDao().clear(e.threadId)
                            }
                        }
                        android.widget.Toast.makeText(this@TrashActivity, "All permanently deleted", android.widget.Toast.LENGTH_SHORT).show()
                        loadTrash()
                    }
                }
                .setNegativeButton(android.R.string.cancel, null).show()
        }

        recycler = findViewById(R.id.recyclerTrash)
        emptyState = findViewById(R.id.emptyTrash)

        adapter = TrashAdapter(
            onClick = { conv -> openDeletedChat(conv) },
            onRestore = { conv -> restoreChat(conv) },
            onDelete = { conv -> confirmPermanentDelete(conv) }
        )
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        // Swipe right = restore, swipe left = permanent delete
        val swipeHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val conv = adapter.currentList[vh.adapterPosition]
                if (dir == ItemTouchHelper.RIGHT) restoreChat(conv)
                else confirmPermanentDelete(conv)
            }

            override fun onChildDraw(c: android.graphics.Canvas, rv: RecyclerView,
                vh: RecyclerView.ViewHolder, dX: Float, dY: Float, state: Int, active: Boolean) {
                val itemView = vh.itemView
                val paint = android.graphics.Paint()
                if (dX > 0) {
                    // Swipe right = green restore
                    paint.color = android.graphics.Color.parseColor("#388E3C")
                    c.drawRect(itemView.left.toFloat(), itemView.top.toFloat(),
                        itemView.left + dX, itemView.bottom.toFloat(), paint)
                } else {
                    // Swipe left = red delete
                    paint.color = android.graphics.Color.parseColor("#D32F2F")
                    c.drawRect(itemView.right + dX, itemView.top.toFloat(),
                        itemView.right.toFloat(), itemView.bottom.toFloat(), paint)
                }
                super.onChildDraw(c, rv, vh, dX, dY, state, active)
            }
        })
        swipeHelper.attachToRecyclerView(recycler)

        loadTrash()
    }

    override fun onResume() {
        super.onResume()
        loadTrash()
    }

    private fun loadTrash() {
        lifecycleScope.launch {
            val allConvs = withContext(Dispatchers.IO) {
                repository.getConversations().filter { it.isDeleted }
            }
            deletedTimestamps = withContext(Dispatchers.IO) {
                database.threadLockDao().getTrashedThreads()
                    .associate { it.threadId to it.deletedAtMillis }
            }
            adapter.submitList(allConvs)
            emptyState.visibility = if (allConvs.isEmpty()) View.VISIBLE else View.GONE
            recycler.visibility = if (allConvs.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun openDeletedChat(conv: ConversationSummary) {
        val intent = Intent(this, ThreadActivity::class.java).apply {
            putExtra(ThreadActivity.EXTRA_THREAD_ID, conv.threadId)
            putExtra(ThreadActivity.EXTRA_ADDRESS, conv.address)
            putExtra(ThreadActivity.EXTRA_DISPLAY_NAME, conv.displayName)
            putExtra(ThreadActivity.EXTRA_FROM_TRASH, true)  // prevents auto-restore
        }
        startActivity(intent)
    }

    private fun restoreChat(conv: ConversationSummary) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val existing = database.threadLockDao().getForThread(conv.threadId)
                if (existing != null) database.threadLockDao().upsert(existing.copy(deletedAtMillis = 0L))
            }
            android.widget.Toast.makeText(this@TrashActivity,
                "${conv.displayName} restored", android.widget.Toast.LENGTH_SHORT).show()
            loadTrash()
        }
    }

    private fun confirmPermanentDelete(conv: ConversationSummary) {
        AlertDialog.Builder(this)
            .setTitle("Delete permanently?")
            .setMessage("${conv.displayName}\n\nAll messages will be deleted. This cannot be undone.")
            .setPositiveButton("Delete permanently") { _, _ ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        repository.deleteThread(conv.threadId)
                        database.threadLockDao().clear(conv.threadId)
                    }
                    android.widget.Toast.makeText(this@TrashActivity,
                        "Permanently deleted", android.widget.Toast.LENGTH_SHORT).show()
                    loadTrash()
                }
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                // Restore item position in recycler
                adapter.notifyDataSetChanged()
            }
            .show()
    }

    inner class TrashAdapter(
        private val onClick: (ConversationSummary) -> Unit,
        private val onRestore: (ConversationSummary) -> Unit,
        private val onDelete: (ConversationSummary) -> Unit
    ) : ListAdapter<ConversationSummary, TrashAdapter.ViewHolder>(TRASH_DIFF) {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imgAvatar: android.widget.ImageView = itemView.findViewById(R.id.imgTrashAvatar)
            val txtName: TextView = itemView.findViewById(R.id.txtTrashName)
            val txtDate: TextView = itemView.findViewById(R.id.txtTrashDate)
            val txtSnippet: TextView = itemView.findViewById(R.id.txtTrashSnippet)
            val txtDaysLeft: TextView = itemView.findViewById(R.id.txtDaysLeft)
            val txtMsgCount: TextView = itemView.findViewById(R.id.txtMsgCount)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_trash, parent, false))

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val conv = getItem(position)
            val deletedAt = deletedTimestamps[conv.threadId] ?: 0L
            val daysLeft = if (deletedAt > 0L)
                30L - java.util.concurrent.TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - deletedAt)
            else 30L

            holder.txtName.text = conv.displayName
            holder.txtSnippet.text = conv.snippet.take(80).ifBlank { "No messages" }
            holder.txtDate.text = if (deletedAt > 0L)
                SimpleDateFormat("MMM d", Locale.getDefault()).format(Date(deletedAt)) else ""
            holder.txtDaysLeft.text = if (daysLeft > 0) "$daysLeft days" else "Expires today"
            holder.txtMsgCount.text = if (deletedAt > 0L)
                "Deleted ${SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date(deletedAt))}"
            else "Swipe right to restore, left to delete"

            // Load avatar
            val uri = conv.contactPhotoUri
            if (uri != null) {
                try {
                    holder.itemView.context.contentResolver.openInputStream(android.net.Uri.parse(uri))?.use { s ->
                        val bmp = android.graphics.BitmapFactory.decodeStream(s)
                        if (bmp != null) {
                            holder.imgAvatar.setImageBitmap(bmp)
                            holder.imgAvatar.scaleType = android.widget.ImageView.ScaleType.CENTER_CROP
                            holder.imgAvatar.setPadding(0, 0, 0, 0)
                            holder.imgAvatar.clipToOutline = true
                        }
                    }
                } catch (_: Exception) {}
            } else {
                holder.imgAvatar.setImageResource(R.drawable.ic_person)
                holder.imgAvatar.setPadding(16, 16, 16, 16)
            }

            // Single tap = open chat, long press = restore/delete options
            holder.itemView.setOnClickListener { onClick(conv) }
            holder.itemView.setOnLongClickListener {
                AlertDialog.Builder(holder.itemView.context)
                    .setTitle(conv.displayName)
                    .setItems(arrayOf("✓  Restore chat", "🗑  Delete permanently")) { _, i ->
                        when (i) {
                            0 -> onRestore(conv)
                            1 -> onDelete(conv)
                        }
                    }.show()
                true
            }
        }
    }

    companion object {
        val TRASH_DIFF = object : DiffUtil.ItemCallback<ConversationSummary>() {
            override fun areItemsTheSame(a: ConversationSummary, b: ConversationSummary) = a.threadId == b.threadId
            override fun areContentsTheSame(a: ConversationSummary, b: ConversationSummary) = a == b
        }
    }
}
