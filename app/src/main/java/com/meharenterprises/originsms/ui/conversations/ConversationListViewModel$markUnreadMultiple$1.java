package com.meharenterprises.originsms.ui.conversations;

import android.content.ContentValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.core.SmsRepository;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConversationListViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$markUnreadMultiple$1", f = "ConversationListViewModel.kt", i = {}, l = {165}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes11.dex */
public final class ConversationListViewModel$markUnreadMultiple$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Set<Long> $threadIds;
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ ConversationListViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConversationListViewModel$markUnreadMultiple$1(Set<Long> set, ConversationListViewModel conversationListViewModel, Continuation<? super ConversationListViewModel$markUnreadMultiple$1> continuation) {
        super(2, continuation);
        this.$threadIds = set;
        this.this$0 = conversationListViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ConversationListViewModel$markUnreadMultiple$1(this.$threadIds, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ConversationListViewModel$markUnreadMultiple$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ConversationListViewModel$markUnreadMultiple$1 conversationListViewModel$markUnreadMultiple$1;
        Iterator it;
        ConversationListViewModel conversationListViewModel;
        SmsRepository smsRepository;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                conversationListViewModel$markUnreadMultiple$1 = this;
                Iterable $this$forEach$iv = conversationListViewModel$markUnreadMultiple$1.$threadIds;
                ConversationListViewModel conversationListViewModel2 = conversationListViewModel$markUnreadMultiple$1.this$0;
                it = $this$forEach$iv.iterator();
                conversationListViewModel = conversationListViewModel2;
                break;
            case 1:
                conversationListViewModel$markUnreadMultiple$1 = this;
                it = (Iterator) conversationListViewModel$markUnreadMultiple$1.L$1;
                conversationListViewModel = (ConversationListViewModel) conversationListViewModel$markUnreadMultiple$1.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        while (it.hasNext()) {
            Object element$iv = it.next();
            long id = ((Number) element$iv).longValue();
            ContentValues $this$invokeSuspend_u24lambda_u241_u24lambda_u240 = new ContentValues();
            $this$invokeSuspend_u24lambda_u241_u24lambda_u240.put("read", Boxing.boxInt(0));
            smsRepository = conversationListViewModel.repository;
            conversationListViewModel$markUnreadMultiple$1.L$0 = conversationListViewModel;
            conversationListViewModel$markUnreadMultiple$1.L$1 = it;
            conversationListViewModel$markUnreadMultiple$1.label = 1;
            if (smsRepository.markThreadUnread(id, conversationListViewModel$markUnreadMultiple$1) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        conversationListViewModel$markUnreadMultiple$1.this$0.loadConversations();
        return Unit.INSTANCE;
    }
}
