package com.meharenterprises.originsms.ui.conversations;

import com.meharenterprises.originsms.data.db.ThreadLockEntity;
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

/* compiled from: ConversationListViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$setLocked$1", f = "ConversationListViewModel.kt", i = {}, l = {85}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes11.dex */
final class ConversationListViewModel$setLocked$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ boolean $locked;
    final /* synthetic */ long $threadId;
    int label;
    final /* synthetic */ ConversationListViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConversationListViewModel$setLocked$1(ConversationListViewModel conversationListViewModel, long j, boolean z, Continuation<? super ConversationListViewModel$setLocked$1> continuation) {
        super(2, continuation);
        this.this$0 = conversationListViewModel;
        this.$threadId = j;
        this.$locked = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ConversationListViewModel$setLocked$1(this.this$0, this.$threadId, this.$locked, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ConversationListViewModel$setLocked$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Object upsertWithChange;
        ConversationListViewModel$setLocked$1 conversationListViewModel$setLocked$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                ConversationListViewModel conversationListViewModel = this.this$0;
                long j = this.$threadId;
                final boolean z = this.$locked;
                this.label = 1;
                upsertWithChange = conversationListViewModel.upsertWithChange(j, new Function1<ThreadLockEntity, ThreadLockEntity>() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$setLocked$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final ThreadLockEntity invoke(ThreadLockEntity it) {
                        ThreadLockEntity copy;
                        Intrinsics.checkNotNullParameter(it, "it");
                        copy = it.copy((r34 & 1) != 0 ? it.threadId : 0L, (r34 & 2) != 0 ? it.isLocked : z, (r34 & 4) != 0 ? it.isHidden : false, (r34 & 8) != 0 ? it.lockedAtMillis : z ? System.currentTimeMillis() : 0L, (r34 & 16) != 0 ? it.isMuted : false, (r34 & 32) != 0 ? it.isArchived : false, (r34 & 64) != 0 ? it.muteUntilMillis : 0L, (r34 & 128) != 0 ? it.autoUnhideAtMillis : 0L, (r34 & 256) != 0 ? it.dailyHideTimeMinutes : 0, (r34 & 512) != 0 ? it.deletedAtMillis : 0L, (r34 & 1024) != 0 ? it.newChatStartMillis : 0L);
                        return copy;
                    }
                }, this);
                if (upsertWithChange == coroutine_suspended) {
                    return coroutine_suspended;
                }
                conversationListViewModel$setLocked$1 = this;
                break;
            case 1:
                conversationListViewModel$setLocked$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        conversationListViewModel$setLocked$1.this$0.loadConversations();
        return Unit.INSTANCE;
    }
}
