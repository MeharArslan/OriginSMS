package com.meharenterprises.originsms.ui;

import android.widget.Toast;
import androidx.core.text.HtmlCompat;
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
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TrashActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.TrashActivity$onCreate$3$1", f = "TrashActivity.kt", i = {}, l = {LockFreeTaskQueueCore.CLOSED_SHIFT}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
public final class TrashActivity$onCreate$3$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Set<Long> $ids;
    int label;
    final /* synthetic */ TrashActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TrashActivity$onCreate$3$1(TrashActivity trashActivity, Set<Long> set, Continuation<? super TrashActivity$onCreate$3$1> continuation) {
        super(2, continuation);
        this.this$0 = trashActivity;
        this.$ids = set;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new TrashActivity$onCreate$3$1(this.this$0, this.$ids, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((TrashActivity$onCreate$3$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TrashActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.meharenterprises.originsms.ui.TrashActivity$onCreate$3$1$1", f = "TrashActivity.kt", i = {}, l = {HtmlCompat.FROM_HTML_MODE_COMPACT, 64}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.meharenterprises.originsms.ui.TrashActivity$onCreate$3$1$1, reason: invalid class name */
    /* loaded from: classes3.dex */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Set<Long> $ids;
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

        /* JADX WARN: Failed to find 'out' block for switch in B:2:0x000a. Please report as an issue. */
        /* JADX WARN: Removed duplicated region for block: B:10:0x0056  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0089  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00cb  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00ce  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x00c3 -> B:7:0x00c7). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x00cb -> B:8:0x0050). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r33) {
            /*
                Method dump skipped, instructions count: 220
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.TrashActivity$onCreate$3$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        TrashActivity$onCreate$3$1 trashActivity$onCreate$3$1;
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
                trashActivity$onCreate$3$1 = this;
                break;
            case 1:
                trashActivity$onCreate$3$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        trashActivity$onCreate$3$1.this$0.selectedIds.clear();
        trashActivity$onCreate$3$1.this$0.selectionMode = false;
        trashAdapter = trashActivity$onCreate$3$1.this$0.adapter;
        if (trashAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            trashAdapter2 = trashAdapter;
        }
        trashAdapter2.notifyDataSetChanged();
        trashActivity$onCreate$3$1.this$0.updateSelectionBar();
        Toast.makeText(trashActivity$onCreate$3$1.this$0, trashActivity$onCreate$3$1.$ids.size() + " restored", 0).show();
        trashActivity$onCreate$3$1.this$0.loadTrash();
        return Unit.INSTANCE;
    }
}
