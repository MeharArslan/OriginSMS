package com.meharenterprises.originsms.ui.thread

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var attachmentScroll: HorizontalScrollView
    private lateinit var attachmentContainer: LinearLayout

    private var threadId: Long = -1L
    private var address: String = ""
    private var displayName: String = ""

    private val attachmentPickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(MAX_ATTACHMENTS_PER_MESSAGE)
    ) { uris ->
        uris.forEach { uri ->
            try {
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } catch (_: SecurityException) {
                // Photo Picker URIs are already scoped to this app's lifetime read
                // access without needing a persistable grant, so this is expected
                // to throw on most devices — the URI remains readable regardless.
            }
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
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupRecyclerView() {
        recycler = findViewById(R.id.recyclerMessages)
        adapter = MessageAdapter(onLongPress = { message -> showMessageActions(message) })
        val layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
    }

    private fun setupComposeBar() {
        editMessage = findViewById(R.id.editMessage)
        attachmentScroll = findViewById(R.id.attachmentPreviewScroll)
        attachmentContainer = findViewById(R.id.attachmentPreviewContainer)
        val sendButton = findViewById<ImageButton>(R.id.btnSend)
        val attachButton = findViewById<ImageButton>(R.id.btnAttach)

        attachButton.setOnClickListener {
            attachmentPickerLauncher.launch(
                androidx.activity.result.PickVisualMediaRequest(
                    androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageAndVideo
                )
            )
        }

        sendButton.setOnClickListener {
            val text = editMessage.text?.toString().orEmpty()
            val hasAttachments = !viewModel.pendingAttachments.value.isNullOrEmpty()
            if (text.isNotBlank() || hasAttachments) {
                viewModel.sendMessage(text)
                editMessage.setText("")
            }
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
        private const val MAX_ATTACHMENTS_PER_MESSAGE = 5
    }
}
