package com.meharenterprises.originsms.lock;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.core.ConversationSummary;
import com.meharenterprises.originsms.core.SmsRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: LockUnlockActivity.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "Lcom/meharenterprises/originsms/core/ConversationSummary;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.lock.LockUnlockActivity$showVault$1$hidden$1", f = "LockUnlockActivity.kt", i = {}, l = {225}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes8.dex */
final class LockUnlockActivity$showVault$1$hidden$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<? extends ConversationSummary>>, Object> {
    int label;
    final /* synthetic */ LockUnlockActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockUnlockActivity$showVault$1$hidden$1(LockUnlockActivity lockUnlockActivity, Continuation<? super LockUnlockActivity$showVault$1$hidden$1> continuation) {
        super(2, continuation);
        this.this$0 = lockUnlockActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new LockUnlockActivity$showVault$1$hidden$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super List<? extends ConversationSummary>> continuation) {
        return invoke2(coroutineScope, (Continuation<? super List<ConversationSummary>>) continuation);
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super List<ConversationSummary>> continuation) {
        return ((LockUnlockActivity$showVault$1$hidden$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                Object conversations = new SmsRepository(this.this$0).getConversations(this);
                if (conversations == coroutine_suspended) {
                    return coroutine_suspended;
                }
                $result = conversations;
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Iterable $this$filterTo$iv$iv = (Iterable) $result;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            ConversationSummary it = (ConversationSummary) element$iv$iv;
            if (it.isHidden()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }
}
