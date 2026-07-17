package com.meharenterprises.originsms.ui;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.ui.TrashActivity;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TrashActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.TrashActivity$onCreate$4$1$1", f = "TrashActivity.kt", i = {}, l = {82}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
public final class TrashActivity$onCreate$4$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Set<Long> $ids;
    int label;
    final /* synthetic */ TrashActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TrashActivity$onCreate$4$1$1(TrashActivity trashActivity, Set<Long> set, Continuation<? super TrashActivity$onCreate$4$1$1> continuation) {
        super(2, continuation);
        this.this$0 = trashActivity;
        this.$ids = set;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new TrashActivity$onCreate$4$1$1(this.this$0, this.$ids, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((TrashActivity$onCreate$4$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TrashActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.meharenterprises.originsms.ui.TrashActivity$onCreate$4$1$1$1", f = "TrashActivity.kt", i = {0}, l = {84, 85}, m = "invokeSuspend", n = {"tid"}, s = {"J$0"})
    /* renamed from: com.meharenterprises.originsms.ui.TrashActivity$onCreate$4$1$1$1, reason: invalid class name */
    /* loaded from: classes3.dex */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Set<Long> $ids;
        long J$0;
        Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ TrashActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Set<Long> set, TrashActivity trashActivity, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$ids = set;
            this.this$0 = trashActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$ids, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0007. Please report as an issue. */
        /* JADX WARN: Removed duplicated region for block: B:10:0x004f  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0082  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0099 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:23:0x009a  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00a1  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x009a -> B:7:0x009d). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r14) {
            /*
                r13 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r13.label
                r2 = 0
                switch(r1) {
                    case 0: goto L3a;
                    case 1: goto L25;
                    case 2: goto L12;
                    default: goto La;
                }
            La:
                java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r14.<init>(r0)
                throw r14
            L12:
                r1 = r13
                r3 = 0
                r4 = 0
                java.lang.Object r5 = r1.L$1
                java.util.Iterator r5 = (java.util.Iterator) r5
                java.lang.Object r6 = r1.L$0
                com.meharenterprises.originsms.ui.TrashActivity r6 = (com.meharenterprises.originsms.ui.TrashActivity) r6
                kotlin.ResultKt.throwOnFailure(r14)
                r11 = r5
                r5 = r3
                r3 = r11
                goto L9d
            L25:
                r1 = r13
                r3 = 0
                r4 = 0
                long r5 = r1.J$0
                java.lang.Object r7 = r1.L$1
                java.util.Iterator r7 = (java.util.Iterator) r7
                java.lang.Object r8 = r1.L$0
                com.meharenterprises.originsms.ui.TrashActivity r8 = (com.meharenterprises.originsms.ui.TrashActivity) r8
                kotlin.ResultKt.throwOnFailure(r14)
                r11 = r5
                r5 = r7
                r6 = r8
                r7 = r11
                goto L7c
            L3a:
                kotlin.ResultKt.throwOnFailure(r14)
                r1 = r13
                java.util.Set<java.lang.Long> r3 = r1.$ids
                java.lang.Iterable r3 = (java.lang.Iterable) r3
                com.meharenterprises.originsms.ui.TrashActivity r4 = r1.this$0
                r5 = 0
                java.util.Iterator r6 = r3.iterator()
            L49:
                boolean r3 = r6.hasNext()
                if (r3 == 0) goto La1
                java.lang.Object r3 = r6.next()
                r7 = r3
                java.lang.Number r7 = (java.lang.Number) r7
                long r7 = r7.longValue()
                r3 = 0
                com.meharenterprises.originsms.core.SmsRepository r9 = com.meharenterprises.originsms.ui.TrashActivity.access$getRepository$p(r4)
                if (r9 != 0) goto L67
                java.lang.String r9 = "repository"
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r9)
                r9 = r2
            L67:
                r1.L$0 = r4
                r1.L$1 = r6
                r1.J$0 = r7
                r10 = 1
                r1.label = r10
                java.lang.Object r9 = r9.deleteThread(r7, r1)
                if (r9 != r0) goto L77
                return r0
            L77:
                r11 = r4
                r4 = r3
                r3 = r5
                r5 = r6
                r6 = r11
            L7c:
                com.meharenterprises.originsms.data.db.OriginDatabase r9 = com.meharenterprises.originsms.ui.TrashActivity.access$getDatabase$p(r6)
                if (r9 != 0) goto L88
                java.lang.String r9 = "database"
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r9)
                r9 = r2
            L88:
                com.meharenterprises.originsms.data.db.ThreadLockDao r9 = r9.threadLockDao()
                r1.L$0 = r6
                r1.L$1 = r5
                r10 = 2
                r1.label = r10
                java.lang.Object r7 = r9.clear(r7, r1)
                if (r7 != r0) goto L9a
                return r0
            L9a:
                r11 = r5
                r5 = r3
                r3 = r11
            L9d:
                r4 = r6
                r6 = r3
                goto L49
            La1:
                kotlin.Unit r0 = kotlin.Unit.INSTANCE
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.TrashActivity$onCreate$4$1$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        TrashActivity$onCreate$4$1$1 trashActivity$onCreate$4$1$1;
        TrashActivity.TrashAdapter trashAdapter;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        TrashActivity.TrashAdapter trashAdapter2 = null;
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass1(this.$ids, this.this$0, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                trashActivity$onCreate$4$1$1 = this;
                break;
            case 1:
                trashActivity$onCreate$4$1$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        trashActivity$onCreate$4$1$1.this$0.selectedIds.clear();
        trashActivity$onCreate$4$1$1.this$0.selectionMode = false;
        trashAdapter = trashActivity$onCreate$4$1$1.this$0.adapter;
        if (trashAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            trashAdapter2 = trashAdapter;
        }
        trashAdapter2.notifyDataSetChanged();
        trashActivity$onCreate$4$1$1.this$0.updateSelectionBar();
        trashActivity$onCreate$4$1$1.this$0.loadTrash();
        return Unit.INSTANCE;
    }
}
