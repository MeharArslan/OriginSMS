package com.meharenterprises.originsms.ui.conversations

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.ConversationSummary
import com.meharenterprises.originsms.ui.DpViewActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ConversationAdapter(
    private val onClick: (ConversationSummary) -> Unit,
    private val onLongClick: (ConversationSummary) -> Unit,
    private val selectionModeEnabled: Boolean = true
) : ListAdapter<ConversationSummary, ConversationAdapter.ViewHolder>(DIFF) {

    private val selectedThreadIds = mutableSetOf<Long>()
    var isSelectionMode: Boolean = false
        private set

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)
        val imgLockBadge: ImageView? = itemView.findViewById(R.id.imgLockBadge)
        val imgSelectedCheck: ImageView? = itemView.findViewById(R.id.imgSelectedCheck)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtSnippet: TextView = itemView.findViewById(R.id.txtSnippet)
        val txtTimestamp: TextView? = itemView.findViewById(R.id.txtTimestamp)
        val txtUnreadBadge: TextView = itemView.findViewById(R.id.txtUnreadBadge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_conversation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val context = holder.itemView.context
        val isSelected = selectedThreadIds.contains(item.threadId)

        holder.txtName.text = item.displayName
        if (!item.draftText.isNullOrBlank()) {
            // Show draft with red "Draft:" prefix
            val spannable = android.text.SpannableString("Draft: ${item.draftText}")
            spannable.setSpan(
                android.text.style.ForegroundColorSpan(android.graphics.Color.RED),
                0, 6,
                android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            holder.txtSnippet.text = spannable
        } else {
            holder.txtSnippet.text = if (item.isLocked) {
                context.getString(R.string.notif_locked_chat_content)
            } else {
                item.snippet
            }
        }
        holder.txtTimestamp?.text = formatTimestamp(item.dateMillis)

        holder.imgLockBadge?.visibility = if (item.isLocked) View.VISIBLE else View.GONE
        holder.imgSelectedCheck?.visibility = if (isSelected) View.VISIBLE else View.GONE
        // Google Messages style: only avatar gets the circular selection indicator,
        // the row background stays transparent (no full-row highlight)
        holder.itemView.isActivated = false

        if (item.unreadCount > 0 && !item.isLocked) {
            holder.txtUnreadBadge.visibility = View.VISIBLE
            holder.txtUnreadBadge.text = if (item.unreadCount > 9) "9+" else item.unreadCount.toString()
            holder.txtName.setTypeface(null, android.graphics.Typeface.BOLD)
        } else {
            holder.txtUnreadBadge.visibility = View.GONE
        }

        if (!isSelected) {
            if (item.contactPhotoUri != null) {
                try {
                    val uri = Uri.parse(item.contactPhotoUri)
                    context.contentResolver.openInputStream(uri)?.use { stream ->
                        val bitmap = BitmapFactory.decodeStream(stream)
                        if (bitmap != null) {
                            holder.imgAvatar.setImageBitmap(bitmap)
                            holder.imgAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
                            holder.imgAvatar.setPadding(0, 0, 0, 0)
                            holder.imgAvatar.clipToOutline = true
                        }
                    }
                } catch (_: Exception) {
                    holder.imgAvatar.setImageResource(R.drawable.ic_person)
                }
            } else {
                holder.imgAvatar.setImageResource(R.drawable.ic_person)
                holder.imgAvatar.scaleType = ImageView.ScaleType.CENTER_INSIDE
                val pad = (8 * context.resources.displayMetrics.density).toInt()
                holder.imgAvatar.setPadding(pad, pad, pad, pad)
            }
        }

        // Avatar tap → full-screen DP preview (Google Messages style)
        holder.imgAvatar.setOnClickListener {
            if (!isSelectionMode) {
                val uri = item.contactPhotoUri
                if (uri != null) {
                    val intent = android.content.Intent(holder.itemView.context, DpViewActivity::class.java).apply {
                        putExtra("photoUri", uri)
                        putExtra("displayName", item.displayName)
                    }
                    holder.itemView.context.startActivity(intent)
                }
            }
        }

        setAvatar(holder, item)
        holder.itemView.setOnClickListener {
            if (isSelectionMode) {
                toggleSelection(item.threadId)
                onLongClick(item)  // notify activity to update bar even when deselecting
            } else {
                onClick(item)
            }
        }
        holder.itemView.setOnLongClickListener {
            if (!selectionModeEnabled) {
                onLongClick(item)
            } else if (!isSelectionMode) {
                isSelectionMode = true
                selectedThreadIds.add(item.threadId)
                notifyDataSetChanged()
                onLongClick(item)
            }
            true
        }
    }

    private fun toggleSelection(threadId: Long) {
        if (selectedThreadIds.contains(threadId)) {
            selectedThreadIds.remove(threadId)
        } else {
            selectedThreadIds.add(threadId)
        }
        if (selectedThreadIds.isEmpty()) {
            isSelectionMode = false
        }
        notifyDataSetChanged()
    }

    fun getSelectedThreadIds(): Set<Long> = selectedThreadIds.toSet()

    fun getSelectedCount(): Int = selectedThreadIds.size

    fun clearSelection() {
        selectedThreadIds.clear()
        isSelectionMode = false
        notifyDataSetChanged()
    }

    private fun formatTimestamp(millis: Long): String {
        if (millis == 0L) return ""
        val now = Calendar.getInstance()
        val then = Calendar.getInstance().apply { timeInMillis = millis }

        return when {
            now.get(Calendar.YEAR) == then.get(Calendar.YEAR) &&
                now.get(Calendar.DAY_OF_YEAR) == then.get(Calendar.DAY_OF_YEAR) ->
                SimpleDateFormat("h:mm a", Locale.getDefault()).format(then.time)

            now.get(Calendar.YEAR) == then.get(Calendar.YEAR) &&
                now.get(Calendar.DAY_OF_YEAR) - then.get(Calendar.DAY_OF_YEAR) == 1 ->
                "Yesterday"

            now.get(Calendar.YEAR) == then.get(Calendar.YEAR) ->
                SimpleDateFormat("MMM d", Locale.getDefault()).format(then.time)

            else ->
                SimpleDateFormat("MM/dd/yy", Locale.getDefault()).format(then.time)
        }
    }

    private fun setAvatar(holder: ViewHolder, conv: com.meharenterprises.originsms.core.ConversationSummary) {
        // Google Messages color palette - exact match
        val colors = listOf(
            0xFFE53935.toInt(), // red
            0xFFE91E63.toInt(), // pink
            0xFF9C27B0.toInt(), // purple
            0xFF3F51B5.toInt(), // indigo
            0xFF1976D2.toInt(), // blue
            0xFF0097A7.toInt(), // cyan
            0xFF388E3C.toInt(), // green
            0xFFF57C00.toInt(), // orange
            0xFF795548.toInt(), // brown
            0xFF455A64.toInt()  // blue-grey
        )
        val color = colors[conv.displayName.hashCode().and(0x7FFFFFFF) % colors.size]

        // Try contact photo first
        if (!conv.contactPhotoUri.isNullOrBlank()) {
            try {
                holder.imgAvatar.setImageURI(android.net.Uri.parse(conv.contactPhotoUri))
                return
            } catch (_: Exception) {}
        }

        // 192x192 high-res bitmap for crisp rendering
        val S = 192
        val half = S / 2f
        val bmp = android.graphics.Bitmap.createBitmap(S, S, android.graphics.Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bmp)
        val paint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG)

        // Colored background circle
        paint.color = color
        canvas.drawCircle(half, half, half, paint)

        val isPhoneOnly = conv.displayName
            .replace("+","").replace(" ","").replace("-","").all { it.isDigit() }

        paint.color = android.graphics.Color.WHITE
        if (isPhoneOnly) {
            // Google Messages style person silhouette
            // Head: centered at 38% from top, radius 22% of size
            paint.alpha = 230
            val headR = S * 0.22f
            val headY = S * 0.38f
            canvas.drawCircle(half, headY, headR, paint)
            // Body: oval lower half
            val bodyRect = android.graphics.RectF(
                S * 0.15f, S * 0.62f, S * 0.85f, S * 1.05f
            )
            canvas.drawOval(bodyRect, paint)
        } else {
            // Initial letter - large and centered
            val initial = conv.displayName
                .firstOrNull { it.isLetter() }?.uppercaseChar()?.toString()
                ?: conv.displayName.first().toString()
            paint.textSize = S * 0.45f
            paint.textAlign = android.graphics.Paint.Align.CENTER
            paint.typeface = android.graphics.Typeface.DEFAULT_BOLD
            val metrics = paint.fontMetrics
            val y = half - (metrics.ascent + metrics.descent) / 2f
            canvas.drawText(initial, half, y, paint)
        }
        holder.imgAvatar.setImageBitmap(bmp)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ConversationSummary>() {
            override fun areItemsTheSame(oldItem: ConversationSummary, newItem: ConversationSummary) =
                oldItem.threadId == newItem.threadId

            override fun areContentsTheSame(oldItem: ConversationSummary, newItem: ConversationSummary) =
                oldItem == newItem
        }
    }
}
