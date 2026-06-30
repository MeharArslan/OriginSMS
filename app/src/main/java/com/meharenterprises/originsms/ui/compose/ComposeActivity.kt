package com.meharenterprises.originsms.ui.compose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Telephony
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.emoji2.emojipicker.EmojiPickerView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.ContactsHelper
import com.meharenterprises.originsms.core.SmsRepository
import com.meharenterprises.originsms.ui.thread.MessageAdapter
import com.meharenterprises.originsms.ui.thread.ThreadActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * New-message screen with:
 * - Contact search as-you-type (name or number) backed by ContactsHelper
 * - Manual number entry still works — just type a number and tap Send
 * - Once a contact is selected or number confirmed, opens ThreadActivity
 */
class ComposeActivity : AppCompatActivity() {

    private lateinit var editRecipient: EditText
    private lateinit var editMessage: EditText
    private lateinit var recyclerContactPicker: RecyclerView
    private lateinit var recyclerMessages: RecyclerView
    private lateinit var contactPickerAdapter: ContactPickerAdapter
    private lateinit var emojiPicker: EmojiPickerView
    private lateinit var repository: SmsRepository
    private lateinit var contactsHelper: ContactsHelper

    private var selectedAddress: String? = null  // set when user taps a contact
    private var isProgrammaticTextChange = false
    private var emojiPickerVisible = false
    private val pendingAttachments = mutableListOf<Uri>()

    private val attachmentPickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(5)
    ) { uris ->
        uris.forEach { uri ->
            try {
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } catch (_: SecurityException) { }
            pendingAttachments.add(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        repository = SmsRepository(applicationContext)
        contactsHelper = ContactsHelper(this)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setDisplayShowTitleEnabled(false)

        editRecipient = findViewById(R.id.editRecipient)
        editMessage = findViewById(R.id.editMessage)
        recyclerContactPicker = findViewById(R.id.recyclerContactPicker)
        recyclerMessages = findViewById(R.id.recyclerMessages)
        emojiPicker = findViewById(R.id.emojiPickerView)

        intent.getStringExtra(EXTRA_PREFILL_BODY)?.let { editMessage.setText(it) }

        recyclerMessages.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }
        recyclerMessages.adapter = MessageAdapter(onLongPress = {})

        contactPickerAdapter = ContactPickerAdapter { contact ->
            selectedAddress = ContactsHelper.normalize(contact.phoneNumber)
            isProgrammaticTextChange = true
            editRecipient.setText(contact.displayName)
            isProgrammaticTextChange = false
            editRecipient.clearFocus()
            showMessageMode()
        }
        recyclerContactPicker.layoutManager = LinearLayoutManager(this)
        recyclerContactPicker.adapter = contactPickerAdapter

        lifecycleScope.launch {
            val contacts = withContext(Dispatchers.IO) { contactsHelper.getAllContactsWithNumbers() }
            contactPickerAdapter.submitContacts(contacts)
        }

        editRecipient.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                if (isProgrammaticTextChange) return

                val query = s?.toString().orEmpty()
                if (selectedAddress != null) selectedAddress = null
                contactPickerAdapter.filter(query)
                if (recyclerMessages.visibility == View.VISIBLE && query.isNotBlank()) {
                    showContactMode()
                }
            }
        })

        editRecipient.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) showContactMode()
        }

        editMessage.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && selectedAddress == null) {
                val raw = editRecipient.text?.toString().orEmpty().trim()
                if (raw.isNotBlank()) {
                    selectedAddress = ContactsHelper.normalize(raw)
                    showMessageMode()
                }
            }
            if (hasFocus && emojiPickerVisible) hideEmojiPicker()
        }

        findViewById<ImageButton>(R.id.btnSend).setOnClickListener { attemptSend() }
        findViewById<ImageButton>(R.id.btnAttach).setOnClickListener {
            attachmentPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
            )
        }

        setupEmojiPicker()
    }

    private fun setupEmojiPicker() {
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

    private fun showContactMode() {
        recyclerContactPicker.visibility = View.VISIBLE
        recyclerMessages.visibility = View.GONE
    }

    private fun showMessageMode() {
        recyclerContactPicker.visibility = View.GONE
        recyclerMessages.visibility = View.VISIBLE
        editMessage.requestFocus()
        val imm = getSystemService(android.view.inputmethod.InputMethodManager::class.java)
        imm.showSoftInput(editMessage, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT)
    }

    private fun attemptSend() {
        val body = editMessage.text?.toString().orEmpty()
        val destination = selectedAddress?.takeIf { it.isNotBlank() }
            ?: ContactsHelper.normalize(editRecipient.text?.toString().orEmpty())

        if (destination.isBlank()) return
        if (body.isBlank() && pendingAttachments.isEmpty()) return

        lifecycleScope.launch {
            if (pendingAttachments.isEmpty()) {
                repository.sendSms(destination, body, threadId = null)
            } else {
                repository.sendMms(destination, body, pendingAttachments.toList(), threadId = null)
            }
            val resolvedThreadId = withContext(Dispatchers.IO) {
                Telephony.Threads.getOrCreateThreadId(this@ComposeActivity, destination)
            }
            val contactInfo = withContext(Dispatchers.IO) { contactsHelper.resolve(destination) }

            val intent = Intent(this@ComposeActivity, ThreadActivity::class.java).apply {
                putExtra(ThreadActivity.EXTRA_THREAD_ID, resolvedThreadId)
                putExtra(ThreadActivity.EXTRA_ADDRESS, destination)
                putExtra(ThreadActivity.EXTRA_DISPLAY_NAME, contactInfo.displayName)
            }
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_PREFILL_BODY = "extra_prefill_body"
    }
}
