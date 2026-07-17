package com.meharenterprises.originsms.ui.thread;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.core.SmsRepository;
import java.util.Iterator;
import java.util.Set;
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
/* compiled from: ThreadViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.thread.ThreadViewModel$deleteMessages$1", f = "ThreadViewModel.kt", i = {}, l = {275}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes10.dex */
public final class ThreadViewModel$deleteMessages$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Set<Long> $messageIds;
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ ThreadViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadViewModel$deleteMessages$1(Set<Long> set, ThreadViewModel threadViewModel, Continuation<? super ThreadViewModel$deleteMessages$1> continuation) {
        super(2, continuation);
        this.$messageIds = set;
        this.this$0 = threadViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThreadViewModel$deleteMessages$1(this.$messageIds, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ThreadViewModel$deleteMessages$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ThreadViewModel$deleteMessages$1 threadViewModel$deleteMessages$1;
        Iterator it;
        ThreadViewModel threadViewModel;
        SmsRepository smsRepository;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                threadViewModel$deleteMessages$1 = this;
                Iterable $this$forEach$iv = threadViewModel$deleteMessages$1.$messageIds;
                ThreadViewModel threadViewModel2 = threadViewModel$deleteMessages$1.this$0;
                it = $this$forEach$iv.iterator();
                threadViewModel = threadViewModel2;
                break;
            case 1:
                threadViewModel$deleteMessages$1 = this;
                it = (Iterator) threadViewModel$deleteMessages$1.L$1;
                threadViewModel = (ThreadViewModel) threadViewModel$deleteMessages$1.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        while (it.hasNext()) {
            Object element$iv = it.next();
            long it2 = ((Number) element$iv).longValue();
            smsRepository = threadViewModel.repository;
            threadViewModel$deleteMessages$1.L$0 = threadViewModel;
            threadViewModel$deleteMessages$1.L$1 = it;
            threadViewModel$deleteMessages$1.label = 1;
            if (smsRepository.deleteMessage(it2, threadViewModel$deleteMessages$1) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        threadViewModel$deleteMessages$1.this$0.loadMessages();
        return Unit.INSTANCE;
    }
}
