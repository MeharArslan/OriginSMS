package com.meharenterprises.originsms.ui.conversations;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ConversationSummary;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: ConversationListActivity.kt */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes11.dex */
final class ConversationListActivity$setupRecyclerView$swipeCallback$1$onSwiped$2 extends Lambda implements Function0<Unit> {
    final /* synthetic */ ConversationSummary $conv;
    final /* synthetic */ ConversationListActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConversationListActivity$setupRecyclerView$swipeCallback$1$onSwiped$2(ConversationListActivity conversationListActivity, ConversationSummary conversationSummary) {
        super(0);
        this.this$0 = conversationListActivity;
        this.$conv = conversationSummary;
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Unit invoke() {
        invoke2();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2() {
        ConversationListViewModel conversationListViewModel;
        conversationListViewModel = this.this$0.viewModel;
        if (conversationListViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            conversationListViewModel = null;
        }
        conversationListViewModel.moveToTrash(SetsKt.setOf(Long.valueOf(this.$conv.getThreadId())));
        final ConversationListActivity conversationListActivity = this.this$0;
        final ConversationSummary conversationSummary = this.$conv;
        Snackbar.make(this.this$0.findViewById(R.id.recyclerConversations), "1 chat moved to Trash", 0).setAction("Undo", new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$setupRecyclerView$swipeCallback$1$onSwiped$2$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConversationListActivity$setupRecyclerView$swipeCallback$1$onSwiped$2.invoke$lambda$0(ConversationListActivity.this, conversationSummary, view);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(ConversationListActivity this$0, ConversationSummary conv, View it) {
        ConversationListViewModel conversationListViewModel;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(conv, "$conv");
        conversationListViewModel = this$0.viewModel;
        if (conversationListViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            conversationListViewModel = null;
        }
        conversationListViewModel.restoreFromTrash(SetsKt.setOf(Long.valueOf(conv.getThreadId())));
    }
}
