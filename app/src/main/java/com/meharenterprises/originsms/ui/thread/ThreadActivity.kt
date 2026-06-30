package com.meharenterprises.originsms.ui.thread

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
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
    private lateinit var simIndicatorRow: LinearLayout
    private lateinit var txtSimLabel: TextView
    private lateinit var toolbar: MaterialToolbar
    private lateinit var messageSelectionBar: View
    private lateinit var txtMessageSelectionCount: TextView

    private var threadId: Long = -1L
    private var address: String = ""
    private var displayName: String = ""
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)

        threadId = intent.getLongExtra(EXTRA_THREAD_ID, -1L)
        address = intent.getStringExtra(EXTRA_ADDRESS).orEmpty()
        displayName = intent.getStringExtra(EXTRA_DISPLAY_NAME).orEmpty().ifBlank { address }

        viewModel = ViewModelProvider(this)[ThreadViewModel::class.java]

        setupToolbar()
        setupMessageSelectionBar()
        setupRecyclerView()
        setupComposeBar()
        setupEmojiPicker()
        setupSimIndicator()

        viewModel.bind(threadId, address)
        viewModel.messages.observe(this) { list ->
            adapter.submitList(list) {
                if (list.isNotEmpty()) recycler.scrollToPosition(list.size - 1)
            }
        }
        viewModel.pendingAttachments.observe(this) { uris -> renderAttachmentPreviews(uris) }

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
        toolbar.title = displayName
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setDisplayShowTitleEnabled(true)
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
            R.id.action_delete_thread -> {
                confirmDeleteThread()
                true
            }
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
        AlertDialog.Builder(this)
            .setTitle(R.string.menu_hide_chat)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                lifecycleScope.launch {
                    val dao = OriginDatabase.getInstance(this@ThreadActivity).threadLockDao()
                    val existing = dao.getForThread(threadId)
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
        findViewById<View>(R.id.btnMessageForward).setOnClickListener {
            val selected = currentSelectedMessages()
            if (selected.size == 1) {
                forwardMessage(selected.first())
            }
            adapter.clearSelection()
            updateMessageSelectionBar()
        }
        findViewById<View>(R.id.btnMessageDelete).setOnClickListener {
            val ids = adapter.getSelectedIds()
            AlertDialog.Builder(this)
                .setTitle(R.string.action_delete_message)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    viewModel.deleteMessages(ids)
                    adapter.clearSelection()
                    updateMessageSelectionBar()
                }
                .setNegativeButton(android.R.string.cancel, null)
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
            onTapFailedRetry = { message -> confirmRetry(message) }
        )
        val lm = LinearLayoutManager(this).apply { stackFromEnd = true }
        recycler.layoutManager = lm
        recycler.adapter = adapter
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

        findViewById<ImageButton>(R.id.btnSend).setOnClickListener {
            val text = editMessage.text?.toString().orEmpty()
            val hasAttachments = !viewModel.pendingAttachments.value.isNullOrEmpty()
            if (text.isNotBlank() || hasAttachments) {
                viewModel.sendMessage(text)
                editMessage.setText("")
                hideEmojiPicker()
            }
        }

        findViewById<ImageButton>(R.id.btnAttach).setOnClickListener { showAttachmentSheet() }
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
        simIndicatorRow = findViewById(R.id.simIndicatorRow)
        txtSimLabel = findViewById(R.id.txtSimLabel)

        try {
            val subManager = getSystemService(SubscriptionManager::class.java)
            val activeSubscriptions = subManager.activeSubscriptionInfoList
            if (activeSubscriptions != null && activeSubscriptions.size > 1) {
                simIndicatorRow.visibility = View.VISIBLE
                val defaultSubId = SubscriptionManager.getDefaultSmsSubscriptionId()
                viewModel.selectedSubscriptionId = defaultSubId
                updateSimLabel(activeSubscriptions.indexOfFirst { it.subscriptionId == defaultSubId })

                simIndicatorRow.setOnClickListener {
                    val options = activeSubscriptions.mapIndexed { idx, info ->
                        "SIM ${idx + 1} · ${info.displayName}"
                    }.toTypedArray()
                    val currentIdx = activeSubscriptions.indexOfFirst {
                        it.subscriptionId == viewModel.selectedSubscriptionId
                    }
                    AlertDialog.Builder(this)
                        .setTitle("Send from")
                        .setSingleChoiceItems(options, currentIdx) { dialog, idx ->
                            viewModel.selectedSubscriptionId = activeSubscriptions[idx].subscriptionId
                            updateSimLabel(idx)
                            dialog.dismiss()
                        }
                        .setNegativeButton(android.R.string.cancel, null)
                        .show()
                }
            } else {
                simIndicatorRow.visibility = View.GONE
            }
        } catch (_: SecurityException) {
            simIndicatorRow.visibility = View.GONE
        }
    }

    private fun updateSimLabel(simIndex: Int) {
        try {
            val subManager = getSystemService(SubscriptionManager::class.java)
            val sub = subManager.activeSubscriptionInfoList?.getOrNull(simIndex)
            val simName = sub?.displayName ?: "SIM ${simIndex + 1}"
            txtSimLabel.text = "Sending from $simName · tap to switch"
        } catch (_: Exception) {
            txtSimLabel.text = "SIM ${simIndex + 1}"
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

    override fun onPause() {
        super.onPause()
        viewModel.saveDraft(editMessage.text?.toString().orEmpty())
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMessages()
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

    companion object {
        const val EXTRA_THREAD_ID = "extra_thread_id"
        const val EXTRA_ADDRESS = "extra_address"
        const val EXTRA_DISPLAY_NAME = "extra_display_name"
        const val EXTRA_PREFILL_BODY = "extra_prefill_body"
        private const val MAX_ATTACHMENTS = 5
    }
}
