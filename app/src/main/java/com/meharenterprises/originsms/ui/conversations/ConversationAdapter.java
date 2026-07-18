package com.meharenterprises.originsms.ui.conversations;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ConversationSummary;
import com.meharenterprises.originsms.ui.DpViewActivity;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: ConversationAdapter.kt */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \"2\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0002\"#B7\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010\u0011\u001a\u00020\u0006J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0010H\u0002J\u0006\u0010\u0015\u001a\u00020\u0016J\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00100\u0018J\u001c\u0010\u0019\u001a\u00020\u00062\n\u0010\u001a\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u0016H\u0016J\u001c\u0010\u001c\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0016H\u0016J\u0010\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u0010H\u0002R\u001e\u0010\f\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/meharenterprises/originsms/ui/conversations/ConversationAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/meharenterprises/originsms/core/ConversationSummary;", "Lcom/meharenterprises/originsms/ui/conversations/ConversationAdapter$ViewHolder;", "onClick", "Lkotlin/Function1;", "", "onLongClick", "selectionModeEnabled", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Z)V", "<set-?>", "isSelectionMode", "()Z", "selectedThreadIds", "", "", "clearSelection", "formatTimestamp", "", "millis", "getSelectedCount", "", "getSelectedThreadIds", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "toggleSelection", "threadId", "Companion", "ViewHolder", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class ConversationAdapter extends ListAdapter<ConversationSummary, ViewHolder> {
    private static final ConversationAdapter$Companion$DIFF$1 DIFF = new DiffUtil.ItemCallback<ConversationSummary>() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationAdapter$Companion$DIFF$1
        @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
        public boolean areItemsTheSame(ConversationSummary oldItem, ConversationSummary newItem) {
            Intrinsics.checkNotNullParameter(oldItem, "oldItem");
            Intrinsics.checkNotNullParameter(newItem, "newItem");
            return oldItem.getThreadId() == newItem.getThreadId();
        }

        @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
        public boolean areContentsTheSame(ConversationSummary oldItem, ConversationSummary newItem) {
            Intrinsics.checkNotNullParameter(oldItem, "oldItem");
            Intrinsics.checkNotNullParameter(newItem, "newItem");
            return Intrinsics.areEqual(oldItem, newItem);
        }
    };
    private boolean isSelectionMode;
    private final Function1<ConversationSummary, Unit> onClick;
    private final Function1<ConversationSummary, Unit> onLongClick;
    private final Set<Long> selectedThreadIds;
    private final boolean selectionModeEnabled;

    public /* synthetic */ ConversationAdapter(Function1 function1, Function1 function12, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(function1, function12, (i & 4) != 0 ? true : z);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ConversationAdapter(Function1<? super ConversationSummary, Unit> onClick, Function1<? super ConversationSummary, Unit> onLongClick, boolean selectionModeEnabled) {
        super(DIFF);
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        Intrinsics.checkNotNullParameter(onLongClick, "onLongClick");
        this.onClick = onClick;
        this.onLongClick = onLongClick;
        this.selectionModeEnabled = selectionModeEnabled;
        this.selectedThreadIds = new LinkedHashSet();
    }

    /* renamed from: isSelectionMode, reason: from getter */
    public final boolean getIsSelectionMode() {
        return this.isSelectionMode;
    }

    /* compiled from: ConversationAdapter.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0013\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0015\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010¨\u0006\u0017"}, d2 = {"Lcom/meharenterprises/originsms/ui/conversations/ConversationAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/meharenterprises/originsms/ui/conversations/ConversationAdapter;Landroid/view/View;)V", "imgAvatar", "Landroid/widget/ImageView;", "getImgAvatar", "()Landroid/widget/ImageView;", "imgLockBadge", "getImgLockBadge", "imgSelectedCheck", "getImgSelectedCheck", "txtName", "Landroid/widget/TextView;", "getTxtName", "()Landroid/widget/TextView;", "txtSnippet", "getTxtSnippet", "txtTime", "getTxtTime", "txtUnreadBadge", "getTxtUnreadBadge", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes11.dex */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgAvatar;
        private final ImageView imgLockBadge;
        private final ImageView imgSelectedCheck;
        final /* synthetic */ ConversationAdapter this$0;
        private final TextView txtName;
        private final TextView txtSnippet;
        private final TextView txtTime;
        private final TextView txtUnreadBadge;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(ConversationAdapter this$0, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = this$0;
            View findViewById = itemView.findViewById(R.id.imgAvatar);
            Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
            this.imgAvatar = (ImageView) findViewById;
            View findViewById2 = itemView.findViewById(R.id.imgLockBadge);
            Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
            this.imgLockBadge = (ImageView) findViewById2;
            View findViewById3 = itemView.findViewById(R.id.imgSelectedCheck);
            Intrinsics.checkNotNullExpressionValue(findViewById3, "findViewById(...)");
            this.imgSelectedCheck = (ImageView) findViewById3;
            View findViewById4 = itemView.findViewById(R.id.txtName);
            Intrinsics.checkNotNullExpressionValue(findViewById4, "findViewById(...)");
            this.txtName = (TextView) findViewById4;
            View findViewById5 = itemView.findViewById(R.id.txtSnippet);
            Intrinsics.checkNotNullExpressionValue(findViewById5, "findViewById(...)");
            this.txtSnippet = (TextView) findViewById5;
            View findViewById6 = itemView.findViewById(R.id.txtTime);
            Intrinsics.checkNotNullExpressionValue(findViewById6, "findViewById(...)");
            this.txtTime = (TextView) findViewById6;
            View findViewById7 = itemView.findViewById(R.id.txtUnreadBadge);
            Intrinsics.checkNotNullExpressionValue(findViewById7, "findViewById(...)");
            this.txtUnreadBadge = (TextView) findViewById7;
        }

        public final ImageView getImgAvatar() {
            return this.imgAvatar;
        }

        public final ImageView getImgLockBadge() {
            return this.imgLockBadge;
        }

        public final ImageView getImgSelectedCheck() {
            return this.imgSelectedCheck;
        }

        public final TextView getTxtName() {
            return this.txtName;
        }

        public final TextView getTxtSnippet() {
            return this.txtSnippet;
        }

        public final TextView getTxtTime() {
            return this.txtTime;
        }

        public final TextView getTxtUnreadBadge() {
            return this.txtUnreadBadge;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false);
        Intrinsics.checkNotNull(view);
        return new ViewHolder(this, view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String snippet;
        Intrinsics.checkNotNullParameter(holder, "holder");
        final ConversationSummary item = getItem(position);
        Context context = holder.itemView.getContext();
        boolean isSelected = this.selectedThreadIds.contains(Long.valueOf(item.getThreadId()));
        holder.getTxtName().setText(item.getDisplayName());
        String draftText = item.getDraftText();
        if (!(draftText == null || StringsKt.isBlank(draftText))) {
            SpannableString spannable = new SpannableString("Draft: " + item.getDraftText());
            spannable.setSpan(new ForegroundColorSpan(SupportMenu.CATEGORY_MASK), 0, 6, 33);
            holder.getTxtSnippet().setText(spannable);
        } else {
            TextView txtSnippet = holder.getTxtSnippet();
            if (item.isLocked()) {
                snippet = context.getString(R.string.notif_locked_chat_content);
            } else {
                snippet = item.getSnippet();
            }
            txtSnippet.setText(snippet);
        }
        holder.getTxtTime().setText(formatTimestamp(item.getDateMillis()));
        holder.getImgLockBadge().setVisibility(item.isLocked() ? 0 : 8);
        holder.getImgSelectedCheck().setVisibility(isSelected ? 0 : 8);
        holder.itemView.setActivated(false);
        if (item.getUnreadCount() > 0 && !item.isLocked()) {
            holder.getTxtUnreadBadge().setVisibility(0);
            holder.getTxtUnreadBadge().setText(item.getUnreadCount() > 9 ? "9+" : String.valueOf(item.getUnreadCount()));
            holder.getTxtName().setTypeface(null, 1);
        } else {
            holder.getTxtUnreadBadge().setVisibility(8);
        }
        if (!isSelected) {
            if (item.getContactPhotoUri() != null) {
                try {
                    Uri uri = Uri.parse(item.getContactPhotoUri());
                    InputStream openInputStream = context.getContentResolver().openInputStream(uri);
                    if (openInputStream != null) {
                        InputStream inputStream = openInputStream;
                        try {
                            InputStream stream = inputStream;
                            Bitmap bitmap = BitmapFactory.decodeStream(stream);
                            if (bitmap != null) {
                                holder.getImgAvatar().setImageBitmap(bitmap);
                                holder.getImgAvatar().setScaleType(ImageView.ScaleType.CENTER_CROP);
                                holder.getImgAvatar().setPadding(0, 0, 0, 0);
                                holder.getImgAvatar().setClipToOutline(true);
                            }
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(inputStream, null);
                        } finally {
                        }
                    }
                } catch (Exception e) {
                    holder.getImgAvatar().setImageResource(R.drawable.ic_person);
                }
            } else {
                holder.getImgAvatar().setImageResource(R.drawable.ic_person);
                holder.getImgAvatar().setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                int pad = (int) (8 * context.getResources().getDisplayMetrics().density);
                holder.getImgAvatar().setPadding(pad, pad, pad, pad);
            }
        }
        holder.getImgAvatar().setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConversationAdapter.onBindViewHolder$lambda$2(ConversationAdapter.this, item, holder, view);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConversationAdapter.onBindViewHolder$lambda$3(ConversationAdapter.this, item, view);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationAdapter$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                boolean onBindViewHolder$lambda$4;
                onBindViewHolder$lambda$4 = ConversationAdapter.onBindViewHolder$lambda$4(ConversationAdapter.this, item, view);
                return onBindViewHolder$lambda$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$2(ConversationAdapter this$0, ConversationSummary $item, ViewHolder holder, View it) {
        String uri;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        if (!this$0.isSelectionMode && (uri = $item.getContactPhotoUri()) != null) {
            Intent intent = new Intent(holder.itemView.getContext(), (Class<?>) DpViewActivity.class);
            intent.putExtra("photoUri", uri);
            intent.putExtra("displayName", $item.getDisplayName());
            holder.itemView.getContext().startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$3(ConversationAdapter this$0, ConversationSummary $item, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isSelectionMode) {
            this$0.toggleSelection($item.getThreadId());
            Function1<ConversationSummary, Unit> function1 = this$0.onLongClick;
            Intrinsics.checkNotNull($item);
            function1.invoke($item);
            return;
        }
        Function1<ConversationSummary, Unit> function12 = this$0.onClick;
        Intrinsics.checkNotNull($item);
        function12.invoke($item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onBindViewHolder$lambda$4(ConversationAdapter this$0, ConversationSummary $item, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!this$0.selectionModeEnabled) {
            Function1<ConversationSummary, Unit> function1 = this$0.onLongClick;
            Intrinsics.checkNotNull($item);
            function1.invoke($item);
        } else if (!this$0.isSelectionMode) {
            this$0.isSelectionMode = true;
            this$0.selectedThreadIds.add(Long.valueOf($item.getThreadId()));
            this$0.notifyDataSetChanged();
            Function1<ConversationSummary, Unit> function12 = this$0.onLongClick;
            Intrinsics.checkNotNull($item);
            function12.invoke($item);
        }
        return true;
    }

    private final void toggleSelection(long threadId) {
        if (this.selectedThreadIds.contains(Long.valueOf(threadId))) {
            this.selectedThreadIds.remove(Long.valueOf(threadId));
        } else {
            this.selectedThreadIds.add(Long.valueOf(threadId));
        }
        if (this.selectedThreadIds.isEmpty()) {
            this.isSelectionMode = false;
        }
        notifyDataSetChanged();
    }

    public final Set<Long> getSelectedThreadIds() {
        return CollectionsKt.toSet(this.selectedThreadIds);
    }

    public final int getSelectedCount() {
        return this.selectedThreadIds.size();
    }

    public final void clearSelection() {
        this.selectedThreadIds.clear();
        this.isSelectionMode = false;
        notifyDataSetChanged();
    }

    private final String formatTimestamp(long millis) {
        if (millis == 0) {
            return "";
        }
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        then.setTimeInMillis(millis);
        if (now.get(1) != then.get(1) || now.get(6) != then.get(6)) {
            if (now.get(1) != then.get(1) || now.get(6) - then.get(6) != 1) {
                if (now.get(1) == then.get(1)) {
                    String format = new SimpleDateFormat("MMM d", Locale.getDefault()).format(then.getTime());
                    Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                    return format;
                }
                String format2 = new SimpleDateFormat("MM/dd/yy", Locale.getDefault()).format(then.getTime());
                Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
                return format2;
            }
            return "Yesterday";
        }
        String format3 = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(then.getTime());
        Intrinsics.checkNotNullExpressionValue(format3, "format(...)");
        return format3;
    }
}
