package com.meharenterprises.originsms.ui.conversations;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import com.meharenterprises.originsms.core.ConversationSummary;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConversationListViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$loadConversations$1", f = "ConversationListViewModel.kt", i = {}, l = {ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_TAG}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes11.dex */
public final class ConversationListViewModel$loadConversations$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ ConversationListViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConversationListViewModel$loadConversations$1(ConversationListViewModel conversationListViewModel, Continuation<? super ConversationListViewModel$loadConversations$1> continuation) {
        super(2, continuation);
        this.this$0 = conversationListViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ConversationListViewModel$loadConversations$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ConversationListViewModel$loadConversations$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ConversationListViewModel$loadConversations$1 conversationListViewModel$loadConversations$1;
        MutableLiveData mutableLiveData;
        MutableLiveData mutableLiveData2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                conversationListViewModel$loadConversations$1 = this;
                mutableLiveData = conversationListViewModel$loadConversations$1.this$0._isLoading;
                mutableLiveData.setValue(Boxing.boxBoolean(true));
                conversationListViewModel$loadConversations$1.label = 1;
                Object withContext = BuildersKt.withContext(Dispatchers.getIO(), new ConversationListViewModel$loadConversations$1$all$1(conversationListViewModel$loadConversations$1.this$0, null), conversationListViewModel$loadConversations$1);
                if (withContext != coroutine_suspended) {
                    $result = withContext;
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                conversationListViewModel$loadConversations$1 = this;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Iterable all = (List) $result;
        ConversationListViewModel conversationListViewModel = conversationListViewModel$loadConversations$1.this$0;
        Iterable $this$filterTo$iv$iv = all;
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filterTo$iv$iv.iterator();
        while (true) {
            boolean z = false;
            if (!it.hasNext()) {
                conversationListViewModel.allLoadedConversations = (List) destination$iv$iv;
                conversationListViewModel$loadConversations$1.this$0.applySearchFilter();
                mutableLiveData2 = conversationListViewModel$loadConversations$1.this$0._isLoading;
                mutableLiveData2.setValue(Boxing.boxBoolean(false));
                return Unit.INSTANCE;
            }
            Object element$iv$iv = it.next();
            ConversationSummary it2 = (ConversationSummary) element$iv$iv;
            if (!it2.isHidden() && !it2.isArchived() && !it2.isDeleted()) {
                z = true;
            }
            if (z) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
    }
}
