package com.meharenterprises.originsms.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.RemoteInput;
import com.meharenterprises.originsms.core.SmsRepository;
import com.meharenterprises.originsms.services.NotificationHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;

/* compiled from: BootCompletedReceiver.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lcom/meharenterprises/originsms/receivers/NotificationActionReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes4.dex */
public final class NotificationActionReceiver extends BroadcastReceiver {
    public static final String ACTION_MARK_READ = "com.meharenterprises.originsms.MARK_READ";
    public static final String ACTION_QUICK_REPLY = "com.meharenterprises.originsms.QUICK_REPLY";
    public static final String EXTRA_ADDRESS = "extra_address";
    public static final String EXTRA_THREAD_ID = "extra_thread_id";
    public static final String KEY_QUICK_REPLY = "key_quick_reply";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Object runBlocking$default;
        Bundle resultsFromIntent;
        CharSequence charSequence;
        String replyText;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        String action = intent.getAction();
        if (action != null) {
            switch (action.hashCode()) {
                case -1483991171:
                    if (action.equals(ACTION_MARK_READ)) {
                        long threadId = intent.getLongExtra("extra_thread_id", -1L);
                        if (threadId != -1) {
                            SmsRepository repo = new SmsRepository(context);
                            runBlocking$default = BuildersKt__BuildersKt.runBlocking$default(null, new NotificationActionReceiver$onReceive$1$1(repo, threadId, null), 1, null);
                            ((Number) runBlocking$default).intValue();
                        }
                        new NotificationHelper(context).cancel(threadId);
                        return;
                    }
                    return;
                case -575793075:
                    if (action.equals(ACTION_QUICK_REPLY)) {
                        long threadId2 = intent.getLongExtra("extra_thread_id", -1L);
                        String address = intent.getStringExtra("extra_address");
                        if (address == null || (resultsFromIntent = RemoteInput.getResultsFromIntent(intent)) == null || (charSequence = resultsFromIntent.getCharSequence(KEY_QUICK_REPLY)) == null || (replyText = charSequence.toString()) == null) {
                            return;
                        }
                        SmsRepository.sendSms$default(new SmsRepository(context), address, replyText, threadId2 != -1 ? Long.valueOf(threadId2) : null, null, 8, null);
                        new NotificationHelper(context).cancel(threadId2);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
