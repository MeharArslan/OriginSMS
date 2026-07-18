package com.meharenterprises.originsms.ui.conversations;

import android.app.Application;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Telephony;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelKt;
import com.meharenterprises.originsms.core.ConversationSummary;
import com.meharenterprises.originsms.core.SmsRepository;
import com.meharenterprises.originsms.data.db.OriginDatabase;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;

/* compiled from: ConversationListViewModel.kt */
@Metadata(d1 = {"\u0000q\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0019\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\u001c\u0010\u001d\u001a\u00020\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020 J\u0014\u0010%\u001a\u00020\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fJ\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0086@¢\u0006\u0002\u0010'J\u0014\u0010(\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0086@¢\u0006\u0002\u0010'J\u0006\u0010)\u001a\u00020\u001cJ\u000e\u0010*\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020 J\u0014\u0010+\u001a\u00020\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fJ\u0014\u0010,\u001a\u00020\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fJ\u0014\u0010-\u001a\u00020\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fJ\b\u0010.\u001a\u00020\u001cH\u0014J\u0014\u0010/\u001a\u00020\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fJ\u001c\u00100\u001a\u00020\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\u0006\u00101\u001a\u00020\nJ\u0016\u00102\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020 2\u0006\u00103\u001a\u00020\nJ\u0016\u00104\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020 2\u0006\u00105\u001a\u00020\nJ\u001c\u00106\u001a\u00020\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\u0006\u00107\u001a\u00020\nJ\u001c\u00108\u001a\u00020\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\u0006\u00109\u001a\u00020\nJ\u000e\u0010:\u001a\u00020\u001c2\u0006\u0010;\u001a\u00020\u0012J*\u0010<\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020 2\u0012\u0010=\u001a\u000e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020?0>H\u0082@¢\u0006\u0002\u0010@R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0010\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001a¨\u0006A"}, d2 = {"Lcom/meharenterprises/originsms/ui/conversations/ConversationListViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_conversations", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/meharenterprises/originsms/core/ConversationSummary;", "_isLoading", "", "kotlin.jvm.PlatformType", "allLoadedConversations", "conversations", "Landroidx/lifecycle/LiveData;", "getConversations", "()Landroidx/lifecycle/LiveData;", "currentSearchQuery", "", "database", "Lcom/meharenterprises/originsms/data/db/OriginDatabase;", "isLoading", "repository", "Lcom/meharenterprises/originsms/core/SmsRepository;", "smsObserver", "com/meharenterprises/originsms/ui/conversations/ConversationListViewModel$smsObserver$1", "Lcom/meharenterprises/originsms/ui/conversations/ConversationListViewModel$smsObserver$1;", "applySearchFilter", "", "blockSelectedThreads", "threadIds", "", "", "context", "Landroid/content/Context;", "deleteThread", "threadId", "deleteThreads", "getArchivedConversations", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHiddenConversations", "loadConversations", "markRead", "markReadMultiple", "markUnreadMultiple", "moveToTrash", "onCleared", "restoreFromTrash", "setArchived", "archived", "setHidden", "hidden", "setLocked", "locked", "setMuted", "muted", "setPinned", "pinned", "setSearchQuery", "query", "upsertWithChange", "change", "Lkotlin/Function1;", "Lcom/meharenterprises/originsms/data/db/ThreadLockEntity;", "(JLkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class ConversationListViewModel extends AndroidViewModel {
    private final MutableLiveData<List<ConversationSummary>> _conversations;
    private final MutableLiveData<Boolean> _isLoading;
    private List<ConversationSummary> allLoadedConversations;
    private final LiveData<List<ConversationSummary>> conversations;
    private String currentSearchQuery;
    private final OriginDatabase database;
    private final LiveData<Boolean> isLoading;
    private final SmsRepository repository;
    private final ConversationListViewModel$smsObserver$1 smsObserver;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$smsObserver$1] */
    public ConversationListViewModel(Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this.repository = new SmsRepository(application);
        this.database = OriginDatabase.INSTANCE.getInstance(application);
        this.allLoadedConversations = CollectionsKt.emptyList();
        this.currentSearchQuery = "";
        this._conversations = new MutableLiveData<>(CollectionsKt.emptyList());
        this.conversations = this._conversations;
        this._isLoading = new MutableLiveData<>(false);
        this.isLoading = this._isLoading;
        final Handler handler = new Handler(Looper.getMainLooper());
        this.smsObserver = new ContentObserver(handler) { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$smsObserver$1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                ConversationListViewModel.this.loadConversations();
            }
        };
        application.getContentResolver().registerContentObserver(Telephony.Sms.CONTENT_URI, true, this.smsObserver);
    }

    public final LiveData<List<ConversationSummary>> getConversations() {
        return this.conversations;
    }

    public final LiveData<Boolean> isLoading() {
        return this.isLoading;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        getApplication().getContentResolver().unregisterContentObserver(this.smsObserver);
    }

    public final void loadConversations() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$loadConversations$1(this, null), 3, null);
    }

    public final void setSearchQuery(String query) {
        Intrinsics.checkNotNullParameter(query, "query");
        this.currentSearchQuery = query;
        applySearchFilter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0094, code lost:
    
        if (kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r13, (java.lang.CharSequence) r2, false, 2, (java.lang.Object) null) != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void applySearchFilter() {
        /*
            r17 = this;
            r0 = r17
            androidx.lifecycle.MutableLiveData<java.util.List<com.meharenterprises.originsms.core.ConversationSummary>> r1 = r0._conversations
            java.lang.String r2 = r0.currentSearchQuery
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            boolean r2 = kotlin.text.StringsKt.isBlank(r2)
            if (r2 == 0) goto L12
            java.util.List<com.meharenterprises.originsms.core.ConversationSummary> r2 = r0.allLoadedConversations
            goto La8
        L12:
            java.lang.String r2 = r0.currentSearchQuery
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            java.lang.CharSequence r2 = kotlin.text.StringsKt.trim(r2)
            java.lang.String r2 = r2.toString()
            java.util.Locale r3 = java.util.Locale.ROOT
            java.lang.String r2 = r2.toLowerCase(r3)
            java.lang.String r3 = "toLowerCase(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r3)
            java.util.List<com.meharenterprises.originsms.core.ConversationSummary> r4 = r0.allLoadedConversations
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            r5 = 0
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            java.util.Collection r6 = (java.util.Collection) r6
            r7 = r4
            r8 = 0
            java.util.Iterator r9 = r7.iterator()
        L3b:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto La2
            java.lang.Object r10 = r9.next()
            r11 = r10
            com.meharenterprises.originsms.core.ConversationSummary r11 = (com.meharenterprises.originsms.core.ConversationSummary) r11
            r12 = 0
            java.lang.String r13 = r11.getDisplayName()
            java.util.Locale r14 = java.util.Locale.ROOT
            java.lang.String r13 = r13.toLowerCase(r14)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r13, r3)
            java.lang.CharSequence r13 = (java.lang.CharSequence) r13
            r14 = r2
            java.lang.CharSequence r14 = (java.lang.CharSequence) r14
            r15 = 0
            r0 = 2
            r16 = r4
            r4 = 0
            boolean r13 = kotlin.text.StringsKt.contains$default(r13, r14, r15, r0, r4)
            if (r13 != 0) goto L96
            java.lang.String r13 = r11.getAddress()
            java.util.Locale r14 = java.util.Locale.ROOT
            java.lang.String r13 = r13.toLowerCase(r14)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r13, r3)
            java.lang.CharSequence r13 = (java.lang.CharSequence) r13
            r14 = r2
            java.lang.CharSequence r14 = (java.lang.CharSequence) r14
            boolean r13 = kotlin.text.StringsKt.contains$default(r13, r14, r15, r0, r4)
            if (r13 != 0) goto L96
            java.lang.String r13 = r11.getSnippet()
            java.util.Locale r14 = java.util.Locale.ROOT
            java.lang.String r13 = r13.toLowerCase(r14)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r13, r3)
            java.lang.CharSequence r13 = (java.lang.CharSequence) r13
            r14 = r2
            java.lang.CharSequence r14 = (java.lang.CharSequence) r14
            boolean r0 = kotlin.text.StringsKt.contains$default(r13, r14, r15, r0, r4)
            if (r0 == 0) goto L97
        L96:
            r15 = 1
        L97:
            if (r15 == 0) goto L9d
            r6.add(r10)
        L9d:
            r0 = r17
            r4 = r16
            goto L3b
        La2:
            r16 = r4
            r0 = r6
            java.util.List r0 = (java.util.List) r0
            r2 = r0
        La8:
            r1.setValue(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.conversations.ConversationListViewModel.applySearchFilter():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to find 'out' block for switch in B:7:0x0029. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00a2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object upsertWithChange(long r30, kotlin.jvm.functions.Function1<? super com.meharenterprises.originsms.data.db.ThreadLockEntity, com.meharenterprises.originsms.data.db.ThreadLockEntity> r32, kotlin.coroutines.Continuation<? super kotlin.Unit> r33) {
        /*
            r29 = this;
            r0 = r33
            boolean r1 = r0 instanceof com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$upsertWithChange$1
            if (r1 == 0) goto L19
            r1 = r0
            com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$upsertWithChange$1 r1 = (com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$upsertWithChange$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L19
            int r0 = r1.label
            int r0 = r0 - r3
            r1.label = r0
            r0 = r1
            r2 = r29
            goto L21
        L19:
            com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$upsertWithChange$1 r1 = new com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$upsertWithChange$1
            r2 = r29
            r1.<init>(r2, r0)
            r0 = r1
        L21:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r0.label
            switch(r4) {
                case 0: goto L47;
                case 1: goto L38;
                case 2: goto L34;
                default: goto L2c;
            }
        L2c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L34:
            kotlin.ResultKt.throwOnFailure(r1)
            goto La3
        L38:
            long r4 = r0.J$0
            java.lang.Object r6 = r0.L$1
            kotlin.jvm.functions.Function1 r6 = (kotlin.jvm.functions.Function1) r6
            java.lang.Object r7 = r0.L$0
            com.meharenterprises.originsms.ui.conversations.ConversationListViewModel r7 = (com.meharenterprises.originsms.ui.conversations.ConversationListViewModel) r7
            kotlin.ResultKt.throwOnFailure(r1)
            r8 = r1
            goto L66
        L47:
            kotlin.ResultKt.throwOnFailure(r1)
            r7 = r29
            r4 = r30
            r6 = r32
            com.meharenterprises.originsms.data.db.OriginDatabase r8 = r7.database
            com.meharenterprises.originsms.data.db.ThreadLockDao r8 = r8.threadLockDao()
            r0.L$0 = r7
            r0.L$1 = r6
            r0.J$0 = r4
            r9 = 1
            r0.label = r9
            java.lang.Object r8 = r8.getForThread(r4, r0)
            if (r8 != r3) goto L66
            return r3
        L66:
            com.meharenterprises.originsms.data.db.ThreadLockEntity r8 = (com.meharenterprises.originsms.data.db.ThreadLockEntity) r8
            if (r8 != 0) goto L87
            com.meharenterprises.originsms.data.db.ThreadLockEntity r8 = new com.meharenterprises.originsms.data.db.ThreadLockEntity
            r9 = r8
            r27 = 2046(0x7fe, float:2.867E-42)
            r28 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r20 = 0
            r22 = 0
            r23 = 0
            r25 = 0
            r10 = r4
            r9.<init>(r10, r12, r13, r14, r16, r17, r18, r20, r22, r23, r25, r27, r28)
        L87:
            r4 = r8
            com.meharenterprises.originsms.data.db.OriginDatabase r5 = r7.database
            com.meharenterprises.originsms.data.db.ThreadLockDao r5 = r5.threadLockDao()
            java.lang.Object r8 = r6.invoke(r4)
            com.meharenterprises.originsms.data.db.ThreadLockEntity r8 = (com.meharenterprises.originsms.data.db.ThreadLockEntity) r8
            r9 = 0
            r0.L$0 = r9
            r0.L$1 = r9
            r9 = 2
            r0.label = r9
            java.lang.Object r4 = r5.upsert(r8, r0)
            if (r4 != r3) goto La3
            return r3
        La3:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.conversations.ConversationListViewModel.upsertWithChange(long, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void setLocked(long threadId, boolean locked) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$setLocked$1(this, threadId, locked, null), 3, null);
    }

    public final void setHidden(long threadId, boolean hidden) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$setHidden$1(this, threadId, hidden, null), 3, null);
    }

    public final void setMuted(Set<Long> threadIds, boolean muted) {
        Intrinsics.checkNotNullParameter(threadIds, "threadIds");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$setMuted$1(threadIds, this, muted, null), 3, null);
    }

    public final void setArchived(Set<Long> threadIds, boolean archived) {
        Intrinsics.checkNotNullParameter(threadIds, "threadIds");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$setArchived$1(threadIds, this, archived, null), 3, null);
    }

    public final void deleteThread(long threadId) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$deleteThread$1(this, threadId, null), 3, null);
    }

    public final void restoreFromTrash(Set<Long> threadIds) {
        Intrinsics.checkNotNullParameter(threadIds, "threadIds");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$restoreFromTrash$1(threadIds, this, null), 3, null);
    }

    public final void moveToTrash(Set<Long> threadIds) {
        Intrinsics.checkNotNullParameter(threadIds, "threadIds");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$moveToTrash$1(this, threadIds, null), 3, null);
    }

    public final void deleteThreads(Set<Long> threadIds) {
        Intrinsics.checkNotNullParameter(threadIds, "threadIds");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$deleteThreads$1(threadIds, this, null), 3, null);
    }

    public final void setPinned(Set<Long> threadIds, boolean pinned) {
        Intrinsics.checkNotNullParameter(threadIds, "threadIds");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$setPinned$1(threadIds, this, null), 3, null);
    }

    public final void markUnreadMultiple(Set<Long> threadIds) {
        Intrinsics.checkNotNullParameter(threadIds, "threadIds");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$markUnreadMultiple$1(threadIds, this, null), 3, null);
    }

    public final void blockSelectedThreads(Set<Long> threadIds, Context context) {
        Intrinsics.checkNotNullParameter(threadIds, "threadIds");
        Intrinsics.checkNotNullParameter(context, "context");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$blockSelectedThreads$1(threadIds, this, null), 3, null);
    }

    public final void markRead(long threadId) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$markRead$1(this, threadId, null), 3, null);
    }

    public final void markReadMultiple(Set<Long> threadIds) {
        Intrinsics.checkNotNullParameter(threadIds, "threadIds");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new ConversationListViewModel$markReadMultiple$1(threadIds, this, null), 3, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object getHiddenConversations(kotlin.coroutines.Continuation<? super java.util.List<com.meharenterprises.originsms.core.ConversationSummary>> r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$getHiddenConversations$1
            if (r0 == 0) goto L14
            r0 = r9
            com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$getHiddenConversations$1 r0 = (com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$getHiddenConversations$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$getHiddenConversations$1 r0 = new com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$getHiddenConversations$1
            r0.<init>(r8, r9)
        L19:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r9.label
            switch(r2) {
                case 0: goto L32;
                case 1: goto L2d;
                default: goto L25;
            }
        L25:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L2d:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r0
            goto L42
        L32:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r8
            com.meharenterprises.originsms.core.SmsRepository r3 = r2.repository
            r4 = 1
            r9.label = r4
            java.lang.Object r2 = r3.getConversations(r9)
            if (r2 != r1) goto L42
            return r1
        L42:
            r1 = r2
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            r2 = 0
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.Collection r3 = (java.util.Collection) r3
            r4 = 0
            java.util.Iterator r5 = r1.iterator()
        L52:
            boolean r1 = r5.hasNext()
            if (r1 == 0) goto L6a
            java.lang.Object r1 = r5.next()
            r6 = r1
            com.meharenterprises.originsms.core.ConversationSummary r6 = (com.meharenterprises.originsms.core.ConversationSummary) r6
            r7 = 0
            boolean r6 = r6.isHidden()
            if (r6 == 0) goto L52
            r3.add(r1)
            goto L52
        L6a:
            r1 = r3
            java.util.List r1 = (java.util.List) r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.conversations.ConversationListViewModel.getHiddenConversations(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object getArchivedConversations(kotlin.coroutines.Continuation<? super java.util.List<com.meharenterprises.originsms.core.ConversationSummary>> r11) {
        /*
            r10 = this;
            boolean r0 = r11 instanceof com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$getArchivedConversations$1
            if (r0 == 0) goto L14
            r0 = r11
            com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$getArchivedConversations$1 r0 = (com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$getArchivedConversations$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L19
        L14:
            com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$getArchivedConversations$1 r0 = new com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$getArchivedConversations$1
            r0.<init>(r10, r11)
        L19:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r11.label
            r3 = 1
            switch(r2) {
                case 0: goto L33;
                case 1: goto L2e;
                default: goto L26;
            }
        L26:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L2e:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r0
            goto L42
        L33:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r10
            com.meharenterprises.originsms.core.SmsRepository r4 = r2.repository
            r11.label = r3
            java.lang.Object r2 = r4.getConversations(r11)
            if (r2 != r1) goto L42
            return r1
        L42:
            r1 = r2
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            r2 = 0
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.Collection r4 = (java.util.Collection) r4
            r5 = 0
            java.util.Iterator r6 = r1.iterator()
        L52:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L75
            java.lang.Object r1 = r6.next()
            r7 = r1
            com.meharenterprises.originsms.core.ConversationSummary r7 = (com.meharenterprises.originsms.core.ConversationSummary) r7
            r8 = 0
            boolean r9 = r7.isArchived()
            if (r9 == 0) goto L6e
            boolean r9 = r7.isHidden()
            if (r9 != 0) goto L6e
            r7 = r3
            goto L6f
        L6e:
            r7 = 0
        L6f:
            if (r7 == 0) goto L52
            r4.add(r1)
            goto L52
        L75:
            r1 = r4
            java.util.List r1 = (java.util.List) r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.conversations.ConversationListViewModel.getArchivedConversations(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
