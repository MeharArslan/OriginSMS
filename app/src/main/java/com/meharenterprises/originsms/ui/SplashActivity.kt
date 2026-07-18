package com.meharenterprises.originsms.ui

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.ui.conversations.ConversationListActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.imgSplashLogo)
        val avd = logo.drawable as? AnimatedVectorDrawable
        avd?.start()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, ConversationListActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 1500L)
    }
}
