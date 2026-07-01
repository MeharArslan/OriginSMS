package com.meharenterprises.originsms.lock

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.ConversationSummary
import com.meharenterprises.originsms.core.SmsRepository
import com.meharenterprises.originsms.data.db.OriginDatabase
import com.meharenterprises.originsms.ui.conversations.ConversationAdapter
import com.meharenterprises.originsms.ui.thread.ThreadActivity
import kotlinx.coroutines.launch

/**
 * Single gate activity for all PIN/biometric-protected actions:
 *  - INTENT_OPEN_THREAD (default): authenticate then open the locked thread
 *  - INTENT_REMOVE_LOCK: authenticate then strip the lock flag from a thread
 *  - INTENT_OPEN_VAULT: authenticate then show the list of hidden conversations
 *
 * Biometric is offered first automatically when enabled and available; PIN is
 * always available as a fallback. Failed PIN attempts are throttled by PinManager.
 */
class LockUnlockActivity : AppCompatActivity() {

    private lateinit var pinManager: PinManager
    private lateinit var editPin: EditText
    private lateinit var txtError: TextView
    private lateinit var txtPrompt: TextView
    private lateinit var txtDisplayName: TextView
    private lateinit var btnUnlock: Button
    private lateinit var btnUseBiometric: Button
    private lateinit var recyclerVault: RecyclerView

    private var mode: String = INTENT_OPEN_THREAD
    private var threadId: Long = -1L
    private var address: String = ""
    private var displayName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_unlock)

        pinManager = PinManager(this)
        mode = intent.getStringExtra(EXTRA_UNLOCK_INTENT) ?: INTENT_OPEN_THREAD
        threadId = intent.getLongExtra(EXTRA_THREAD_ID, -1L)
        address = intent.getStringExtra(EXTRA_ADDRESS).orEmpty()
        displayName = intent.getStringExtra(EXTRA_DISPLAY_NAME).orEmpty().ifBlank { address }

        bindViews()
        configureForMode()

        btnUnlock.setOnClickListener { attemptPinUnlock() }
        btnUseBiometric.setOnClickListener { showBiometricPrompt() }

        if (pinManager.isBiometricEnabled() && isBiometricAvailable()) {
            showBiometricPrompt()
        }
    }

    private fun bindViews() {
        editPin = findViewById(R.id.editPin)
        txtError = findViewById(R.id.txtError)
        txtPrompt = findViewById(R.id.txtPrompt)
        txtDisplayName = findViewById(R.id.txtDisplayName)
        btnUnlock = findViewById(R.id.btnUnlock)
        btnUseBiometric = findViewById(R.id.btnUseBiometric)
        recyclerVault = findViewById(R.id.recyclerVault)
    }

    private fun configureForMode() {
        when (mode) {
            INTENT_REMOVE_LOCK -> {
                txtDisplayName.text = displayName
                txtPrompt.text = getString(R.string.menu_unlock_chat)
            }
            INTENT_OPEN_VAULT -> {
                txtDisplayName.text = getString(R.string.section_hidden_chats)
                txtPrompt.text = getString(R.string.lock_enter_pin)
            }
            else -> {
                txtDisplayName.text = displayName
                txtPrompt.text = getString(R.string.lock_enter_pin)
            }
        }

        btnUseBiometric.visibility = if (isBiometricAvailable()) View.VISIBLE else View.GONE

        val remainingLockout = pinManager.getLockoutSecondsRemaining()
        if (remainingLockout > 0) {
            applyLockoutUi(remainingLockout)
        }
    }

    private fun isBiometricAvailable(): Boolean {
        return BiometricManager.from(this)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
            BiometricManager.BIOMETRIC_SUCCESS
    }

    private fun attemptPinUnlock() {
        val remainingLockout = pinManager.getLockoutSecondsRemaining()
        if (remainingLockout > 0) {
            applyLockoutUi(remainingLockout)
            return
        }

        val candidate = editPin.text?.toString().orEmpty()
        if (candidate.isBlank()) return

        if (pinManager.verifyPin(candidate)) {
            onAuthenticated()
        } else {
            val newLockout = pinManager.getLockoutSecondsRemaining()
            if (newLockout > 0) {
                applyLockoutUi(newLockout)
            } else {
                showError(getString(R.string.lock_wrong_pin))
            }
            editPin.setText("")
        }
    }

    private fun applyLockoutUi(secondsRemaining: Long) {
        showError(getString(R.string.lock_too_many_attempts, secondsRemaining))
        btnUnlock.isEnabled = false
        btnUseBiometric.isEnabled = false
        editPin.postDelayed({
            val stillRemaining = pinManager.getLockoutSecondsRemaining()
            if (stillRemaining <= 0) {
                btnUnlock.isEnabled = true
                btnUseBiometric.isEnabled = true
                txtError.visibility = View.GONE
            } else {
                applyLockoutUi(stillRemaining)
            }
        }, 1000L)
    }

    private fun showBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onAuthenticated()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
            }
        }

        val prompt = BiometricPrompt(this, executor, callback)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.lock_biometric_title))
            .setSubtitle(getString(R.string.lock_biometric_subtitle))
            .setNegativeButtonText(getString(android.R.string.cancel))
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()

        prompt.authenticate(promptInfo)
    }

    private fun showError(message: String) {
        txtError.text = message
        txtError.visibility = View.VISIBLE
    }

    private fun onAuthenticated() {
        when (mode) {
            INTENT_REMOVE_LOCK -> {
                lifecycleScope.launch {
                    val database = OriginDatabase.getInstance(this@LockUnlockActivity)
                    database.threadLockDao().setLocked(threadId, false, 0L)
                    database.threadLockDao().setHidden(threadId, false)
                    setResult(RESULT_OK)
                    finish()
                }
            }
            INTENT_OPEN_VAULT -> {
                showVault()
            }
            else -> {
                val intent = Intent(this, ThreadActivity::class.java).apply {
                    putExtra(ThreadActivity.EXTRA_THREAD_ID, threadId)
                    putExtra(ThreadActivity.EXTRA_ADDRESS, address)
                    putExtra(ThreadActivity.EXTRA_DISPLAY_NAME, displayName)
                }
                startActivity(intent)
                finish()
            }
        }
    }

    private fun showVault() {
        editPin.visibility = View.GONE
        btnUnlock.visibility = View.GONE
        btnUseBiometric.visibility = View.GONE
        txtError.visibility = View.GONE
        recyclerVault.visibility = View.VISIBLE

        lifecycleScope.launch {
            val repository = SmsRepository(this@LockUnlockActivity)
            val hidden = repository.getConversations().filter { it.isHidden }

            val adapter = ConversationAdapter(
                onClick = { conversation -> openHiddenThread(conversation) },
                onLongClick = { conversation -> confirmUnhide(conversation) },
                selectionModeEnabled = false
            )
            recyclerVault.layoutManager = LinearLayoutManager(this@LockUnlockActivity)
            recyclerVault.adapter = adapter
            adapter.submitList(hidden)
        }
    }

    private fun openHiddenThread(conversation: ConversationSummary) {
        val intent = Intent(this, ThreadActivity::class.java).apply {
            putExtra(ThreadActivity.EXTRA_THREAD_ID, conversation.threadId)
            putExtra(ThreadActivity.EXTRA_ADDRESS, conversation.address)
            putExtra(ThreadActivity.EXTRA_DISPLAY_NAME, conversation.displayName)
        }
        startActivity(intent)
    }

    private fun confirmUnhide(conversation: ConversationSummary) {
        val options = arrayOf(
            getString(R.string.menu_unhide_chat),
            getString(R.string.schedule_auto_unhide)
        )
        AlertDialog.Builder(this)
            .setTitle(conversation.displayName)
            .setItems(options) { _, index ->
                if (index == 0) {
                    lifecycleScope.launch {
                        OriginDatabase.getInstance(this@LockUnlockActivity).threadLockDao()
                            .setHidden(conversation.threadId, false)
                        showVault()
                    }
                } else {
                    showScheduleUnhideDialog(conversation)
                }
            }
            .show()
    }

    private fun showScheduleUnhideDialog(conversation: ConversationSummary) {
        val options = arrayOf(
            getString(R.string.schedule_1_hour),
            getString(R.string.schedule_6_hours),
            getString(R.string.schedule_24_hours),
            getString(R.string.schedule_7_days)
        )
        val hoursValues = longArrayOf(1, 6, 24, 24 * 7)

        AlertDialog.Builder(this)
            .setTitle(R.string.schedule_auto_unhide)
            .setItems(options) { _, index ->
                val unhideAt = System.currentTimeMillis() + hoursValues[index] * 60 * 60 * 1000L
                lifecycleScope.launch {
                    OriginDatabase.getInstance(this@LockUnlockActivity).threadLockDao()
                        .setAutoUnhideAt(conversation.threadId, unhideAt)
                    android.widget.Toast.makeText(
                        this@LockUnlockActivity,
                        getString(R.string.schedule_auto_unhide_confirmed),
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .show()
    }

    companion object {
        const val EXTRA_THREAD_ID = "extra_thread_id"
        const val EXTRA_ADDRESS = "extra_address"
        const val EXTRA_DISPLAY_NAME = "extra_display_name"
        const val EXTRA_UNLOCK_INTENT = "extra_unlock_intent"

        const val INTENT_OPEN_THREAD = "open_thread"
        const val INTENT_REMOVE_LOCK = "remove_lock"
        const val INTENT_OPEN_VAULT = "open_vault"
    }
}
