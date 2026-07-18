package com.meharenterprises.originsms.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ContactsHelper;
import com.meharenterprises.originsms.data.db.StarredMessageEntity;
import com.meharenterprises.originsms.ui.StarredMessagesActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;

/* compiled from: StarredMessagesActivity.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0007B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\b"}, d2 = {"Lcom/meharenterprises/originsms/ui/StarredMessagesActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "StarredAdapter", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class StarredMessagesActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starred_messages);
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("Starred Messages");
        }
        ActionBar supportActionBar2 = getSupportActionBar();
        if (supportActionBar2 != null) {
            supportActionBar2.setDisplayShowTitleEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.StarredMessagesActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StarredMessagesActivity.onCreate$lambda$0(StarredMessagesActivity.this, view);
            }
        });
        long filterThreadId = getIntent().getLongExtra("FILTER_THREAD_ID", -1L);
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recyclerStarred);
        TextView empty = (TextView) findViewById(R.id.emptyStarred);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new StarredMessagesActivity$onCreate$2(this, filterThreadId, empty, recycler, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(StarredMessagesActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* compiled from: StarredMessagesActivity.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u0019BC\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0011H\u0016J \u0010\u0012\u001a\u00020\u000e2\u000e\u0010\u0013\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0011H\u0016J \u0010\u0015\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0011H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/meharenterprises/originsms/ui/StarredMessagesActivity$StarredAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/meharenterprises/originsms/ui/StarredMessagesActivity$StarredAdapter$VH;", "Lcom/meharenterprises/originsms/ui/StarredMessagesActivity;", "items", "", "Lcom/meharenterprises/originsms/data/db/StarredMessageEntity;", "contactsHelper", "Lcom/meharenterprises/originsms/core/ContactsHelper;", "nameCache", "", "", "onClick", "Lkotlin/Function1;", "", "(Lcom/meharenterprises/originsms/ui/StarredMessagesActivity;Ljava/util/List;Lcom/meharenterprises/originsms/core/ContactsHelper;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "VH", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes3.dex */
    public final class StarredAdapter extends RecyclerView.Adapter<VH> {
        private final ContactsHelper contactsHelper;
        private final List<StarredMessageEntity> items;
        private final Map<String, String> nameCache;
        private final Function1<StarredMessageEntity, Unit> onClick;
        final /* synthetic */ StarredMessagesActivity this$0;

        /* JADX WARN: Multi-variable type inference failed */
        public StarredAdapter(StarredMessagesActivity this$0, List<StarredMessageEntity> items, ContactsHelper contactsHelper, Map<String, String> nameCache, Function1<? super StarredMessageEntity, Unit> onClick) {
            Intrinsics.checkNotNullParameter(items, "items");
            Intrinsics.checkNotNullParameter(contactsHelper, "contactsHelper");
            Intrinsics.checkNotNullParameter(nameCache, "nameCache");
            Intrinsics.checkNotNullParameter(onClick, "onClick");
            this.this$0 = this$0;
            this.items = items;
            this.contactsHelper = contactsHelper;
            this.nameCache = nameCache;
            this.onClick = onClick;
        }

        /* compiled from: StarredMessagesActivity.kt */
        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\b¨\u0006\r"}, d2 = {"Lcom/meharenterprises/originsms/ui/StarredMessagesActivity$StarredAdapter$VH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "v", "Landroid/view/View;", "(Lcom/meharenterprises/originsms/ui/StarredMessagesActivity$StarredAdapter;Landroid/view/View;)V", "txtBody", "Landroid/widget/TextView;", "getTxtBody", "()Landroid/widget/TextView;", "txtContact", "getTxtContact", "txtMeta", "getTxtMeta", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* loaded from: classes3.dex */
        public final class VH extends RecyclerView.ViewHolder {
            final /* synthetic */ StarredAdapter this$0;
            private final TextView txtBody;
            private final TextView txtContact;
            private final TextView txtMeta;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public VH(StarredAdapter this$0, View v) {
                super(v);
                Intrinsics.checkNotNullParameter(v, "v");
                this.this$0 = this$0;
                View findViewById = v.findViewById(R.id.txtStarredBody);
                Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
                this.txtBody = (TextView) findViewById;
                View findViewById2 = v.findViewById(R.id.txtStarredMeta);
                Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
                this.txtMeta = (TextView) findViewById2;
                this.txtContact = (TextView) v.findViewById(R.id.txtStarredContact);
            }

            public final TextView getTxtBody() {
                return this.txtBody;
            }

            public final TextView getTxtMeta() {
                return this.txtMeta;
            }

            public final TextView getTxtContact() {
                return this.txtContact;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_starred_message, parent, false);
            Intrinsics.checkNotNullExpressionValue(inflate, "inflate(...)");
            return new VH(this, inflate);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.items.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(VH holder, int position) {
            String str;
            Intrinsics.checkNotNullParameter(holder, "holder");
            final StarredMessageEntity item = this.items.get(position);
            holder.getTxtBody().setText(item.getBody());
            Map $this$getOrPut$iv = this.nameCache;
            String address = item.getAddress();
            String str2 = $this$getOrPut$iv.get(address);
            if (str2 == null) {
                str = this.contactsHelper.resolve(item.getAddress()).getDisplayName();
                $this$getOrPut$iv.put(address, str);
            } else {
                str = str2;
            }
            String name = str;
            String date = new SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()).format(new Date(item.getDateMillis()));
            holder.getTxtMeta().setText(String.valueOf(date));
            TextView txtContact = holder.getTxtContact();
            if (txtContact != null) {
                txtContact.setText(name);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.StarredMessagesActivity$StarredAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    StarredMessagesActivity.StarredAdapter.onBindViewHolder$lambda$1(StarredMessagesActivity.StarredAdapter.this, item, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onBindViewHolder$lambda$1(StarredAdapter this$0, StarredMessageEntity item, View it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(item, "$item");
            this$0.onClick.invoke(item);
        }
    }
}
