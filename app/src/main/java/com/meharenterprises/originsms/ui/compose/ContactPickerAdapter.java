package com.meharenterprises.originsms.ui.compose;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ContactsHelper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: ContactPickerAdapter.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0019B\u0019\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\u0010\u0007J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u001c\u0010\u0010\u001a\u00020\u00062\n\u0010\u0011\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J\u001c\u0010\u0013\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000fH\u0016J\u0014\u0010\u0017\u001a\u00020\u00062\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\tR\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/meharenterprises/originsms/ui/compose/ContactPickerAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/meharenterprises/originsms/ui/compose/ContactPickerAdapter$ViewHolder;", "onContactSelected", "Lkotlin/Function1;", "Lcom/meharenterprises/originsms/core/ContactsHelper$PickableContact;", "", "(Lkotlin/jvm/functions/Function1;)V", "filteredList", "", "fullList", "filter", "query", "", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "submitContacts", "contacts", "ViewHolder", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes7.dex */
public final class ContactPickerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<ContactsHelper.PickableContact> filteredList;
    private List<ContactsHelper.PickableContact> fullList;
    private final Function1<ContactsHelper.PickableContact, Unit> onContactSelected;

    /* JADX WARN: Multi-variable type inference failed */
    public ContactPickerAdapter(Function1<? super ContactsHelper.PickableContact, Unit> onContactSelected) {
        Intrinsics.checkNotNullParameter(onContactSelected, "onContactSelected");
        this.onContactSelected = onContactSelected;
        this.fullList = CollectionsKt.emptyList();
        this.filteredList = CollectionsKt.emptyList();
    }

    /* compiled from: ContactPickerAdapter.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f¨\u0006\u000f"}, d2 = {"Lcom/meharenterprises/originsms/ui/compose/ContactPickerAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/meharenterprises/originsms/ui/compose/ContactPickerAdapter;Landroid/view/View;)V", "imgAvatar", "Landroid/widget/ImageView;", "getImgAvatar", "()Landroid/widget/ImageView;", "txtName", "Landroid/widget/TextView;", "getTxtName", "()Landroid/widget/TextView;", "txtNumber", "getTxtNumber", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes7.dex */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgAvatar;
        final /* synthetic */ ContactPickerAdapter this$0;
        private final TextView txtName;
        private final TextView txtNumber;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(ContactPickerAdapter this$0, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = this$0;
            View findViewById = itemView.findViewById(R.id.imgAvatar);
            Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
            this.imgAvatar = (ImageView) findViewById;
            View findViewById2 = itemView.findViewById(R.id.txtContactName);
            Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
            this.txtName = (TextView) findViewById2;
            View findViewById3 = itemView.findViewById(R.id.txtContactNumber);
            Intrinsics.checkNotNullExpressionValue(findViewById3, "findViewById(...)");
            this.txtNumber = (TextView) findViewById3;
        }

        public final ImageView getImgAvatar() {
            return this.imgAvatar;
        }

        public final TextView getTxtName() {
            return this.txtName;
        }

        public final TextView getTxtNumber() {
            return this.txtNumber;
        }
    }

    public final void submitContacts(List<ContactsHelper.PickableContact> contacts) {
        Intrinsics.checkNotNullParameter(contacts, "contacts");
        this.fullList = contacts;
        this.filteredList = contacts;
        notifyDataSetChanged();
    }

    public final void filter(String query) {
        List<ContactsHelper.PickableContact> list;
        Intrinsics.checkNotNullParameter(query, "query");
        if (StringsKt.isBlank(query)) {
            list = this.fullList;
        } else {
            String normalizedQuery = StringsKt.trim((CharSequence) query).toString().toLowerCase(Locale.ROOT);
            String str = "toLowerCase(...)";
            Intrinsics.checkNotNullExpressionValue(normalizedQuery, "toLowerCase(...)");
            Iterable $this$filter$iv = this.fullList;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                ContactsHelper.PickableContact it = (ContactsHelper.PickableContact) element$iv$iv;
                String lowerCase = it.getDisplayName().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, str);
                String str2 = str;
                if (StringsKt.contains$default((CharSequence) lowerCase, (CharSequence) normalizedQuery, false, 2, (Object) null) || StringsKt.contains$default((CharSequence) it.getPhoneNumber(), (CharSequence) normalizedQuery, false, 2, (Object) null)) {
                    destination$iv$iv.add(element$iv$iv);
                }
                str = str2;
            }
            list = (List) destination$iv$iv;
        }
        this.filteredList = list;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_picker, parent, false);
        Intrinsics.checkNotNull(view);
        return new ViewHolder(this, view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        final ContactsHelper.PickableContact contact = this.filteredList.get(position);
        holder.getTxtName().setText(contact.getDisplayName());
        holder.getTxtNumber().setText(contact.getPhoneNumber());
        if (contact.getPhotoUri() != null) {
            try {
                Uri uri = Uri.parse(contact.getPhotoUri());
                InputStream openInputStream = holder.itemView.getContext().getContentResolver().openInputStream(uri);
                if (openInputStream != null) {
                    InputStream inputStream = openInputStream;
                    try {
                        InputStream stream = inputStream;
                        Bitmap bitmap = BitmapFactory.decodeStream(stream);
                        if (bitmap != null) {
                            holder.getImgAvatar().setImageBitmap(bitmap);
                            holder.getImgAvatar().setScaleType(ImageView.ScaleType.CENTER_CROP);
                            holder.getImgAvatar().setPadding(0, 0, 0, 0);
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
            int pad = (int) (6 * holder.itemView.getContext().getResources().getDisplayMetrics().density);
            holder.getImgAvatar().setPadding(pad, pad, pad, pad);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.compose.ContactPickerAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ContactPickerAdapter.onBindViewHolder$lambda$2(ContactPickerAdapter.this, contact, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$2(ContactPickerAdapter this$0, ContactsHelper.PickableContact contact, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(contact, "$contact");
        this$0.onContactSelected.invoke(contact);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.filteredList.size();
    }
}
