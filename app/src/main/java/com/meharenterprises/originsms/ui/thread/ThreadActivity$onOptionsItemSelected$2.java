package com.meharenterprises.originsms.ui.thread;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: ThreadActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.thread.ThreadActivity$onOptionsItemSelected$2", f = "ThreadActivity.kt", i = {0}, l = {248, 249}, m = "invokeSuspend", n = {"dao"}, s = {"L$0"})
/* loaded from: classes10.dex */
final class ThreadActivity$onOptionsItemSelected$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    int label;
    final /* synthetic */ ThreadActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadActivity$onOptionsItemSelected$2(ThreadActivity threadActivity, Continuation<? super ThreadActivity$onOptionsItemSelected$2> continuation) {
        super(2, continuation);
        this.this$0 = threadActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThreadActivity$onOptionsItemSelected$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ThreadActivity$onOptionsItemSelected$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00aa A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x007b  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r47) {
        /*
            r46 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r46
            int r2 = r1.label
            switch(r2) {
                case 0: goto L29;
                case 1: goto L1c;
                case 2: goto L13;
                default: goto Lb;
            }
        Lb:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L13:
            r0 = r46
            r2 = r47
            kotlin.ResultKt.throwOnFailure(r2)
            goto Lad
        L1c:
            r2 = r46
            r3 = r47
            java.lang.Object r4 = r2.L$0
            com.meharenterprises.originsms.data.db.ThreadLockDao r4 = (com.meharenterprises.originsms.data.db.ThreadLockDao) r4
            kotlin.ResultKt.throwOnFailure(r3)
            r5 = r3
            goto L53
        L29:
            kotlin.ResultKt.throwOnFailure(r47)
            r2 = r46
            r3 = r47
            com.meharenterprises.originsms.data.db.OriginDatabase$Companion r4 = com.meharenterprises.originsms.data.db.OriginDatabase.INSTANCE
            com.meharenterprises.originsms.ui.thread.ThreadActivity r5 = r2.this$0
            android.content.Context r5 = (android.content.Context) r5
            com.meharenterprises.originsms.data.db.OriginDatabase r4 = r4.getInstance(r5)
            com.meharenterprises.originsms.data.db.ThreadLockDao r4 = r4.threadLockDao()
            com.meharenterprises.originsms.ui.thread.ThreadActivity r5 = r2.this$0
            long r5 = com.meharenterprises.originsms.ui.thread.ThreadActivity.access$getThreadId$p(r5)
            r7 = r2
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r2.L$0 = r4
            r8 = 1
            r2.label = r8
            java.lang.Object r5 = r4.getForThread(r5, r7)
            if (r5 != r0) goto L53
            return r0
        L53:
            com.meharenterprises.originsms.data.db.ThreadLockEntity r5 = (com.meharenterprises.originsms.data.db.ThreadLockEntity) r5
            if (r5 != 0) goto L7b
            com.meharenterprises.originsms.data.db.ThreadLockEntity r5 = new com.meharenterprises.originsms.data.db.ThreadLockEntity
            r6 = r5
            com.meharenterprises.originsms.ui.thread.ThreadActivity r7 = r2.this$0
            long r7 = com.meharenterprises.originsms.ui.thread.ThreadActivity.access$getThreadId$p(r7)
            r24 = 2046(0x7fe, float:2.867E-42)
            r25 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r17 = 0
            r19 = 0
            r20 = 0
            r22 = 0
            r6.<init>(r7, r9, r10, r11, r13, r14, r15, r17, r19, r20, r22, r24, r25)
            r26 = r5
            goto L7d
        L7b:
            r26 = r5
        L7d:
            r44 = 2015(0x7df, float:2.824E-42)
            r45 = 0
            r27 = 0
            r29 = 0
            r30 = 0
            r31 = 0
            r33 = 0
            r34 = 1
            r35 = 0
            r37 = 0
            r39 = 0
            r40 = 0
            r42 = 0
            com.meharenterprises.originsms.data.db.ThreadLockEntity r5 = com.meharenterprises.originsms.data.db.ThreadLockEntity.copy$default(r26, r27, r29, r30, r31, r33, r34, r35, r37, r39, r40, r42, r44, r45)
            r6 = r2
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r7 = 0
            r2.L$0 = r7
            r7 = 2
            r2.label = r7
            java.lang.Object r4 = r4.upsert(r5, r6)
            if (r4 != r0) goto Lab
            return r0
        Lab:
            r0 = r2
            r2 = r3
        Lad:
            com.meharenterprises.originsms.ui.thread.ThreadActivity r3 = r0.this$0
            android.content.Context r3 = (android.content.Context) r3
            com.meharenterprises.originsms.ui.thread.ThreadActivity r4 = r0.this$0
            int r5 = com.meharenterprises.originsms.R.string.menu_archive_chat
            java.lang.String r4 = r4.getString(r5)
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4
            r5 = 0
            android.widget.Toast r3 = android.widget.Toast.makeText(r3, r4, r5)
            r3.show()
            com.meharenterprises.originsms.ui.thread.ThreadActivity r3 = r0.this$0
            r3.finish()
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.thread.ThreadActivity$onOptionsItemSelected$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
