package com.meharenterprises.originsms.ui.compose

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var repository: SmsRepository
    private lateinit var contactsHelper: ContactsHelper

    private var selectedAddress: String? = null  // set when user taps a contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        repository = SmsRepository(applicationContext)
        contactsHelper = ContactsHelper(this)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        editRecipient = findViewById(R.id.editRecipient)
        editMessage = findViewById(R.id.editMessage)
        recyclerContactPicker = findViewById(R.id.recyclerContactPicker)
        recyclerMessages = findViewById(R.id.recyclerMessages)

        intent.getStringExtra(EXTRA_PREFILL_BODY)?.let { editMessage.setText(it) }

        // Empty message history (no thread yet)
        recyclerMessages.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }
        recyclerMessages.adapter = MessageAdapter(onLongPress = {})

        // Contact picker list
        contactPickerAdapter = ContactPickerAdapter { contact ->
            // User tapped a contact — fill the recipient field and hide picker
            selectedAddress = ContactsHelper.normalize(contact.phoneNumber)
            editRecipient.setText(contact.displayName)
            editRecipient.clearFocus()
            showMessageMode()
        }
        recyclerContactPicker.layoutManager = LinearLayoutManager(this)
        recyclerContactPicker.adapter = contactPickerAdapter

        // Load all contacts in background and populate the picker
        lifecycleScope.launch {
            val contacts = withContext(Dispatchers.IO) { contactsHelper.getAllContactsWithNumbers() }
            contactPickerAdapter.submitContacts(contacts)
        }

        // As the user types, filter the contact list live
        editRecipient.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                val query = s?.toString().orEmpty()
                // If user starts editing after having selected a contact,
                // clear the selection so we don't send to a stale address
                if (selectedAddress != null) selectedAddress = null
                contactPickerAdapter.filter(query)
                // If nothing typed show full contact list; if typing a pure
                // number show both the list (filtered) and enable direct send
                if (recyclerMessages.visibility == View.VISIBLE && query.isNotBlank()) {
                    showContactMode()
                }
            }
        })

        // Edit-recipient field gains focus → show contact picker
        editRecipient.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) showContactMode()
        }

        editMessage.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && selectedAddress == null) {
                // User jumped to message body without selecting a contact —
                // treat whatever is in the recipient field as a raw number
                val raw = editRecipient.text?.toString().orEmpty().trim()
                if (raw.isNotBlank()) {
                    selectedAddress = ContactsHelper.normalize(raw)
                    showMessageMode()
                }
            }
        }

        findViewById<ImageButton>(R.id.btnSend).setOnClickListener { attemptSend() }
    }

    private fun showContactMode() {
        recyclerContactPicker.visibility = View.VISIBLE
        recyclerMessages.visibility = View.GONE
    }

    private fun showMessageMode() {
        recyclerContactPicker.visibility = View.GONE
        recyclerMessages.visibility = View.VISIBLE
        // Move focus to the message body
        editMessage.requestFocus()
        val imm = getSystemService(android.view.inputmethod.InputMethodManager::class.java)
        imm.showSoftInput(editMessage, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT)
    }

    private fun attemptSend() {
        val body = editMessage.text?.toString().orEmpty()
        // Resolve final address — either from contact selection or raw field text
        val destination = selectedAddress?.takeIf { it.isNotBlank() }
            ?: ContactsHelper.normalize(editRecipient.text?.toString().orEmpty())

        if (destination.isBlank() || body.isBlank()) return

        lifecycleScope.launch {
            repository.sendSms(destination, body, threadId = null)
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
