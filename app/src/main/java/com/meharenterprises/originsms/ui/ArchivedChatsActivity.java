package com.meharenterprises.originsms.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ConversationSummary;
import com.meharenterprises.originsms.core.SmsRepository;
import com.meharenterprises.originsms.ui.conversations.ConversationAdapter;
import com.meharenterprises.originsms.ui.thread.ThreadActivity;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;

/* compiled from: ArchivedChatsActivity.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\u0012\u0010\r\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014J\b\u0010\u0010\u001a\u00020\fH\u0014J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/meharenterprises/originsms/ui/ArchivedChatsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/meharenterprises/originsms/ui/conversations/ConversationAdapter;", "emptyState", "Landroid/widget/TextView;", "recycler", "Landroidx/recyclerview/widget/RecyclerView;", "repository", "Lcom/meharenterprises/originsms/core/SmsRepository;", "loadArchived", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "openOrUnarchive", "conv", "Lcom/meharenterprises/originsms/core/ConversationSummary;", "showOptions", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ArchivedChatsActivity extends AppCompatActivity {
    private ConversationAdapter adapter;
    private TextView emptyState;
    private RecyclerView recycler;
    private SmsRepository repository;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_chats);
        this.repository = new SmsRepository(this);
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.ArchivedChatsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ArchivedChatsActivity.onCreate$lambda$0(ArchivedChatsActivity.this, view);
            }
        });
        View findViewById = findViewById(R.id.recyclerArchived);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.recycler = (RecyclerView) findViewById;
        View findViewById2 = findViewById(R.id.emptyArchived);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
        this.emptyState = (TextView) findViewById2;
        this.adapter = new ConversationAdapter(new Function1<ConversationSummary, Unit>() { // from class: com.meharenterprises.originsms.ui.ArchivedChatsActivity$onCreate$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ConversationSummary conversationSummary) {
                invoke2(conversationSummary);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ConversationSummary conv) {
                Intrinsics.checkNotNullParameter(conv, "conv");
                ArchivedChatsActivity.this.openOrUnarchive(conv);
            }
        }, new Function1<ConversationSummary, Unit>() { // from class: com.meharenterprises.originsms.ui.ArchivedChatsActivity$onCreate$3
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ConversationSummary conversationSummary) {
                invoke2(conversationSummary);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ConversationSummary conv) {
                Intrinsics.checkNotNullParameter(conv, "conv");
                ArchivedChatsActivity.this.showOptions(conv);
            }
        }, false, 4, null);
        RecyclerView recyclerView = this.recycler;
        ConversationAdapter conversationAdapter = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView2 = this.recycler;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
            recyclerView2 = null;
        }
        ConversationAdapter conversationAdapter2 = this.adapter;
        if (conversationAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            conversationAdapter = conversationAdapter2;
        }
        recyclerView2.setAdapter(conversationAdapter);
        loadArchived();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(ArchivedChatsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        loadArchived();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadArchived() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new ArchivedChatsActivity$loadArchived$1(this, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openOrUnarchive(ConversationSummary conv) {
        Intent intent = new Intent(this, (Class<?>) ThreadActivity.class);
        intent.putExtra("extra_thread_id", conv.getThreadId());
        intent.putExtra("extra_address", conv.getAddress());
        intent.putExtra("extra_display_name", conv.getDisplayName());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showOptions(final ConversationSummary conv) {
        new AlertDialog.Builder(this).setTitle(conv.getDisplayName()).setItems(new String[]{"Open chat", "Unarchive"}, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.ArchivedChatsActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ArchivedChatsActivity.showOptions$lambda$2(ArchivedChatsActivity.this, conv, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showOptions$lambda$2(ArchivedChatsActivity this$0, ConversationSummary conv, DialogInterface dialogInterface, int index) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(conv, "$conv");
        switch (index) {
            case 0:
                this$0.openOrUnarchive(conv);
                return;
            case 1:
                BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new ArchivedChatsActivity$showOptions$1$1(this$0, conv, null), 3, null);
                return;
            default:
                return;
        }
    }
}
