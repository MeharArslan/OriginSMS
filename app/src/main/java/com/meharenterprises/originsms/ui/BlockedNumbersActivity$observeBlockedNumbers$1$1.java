package com.meharenterprises.originsms.ui;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.data.db.BlockedNumberEntity;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BlockedNumbersActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.BlockedNumbersActivity$observeBlockedNumbers$1$1", f = "BlockedNumbersActivity.kt", i = {}, l = {50}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
public final class BlockedNumbersActivity$observeBlockedNumbers$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow<List<BlockedNumberEntity>> $flow;
    int label;
    final /* synthetic */ BlockedNumbersActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BlockedNumbersActivity$observeBlockedNumbers$1$1(Flow<? extends List<BlockedNumberEntity>> flow, BlockedNumbersActivity blockedNumbersActivity, Continuation<? super BlockedNumbersActivity$observeBlockedNumbers$1$1> continuation) {
        super(2, continuation);
        this.$flow = flow;
        this.this$0 = blockedNumbersActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new BlockedNumbersActivity$observeBlockedNumbers$1$1(this.$flow, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((BlockedNumbersActivity$observeBlockedNumbers$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                Flow<List<BlockedNumberEntity>> flow = this.$flow;
                final BlockedNumbersActivity blockedNumbersActivity = this.this$0;
                this.label = 1;
                if (flow.collect(new FlowCollector() { // from class: com.meharenterprises.originsms.ui.BlockedNumbersActivity$observeBlockedNumbers$1$1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object value, Continuation $completion) {
                        return emit((List<BlockedNumberEntity>) value, (Continuation<? super Unit>) $completion);
                    }

                    public final Object emit(List<BlockedNumberEntity> list, Continuation<? super Unit> continuation) {
                        BlockedNumberAdapter blockedNumberAdapter;
                        blockedNumberAdapter = BlockedNumbersActivity.this.adapter;
                        if (blockedNumberAdapter == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("adapter");
                            blockedNumberAdapter = null;
                        }
                        blockedNumberAdapter.submitList(list);
                        BlockedNumbersActivity.this.findViewById(R.id.emptyState).setVisibility(list.isEmpty() ? 0 : 8);
                        return Unit.INSTANCE;
                    }
                }, this) != coroutine_suspended) {
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
