package com.meharenterprises.originsms.lock;

import androidx.core.location.LocationRequestCompat;
import com.meharenterprises.originsms.data.db.OriginDatabase;
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
/* compiled from: LockSetupActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.lock.LockSetupActivity$finalizeSetup$1", f = "LockSetupActivity.kt", i = {}, l = {LocationRequestCompat.QUALITY_BALANCED_POWER_ACCURACY}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes8.dex */
public final class LockSetupActivity$finalizeSetup$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ LockSetupActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockSetupActivity$finalizeSetup$1(LockSetupActivity lockSetupActivity, Continuation<? super LockSetupActivity$finalizeSetup$1> continuation) {
        super(2, continuation);
        this.this$0 = lockSetupActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new LockSetupActivity$finalizeSetup$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((LockSetupActivity$finalizeSetup$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        long j;
        boolean z;
        LockSetupActivity$finalizeSetup$1 lockSetupActivity$finalizeSetup$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                OriginDatabase database = OriginDatabase.INSTANCE.getInstance(this.this$0);
                ThreadLockDao threadLockDao = database.threadLockDao();
                j = this.this$0.threadIdToLock;
                z = this.this$0.alsoHide;
                this.label = 1;
                if (threadLockDao.upsert(new ThreadLockEntity(j, true, z, System.currentTimeMillis(), false, false, 0L, 0L, 0, 0L, 0L, 2032, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                lockSetupActivity$finalizeSetup$1 = this;
                break;
            case 1:
                lockSetupActivity$finalizeSetup$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        lockSetupActivity$finalizeSetup$1.this$0.finish();
        return Unit.INSTANCE;
    }
}
