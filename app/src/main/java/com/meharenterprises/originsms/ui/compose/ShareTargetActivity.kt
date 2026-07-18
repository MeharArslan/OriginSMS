package com.meharenterprises.originsms.ui.compose

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.meharenterprises.originsms.core.ContactsHelper
import com.meharenterprises.originsms.ui.thread.ThreadActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Transparent routing activity for android.intent.action.SENDTO / SEND intents
 * targeting sms/smsto/mms/mmsto schemes, and plain text shares ("Share via...").
 * Required for OriginSMS to behave correctly as a full default SMS app — other
 * apps expect a messaging app to handle these intents.
 */
class ShareTargetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val destinationNumber = extractDestinationNumber()
        val sharedText = extractSharedText()

        if (destinationNumber.isNullOrBlank()) {
            val intent = Intent(this, ComposeActivity::class.java).apply {
                if (!sharedText.isNullOrBlank()) putExtra(ComposeActivity.EXTRA_PREFILL_BODY, sharedText)
            }
            startActivity(intent)
            finish()
            return
        }

        lifecycleScope.launch {
            val threadId = withContext(Dispatchers.IO) {
                Telephony.Threads.getOrCreateThreadId(this@ShareTargetActivity, destinationNumber)
            }
            val displayName = withContext(Dispatchers.IO) {
                ContactsHelper(this@ShareTargetActivity).resolve(destinationNumber).displayName
            }
            val intent = Intent(this@ShareTargetActivity, ThreadActivity::class.java).apply {
                putExtra(ThreadActivity.EXTRA_THREAD_ID, threadId)
                putExtra(ThreadActivity.EXTRA_ADDRESS, destinationNumber)
                putExtra(ThreadActivity.EXTRA_DISPLAY_NAME, displayName)
                if (!sharedText.isNullOrBlank()) putExtra(ThreadActivity.EXTRA_PREFILL_BODY, sharedText)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun extractDestinationNumber(): String? {
        val data = intent.data ?: return null
        val schemeSpecific = data.schemeSpecificPart ?: return null
        return schemeSpecific.split(";").firstOrNull()?.takeIf { it.isNotBlank() }
    }

    private fun extractSharedText(): String? {
        return when (intent.action) {
            Intent.ACTION_SEND -> intent.getStringExtra(Intent.EXTRA_TEXT)
            Intent.ACTION_SENDTO -> intent.getStringExtra(Intent.EXTRA_TEXT)
            else -> null
        }
    }
}
