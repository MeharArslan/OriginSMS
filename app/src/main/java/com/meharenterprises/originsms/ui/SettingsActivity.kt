package com.meharenterprises.originsms.ui

import android.app.role.RoleManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.lock.LockSetupActivity
import com.meharenterprises.originsms.lock.LockUnlockActivity
import com.meharenterprises.originsms.lock.PinManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsActivity : AppCompatActivity() {

    private lateinit var pinManager: PinManager
    private lateinit var themeManager: ThemePreferenceManager
    private lateinit var switchBiometric: Switch
    private lateinit var txtChatLockStatus: TextView
    private lateinit var txtDefaultAppStatus: TextView
    private lateinit var txtThemeStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        pinManager = PinManager(this)
        themeManager = ThemePreferenceManager(this)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setDisplayShowTitleEnabled(false)

        switchBiometric = findViewById(R.id.switchBiometric)
        txtChatLockStatus = findViewById(R.id.txtChatLockStatus)
        txtDefaultAppStatus = findViewById(R.id.txtDefaultAppStatus)
        txtThemeStatus = findViewById(R.id.txtThemeStatus)

        setupChatLockRow()
        setupBiometricRow()
        setupResetPinRow()
        setupDefaultAppRow()
        setupThemeRow()
        setupAutoHideTimerRow()
        setupBlockedNumbersRow()
    }

    override fun onResume() {
        super.onResume()
        refreshStatuses()
    }

    private fun refreshStatuses() {
        txtChatLockStatus.text = if (pinManager.hasPinConfigured()) "Enabled" else "Not set up"
        txtDefaultAppStatus.text = if (isDefaultSmsApp()) "Yes" else "No — tap to set"
        txtThemeStatus.text = themeLabel(themeManager.getCurrentMode())

        val biometricAvailable = BiometricManager.from(this)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
            BiometricManager.BIOMETRIC_SUCCESS
        switchBiometric.isEnabled = biometricAvailable && pinManager.hasPinConfigured()
        switchBiometric.isChecked = pinManager.isBiometricEnabled() && biometricAvailable
    }

    private fun themeLabel(mode: ThemePreferenceManager.ThemeMode): String = when (mode) {
        ThemePreferenceManager.ThemeMode.LIGHT -> "Light"
        ThemePreferenceManager.ThemeMode.DARK -> "Dark"
        ThemePreferenceManager.ThemeMode.SYSTEM -> "System default"
    }

    private fun setupThemeRow() {
        findViewById<android.view.View>(R.id.rowTheme).setOnClickListener {
            val options = arrayOf("Light", "Dark", "System default")
            val modes = arrayOf(
                ThemePreferenceManager.ThemeMode.LIGHT,
                ThemePreferenceManager.ThemeMode.DARK,
                ThemePreferenceManager.ThemeMode.SYSTEM
            )
            val currentIndex = modes.indexOf(themeManager.getCurrentMode())

            AlertDialog.Builder(this)
                .setTitle(R.string.settings_theme)
                .setSingleChoiceItems(options, currentIndex) { dialog, index ->
                    themeManager.setMode(modes[index])
                    txtThemeStatus.text = options[index]
                    dialog.dismiss()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
    }

    private fun setupChatLockRow() {
        findViewById<android.view.View>(R.id.rowChatLock).setOnClickListener {
            if (!pinManager.hasPinConfigured()) {
                startActivity(Intent(this, LockSetupActivity::class.java))
            } else {
                AlertDialog.Builder(this)
                    .setTitle(R.string.settings_chat_lock)
                    .setMessage("Chat lock is active. You can change your PIN from \"Forgot PIN?\" below.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
            }
        }
    }

    private fun setupBiometricRow() {
        findViewById<android.view.View>(R.id.rowBiometric).setOnClickListener {
            if (switchBiometric.isEnabled) {
                switchBiometric.isChecked = !switchBiometric.isChecked
            }
        }
        switchBiometric.setOnCheckedChangeListener { _, isChecked ->
            pinManager.setBiometricEnabled(isChecked)
        }
    }

    private fun setupResetPinRow() {
        findViewById<android.view.View>(R.id.rowResetPin).setOnClickListener {
            if (!pinManager.hasPinConfigured()) {
                startActivity(Intent(this, LockSetupActivity::class.java))
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle(R.string.lock_forgot_pin)
                .setMessage(R.string.lock_reset_warning)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    val intent = Intent(this, LockUnlockActivity::class.java).apply {
                        putExtra(LockUnlockActivity.EXTRA_UNLOCK_INTENT, LockUnlockActivity.INTENT_REMOVE_LOCK)
                        putExtra(LockUnlockActivity.EXTRA_THREAD_ID, RESET_PIN_SENTINEL_THREAD_ID)
                    }
                    startActivityForResult(intent, REQUEST_RESET_PIN)
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RESET_PIN && resultCode == RESULT_OK) {
            pinManager.clearPin()
            startActivity(Intent(this, LockSetupActivity::class.java))
            refreshStatuses()
        }
    }

    private val defaultAppRoleLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) {
        refreshStatuses()
    }

    private fun setupDefaultAppRow() {
        findViewById<android.view.View>(R.id.rowDefaultApp).setOnClickListener {
            requestDefaultSmsRole()
        }
    }

    private fun isDefaultSmsApp(): Boolean = Telephony.Sms.getDefaultSmsPackage(this) == packageName

    private fun requestDefaultSmsRole() {
        if (isDefaultSmsApp()) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(RoleManager::class.java)
            if (roleManager.isRoleAvailable(RoleManager.ROLE_SMS)) {
                defaultAppRoleLauncher.launch(roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS))
            }
        } else {
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT).apply {
                putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
            }
            defaultAppRoleLauncher.launch(intent)
        }
    }

    private fun setupAutoHideTimerRow() {
        findViewById<android.view.View>(R.id.rowAutoHideTimer).setOnClickListener {
            showAutoHideContactPicker()
        }
    }

    private fun showAutoHideContactPicker() {
        lifecycleScope.launch {
            val conversations = withContext(Dispatchers.IO) {
                com.meharenterprises.originsms.core.SmsRepository(this@SettingsActivity).getConversations()
            }
            if (conversations.isEmpty()) {
                AlertDialog.Builder(this@SettingsActivity)
                    .setTitle(R.string.auto_hide_timer_title)
                    .setMessage("No chats found. Make sure OriginSMS is set as your default SMS app and you have existing conversations.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
                return@launch
            }
            val names = conversations.map { it.displayName }.toTypedArray()
            AlertDialog.Builder(this@SettingsActivity)
                .setTitle(R.string.auto_hide_timer_title)
                .setItems(names) { _, index ->
                    showDailyHideTimePicker(conversations[index])
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
    }

    private fun showDailyHideTimePicker(conversation: com.meharenterprises.originsms.core.ConversationSummary) {
        val now = java.util.Calendar.getInstance()
        val timePicker = android.app.TimePickerDialog(
            this,
            { _, hour, minute ->
                lifecycleScope.launch {
                    val dao = withContext(Dispatchers.IO) {
                        com.meharenterprises.originsms.data.db.OriginDatabase
                            .getInstance(this@SettingsActivity).threadLockDao()
                    }
                    val totalMinutes = hour * 60 + minute
                    withContext(Dispatchers.IO) {
                        val existing = dao.getForThread(conversation.threadId)
                        dao.upsert(
                            (existing ?: com.meharenterprises.originsms.data.db.ThreadLockEntity(
                                threadId = conversation.threadId
                            )).copy(dailyHideTimeMinutes = totalMinutes)
                        )
                    }
                    val timeStr = String.format("%02d:%02d", hour, minute)
                    android.widget.Toast.makeText(
                        this@SettingsActivity,
                        "${conversation.displayName} will auto-hide daily at $timeStr",
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                }
            },
            now.get(java.util.Calendar.HOUR_OF_DAY),
            now.get(java.util.Calendar.MINUTE),
            true
        )
        // Option to disable
        timePicker.setButton(
            android.app.AlertDialog.BUTTON_NEUTRAL, "Disable"
        ) { _, _ ->
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    com.meharenterprises.originsms.data.db.OriginDatabase
                        .getInstance(this@SettingsActivity)
                        .threadLockDao()
                        .setDailyHideTime(conversation.threadId, -1)
                }
                android.widget.Toast.makeText(
                    this@SettingsActivity,
                    getString(R.string.auto_hide_timer_disabled),
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        }
        timePicker.setTitle("Daily auto-hide time for ${conversation.displayName}")
        timePicker.show()
    }

    private fun setupBlockedNumbersRow() {
        findViewById<android.view.View>(R.id.rowBlockedNumbers).setOnClickListener {
            startActivity(Intent(this, BlockedNumbersActivity::class.java))
        }
    }

    companion object {
        private const val REQUEST_RESET_PIN = 1001
        const val RESET_PIN_SENTINEL_THREAD_ID = -2L
    }
}
