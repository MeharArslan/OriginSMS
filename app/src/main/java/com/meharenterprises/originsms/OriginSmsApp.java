package com.meharenterprises.originsms;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import com.meharenterprises.originsms.data.db.OriginDatabase;
import com.meharenterprises.originsms.ui.ThemePreferenceManager;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

/* compiled from: OriginSmsApp.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0010H\u0002J\b\u0010\u0012\u001a\u00020\u0010H\u0016J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006\u0017"}, d2 = {"Lcom/meharenterprises/originsms/OriginSmsApp;", "Landroid/app/Application;", "()V", "activeThreadId", "", "getActiveThreadId", "()J", "setActiveThreadId", "(J)V", "database", "Lcom/meharenterprises/originsms/data/db/OriginDatabase;", "getDatabase", "()Lcom/meharenterprises/originsms/data/db/OriginDatabase;", "database$delegate", "Lkotlin/Lazy;", "createNotificationChannels", "", "installCrashLogger", "onCreate", "writeCrashToFile", "throwable", "", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class OriginSmsApp extends Application {
    public static final String CHANNEL_MESSAGES = "channel_messages";

    /* renamed from: database$delegate, reason: from kotlin metadata */
    private final Lazy database = LazyKt.lazy(new Function0<OriginDatabase>() { // from class: com.meharenterprises.originsms.OriginSmsApp$database$2
        /* JADX INFO: Access modifiers changed from: package-private */
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final OriginDatabase invoke() {
            return OriginDatabase.INSTANCE.getInstance(OriginSmsApp.this);
        }
    });
    private long activeThreadId = -1;

    public final OriginDatabase getDatabase() {
        return (OriginDatabase) this.database.getValue();
    }

    public final long getActiveThreadId() {
        return this.activeThreadId;
    }

    public final void setActiveThreadId(long j) {
        this.activeThreadId = j;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        new ThemePreferenceManager(this).applyStoredMode();
        createNotificationChannels();
        installCrashLogger();
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new OriginSmsApp$onCreate$1(this, null), 2, null);
    }

    private final void createNotificationChannels() {
        NotificationManager manager = (NotificationManager) getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel(CHANNEL_MESSAGES, getString(R.string.notif_channel_messages), 4);
        channel.setDescription(getString(R.string.notif_channel_messages_desc));
        channel.enableVibration(true);
        manager.createNotificationChannel(channel);
    }

    private final void installCrashLogger() {
        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: com.meharenterprises.originsms.OriginSmsApp$$ExternalSyntheticLambda0
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final void uncaughtException(Thread thread, Throwable th) {
                OriginSmsApp.installCrashLogger$lambda$1(OriginSmsApp.this, defaultHandler, thread, th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void installCrashLogger$lambda$1(OriginSmsApp this$0, Thread.UncaughtExceptionHandler $defaultHandler, Thread thread, Throwable throwable) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            Intrinsics.checkNotNull(throwable);
            this$0.writeCrashToFile(throwable);
        } catch (Exception e) {
        }
        if ($defaultHandler != null) {
            $defaultHandler.uncaughtException(thread, throwable);
        }
    }

    private final void writeCrashToFile(Throwable throwable) {
        OutputStream openOutputStream;
        StringWriter stackTraceWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stackTraceWriter));
        String crashText = stackTraceWriter.toString();
        Intrinsics.checkNotNullExpressionValue(crashText, "toString(...)");
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US).format(new Date());
        String fileName = "OriginSMS_crash_" + timestamp + ".txt";
        File privateDir = getExternalFilesDir(null);
        if (privateDir == null) {
            privateDir = getFilesDir();
        }
        FilesKt.writeText$default(new File(privateDir, fileName), crashText, null, 2, null);
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                ContentValues values = new ContentValues();
                values.put("_display_name", fileName);
                values.put("mime_type", "text/plain");
                Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri != null && (openOutputStream = getContentResolver().openOutputStream(uri)) != null) {
                    OutputStream outputStream = openOutputStream;
                    try {
                        OutputStream it = outputStream;
                        byte[] bytes = crashText.getBytes(Charsets.UTF_8);
                        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                        it.write(bytes);
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(outputStream, null);
                        return;
                    } finally {
                    }
                }
                return;
            }
            File legacyDownloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            FilesKt.writeText$default(new File(legacyDownloads, fileName), crashText, null, 2, null);
        } catch (Exception e) {
        }
    }
}
