package com.meharenterprises.originsms.ui.thread;

import com.meharenterprises.originsms.data.db.ThreadLockDao;
import com.meharenterprises.originsms.data.db.ThreadLockEntity;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ThreadActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.thread.ThreadActivity$confirmHideChat$1$3$1", f = "ThreadActivity.kt", i = {}, l = {366}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes10.dex */
public final class ThreadActivity$confirmHideChat$1$3$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ThreadLockDao $dao;
    final /* synthetic */ ThreadLockEntity $existing;
    int label;
    final /* synthetic */ ThreadActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadActivity$confirmHideChat$1$3$1(ThreadLockDao threadLockDao, ThreadLockEntity threadLockEntity, ThreadActivity threadActivity, Continuation<? super ThreadActivity$confirmHideChat$1$3$1> continuation) {
        super(2, continuation);
        this.$dao = threadLockDao;
        this.$existing = threadLockEntity;
        this.this$0 = threadActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThreadActivity$confirmHideChat$1$3$1(this.$dao, this.$existing, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ThreadActivity$confirmHideChat$1$3$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ThreadLockEntity copy;
        ThreadActivity$confirmHideChat$1$3$1 threadActivity$confirmHideChat$1$3$1;
        long j;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                ThreadLockDao threadLockDao = this.$dao;
                ThreadLockEntity threadLockEntity = this.$existing;
                if (threadLockEntity == null) {
                    j = this.this$0.threadId;
                    threadLockEntity = new ThreadLockEntity(j, false, false, 0L, false, false, 0L, 0L, 0, 0L, 0L, 2046, null);
                }
                copy = r26.copy((r34 & 1) != 0 ? r26.threadId : 0L, (r34 & 2) != 0 ? r26.isLocked : true, (r34 & 4) != 0 ? r26.isHidden : true, (r34 & 8) != 0 ? r26.lockedAtMillis : 0L, (r34 & 16) != 0 ? r26.isMuted : false, (r34 & 32) != 0 ? r26.isArchived : false, (r34 & 64) != 0 ? r26.muteUntilMillis : 0L, (r34 & 128) != 0 ? r26.autoUnhideAtMillis : 0L, (r34 & 256) != 0 ? r26.dailyHideTimeMinutes : 0, (r34 & 512) != 0 ? r26.deletedAtMillis : 0L, (r34 & 1024) != 0 ? threadLockEntity.newChatStartMillis : 0L);
                this.label = 1;
                if (threadLockDao.upsert(copy, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                threadActivity$confirmHideChat$1$3$1 = this;
                break;
            case 1:
                threadActivity$confirmHideChat$1$3$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        threadActivity$confirmHideChat$1$3$1.this$0.finish();
        return Unit.INSTANCE;
    }
}
