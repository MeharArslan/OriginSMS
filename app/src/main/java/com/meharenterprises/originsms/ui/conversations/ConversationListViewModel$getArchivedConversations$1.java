package com.meharenterprises.originsms.ui.conversations;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConversationListViewModel.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.conversations.ConversationListViewModel", f = "ConversationListViewModel.kt", i = {}, l = {212}, m = "getArchivedConversations", n = {}, s = {})
/* loaded from: classes11.dex */
public final class ConversationListViewModel$getArchivedConversations$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ConversationListViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConversationListViewModel$getArchivedConversations$1(ConversationListViewModel conversationListViewModel, Continuation<? super ConversationListViewModel$getArchivedConversations$1> continuation) {
        super(continuation);
        this.this$0 = conversationListViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getArchivedConversations(this);
    }
}
