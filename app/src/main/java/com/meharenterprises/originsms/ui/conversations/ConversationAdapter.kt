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
        val imgLockBadge: ImageView = itemView.findViewById(R.id.imgLockBadge)
        val imgSelectedCheck: ImageView = itemView.findViewById(R.id.imgSelectedCheck)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtSnippet: TextView = itemView.findViewById(R.id.txtSnippet)
        val txtTime: TextView = itemView.findViewById(R.id.txtTime)
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
        holder.txtSnippet.text = if (item.isLocked) {
            context.getString(R.string.notif_locked_chat_content)
        } else {
            item.snippet
        }
        holder.txtTime.text = formatTimestamp(item.dateMillis)

        holder.imgLockBadge.visibility = if (item.isLocked) View.VISIBLE else View.GONE
        holder.imgSelectedCheck.visibility = if (isSelected) View.VISIBLE else View.GONE
        holder.itemView.isActivated = isSelected

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

        holder.itemView.setOnClickListener {
            if (isSelectionMode) {
                toggleSelection(item.threadId)
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

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ConversationSummary>() {
            override fun areItemsTheSame(oldItem: ConversationSummary, newItem: ConversationSummary) =
                oldItem.threadId == newItem.threadId

            override fun areContentsTheSame(oldItem: ConversationSummary, newItem: ConversationSummary) =
                oldItem == newItem
        }
    }
}
