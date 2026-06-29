package com.meharenterprises.originsms.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meharenterprises.originsms.ui.conversations.ConversationListActivity

/**
 * Minimal launcher entry point. Kept separate from ConversationListActivity
 * so the launcher activity's theme (solid brand color, no UI) can differ from
 * the main screen's theme without any visible flash between them.
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, ConversationListActivity::class.java))
        finish()
    }
}
