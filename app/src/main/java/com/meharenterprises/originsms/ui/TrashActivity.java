package com.meharenterprises.originsms.ui;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ConversationSummary;
import com.meharenterprises.originsms.core.SmsRepository;
import com.meharenterprises.originsms.data.db.OriginDatabase;
import com.meharenterprises.originsms.ui.TrashActivity;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;

/* compiled from: TrashActivity.kt */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 !2\u00020\u0001:\u0002!\"B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0015H\u0002J\u0012\u0010\u0019\u001a\u00020\u00152\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\b\u0010\u001c\u001a\u00020\u0015H\u0014J\u0010\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010 \u001a\u00020\u0015H\u0002R\u0012\u0010\u0003\u001a\u00060\u0004R\u00020\u0000X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/meharenterprises/originsms/ui/TrashActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/meharenterprises/originsms/ui/TrashActivity$TrashAdapter;", "database", "Lcom/meharenterprises/originsms/data/db/OriginDatabase;", "deletedTimestamps", "", "", "emptyState", "Landroid/widget/TextView;", "recycler", "Landroidx/recyclerview/widget/RecyclerView;", "repository", "Lcom/meharenterprises/originsms/core/SmsRepository;", "selectedIds", "", "selectionMode", "", "confirmPermanentDelete", "", "conv", "Lcom/meharenterprises/originsms/core/ConversationSummary;", "loadTrash", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "openDeletedChat", "restoreChat", "showTrashActionDialog", "updateSelectionBar", "Companion", "TrashAdapter", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class TrashActivity extends AppCompatActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final DiffUtil.ItemCallback<ConversationSummary> TRASH_DIFF = new DiffUtil.ItemCallback<ConversationSummary>() { // from class: com.meharenterprises.originsms.ui.TrashActivity$Companion$TRASH_DIFF$1
        @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
        public boolean areItemsTheSame(ConversationSummary a, ConversationSummary b) {
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            return a.getThreadId() == b.getThreadId();
        }

        @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
        public boolean areContentsTheSame(ConversationSummary a, ConversationSummary b) {
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            return Intrinsics.areEqual(a, b);
        }
    };
    private TrashAdapter adapter;
    private OriginDatabase database;
    private TextView emptyState;
    private RecyclerView recycler;
    private SmsRepository repository;
    private boolean selectionMode;
    private Map<Long, Long> deletedTimestamps = MapsKt.emptyMap();
    private final Set<Long> selectedIds = new LinkedHashSet();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);
        this.database = OriginDatabase.INSTANCE.getInstance(this);
        this.repository = new SmsRepository(this);
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.TrashActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TrashActivity.onCreate$lambda$0(TrashActivity.this, view);
            }
        });
        View findViewById = findViewById(R.id.btnCloseSelection);
        if (findViewById != null) {
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.TrashActivity$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TrashActivity.onCreate$lambda$1(TrashActivity.this, view);
                }
            });
        }
        View findViewById2 = findViewById(R.id.btnSelectionRestore);
        if (findViewById2 != null) {
            findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.TrashActivity$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TrashActivity.onCreate$lambda$2(TrashActivity.this, view);
                }
            });
        }
        View findViewById3 = findViewById(R.id.btnSelectionDelete);
        if (findViewById3 != null) {
            findViewById3.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.TrashActivity$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TrashActivity.onCreate$lambda$4(TrashActivity.this, view);
                }
            });
        }
        View findViewById4 = findViewById(R.id.recyclerTrash);
        Intrinsics.checkNotNullExpressionValue(findViewById4, "findViewById(...)");
        this.recycler = (RecyclerView) findViewById4;
        View findViewById5 = findViewById(R.id.emptyTrash);
        Intrinsics.checkNotNullExpressionValue(findViewById5, "findViewById(...)");
        this.emptyState = (TextView) findViewById5;
        this.adapter = new TrashAdapter(this, new Function1<ConversationSummary, Unit>() { // from class: com.meharenterprises.originsms.ui.TrashActivity$onCreate$5
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
                TrashActivity.this.openDeletedChat(conv);
            }
        }, new Function1<ConversationSummary, Unit>() { // from class: com.meharenterprises.originsms.ui.TrashActivity$onCreate$6
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
                TrashActivity.this.restoreChat(conv);
            }
        }, new Function1<ConversationSummary, Unit>() { // from class: com.meharenterprises.originsms.ui.TrashActivity$onCreate$7
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
                TrashActivity.this.confirmPermanentDelete(conv);
            }
        });
        RecyclerView recyclerView = this.recycler;
        RecyclerView recyclerView2 = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView3 = this.recycler;
        if (recyclerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
            recyclerView3 = null;
        }
        TrashAdapter trashAdapter = this.adapter;
        if (trashAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            trashAdapter = null;
        }
        recyclerView3.setAdapter(trashAdapter);
        ItemTouchHelper swipeHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback() { // from class: com.meharenterprises.originsms.ui.TrashActivity$onCreate$swipeHelper$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0, 12);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder t) {
                Intrinsics.checkNotNullParameter(rv, "rv");
                Intrinsics.checkNotNullParameter(vh, "vh");
                Intrinsics.checkNotNullParameter(t, "t");
                return false;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onSwiped(RecyclerView.ViewHolder vh, int dir) {
                TrashActivity.TrashAdapter trashAdapter2;
                Intrinsics.checkNotNullParameter(vh, "vh");
                trashAdapter2 = TrashActivity.this.adapter;
                if (trashAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    trashAdapter2 = null;
                }
                ConversationSummary conv = trashAdapter2.getCurrentList().get(vh.getAdapterPosition());
                if (dir == 8) {
                    TrashActivity trashActivity = TrashActivity.this;
                    Intrinsics.checkNotNull(conv);
                    trashActivity.restoreChat(conv);
                } else {
                    TrashActivity trashActivity2 = TrashActivity.this;
                    Intrinsics.checkNotNull(conv);
                    trashActivity2.confirmPermanentDelete(conv);
                }
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onChildDraw(Canvas c, RecyclerView rv, RecyclerView.ViewHolder vh, float dX, float dY, int state, boolean active) {
                Intrinsics.checkNotNullParameter(c, "c");
                Intrinsics.checkNotNullParameter(rv, "rv");
                Intrinsics.checkNotNullParameter(vh, "vh");
                View itemView = vh.itemView;
                Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
                Paint paint = new Paint();
                if (dX > 0.0f) {
                    paint.setColor(Color.parseColor("#388E3C"));
                    c.drawRect(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + dX, itemView.getBottom(), paint);
                } else {
                    paint.setColor(Color.parseColor("#D32F2F"));
                    c.drawRect(itemView.getRight() + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom(), paint);
                }
                super.onChildDraw(c, rv, vh, dX, dY, state, active);
            }
        });
        RecyclerView recyclerView4 = this.recycler;
        if (recyclerView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
        } else {
            recyclerView2 = recyclerView4;
        }
        swipeHelper.attachToRecyclerView(recyclerView2);
        loadTrash();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(TrashActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(TrashActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.selectedIds.clear();
        this$0.selectionMode = false;
        TrashAdapter trashAdapter = this$0.adapter;
        if (trashAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            trashAdapter = null;
        }
        trashAdapter.notifyDataSetChanged();
        this$0.updateSelectionBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(TrashActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Set ids = CollectionsKt.toSet(this$0.selectedIds);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new TrashActivity$onCreate$3$1(this$0, ids, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$4(final TrashActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        final Set ids = CollectionsKt.toSet(this$0.selectedIds);
        new AlertDialog.Builder(this$0).setTitle("Delete " + ids.size() + " conversation(s)?").setMessage("This cannot be undone.").setPositiveButton("Delete permanently", new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.TrashActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                TrashActivity.onCreate$lambda$4$lambda$3(TrashActivity.this, ids, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$4$lambda$3(TrashActivity this$0, Set ids, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(ids, "$ids");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new TrashActivity$onCreate$4$1$1(this$0, ids, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        loadTrash();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadTrash() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new TrashActivity$loadTrash$1(this, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openDeletedChat(ConversationSummary conv) {
        showTrashActionDialog(conv);
    }

    private final void showTrashActionDialog(final ConversationSummary conv) {
        long daysLeft;
        Long deletedAtMillis = conv.getDeletedAtMillis();
        long deletedAt = deletedAtMillis != null ? deletedAtMillis.longValue() : 0L;
        if (deletedAt > 0) {
            long msLeft = (2592000000L + deletedAt) - System.currentTimeMillis();
            daysLeft = RangesKt.coerceAtLeast(msLeft / 86400000, 0L);
        } else {
            daysLeft = 30;
        }
        int dp = (int) getResources().getDisplayMetrics().density;
        final BottomSheetDialog sheet = new BottomSheetDialog(this);
        LinearLayout view = new LinearLayout(this);
        view.setOrientation(1);
        view.setPadding(0, dp * 8, 0, dp * 16);
        TextView $this$showTrashActionDialog_u24lambda_u246 = new TextView(this);
        $this$showTrashActionDialog_u24lambda_u246.setText(conv.getDisplayName());
        $this$showTrashActionDialog_u24lambda_u246.setTextSize(18.0f);
        $this$showTrashActionDialog_u24lambda_u246.setTypeface(null, 1);
        $this$showTrashActionDialog_u24lambda_u246.setPadding(dp * 24, dp * 16, dp * 24, dp * 4);
        TextView $this$showTrashActionDialog_u24lambda_u247 = new TextView(this);
        $this$showTrashActionDialog_u24lambda_u247.setText("Will be permanently deleted in " + daysLeft + " days");
        $this$showTrashActionDialog_u24lambda_u247.setTextSize(13.0f);
        $this$showTrashActionDialog_u24lambda_u247.setPadding(dp * 24, 0, dp * 24, dp * 16);
        $this$showTrashActionDialog_u24lambda_u247.setTextColor($this$showTrashActionDialog_u24lambda_u247.getContext().getColor(R.color.text_secondary));
        TextView btnRestore = new TextView(this);
        btnRestore.setText("  ↩  Restore");
        btnRestore.setTextSize(15.0f);
        btnRestore.setPadding(dp * 24, dp * 14, dp * 24, dp * 14);
        TypedValue tv = new TypedValue();
        btnRestore.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, tv, true);
        btnRestore.setBackground(btnRestore.getContext().getDrawable(tv.resourceId));
        btnRestore.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.TrashActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                TrashActivity.showTrashActionDialog$lambda$10(BottomSheetDialog.this, this, conv, view2);
            }
        });
        TextView btnDelete = new TextView(this);
        btnDelete.setText("  🗑  Delete permanently");
        btnDelete.setTextSize(15.0f);
        btnDelete.setPadding(dp * 24, dp * 14, dp * 24, dp * 14);
        btnDelete.setTextColor(btnDelete.getContext().getColor(android.R.color.holo_red_light));
        TypedValue tv2 = new TypedValue();
        btnDelete.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, tv2, true);
        btnDelete.setBackground(btnDelete.getContext().getDrawable(tv2.resourceId));
        btnDelete.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.TrashActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                TrashActivity.showTrashActionDialog$lambda$13(BottomSheetDialog.this, this, conv, view2);
            }
        });
        view.addView($this$showTrashActionDialog_u24lambda_u246);
        view.addView($this$showTrashActionDialog_u24lambda_u247);
        View $this$showTrashActionDialog_u24lambda_u2414 = new View(this);
        $this$showTrashActionDialog_u24lambda_u2414.setLayoutParams(new LinearLayout.LayoutParams(-1, 1));
        $this$showTrashActionDialog_u24lambda_u2414.setBackgroundColor($this$showTrashActionDialog_u24lambda_u2414.getContext().getColor(R.color.divider));
        view.addView($this$showTrashActionDialog_u24lambda_u2414);
        view.addView(btnRestore);
        view.addView(btnDelete);
        sheet.setContentView(view);
        sheet.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTrashActionDialog$lambda$10(BottomSheetDialog sheet, TrashActivity this$0, ConversationSummary conv, View it) {
        Intrinsics.checkNotNullParameter(sheet, "$sheet");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(conv, "$conv");
        sheet.dismiss();
        this$0.restoreChat(conv);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTrashActionDialog$lambda$13(BottomSheetDialog sheet, TrashActivity this$0, ConversationSummary conv, View it) {
        Intrinsics.checkNotNullParameter(sheet, "$sheet");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(conv, "$conv");
        sheet.dismiss();
        this$0.confirmPermanentDelete(conv);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void restoreChat(ConversationSummary conv) {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new TrashActivity$restoreChat$1(this, conv, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void confirmPermanentDelete(final ConversationSummary conv) {
        new AlertDialog.Builder(this).setTitle("Delete permanently?").setMessage(conv.getDisplayName() + "\n\nAll messages will be deleted. This cannot be undone.").setPositiveButton("Delete permanently", new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.TrashActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                TrashActivity.confirmPermanentDelete$lambda$15(TrashActivity.this, conv, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.TrashActivity$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                TrashActivity.confirmPermanentDelete$lambda$16(TrashActivity.this, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmPermanentDelete$lambda$15(TrashActivity this$0, ConversationSummary conv, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(conv, "$conv");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new TrashActivity$confirmPermanentDelete$1$1(this$0, conv, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmPermanentDelete$lambda$16(TrashActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TrashAdapter trashAdapter = this$0.adapter;
        if (trashAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            trashAdapter = null;
        }
        trashAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateSelectionBar() {
        View bar = findViewById(R.id.selectionBar);
        TextView txt = (TextView) findViewById(R.id.txtSelectionCount);
        if (this.selectedIds.isEmpty()) {
            this.selectionMode = false;
            if (bar != null) {
                bar.setVisibility(8);
            }
            TrashAdapter trashAdapter = this.adapter;
            if (trashAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                trashAdapter = null;
            }
            trashAdapter.notifyDataSetChanged();
            return;
        }
        if (bar != null) {
            bar.setVisibility(0);
        }
        if (txt == null) {
            return;
        }
        txt.setText(this.selectedIds.size() + " selected");
    }

    /* compiled from: TrashActivity.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u0016\u0012\u0004\u0012\u00020\u0002\u0012\f\u0012\n0\u0003R\u00060\u0000R\u00020\u00040\u0001:\u0001\u0013BA\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\nJ \u0010\u000b\u001a\u00020\u00072\u000e\u0010\f\u001a\n0\u0003R\u00060\u0000R\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0016J \u0010\u000f\u001a\n0\u0003R\u00060\u0000R\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000eH\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/meharenterprises/originsms/ui/TrashActivity$TrashAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/meharenterprises/originsms/core/ConversationSummary;", "Lcom/meharenterprises/originsms/ui/TrashActivity$TrashAdapter$ViewHolder;", "Lcom/meharenterprises/originsms/ui/TrashActivity;", "onClick", "Lkotlin/Function1;", "", "onRestore", "onDelete", "(Lcom/meharenterprises/originsms/ui/TrashActivity;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes3.dex */
    public final class TrashAdapter extends ListAdapter<ConversationSummary, ViewHolder> {
        private final Function1<ConversationSummary, Unit> onClick;
        private final Function1<ConversationSummary, Unit> onDelete;
        private final Function1<ConversationSummary, Unit> onRestore;
        final /* synthetic */ TrashActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public TrashAdapter(TrashActivity this$0, Function1<? super ConversationSummary, Unit> onClick, Function1<? super ConversationSummary, Unit> onRestore, Function1<? super ConversationSummary, Unit> onDelete) {
            super(TrashActivity.INSTANCE.getTRASH_DIFF());
            Intrinsics.checkNotNullParameter(onClick, "onClick");
            Intrinsics.checkNotNullParameter(onRestore, "onRestore");
            Intrinsics.checkNotNullParameter(onDelete, "onDelete");
            this.this$0 = this$0;
            this.onClick = onClick;
            this.onRestore = onRestore;
            this.onDelete = onDelete;
        }

        /* compiled from: TrashActivity.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0013\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0015\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010¨\u0006\u0017"}, d2 = {"Lcom/meharenterprises/originsms/ui/TrashActivity$TrashAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/meharenterprises/originsms/ui/TrashActivity$TrashAdapter;Landroid/view/View;)V", "cbSelect", "Landroid/widget/CheckBox;", "getCbSelect", "()Landroid/widget/CheckBox;", "imgAvatar", "Landroid/widget/ImageView;", "getImgAvatar", "()Landroid/widget/ImageView;", "txtDate", "Landroid/widget/TextView;", "getTxtDate", "()Landroid/widget/TextView;", "txtDaysLeft", "getTxtDaysLeft", "txtMsgCount", "getTxtMsgCount", "txtName", "getTxtName", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* loaded from: classes3.dex */
        public final class ViewHolder extends RecyclerView.ViewHolder {
            private final CheckBox cbSelect;
            private final ImageView imgAvatar;
            final /* synthetic */ TrashAdapter this$0;
            private final TextView txtDate;
            private final TextView txtDaysLeft;
            private final TextView txtMsgCount;
            private final TextView txtName;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public ViewHolder(TrashAdapter this$0, View itemView) {
                super(itemView);
                Intrinsics.checkNotNullParameter(itemView, "itemView");
                this.this$0 = this$0;
                View findViewById = itemView.findViewById(R.id.imgTrashAvatar);
                Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
                this.imgAvatar = (ImageView) findViewById;
                View findViewById2 = itemView.findViewById(R.id.txtTrashName);
                Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
                this.txtName = (TextView) findViewById2;
                View findViewById3 = itemView.findViewById(R.id.txtTrashDate);
                Intrinsics.checkNotNullExpressionValue(findViewById3, "findViewById(...)");
                this.txtDate = (TextView) findViewById3;
                View findViewById4 = itemView.findViewById(R.id.txtDaysLeft);
                Intrinsics.checkNotNullExpressionValue(findViewById4, "findViewById(...)");
                this.txtDaysLeft = (TextView) findViewById4;
                View findViewById5 = itemView.findViewById(R.id.txtMsgCount);
                Intrinsics.checkNotNullExpressionValue(findViewById5, "findViewById(...)");
                this.txtMsgCount = (TextView) findViewById5;
                this.cbSelect = (CheckBox) itemView.findViewById(R.id.cbTrashSelect);
            }

            public final ImageView getImgAvatar() {
                return this.imgAvatar;
            }

            public final TextView getTxtName() {
                return this.txtName;
            }

            public final TextView getTxtDate() {
                return this.txtDate;
            }

            public final TextView getTxtDaysLeft() {
                return this.txtDaysLeft;
            }

            public final TextView getTxtMsgCount() {
                return this.txtMsgCount;
            }

            public final CheckBox getCbSelect() {
                return this.cbSelect;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trash, parent, false);
            Intrinsics.checkNotNullExpressionValue(inflate, "inflate(...)");
            return new ViewHolder(this, inflate);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(final ViewHolder holder, int position) {
            int i;
            InputStream inputStream;
            Intrinsics.checkNotNullParameter(holder, "holder");
            final ConversationSummary conv = getItem(position);
            Long l = (Long) this.this$0.deletedTimestamps.get(Long.valueOf(conv.getThreadId()));
            long deletedAt = l != null ? l.longValue() : 0L;
            long daysLeft = deletedAt > 0 ? 30 - TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - deletedAt) : 30L;
            holder.getTxtName().setText(conv.getDisplayName());
            holder.getTxtDate().setText(deletedAt > 0 ? new SimpleDateFormat("MMM d", Locale.getDefault()).format(new Date(deletedAt)) : "");
            holder.getTxtDaysLeft().setText(daysLeft > 0 ? daysLeft + " days" : "Expires today");
            try {
                Cursor query = holder.itemView.getContext().getContentResolver().query(Telephony.Sms.CONTENT_URI, new String[]{"_id"}, "thread_id = ?", new String[]{String.valueOf(conv.getThreadId())}, null);
                if (query != null) {
                    inputStream = query;
                    try {
                        Cursor it = inputStream;
                        i = it.getCount();
                        CloseableKt.closeFinally(inputStream, null);
                    } finally {
                    }
                } else {
                    i = 0;
                }
            } catch (Exception e) {
                i = 0;
            }
            int msgCount = i;
            holder.getTxtMsgCount().setText(msgCount > 0 ? msgCount + " messages" : "Conversation");
            String uri = conv.getContactPhotoUri();
            if (uri != null) {
                try {
                    InputStream openInputStream = holder.itemView.getContext().getContentResolver().openInputStream(Uri.parse(uri));
                    if (openInputStream != null) {
                        inputStream = openInputStream;
                        try {
                            InputStream s = inputStream;
                            Bitmap bmp = BitmapFactory.decodeStream(s);
                            if (bmp != null) {
                                holder.getImgAvatar().setImageBitmap(bmp);
                                holder.getImgAvatar().setScaleType(ImageView.ScaleType.CENTER_CROP);
                                holder.getImgAvatar().setPadding(0, 0, 0, 0);
                                holder.getImgAvatar().setClipToOutline(true);
                            }
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(inputStream, null);
                        } finally {
                            try {
                                throw th;
                            } finally {
                            }
                        }
                    }
                } catch (Exception e2) {
                }
            } else {
                holder.getImgAvatar().setImageResource(R.drawable.ic_person);
                holder.getImgAvatar().setPadding(16, 16, 16, 16);
            }
            CheckBox cbSelect = holder.getCbSelect();
            if (cbSelect != null) {
                cbSelect.setVisibility(this.this$0.selectionMode ? 0 : 8);
            }
            CheckBox cbSelect2 = holder.getCbSelect();
            if (cbSelect2 != null) {
                cbSelect2.setChecked(this.this$0.selectedIds.contains(Long.valueOf(conv.getThreadId())));
            }
            View view = holder.itemView;
            final TrashActivity trashActivity = this.this$0;
            view.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.TrashActivity$TrashAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    TrashActivity.TrashAdapter.onBindViewHolder$lambda$2(TrashActivity.this, conv, this, holder, view2);
                }
            });
            View view2 = holder.itemView;
            final TrashActivity trashActivity2 = this.this$0;
            view2.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.meharenterprises.originsms.ui.TrashActivity$TrashAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view3) {
                    boolean onBindViewHolder$lambda$3;
                    onBindViewHolder$lambda$3 = TrashActivity.TrashAdapter.onBindViewHolder$lambda$3(TrashActivity.this, conv, this, view3);
                    return onBindViewHolder$lambda$3;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onBindViewHolder$lambda$2(TrashActivity this$0, ConversationSummary $conv, TrashAdapter this$1, ViewHolder holder, View it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            Intrinsics.checkNotNullParameter(holder, "$holder");
            if (this$0.selectionMode) {
                if (this$0.selectedIds.contains(Long.valueOf($conv.getThreadId()))) {
                    this$0.selectedIds.remove(Long.valueOf($conv.getThreadId()));
                } else {
                    this$0.selectedIds.add(Long.valueOf($conv.getThreadId()));
                }
                this$1.notifyItemChanged(holder.getBindingAdapterPosition());
                this$0.updateSelectionBar();
                return;
            }
            Function1<ConversationSummary, Unit> function1 = this$1.onClick;
            Intrinsics.checkNotNull($conv);
            function1.invoke($conv);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean onBindViewHolder$lambda$3(TrashActivity this$0, ConversationSummary $conv, TrashAdapter this$1, View it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            if (!this$0.selectionMode) {
                this$0.selectionMode = true;
                this$0.selectedIds.add(Long.valueOf($conv.getThreadId()));
                this$1.notifyDataSetChanged();
                this$0.updateSelectionBar();
            }
            return true;
        }
    }

    /* compiled from: TrashActivity.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/meharenterprises/originsms/ui/TrashActivity$Companion;", "", "()V", "TRASH_DIFF", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/meharenterprises/originsms/core/ConversationSummary;", "getTRASH_DIFF", "()Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DiffUtil.ItemCallback<ConversationSummary> getTRASH_DIFF() {
            return TrashActivity.TRASH_DIFF;
        }
    }
}
