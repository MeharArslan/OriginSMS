package com.meharenterprises.originsms.ui;

import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.meharenterprises.originsms.ui.conversations.ConversationAdapter;
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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ArchivedChatsActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.ArchivedChatsActivity$loadArchived$1", f = "ArchivedChatsActivity.kt", i = {}, l = {LockFreeTaskQueueCore.FROZEN_SHIFT}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
public final class ArchivedChatsActivity$loadArchived$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ ArchivedChatsActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArchivedChatsActivity$loadArchived$1(ArchivedChatsActivity archivedChatsActivity, Continuation<? super ArchivedChatsActivity$loadArchived$1> continuation) {
        super(2, continuation);
        this.this$0 = archivedChatsActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ArchivedChatsActivity$loadArchived$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ArchivedChatsActivity$loadArchived$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ArchivedChatsActivity$loadArchived$1 archivedChatsActivity$loadArchived$1;
        ConversationAdapter conversationAdapter;
        TextView textView;
        RecyclerView recyclerView;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        RecyclerView recyclerView2 = null;
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                archivedChatsActivity$loadArchived$1 = this;
                archivedChatsActivity$loadArchived$1.label = 1;
                Object withContext = BuildersKt.withContext(Dispatchers.getIO(), new ArchivedChatsActivity$loadArchived$1$archived$1(archivedChatsActivity$loadArchived$1.this$0, null), archivedChatsActivity$loadArchived$1);
                if (withContext != coroutine_suspended) {
                    $result = withContext;
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                archivedChatsActivity$loadArchived$1 = this;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        List archived = (List) $result;
        conversationAdapter = archivedChatsActivity$loadArchived$1.this$0.adapter;
        if (conversationAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            conversationAdapter = null;
        }
        conversationAdapter.submitList(archived);
        textView = archivedChatsActivity$loadArchived$1.this$0.emptyState;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyState");
            textView = null;
        }
        textView.setVisibility(archived.isEmpty() ? 0 : 8);
        recyclerView = archivedChatsActivity$loadArchived$1.this$0.recycler;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
        } else {
            recyclerView2 = recyclerView;
        }
        recyclerView2.setVisibility(archived.isEmpty() ? 8 : 0);
        return Unit.INSTANCE;
    }
}
