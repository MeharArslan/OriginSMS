package com.meharenterprises.originsms.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import com.meharenterprises.originsms.core.SmsRepository;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: RespondViaMessageService.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J\"\u0010\t\u001a\u00020\n2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0016¨\u0006\r"}, d2 = {"Lcom/meharenterprises/originsms/services/RespondViaMessageService;", "Landroid/app/Service;", "()V", "handleRespondViaMessage", "", "intent", "Landroid/content/Intent;", "onBind", "Landroid/os/IBinder;", "onStartCommand", "", "flags", "startId", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class RespondViaMessageService extends Service {
    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            handleRespondViaMessage(intent);
        }
        stopSelf(startId);
        return 2;
    }

    private final void handleRespondViaMessage(Intent intent) {
        String body;
        String schemeSpecificPart;
        List split$default;
        String destination;
        Uri data = intent.getData();
        if (data == null || (body = intent.getStringExtra("android.intent.extra.TEXT")) == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null || (split$default = StringsKt.split$default((CharSequence) schemeSpecificPart, new String[]{";"}, false, 0, 6, (Object) null)) == null || (destination = (String) CollectionsKt.firstOrNull(split$default)) == null) {
            return;
        }
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        SmsRepository.sendSms$default(new SmsRepository(applicationContext), destination, body, null, null, 8, null);
    }
}
