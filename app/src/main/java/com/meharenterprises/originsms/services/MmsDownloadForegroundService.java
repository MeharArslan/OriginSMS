package com.meharenterprises.originsms.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import com.meharenterprises.originsms.OriginSmsApp;
import com.meharenterprises.originsms.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MmsDownloadForegroundService.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\"\u0010\t\u001a\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0016¨\u0006\u000e"}, d2 = {"Lcom/meharenterprises/originsms/services/MmsDownloadForegroundService;", "Landroid/app/Service;", "()V", "buildProgressNotification", "Landroid/app/Notification;", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onStartCommand", "", "flags", "startId", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes5.dex */
public final class MmsDownloadForegroundService extends Service {
    public static final String EXTRA_PUSH_DATA = "extra_push_data";
    private static final int NOTIF_ID = 9001;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIF_ID, buildProgressNotification());
        if (intent != null) {
            intent.getByteArrayExtra(EXTRA_PUSH_DATA);
        }
        stopForeground(1);
        stopSelf(startId);
        return 2;
    }

    private final Notification buildProgressNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NotificationManager.class);
        if (manager.getNotificationChannel(OriginSmsApp.CHANNEL_MESSAGES) == null) {
            manager.createNotificationChannel(new NotificationChannel(OriginSmsApp.CHANNEL_MESSAGES, getString(R.string.notif_channel_messages), 2));
        }
        Notification build = new NotificationCompat.Builder(this, OriginSmsApp.CHANNEL_MESSAGES).setSmallIcon(R.drawable.ic_notification).setContentTitle(getString(R.string.app_name)).setProgress(0, 0, true).setOngoing(true).build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        return build;
    }
}
