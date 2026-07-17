package com.meharenterprises.originsms.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.data.db.BlockedNumberEntity;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BlockedNumberAdapter.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00102\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0002\u0010\u0011B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u001c\u0010\b\u001a\u00020\u00062\n\u0010\t\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001c\u0010\f\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000bH\u0016R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/meharenterprises/originsms/ui/BlockedNumberAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/meharenterprises/originsms/data/db/BlockedNumberEntity;", "Lcom/meharenterprises/originsms/ui/BlockedNumberAdapter$ViewHolder;", "onUnblock", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;)V", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "Companion", "ViewHolder", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class BlockedNumberAdapter extends ListAdapter<BlockedNumberEntity, ViewHolder> {
    private static final BlockedNumberAdapter$Companion$DIFF$1 DIFF = new DiffUtil.ItemCallback<BlockedNumberEntity>() { // from class: com.meharenterprises.originsms.ui.BlockedNumberAdapter$Companion$DIFF$1
        @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
        public boolean areItemsTheSame(BlockedNumberEntity oldItem, BlockedNumberEntity newItem) {
            Intrinsics.checkNotNullParameter(oldItem, "oldItem");
            Intrinsics.checkNotNullParameter(newItem, "newItem");
            return Intrinsics.areEqual(oldItem.getNormalizedNumber(), newItem.getNormalizedNumber());
        }

        @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
        public boolean areContentsTheSame(BlockedNumberEntity oldItem, BlockedNumberEntity newItem) {
            Intrinsics.checkNotNullParameter(oldItem, "oldItem");
            Intrinsics.checkNotNullParameter(newItem, "newItem");
            return Intrinsics.areEqual(oldItem, newItem);
        }
    };
    private final Function1<BlockedNumberEntity, Unit> onUnblock;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BlockedNumberAdapter(Function1<? super BlockedNumberEntity, Unit> onUnblock) {
        super(DIFF);
        Intrinsics.checkNotNullParameter(onUnblock, "onUnblock");
        this.onUnblock = onUnblock;
    }

    /* compiled from: BlockedNumberAdapter.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lcom/meharenterprises/originsms/ui/BlockedNumberAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/meharenterprises/originsms/ui/BlockedNumberAdapter;Landroid/view/View;)V", "btnUnblock", "Landroid/widget/ImageButton;", "getBtnUnblock", "()Landroid/widget/ImageButton;", "txtNumber", "Landroid/widget/TextView;", "getTxtNumber", "()Landroid/widget/TextView;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageButton btnUnblock;
        final /* synthetic */ BlockedNumberAdapter this$0;
        private final TextView txtNumber;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(BlockedNumberAdapter this$0, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = this$0;
            View findViewById = itemView.findViewById(R.id.txtNumber);
            Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
            this.txtNumber = (TextView) findViewById;
            View findViewById2 = itemView.findViewById(R.id.btnUnblock);
            Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
            this.btnUnblock = (ImageButton) findViewById2;
        }

        public final TextView getTxtNumber() {
            return this.txtNumber;
        }

        public final ImageButton getBtnUnblock() {
            return this.btnUnblock;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blocked_number, parent, false);
        Intrinsics.checkNotNull(view);
        return new ViewHolder(this, view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        final BlockedNumberEntity item = getItem(position);
        holder.getTxtNumber().setText(item.getDisplayNumber());
        holder.getBtnUnblock().setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.BlockedNumberAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BlockedNumberAdapter.onBindViewHolder$lambda$0(BlockedNumberAdapter.this, item, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(BlockedNumberAdapter this$0, BlockedNumberEntity $item, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Function1<BlockedNumberEntity, Unit> function1 = this$0.onUnblock;
        Intrinsics.checkNotNull($item);
        function1.invoke($item);
    }
}
