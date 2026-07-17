package com.meharenterprises.originsms.ui.thread;

import androidx.constraintlayout.widget.ConstraintLayout;
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
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ThreadActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.thread.ThreadActivity$confirmHideChat$1$1$1", f = "ThreadActivity.kt", i = {}, l = {342}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes10.dex */
public final class ThreadActivity$confirmHideChat$1$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ThreadLockDao $dao;
    final /* synthetic */ ThreadLockEntity $existing;
    int label;
    final /* synthetic */ ThreadActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadActivity$confirmHideChat$1$1$1(ThreadLockDao threadLockDao, ThreadLockEntity threadLockEntity, ThreadActivity threadActivity, Continuation<? super ThreadActivity$confirmHideChat$1$1$1> continuation) {
        super(2, continuation);
        this.$dao = threadLockDao;
        this.$existing = threadLockEntity;
        this.this$0 = threadActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThreadActivity$confirmHideChat$1$1$1(this.$dao, this.$existing, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ThreadActivity$confirmHideChat$1$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ThreadLockEntity copy;
        ThreadActivity$confirmHideChat$1$1$1 threadActivity$confirmHideChat$1$1$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                ThreadLockDao threadLockDao = this.$dao;
                ThreadLockEntity threadLockEntity = this.$existing;
                Intrinsics.checkNotNull(threadLockEntity);
                copy = threadLockEntity.copy((r34 & 1) != 0 ? threadLockEntity.threadId : 0L, (r34 & 2) != 0 ? threadLockEntity.isLocked : false, (r34 & 4) != 0 ? threadLockEntity.isHidden : false, (r34 & 8) != 0 ? threadLockEntity.lockedAtMillis : 0L, (r34 & 16) != 0 ? threadLockEntity.isMuted : false, (r34 & 32) != 0 ? threadLockEntity.isArchived : false, (r34 & 64) != 0 ? threadLockEntity.muteUntilMillis : 0L, (r34 & 128) != 0 ? threadLockEntity.autoUnhideAtMillis : 0L, (r34 & 256) != 0 ? threadLockEntity.dailyHideTimeMinutes : -1, (r34 & 512) != 0 ? threadLockEntity.deletedAtMillis : 0L, (r34 & 1024) != 0 ? threadLockEntity.newChatStartMillis : 0L);
                this.label = 1;
                if (threadLockDao.upsert(copy, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                threadActivity$confirmHideChat$1$1$1 = this;
                break;
            case 1:
                threadActivity$confirmHideChat$1$1$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        threadActivity$confirmHideChat$1$1$1.this$0.finish();
        return Unit.INSTANCE;
    }
}
