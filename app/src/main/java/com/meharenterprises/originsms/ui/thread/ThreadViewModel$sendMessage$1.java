package com.meharenterprises.originsms.ui.thread;

import androidx.constraintlayout.widget.ConstraintLayout;
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
@DebugMetadata(c = "com.meharenterprises.originsms.ui.thread.ThreadViewModel$sendMessage$1", f = "ThreadViewModel.kt", i = {}, l = {206, 211, 225}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes10.dex */
public final class ThreadViewModel$sendMessage$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ ThreadViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadViewModel$sendMessage$1(ThreadViewModel threadViewModel, Continuation<? super ThreadViewModel$sendMessage$1> continuation) {
        super(2, continuation);
        this.this$0 = threadViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThreadViewModel$sendMessage$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ThreadViewModel$sendMessage$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0009. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:13:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0051  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r11.label
            r2 = 0
            r3 = -1
            switch(r1) {
                case 0: goto L26;
                case 1: goto L21;
                case 2: goto L1a;
                case 3: goto L14;
                default: goto Lc;
            }
        Lc:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r0)
            throw r12
        L14:
            r0 = r11
            kotlin.ResultKt.throwOnFailure(r12)
            goto L9e
        L1a:
            r1 = r11
            kotlin.ResultKt.throwOnFailure(r12)
            r5 = r1
            r1 = r12
            goto L71
        L21:
            r1 = r11
            kotlin.ResultKt.throwOnFailure(r12)
            goto L47
        L26:
            kotlin.ResultKt.throwOnFailure(r12)
            r1 = r11
            com.meharenterprises.originsms.ui.thread.ThreadViewModel r5 = r1.this$0
            com.meharenterprises.originsms.data.db.OriginDatabase r5 = com.meharenterprises.originsms.ui.thread.ThreadViewModel.access$getDatabase$p(r5)
            com.meharenterprises.originsms.data.db.DraftDao r5 = r5.draftDao()
            com.meharenterprises.originsms.ui.thread.ThreadViewModel r6 = r1.this$0
            long r6 = com.meharenterprises.originsms.ui.thread.ThreadViewModel.access$getCurrentThreadId$p(r6)
            r8 = r1
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            r9 = 1
            r1.label = r9
            java.lang.Object r5 = r5.clearDraft(r6, r8)
            if (r5 != r0) goto L47
            return r0
        L47:
            com.meharenterprises.originsms.ui.thread.ThreadViewModel r5 = r1.this$0
            long r5 = com.meharenterprises.originsms.ui.thread.ThreadViewModel.access$getCurrentThreadId$p(r5)
            int r5 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r5 != 0) goto La2
            kotlinx.coroutines.CoroutineDispatcher r5 = kotlinx.coroutines.Dispatchers.getIO()
            kotlin.coroutines.CoroutineContext r5 = (kotlin.coroutines.CoroutineContext) r5
            com.meharenterprises.originsms.ui.thread.ThreadViewModel$sendMessage$1$resolvedId$1 r6 = new com.meharenterprises.originsms.ui.thread.ThreadViewModel$sendMessage$1$resolvedId$1
            com.meharenterprises.originsms.ui.thread.ThreadViewModel r7 = r1.this$0
            r6.<init>(r7, r2)
            kotlin.jvm.functions.Function2 r6 = (kotlin.jvm.functions.Function2) r6
            r7 = r1
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r8 = 2
            r1.label = r8
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r5, r6, r7)
            if (r5 != r0) goto L6d
            return r0
        L6d:
            r10 = r1
            r1 = r12
            r12 = r5
            r5 = r10
        L71:
            java.lang.Number r12 = (java.lang.Number) r12
            long r6 = r12.longValue()
            int r12 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r12 == 0) goto La0
            com.meharenterprises.originsms.ui.thread.ThreadViewModel r12 = r5.this$0
            com.meharenterprises.originsms.ui.thread.ThreadViewModel.access$setCurrentThreadId$p(r12, r6)
            kotlinx.coroutines.CoroutineDispatcher r12 = kotlinx.coroutines.Dispatchers.getIO()
            kotlin.coroutines.CoroutineContext r12 = (kotlin.coroutines.CoroutineContext) r12
            com.meharenterprises.originsms.ui.thread.ThreadViewModel$sendMessage$1$1 r3 = new com.meharenterprises.originsms.ui.thread.ThreadViewModel$sendMessage$1$1
            com.meharenterprises.originsms.ui.thread.ThreadViewModel r4 = r5.this$0
            r3.<init>(r4, r6, r2)
            kotlin.jvm.functions.Function2 r3 = (kotlin.jvm.functions.Function2) r3
            r2 = r5
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r4 = 3
            r5.label = r4
            java.lang.Object r12 = kotlinx.coroutines.BuildersKt.withContext(r12, r3, r2)
            if (r12 != r0) goto L9c
            return r0
        L9c:
            r12 = r1
            r0 = r5
        L9e:
            r1 = r0
            goto La2
        La0:
            r12 = r1
            r1 = r5
        La2:
            com.meharenterprises.originsms.ui.thread.ThreadViewModel r0 = r1.this$0
            r0.loadMessages()
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.thread.ThreadViewModel$sendMessage$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ThreadViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.meharenterprises.originsms.ui.thread.ThreadViewModel$sendMessage$1$1", f = "ThreadViewModel.kt", i = {}, l = {226, 230}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.meharenterprises.originsms.ui.thread.ThreadViewModel$sendMessage$1$1, reason: invalid class name */
    /* loaded from: classes10.dex */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $resolvedId;
        int label;
        final /* synthetic */ ThreadViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(ThreadViewModel threadViewModel, long j, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = threadViewModel;
            this.$resolvedId = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.this$0, this.$resolvedId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0049  */
        /* JADX WARN: Removed duplicated region for block: B:15:0x00a5 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:16:0x00a6  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x0068  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r46) {
            /*
                r45 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                r1 = r45
                int r2 = r1.label
                switch(r2) {
                    case 0: goto L25;
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
                r0 = r45
                r2 = r46
                kotlin.ResultKt.throwOnFailure(r2)
                goto La8
            L1c:
                r2 = r45
                r3 = r46
                kotlin.ResultKt.throwOnFailure(r3)
                r4 = r3
                goto L45
            L25:
                kotlin.ResultKt.throwOnFailure(r46)
                r2 = r45
                r3 = r46
                com.meharenterprises.originsms.ui.thread.ThreadViewModel r4 = r2.this$0
                com.meharenterprises.originsms.data.db.OriginDatabase r4 = com.meharenterprises.originsms.ui.thread.ThreadViewModel.access$getDatabase$p(r4)
                com.meharenterprises.originsms.data.db.ThreadLockDao r4 = r4.threadLockDao()
                long r5 = r2.$resolvedId
                r7 = r2
                kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
                r8 = 1
                r2.label = r8
                java.lang.Object r4 = r4.getForThread(r5, r7)
                if (r4 != r0) goto L45
                return r0
            L45:
                com.meharenterprises.originsms.data.db.ThreadLockEntity r4 = (com.meharenterprises.originsms.data.db.ThreadLockEntity) r4
                if (r4 != 0) goto L68
                com.meharenterprises.originsms.data.db.ThreadLockEntity r4 = new com.meharenterprises.originsms.data.db.ThreadLockEntity
                r5 = r4
                long r6 = r2.$resolvedId
                r23 = 2046(0x7fe, float:2.867E-42)
                r24 = 0
                r8 = 0
                r9 = 0
                r10 = 0
                r12 = 0
                r13 = 0
                r14 = 0
                r16 = 0
                r18 = 0
                r19 = 0
                r21 = 0
                r5.<init>(r6, r8, r9, r10, r12, r13, r14, r16, r18, r19, r21, r23, r24)
                r25 = r4
                goto L6a
            L68:
                r25 = r4
            L6a:
                com.meharenterprises.originsms.ui.thread.ThreadViewModel r4 = r2.this$0
                com.meharenterprises.originsms.data.db.OriginDatabase r4 = com.meharenterprises.originsms.ui.thread.ThreadViewModel.access$getDatabase$p(r4)
                com.meharenterprises.originsms.data.db.ThreadLockDao r4 = r4.threadLockDao()
                com.meharenterprises.originsms.ui.thread.ThreadViewModel r5 = r2.this$0
                long r41 = com.meharenterprises.originsms.ui.thread.ThreadViewModel.access$getNewChatStartedAt$p(r5)
                r43 = 511(0x1ff, float:7.16E-43)
                r44 = 0
                r26 = 0
                r28 = 0
                r29 = 0
                r30 = 0
                r32 = 0
                r33 = 0
                r34 = 0
                r36 = 0
                r38 = 0
                r39 = 0
                com.meharenterprises.originsms.data.db.ThreadLockEntity r5 = com.meharenterprises.originsms.data.db.ThreadLockEntity.copy$default(r25, r26, r28, r29, r30, r32, r33, r34, r36, r38, r39, r41, r43, r44)
                r6 = r2
                kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
                r7 = 2
                r2.label = r7
                java.lang.Object r4 = r4.upsert(r5, r6)
                if (r4 != r0) goto La6
                return r0
            La6:
                r0 = r2
                r2 = r3
            La8:
                kotlin.Unit r3 = kotlin.Unit.INSTANCE
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.thread.ThreadViewModel$sendMessage$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }
}
