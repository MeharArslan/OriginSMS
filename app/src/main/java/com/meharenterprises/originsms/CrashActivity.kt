package com.meharenterprises.originsms

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

/**
 * Shown by the global uncaught-exception handler so crashes are visible
 * on-device (no logcat access needed). Runs in its own process so it
 * survives the crashing process being killed.
 */
class CrashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val trace = intent.getStringExtra(EXTRA_TRACE) ?: "No trace"

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#FFF3F3"))
            setPadding(32, 64, 32, 32)
        }

        root.addView(TextView(this).apply {
            text = "OriginSMS Crash Report"
            textSize = 20f
            setTextColor(Color.parseColor("#B00020"))
        })

        root.addView(Button(this).apply {
            text = "Copy Full Trace"
            setOnClickListener {
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cm.setPrimaryClip(ClipData.newPlainText("crash", trace))
                Toast.makeText(this@CrashActivity, "Copied", Toast.LENGTH_SHORT).show()
            }
        })

        val scroll = ScrollView(this)
        scroll.addView(TextView(this).apply {
            text = trace
            textSize = 12f
            setTextColor(Color.parseColor("#333333"))
            setTextIsSelectable(true)
        })
        root.addView(scroll, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ))

        setContentView(root)
    }

    companion object {
        const val EXTRA_TRACE = "extra_trace"
    }
}
