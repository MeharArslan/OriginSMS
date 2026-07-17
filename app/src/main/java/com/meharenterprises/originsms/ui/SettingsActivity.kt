package com.meharenterprises.originsms.ui

import android.app.role.RoleManager
import com.meharenterprises.originsms.connect.ui.splash.OriginConnectActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import com.google.android.material.appbar.MaterialToolbar
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.lock.LockSetupActivity
import com.meharenterprises.originsms.lock.LockUnlockActivity
import com.meharenterprises.originsms.lock.PinManager

class SettingsActivity : AppCompatActivity() {

    private lateinit var pinManager: PinManager
    private lateinit var switchBiometric: Switch
    private lateinit var txtChatLockStatus: TextView
    private lateinit var txtDefaultAppStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        pinManager = PinManager(this)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        switchBiometric = findViewById(R.id.switchBiometric)
        txtChatLockStatus = findViewById(R.id.txtChatLockStatus)
        txtDefaultAppStatus = findViewById(R.id.txtDefaultAppStatus)

        setupChatLockRow()
        setupBiometricRow()
        setupResetPinRow()
        setupDefaultAppRow()
        setupBlockedNumbersRow()
    }

    override fun onResume() {
        super.onResume()
        refreshStatuses()
    }

    private fun refreshStatuses() {
        txtChatLockStatus.text = if (pinManager.hasPinConfigured()) "Enabled" else "Not set up"
        txtDefaultAppStatus.text = if (isDefaultSmsApp()) "Yes" else "No — tap to set"

        val biometricAvailable = BiometricManager.from(this)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
            BiometricManager.BIOMETRIC_SUCCESS
        switchBiometric.isEnabled = biometricAvailable && pinManager.hasPinConfigured()
        switchBiometric.isChecked = pinManager.isBiometricEnabled() && biometricAvailable
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

    private fun setupDefaultAppRow() {
        findViewById<android.view.View>(R.id.rowDefaultApp).setOnClickListener {
            if (!isDefaultSmsApp()) {
                requestDefaultSmsRole()
            }
        }
    }

    private fun isDefaultSmsApp(): Boolean = Telephony.Sms.getDefaultSmsPackage(this) == packageName

    private fun requestDefaultSmsRole() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(RoleManager::class.java)
            if (roleManager.isRoleAvailable(RoleManager.ROLE_SMS) && !roleManager.isRoleHeld(RoleManager.ROLE_SMS)) {
                startActivity(roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS))
            }
        } else {
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT).apply {
                putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
            }
            startActivity(intent)
        }
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
        // Origin Connect - Online Mode
        findViewById<android.view.View?>(R.id.rowOnlineMode)?.setOnClickListener {
            startActivity(Intent(this, OriginConnectActivity::class.java))
        }
    }
}