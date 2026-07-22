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
        // Trash items do not open conversation - select only
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
            holder.txtDaysLeft.text = if (daysLeft > 0) "$daysLeft Days Left" else "Expires Today"
            val mc = try {
                val cur = holder.itemView.context.contentResolver.query(
                    android.provider.Telephony.Sms.CONTENT_URI, arrayOf("COUNT(*)"),
                    "${android.provider.Telephony.Sms.THREAD_ID} = ?",
                    arrayOf(conv.threadId.toString()), null)
                cur?.use { if (it.moveToFirst()) it.getInt(0) else 0 } ?: 0
            } catch (_: Exception) { 0 }
            holder.txtMsgCount.text = if (mc > 0) "$mc Messages" else "No messages"

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

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { finish(); true }
            R.id.action_restore_all -> {
                lifecycleScope.launch {
                    val dao = com.meharenterprises.originsms.data.db.OriginDatabase
                        .getInstance(this@TrashActivity).threadLockDao()
                    dao.getTrashedThreads().forEach { dao.setDeletedAt(it.threadId, 0L) }
                    loadTrash()
                }
                true
            }
            R.id.action_delete_forever -> {
                lifecycleScope.launch {
                    val dao = com.meharenterprises.originsms.data.db.OriginDatabase
                        .getInstance(this@TrashActivity).threadLockDao()
                    dao.getTrashedThreads().forEach { dao.clear(it.threadId) }
                    loadTrash()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        val TRASH_DIFF = object : DiffUtil.ItemCallback<ConversationSummary>() {
            override fun areItemsTheSame(a: ConversationSummary, b: ConversationSummary) = a.threadId == b.threadId
            override fun areContentsTheSame(a: ConversationSummary, b: ConversationSummary) = a == b
        }
    }
}
