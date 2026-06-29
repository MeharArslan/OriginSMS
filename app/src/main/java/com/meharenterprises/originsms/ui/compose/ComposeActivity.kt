package com.meharenterprises.originsms.ui.compose

import android.os.Bundle
import android.provider.Telephony
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
 * Screen for starting a brand-new conversation. Once the first message is
 * sent successfully, the activity hands off to ThreadActivity using the
 * thread ID the system provider assigns, so the rest of the conversation
 * continues in the normal thread UI.
 */
class ComposeActivity : AppCompatActivity() {

    private lateinit var editRecipient: EditText
    private lateinit var editMessage: EditText
    private lateinit var repository: SmsRepository
    private lateinit var contactsHelper: ContactsHelper

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

        intent.getStringExtra(EXTRA_PREFILL_BODY)?.let { editMessage.setText(it) }

        // Empty adapter for visual consistency; this screen doesn't show history
        // since no thread exists yet for a brand-new recipient.
        findViewById<RecyclerView>(R.id.recyclerMessages).apply {
            layoutManager = LinearLayoutManager(this@ComposeActivity).apply { stackFromEnd = true }
            adapter = MessageAdapter(onLongPress = {})
        }

        findViewById<ImageButton>(R.id.btnSend).setOnClickListener {
            attemptSend()
        }
    }

    private fun attemptSend() {
        val rawNumber = editRecipient.text?.toString()?.trim().orEmpty()
        val body = editMessage.text?.toString().orEmpty()

        if (rawNumber.isBlank() || body.isBlank()) return

        val destination = normalizeNumber(rawNumber)

        lifecycleScope.launch {
            repository.sendSms(destination, body, threadId = null)
            val resolvedThreadId = withContext(Dispatchers.IO) {
                Telephony.Threads.getOrCreateThreadId(this@ComposeActivity, destination)
            }
            val contactInfo = withContext(Dispatchers.IO) { contactsHelper.resolve(destination) }

            val intent = android.content.Intent(this@ComposeActivity, ThreadActivity::class.java).apply {
                putExtra(ThreadActivity.EXTRA_THREAD_ID, resolvedThreadId)
                putExtra(ThreadActivity.EXTRA_ADDRESS, destination)
                putExtra(ThreadActivity.EXTRA_DISPLAY_NAME, contactInfo.displayName)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun normalizeNumber(input: String): String {
        return input.filter { it.isDigit() || it == '+' }
    }

    companion object {
        const val EXTRA_PREFILL_BODY = "extra_prefill_body"
    }
}
