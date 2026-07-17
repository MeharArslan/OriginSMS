package com.meharenterprises.originsms.ui.conversations;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.core.SmsRepository;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: ConversationListViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$deleteThread$1", f = "ConversationListViewModel.kt", i = {}, l = {117}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes11.dex */
final class ConversationListViewModel$deleteThread$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ long $threadId;
    int label;
    final /* synthetic */ ConversationListViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConversationListViewModel$deleteThread$1(ConversationListViewModel conversationListViewModel, long j, Continuation<? super ConversationListViewModel$deleteThread$1> continuation) {
        super(2, continuation);
        this.this$0 = conversationListViewModel;
        this.$threadId = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ConversationListViewModel$deleteThread$1(this.this$0, this.$threadId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ConversationListViewModel$deleteThread$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        SmsRepository smsRepository;
        ConversationListViewModel$deleteThread$1 conversationListViewModel$deleteThread$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                smsRepository = this.this$0.repository;
                this.label = 1;
                if (smsRepository.deleteThread(this.$threadId, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                conversationListViewModel$deleteThread$1 = this;
                break;
            case 1:
                conversationListViewModel$deleteThread$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        conversationListViewModel$deleteThread$1.this$0.loadConversations();
        return Unit.INSTANCE;
    }
}
