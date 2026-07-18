package com.meharenterprises.originsms.lock;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LockUnlockActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.lock.LockUnlockActivity$onAuthenticated$1", f = "LockUnlockActivity.kt", i = {0}, l = {194, 195}, m = "invokeSuspend", n = {"database"}, s = {"L$0"})
/* loaded from: classes8.dex */
public final class LockUnlockActivity$onAuthenticated$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    int label;
    final /* synthetic */ LockUnlockActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockUnlockActivity$onAuthenticated$1(LockUnlockActivity lockUnlockActivity, Continuation<? super LockUnlockActivity$onAuthenticated$1> continuation) {
        super(2, continuation);
        this.this$0 = lockUnlockActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new LockUnlockActivity$onAuthenticated$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((LockUnlockActivity$onAuthenticated$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0006. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0063 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0064  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            switch(r1) {
                case 0: goto L1f;
                case 1: goto L16;
                case 2: goto L11;
                default: goto L9;
            }
        L9:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L11:
            r0 = r10
            kotlin.ResultKt.throwOnFailure(r11)
            goto L65
        L16:
            r1 = r10
            java.lang.Object r2 = r1.L$0
            com.meharenterprises.originsms.data.db.OriginDatabase r2 = (com.meharenterprises.originsms.data.db.OriginDatabase) r2
            kotlin.ResultKt.throwOnFailure(r11)
            goto L49
        L1f:
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            com.meharenterprises.originsms.data.db.OriginDatabase$Companion r2 = com.meharenterprises.originsms.data.db.OriginDatabase.INSTANCE
            com.meharenterprises.originsms.lock.LockUnlockActivity r3 = r1.this$0
            android.content.Context r3 = (android.content.Context) r3
            com.meharenterprises.originsms.data.db.OriginDatabase r2 = r2.getInstance(r3)
            com.meharenterprises.originsms.data.db.ThreadLockDao r3 = r2.threadLockDao()
            com.meharenterprises.originsms.lock.LockUnlockActivity r4 = r1.this$0
            long r4 = com.meharenterprises.originsms.lock.LockUnlockActivity.access$getThreadId$p(r4)
            r9 = r1
            kotlin.coroutines.Continuation r9 = (kotlin.coroutines.Continuation) r9
            r1.L$0 = r2
            r6 = 1
            r1.label = r6
            r6 = 0
            r7 = 0
            java.lang.Object r3 = r3.setLocked(r4, r6, r7, r9)
            if (r3 != r0) goto L49
            return r0
        L49:
            com.meharenterprises.originsms.data.db.ThreadLockDao r3 = r2.threadLockDao()
            com.meharenterprises.originsms.lock.LockUnlockActivity r4 = r1.this$0
            long r4 = com.meharenterprises.originsms.lock.LockUnlockActivity.access$getThreadId$p(r4)
            r6 = r1
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r7 = 0
            r1.L$0 = r7
            r7 = 2
            r1.label = r7
            r7 = 0
            java.lang.Object r2 = r3.setHidden(r4, r7, r6)
            if (r2 != r0) goto L64
            return r0
        L64:
            r0 = r1
        L65:
            com.meharenterprises.originsms.lock.LockUnlockActivity r1 = r0.this$0
            r2 = -1
            r1.setResult(r2)
            com.meharenterprises.originsms.lock.LockUnlockActivity r1 = r0.this$0
            r1.finish()
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.lock.LockUnlockActivity$onAuthenticated$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
