package com.meharenterprises.originsms.ui.thread;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Telephony;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelKt;
import com.meharenterprises.originsms.core.Message;
import com.meharenterprises.originsms.core.SmsRepository;
import com.meharenterprises.originsms.data.db.OriginDatabase;
import com.meharenterprises.originsms.ui.thread.ThreadViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;

/* compiled from: ThreadViewModel.kt */
@Metadata(d1 = {"\u0000\u0083\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\"\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\b*\u0001*\u0018\u00002\u00020\u0001:\u0001MB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\nJ\u0016\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020\u00122\u0006\u00101\u001a\u00020\u0010J\u0006\u00102\u001a\u00020-J\u000e\u00103\u001a\u00020-2\u0006\u00104\u001a\u00020\u0012J\u0014\u00105\u001a\u00020-2\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u001207J\u000e\u00108\u001a\u00020-2\u0006\u00109\u001a\u00020\u0010J\u0018\u0010:\u001a\u0004\u0018\u00010\u00102\u0006\u00100\u001a\u00020\u0012H\u0086@¢\u0006\u0002\u0010;J\u0006\u0010<\u001a\u00020-J\b\u0010=\u001a\u00020-H\u0002J\b\u0010>\u001a\u00020-H\u0014J\b\u0010?\u001a\u00020-H\u0002J\u000e\u0010@\u001a\u00020-2\u0006\u0010.\u001a\u00020\nJ\u000e\u0010A\u001a\u00020-2\u0006\u0010B\u001a\u00020\bJ\u000e\u0010C\u001a\u00020-2\u0006\u0010D\u001a\u00020\u0010J\u0010\u0010E\u001a\n G*\u0004\u0018\u00010F0FH\u0002J\u000e\u0010H\u001a\u00020-2\u0006\u0010I\u001a\u00020\u0010J\u001e\u0010J\u001a\u00020-2\u0006\u0010D\u001a\u00020\u00102\u0006\u0010K\u001a\u00020\u00122\u0006\u00109\u001a\u00020\u0010J\u000e\u0010L\u001a\u00020-2\u0006\u0010B\u001a\u00020\bR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00070\u0016¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0018R\u001e\u0010\"\u001a\u0004\u0018\u00010#X\u0086\u000e¢\u0006\u0010\n\u0002\u0010(\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u0010\u0010)\u001a\u00020*X\u0082\u0004¢\u0006\u0004\n\u0002\u0010+¨\u0006N"}, d2 = {"Lcom/meharenterprises/originsms/ui/thread/ThreadViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_messages", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/meharenterprises/originsms/core/Message;", "_pendingAttachments", "Landroid/net/Uri;", "_scheduledEntries", "", "Lcom/meharenterprises/originsms/ui/thread/ThreadViewModel$ScheduledEntry;", "_scheduledLive", "currentAddress", "", "currentThreadId", "", "database", "Lcom/meharenterprises/originsms/data/db/OriginDatabase;", "messages", "Landroidx/lifecycle/LiveData;", "getMessages", "()Landroidx/lifecycle/LiveData;", "newChatStartedAt", "observerRegistered", "", "pendingAttachments", "getPendingAttachments", "repository", "Lcom/meharenterprises/originsms/core/SmsRepository;", "scheduledEntries", "getScheduledEntries", "selectedSubscriptionId", "", "getSelectedSubscriptionId", "()Ljava/lang/Integer;", "setSelectedSubscriptionId", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "smsContentObserver", "com/meharenterprises/originsms/ui/thread/ThreadViewModel$smsContentObserver$1", "Lcom/meharenterprises/originsms/ui/thread/ThreadViewModel$smsContentObserver$1;", "addPendingAttachment", "", "uri", "bind", "threadId", "address", "clearPendingAttachments", "deleteMessage", "messageId", "deleteMessages", "messageIds", "", "deleteScheduledPlaceholder", "key", "getDraft", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadMessages", "loadPersistedScheduled", "onCleared", "registerContentObserver", "removePendingAttachment", "retryMessage", "message", "saveDraft", "text", "scheduledPrefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "sendMessage", "body", "sendScheduledPlaceholder", "scheduledAtMillis", "toggleStar", "ScheduledEntry", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public final class ThreadViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Message>> _messages;
    private final MutableLiveData<List<Uri>> _pendingAttachments;
    private final List<ScheduledEntry> _scheduledEntries;
    private final MutableLiveData<List<ScheduledEntry>> _scheduledLive;
    private String currentAddress;
    private long currentThreadId;
    private final OriginDatabase database;
    private final LiveData<List<Message>> messages;
    private long newChatStartedAt;
    private boolean observerRegistered;
    private final LiveData<List<Uri>> pendingAttachments;
    private final SmsRepository repository;
    private final LiveData<List<ScheduledEntry>> scheduledEntries;
    private Integer selectedSubscriptionId;
    private final ThreadViewModel$smsContentObserver$1 smsContentObserver;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.meharenterprises.originsms.ui.thread.ThreadViewModel$smsContentObserver$1] */
    public ThreadViewModel(Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this.repository = new SmsRepository(application);
        this.database = OriginDatabase.INSTANCE.getInstance(application);
        this._messages = new MutableLiveData<>(CollectionsKt.emptyList());
        this.messages = this._messages;
        this._pendingAttachments = new MutableLiveData<>(CollectionsKt.emptyList());
        this.pendingAttachments = this._pendingAttachments;
        this.currentThreadId = -1L;
        this.currentAddress = "";
        final Handler handler = new Handler(Looper.getMainLooper());
        this.smsContentObserver = new ContentObserver(handler) { // from class: com.meharenterprises.originsms.ui.thread.ThreadViewModel$smsContentObserver$1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                ThreadViewModel.this.loadMessages();
            }
        };
        this._scheduledEntries = new ArrayList();
        this._scheduledLive = new MutableLiveData<>(CollectionsKt.emptyList());
        this.scheduledEntries = this._scheduledLive;
    }

    public final LiveData<List<Message>> getMessages() {
        return this.messages;
    }

    public final LiveData<List<Uri>> getPendingAttachments() {
        return this.pendingAttachments;
    }

    public final Integer getSelectedSubscriptionId() {
        return this.selectedSubscriptionId;
    }

    public final void setSelectedSubscriptionId(Integer num) {
        this.selectedSubscriptionId = num;
    }

    public final void bind(long threadId, String address) {
        Intrinsics.checkNotNullParameter(address, "address");
        this.currentThreadId = threadId;
        this.currentAddress = address;
        if (threadId != -1) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ThreadViewModel$bind$1(this, threadId, null), 3, null);
            loadPersistedScheduled();
            registerContentObserver();
        } else {
            this.newChatStartedAt = System.currentTimeMillis();
            loadMessages();
            loadPersistedScheduled();
            registerContentObserver();
        }
    }

    private final void registerContentObserver() {
        if (this.observerRegistered) {
            return;
        }
        getApplication().getContentResolver().registerContentObserver(Telephony.Sms.CONTENT_URI, true, this.smsContentObserver);
        getApplication().getContentResolver().registerContentObserver(Telephony.Mms.CONTENT_URI, true, this.smsContentObserver);
        this.observerRegistered = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        if (this.observerRegistered) {
            getApplication().getContentResolver().unregisterContentObserver(this.smsContentObserver);
            this.observerRegistered = false;
        }
    }

    public final void loadMessages() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ThreadViewModel$loadMessages$1(this, null), 3, null);
    }

    public final void addPendingAttachment(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        MutableLiveData<List<Uri>> mutableLiveData = this._pendingAttachments;
        List<Uri> value = this._pendingAttachments.getValue();
        if (value == null) {
            value = CollectionsKt.emptyList();
        }
        mutableLiveData.setValue(CollectionsKt.distinct(CollectionsKt.plus((Collection<? extends Uri>) value, uri)));
    }

    public final void removePendingAttachment(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        MutableLiveData<List<Uri>> mutableLiveData = this._pendingAttachments;
        Iterable iterable = (List) this._pendingAttachments.getValue();
        if (iterable == null) {
            iterable = CollectionsKt.emptyList();
        }
        Iterable $this$filterNot$iv = iterable;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filterNot$iv) {
            Uri it = (Uri) element$iv$iv;
            if (!Intrinsics.areEqual(it, uri)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        mutableLiveData.setValue((List) destination$iv$iv);
    }

    public final void clearPendingAttachments() {
        this._pendingAttachments.setValue(CollectionsKt.emptyList());
    }

    /* compiled from: ThreadViewModel.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0017"}, d2 = {"Lcom/meharenterprises/originsms/ui/thread/ThreadViewModel$ScheduledEntry;", "", "text", "", "scheduledAtMillis", "", "key", "(Ljava/lang/String;JLjava/lang/String;)V", "getKey", "()Ljava/lang/String;", "getScheduledAtMillis", "()J", "getText", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes10.dex */
    public static final /* data */ class ScheduledEntry {
        private final String key;
        private final long scheduledAtMillis;
        private final String text;

        public static /* synthetic */ ScheduledEntry copy$default(ScheduledEntry scheduledEntry, String str, long j, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = scheduledEntry.text;
            }
            if ((i & 2) != 0) {
                j = scheduledEntry.scheduledAtMillis;
            }
            if ((i & 4) != 0) {
                str2 = scheduledEntry.key;
            }
            return scheduledEntry.copy(str, j, str2);
        }

        /* renamed from: component1, reason: from getter */
        public final String getText() {
            return this.text;
        }

        /* renamed from: component2, reason: from getter */
        public final long getScheduledAtMillis() {
            return this.scheduledAtMillis;
        }

        /* renamed from: component3, reason: from getter */
        public final String getKey() {
            return this.key;
        }

        public final ScheduledEntry copy(String text, long scheduledAtMillis, String key) {
            Intrinsics.checkNotNullParameter(text, "text");
            Intrinsics.checkNotNullParameter(key, "key");
            return new ScheduledEntry(text, scheduledAtMillis, key);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ScheduledEntry)) {
                return false;
            }
            ScheduledEntry scheduledEntry = (ScheduledEntry) other;
            return Intrinsics.areEqual(this.text, scheduledEntry.text) && this.scheduledAtMillis == scheduledEntry.scheduledAtMillis && Intrinsics.areEqual(this.key, scheduledEntry.key);
        }

        public int hashCode() {
            return (((this.text.hashCode() * 31) + Long.hashCode(this.scheduledAtMillis)) * 31) + this.key.hashCode();
        }

        public String toString() {
            return "ScheduledEntry(text=" + this.text + ", scheduledAtMillis=" + this.scheduledAtMillis + ", key=" + this.key + ")";
        }

        public ScheduledEntry(String text, long scheduledAtMillis, String key) {
            Intrinsics.checkNotNullParameter(text, "text");
            Intrinsics.checkNotNullParameter(key, "key");
            this.text = text;
            this.scheduledAtMillis = scheduledAtMillis;
            this.key = key;
        }

        public final String getKey() {
            return this.key;
        }

        public final long getScheduledAtMillis() {
            return this.scheduledAtMillis;
        }

        public final String getText() {
            return this.text;
        }
    }

    public final LiveData<List<ScheduledEntry>> getScheduledEntries() {
        return this.scheduledEntries;
    }

    private final SharedPreferences scheduledPrefs() {
        return getApplication().getSharedPreferences("scheduled_msgs_" + this.currentThreadId, 0);
    }

    private final void loadPersistedScheduled() {
        Iterable $this$forEach$iv;
        int $i$f$forEach;
        SharedPreferences prefs = scheduledPrefs();
        Iterable keys = prefs.getStringSet("keys", SetsKt.emptySet());
        if (keys == null) {
            return;
        }
        this._scheduledEntries.clear();
        Iterable $this$forEach$iv2 = keys;
        int $i$f$forEach2 = 0;
        for (Object element$iv : $this$forEach$iv2) {
            String key = (String) element$iv;
            String text = prefs.getString("text_" + key, null);
            if (text == null) {
                $this$forEach$iv = $this$forEach$iv2;
                $i$f$forEach = $i$f$forEach2;
            } else {
                Intrinsics.checkNotNull(text);
                $this$forEach$iv = $this$forEach$iv2;
                $i$f$forEach = $i$f$forEach2;
                long time = prefs.getLong("time_" + key, 0L);
                if (time <= System.currentTimeMillis()) {
                    BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ThreadViewModel$loadPersistedScheduled$1$1(this, text, null), 3, null);
                    prefs.edit().remove("text_" + key).remove("time_" + key).apply();
                } else {
                    List<ScheduledEntry> list = this._scheduledEntries;
                    Intrinsics.checkNotNull(key);
                    list.add(new ScheduledEntry(text, time, key));
                }
            }
            $this$forEach$iv2 = $this$forEach$iv;
            $i$f$forEach2 = $i$f$forEach;
        }
        SharedPreferences.Editor edit = prefs.edit();
        Iterable $this$map$iv = this._scheduledEntries;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            ScheduledEntry it = (ScheduledEntry) item$iv$iv;
            destination$iv$iv.add(it.getKey());
        }
        edit.putStringSet("keys", CollectionsKt.toSet((List) destination$iv$iv)).apply();
        this._scheduledLive.setValue(CollectionsKt.toList(this._scheduledEntries));
    }

    public final void sendScheduledPlaceholder(String text, long scheduledAtMillis, String key) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(key, "key");
        this._scheduledEntries.add(new ScheduledEntry(text, scheduledAtMillis, key));
        this._scheduledLive.setValue(CollectionsKt.toList(this._scheduledEntries));
        SharedPreferences prefs = scheduledPrefs();
        Set<String> stringSet = prefs.getStringSet("keys", SetsKt.emptySet());
        if (stringSet == null) {
            stringSet = SetsKt.emptySet();
        }
        Set keys = SetsKt.plus(stringSet, key);
        prefs.edit().putStringSet("keys", keys).putString("text_" + key, text).putLong("time_" + key, scheduledAtMillis).apply();
    }

    public final void deleteScheduledPlaceholder(final String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        CollectionsKt.removeAll((List) this._scheduledEntries, (Function1) new Function1<ScheduledEntry, Boolean>() { // from class: com.meharenterprises.originsms.ui.thread.ThreadViewModel$deleteScheduledPlaceholder$1
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ThreadViewModel.ScheduledEntry it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getKey(), key));
            }
        });
        this._scheduledLive.setValue(CollectionsKt.toList(this._scheduledEntries));
        SharedPreferences prefs = scheduledPrefs();
        Set<String> stringSet = prefs.getStringSet("keys", SetsKt.emptySet());
        if (stringSet == null) {
            stringSet = SetsKt.emptySet();
        }
        Set keys = SetsKt.minus(stringSet, key);
        prefs.edit().putStringSet("keys", keys).remove("text_" + key).remove("time_" + key).apply();
    }

    public final void sendMessage(String body) {
        Intrinsics.checkNotNullParameter(body, "body");
        if (StringsKt.isBlank(this.currentAddress)) {
            return;
        }
        List attachments = this._pendingAttachments.getValue();
        if (attachments == null) {
            attachments = CollectionsKt.emptyList();
        }
        if (attachments.isEmpty()) {
            if (StringsKt.isBlank(body)) {
                return;
            } else {
                this.repository.sendSms(this.currentAddress, body, this.currentThreadId != -1 ? Long.valueOf(this.currentThreadId) : null, this.selectedSubscriptionId);
            }
        } else {
            this.repository.sendMms(this.currentAddress, body, attachments, this.currentThreadId != -1 ? Long.valueOf(this.currentThreadId) : null);
        }
        clearPendingAttachments();
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ThreadViewModel$sendMessage$1(this, null), 3, null);
    }

    public final void toggleStar(Message message) {
        Intrinsics.checkNotNullParameter(message, "message");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ThreadViewModel$toggleStar$1(this, message, null), 3, null);
    }

    public final void deleteMessage(long messageId) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ThreadViewModel$deleteMessage$1(this, messageId, null), 3, null);
    }

    public final void deleteMessages(Set<Long> messageIds) {
        Intrinsics.checkNotNullParameter(messageIds, "messageIds");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ThreadViewModel$deleteMessages$1(messageIds, this, null), 3, null);
    }

    public final void retryMessage(Message message) {
        Intrinsics.checkNotNullParameter(message, "message");
        sendMessage(message.getBody());
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ThreadViewModel$retryMessage$1(this, message, null), 3, null);
    }

    public final void saveDraft(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        if (this.currentThreadId == -1) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ThreadViewModel$saveDraft$1(text, this, null), 3, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004f A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object getDraft(long r6, kotlin.coroutines.Continuation<? super java.lang.String> r8) {
        /*
            r5 = this;
            boolean r0 = r8 instanceof com.meharenterprises.originsms.ui.thread.ThreadViewModel$getDraft$1
            if (r0 == 0) goto L14
            r0 = r8
            com.meharenterprises.originsms.ui.thread.ThreadViewModel$getDraft$1 r0 = (com.meharenterprises.originsms.ui.thread.ThreadViewModel$getDraft$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L19
        L14:
            com.meharenterprises.originsms.ui.thread.ThreadViewModel$getDraft$1 r0 = new com.meharenterprises.originsms.ui.thread.ThreadViewModel$getDraft$1
            r0.<init>(r5, r8)
        L19:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L32;
                case 1: goto L2d;
                default: goto L25;
            }
        L25:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L2d:
            kotlin.ResultKt.throwOnFailure(r0)
            r6 = r0
            goto L46
        L32:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r5
            com.meharenterprises.originsms.data.db.OriginDatabase r3 = r2.database
            com.meharenterprises.originsms.data.db.DraftDao r3 = r3.draftDao()
            r4 = 1
            r8.label = r4
            java.lang.Object r6 = r3.getDraft(r6, r8)
            if (r6 != r1) goto L46
            return r1
        L46:
            com.meharenterprises.originsms.data.db.DraftEntity r6 = (com.meharenterprises.originsms.data.db.DraftEntity) r6
            if (r6 == 0) goto L4f
            java.lang.String r6 = r6.getText()
            goto L50
        L4f:
            r6 = 0
        L50:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.thread.ThreadViewModel.getDraft(long, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
