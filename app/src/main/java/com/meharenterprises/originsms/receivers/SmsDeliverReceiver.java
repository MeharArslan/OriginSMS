package com.meharenterprises.originsms.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: SmsDeliverReceiver.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lcom/meharenterprises/originsms/receivers/SmsDeliverReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "extractThreadId", "", "uri", "Landroid/net/Uri;", "context", "Landroid/content/Context;", "onReceive", "", "intent", "Landroid/content/Intent;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes4.dex */
public final class SmsDeliverReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String sender;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        if (Intrinsics.areEqual(intent.getAction(), "android.provider.Telephony.SMS_DELIVER")) {
            SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            Intrinsics.checkNotNull(messages);
            if ((messages.length == 0) || (sender = messages[0].getOriginatingAddress()) == null) {
                return;
            }
            long timestamp = messages[0].getTimestampMillis();
            String fullBody = ArraysKt.joinToString$default(messages, "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1<SmsMessage, CharSequence>() { // from class: com.meharenterprises.originsms.receivers.SmsDeliverReceiver$onReceive$fullBody$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(SmsMessage it) {
                    String messageBody = it.getMessageBody();
                    if (messageBody == null) {
                        messageBody = "";
                    }
                    return messageBody;
                }
            }, 30, (Object) null);
            BroadcastReceiver.PendingResult pendingResult = goAsync();
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new SmsDeliverReceiver$onReceive$1(sender, context, fullBody, this, pendingResult, timestamp, null), 3, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long extractThreadId(Uri uri, Context context) {
        Cursor query;
        int idx;
        if (uri != null && (query = context.getContentResolver().query(uri, new String[]{"thread_id"}, null, null, null)) != null) {
            Cursor cursor = query;
            try {
                Cursor it = cursor;
                if (it.moveToFirst() && (idx = it.getColumnIndex("thread_id")) >= 0) {
                    long j = it.getLong(idx);
                    CloseableKt.closeFinally(cursor, null);
                    return j;
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(cursor, null);
            } finally {
            }
        }
        return -1L;
    }
}
