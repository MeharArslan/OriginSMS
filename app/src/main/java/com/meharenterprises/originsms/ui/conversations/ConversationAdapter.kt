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
        val selBg: android.view.View? = itemView.findViewById(R.id.viewSelectedBg)
        val imgSelectedCheck: android.view.View? = itemView.findViewById(R.id.imgSelectedCheck)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtSnippet: TextView = itemView.findViewById(R.id.txtSnippet)
        val txtTimestamp: TextView? = itemView.findViewById(R.id.txtTimestamp)
        val txtUnreadBadge: TextView = itemView.findViewById(R.id.txtUnreadBadge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_conversation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.contains("selection")) {
            val sel = selectedThreadIds.contains(getItem(position).threadId)
            if (sel) {
                holder.selBg?.animate()?.alpha(1f)?.setDuration(120)?.start()
                holder.imgSelectedCheck?.visibility = android.view.View.VISIBLE
                holder.imgSelectedCheck?.animate()?.alpha(1f)?.setDuration(120)?.start()
                holder.imgAvatar?.animate()?.alpha(0f)?.setDuration(120)?.start()
            } else {
                holder.selBg?.animate()?.alpha(0f)?.setDuration(120)?.start()
                holder.imgSelectedCheck?.animate()?.alpha(0f)?.setDuration(120)
                    ?.withEndAction { holder.imgSelectedCheck?.visibility = android.view.View.GONE }?.start()
                holder.imgAvatar?.animate()?.alpha(1f)?.setDuration(120)?.start()
            }
            return
        }
        super.onBindViewHolder(holder, position, payloads)
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
        if (isSelected) {
            holder.selBg?.animate()?.alpha(1f)?.setDuration(150)?.start()
            holder.imgSelectedCheck?.visibility = android.view.View.VISIBLE
            holder.imgSelectedCheck?.animate()?.alpha(1f)?.setDuration(150)?.start()
            holder.imgAvatar?.animate()?.alpha(0f)?.setDuration(150)?.start()
        } else {
            holder.selBg?.animate()?.alpha(0f)?.setDuration(150)?.start()
            holder.imgSelectedCheck?.animate()?.alpha(0f)?.setDuration(150)
                ?.withEndAction { holder.imgSelectedCheck?.visibility = android.view.View.GONE }?.start()
            holder.imgAvatar?.animate()?.alpha(1f)?.setDuration(150)?.start()
        }

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
                        val raw = BitmapFactory.decodeStream(stream)
                        if (raw != null) {
                            val size = minOf(raw.width, raw.height)
                            val sx = (raw.width - size) / 2; val sy = (raw.height - size) / 2
                            val sq = android.graphics.Bitmap.createBitmap(raw, sx, sy, size, size)
                            val out = android.graphics.Bitmap.createBitmap(size, size, android.graphics.Bitmap.Config.ARGB_8888)
                            val cv = android.graphics.Canvas(out)
                            val pt = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG)
                            cv.drawCircle(size/2f, size/2f, size/2f, pt)
                            pt.xfermode = android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN)
                            cv.drawBitmap(sq, 0f, 0f, pt)
                            holder.imgAvatar.setImageBitmap(out)
                            holder.imgAvatar.scaleType = ImageView.ScaleType.FIT_XY
                            holder.imgAvatar.setPadding(0, 0, 0, 0)
                        }
                    }
                } catch (_: Exception) {
                    holder.imgAvatar.setImageResource(R.drawable.ic_person)
                }
            } else {
                // No contact photo — draw colored avatar with initial/silhouette
                // NO padding — fills full 56dp circle
                holder.imgAvatar.setPadding(0, 0, 0, 0)
                holder.imgAvatar.scaleType = android.widget.ImageView.ScaleType.FIT_XY
                holder.imgAvatar.setImageBitmap(makeColoredAvatar(item.displayName,
                    listOf(0xFFE53935,0xFFE91E63,0xFF9C27B0,0xFF3F51B5,0xFF1976D2,
                           0xFF0097A7,0xFF388E3C,0xFFF57C00,0xFF795548,0xFF455A64)
                        .map{it.toInt()}[item.displayName.hashCode().and(0x7FFFFFFF) % 10]))
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
        val colors = listOf(
            0xFFE53935.toInt(), 0xFFE91E63.toInt(), 0xFF9C27B0.toInt(),
            0xFF3F51B5.toInt(), 0xFF1976D2.toInt(), 0xFF0097A7.toInt(),
            0xFF388E3C.toInt(), 0xFFF57C00.toInt(), 0xFF795548.toInt(), 0xFF455A64.toInt()
        )
        val color = colors[conv.displayName.hashCode().and(0x7FFFFFFF) % colors.size]

        // Use actual contact photo if available
        if (!conv.contactPhotoUri.isNullOrBlank()) {
            try {
                holder.imgAvatar.setImageURI(android.net.Uri.parse(conv.contactPhotoUri))
                return
            } catch (_: Exception) {}
        }

        // Draw 320x320 bitmap — crisp on all densities
        val S = 320; val h = S / 2f
        val bmp = android.graphics.Bitmap.createBitmap(S, S, android.graphics.Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bmp)
        val paint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG)

        // 1. Colored background circle fills entire bitmap
        paint.color = color
        canvas.drawCircle(h, h, h, paint)

        // 2. White content (person or initial)
        paint.color = android.graphics.Color.WHITE
        paint.alpha = 255

        val isPhoneOnly = conv.displayName.replace("+","").replace(" ","")
            .replace("-","").replace("(","").replace(")","").all { it.isDigit() }

        if (isPhoneOnly) {
            // Google Messages person silhouette proportions
            val headRadius = S * 0.21f
            val headCY = S * 0.36f
            canvas.drawCircle(h, headCY, headRadius, paint)
            val bodyTop = headCY + headRadius + S * 0.04f
            canvas.drawOval(android.graphics.RectF(S*0.10f, bodyTop, S*0.90f, S*1.08f), paint)
        } else {
            val initial = conv.displayName.firstOrNull { it.isLetter() }
                ?.uppercaseChar()?.toString() ?: conv.displayName.take(1).uppercase()
            paint.textSize = S * 0.45f
            paint.textAlign = android.graphics.Paint.Align.CENTER
            paint.typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL)
            val fm = paint.fontMetrics
            canvas.drawText(initial, h, h - (fm.ascent + fm.descent) / 2f, paint)
        }

        holder.imgAvatar.setImageBitmap(bmp)
    }

    private fun makeColoredAvatar(name: String, color: Int): android.graphics.Bitmap {
        val S = 320; val h = S / 2f
        val bmp = android.graphics.Bitmap.createBitmap(S, S, android.graphics.Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bmp)
        val paint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG)
        paint.color = color; canvas.drawCircle(h, h, h, paint)
        paint.color = android.graphics.Color.WHITE; paint.alpha = 255
        val isPhone = name.replace("+","").replace(" ","").replace("-","").all { it.isDigit() }
        if (isPhone) {
            canvas.drawCircle(h, S*0.365f, S*0.21f, paint)
            canvas.drawOval(android.graphics.RectF(S*0.11f, S*0.60f, S*0.89f, S*1.09f), paint)
        } else {
            val initial = name.firstOrNull{it.isLetter()}?.uppercaseChar()?.toString() ?: name.take(1).uppercase()
            paint.textSize = S*0.44f; paint.textAlign = android.graphics.Paint.Align.CENTER
            paint.typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL)
            val fm = paint.fontMetrics
            canvas.drawText(initial, h, h-(fm.ascent+fm.descent)/2f, paint)
        }
        return bmp
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
