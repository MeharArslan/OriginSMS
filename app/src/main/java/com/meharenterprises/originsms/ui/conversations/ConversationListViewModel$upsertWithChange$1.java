package com.meharenterprises.originsms.ui.conversations;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConversationListViewModel.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.conversations.ConversationListViewModel", f = "ConversationListViewModel.kt", i = {0, 0, 0}, l = {79, 80}, m = "upsertWithChange", n = {"this", "change", "threadId"}, s = {"L$0", "L$1", "J$0"})
/* loaded from: classes11.dex */
public final class ConversationListViewModel$upsertWithChange$1 extends ContinuationImpl {
    long J$0;
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ConversationListViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConversationListViewModel$upsertWithChange$1(ConversationListViewModel conversationListViewModel, Continuation<? super ConversationListViewModel$upsertWithChange$1> continuation) {
        super(continuation);
        this.this$0 = conversationListViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object upsertWithChange;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        upsertWithChange = this.this$0.upsertWithChange(0L, null, this);
        return upsertWithChange;
    }
}
