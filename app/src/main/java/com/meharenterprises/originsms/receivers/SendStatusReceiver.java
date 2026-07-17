package com.meharenterprises.originsms.receivers;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.Telephony;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.exifinterface.media.ExifInterface;
import com.meharenterprises.originsms.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SendStatusReceiver.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lcom/meharenterprises/originsms/receivers/SendStatusReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "markMostRecentSentMessageFailed", "", "context", "Landroid/content/Context;", "onReceive", "intent", "Landroid/content/Intent;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes4.dex */
public final class SendStatusReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        switch (getResultCode()) {
            case -1:
            case 0:
            default:
                return;
            case 1:
            case 2:
            case 3:
            case 4:
                Toast.makeText(context, context.getString(R.string.status_failed), 0).show();
                markMostRecentSentMessageFailed(context);
                return;
        }
    }

    private final void markMostRecentSentMessageFailed(Context context) {
        int idIdx;
        Cursor query = context.getContentResolver().query(Telephony.Sms.CONTENT_URI, new String[]{"_id"}, "type = ?", new String[]{ExifInterface.GPS_MEASUREMENT_2D}, "date DESC LIMIT 1");
        if (query != null) {
            Cursor cursor = query;
            try {
                Cursor cursor2 = cursor;
                if (cursor2.moveToFirst() && (idIdx = cursor2.getColumnIndex("_id")) >= 0) {
                    long messageId = cursor2.getLong(idIdx);
                    ContentValues values = new ContentValues();
                    values.put("type", (Integer) 5);
                    context.getContentResolver().update(Telephony.Sms.CONTENT_URI, values, "_id = ?", new String[]{String.valueOf(messageId)});
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(cursor, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(cursor, th);
                    throw th2;
                }
            }
        }
    }
}
