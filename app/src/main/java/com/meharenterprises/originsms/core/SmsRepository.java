package com.meharenterprises.originsms.core;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.telephony.SmsManager;
import androidx.core.content.FileProvider;
import com.meharenterprises.originsms.core.SmsRepository;
import com.meharenterprises.originsms.data.db.OriginDatabase;
import com.meharenterprises.originsms.receivers.DeliveryStatusReceiver;
import com.meharenterprises.originsms.receivers.SendStatusReceiver;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: SmsRepository.kt */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 82\u00020\u0001:\u000389:B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@¢\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0010H\u0086@¢\u0006\u0002\u0010\u0011J\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0086@¢\u0006\u0002\u0010\u0018J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u0014\u001a\u00020\u0010H\u0002J\u001c\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00162\u0006\u0010\u0014\u001a\u00020\u0010H\u0086@¢\u0006\u0002\u0010\u0011J\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00162\u0006\u0010\u0014\u001a\u00020\u0010H\u0002J\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00162\u0006\u0010 \u001a\u00020\u0010H\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010 \u001a\u00020\u0010H\u0002J\u0016\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00162\u0006\u0010\u0014\u001a\u00020\u0010H\u0002J\u0016\u0010$\u001a\u00020\u00102\u0006\u0010%\u001a\u00020\"H\u0086@¢\u0006\u0002\u0010&J\u0010\u0010'\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0010H\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000eH\u0002J\u0010\u0010+\u001a\u00020)2\u0006\u0010,\u001a\u00020\u000eH\u0002J\u0016\u0010-\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0010H\u0086@¢\u0006\u0002\u0010\u0011J\u0016\u0010.\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0010H\u0086@¢\u0006\u0002\u0010\u0011J3\u0010/\u001a\u00020\u00132\u0006\u00100\u001a\u00020\"2\u0006\u00101\u001a\u00020\"2\f\u00102\u001a\b\u0012\u0004\u0012\u0002030\u00162\b\u0010\u0014\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u00104J1\u00105\u001a\u00020\u00132\u0006\u00100\u001a\u00020\"2\u0006\u00101\u001a\u00020\"2\b\u0010\u0014\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u00106\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u00107R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n¨\u0006;"}, d2 = {"Lcom/meharenterprises/originsms/core/SmsRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "contactsHelper", "Lcom/meharenterprises/originsms/core/ContactsHelper;", "database", "Lcom/meharenterprises/originsms/data/db/OriginDatabase;", "getDatabase", "()Lcom/meharenterprises/originsms/data/db/OriginDatabase;", "database$delegate", "Lkotlin/Lazy;", "deleteMessage", "", "messageId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteThread", "", "threadId", "getConversations", "", "Lcom/meharenterprises/originsms/core/ConversationSummary;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLatestMessageForThread", "Lcom/meharenterprises/originsms/core/SmsRepository$LatestMessage;", "getMessages", "Lcom/meharenterprises/originsms/core/Message;", "getMmsMessages", "getMmsParts", "Lcom/meharenterprises/originsms/core/SmsRepository$MmsPart;", "mmsId", "getMmsSenderAddress", "", "getSmsMessages", "getThreadIdForAddress", "address", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUnreadCountForThread", "mapMmsBox", "Lcom/meharenterprises/originsms/core/MessageBox;", "mmsBox", "mapSmsBox", "smsType", "markThreadRead", "markThreadUnread", "sendMms", "destinationAddress", "body", "attachmentUris", "Landroid/net/Uri;", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/Long;)V", "sendSms", "subscriptionId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Integer;)V", "Companion", "LatestMessage", "MmsPart", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class SmsRepository {
    public static final String ACTION_MMS_SENT = "com.meharenterprises.originsms.MMS_SENT";
    public static final String ACTION_SMS_DELIVERED = "com.meharenterprises.originsms.SMS_DELIVERED";
    public static final String ACTION_SMS_SENT = "com.meharenterprises.originsms.SMS_SENT";
    public static final String EXTRA_PART_INDEX = "part_index";
    private final ContactsHelper contactsHelper;
    private final Context context;

    /* renamed from: database$delegate, reason: from kotlin metadata */
    private final Lazy database;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final AtomicInteger pendingIntentRequestCodeCounter = new AtomicInteger(0);

    public SmsRepository(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.contactsHelper = new ContactsHelper(this.context);
        this.database = LazyKt.lazy(new Function0<OriginDatabase>() { // from class: com.meharenterprises.originsms.core.SmsRepository$database$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final OriginDatabase invoke() {
                Context context2;
                OriginDatabase.Companion companion = OriginDatabase.INSTANCE;
                context2 = SmsRepository.this.context;
                return companion.getInstance(context2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final OriginDatabase getDatabase() {
        return (OriginDatabase) this.database.getValue();
    }

    public final Object getThreadIdForAddress(String address, Continuation<? super Long> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new SmsRepository$getThreadIdForAddress$2(address, this, null), continuation);
    }

    public final Object getConversations(Continuation<? super List<ConversationSummary>> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new SmsRepository$getConversations$2(this, null), continuation);
    }

    /* compiled from: SmsRepository.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0017"}, d2 = {"Lcom/meharenterprises/originsms/core/SmsRepository$LatestMessage;", "", "address", "", "body", "dateMillis", "", "(Ljava/lang/String;Ljava/lang/String;J)V", "getAddress", "()Ljava/lang/String;", "getBody", "getDateMillis", "()J", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes8.dex */
    private static final /* data */ class LatestMessage {
        private final String address;
        private final String body;
        private final long dateMillis;

        public static /* synthetic */ LatestMessage copy$default(LatestMessage latestMessage, String str, String str2, long j, int i, Object obj) {
            if ((i & 1) != 0) {
                str = latestMessage.address;
            }
            if ((i & 2) != 0) {
                str2 = latestMessage.body;
            }
            if ((i & 4) != 0) {
                j = latestMessage.dateMillis;
            }
            return latestMessage.copy(str, str2, j);
        }

        /* renamed from: component1, reason: from getter */
        public final String getAddress() {
            return this.address;
        }

        /* renamed from: component2, reason: from getter */
        public final String getBody() {
            return this.body;
        }

        /* renamed from: component3, reason: from getter */
        public final long getDateMillis() {
            return this.dateMillis;
        }

        public final LatestMessage copy(String address, String body, long dateMillis) {
            Intrinsics.checkNotNullParameter(address, "address");
            Intrinsics.checkNotNullParameter(body, "body");
            return new LatestMessage(address, body, dateMillis);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LatestMessage)) {
                return false;
            }
            LatestMessage latestMessage = (LatestMessage) other;
            return Intrinsics.areEqual(this.address, latestMessage.address) && Intrinsics.areEqual(this.body, latestMessage.body) && this.dateMillis == latestMessage.dateMillis;
        }

        public int hashCode() {
            return (((this.address.hashCode() * 31) + this.body.hashCode()) * 31) + Long.hashCode(this.dateMillis);
        }

        public String toString() {
            return "LatestMessage(address=" + this.address + ", body=" + this.body + ", dateMillis=" + this.dateMillis + ")";
        }

        public LatestMessage(String address, String body, long dateMillis) {
            Intrinsics.checkNotNullParameter(address, "address");
            Intrinsics.checkNotNullParameter(body, "body");
            this.address = address;
            this.body = body;
            this.dateMillis = dateMillis;
        }

        public final String getAddress() {
            return this.address;
        }

        public final String getBody() {
            return this.body;
        }

        public final long getDateMillis() {
            return this.dateMillis;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x006f, code lost:
    
        r10 = r3.getLong(r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final com.meharenterprises.originsms.core.SmsRepository.LatestMessage getLatestMessageForThread(long r13) {
        /*
            r12 = this;
            android.content.Context r0 = r12.context
            android.content.ContentResolver r1 = r0.getContentResolver()
            android.net.Uri r2 = android.provider.Telephony.Sms.CONTENT_URI
            r0 = 3
            java.lang.String[] r3 = new java.lang.String[r0]
            r0 = 0
            java.lang.String r7 = "address"
            r3[r0] = r7
            r8 = 1
            java.lang.String r9 = "body"
            r3[r8] = r9
            r4 = 2
            java.lang.String r10 = "date"
            r3[r4] = r10
            java.lang.String[] r5 = new java.lang.String[r8]
            java.lang.String r4 = java.lang.String.valueOf(r13)
            r5[r0] = r4
            java.lang.String r4 = "thread_id = ?"
            java.lang.String r6 = "date DESC LIMIT 1"
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6)
            r2 = 0
            if (r1 == 0) goto L8b
            java.io.Closeable r1 = (java.io.Closeable) r1
            r3 = r1
            android.database.Cursor r3 = (android.database.Cursor) r3     // Catch: java.lang.Throwable -> L84
            r4 = 0
            boolean r5 = r3.moveToFirst()     // Catch: java.lang.Throwable -> L84
            if (r5 == 0) goto L7d
            int r5 = r3.getColumnIndex(r7)     // Catch: java.lang.Throwable -> L84
            int r6 = r3.getColumnIndex(r9)     // Catch: java.lang.Throwable -> L84
            int r7 = r3.getColumnIndex(r10)     // Catch: java.lang.Throwable -> L84
            if (r5 < 0) goto L4f
            java.lang.String r9 = r3.getString(r5)     // Catch: java.lang.Throwable -> L84
            goto L50
        L4f:
            r9 = r2
        L50:
            r10 = r9
            java.lang.CharSequence r10 = (java.lang.CharSequence) r10     // Catch: java.lang.Throwable -> L84
            if (r10 == 0) goto L5b
            boolean r10 = kotlin.text.StringsKt.isBlank(r10)     // Catch: java.lang.Throwable -> L84
            if (r10 == 0) goto L5c
        L5b:
            r0 = r8
        L5c:
            if (r0 != 0) goto L7d
            com.meharenterprises.originsms.core.SmsRepository$LatestMessage r0 = new com.meharenterprises.originsms.core.SmsRepository$LatestMessage     // Catch: java.lang.Throwable -> L84
            java.lang.String r8 = ""
            if (r6 < 0) goto L6d
            java.lang.String r10 = r3.getString(r6)     // Catch: java.lang.Throwable -> L84
            if (r10 != 0) goto L6c
            goto L6d
        L6c:
            r8 = r10
        L6d:
            if (r7 < 0) goto L74
            long r10 = r3.getLong(r7)     // Catch: java.lang.Throwable -> L84
            goto L76
        L74:
            r10 = 0
        L76:
            r0.<init>(r9, r8, r10)     // Catch: java.lang.Throwable -> L84
            kotlin.io.CloseableKt.closeFinally(r1, r2)
            return r0
        L7d:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L84
            kotlin.io.CloseableKt.closeFinally(r1, r2)
            goto L8b
        L84:
            r0 = move-exception
            throw r0     // Catch: java.lang.Throwable -> L86
        L86:
            r2 = move-exception
            kotlin.io.CloseableKt.closeFinally(r1, r0)
            throw r2
        L8b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.core.SmsRepository.getLatestMessageForThread(long):com.meharenterprises.originsms.core.SmsRepository$LatestMessage");
    }

    private final int getUnreadCountForThread(long threadId) {
        Cursor query = this.context.getContentResolver().query(Telephony.Sms.CONTENT_URI, new String[]{"_id"}, "thread_id = ? AND read = 0", new String[]{String.valueOf(threadId)}, null);
        if (query == null) {
            return 0;
        }
        Cursor cursor = query;
        try {
            Cursor it = cursor;
            int count = it.getCount();
            CloseableKt.closeFinally(cursor, null);
            return count;
        } finally {
        }
    }

    public final Object getMessages(long threadId, Continuation<? super List<Message>> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new SmsRepository$getMessages$2(this, threadId, null), continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00b2 A[Catch: all -> 0x00fa, TryCatch #0 {all -> 0x00fa, blocks: (B:5:0x005a, B:6:0x007a, B:9:0x0082, B:10:0x0088, B:13:0x0094, B:18:0x00a2, B:24:0x00b2, B:26:0x00bb, B:27:0x00bf, B:29:0x00c9, B:34:0x00db, B:43:0x00f1), top: B:4:0x005a }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00bb A[Catch: all -> 0x00fa, TryCatch #0 {all -> 0x00fa, blocks: (B:5:0x005a, B:6:0x007a, B:9:0x0082, B:10:0x0088, B:13:0x0094, B:18:0x00a2, B:24:0x00b2, B:26:0x00bb, B:27:0x00bf, B:29:0x00c9, B:34:0x00db, B:43:0x00f1), top: B:4:0x005a }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00c9 A[Catch: all -> 0x00fa, TryCatch #0 {all -> 0x00fa, blocks: (B:5:0x005a, B:6:0x007a, B:9:0x0082, B:10:0x0088, B:13:0x0094, B:18:0x00a2, B:24:0x00b2, B:26:0x00bb, B:27:0x00bf, B:29:0x00c9, B:34:0x00db, B:43:0x00f1), top: B:4:0x005a }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.List<com.meharenterprises.originsms.core.Message> getSmsMessages(long r37) {
        /*
            Method dump skipped, instructions count: 260
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.core.SmsRepository.getSmsMessages(long):java.util.List");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final List<Message> getMmsMessages(long threadId) {
        Throwable th;
        int mmsBox;
        boolean z;
        List messages = new ArrayList();
        int i = 1;
        String[] projection = {"_id", "date", "date_sent", "msg_box", "read"};
        Cursor query = this.context.getContentResolver().query(Telephony.Mms.CONTENT_URI, projection, "thread_id = ?", new String[]{String.valueOf(threadId)}, "date ASC");
        if (query != null) {
            Cursor cursor = query;
            try {
                Cursor cursor2 = cursor;
                int idIdx = cursor2.getColumnIndex("_id");
                int dateIdx = cursor2.getColumnIndex("date");
                int dateSentIdx = cursor2.getColumnIndex("date_sent");
                int boxIdx = cursor2.getColumnIndex("msg_box");
                int readIdx = cursor2.getColumnIndex("read");
                while (cursor2.moveToNext()) {
                    long mmsId = cursor2.getLong(idIdx);
                    if (boxIdx >= 0) {
                        try {
                            mmsBox = cursor2.getInt(boxIdx);
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                throw th;
                            } catch (Throwable th3) {
                                CloseableKt.closeFinally(cursor, th);
                                throw th3;
                            }
                        }
                    } else {
                        mmsBox = i;
                    }
                    long dateSeconds = dateIdx >= 0 ? cursor2.getLong(dateIdx) : 0L;
                    long dateSentSeconds = dateSentIdx >= 0 ? cursor2.getLong(dateSentIdx) : 0L;
                    Iterable parts = getMmsParts(mmsId);
                    Collection destination$iv$iv = new ArrayList();
                    Collection destination$iv$iv2 = destination$iv$iv;
                    for (Object element$iv$iv : parts) {
                        if (element$iv$iv instanceof MmsPart.Text) {
                            Collection destination$iv$iv3 = destination$iv$iv2;
                            destination$iv$iv3.add(element$iv$iv);
                            destination$iv$iv2 = destination$iv$iv3;
                        }
                    }
                    String textBody = CollectionsKt.joinToString$default((List) destination$iv$iv2, " ", null, null, 0, null, new Function1<MmsPart.Text, CharSequence>() { // from class: com.meharenterprises.originsms.core.SmsRepository$getMmsMessages$1$textBody$1
                        @Override // kotlin.jvm.functions.Function1
                        public final CharSequence invoke(SmsRepository.MmsPart.Text it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return it.getText();
                        }
                    }, 30, null);
                    Iterable $this$filterIsInstance$iv = parts;
                    Collection destination$iv$iv4 = new ArrayList();
                    for (Object element$iv$iv2 : $this$filterIsInstance$iv) {
                        Iterable $this$filterIsInstance$iv2 = $this$filterIsInstance$iv;
                        String[] projection2 = projection;
                        try {
                            if (element$iv$iv2 instanceof MmsPart.Binary) {
                                Collection destination$iv$iv5 = destination$iv$iv4;
                                destination$iv$iv5.add(element$iv$iv2);
                                destination$iv$iv4 = destination$iv$iv5;
                            }
                            $this$filterIsInstance$iv = $this$filterIsInstance$iv2;
                            projection = projection2;
                        } catch (Throwable th4) {
                            th = th4;
                            throw th;
                        }
                    }
                    String[] projection3 = projection;
                    Iterable $this$map$iv = (List) destination$iv$iv4;
                    Collection destination$iv$iv6 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    for (Object item$iv$iv : $this$map$iv) {
                        MmsPart.Binary it = (MmsPart.Binary) item$iv$iv;
                        destination$iv$iv6.add(new Attachment(it.getPartId(), it.getContentType(), it.getUri(), it.getFileName()));
                        $this$map$iv = $this$map$iv;
                    }
                    List attachments = (List) destination$iv$iv6;
                    String senderAddress = getMmsSenderAddress(mmsId);
                    long j = dateSeconds * 1000;
                    long j2 = dateSentSeconds * 1000;
                    MessageType messageType = MessageType.MMS;
                    MessageBox mapMmsBox = mapMmsBox(mmsBox);
                    if (readIdx >= 0) {
                        i = 1;
                        if (cursor2.getInt(readIdx) != 1) {
                            z = 0;
                            messages.add(new Message(mmsId, threadId, senderAddress, textBody, j, j2, messageType, mapMmsBox, z, attachments, false, 1024, null));
                            projection = projection3;
                        }
                    } else {
                        i = 1;
                    }
                    z = i;
                    messages.add(new Message(mmsId, threadId, senderAddress, textBody, j, j2, messageType, mapMmsBox, z, attachments, false, 1024, null));
                    projection = projection3;
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(cursor, null);
            } catch (Throwable th5) {
                th = th5;
            }
        }
        return messages;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SmsRepository.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lcom/meharenterprises/originsms/core/SmsRepository$MmsPart;", "", "()V", "Binary", "Text", "Lcom/meharenterprises/originsms/core/SmsRepository$MmsPart$Binary;", "Lcom/meharenterprises/originsms/core/SmsRepository$MmsPart$Text;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes8.dex */
    public static abstract class MmsPart {
        public /* synthetic */ MmsPart(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* compiled from: SmsRepository.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/meharenterprises/originsms/core/SmsRepository$MmsPart$Text;", "Lcom/meharenterprises/originsms/core/SmsRepository$MmsPart;", "text", "", "(Ljava/lang/String;)V", "getText", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* loaded from: classes8.dex */
        public static final /* data */ class Text extends MmsPart {
            private final String text;

            public static /* synthetic */ Text copy$default(Text text, String str, int i, Object obj) {
                if ((i & 1) != 0) {
                    str = text.text;
                }
                return text.copy(str);
            }

            /* renamed from: component1, reason: from getter */
            public final String getText() {
                return this.text;
            }

            public final Text copy(String text) {
                Intrinsics.checkNotNullParameter(text, "text");
                return new Text(text);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof Text) && Intrinsics.areEqual(this.text, ((Text) other).text);
            }

            public int hashCode() {
                return this.text.hashCode();
            }

            public String toString() {
                return "Text(text=" + this.text + ")";
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Text(String text) {
                super(null);
                Intrinsics.checkNotNullParameter(text, "text");
                this.text = text;
            }

            public final String getText() {
                return this.text;
            }
        }

        private MmsPart() {
        }

        /* compiled from: SmsRepository.kt */
        @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0005HÆ\u0003J3\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001b"}, d2 = {"Lcom/meharenterprises/originsms/core/SmsRepository$MmsPart$Binary;", "Lcom/meharenterprises/originsms/core/SmsRepository$MmsPart;", "partId", "", "contentType", "", "uri", "fileName", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getContentType", "()Ljava/lang/String;", "getFileName", "getPartId", "()J", "getUri", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* loaded from: classes8.dex */
        public static final /* data */ class Binary extends MmsPart {
            private final String contentType;
            private final String fileName;
            private final long partId;
            private final String uri;

            public static /* synthetic */ Binary copy$default(Binary binary, long j, String str, String str2, String str3, int i, Object obj) {
                if ((i & 1) != 0) {
                    j = binary.partId;
                }
                long j2 = j;
                if ((i & 2) != 0) {
                    str = binary.contentType;
                }
                String str4 = str;
                if ((i & 4) != 0) {
                    str2 = binary.uri;
                }
                String str5 = str2;
                if ((i & 8) != 0) {
                    str3 = binary.fileName;
                }
                return binary.copy(j2, str4, str5, str3);
            }

            /* renamed from: component1, reason: from getter */
            public final long getPartId() {
                return this.partId;
            }

            /* renamed from: component2, reason: from getter */
            public final String getContentType() {
                return this.contentType;
            }

            /* renamed from: component3, reason: from getter */
            public final String getUri() {
                return this.uri;
            }

            /* renamed from: component4, reason: from getter */
            public final String getFileName() {
                return this.fileName;
            }

            public final Binary copy(long partId, String contentType, String uri, String fileName) {
                Intrinsics.checkNotNullParameter(contentType, "contentType");
                Intrinsics.checkNotNullParameter(uri, "uri");
                return new Binary(partId, contentType, uri, fileName);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof Binary)) {
                    return false;
                }
                Binary binary = (Binary) other;
                return this.partId == binary.partId && Intrinsics.areEqual(this.contentType, binary.contentType) && Intrinsics.areEqual(this.uri, binary.uri) && Intrinsics.areEqual(this.fileName, binary.fileName);
            }

            public int hashCode() {
                return (((((Long.hashCode(this.partId) * 31) + this.contentType.hashCode()) * 31) + this.uri.hashCode()) * 31) + (this.fileName == null ? 0 : this.fileName.hashCode());
            }

            public String toString() {
                return "Binary(partId=" + this.partId + ", contentType=" + this.contentType + ", uri=" + this.uri + ", fileName=" + this.fileName + ")";
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Binary(long partId, String contentType, String uri, String fileName) {
                super(null);
                Intrinsics.checkNotNullParameter(contentType, "contentType");
                Intrinsics.checkNotNullParameter(uri, "uri");
                this.partId = partId;
                this.contentType = contentType;
                this.uri = uri;
                this.fileName = fileName;
            }

            public final String getContentType() {
                return this.contentType;
            }

            public final String getFileName() {
                return this.fileName;
            }

            public final long getPartId() {
                return this.partId;
            }

            public final String getUri() {
                return this.uri;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00ca, code lost:
    
        r15 = r3.getString(r11);
     */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00a3 A[Catch: all -> 0x0129, TryCatch #0 {all -> 0x0129, blocks: (B:5:0x004e, B:6:0x0066, B:14:0x006f, B:17:0x0079, B:21:0x0082, B:45:0x008d, B:46:0x0091, B:48:0x0097, B:53:0x00a3, B:24:0x00ad, B:26:0x00b5, B:28:0x00bd, B:33:0x00ca, B:34:0x00d5, B:37:0x00d1, B:58:0x011f), top: B:4:0x004e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final java.util.List<com.meharenterprises.originsms.core.SmsRepository.MmsPart> getMmsParts(long r28) {
        /*
            Method dump skipped, instructions count: 307
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.core.SmsRepository.getMmsParts(long):java.util.List");
    }

    private final String getMmsSenderAddress(long mmsId) {
        Uri addrUri = Uri.parse("content://mms/" + mmsId + "/addr");
        Cursor query = this.context.getContentResolver().query(addrUri, new String[]{"address", "type"}, null, null, null);
        if (query != null) {
            Cursor cursor = query;
            try {
                Cursor cursor2 = cursor;
                int addrIdx = cursor2.getColumnIndex("address");
                int typeIdx = cursor2.getColumnIndex("type");
                while (cursor2.moveToNext()) {
                    int type = typeIdx >= 0 ? cursor2.getInt(typeIdx) : -1;
                    if (type == 137 && addrIdx >= 0) {
                        String string = cursor2.getString(addrIdx);
                        String str = string != null ? string : "";
                        CloseableKt.closeFinally(cursor, null);
                        return str;
                    }
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(cursor, null);
            } finally {
            }
        }
        return "";
    }

    private final MessageBox mapMmsBox(int mmsBox) {
        switch (mmsBox) {
            case 1:
                return MessageBox.INBOX;
            case 2:
                return MessageBox.SENT;
            case 3:
                return MessageBox.DRAFT;
            case 4:
                return MessageBox.OUTBOX;
            case 5:
                return MessageBox.FAILED;
            default:
                return MessageBox.INBOX;
        }
    }

    private final MessageBox mapSmsBox(int smsType) {
        switch (smsType) {
            case 1:
                return MessageBox.INBOX;
            case 2:
                return MessageBox.SENT;
            case 3:
                return MessageBox.DRAFT;
            case 4:
                return MessageBox.OUTBOX;
            case 5:
                return MessageBox.FAILED;
            case 6:
                return MessageBox.QUEUED;
            default:
                return MessageBox.INBOX;
        }
    }

    public static /* synthetic */ void sendSms$default(SmsRepository smsRepository, String str, String str2, Long l, Integer num, int i, Object obj) {
        if ((i & 8) != 0) {
            num = null;
        }
        smsRepository.sendSms(str, str2, l, num);
    }

    public final void sendSms(String destinationAddress, String body, Long threadId, Integer subscriptionId) {
        SmsManager smsManager;
        Intrinsics.checkNotNullParameter(destinationAddress, "destinationAddress");
        Intrinsics.checkNotNullParameter(body, "body");
        if (subscriptionId != null && subscriptionId.intValue() != -1) {
            smsManager = SmsManager.getSmsManagerForSubscriptionId(subscriptionId.intValue());
        } else {
            smsManager = (SmsManager) this.context.getSystemService(SmsManager.class);
        }
        SmsManager smsManager2 = smsManager;
        ArrayList parts = smsManager2.divideMessage(body);
        ArrayList sentIntents = new ArrayList();
        ArrayList deliveredIntents = new ArrayList();
        int size = parts.size();
        for (int i = 0; i < size; i++) {
            int requestCode = INSTANCE.nextPendingIntentRequestCode();
            Intent sentIntent = new Intent(this.context, (Class<?>) SendStatusReceiver.class);
            sentIntent.setAction(ACTION_SMS_SENT);
            sentIntent.putExtra(EXTRA_PART_INDEX, i);
            sentIntents.add(PendingIntent.getBroadcast(this.context, requestCode, sentIntent, 201326592));
            Intent deliveredIntent = new Intent(this.context, (Class<?>) DeliveryStatusReceiver.class);
            deliveredIntent.setAction(ACTION_SMS_DELIVERED);
            deliveredIntents.add(PendingIntent.getBroadcast(this.context, INSTANCE.nextPendingIntentRequestCode(), deliveredIntent, 201326592));
        }
        smsManager2.sendMultipartTextMessage(destinationAddress, null, parts, sentIntents, deliveredIntents);
        ContentValues values = new ContentValues();
        values.put("address", destinationAddress);
        values.put("body", body);
        values.put("date", Long.valueOf(System.currentTimeMillis()));
        values.put("read", (Integer) 1);
        values.put("type", (Integer) 2);
        if (threadId != null) {
            long it = threadId.longValue();
            values.put("thread_id", Long.valueOf(it));
        }
        this.context.getContentResolver().insert(Telephony.Sms.CONTENT_URI, values);
    }

    public final void sendMms(String destinationAddress, String body, List<? extends Uri> attachmentUris, Long threadId) {
        Intrinsics.checkNotNullParameter(destinationAddress, "destinationAddress");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(attachmentUris, "attachmentUris");
        File pduFile = new File(this.context.getCacheDir(), "send_" + System.currentTimeMillis() + ".dat");
        byte[] pduBytes = MmsPduBuilder.INSTANCE.buildSendRequest(this.context, destinationAddress, body, attachmentUris);
        FilesKt.writeBytes(pduFile, pduBytes);
        Uri pduUri = FileProvider.getUriForFile(this.context, this.context.getPackageName() + ".fileprovider", pduFile);
        Intent sentIntent = new Intent(this.context, (Class<?>) SendStatusReceiver.class);
        sentIntent.setAction(ACTION_MMS_SENT);
        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this.context, INSTANCE.nextPendingIntentRequestCode(), sentIntent, 201326592);
        SmsManager smsManager = (SmsManager) this.context.getSystemService(SmsManager.class);
        smsManager.sendMultimediaMessage(this.context, pduUri, null, null, sentPendingIntent);
        ContentValues $this$sendMms_u24lambda_u2413 = new ContentValues();
        $this$sendMms_u24lambda_u2413.put("address", destinationAddress);
        $this$sendMms_u24lambda_u2413.put("body", body);
        $this$sendMms_u24lambda_u2413.put("date", Long.valueOf(System.currentTimeMillis()));
        $this$sendMms_u24lambda_u2413.put("read", (Integer) 1);
        $this$sendMms_u24lambda_u2413.put("type", (Integer) 2);
        if (threadId != null) {
            long it = threadId.longValue();
            $this$sendMms_u24lambda_u2413.put("thread_id", Long.valueOf(it));
        }
        this.context.getContentResolver().insert(Telephony.Sms.CONTENT_URI, $this$sendMms_u24lambda_u2413);
    }

    public final Object markThreadRead(long threadId, Continuation<? super Integer> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new SmsRepository$markThreadRead$2(this, threadId, null), continuation);
    }

    public final Object markThreadUnread(long threadId, Continuation<? super Unit> continuation) {
        Object withContext = BuildersKt.withContext(Dispatchers.getIO(), new SmsRepository$markThreadUnread$2(this, threadId, null), continuation);
        return withContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? withContext : Unit.INSTANCE;
    }

    public final Object deleteThread(long threadId, Continuation<? super Unit> continuation) {
        Object withContext = BuildersKt.withContext(Dispatchers.getIO(), new SmsRepository$deleteThread$2(this, threadId, null), continuation);
        return withContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? withContext : Unit.INSTANCE;
    }

    public final Object deleteMessage(long messageId, Continuation<? super Integer> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new SmsRepository$deleteMessage$2(this, messageId, null), continuation);
    }

    /* compiled from: SmsRepository.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/meharenterprises/originsms/core/SmsRepository$Companion;", "", "()V", "ACTION_MMS_SENT", "", "ACTION_SMS_DELIVERED", "ACTION_SMS_SENT", "EXTRA_PART_INDEX", "pendingIntentRequestCodeCounter", "Ljava/util/concurrent/atomic/AtomicInteger;", "nextPendingIntentRequestCode", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes8.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int nextPendingIntentRequestCode() {
            return SmsRepository.pendingIntentRequestCodeCounter.incrementAndGet();
        }
    }
}
