package com.meharenterprises.originsms.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.meharenterprises.originsms.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FontConverterActivity.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0016B-\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\u0010\bJ\b\u0010\f\u001a\u00020\u000bH\u0016J\u001c\u0010\r\u001a\u00020\u00062\n\u0010\u000e\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u000bH\u0016J\u001c\u0010\u0010\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000bH\u0016J\u000e\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0005R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/meharenterprises/originsms/ui/FontAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/meharenterprises/originsms/ui/FontAdapter$FontVH;", "onCopy", "Lkotlin/Function1;", "", "", "onApply", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "inputText", "selectedIndex", "", "getItemCount", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "updateText", "text", "FontVH", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class FontAdapter extends RecyclerView.Adapter<FontVH> {
    private String inputText;
    private final Function1<String, Unit> onApply;
    private final Function1<String, Unit> onCopy;
    private int selectedIndex;

    /* JADX WARN: Multi-variable type inference failed */
    public FontAdapter(Function1<? super String, Unit> onCopy, Function1<? super String, Unit> onApply) {
        Intrinsics.checkNotNullParameter(onCopy, "onCopy");
        Intrinsics.checkNotNullParameter(onApply, "onApply");
        this.onCopy = onCopy;
        this.onApply = onApply;
        this.inputText = "Font Style";
    }

    public final void updateText(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.inputText = text;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return FontConverterActivity.INSTANCE.getFONT_NAMES().length;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public FontVH onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_font_style, parent, false);
        Intrinsics.checkNotNull(view);
        return new FontVH(this, view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(FontVH holder, final int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        final String converted = FontConverterActivity.INSTANCE.convert(this.inputText, position);
        holder.getTxtFontName().setText(FontConverterActivity.INSTANCE.getFONT_NAMES()[position]);
        holder.getTxtFontPreview().setText(converted);
        if (position == this.selectedIndex) {
            holder.itemView.setBackgroundColor(570451711);
            holder.getTxtSelected().setVisibility(0);
        } else {
            holder.itemView.setBackgroundColor(0);
            holder.getTxtSelected().setVisibility(8);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.FontAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FontAdapter.onBindViewHolder$lambda$0(FontAdapter.this, position, view);
            }
        });
        holder.getBtnCopy().setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.FontAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FontAdapter.onBindViewHolder$lambda$1(FontAdapter.this, converted, view);
            }
        });
        holder.getBtnApply().setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.FontAdapter$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FontAdapter.onBindViewHolder$lambda$2(FontAdapter.this, position, converted, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(FontAdapter this$0, int $position, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.selectedIndex = $position;
        this$0.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$1(FontAdapter this$0, String converted, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(converted, "$converted");
        this$0.onCopy.invoke(converted);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$2(FontAdapter this$0, int $position, String converted, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(converted, "$converted");
        this$0.selectedIndex = $position;
        this$0.onApply.invoke(converted);
    }

    /* compiled from: FontConverterActivity.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0010\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\r¨\u0006\u0012"}, d2 = {"Lcom/meharenterprises/originsms/ui/FontAdapter$FontVH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "v", "Landroid/view/View;", "(Lcom/meharenterprises/originsms/ui/FontAdapter;Landroid/view/View;)V", "btnApply", "getBtnApply", "()Landroid/view/View;", "btnCopy", "getBtnCopy", "txtFontName", "Landroid/widget/TextView;", "getTxtFontName", "()Landroid/widget/TextView;", "txtFontPreview", "getTxtFontPreview", "txtSelected", "getTxtSelected", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public final class FontVH extends RecyclerView.ViewHolder {
        private final View btnApply;
        private final View btnCopy;
        final /* synthetic */ FontAdapter this$0;
        private final TextView txtFontName;
        private final TextView txtFontPreview;
        private final TextView txtSelected;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FontVH(FontAdapter this$0, View v) {
            super(v);
            Intrinsics.checkNotNullParameter(v, "v");
            this.this$0 = this$0;
            View findViewById = v.findViewById(R.id.txtFontStyleName);
            Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
            this.txtFontName = (TextView) findViewById;
            View findViewById2 = v.findViewById(R.id.txtFontPreview);
            Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
            this.txtFontPreview = (TextView) findViewById2;
            View findViewById3 = v.findViewById(R.id.btnFontCopy);
            Intrinsics.checkNotNullExpressionValue(findViewById3, "findViewById(...)");
            this.btnCopy = findViewById3;
            View findViewById4 = v.findViewById(R.id.btnFontApply);
            Intrinsics.checkNotNullExpressionValue(findViewById4, "findViewById(...)");
            this.btnApply = findViewById4;
            View findViewById5 = v.findViewById(R.id.txtFontSelected);
            Intrinsics.checkNotNullExpressionValue(findViewById5, "findViewById(...)");
            this.txtSelected = (TextView) findViewById5;
        }

        public final TextView getTxtFontName() {
            return this.txtFontName;
        }

        public final TextView getTxtFontPreview() {
            return this.txtFontPreview;
        }

        public final View getBtnCopy() {
            return this.btnCopy;
        }

        public final View getBtnApply() {
            return this.btnApply;
        }

        public final TextView getTxtSelected() {
            return this.txtSelected;
        }
    }
}
