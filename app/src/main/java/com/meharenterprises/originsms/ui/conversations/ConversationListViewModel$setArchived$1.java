package com.meharenterprises.originsms.ui.conversations;

import androidx.appcompat.app.AppCompatDelegate;
import com.meharenterprises.originsms.data.db.ThreadLockEntity;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConversationListViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$setArchived$1", f = "ConversationListViewModel.kt", i = {}, l = {AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes11.dex */
public final class ConversationListViewModel$setArchived$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ boolean $archived;
    final /* synthetic */ Set<Long> $threadIds;
    Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ ConversationListViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConversationListViewModel$setArchived$1(Set<Long> set, ConversationListViewModel conversationListViewModel, boolean z, Continuation<? super ConversationListViewModel$setArchived$1> continuation) {
        super(2, continuation);
        this.$threadIds = set;
        this.this$0 = conversationListViewModel;
        this.$archived = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ConversationListViewModel$setArchived$1(this.$threadIds, this.this$0, this.$archived, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ConversationListViewModel$setArchived$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ConversationListViewModel$setArchived$1 conversationListViewModel$setArchived$1;
        boolean z;
        Iterator it;
        ConversationListViewModel conversationListViewModel;
        Object upsertWithChange;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                conversationListViewModel$setArchived$1 = this;
                Iterable $this$forEach$iv = conversationListViewModel$setArchived$1.$threadIds;
                ConversationListViewModel conversationListViewModel2 = conversationListViewModel$setArchived$1.this$0;
                z = conversationListViewModel$setArchived$1.$archived;
                it = $this$forEach$iv.iterator();
                conversationListViewModel = conversationListViewModel2;
                break;
            case 1:
                conversationListViewModel$setArchived$1 = this;
                z = conversationListViewModel$setArchived$1.Z$0;
                it = (Iterator) conversationListViewModel$setArchived$1.L$1;
                conversationListViewModel = (ConversationListViewModel) conversationListViewModel$setArchived$1.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        while (it.hasNext()) {
            Object element$iv = it.next();
            long longValue = ((Number) element$iv).longValue();
            final boolean z2 = z;
            Function1<ThreadLockEntity, ThreadLockEntity> function1 = new Function1<ThreadLockEntity, ThreadLockEntity>() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$setArchived$1$1$1
                /* JADX INFO: Access modifiers changed from: package-private */
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ThreadLockEntity invoke(ThreadLockEntity it2) {
                    ThreadLockEntity copy;
                    Intrinsics.checkNotNullParameter(it2, "it");
                    copy = it2.copy((r34 & 1) != 0 ? it2.threadId : 0L, (r34 & 2) != 0 ? it2.isLocked : false, (r34 & 4) != 0 ? it2.isHidden : false, (r34 & 8) != 0 ? it2.lockedAtMillis : 0L, (r34 & 16) != 0 ? it2.isMuted : false, (r34 & 32) != 0 ? it2.isArchived : z2, (r34 & 64) != 0 ? it2.muteUntilMillis : 0L, (r34 & 128) != 0 ? it2.autoUnhideAtMillis : 0L, (r34 & 256) != 0 ? it2.dailyHideTimeMinutes : 0, (r34 & 512) != 0 ? it2.deletedAtMillis : 0L, (r34 & 1024) != 0 ? it2.newChatStartMillis : 0L);
                    return copy;
                }
            };
            conversationListViewModel$setArchived$1.L$0 = conversationListViewModel;
            conversationListViewModel$setArchived$1.L$1 = it;
            conversationListViewModel$setArchived$1.Z$0 = z;
            conversationListViewModel$setArchived$1.label = 1;
            upsertWithChange = conversationListViewModel.upsertWithChange(longValue, function1, conversationListViewModel$setArchived$1);
            if (upsertWithChange == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        conversationListViewModel$setArchived$1.this$0.loadConversations();
        return Unit.INSTANCE;
    }
}
