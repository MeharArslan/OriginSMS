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
    private val onTapFailedRetry: (Message) -> Unit = {},
    private val onTapScheduled: (Message) -> Unit = {}
) : ListAdapter<Message, RecyclerView.ViewHolder>(DIFF) {

    private val selectedIds = mutableSetOf<Long>()
    var isSelectionMode: Boolean = false
        private set

    inner class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtBody: TextView = itemView.findViewById(R.id.txtMessageBody)
        val txtStatus: TextView = itemView.findViewById(R.id.txtMessageStatus)
        val imgStarIndicator: android.widget.ImageView? = itemView.findViewById(R.id.imgStarIndicator)
        val imgAttachment: ShapeableImageView = itemView.findViewById(R.id.imgAttachment)
        val cardLinkPreview: View? = itemView.findViewById(R.id.cardLinkPreview)
        val imgLinkPreview: android.widget.ImageView? = itemView.findViewById(R.id.imgLinkPreview)
        val txtLinkTitle: TextView? = itemView.findViewById(R.id.txtLinkTitle)
        val txtLinkDesc: TextView? = itemView.findViewById(R.id.txtLinkDesc)
        val txtLinkUrl: TextView? = itemView.findViewById(R.id.txtLinkUrl)
    }

    inner class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtBody: TextView = itemView.findViewById(R.id.txtMessageBody)
        val txtStatus: TextView = itemView.findViewById(R.id.txtMessageStatus)
        val imgStarIndicator: android.widget.ImageView? = itemView.findViewById(R.id.imgStarIndicator)
        val imgAttachment: ShapeableImageView = itemView.findViewById(R.id.imgAttachment)
        val cardLinkPreview: View? = itemView.findViewById(R.id.cardLinkPreview)
        val imgLinkPreview: android.widget.ImageView? = itemView.findViewById(R.id.imgLinkPreview)
        val txtLinkTitle: TextView? = itemView.findViewById(R.id.txtLinkTitle)
        val txtLinkDesc: TextView? = itemView.findViewById(R.id.txtLinkDesc)
        val txtLinkUrl: TextView? = itemView.findViewById(R.id.txtLinkUrl)

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

    private fun showDateSeparatorIfNeeded(holder: RecyclerView.ViewHolder, position: Int) {
        // Find date separator view by tag (set in XML via android:tag)
        val txtDate = holder.itemView.findViewWithTag<android.widget.TextView?>("dateSeparator")
        if (txtDate != null) {
            val msg = getItem(position)
            val showDate = position == 0 || !isSameDay(msg.dateMillis, getItem(position - 1).dateMillis)
            if (showDate) {
                txtDate.text = formatDateHeader(msg.dateMillis)
                txtDate.visibility = android.view.View.VISIBLE
            } else {
                txtDate.visibility = android.view.View.GONE
            }
        }
    }

    private fun isSameDay(m1: Long, m2: Long): Boolean {
        val c1 = java.util.Calendar.getInstance().apply { timeInMillis = m1 }
        val c2 = java.util.Calendar.getInstance().apply { timeInMillis = m2 }
        return c1.get(java.util.Calendar.YEAR) == c2.get(java.util.Calendar.YEAR) &&
            c1.get(java.util.Calendar.DAY_OF_YEAR) == c2.get(java.util.Calendar.DAY_OF_YEAR)
    }

    // Track ALL messages with visible timestamps (tap to add, tap again to remove)
    private val expandedTimestampIds = mutableSetOf<Long>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        showDateSeparatorIfNeeded(holder, position)
        val message = getItem(position)
        val time = formatTime(message.dateMillis)
        val isSelected = selectedIds.contains(message.id)
        val isLast = position == itemCount - 1
        val isTimestampExpanded = expandedTimestampIds.contains(message.id)

        val firstImageOrVideoAttachment = message.attachments.firstOrNull {
            it.contentType.startsWith("image/") || it.contentType.startsWith("video/")
        }

        // Apply a clearly visible highlight instead of the subtle alpha-dim
        // approach which was hard to see in dark mode.
        // Clear full-row background — highlight goes on bubble only
        holder.itemView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
        holder.itemView.alpha = 1f
        // Selected: dim non-selected items so selected ones stand out
        if (isSelectionMode && !isSelected) holder.itemView.alpha = 0.5f

        when (holder) {
            is SentViewHolder -> {
                val theme = com.meharenterprises.originsms.ui.ChatThemeManager.getCurrentTheme(holder.itemView.context)
                val ctx = holder.itemView.context
                // Apply to bubble TextView only — not the entire row
                holder.itemView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                val baseBg = androidx.core.content.ContextCompat.getDrawable(ctx, theme.sentBubbleDrawable)?.mutate()
                if (isSelected) {
                    val sel = android.graphics.drawable.GradientDrawable().apply {
                        setColor(theme.outgoingBubble)
                        setStroke(4, theme.accentColor)
                        cornerRadius = theme.bubbleCornerRadius
                    }
                    holder.txtBody.background = sel
                } else {
                    holder.txtBody.background = baseBg
                }
                holder.txtBody.setTextColor(theme.outgoingTextColor)
                bindAttachment(holder.imgAttachment, firstImageOrVideoAttachment)
                bindLinkPreview(holder.cardLinkPreview, holder.imgLinkPreview,
                    holder.txtLinkTitle, holder.txtLinkDesc, holder.txtLinkUrl, message.body)
                holder.txtBody.textSize = 15f * textScaleFactor
                holder.txtBody.visibility = if (message.body.isBlank()) View.GONE else View.VISIBLE
                holder.txtBody.text = message.body
                if (message.isStarred) {
                    if (holder.imgStarIndicator?.visibility != View.VISIBLE) {
                        holder.imgStarIndicator?.visibility = View.VISIBLE
                        // Floating star bubbles animation
                        val act = holder.itemView.context
                        if (act is com.meharenterprises.originsms.ui.thread.ThreadActivity) {
                            act.showFloatingStarAnimation(holder.itemView)
                        }
                        holder.imgStarIndicator?.let { img ->
                            img.alpha = 0f; img.scaleX = 0.3f; img.scaleY = 0.3f
                            android.animation.AnimatorSet().apply {
                                playTogether(
                                    android.animation.ObjectAnimator.ofFloat(img,"alpha",0f,1f),
                                    android.animation.ObjectAnimator.ofFloat(img,"scaleX",0.3f,1.2f,1f),
                                    android.animation.ObjectAnimator.ofFloat(img,"scaleY",0.3f,1.2f,1f)
                                ); duration=350; start()
                            }
                        }
                    }
                } else { holder.imgStarIndicator?.visibility = View.GONE }
                // Note: We don't set autoLinkMask here to avoid intercepting long-press
                val sentStatusText = when (message.box) {
                    MessageBox.FAILED -> holder.itemView.context.getString(R.string.status_failed)
                    MessageBox.OUTBOX -> holder.itemView.context.getString(R.string.status_sending)
                    MessageBox.QUEUED -> {
                        if (message.id < 0) {
                            "⏰ " + java.text.SimpleDateFormat("MMM d, h:mm a", java.util.Locale.getDefault())
                                .format(java.util.Date(message.scheduledTimeOrDate()))
                        } else {
                            holder.itemView.context.getString(R.string.status_sending)
                        }
                    }
                    else -> "${holder.itemView.context.getString(R.string.status_sent)} · $time"
                }
                holder.txtStatus.text = sentStatusText
                // Show timestamp only on last msg OR when tapped
                // Last msg: always show EXCEPT when user tapped it to hide
                // Other msgs: show only when tapped
                // Last msg always visible; tapping it toggles (hide). Other msgs show when in expanded set
                // Google Messages: only last msg shows timestamp by default
                // Tapped messages show timestamp temporarily (handled by expandedTimestampIds)
                val isLastHidden = isLast && expandedTimestampIds.contains(message.id)
                val showStatus = when {
                    message.box == MessageBox.FAILED || message.box == MessageBox.OUTBOX || message.box == MessageBox.QUEUED -> true
                    isLastHidden -> false
                    isLast -> true
                    isTimestampExpanded -> true
                    else -> false
                }
                if (showStatus) {
                    holder.txtStatus.visibility = View.VISIBLE
                    holder.txtStatus.alpha = 1f
                } else {
                    holder.txtStatus.visibility = View.GONE
                    holder.txtStatus.alpha = 0f
                }
                bindClickHandlers(holder.itemView, message)
            }
            is ReceivedViewHolder -> {
                val theme = com.meharenterprises.originsms.ui.ChatThemeManager.getCurrentTheme(holder.itemView.context)
                val ctx = holder.itemView.context
                // Apply to bubble TextView only — not the entire row
                holder.itemView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                val baseBg = androidx.core.content.ContextCompat.getDrawable(ctx, theme.recvBubbleDrawable)?.mutate()
                if (isSelected) {
                    val sel = android.graphics.drawable.GradientDrawable().apply {
                        setColor(theme.incomingBubble)
                        setStroke(4, theme.accentColor)
                        cornerRadius = theme.bubbleCornerRadius
                    }
                    holder.txtBody.background = sel
                } else {
                    holder.txtBody.background = baseBg
                }
                holder.txtBody.setTextColor(theme.incomingTextColor)
                bindAttachment(holder.imgAttachment, firstImageOrVideoAttachment)
                bindLinkPreview(holder.cardLinkPreview, holder.imgLinkPreview,
                    holder.txtLinkTitle, holder.txtLinkDesc, holder.txtLinkUrl, message.body)
                holder.txtBody.textSize = 15f * textScaleFactor
                holder.txtBody.visibility = if (message.body.isBlank()) View.GONE else View.VISIBLE
                holder.txtBody.text = message.body
                if (message.isStarred) {
                    if (holder.imgStarIndicator?.visibility != View.VISIBLE) {
                        holder.imgStarIndicator?.visibility = View.VISIBLE
                        // Floating star bubbles animation
                        val act = holder.itemView.context
                        if (act is com.meharenterprises.originsms.ui.thread.ThreadActivity) {
                            act.showFloatingStarAnimation(holder.itemView)
                        }
                        holder.imgStarIndicator?.let { img ->
                            img.alpha = 0f; img.scaleX = 0.3f; img.scaleY = 0.3f
                            android.animation.AnimatorSet().apply {
                                playTogether(
                                    android.animation.ObjectAnimator.ofFloat(img,"alpha",0f,1f),
                                    android.animation.ObjectAnimator.ofFloat(img,"scaleX",0.3f,1.2f,1f),
                                    android.animation.ObjectAnimator.ofFloat(img,"scaleY",0.3f,1.2f,1f)
                                ); duration=350; start()
                            }
                        }
                    }
                } else { holder.imgStarIndicator?.visibility = View.GONE }
                // Note: We don't set autoLinkMask here to avoid intercepting long-press
                holder.txtStatus.text = time
                // Same Google Messages behavior for received
                val isLastHiddenRecv = isLast && expandedTimestampIds.contains(message.id)
                val showRecvStatus = when {
                    isLastHiddenRecv -> false
                    isLast -> true
                    isTimestampExpanded -> true
                    else -> false
                }
                holder.txtStatus.visibility = if (showRecvStatus) View.VISIBLE else View.GONE

                bindClickHandlers(holder.itemView, message)
            }
        }
    }

    private fun bindClickHandlers(itemView: View, message: Message) {
        // Enable long press on the item
        itemView.isLongClickable = true
        itemView.setOnClickListener {
            when {
                isSelectionMode -> {
                    if (message.id >= 0) toggleSelection(message.id)
                    onTapWhileSelecting(message)
                }
                message.id < 0 && message.box == MessageBox.QUEUED -> {
                    onTapScheduled(message)
                }
                message.box == MessageBox.FAILED -> onTapFailedRetry(message)
                else -> {
                    // Toggle timestamp — show with fade, auto-hide after 3s
                    val msgId = message.id
                    if (expandedTimestampIds.contains(msgId)) {
                        expandedTimestampIds.remove(msgId)
                        val pos = currentList.indexOfFirst { it.id == msgId }
                        if (pos >= 0) notifyItemChanged(pos)
                    } else {
                        expandedTimestampIds.add(msgId)
                        val pos = currentList.indexOfFirst { it.id == msgId }
                        if (pos >= 0) notifyItemChanged(pos)
                        // Auto-hide after 3 seconds
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            if (expandedTimestampIds.contains(msgId)) {
                                expandedTimestampIds.remove(msgId)
                                val p = currentList.indexOfFirst { it.id == msgId }
                                if (p >= 0) notifyItemChanged(p)
                            }
                        }, 3000L)
                    }
                }
            }
        }
        itemView.setOnLongClickListener {
            if (message.id < 0) return@setOnLongClickListener true
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

    private var textScaleFactor = 1f

    fun setTextScale(scale: Float) {
        textScaleFactor = scale
        notifyDataSetChanged()
    }
    fun getSelectedCount(): Int = selectedIds.size

    fun clearSelection() {
        selectedIds.clear()
        isSelectionMode = false
        notifyDataSetChanged()
    }

    // ──────────────────────────────────────────────
    // Link Preview — detect URL, fetch OG metadata
    // ──────────────────────────────────────────────

    data class LinkPreviewData(
        val title: String,
        val description: String,
        val imageUrl: String?,
        val url: String
    )

    private val previewCache = object : LinkedHashMap<String, LinkPreviewData?>(16, 0.75f, true) {
        override fun removeEldestEntry(e: MutableMap.MutableEntry<String, LinkPreviewData?>) = size > 50
    }

    private val URL_REGEX = Regex(
        """https?://[^\s\u200B-\u200D\uFEFF<>"{}|\\^`\[\]]+""",
        RegexOption.IGNORE_CASE
    )

    private val mainHandler = android.os.Handler(android.os.Looper.getMainLooper())
    private val bgExecutor = java.util.concurrent.Executors.newCachedThreadPool()

    private fun bindLinkPreview(
        card: View?,
        img: android.widget.ImageView?,
        txtTitle: TextView?,
        txtDesc: TextView?,
        txtUrl: TextView?,
        body: String
    ) {
        if (card == null) return
        val url = URL_REGEX.find(body)?.value
        if (url == null) { card.visibility = View.GONE; return }

        card.visibility = View.VISIBLE
        txtTitle?.text = ""
        txtDesc?.text = ""
        txtUrl?.text = url.removePrefix("https://").removePrefix("http://").substringBefore("/")
        img?.setImageDrawable(null)
        img?.visibility = View.GONE

        // Open link in browser on tap
        card.setOnClickListener {
            try {
                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW,
                    android.net.Uri.parse(url)).apply {
                    addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                it.context.startActivity(intent)
            } catch (_: Exception) {}
        }

        if (previewCache.containsKey(url)) {
            applyPreviewData(card, img, txtTitle, txtDesc, txtUrl, previewCache[url])
            return
        }

        bgExecutor.execute {
            val data = fetchOgData(url)
            previewCache[url] = data
            mainHandler.post { applyPreviewData(card, img, txtTitle, txtDesc, txtUrl, data) }
        }
    }

    private fun applyPreviewData(
        card: View?,
        img: android.widget.ImageView?,
        txtTitle: TextView?,
        txtDesc: TextView?,
        txtUrl: TextView?,
        data: LinkPreviewData?
    ) {
        if (data == null || card == null) return
        txtTitle?.text = data.title.ifBlank { data.url }
        txtDesc?.text = data.description
        txtDesc?.visibility = if (data.description.isBlank()) View.GONE else View.VISIBLE
        txtUrl?.text = data.url.removePrefix("https://").removePrefix("http://").substringBefore("/")

        if (!data.imageUrl.isNullOrBlank()) {
            img?.visibility = View.VISIBLE
            bgExecutor.execute {
                val bmp = try {
                    val conn = java.net.URL(data.imageUrl).openConnection() as java.net.HttpURLConnection
                    conn.connectTimeout = 5000; conn.readTimeout = 5000
                    android.graphics.BitmapFactory.decodeStream(conn.inputStream)
                } catch (_: Exception) { null }
                mainHandler.post { if (bmp != null) img?.setImageBitmap(bmp) else img?.visibility = View.GONE }
            }
        } else {
            img?.visibility = View.GONE
        }
    }

    private fun fetchOgData(url: String): LinkPreviewData? {
        return try {
            val conn = java.net.URL(url).openConnection() as java.net.HttpURLConnection
            conn.connectTimeout = 5000
            conn.readTimeout = 5000
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; OriginSMS)")
            val html = conn.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText().take(50000) }
            conn.disconnect()

            fun ogTag(prop: String): String {
                val patterns = listOf(
                    Regex("""<meta[^>]+property=["']og:$prop["'][^>]+content=["']([^"']+)["']""", RegexOption.IGNORE_CASE),
                    Regex("""<meta[^>]+content=["']([^"']+)["'][^>]+property=["']og:$prop["']""", RegexOption.IGNORE_CASE),
                    Regex("""<meta[^>]+name=["']$prop["'][^>]+content=["']([^"']+)["']""", RegexOption.IGNORE_CASE)
                )
                return patterns.firstNotNullOfOrNull { it.find(html)?.groupValues?.get(1) } ?: ""
            }

            val title = ogTag("title").ifBlank {
                Regex("<title>([^<]+)</title>", RegexOption.IGNORE_CASE).find(html)?.groupValues?.get(1) ?: ""
            }
            val desc = ogTag("description")
            val image = ogTag("image")

            LinkPreviewData(
                title = title.trim().take(100),
                description = desc.trim().take(200),
                imageUrl = if (image.startsWith("http")) image else null,
                url = url
            )
        } catch (_: Exception) { null }
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

    private fun formatDateHeader(millis: Long): String {
        val now = java.util.Calendar.getInstance()
        val then = java.util.Calendar.getInstance().apply { timeInMillis = millis }
        val dayFmt = java.text.SimpleDateFormat("EEEE", java.util.Locale.getDefault())
        val dateFmt = java.text.SimpleDateFormat("d-MMM-yyyy", java.util.Locale.getDefault())
        val timeFmt = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
        val dayName = when {
            now.get(java.util.Calendar.DAY_OF_YEAR) == then.get(java.util.Calendar.DAY_OF_YEAR) &&
                now.get(java.util.Calendar.YEAR) == then.get(java.util.Calendar.YEAR) -> "Today"
            now.get(java.util.Calendar.DAY_OF_YEAR) - then.get(java.util.Calendar.DAY_OF_YEAR) == 1 &&
                now.get(java.util.Calendar.YEAR) == then.get(java.util.Calendar.YEAR) -> "Yesterday"
            else -> dayFmt.format(then.time)
        }
        return "$dayName ${dateFmt.format(then.time)} ${timeFmt.format(then.time)}"
    }

        private fun formatTime(millis: Long): String {
        if (millis == 0L) return ""
        return SimpleDateFormat("h:mm a", Locale.getDefault()).format(millis)
    }

    companion object {
        private const val TYPE_DATE_HEADER = 0
        private const val TYPE_SENT = 1
        private const val TYPE_RECEIVED = 2

        private val DIFF = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem
        }
    }
}
