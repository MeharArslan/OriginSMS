package com.meharenterprises.originsms.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.meharenterprises.originsms.data.db.OriginDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AutoUnhideReceiver : BroadcastReceiver() {
    companion object {
        const val ACTION_AUTO_UNHIDE = "com.meharenterprises.originsms.ACTION_AUTO_UNHIDE"
        const val EXTRA_THREAD_ID = "thread_id"
        const val ACTION_UNHIDE_DONE = "com.meharenterprises.originsms.UNHIDE_DONE"

        fun schedule(context: Context, threadId: Long, atMillis: Long) {
            val am = context.getSystemService(android.app.AlarmManager::class.java)
            val pi = pendingIntent(context, threadId)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
                am.setExactAndAllowWhileIdle(android.app.AlarmManager.RTC_WAKEUP, atMillis, pi)
            else am.setExact(android.app.AlarmManager.RTC_WAKEUP, atMillis, pi)
        }

        fun cancel(context: Context, threadId: Long) =
            context.getSystemService(android.app.AlarmManager::class.java).cancel(pendingIntent(context, threadId))

        private fun pendingIntent(context: Context, threadId: Long): android.app.PendingIntent {
            val i = Intent(context, AutoUnhideReceiver::class.java).apply {
                action = ACTION_AUTO_UNHIDE
                putExtra(EXTRA_THREAD_ID, threadId)
            }
            return android.app.PendingIntent.getBroadcast(
                context, threadId.toInt(), i,
                android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
            )
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ACTION_AUTO_UNHIDE) return
        val threadId = intent.getLongExtra(EXTRA_THREAD_ID, -1L)
        if (threadId < 0L) return
        val pending = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val dao = OriginDatabase.getInstance(context).threadLockDao()
                dao.setHidden(threadId, false)
                dao.setAutoUnhideAt(threadId, 0L)
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent(ACTION_UNHIDE_DONE).putExtra(EXTRA_THREAD_ID, threadId))
            } finally { pending.finish() }
        }
    }
}
