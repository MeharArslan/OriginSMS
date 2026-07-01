package com.meharenterprises.originsms.ui.thread

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.Message
import com.meharenterprises.originsms.core.MessageBox
import java.text.SimpleDateFormat
import java.util.Locale

class MessageAdapter(
    private val onLongPress: (Message) -> Unit,
    private val onTapWhileSelecting: (Message) -> Unit = {},
    private val onTapFailedRetry: (Message) -> Unit = {}
) : ListAdapter<Message, RecyclerView.ViewHolder>(DIFF) {

    private val selectedIds = mutableSetOf<Long>()
    var isSelectionMode: Boolean = false
        private set

    inner class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtBody: TextView = itemView.findViewById(R.id.txtMessageBody)
        val txtStatus: TextView = itemView.findViewById(R.id.txtMessageStatus)
        val imgAttachment: ShapeableImageView = itemView.findViewById(R.id.imgAttachment)
    }

    inner class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtBody: TextView = itemView.findViewById(R.id.txtMessageBody)
        val txtStatus: TextView = itemView.findViewById(R.id.txtMessageStatus)
        val imgAttachment: ShapeableImageView = itemView.findViewById(R.id.imgAttachment)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).box == MessageBox.SENT ||
            getItem(position).box == MessageBox.OUTBOX ||
            getItem(position).box == MessageBox.FAILED ||
            getItem(position).box == MessageBox.QUEUED
        ) TYPE_SENT else TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_SENT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_sent, parent, false)
            SentViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_received, parent, false)
            ReceivedViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        val time = formatTime(message.dateMillis)
        val isSelected = selectedIds.contains(message.id)
        val firstImageOrVideoAttachment = message.attachments.firstOrNull {
            it.contentType.startsWith("image/") || it.contentType.startsWith("video/")
        }

        // Apply a clearly visible highlight instead of the subtle alpha-dim
        // approach which was hard to see in dark mode.
        val selectedBg = if (isSelected)
            androidx.core.content.ContextCompat.getColor(holder.itemView.context, R.color.row_selected_bg)
        else
            android.graphics.Color.TRANSPARENT
        holder.itemView.setBackgroundColor(selectedBg)

        when (holder) {
            is SentViewHolder -> {
                bindAttachment(holder.imgAttachment, firstImageOrVideoAttachment)
                holder.txtBody.visibility = if (message.body.isBlank()) View.GONE else View.VISIBLE
                holder.txtBody.text = message.body
                holder.txtStatus.text = when (message.box) {
                    MessageBox.FAILED -> holder.itemView.context.getString(R.string.status_failed)
                    MessageBox.OUTBOX, MessageBox.QUEUED -> holder.itemView.context.getString(R.string.status_sending)
                    else -> "${holder.itemView.context.getString(R.string.status_sent)} · $time"
                }
                bindClickHandlers(holder.itemView, message)
            }
            is ReceivedViewHolder -> {
                bindAttachment(holder.imgAttachment, firstImageOrVideoAttachment)
                holder.txtBody.visibility = if (message.body.isBlank()) View.GONE else View.VISIBLE
                holder.txtBody.text = message.body
                holder.txtStatus.text = time
                bindClickHandlers(holder.itemView, message)
            }
        }
    }

    private fun bindClickHandlers(itemView: View, message: Message) {
        itemView.setOnClickListener {
            when {
                isSelectionMode -> {
                    toggleSelection(message.id)
                    onTapWhileSelecting(message)
                }
                message.box == MessageBox.FAILED -> onTapFailedRetry(message)
            }
        }
        itemView.setOnLongClickListener {
            if (!isSelectionMode) {
                isSelectionMode = true
                selectedIds.add(message.id)
                notifyDataSetChanged()
            }
            onLongPress(message)
            true
        }
    }

    private fun toggleSelection(messageId: Long) {
        if (selectedIds.contains(messageId)) selectedIds.remove(messageId) else selectedIds.add(messageId)
        if (selectedIds.isEmpty()) isSelectionMode = false
        notifyDataSetChanged()
    }

    fun getSelectedIds(): Set<Long> = selectedIds.toSet()
    fun getSelectedCount(): Int = selectedIds.size

    fun clearSelection() {
        selectedIds.clear()
        isSelectionMode = false
        notifyDataSetChanged()
    }

    private fun bindAttachment(imageView: ShapeableImageView, attachment: com.meharenterprises.originsms.core.Attachment?) {
        if (attachment == null) {
            imageView.visibility = View.GONE
            return
        }
        imageView.visibility = View.VISIBLE
        imageView.load(android.net.Uri.parse(attachment.uri)) {
            crossfade(true)
            placeholder(R.drawable.ic_attach)
            error(R.drawable.ic_attach)
        }
    }

    private fun formatTime(millis: Long): String {
        if (millis == 0L) return ""
        return SimpleDateFormat("h:mm a", Locale.getDefault()).format(millis)
    }

    companion object {
        private const val TYPE_SENT = 1
        private const val TYPE_RECEIVED = 2

        private val DIFF = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem
        }
    }
}
