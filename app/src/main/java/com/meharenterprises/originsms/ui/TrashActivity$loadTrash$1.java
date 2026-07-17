package com.meharenterprises.originsms.ui;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.data.db.OriginDatabase;
import com.meharenterprises.originsms.data.db.ThreadLockEntity;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TrashActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.TrashActivity$loadTrash$1", f = "TrashActivity.kt", i = {1}, l = {148, 151}, m = "invokeSuspend", n = {"allConvs"}, s = {"L$0"})
/* loaded from: classes3.dex */
public final class TrashActivity$loadTrash$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ TrashActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TrashActivity$loadTrash$1(TrashActivity trashActivity, Continuation<? super TrashActivity$loadTrash$1> continuation) {
        super(2, continuation);
        this.this$0 = trashActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new TrashActivity$loadTrash$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((TrashActivity$loadTrash$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0007. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x006f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0080  */
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
            r2 = 0
            switch(r1) {
                case 0: goto L28;
                case 1: goto L21;
                case 2: goto L12;
                default: goto La;
            }
        La:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L12:
            r0 = r10
            java.lang.Object r1 = r0.L$1
            com.meharenterprises.originsms.ui.TrashActivity r1 = (com.meharenterprises.originsms.ui.TrashActivity) r1
            java.lang.Object r3 = r0.L$0
            java.util.List r3 = (java.util.List) r3
            kotlin.ResultKt.throwOnFailure(r11)
            r4 = r1
            r1 = r11
            goto L73
        L21:
            r1 = r10
            kotlin.ResultKt.throwOnFailure(r11)
            r3 = r1
            r1 = r11
            goto L4c
        L28:
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            kotlinx.coroutines.CoroutineDispatcher r3 = kotlinx.coroutines.Dispatchers.getIO()
            kotlin.coroutines.CoroutineContext r3 = (kotlin.coroutines.CoroutineContext) r3
            com.meharenterprises.originsms.ui.TrashActivity$loadTrash$1$allConvs$1 r4 = new com.meharenterprises.originsms.ui.TrashActivity$loadTrash$1$allConvs$1
            com.meharenterprises.originsms.ui.TrashActivity r5 = r1.this$0
            r4.<init>(r5, r2)
            kotlin.jvm.functions.Function2 r4 = (kotlin.jvm.functions.Function2) r4
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r6 = 1
            r1.label = r6
            java.lang.Object r3 = kotlinx.coroutines.BuildersKt.withContext(r3, r4, r5)
            if (r3 != r0) goto L48
            return r0
        L48:
            r9 = r1
            r1 = r11
            r11 = r3
            r3 = r9
        L4c:
            java.util.List r11 = (java.util.List) r11
            com.meharenterprises.originsms.ui.TrashActivity r4 = r3.this$0
            kotlinx.coroutines.CoroutineDispatcher r5 = kotlinx.coroutines.Dispatchers.getIO()
            kotlin.coroutines.CoroutineContext r5 = (kotlin.coroutines.CoroutineContext) r5
            com.meharenterprises.originsms.ui.TrashActivity$loadTrash$1$1 r6 = new com.meharenterprises.originsms.ui.TrashActivity$loadTrash$1$1
            com.meharenterprises.originsms.ui.TrashActivity r7 = r3.this$0
            r6.<init>(r7, r2)
            kotlin.jvm.functions.Function2 r6 = (kotlin.jvm.functions.Function2) r6
            r7 = r3
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r3.L$0 = r11
            r3.L$1 = r4
            r8 = 2
            r3.label = r8
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r5, r6, r7)
            if (r5 != r0) goto L70
            return r0
        L70:
            r0 = r3
            r3 = r11
            r11 = r5
        L73:
            java.util.Map r11 = (java.util.Map) r11
            com.meharenterprises.originsms.ui.TrashActivity.access$setDeletedTimestamps$p(r4, r11)
            com.meharenterprises.originsms.ui.TrashActivity r11 = r0.this$0
            com.meharenterprises.originsms.ui.TrashActivity$TrashAdapter r11 = com.meharenterprises.originsms.ui.TrashActivity.access$getAdapter$p(r11)
            if (r11 != 0) goto L86
            java.lang.String r11 = "adapter"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r11)
            r11 = r2
        L86:
            r11.submitList(r3)
            com.meharenterprises.originsms.ui.TrashActivity r11 = r0.this$0
            android.widget.TextView r11 = com.meharenterprises.originsms.ui.TrashActivity.access$getEmptyState$p(r11)
            if (r11 != 0) goto L97
            java.lang.String r11 = "emptyState"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r11)
            r11 = r2
        L97:
            boolean r4 = r3.isEmpty()
            r5 = 0
            r6 = 8
            if (r4 == 0) goto La2
            r4 = r5
            goto La3
        La2:
            r4 = r6
        La3:
            r11.setVisibility(r4)
            com.meharenterprises.originsms.ui.TrashActivity r11 = r0.this$0
            androidx.recyclerview.widget.RecyclerView r11 = com.meharenterprises.originsms.ui.TrashActivity.access$getRecycler$p(r11)
            if (r11 != 0) goto Lb4
            java.lang.String r11 = "recycler"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r11)
            goto Lb5
        Lb4:
            r2 = r11
        Lb5:
            boolean r11 = r3.isEmpty()
            if (r11 == 0) goto Lbd
            r5 = r6
        Lbd:
            r2.setVisibility(r5)
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.TrashActivity$loadTrash$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TrashActivity.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0010\t\n\u0002\u0018\u0002\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.meharenterprises.originsms.ui.TrashActivity$loadTrash$1$1", f = "TrashActivity.kt", i = {}, l = {152}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.meharenterprises.originsms.ui.TrashActivity$loadTrash$1$1, reason: invalid class name */
    /* loaded from: classes3.dex */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Map<Long, ? extends Long>>, Object> {
        int label;
        final /* synthetic */ TrashActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(TrashActivity trashActivity, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = trashActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super Map<Long, ? extends Long>> continuation) {
            return invoke2(coroutineScope, (Continuation<? super Map<Long, Long>>) continuation);
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super Map<Long, Long>> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object $result) {
            OriginDatabase originDatabase;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    originDatabase = this.this$0.database;
                    if (originDatabase == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("database");
                        originDatabase = null;
                    }
                    this.label = 1;
                    Object trashedThreads = originDatabase.threadLockDao().getTrashedThreads(this);
                    if (trashedThreads == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    $result = trashedThreads;
                    break;
                case 1:
                    ResultKt.throwOnFailure($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Iterable $this$associate$iv = (Iterable) $result;
            int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associate$iv, 10)), 16);
            Map destination$iv$iv = new LinkedHashMap(capacity$iv);
            for (Object element$iv$iv : $this$associate$iv) {
                ThreadLockEntity it = (ThreadLockEntity) element$iv$iv;
                Pair pair = TuplesKt.to(Boxing.boxLong(it.getThreadId()), Boxing.boxLong(it.getDeletedAtMillis()));
                destination$iv$iv.put(pair.getFirst(), pair.getSecond());
            }
            return destination$iv$iv;
        }
    }
}
