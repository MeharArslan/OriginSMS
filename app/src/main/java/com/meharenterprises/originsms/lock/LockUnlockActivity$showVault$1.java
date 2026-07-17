package com.meharenterprises.originsms.lock;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LockUnlockActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.lock.LockUnlockActivity$showVault$1", f = "LockUnlockActivity.kt", i = {}, l = {224}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes8.dex */
public final class LockUnlockActivity$showVault$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ LockUnlockActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockUnlockActivity$showVault$1(LockUnlockActivity lockUnlockActivity, Continuation<? super LockUnlockActivity$showVault$1> continuation) {
        super(2, continuation);
        this.this$0 = lockUnlockActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new LockUnlockActivity$showVault$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((LockUnlockActivity$showVault$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x000a. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:14:0x004e A[Catch: Exception -> 0x00e8, TRY_ENTER, TryCatch #2 {Exception -> 0x00e8, blocks: (B:11:0x0042, B:14:0x004e, B:16:0x0056, B:17:0x005a, B:19:0x0069, B:20:0x006d, B:22:0x0078, B:23:0x007c, B:25:0x0082, B:27:0x008a, B:28:0x008e, B:30:0x0099, B:31:0x009d, B:33:0x00c0, B:34:0x00c4, B:36:0x00da, B:37:0x00de), top: B:10:0x0042 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0082 A[Catch: Exception -> 0x00e8, TryCatch #2 {Exception -> 0x00e8, blocks: (B:11:0x0042, B:14:0x004e, B:16:0x0056, B:17:0x005a, B:19:0x0069, B:20:0x006d, B:22:0x0078, B:23:0x007c, B:25:0x0082, B:27:0x008a, B:28:0x008e, B:30:0x0099, B:31:0x009d, B:33:0x00c0, B:34:0x00c4, B:36:0x00da, B:37:0x00de), top: B:10:0x0042 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0126  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            Method dump skipped, instructions count: 312
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.lock.LockUnlockActivity$showVault$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
