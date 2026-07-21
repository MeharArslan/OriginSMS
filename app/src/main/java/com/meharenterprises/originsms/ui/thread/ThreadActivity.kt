package com.meharenterprises.originsms.ui.thread

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import com.meharenterprises.originsms.ui.ChatThemeManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.telephony.SubscriptionManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.emoji2.emojipicker.EmojiPickerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.Message
import com.meharenterprises.originsms.data.db.OriginDatabase
import kotlinx.coroutines.launch
import java.io.File

class ThreadActivity : AppCompatActivity() {

    private lateinit var viewModel: ThreadViewModel
    private lateinit var adapter: MessageAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var editMessage: EditText
    private lateinit var emojiPicker: EmojiPickerView
    private lateinit var attachmentScroll: HorizontalScrollView
    private lateinit var attachmentContainer: LinearLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var messageSelectionBar: View
    private lateinit var txtMessageSelectionCount: TextView

    private var threadId: Long = -1L
    private var address: String = ""
    private var displayName: String = ""
    private var openedFromTrash: Boolean = false
    private var emojiPickerVisible = false
    private var pendingCameraUri: Uri? = null

    private val attachmentPickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(MAX_ATTACHMENTS)
    ) { uris ->
        uris.forEach { uri ->
            try {
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } catch (_: SecurityException) { }
            viewModel.addPendingAttachment(uri)
        }
    }

    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        uris.forEach { uri ->
            try {
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } catch (_: SecurityException) { }
            viewModel.addPendingAttachment(uri)
        }
    }

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) pendingCameraUri?.let { viewModel.addPendingAttachment(it) }
    }

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> if (granted) launchCamera() }

    private var unreadBelowFold = 0
    private var fabCard: androidx.cardview.widget.CardView? = null
    private var fabTextView: android.widget.TextView? = null
    private var fabMsgCount = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)

        threadId = intent.getLongExtra(EXTRA_THREAD_ID, -1L)

        address = intent.getStringExtra(EXTRA_ADDRESS).orEmpty()
        displayName = intent.getStringExtra(EXTRA_DISPLAY_NAME).orEmpty().ifBlank { address }
        openedFromTrash = intent.getBooleanExtra(EXTRA_FROM_TRASH, false)

        // Auto-restore deleted thread ONLY when user opens it via new chat (not from Trash view)
        if (threadId != -1L && !openedFromTrash) {
            lifecycleScope.launch {
                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                    val dao = OriginDatabase.getInstance(applicationContext).threadLockDao()
                    val state = dao.getForThread(threadId)
                    if (state?.deletedAtMillis != null && state.deletedAtMillis > 0L) {
                        dao.upsert(state.copy(deletedAtMillis = 0L))
                    }
                }
            }
        }

        viewModel = ViewModelProvider(this)[ThreadViewModel::class.java]

        setupToolbar()
        setupMessageSelectionBar()
        setupRecyclerView()
        setupComposeBar()
        setupEmojiPicker()
        setupSimIndicator()

        // Hide compose bar if opened from Trash — read-only view
        if (openedFromTrash) {
            findViewById<android.view.View>(R.id.composeBarRoot)?.visibility = android.view.View.GONE
            findViewById<android.view.View>(R.id.scheduledMsgBanner)?.visibility = android.view.View.GONE
            val trashBanner = findViewById<android.widget.TextView?>(R.id.txtTrashViewBanner)
            if (trashBanner != null) {
                trashBanner.visibility = android.view.View.VISIBLE
                trashBanner.text = "📂 Trash — Go back to restore or permanently delete"
            }
        }

        viewModel.bind(threadId, address)
        viewModel.messages.observe(this) { list ->
            val newArrived = fabMsgCount >= 0 && list.size > fabMsgCount
            fabMsgCount = list.size
            mergeAndSubmit(list, viewModel.scheduledEntries.value ?: emptyList())
            if (newArrived) {
                val lm2 = recycler.layoutManager as? LinearLayoutManager
                val atBot = list.isEmpty() || (lm2?.findLastVisibleItemPosition() ?: -1) >= list.size - 3
                if (!atBot) { unreadBelowFold = minOf(unreadBelowFold + 1, 99); showUnreadFab(unreadBelowFold) }
            }
        }
        viewModel.pendingAttachments.observe(this) { uris -> renderAttachmentPreviews(uris) }

        viewModel.scheduledEntries.observe(this) { entries ->
            mergeAndSubmit(viewModel.messages.value ?: emptyList(), entries)
        }

        restoreDraft()
        intent.getStringExtra(EXTRA_PREFILL_BODY)?.let { prefill ->
            if (editMessage.text?.toString().isNullOrBlank()) {
                editMessage.setText(prefill)
                editMessage.setSelection(prefill.length)
            }
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { finish() }

        // Set contact name in custom title view (right of avatar)
        val txtTitle = toolbar.findViewById<android.widget.TextView>(R.id.txtThreadTitle)
        txtTitle?.text = displayName
        txtTitle?.setOnClickListener { viewContact() }

        // Apply theme
        val theme = ChatThemeManager.getCurrentTheme(this)
        toolbar.setBackgroundColor(theme.appBarBg)
        window.statusBarColor = theme.statusBarColor
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerMessages)
        recyclerView.background = androidx.core.content.ContextCompat.getDrawable(this, theme.chatBackgroundDrawable)
        findViewById<android.view.View>(android.R.id.content).setBackgroundColor(theme.appBackground)

        // Load contact avatar - click opens contact view
        val imgAvatar = toolbar.findViewById<android.widget.ImageView>(R.id.imgThreadAvatar)
        imgAvatar?.setOnClickListener { viewContact() }

        lifecycleScope.launch {
            val contactInfo = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                com.meharenterprises.originsms.core.ContactsHelper(this@ThreadActivity).resolve(address)
            }
            if (contactInfo.photoUri != null) {
                try {
                    contentResolver.openInputStream(android.net.Uri.parse(contactInfo.photoUri))?.use { stream ->
                        val bitmap = android.graphics.BitmapFactory.decodeStream(stream)
                        if (bitmap != null && imgAvatar != null) {
                            imgAvatar.setImageBitmap(bitmap)
                            imgAvatar.scaleType = android.widget.ImageView.ScaleType.CENTER_CROP
                            imgAvatar.setPadding(0, 0, 0, 0)
                            imgAvatar.clipToOutline = true
                        }
                    }
                } catch (_: Exception) { }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_thread, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        lifecycleScope.launch {
            val lockState = OriginDatabase.getInstance(this@ThreadActivity).threadLockDao().getForThread(threadId)
            val muted = lockState?.isMuted == true &&
                (lockState.muteUntilMillis == -1L || lockState.muteUntilMillis > System.currentTimeMillis())
            menu.findItem(R.id.action_mute_chat)?.title =
                getString(if (muted) R.string.action_unmute_chat else R.string.action_mute_chat)
            menu.findItem(R.id.action_hide_chat)?.title =
                if (lockState?.isHidden == true) getString(R.string.menu_unhide_chat)
                else getString(R.string.menu_hide_chat)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_call -> {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Uri.encode(address)}"))
                startActivity(dialIntent)
                true
            }
            R.id.action_change_theme -> {
                showInChatThemePicker()
                true
            }
            R.id.action_mute_chat -> {
                toggleMute()
                true
            }
            R.id.action_hide_chat -> {
                confirmHideChat()
                true
            }
            R.id.action_view_contact -> {
                viewContact()
                true
            }
            R.id.action_archive_chat -> {
                lifecycleScope.launch {
                    val dao = OriginDatabase.getInstance(this@ThreadActivity).threadLockDao()
                    val existing = dao.getForThread(threadId)
                    dao.upsert(
                        (existing ?: com.meharenterprises.originsms.data.db.ThreadLockEntity(threadId = threadId))
                            .copy(isArchived = true)
                    )
                    android.widget.Toast.makeText(this@ThreadActivity, getString(R.string.menu_archive_chat), android.widget.Toast.LENGTH_SHORT).show()
                    finish()
                }
                true
            }
            R.id.action_block_number -> {
                AlertDialog.Builder(this)
                    .setTitle(R.string.action_block_number)
                    .setMessage(address)
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        lifecycleScope.launch {
                            OriginDatabase.getInstance(this@ThreadActivity)
                                .blockedNumberDao()
                                .block(com.meharenterprises.originsms.data.db.BlockedNumberEntity(
                                    normalizedNumber = address.filter { it.isDigit() || it == '+' },
                                    displayNumber = address,
                                    blockedAtMillis = System.currentTimeMillis()
                                ))
                            android.widget.Toast.makeText(this@ThreadActivity, getString(R.string.action_block_number), android.widget.Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
                true
            }
            R.id.action_delete_thread -> { confirmDeleteThread(); true }
            R.id.action_starred_messages -> { startActivity(android.content.Intent(this, com.meharenterprises.originsms.ui.StarredMessagesActivity::class.java).apply {
                        putExtra("FILTER_THREAD_ID", threadId) }); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleMute() {
        lifecycleScope.launch {
            val dao = OriginDatabase.getInstance(this@ThreadActivity).threadLockDao()
            val current = dao.getForThread(threadId)
            val currentlyMuted = current?.isMuted == true &&
                (current.muteUntilMillis == -1L || current.muteUntilMillis > System.currentTimeMillis())

            if (currentlyMuted) {
                dao.setMutedUntil(threadId, false, 0L)
                android.widget.Toast.makeText(
                    this@ThreadActivity, getString(R.string.action_unmute_chat), android.widget.Toast.LENGTH_SHORT
                ).show()
                invalidateOptionsMenu()
                return@launch
            }

            val options = arrayOf(
                getString(R.string.mute_duration_always),
                getString(R.string.mute_duration_24h),
                getString(R.string.mute_duration_8h)
            )
            AlertDialog.Builder(this@ThreadActivity)
                .setTitle(R.string.action_mute_chat)
                .setItems(options) { _, index ->
                    val muteUntil = when (index) {
                        0 -> -1L
                        1 -> System.currentTimeMillis() + 24 * 60 * 60 * 1000L
                        else -> System.currentTimeMillis() + 8 * 60 * 60 * 1000L
                    }
                    lifecycleScope.launch {
                        dao.setMutedUntil(threadId, true, muteUntil)
                        android.widget.Toast.makeText(
                            this@ThreadActivity, getString(R.string.action_mute_chat), android.widget.Toast.LENGTH_SHORT
                        ).show()
                        invalidateOptionsMenu()
                    }
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
    }

    private fun confirmHideChat() {
        lifecycleScope.launch {
            val dao = OriginDatabase.getInstance(this@ThreadActivity).threadLockDao()
            val existing = dao.getForThread(threadId)
            val isCurrentlyHidden = existing?.isHidden == true

            if (isCurrentlyHidden) {
                // Unhide — manual only, clears daily hide timer too
                AlertDialog.Builder(this@ThreadActivity)
                    .setTitle(R.string.menu_unhide_chat)
                    .setMessage(displayName)
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        lifecycleScope.launch {
                            dao.upsert(
                                existing!!.copy(
                                    isHidden = false,
                                    isLocked = false,
                                    dailyHideTimeMinutes = -1  // also clear daily timer so it doesn't re-hide
                                )
                            )
                            finish()
                        }
                    }
                    .setNegativeButton("Keep timer") { _, _ ->
                        // Unhide without clearing daily timer — will re-hide tomorrow at same time
                        lifecycleScope.launch {
                            dao.upsert(existing!!.copy(isHidden = false))
                            finish()
                        }
                    }
                    .show()
            } else {
                AlertDialog.Builder(this@ThreadActivity)
                    .setTitle(R.string.menu_hide_chat)
                    .setMessage(displayName)
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        lifecycleScope.launch {
                            dao.upsert(
                                (existing ?: com.meharenterprises.originsms.data.db.ThreadLockEntity(threadId = threadId))
                                    .copy(isHidden = true, isLocked = true)
                            )
                            finish()
                        }
                    }
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
            }
        }
    }

    private fun viewContact() {
        val uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address)
        )
        val projection = arrayOf(ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.LOOKUP_KEY)
        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val idIdx = cursor.getColumnIndex(ContactsContract.PhoneLookup._ID)
                val keyIdx = cursor.getColumnIndex(ContactsContract.PhoneLookup.LOOKUP_KEY)
                if (idIdx >= 0 && keyIdx >= 0) {
                    val contactId = cursor.getLong(idIdx)
                    val lookupKey = cursor.getString(keyIdx)
                    val contactUri = ContactsContract.Contacts.getLookupUri(contactId, lookupKey)
                    startActivity(Intent(Intent.ACTION_VIEW, contactUri))
                    return
                }
            }
        }
        // Not in contacts — offer to add as a new contact instead.
        val addIntent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
            type = ContactsContract.RawContacts.CONTENT_TYPE
            putExtra(ContactsContract.Intents.Insert.PHONE, address)
        }
        startActivity(addIntent)
    }

    private fun confirmDeleteThread() {
        AlertDialog.Builder(this)
            .setTitle(R.string.menu_delete_chat)
            .setMessage(displayName)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                lifecycleScope.launch {
                    com.meharenterprises.originsms.core.SmsRepository(applicationContext).deleteThread(threadId)
                    finish()
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    // ------------------------------------------------------------------
    // Message multi-select bar
    // ------------------------------------------------------------------

    private fun setupMessageSelectionBar() {
        messageSelectionBar = findViewById(R.id.messageSelectionBar)
        txtMessageSelectionCount = findViewById(R.id.txtMessageSelectionCount)

        findViewById<View>(R.id.btnCloseMessageSelection).setOnClickListener {
            adapter.clearSelection()
            updateMessageSelectionBar()
        }
        findViewById<View>(R.id.btnMessageCopy).setOnClickListener {
            val selected = currentSelectedMessages()
            val combined = selected.joinToString("\n") { it.body }
            copyToClipboard(combined)
            adapter.clearSelection()
            updateMessageSelectionBar()
        }
                findViewById<View>(R.id.btnMessageStar).setOnClickListener {
            val selected = currentSelectedMessages()
            if (selected.isNotEmpty()) {
                selected.filter { !it.isStarred }.forEach { msg ->
                    val pos = adapter.currentList.indexOfFirst { it.id == msg.id }
                    val anchor = if (pos >= 0) recycler.findViewHolderForAdapterPosition(pos)?.itemView else null
                    (anchor ?: recycler).post { showFloatingStarAnimation(anchor ?: recycler) }
                }
                selected.forEach { msg -> viewModel.toggleStar(msg) }
            }
            adapter.clearSelection()
            updateMessageSelectionBar()
        }
        findViewById<View>(R.id.btnMessageDelete).setOnClickListener {
            val ids = adapter.getSelectedIds()
            val count = ids.size
            AlertDialog.Builder(this)
                .setTitle("Delete ${if (count == 1) "this message" else "$count messages"}?")
                .setMessage("This action cannot be undone.")
                .setPositiveButton("Delete") { _, _ ->
                    viewModel.deleteMessages(ids)
                    adapter.clearSelection()
                    updateMessageSelectionBar()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
        findViewById<View>(R.id.btnMessageMore).setOnClickListener {
            val selected = currentSelectedMessages()
            if (selected.isEmpty()) return@setOnClickListener
            val options = arrayOf(
                getString(R.string.action_forward),
                getString(R.string.action_share),
                getString(R.string.action_view_details)
            )
            AlertDialog.Builder(this)
                .setItems(options) { _, index ->
                    when (index) {
                        0 -> { if (selected.size == 1) forwardMessage(selected.first()) }
                        1 -> shareMessages(selected)
                        2 -> viewMessageDetails(selected.first())
                    }
                }
                .show()
        }
    }

    private fun currentSelectedMessages(): List<Message> {
        val ids = adapter.getSelectedIds()
        return (viewModel.messages.value ?: emptyList()).filter { ids.contains(it.id) }
    }

    private fun updateMessageSelectionBar() {
        val count = adapter.getSelectedCount()
        if (count > 0) {
            toolbar.visibility = View.GONE
            messageSelectionBar.visibility = View.VISIBLE
            txtMessageSelectionCount.text = count.toString()
        } else {
            messageSelectionBar.visibility = View.GONE
            toolbar.visibility = View.VISIBLE
        }
    }

    private fun showInChatThemePicker() {
        val themes = ChatThemeManager.themes
        val currentIdx = ChatThemeManager.getCurrentIndex(this)
        val dp = resources.displayMetrics.density.toInt()

        val grid = android.widget.GridLayout(this).apply {
            columnCount = 2
            setPadding(8 * dp, 8 * dp, 8 * dp, 8 * dp)
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Chat Theme")
            .setView(android.widget.ScrollView(this).apply { addView(grid) })
            .setNegativeButton(android.R.string.cancel, null)
            .create()

        themes.forEachIndexed { i, theme ->
            val card = LayoutInflater.from(this).inflate(R.layout.item_theme_card, null, false)
            val params = android.widget.GridLayout.LayoutParams().apply {
                width = 0
                columnSpec = android.widget.GridLayout.spec(i % 2, 1f)
                setMargins(6 * dp, 6 * dp, 6 * dp, 6 * dp)
            }
            card.layoutParams = params

            // Apply theme colors to card
            card.findViewById<android.view.View>(R.id.cardBackground).setBackgroundColor(theme.appBackground)
            card.findViewById<android.view.View>(R.id.miniToolbar).setBackgroundColor(theme.appBarBg)
            card.findViewById<android.view.View>(R.id.miniInputBar).setBackgroundColor(theme.appBarBg)
            val cr = theme.bubbleCornerRadius
            card.findViewById<android.widget.TextView>(R.id.bubbleSent).apply {
                background = android.graphics.drawable.GradientDrawable().apply { setColor(theme.outgoingBubble); cornerRadius = cr }
                setTextColor(theme.outgoingTextColor)
            }
            card.findViewById<android.widget.TextView>(R.id.bubbleReceived).apply {
                background = android.graphics.drawable.GradientDrawable().apply { setColor(theme.incomingBubble); cornerRadius = cr }
                setTextColor(theme.incomingTextColor)
            }
            card.findViewById<android.widget.TextView>(R.id.bubbleReceived2).apply {
                background = android.graphics.drawable.GradientDrawable().apply { setColor(theme.incomingBubble); cornerRadius = cr }
                setTextColor(theme.incomingTextColor)
            }
            card.findViewById<android.view.View>(R.id.miniSendBtn).background =
                android.graphics.drawable.GradientDrawable().apply {
                    shape = android.graphics.drawable.GradientDrawable.OVAL; setColor(theme.accentColor)
                }
            card.findViewById<android.widget.TextView>(R.id.txtThemeCardName).apply {
                text = theme.name
                setTextColor(if (i == currentIdx) theme.accentColor else android.graphics.Color.GRAY)
                if (i == currentIdx) setTypeface(null, android.graphics.Typeface.BOLD)
            }
            card.findViewById<android.view.View>(R.id.txtThemeSelected).visibility =
                if (i == currentIdx) android.view.View.VISIBLE else android.view.View.GONE
            if (i == currentIdx) {
                card.foreground = android.graphics.drawable.GradientDrawable().apply {
                    setColor(android.graphics.Color.TRANSPARENT)
                    setStroke((3 * resources.displayMetrics.density).toInt(), theme.accentColor)
                    cornerRadius = 14 * resources.displayMetrics.density
                }
            }

            card.setOnClickListener {
                ChatThemeManager.applyTheme(this, i)
                dialog.dismiss()
                recreate()
            }
            grid.addView(card)
        }
        dialog.show()
        // Scroll to current theme after layout
        grid.post {
            val cardHeight = (256 * resources.displayMetrics.density).toInt()
            val row = currentIdx / 2
            val target = row * cardHeight
            fun android.view.View.findScrollView(): android.widget.ScrollView? {
                if (this is android.widget.ScrollView) return this
                if (this is android.view.ViewGroup) {
                    for (i in 0 until childCount) {
                        getChildAt(i).findScrollView()?.let { return it }
                    }
                }
                return null
            }
            dialog.window?.decorView?.findScrollView()?.scrollTo(0, target)
        }
    }

        private fun Int.dp(density: Float) = (this * density).toInt()

    private fun isDarkColor(color: Int): Boolean {
        val r = (color shr 16 and 0xFF) / 255.0
        val g = (color shr 8 and 0xFF) / 255.0
        val b = (color and 0xFF) / 255.0
        return (0.2126 * r + 0.7152 * g + 0.0722 * b) < 0.5
    }

    private fun blendColor(base: Int, overlay: Int, alpha: Float): Int {
        val a = alpha.coerceIn(0f, 1f)
        val r = ((base shr 16 and 0xFF) * (1 - a) + (overlay shr 16 and 0xFF) * a).toInt()
        val g = ((base shr 8 and 0xFF) * (1 - a) + (overlay shr 8 and 0xFF) * a).toInt()
        val b = ((base and 0xFF) * (1 - a) + (overlay and 0xFF) * a).toInt()
        return (0xFF shl 24) or (r shl 16) or (g shl 8) or b
    }

    private fun shareMessages(messages: List<Message>) {
        val text = messages.joinToString("\n") { it.body }
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.action_share)))
    }

    private fun viewMessageDetails(message: Message) {
        val time = java.text.SimpleDateFormat("EEE, d MMM yyyy, h:mm a", java.util.Locale.getDefault())
            .format(java.util.Date(message.dateMillis))
        AlertDialog.Builder(this)
            .setTitle(R.string.action_view_details)
            .setMessage("From: $address\nTo: (me)\nDate: $time\nType: SMS")
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    private fun forwardMessage(message: Message) {
        val intent = Intent(this, com.meharenterprises.originsms.ui.compose.ComposeActivity::class.java).apply {
            putExtra(com.meharenterprises.originsms.ui.compose.ComposeActivity.EXTRA_PREFILL_BODY, message.body)
        }
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        recycler = findViewById(R.id.recyclerMessages)
        adapter = MessageAdapter(
            onLongPress = { updateMessageSelectionBar() },
            onTapWhileSelecting = { updateMessageSelectionBar() },
            onTapFailedRetry = { message -> confirmRetry(message) },
            onTapScheduled = { message -> showScheduledOptions(message) }
        )
        val lm = LinearLayoutManager(this).apply { stackFromEnd = true }
        recycler.layoutManager = lm
        recycler.adapter = adapter

        // Animations toggle
        if (!ChatThemeManager.isAnimationsEnabled(this)) {
            recycler.itemAnimator = null
        }

        // Google Messages scroll FAB
        fabCard = findViewById(R.id.fabScrollDown)
        fabTextView = fabCard?.findViewById(R.id.fabText)
        setupScrollFab()

        // Pinch to zoom toggle
        if (ChatThemeManager.isPinchZoomEnabled(this)) {
            var scaleFactor = 1f
            val scaleDetector = android.view.ScaleGestureDetector(this,
                object : android.view.ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    override fun onScale(detector: android.view.ScaleGestureDetector): Boolean {
                        scaleFactor *= detector.scaleFactor
                        scaleFactor = scaleFactor.coerceIn(0.7f, 1.8f)
                        adapter.setTextScale(scaleFactor)
                        return true
                    }
                })
            recycler.setOnTouchListener { v, event ->
                scaleDetector.onTouchEvent(event)
                if (scaleDetector.isInProgress) true else { v.performClick(); false }
            }
        }
    }

    private fun confirmRetry(message: Message) {
        AlertDialog.Builder(this)
            .setMessage(R.string.status_failed)
            .setPositiveButton(android.R.string.ok) { _, _ -> viewModel.retryMessage(message) }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun setupComposeBar() {
        editMessage = findViewById(R.id.editMessage)
        attachmentScroll = findViewById(R.id.attachmentPreviewScroll)
        attachmentContainer = findViewById(R.id.attachmentPreviewContainer)

        // Apply theme to input area
        val theme = ChatThemeManager.getCurrentTheme(this)
        val composeBar = findViewById<android.view.View>(R.id.composeBarRoot)
        composeBar?.setBackgroundColor(theme.appBarBg)
        editMessage.setTextColor(theme.textPrimary)
        editMessage.setHintTextColor(theme.textSecondary)
        // Input field background — always visible against compose bar bg
        val inputFieldColor = if (isDarkColor(theme.appBarBg)) {
            // Dark toolbar: lighter input
            blendColor(theme.appBarBg, 0xFFFFFFFF.toInt(), 0.15f)
        } else {
            // Light toolbar: slightly darker input
            blendColor(theme.appBarBg, 0xFF000000.toInt(), 0.08f)
        }
        val inputBg = android.graphics.drawable.GradientDrawable().apply {
            setColor(inputFieldColor)
            cornerRadius = 24f * resources.displayMetrics.density
        }
        editMessage.background = inputBg

        val btnSend = findViewById<ImageButton>(R.id.btnSend)
        btnSend.setOnClickListener {
            val text = editMessage.text?.toString().orEmpty()
            val hasAttachments = !viewModel.pendingAttachments.value.isNullOrEmpty()
            if (text.isNotBlank() || hasAttachments) {
                viewModel.sendMessage(text)
                editMessage.setText("")
                hideEmojiPicker()
                scrollToLatest()
            }
        }
        btnSend.setOnLongClickListener {
            val text = editMessage.text?.toString().orEmpty()
            if (text.isNotBlank()) showScheduleSendDialog(text)
            true
        }

        findViewById<ImageButton>(R.id.btnAttach).setOnClickListener { showAttachmentSheet() }
    }

    // Pending scheduled messages: text -> (scheduledAtMillis, handler)
    private val scheduledMessages = mutableMapOf<String, Pair<Long, android.os.Handler>>()

    private fun showScheduleSendDialog(messageText: String) {
        val sheet = com.google.android.material.bottomsheet.BottomSheetDialog(this)
        val v = layoutInflater.inflate(R.layout.sheet_schedule_send, null)
        sheet.setContentView(v)

        val timeFmt = java.text.SimpleDateFormat("EEE, MMM d · h:mm a", java.util.Locale.getDefault())

        fun tomorrowAt(h: Int, m: Int): Long {
            val c = java.util.Calendar.getInstance()
            c.add(java.util.Calendar.DAY_OF_YEAR, 1)
            c.set(java.util.Calendar.HOUR_OF_DAY, h)
            c.set(java.util.Calendar.MINUTE, m)
            c.set(java.util.Calendar.SECOND, 0)
            return c.timeInMillis
        }

        val t8  = tomorrowAt(8, 0)
        val t13 = tomorrowAt(13, 0)
        val t18 = tomorrowAt(18, 0)

        v.findViewById<android.widget.TextView>(R.id.txt8amTime).text  = timeFmt.format(t8)
        v.findViewById<android.widget.TextView>(R.id.txt1pmTime).text  = timeFmt.format(t13)
        v.findViewById<android.widget.TextView>(R.id.txt6pmTime).text  = timeFmt.format(t18)

        v.findViewById<android.view.View>(R.id.opt8am).setOnClickListener  { sheet.dismiss(); scheduleMessage(messageText, t8) }
        v.findViewById<android.view.View>(R.id.opt1pm).setOnClickListener  { sheet.dismiss(); scheduleMessage(messageText, t13) }
        v.findViewById<android.view.View>(R.id.opt6pm).setOnClickListener  { sheet.dismiss(); scheduleMessage(messageText, t18) }
        v.findViewById<android.view.View>(R.id.optCustom).setOnClickListener {
            sheet.dismiss(); showDateTimePicker(messageText)
        }
        sheet.show()
    }

    private fun showDateTimePicker(messageText: String) {
        val cal = java.util.Calendar.getInstance()
        android.app.DatePickerDialog(this, { _, year, month, day ->
            android.app.TimePickerDialog(this, { _, hour, minute ->
                val scheduled = java.util.Calendar.getInstance().apply {
                    set(year, month, day, hour, minute, 0)
                }
                scheduleMessage(messageText, scheduled.timeInMillis)
            }, cal.get(java.util.Calendar.HOUR_OF_DAY), cal.get(java.util.Calendar.MINUTE), false).show()
        }, cal.get(java.util.Calendar.YEAR), cal.get(java.util.Calendar.MONTH),
           cal.get(java.util.Calendar.DAY_OF_MONTH)).show()
    }

    private fun scheduleMessage(text: String, scheduledAtMillis: Long) {
        val delay = scheduledAtMillis - System.currentTimeMillis()
        if (delay <= 0) {
            android.widget.Toast.makeText(this, "Please choose a future time", android.widget.Toast.LENGTH_SHORT).show()
            return
        }

        editMessage.setText("")
        val timeFmt = java.text.SimpleDateFormat("MMM d, h:mm a", java.util.Locale.getDefault())
        val timeStr = timeFmt.format(java.util.Date(scheduledAtMillis))
        val pendingKey = "sched_${System.currentTimeMillis()}"
        val handler = android.os.Handler(android.os.Looper.getMainLooper())
        scheduledMessages[pendingKey] = Pair(scheduledAtMillis, handler)

        viewModel.sendScheduledPlaceholder(text, scheduledAtMillis, pendingKey)

        handler.postDelayed({
            scheduledMessages.remove(pendingKey)
            viewModel.deleteScheduledPlaceholder(pendingKey)
            viewModel.sendMessage(text)
        }, delay)

        // Brief toast only — no persistent snackbar in input area
        android.widget.Toast.makeText(
            this,
            "⏰ Message scheduled for $timeStr\nTap the message bubble to edit or cancel",
            android.widget.Toast.LENGTH_LONG
        ).show()
    }

    private fun showScheduledOptions(fakeMessage: com.meharenterprises.originsms.core.Message) {
        // Body is now clean text (no prefix)
        val realText = fakeMessage.body

        // Find the matching scheduled entry by scheduled time
        val entry = viewModel.scheduledEntries.value?.firstOrNull {
            it.scheduledAtMillis == fakeMessage.dateMillis
        } ?: return

        val timeFmt = java.text.SimpleDateFormat("MMM d, h:mm a", java.util.Locale.getDefault())
        val timeStr = timeFmt.format(java.util.Date(entry.scheduledAtMillis))

        AlertDialog.Builder(this)
            .setTitle("Scheduled: $timeStr")
            .setItems(arrayOf(
                "✏️  Edit scheduled message",
                "▶️  Send now",
                "🗑  Delete scheduled message"
            )) { _, i ->
                val handler = scheduledMessages[entry.key]?.second
                when (i) {
                    0 -> {
                        // Edit text only — original scheduled time preserved
                        val originalTime = entry.scheduledAtMillis
                        val originalKey = entry.key
                        handler?.removeCallbacksAndMessages(null)
                        scheduledMessages.remove(originalKey)
                        viewModel.deleteScheduledPlaceholder(originalKey)

                        val inputEdit = android.widget.EditText(this@ThreadActivity).apply {
                            setText(realText); setSelection(realText.length)
                            setPadding(48, 32, 48, 32)
                        }
                        val timeFmtEdit = java.text.SimpleDateFormat("MMM d, h:mm a", java.util.Locale.getDefault())
                        AlertDialog.Builder(this@ThreadActivity)
                            .setTitle("Edit message")
                            .setMessage("⏰ Scheduled: ${timeFmtEdit.format(java.util.Date(originalTime))}")
                            .setView(inputEdit)
                            .setPositiveButton("Save") { _, _ ->
                                val newText = inputEdit.text.toString().trim()
                                if (newText.isNotBlank()) scheduleMessage(newText, originalTime)
                            }
                            .setNegativeButton("Cancel") { _, _ ->
                                scheduleMessage(realText, originalTime)
                            }
                            .show()
                    }
                    1 -> {
                        // Send now
                        handler?.removeCallbacksAndMessages(null)
                        scheduledMessages.remove(entry.key)
                        viewModel.deleteScheduledPlaceholder(entry.key)
                        viewModel.sendMessage(realText)
                    }
                    2 -> {
                        // Delete / cancel
                        handler?.removeCallbacksAndMessages(null)
                        scheduledMessages.remove(entry.key)
                        viewModel.deleteScheduledPlaceholder(entry.key)
                    }
                }
            }
            .show()
    }

    private fun mergeAndSubmit(
        messages: List<com.meharenterprises.originsms.core.Message>,
        scheduled: List<com.meharenterprises.originsms.ui.thread.ThreadViewModel.ScheduledEntry>
    ) {
        val timeFmt = java.text.SimpleDateFormat("MMM d, h:mm a", java.util.Locale.getDefault())
        // Convert each scheduled entry into a fake QUEUED message so the
        // existing MessageAdapter renders it as a sent bubble automatically.
        // We use a large negative ID so it never collides with real messages.
        val fakeScheduled = scheduled.mapIndexed { idx, entry ->
            com.meharenterprises.originsms.core.Message(
                id = -(idx + 1L),
                threadId = threadId,
                address = address,
                body = entry.text,  // Clean text — clock shown in status line
                dateMillis = entry.scheduledAtMillis,
                dateSentMillis = entry.scheduledAtMillis,
                type = com.meharenterprises.originsms.core.MessageType.SMS,
                box = com.meharenterprises.originsms.core.MessageBox.QUEUED,
                isRead = true
            )
        }
        val combined = (messages + fakeScheduled).sortedBy { it.dateMillis }
        val highlightId = intent.getLongExtra(EXTRA_HIGHLIGHT_MESSAGE_ID, -1L)
        val lmCheck = recycler.layoutManager as? LinearLayoutManager
        val lastVis = lmCheck?.findLastVisibleItemPosition() ?: -1
        val wasAtBottom = lastVis < 0 || lastVis >= adapter.itemCount - 3
        adapter.submitList(combined) {
            if (highlightId > 0L) {
                val pos = combined.indexOfFirst { it.id == highlightId }
                if (pos >= 0) {
                    recycler.scrollToPosition(pos)
                    recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrollStateChanged(rv: RecyclerView, newState: Int) {
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                rv.removeOnScrollListener(this)
                                rv.postDelayed({
                                    rv.findViewHolderForAdapterPosition(pos)?.itemView?.let { v ->
                                        val bubble = v.findViewById<android.view.View>(R.id.txtMessageBody) ?: v
                                        val origBg = bubble.background
                                        bubble.setBackgroundColor(android.graphics.Color.parseColor("#AAFFE082"))
                                        android.os.Handler(android.os.Looper.getMainLooper())
                                            .postDelayed({ bubble.background = origBg }, 2500)
                                    }
                                }, 100)
                            }
                        }
                    })
                }
                intent.removeExtra(EXTRA_HIGHLIGHT_MESSAGE_ID)
            } else if (combined.isNotEmpty() && wasAtBottom) {
                recycler.scrollToPosition(combined.size - 1)
            }
        }
    }

    private fun showAttachmentSheet() {
        val sheet = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.sheet_attachment_options, null)
        sheet.setContentView(view)

        view.findViewById<View>(R.id.optionCamera).setOnClickListener {
            sheet.dismiss()
            requestCameraThenLaunch()
        }
        view.findViewById<View>(R.id.optionGallery).setOnClickListener {
            sheet.dismiss()
            attachmentPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
            )
        }
        view.findViewById<View>(R.id.optionFile).setOnClickListener {
            sheet.dismiss()
            filePickerLauncher.launch("*/*")
        }
        sheet.show()
    }

    private fun requestCameraThenLaunch() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            launchCamera()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun launchCamera() {
        val photoFile = File.createTempFile("origin_camera_", ".jpg", cacheDir)
        val uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", photoFile)
        pendingCameraUri = uri
        cameraLauncher.launch(uri)
    }

    private fun setupEmojiPicker() {
        emojiPicker = findViewById(R.id.emojiPickerView)
        val btnEmoji = findViewById<ImageButton>(R.id.btnEmoji)

        emojiPicker.setOnEmojiPickedListener { emojiViewItem ->
            val emoji = emojiViewItem.emoji
            val start = editMessage.selectionStart.coerceAtLeast(0)
            val end = editMessage.selectionEnd.coerceAtLeast(0)
            editMessage.text?.replace(minOf(start, end), maxOf(start, end), emoji)
        }

        btnEmoji.setOnClickListener {
            if (emojiPickerVisible) hideEmojiPicker() else showEmojiPicker()
        }

        editMessage.setOnClickListener {
            if (emojiPickerVisible) hideEmojiPicker()
        }
    }

    private fun showEmojiPicker() {
        emojiPickerVisible = true
        emojiPicker.visibility = View.VISIBLE
        val imm = getSystemService(android.view.inputmethod.InputMethodManager::class.java)
        imm.hideSoftInputFromWindow(editMessage.windowToken, 0)
    }

    private fun hideEmojiPicker() {
        emojiPickerVisible = false
        emojiPicker.visibility = View.GONE
    }

    private fun setupSimIndicator() {
        // SIM switcher is now triggered by long-pressing the message input
        // field (like Google Messages), rather than a permanent row that takes
        // up space. On single-SIM devices this long-press does nothing extra.
        try {
            val subManager = getSystemService(SubscriptionManager::class.java)
            val activeSubscriptions = subManager.activeSubscriptionInfoList
            if (activeSubscriptions != null && activeSubscriptions.size > 1) {
                val defaultSubId = SubscriptionManager.getDefaultSmsSubscriptionId()
                viewModel.selectedSubscriptionId = defaultSubId

                editMessage.setOnLongClickListener {
                    val options = activeSubscriptions.mapIndexed { idx, info ->
                        "SIM ${idx + 1} · ${info.displayName}"
                    }.toTypedArray()
                    val currentIdx = activeSubscriptions.indexOfFirst {
                        it.subscriptionId == viewModel.selectedSubscriptionId
                    }
                    AlertDialog.Builder(this)
                        .setTitle("Send from SIM")
                        .setSingleChoiceItems(options, currentIdx) { dialog, idx ->
                            viewModel.selectedSubscriptionId = activeSubscriptions[idx].subscriptionId
                            val simName = activeSubscriptions[idx].displayName
                            android.widget.Toast.makeText(
                                this, "Sending from $simName", android.widget.Toast.LENGTH_SHORT
                            ).show()
                            dialog.dismiss()
                        }
                        .setNegativeButton(android.R.string.cancel, null)
                        .show()
                    true
                }
            }
        } catch (_: SecurityException) {
            // READ_PHONE_STATE not granted; SIM switcher stays hidden gracefully.
        }
    }

    private fun renderAttachmentPreviews(uris: List<Uri>) {
        attachmentContainer.removeAllViews()
        attachmentScroll.visibility = if (uris.isEmpty()) View.GONE else View.VISIBLE

        val inflater = LayoutInflater.from(this)
        for (uri in uris) {
            val chip = inflater.inflate(R.layout.item_attachment_preview, attachmentContainer, false)
            val thumbnail = chip.findViewById<ImageView>(R.id.imgThumbnail)
            val removeButton = chip.findViewById<ImageButton>(R.id.btnRemoveAttachment)

            try {
                contentResolver.openInputStream(uri)?.use { stream ->
                    val bitmap = android.graphics.BitmapFactory.decodeStream(stream)
                    if (bitmap != null) thumbnail.setImageBitmap(bitmap)
                }
            } catch (_: Exception) {
                thumbnail.setImageResource(R.drawable.ic_attach)
            }
            removeButton.setOnClickListener { viewModel.removePendingAttachment(uri) }
            attachmentContainer.addView(chip)
        }
    }

    private fun restoreDraft() {
        lifecycleScope.launch {
            val draft = viewModel.getDraft(threadId)
            if (!draft.isNullOrBlank()) {
                editMessage.setText(draft)
                editMessage.setSelection(draft.length)
            }
        }
    }

    

    

    override fun onBackPressed() {
        if (adapter.getSelectedCount() > 0) {
            adapter.clearSelection()
            updateMessageSelectionBar()
        } else {
            super.onBackPressed()
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(ClipboardManager::class.java)
        clipboard.setPrimaryClip(ClipData.newPlainText("message", text))
    }

    fun showFloatingStarAnimation(anchorView: android.view.View) {
        val ov = (window.decorView as android.view.ViewGroup).overlay; val rnd = java.util.Random()
        val loc = IntArray(2); anchorView.getLocationOnScreen(loc)
        val wloc = IntArray(2); window.decorView.getLocationOnScreen(wloc)
        val sx = (loc[0]-wloc[0]).toFloat()+anchorView.width/2f
        val sy = (loc[1]-wloc[1]).toFloat()+anchorView.height/2f
        listOf("⭐","✨","⭐","✨","⭐").forEachIndexed { i, star ->
            val tv = android.widget.TextView(this).apply {
                text=star; textSize=26f; alpha=0f
                measure(android.view.View.MeasureSpec.UNSPECIFIED, android.view.View.MeasureSpec.UNSPECIFIED)
                layout(0,0,measuredWidth,measuredHeight)
            }
            val screenW = resources.displayMetrics.widthPixels.toFloat()
            val positions = listOf(screenW*0.1f,screenW*0.3f,screenW*0.5f,screenW*0.7f,screenW*0.9f)
            val ox = positions.getOrElse(i){sx} + rnd.nextInt(40)-20f
            ov.add(tv); tv.translationX=ox; tv.translationY=sy
            android.animation.AnimatorSet().apply {
                val d=(i*100L)
                playTogether(
                    android.animation.ObjectAnimator.ofFloat(tv,"alpha",0f,1f,1f,0f).apply{duration=1000;startDelay=d},
                    android.animation.ObjectAnimator.ofFloat(tv,"translationY",sy,sy-240f).apply{duration=1000;startDelay=d},
                    android.animation.ObjectAnimator.ofFloat(tv,"translationX",ox,ox+(rnd.nextInt(80)-40f)).apply{duration=1000;startDelay=d},
                    android.animation.ObjectAnimator.ofFloat(tv,"scaleX",0.4f,1.5f,0.9f).apply{duration=1000;startDelay=d},
                    android.animation.ObjectAnimator.ofFloat(tv,"scaleY",0.4f,1.5f,0.9f).apply{duration=1000;startDelay=d}
                )
                addListener(object:android.animation.AnimatorListenerAdapter(){override fun onAnimationEnd(a:android.animation.Animator){ov.remove(tv)}})
                start()
            }
        }
    }

    override fun onResume() {
        super.onResume(); activeThreadId = threadId
        if (threadId > 0L) lifecycleScope.launch {
            kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                try { com.meharenterprises.originsms.core.SmsRepository(this@ThreadActivity).markThreadRead(threadId) } catch(_:Exception){}
            }
            getSystemService(android.app.NotificationManager::class.java)?.cancel(threadId.toInt())
        }
    }
    override fun onPause() {
        super.onPause(); if (activeThreadId == threadId) activeThreadId = -1L
        if (threadId > 0L) lifecycleScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            try { com.meharenterprises.originsms.core.SmsRepository(this@ThreadActivity).markThreadRead(threadId) } catch(_:Exception){}
        }
    }

    private val fabSizeDp = 36
    private fun fabPx() = (fabSizeDp * resources.displayMetrics.density + 0.5f).toInt()
    private fun setFabCircle() { fabCard?.layoutParams?.width = fabPx(); fabCard?.requestLayout() }
    private fun setFabPill() { fabCard?.layoutParams?.width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT; fabCard?.requestLayout() }
    private fun setupScrollFab() {
        val card = fabCard ?: return; card.visibility = android.view.View.GONE
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                val lm = rv.layoutManager as? LinearLayoutManager ?: return
                val atBottom = adapter.itemCount == 0 || lm.findLastVisibleItemPosition() >= adapter.itemCount - 3
                if (atBottom) {
                    if (card.visibility == android.view.View.VISIBLE) card.animate().alpha(0f).setDuration(200).withEndAction { card.visibility = android.view.View.GONE }.start()
                    collapseFab()
                } else if (card.visibility != android.view.View.VISIBLE) { card.alpha=0f; card.visibility=android.view.View.VISIBLE; card.animate().alpha(1f).setDuration(200).start() }
            }
        })
        card.setOnClickListener { scrollToLatest() }
    }
    private fun showUnreadFab(count: Int) {
        val card = fabCard ?: return; val tv = fabTextView ?: return
        val text = if (count == 1) "1 New message" else "$count New messages"
        if (tv.text == text) return
        tv.text = text; tv.visibility = android.view.View.VISIBLE
        setFabPill()
        if (card.visibility != android.view.View.VISIBLE) { card.alpha=0f; card.visibility=android.view.View.VISIBLE; card.animate().alpha(1f).setDuration(200).start() }
    }
    private fun collapseFab(hide: Boolean = false) {
        unreadBelowFold = 0; fabTextView?.visibility = android.view.View.GONE; fabTextView?.text = ""
        if (hide) fabCard?.animate()?.alpha(0f)?.setDuration(200)?.withEndAction { fabCard?.visibility = android.view.View.GONE }?.start()
    }
    private fun scrollToLatest() {
        collapseFab(hide = true)
        val last = adapter.itemCount - 1; if (last >= 0) recycler.smoothScrollToPosition(last)
    }



    companion object {
        @JvmStatic var activeThreadId: Long = -1L
        const val EXTRA_HIGHLIGHT_MESSAGE_ID = "HIGHLIGHT_MESSAGE_ID"
        const val EXTRA_THREAD_ID = "extra_thread_id"
        const val EXTRA_ADDRESS = "extra_address"
        const val EXTRA_DISPLAY_NAME = "extra_display_name"
        const val EXTRA_PREFILL_BODY = "extra_prefill_body"
        const val EXTRA_FROM_TRASH = "extra_from_trash"
        private const val MAX_ATTACHMENTS = 5
    }
}
