package com.meharenterprises.originsms.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;
import androidx.core.graphics.drawable.IconCompat;
import com.meharenterprises.originsms.OriginSmsApp;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ContactsHelper;
import com.meharenterprises.originsms.data.db.ThreadLockEntity;
import com.meharenterprises.originsms.lock.LockUnlockActivity;
import com.meharenterprises.originsms.receivers.NotificationActionReceiver;
import com.meharenterprises.originsms.ui.thread.ThreadActivity;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.ArrayDeque;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;

/* compiled from: NotificationHelper.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u000bJ\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0002J&\u0010\u0013\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/meharenterprises/originsms/services/NotificationHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "manager", "Landroid/app/NotificationManager;", "kotlin.jvm.PlatformType", "buildMarkReadAction", "Landroidx/core/app/NotificationCompat$Action;", "threadId", "", "buildQuickReplyAction", "address", "", "cancel", "", "loadContactAvatar", "Landroid/graphics/Bitmap;", "showIncomingMessageNotification", "displayName", "body", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class NotificationHelper {
    private static final Map<Long, ArrayDeque<Triple<String, Long, String>>> messageHistory = new LinkedHashMap();
    private final Context context;
    private final NotificationManager manager;

    public NotificationHelper(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.manager = (NotificationManager) this.context.getSystemService(NotificationManager.class);
    }

    public final void showIncomingMessageNotification(String address, String displayName, String body, long threadId) {
        Object runBlocking$default;
        ArrayDeque arrayDeque;
        Intent $this$showIncomingMessageNotification_u24lambda_u241;
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        Intrinsics.checkNotNullParameter(body, "body");
        runBlocking$default = BuildersKt__BuildersKt.runBlocking$default(null, new NotificationHelper$showIncomingMessageNotification$lockState$1(this, threadId, null), 1, null);
        ThreadLockEntity lockState = (ThreadLockEntity) runBlocking$default;
        boolean isLocked = lockState != null && lockState.isLocked();
        boolean isHidden = lockState != null && lockState.isHidden();
        if (isHidden) {
            return;
        }
        Context applicationContext = this.context.getApplicationContext();
        OriginSmsApp app = applicationContext instanceof OriginSmsApp ? (OriginSmsApp) applicationContext : null;
        if (app != null && app.getActiveThreadId() == threadId) {
            return;
        }
        long muteUntil = lockState != null ? lockState.getMuteUntilMillis() : 0L;
        boolean isMuted = (lockState != null && lockState.isMuted()) && (muteUntil == -1 || muteUntil > System.currentTimeMillis());
        if (isMuted) {
            return;
        }
        String title = isLocked ? this.context.getString(R.string.app_name) : displayName;
        Intrinsics.checkNotNull(title);
        String content = isLocked ? this.context.getString(R.string.notif_locked_chat_content) : body;
        Intrinsics.checkNotNull(content);
        String senderName = isLocked ? this.context.getString(R.string.app_name) : displayName;
        Intrinsics.checkNotNull(senderName);
        Map $this$getOrPut$iv = messageHistory;
        Long valueOf = Long.valueOf(threadId);
        ArrayDeque<Triple<String, Long, String>> arrayDeque2 = $this$getOrPut$iv.get(valueOf);
        if (arrayDeque2 == null) {
            arrayDeque = new ArrayDeque();
            $this$getOrPut$iv.put(valueOf, arrayDeque);
        } else {
            arrayDeque = arrayDeque2;
        }
        ArrayDeque key$iv = arrayDeque;
        ArrayDeque history = key$iv;
        history.addLast(new Triple<>(content, Long.valueOf(System.currentTimeMillis()), senderName));
        while (history.size() > 10) {
            history.removeFirst();
        }
        if (isLocked) {
            $this$showIncomingMessageNotification_u24lambda_u241 = new Intent(this.context, (Class<?>) LockUnlockActivity.class);
            $this$showIncomingMessageNotification_u24lambda_u241.putExtra("extra_thread_id", threadId);
            $this$showIncomingMessageNotification_u24lambda_u241.putExtra("extra_address", address);
            $this$showIncomingMessageNotification_u24lambda_u241.setFlags(335544320);
        } else {
            $this$showIncomingMessageNotification_u24lambda_u241 = new Intent(this.context, (Class<?>) ThreadActivity.class);
            $this$showIncomingMessageNotification_u24lambda_u241.putExtra("extra_thread_id", threadId);
            $this$showIncomingMessageNotification_u24lambda_u241.putExtra("extra_address", address);
            $this$showIncomingMessageNotification_u24lambda_u241.putExtra("extra_display_name", displayName);
            $this$showIncomingMessageNotification_u24lambda_u241.setFlags(335544320);
        }
        Intent contentIntent = $this$showIncomingMessageNotification_u24lambda_u241;
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this.context, (int) threadId, contentIntent, 201326592);
        Bitmap contactAvatar = loadContactAvatar(address);
        Person.Builder $this$showIncomingMessageNotification_u24lambda_u243 = new Person.Builder().setName(senderName);
        if (contactAvatar != null) {
            $this$showIncomingMessageNotification_u24lambda_u243.setIcon(IconCompat.createWithBitmap(contactAvatar));
        }
        Person senderPerson = $this$showIncomingMessageNotification_u24lambda_u243.build();
        Intrinsics.checkNotNullExpressionValue(senderPerson, "build(...)");
        Person mePerson = new Person.Builder().setName("You").build();
        Intrinsics.checkNotNullExpressionValue(mePerson, "build(...)");
        NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle(mePerson);
        messagingStyle.setGroupConversation(false);
        ArrayDeque $this$forEach$iv = history;
        for (Object element$iv : $this$forEach$iv) {
            Triple<String, Long, String> triple = (Triple) element$iv;
            String msg = triple.component1();
            Iterable $this$forEach$iv2 = $this$forEach$iv;
            long ts = triple.component2().longValue();
            messagingStyle.addMessage(new NotificationCompat.MessagingStyle.Message(msg, ts, senderPerson));
            mePerson = mePerson;
            isMuted = isMuted;
            $this$forEach$iv = $this$forEach$iv2;
            contentIntent = contentIntent;
        }
        int unreadCount = history.size();
        String summaryText = unreadCount > 1 ? unreadCount + " unread messages" : null;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, OriginSmsApp.CHANNEL_MESSAGES).setSmallIcon(R.drawable.ic_notification).setContentTitle(title).setContentText(content).setStyle(messagingStyle).setSubText(summaryText).setNumber(unreadCount).setAutoCancel(true).setContentIntent(contentPendingIntent).setPriority(1).setCategory(NotificationCompat.CATEGORY_MESSAGE).setGroup("thread_" + threadId).setOnlyAlertOnce(false);
        Intrinsics.checkNotNullExpressionValue(builder, "setOnlyAlertOnce(...)");
        if (contactAvatar != null) {
            builder.setLargeIcon(contactAvatar);
        }
        if (!isLocked) {
            builder.addAction(buildMarkReadAction(threadId));
            builder.addAction(buildQuickReplyAction(threadId, address));
        }
        this.manager.notify((int) threadId, builder.build());
    }

    private final Bitmap loadContactAvatar(String address) {
        InputStream openInputStream;
        try {
            ContactsHelper.ContactInfo contact = new ContactsHelper(this.context).resolve(address);
            String photoUri = contact.getPhotoUri();
            if (photoUri == null || (openInputStream = this.context.getContentResolver().openInputStream(Uri.parse(photoUri))) == null) {
                return null;
            }
            InputStream inputStream = openInputStream;
            try {
                InputStream stream = inputStream;
                Bitmap decodeStream = BitmapFactory.decodeStream(stream);
                CloseableKt.closeFinally(inputStream, null);
                return decodeStream;
            } finally {
            }
        } catch (Exception e) {
            return null;
        }
    }

    private final NotificationCompat.Action buildMarkReadAction(long threadId) {
        Intent intent = new Intent(this.context, (Class<?>) NotificationActionReceiver.class);
        intent.setAction(NotificationActionReceiver.ACTION_MARK_READ);
        intent.putExtra("extra_thread_id", threadId);
        PendingIntent pi = PendingIntent.getBroadcast(this.context, ((int) threadId) + 1, intent, 201326592);
        NotificationCompat.Action build = new NotificationCompat.Action.Builder(0, this.context.getString(R.string.notif_action_mark_read), pi).build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        return build;
    }

    private final NotificationCompat.Action buildQuickReplyAction(long threadId, String address) {
        RemoteInput remoteInput = new RemoteInput.Builder(NotificationActionReceiver.KEY_QUICK_REPLY).setLabel(this.context.getString(R.string.hint_type_message)).build();
        Intrinsics.checkNotNullExpressionValue(remoteInput, "build(...)");
        Intent intent = new Intent(this.context, (Class<?>) NotificationActionReceiver.class);
        intent.setAction(NotificationActionReceiver.ACTION_QUICK_REPLY);
        intent.putExtra("extra_thread_id", threadId);
        intent.putExtra("extra_address", address);
        PendingIntent pi = PendingIntent.getBroadcast(this.context, ((int) threadId) + 2, intent, 167772160);
        NotificationCompat.Action build = new NotificationCompat.Action.Builder(0, this.context.getString(R.string.notif_action_reply), pi).addRemoteInput(remoteInput).build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        return build;
    }

    public final void cancel(long threadId) {
        messageHistory.remove(Long.valueOf(threadId));
        this.manager.cancel((int) threadId);
    }
}
