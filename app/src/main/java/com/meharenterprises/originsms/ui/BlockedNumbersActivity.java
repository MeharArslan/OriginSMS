package com.meharenterprises.originsms.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ContactsHelper;
import com.meharenterprises.originsms.data.db.BlockedNumberEntity;
import com.meharenterprises.originsms.data.db.OriginDatabase;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: BlockedNumbersActivity.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\fH\u0002J\u0012\u0010\u0010\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\b\u0010\u0013\u001a\u00020\fH\u0002J\u0010\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\u0017"}, d2 = {"Lcom/meharenterprises/originsms/ui/BlockedNumbersActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/meharenterprises/originsms/ui/BlockedNumberAdapter;", "database", "Lcom/meharenterprises/originsms/data/db/OriginDatabase;", "getDatabase", "()Lcom/meharenterprises/originsms/data/db/OriginDatabase;", "database$delegate", "Lkotlin/Lazy;", "blockNumber", "", "rawNumber", "", "observeBlockedNumbers", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "showAddBlockedDialog", "unblock", "entity", "Lcom/meharenterprises/originsms/data/db/BlockedNumberEntity;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class BlockedNumbersActivity extends AppCompatActivity {
    private BlockedNumberAdapter adapter;

    /* renamed from: database$delegate, reason: from kotlin metadata */
    private final Lazy database = LazyKt.lazy(new Function0<OriginDatabase>() { // from class: com.meharenterprises.originsms.ui.BlockedNumbersActivity$database$2
        /* JADX INFO: Access modifiers changed from: package-private */
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final OriginDatabase invoke() {
            return OriginDatabase.INSTANCE.getInstance(BlockedNumbersActivity.this);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public final OriginDatabase getDatabase() {
        return (OriginDatabase) this.database.getValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_numbers);
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.BlockedNumbersActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BlockedNumbersActivity.onCreate$lambda$0(BlockedNumbersActivity.this, view);
            }
        });
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("Blocked Numbers");
        }
        ActionBar supportActionBar2 = getSupportActionBar();
        if (supportActionBar2 != null) {
            supportActionBar2.setDisplayShowTitleEnabled(true);
        }
        this.adapter = new BlockedNumberAdapter(new Function1<BlockedNumberEntity, Unit>() { // from class: com.meharenterprises.originsms.ui.BlockedNumbersActivity$onCreate$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BlockedNumberEntity blockedNumberEntity) {
                invoke2(blockedNumberEntity);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BlockedNumberEntity entity) {
                Intrinsics.checkNotNullParameter(entity, "entity");
                BlockedNumbersActivity.this.unblock(entity);
            }
        });
        RecyclerView $this$onCreate_u24lambda_u241 = (RecyclerView) findViewById(R.id.recyclerBlocked);
        $this$onCreate_u24lambda_u241.setLayoutManager(new LinearLayoutManager(this));
        BlockedNumberAdapter blockedNumberAdapter = this.adapter;
        if (blockedNumberAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            blockedNumberAdapter = null;
        }
        $this$onCreate_u24lambda_u241.setAdapter(blockedNumberAdapter);
        ((FloatingActionButton) findViewById(R.id.fabAddBlocked)).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.BlockedNumbersActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BlockedNumbersActivity.onCreate$lambda$2(BlockedNumbersActivity.this, view);
            }
        });
        observeBlockedNumbers();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(BlockedNumbersActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(BlockedNumbersActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showAddBlockedDialog();
    }

    private final void observeBlockedNumbers() {
        Flow flow = getDatabase().blockedNumberDao().observeAll();
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new BlockedNumbersActivity$observeBlockedNumbers$1$1(flow, this, null), 3, null);
    }

    private final void showAddBlockedDialog() {
        final EditText $this$showAddBlockedDialog_u24lambda_u244 = new EditText(this);
        $this$showAddBlockedDialog_u24lambda_u244.setHint("Phone number");
        $this$showAddBlockedDialog_u24lambda_u244.setInputType(3);
        new AlertDialog.Builder(this).setTitle(R.string.settings_blocked_numbers).setView($this$showAddBlockedDialog_u24lambda_u244).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.BlockedNumbersActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BlockedNumbersActivity.showAddBlockedDialog$lambda$5($this$showAddBlockedDialog_u24lambda_u244, this, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAddBlockedDialog$lambda$5(EditText input, BlockedNumbersActivity this$0, DialogInterface dialogInterface, int i) {
        String obj;
        Intrinsics.checkNotNullParameter(input, "$input");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Editable text = input.getText();
        String raw = (text == null || (obj = text.toString()) == null) ? null : StringsKt.trim((CharSequence) obj).toString();
        if (raw == null) {
            raw = "";
        }
        if (!StringsKt.isBlank(raw)) {
            this$0.blockNumber(raw);
        }
    }

    private final void blockNumber(String rawNumber) {
        String normalized = ContactsHelper.INSTANCE.normalize(rawNumber);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new BlockedNumbersActivity$blockNumber$1(this, normalized, rawNumber, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void unblock(BlockedNumberEntity entity) {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new BlockedNumbersActivity$unblock$1(this, entity, null), 3, null);
    }
}
