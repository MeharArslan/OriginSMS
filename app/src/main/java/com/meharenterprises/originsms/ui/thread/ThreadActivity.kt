package com.meharenterprises.originsms.ui.thread

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import androidx.emoji2.emojipicker.EmojiPickerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.Message
import kotlinx.coroutines.launch

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

    private var threadId: Long = -1L
    private var address: String = ""
    private var displayName: String = ""
    private var emojiPickerVisible = false

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)

        threadId = intent.getLongExtra(EXTRA_THREAD_ID, -1L)
        address = intent.getStringExtra(EXTRA_ADDRESS).orEmpty()
        displayName = intent.getStringExtra(EXTRA_DISPLAY_NAME).orEmpty().ifBlank { address }

        viewModel = ViewModelProvider(this)[ThreadViewModel::class.java]

        setupToolbar()
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
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = displayName
        toolbar.setNavigationOnClickListener { finish() }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_thread, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_call -> {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Uri.encode(address)}"))
                startActivity(dialIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        recycler = findViewById(R.id.recyclerMessages)
        adapter = MessageAdapter(onLongPress = { message -> showMessageActions(message) })
        val lm = LinearLayoutManager(this).apply { stackFromEnd = true }
        recycler.layoutManager = lm
        recycler.adapter = adapter
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

        findViewById<ImageButton>(R.id.btnAttach).setOnClickListener {
            attachmentPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
            )
        }
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
        // Hide the software keyboard so the emoji picker doesn't fight for space
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
                // Dual-SIM: show indicator row and allow tapping to switch
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
            // READ_PHONE_STATE may not be granted yet; hide the row gracefully
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

    private fun showMessageActions(message: Message) {
        val options = arrayOf(
            getString(R.string.action_copy_text),
            getString(R.string.action_delete_message)
        )
        AlertDialog.Builder(this)
            .setItems(options) { _, index ->
                when (index) {
                    0 -> copyToClipboard(message.body)
                    1 -> confirmDeleteMessage(message)
                }
            }
            .show()
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(ClipboardManager::class.java)
        clipboard.setPrimaryClip(ClipData.newPlainText("message", text))
    }

    private fun confirmDeleteMessage(message: Message) {
        AlertDialog.Builder(this)
            .setTitle(R.string.action_delete_message)
            .setPositiveButton(android.R.string.ok) { _, _ -> viewModel.deleteMessage(message.id) }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    companion object {
        const val EXTRA_THREAD_ID = "extra_thread_id"
        const val EXTRA_ADDRESS = "extra_address"
        const val EXTRA_DISPLAY_NAME = "extra_display_name"
        const val EXTRA_PREFILL_BODY = "extra_prefill_body"
        private const val MAX_ATTACHMENTS = 5
    }
}
