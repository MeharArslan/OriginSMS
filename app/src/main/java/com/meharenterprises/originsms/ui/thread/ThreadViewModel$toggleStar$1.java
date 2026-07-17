package com.meharenterprises.originsms.ui.thread;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.meharenterprises.originsms.core.Message;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ThreadViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.thread.ThreadViewModel$toggleStar$1", f = "ThreadViewModel.kt", i = {0}, l = {247, 248, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION}, m = "invokeSuspend", n = {"dao"}, s = {"L$0"})
/* loaded from: classes10.dex */
public final class ThreadViewModel$toggleStar$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Message $message;
    Object L$0;
    int label;
    final /* synthetic */ ThreadViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadViewModel$toggleStar$1(ThreadViewModel threadViewModel, Message message, Continuation<? super ThreadViewModel$toggleStar$1> continuation) {
        super(2, continuation);
        this.this$0 = threadViewModel;
        this.$message = message;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThreadViewModel$toggleStar$1(this.this$0, this.$message, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ThreadViewModel$toggleStar$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0078  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r21) {
        /*
            r20 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r20
            int r2 = r1.label
            switch(r2) {
                case 0: goto L31;
                case 1: goto L24;
                case 2: goto L1c;
                case 3: goto L13;
                default: goto Lb;
            }
        Lb:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L13:
            r0 = r20
            r2 = r21
            kotlin.ResultKt.throwOnFailure(r2)
            goto Lb4
        L1c:
            r0 = r20
            r2 = r21
            kotlin.ResultKt.throwOnFailure(r2)
            goto L77
        L24:
            r2 = r20
            r3 = r21
            java.lang.Object r4 = r2.L$0
            com.meharenterprises.originsms.data.db.StarredMessageDao r4 = (com.meharenterprises.originsms.data.db.StarredMessageDao) r4
            kotlin.ResultKt.throwOnFailure(r3)
            r5 = r3
            goto L57
        L31:
            kotlin.ResultKt.throwOnFailure(r21)
            r2 = r20
            r3 = r21
            com.meharenterprises.originsms.ui.thread.ThreadViewModel r4 = r2.this$0
            com.meharenterprises.originsms.data.db.OriginDatabase r4 = com.meharenterprises.originsms.ui.thread.ThreadViewModel.access$getDatabase$p(r4)
            com.meharenterprises.originsms.data.db.StarredMessageDao r4 = r4.starredMessageDao()
            com.meharenterprises.originsms.core.Message r5 = r2.$message
            long r5 = r5.getId()
            r7 = r2
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r2.L$0 = r4
            r8 = 1
            r2.label = r8
            java.lang.Object r5 = r4.isStarred(r5, r7)
            if (r5 != r0) goto L57
            return r0
        L57:
            java.lang.Number r5 = (java.lang.Number) r5
            int r5 = r5.intValue()
            r6 = 0
            if (r5 <= 0) goto L78
            com.meharenterprises.originsms.core.Message r5 = r2.$message
            long r7 = r5.getId()
            r5 = r2
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r2.L$0 = r6
            r6 = 2
            r2.label = r6
            java.lang.Object r4 = r4.unstar(r7, r5)
            if (r4 != r0) goto L75
            return r0
        L75:
            r0 = r2
            r2 = r3
        L77:
            goto Lb5
        L78:
            com.meharenterprises.originsms.data.db.StarredMessageEntity r5 = new com.meharenterprises.originsms.data.db.StarredMessageEntity
            com.meharenterprises.originsms.core.Message r7 = r2.$message
            long r8 = r7.getId()
            com.meharenterprises.originsms.core.Message r7 = r2.$message
            long r10 = r7.getThreadId()
            com.meharenterprises.originsms.core.Message r7 = r2.$message
            java.lang.String r12 = r7.getAddress()
            com.meharenterprises.originsms.core.Message r7 = r2.$message
            java.lang.String r13 = r7.getBody()
            com.meharenterprises.originsms.core.Message r7 = r2.$message
            long r14 = r7.getDateMillis()
            r18 = 32
            r19 = 0
            r16 = 0
            r7 = r5
            r7.<init>(r8, r10, r12, r13, r14, r16, r18, r19)
            r7 = r2
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r2.L$0 = r6
            r6 = 3
            r2.label = r6
            java.lang.Object r4 = r4.star(r5, r7)
            if (r4 != r0) goto Lb2
            return r0
        Lb2:
            r0 = r2
            r2 = r3
        Lb4:
        Lb5:
            com.meharenterprises.originsms.ui.thread.ThreadViewModel r3 = r0.this$0
            r3.loadMessages()
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.thread.ThreadViewModel$toggleStar$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
