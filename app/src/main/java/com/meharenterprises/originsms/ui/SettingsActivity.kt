package com.meharenterprises.originsms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.android.widget.Toolbar
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.lock.LockSetupActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<android.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // Chat Lock
        findViewById<android.view.View>(R.id.rowChatLock).setOnClickListener {
            startActivity(Intent(this, LockSetupActivity::class.java))
        }

        // Blocked Numbers
        findViewById<android.view.View>(R.id.rowBlockedNumbers).setOnClickListener {
            startActivity(Intent(this, BlockedNumbersActivity::class.java))
        }

        // Default App
        findViewById<android.view.View>(R.id.rowDefaultApp).setOnClickListener {
            val roleManager = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                getSystemService(android.app.role.RoleManager::class.java)
            } else null
            if (roleManager != null && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                val intent = roleManager.createRequestRoleIntent(android.app.role.RoleManager.ROLE_SMS)
                startActivity(intent)
            } else {
                val intent = Intent(android.provider.Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
                intent.putExtra(android.provider.Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
                startActivity(intent)
            }
        }

        // Biometric
        val switchBiometric = findViewById<android.widget.Switch?>(R.id.switchBiometric)
        val prefs = getSharedPreferences("lock_prefs", MODE_PRIVATE)
        switchBiometric?.isChecked = prefs.getBoolean("biometric_enabled", false)
        switchBiometric?.setOnCheckedChangeListener { _, checked ->
            prefs.edit().putBoolean("biometric_enabled", checked).apply()
        }
        findViewById<android.view.View>(R.id.rowBiometric).setOnClickListener {
            switchBiometric?.toggle()
        }


    }
}
