package com.meharenterprises.originsms.lock

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import com.google.android.material.appbar.MaterialToolbar
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.data.db.OriginDatabase
import com.meharenterprises.originsms.data.db.ThreadLockEntity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * Two-step PIN creation: enter PIN, then confirm it. On success, persists the
 * hash via PinManager and — if a threadId was passed in (the user triggered
 * setup from a chat's "Lock chat" menu action before any PIN existed yet) —
 * immediately applies the lock (and optionally hide) to that thread so the
 * user doesn't have to repeat the action afterward.
 */
class LockSetupActivity : AppCompatActivity() {

    private lateinit var pinManager: PinManager
    private lateinit var editPin: EditText
    private lateinit var txtStepLabel: TextView
    private lateinit var txtError: TextView
    private lateinit var btnContinue: Button
    private lateinit var switchBiometric: Switch

    private var firstEntry: String? = null
    private var threadIdToLock: Long = -1L
    private var alsoHide: Boolean = false

    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_setup)

        pinManager = PinManager(this)
        threadIdToLock = intent.getLongExtra(EXTRA_THREAD_ID_TO_LOCK, -1L)
        alsoHide = intent.getBooleanExtra(EXTRA_ALSO_HIDE, false)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setDisplayShowTitleEnabled(false)

        editPin = findViewById(R.id.editPin)
        txtStepLabel = findViewById(R.id.txtStepLabel)
        txtError = findViewById(R.id.txtError)
        btnContinue = findViewById(R.id.btnContinue)
        switchBiometric = findViewById(R.id.switchBiometric)

        val biometricAvailable = BiometricManager.from(this)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
            BiometricManager.BIOMETRIC_SUCCESS
        switchBiometric.isEnabled = biometricAvailable
        if (!biometricAvailable) {
            switchBiometric.isChecked = false
        }

        btnContinue.setOnClickListener { onContinueClicked() }
    }

    private fun onContinueClicked() {
        val value = editPin.text?.toString().orEmpty()
        txtError.visibility = android.view.View.GONE

        if (value.length < 4) {
            showError(getString(R.string.lock_pin_too_short))
            return
        }

        if (firstEntry == null) {
            firstEntry = value
            txtStepLabel.text = getString(R.string.lock_confirm_pin)
            editPin.setText("")
        } else {
            if (value == firstEntry) {
                finalizeSetup(value)
            } else {
                showError(getString(R.string.lock_pin_mismatch))
                firstEntry = null
                txtStepLabel.text = getString(R.string.lock_create_pin)
                editPin.setText("")
            }
        }
    }

    private fun finalizeSetup(pin: String) {
        pinManager.setPin(pin)
        pinManager.setBiometricEnabled(switchBiometric.isChecked)

        if (threadIdToLock != -1L) {
            scope.launch {
                val database = OriginDatabase.getInstance(this@LockSetupActivity)
                database.threadLockDao().upsert(
                    ThreadLockEntity(
                        threadId = threadIdToLock,
                        isLocked = true,
                        isHidden = alsoHide,
                        lockedAtMillis = System.currentTimeMillis()
                    )
                )
                finish()
            }
        } else {
            finish()
        }
    }

    private fun showError(message: String) {
        txtError.text = message
        txtError.visibility = android.view.View.VISIBLE
    }

    companion object {
        const val EXTRA_THREAD_ID_TO_LOCK = "extra_thread_id_to_lock"
        const val EXTRA_ALSO_HIDE = "extra_also_hide"
    }
}
