package com.meharenterprises.originsms.ui.conversations;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import com.meharenterprises.originsms.R;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConversationListActivity.kt */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes11.dex */
public final class ConversationListActivity$setupSelectionActionBar$4$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ int $count;
    final /* synthetic */ Set<Long> $ids;
    final /* synthetic */ ConversationListActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConversationListActivity$setupSelectionActionBar$4$1(ConversationListActivity conversationListActivity, Set<Long> set, int i) {
        super(0);
        this.this$0 = conversationListActivity;
        this.$ids = set;
        this.$count = i;
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Unit invoke() {
        invoke2();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2() {
        ConversationListViewModel conversationListViewModel;
        ConversationAdapter conversationAdapter;
        String msg;
        conversationListViewModel = this.this$0.viewModel;
        ConversationAdapter conversationAdapter2 = null;
        if (conversationListViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            conversationListViewModel = null;
        }
        conversationListViewModel.moveToTrash(this.$ids);
        conversationAdapter = this.this$0.adapter;
        if (conversationAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            conversationAdapter2 = conversationAdapter;
        }
        conversationAdapter2.clearSelection();
        this.this$0.updateSelectionBar();
        if (this.$count == 1) {
            msg = "1 chat moved to Trash";
        } else {
            msg = this.$count + " chats moved to Trash";
        }
        final ConversationListActivity conversationListActivity = this.this$0;
        final Set<Long> set = this.$ids;
        Snackbar.make(this.this$0.findViewById(R.id.recyclerConversations), msg, 0).setAction("Undo", new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$setupSelectionActionBar$4$1$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConversationListActivity$setupSelectionActionBar$4$1.invoke$lambda$0(ConversationListActivity.this, set, view);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(ConversationListActivity this$0, Set ids, View it) {
        ConversationListViewModel conversationListViewModel;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(ids, "$ids");
        conversationListViewModel = this$0.viewModel;
        if (conversationListViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            conversationListViewModel = null;
        }
        conversationListViewModel.restoreFromTrash(ids);
    }
}
