package com.meharenterprises.originsms.ui.thread;

import android.widget.Toast;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.data.db.ThreadLockDao;
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
@DebugMetadata(c = "com.meharenterprises.originsms.ui.thread.ThreadActivity$toggleMute$1$1$1", f = "ThreadActivity.kt", i = {}, l = {TypedValues.AttributesType.TYPE_EASING}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes10.dex */
public final class ThreadActivity$toggleMute$1$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ThreadLockDao $dao;
    final /* synthetic */ long $muteUntil;
    int label;
    final /* synthetic */ ThreadActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadActivity$toggleMute$1$1$1(ThreadLockDao threadLockDao, ThreadActivity threadActivity, long j, Continuation<? super ThreadActivity$toggleMute$1$1$1> continuation) {
        super(2, continuation);
        this.$dao = threadLockDao;
        this.this$0 = threadActivity;
        this.$muteUntil = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThreadActivity$toggleMute$1$1$1(this.$dao, this.this$0, this.$muteUntil, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ThreadActivity$toggleMute$1$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        long j;
        ThreadActivity$toggleMute$1$1$1 threadActivity$toggleMute$1$1$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                ThreadLockDao threadLockDao = this.$dao;
                j = this.this$0.threadId;
                this.label = 1;
                if (threadLockDao.setMutedUntil(j, true, this.$muteUntil, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                threadActivity$toggleMute$1$1$1 = this;
                break;
            case 1:
                threadActivity$toggleMute$1$1$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Toast.makeText(threadActivity$toggleMute$1$1$1.this$0, threadActivity$toggleMute$1$1$1.this$0.getString(R.string.action_mute_chat), 0).show();
        threadActivity$toggleMute$1$1$1.this$0.invalidateOptionsMenu();
        return Unit.INSTANCE;
    }
}
