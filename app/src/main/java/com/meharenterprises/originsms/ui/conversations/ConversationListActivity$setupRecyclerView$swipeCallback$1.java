package com.meharenterprises.originsms.ui.conversations;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ConversationSummary;
import com.meharenterprises.originsms.ui.GeneralSettingsActivity;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: ConversationListActivity.kt */
@Metadata(d1 = {"\u00009\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J@\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J \u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\tH\u0016J\u0018\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u000eH\u0016¨\u0006\u0016"}, d2 = {"com/meharenterprises/originsms/ui/conversations/ConversationListActivity$setupRecyclerView$swipeCallback$1", "Landroidx/recyclerview/widget/ItemTouchHelper$SimpleCallback;", "onChildDraw", "", "c", "Landroid/graphics/Canvas;", "rv", "Landroidx/recyclerview/widget/RecyclerView;", "vh", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "dX", "", "dY", "actionState", "", "isActive", "", "onMove", "t", "onSwiped", "viewHolder", "direction", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes11.dex */
public final class ConversationListActivity$setupRecyclerView$swipeCallback$1 extends ItemTouchHelper.SimpleCallback {
    final /* synthetic */ SharedPreferences $swipePrefs;
    final /* synthetic */ ConversationListActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConversationListActivity$setupRecyclerView$swipeCallback$1(ConversationListActivity $receiver, SharedPreferences $swipePrefs) {
        super(0, 12);
        this.this$0 = $receiver;
        this.$swipePrefs = $swipePrefs;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder t) {
        Intrinsics.checkNotNullParameter(rv, "rv");
        Intrinsics.checkNotNullParameter(vh, "vh");
        Intrinsics.checkNotNullParameter(t, "t");
        return false;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onChildDraw(Canvas c, RecyclerView rv, RecyclerView.ViewHolder vh, float dX, float dY, int actionState, boolean isActive) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(rv, "rv");
        Intrinsics.checkNotNullParameter(vh, "vh");
        View itemView = vh.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        Paint paint = new Paint(1);
        int margin = (int) (20 * this.this$0.getResources().getDisplayMetrics().density);
        int iconSize = (int) (28 * this.this$0.getResources().getDisplayMetrics().density);
        float midY = (itemView.getTop() + itemView.getBottom()) / 2.0f;
        if (dX > 0.0f) {
            paint.setColor(Color.parseColor("#388E3C"));
            c.drawRect(itemView.getLeft(), itemView.getTop(), RangesKt.coerceAtLeast(dX, itemView.getWidth()) + itemView.getLeft(), itemView.getBottom(), paint);
            Drawable archiveIcon = ContextCompat.getDrawable(this.this$0, R.drawable.ic_swipe_archive);
            if (archiveIcon != null) {
                archiveIcon.setTint(-1);
            }
            int left = itemView.getLeft() + margin;
            if (archiveIcon != null) {
                archiveIcon.setBounds(left, (int) (midY - (iconSize / 2)), left + iconSize, (int) ((iconSize / 2) + midY));
            }
            if (archiveIcon != null) {
                archiveIcon.draw(c);
            }
        } else if (dX < 0.0f) {
            paint.setColor(Color.parseColor("#C62828"));
            c.drawRect(RangesKt.coerceAtMost(dX, -itemView.getWidth()) + itemView.getRight(), itemView.getTop(), itemView.getRight(), itemView.getBottom(), paint);
            Drawable deleteIcon = ContextCompat.getDrawable(this.this$0, R.drawable.ic_swipe_delete);
            if (deleteIcon != null) {
                deleteIcon.setTint(-1);
            }
            int right = itemView.getRight() - margin;
            if (deleteIcon != null) {
                deleteIcon.setBounds(right - iconSize, (int) (midY - (iconSize / 2)), right, (int) ((iconSize / 2) + midY));
            }
            if (deleteIcon != null) {
                deleteIcon.draw(c);
            }
        }
        super.onChildDraw(c, rv, vh, dX, dY, actionState, isActive);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        ConversationAdapter conversationAdapter;
        ConversationAdapter conversationAdapter2;
        ConversationListViewModel conversationListViewModel;
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        int pos = viewHolder.getAdapterPosition();
        if (pos < 0) {
            return;
        }
        conversationAdapter = this.this$0.adapter;
        ConversationListViewModel conversationListViewModel2 = null;
        if (conversationAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            conversationAdapter = null;
        }
        List<ConversationSummary> currentList = conversationAdapter.getCurrentList();
        Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
        final ConversationSummary conv = (ConversationSummary) CollectionsKt.getOrNull(currentList, pos);
        if (conv == null) {
            return;
        }
        conversationAdapter2 = this.this$0.adapter;
        if (conversationAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            conversationAdapter2 = null;
        }
        conversationAdapter2.notifyItemChanged(pos);
        String actionKey = direction == 8 ? GeneralSettingsActivity.KEY_SWIPE_RIGHT : GeneralSettingsActivity.KEY_SWIPE_LEFT;
        int action = this.$swipePrefs.getInt(actionKey, direction == 8 ? 0 : 1);
        switch (action) {
            case 0:
                final ConversationListActivity conversationListActivity = this.this$0;
                new AlertDialog.Builder(this.this$0).setTitle("Archive chat?").setMessage(conv.getDisplayName()).setPositiveButton("Archive", new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$setupRecyclerView$swipeCallback$1$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ConversationListActivity$setupRecyclerView$swipeCallback$1.onSwiped$lambda$0(ConversationListActivity.this, conv, dialogInterface, i);
                    }
                }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
                return;
            case 1:
                ConversationListActivity.showMoveToTrashDialog$default(this.this$0, conv.getDisplayName(), new ConversationListActivity$setupRecyclerView$swipeCallback$1$onSwiped$2(this.this$0, conv), null, 4, null);
                return;
            case 2:
                conversationListViewModel = this.this$0.viewModel;
                if (conversationListViewModel == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                } else {
                    conversationListViewModel2 = conversationListViewModel;
                }
                conversationListViewModel2.markRead(conv.getThreadId());
                Toast.makeText(this.this$0, "Marked as read", 0).show();
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSwiped$lambda$0(ConversationListActivity this$0, ConversationSummary conv, DialogInterface dialogInterface, int i) {
        ConversationListViewModel conversationListViewModel;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(conv, "$conv");
        conversationListViewModel = this$0.viewModel;
        if (conversationListViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            conversationListViewModel = null;
        }
        conversationListViewModel.setArchived(SetsKt.setOf(Long.valueOf(conv.getThreadId())), true);
        Toast.makeText(this$0, conv.getDisplayName() + " archived", 0).show();
    }
}
