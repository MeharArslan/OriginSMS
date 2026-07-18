package com.meharenterprises.originsms.ui.conversations;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.role.RoleManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ConversationSummary;
import com.meharenterprises.originsms.lock.LockUnlockActivity;
import com.meharenterprises.originsms.lock.PinManager;
import com.meharenterprises.originsms.ui.ArchivedChatsActivity;
import com.meharenterprises.originsms.ui.BlobAnimationView;
import com.meharenterprises.originsms.ui.GeneralSettingsActivity;
import com.meharenterprises.originsms.ui.SettingsActivity;
import com.meharenterprises.originsms.ui.compose.ComposeActivity;
import com.meharenterprises.originsms.ui.thread.ThreadActivity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;

/* compiled from: ConversationListActivity.kt */
@Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 I2\u00020\u0001:\u0001IB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\u001fH\u0002J\b\u0010$\u001a\u00020\u001fH\u0002J\b\u0010%\u001a\u00020\u001fH\u0002J\b\u0010&\u001a\u00020\u0016H\u0002J\b\u0010'\u001a\u00020\u001fH\u0002J\b\u0010(\u001a\u00020\u001fH\u0016J\u0012\u0010)\u001a\u00020\u001f2\b\u0010*\u001a\u0004\u0018\u00010+H\u0014J\u0010\u0010,\u001a\u00020\u00162\u0006\u0010-\u001a\u00020.H\u0016J\u0010\u0010/\u001a\u00020\u00162\u0006\u00100\u001a\u000201H\u0016J\b\u00102\u001a\u00020\u001fH\u0014J\b\u00103\u001a\u00020\u001fH\u0002J\u0010\u00104\u001a\u00020\u001f2\u0006\u00105\u001a\u000206H\u0002J\b\u00107\u001a\u00020\u001fH\u0002J\u0010\u00108\u001a\u00020\u001f2\u0006\u00105\u001a\u000206H\u0002J\b\u00109\u001a\u00020\u001fH\u0002J\b\u0010:\u001a\u00020\u001fH\u0002J\b\u0010;\u001a\u00020\u001fH\u0002J\b\u0010<\u001a\u00020\u001fH\u0002J\b\u0010=\u001a\u00020\u001fH\u0002J\b\u0010>\u001a\u00020\u001fH\u0002J\b\u0010?\u001a\u00020\u001fH\u0002J\b\u0010@\u001a\u00020\u001fH\u0002J\b\u0010A\u001a\u00020\u001fH\u0002J\b\u0010B\u001a\u00020\u001fH\u0002J0\u0010C\u001a\u00020\u001f2\u0006\u0010D\u001a\u00020\n2\f\u0010E\u001a\b\u0012\u0004\u0012\u00020\u001f0F2\u0010\b\u0002\u0010G\u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010FH\u0002J\b\u0010H\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.¢\u0006\u0002\n\u0000¨\u0006J"}, d2 = {"Lcom/meharenterprises/originsms/ui/conversations/ConversationListActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/meharenterprises/originsms/ui/conversations/ConversationAdapter;", "editSearch", "Landroid/widget/EditText;", "permissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "pinManager", "Lcom/meharenterprises/originsms/lock/PinManager;", "prefs", "Landroid/content/SharedPreferences;", "requiredPermissions", "", "roleRequestLauncher", "Landroid/content/Intent;", "searchBarRow", "Landroid/view/View;", "searchModeActive", "", "selectionActionBar", "toolbar", "Lcom/google/android/material/appbar/MaterialToolbar;", "txtSelectionCount", "Landroid/widget/TextView;", "viewModel", "Lcom/meharenterprises/originsms/ui/conversations/ConversationListViewModel;", "animatePulse", "", "view", TypedValues.TransitionType.S_DURATION, "", "ensurePermissionsThenLoad", "enterSearchMode", "exitSearchMode", "isDefaultSmsApp", "maybePromptDefaultAppOnFirstLaunch", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onResume", "openArchivedChats", "openConversation", "conversation", "Lcom/meharenterprises/originsms/core/ConversationSummary;", "openHiddenChatsVault", "openThread", "processScheduledAutoActions", "refreshDefaultAppBanner", "requestDefaultSmsRole", "setupDefaultAppBanner", "setupFab", "setupOnboarding", "setupRecyclerView", "setupSearchBar", "setupSelectionActionBar", "setupToolbar", "showMoveToTrashDialog", "name", "onConfirm", "Lkotlin/Function0;", "onCancel", "updateSelectionBar", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes11.dex */
public final class ConversationListActivity extends AppCompatActivity {
    public static final String KEY_DISPLAY_NAME = "display_name";
    private static final String KEY_PROMPTED_DEFAULT = "prompted_default_on_launch";
    private static final String PREFS_NAME = "origin_sms_app_prefs";
    private ConversationAdapter adapter;
    private EditText editSearch;
    private final ActivityResultLauncher<String[]> permissionLauncher;
    private PinManager pinManager;
    private SharedPreferences prefs;
    private final List<String> requiredPermissions;
    private final ActivityResultLauncher<Intent> roleRequestLauncher;
    private View searchBarRow;
    private boolean searchModeActive;
    private View selectionActionBar;
    private MaterialToolbar toolbar;
    private TextView txtSelectionCount;
    private ConversationListViewModel viewModel;

    public ConversationListActivity() {
        List $this$requiredPermissions_u24lambda_u240 = CollectionsKt.createListBuilder();
        $this$requiredPermissions_u24lambda_u240.add("android.permission.SEND_SMS");
        $this$requiredPermissions_u24lambda_u240.add("android.permission.RECEIVE_SMS");
        $this$requiredPermissions_u24lambda_u240.add("android.permission.READ_SMS");
        $this$requiredPermissions_u24lambda_u240.add("android.permission.READ_CONTACTS");
        $this$requiredPermissions_u24lambda_u240.add("android.permission.READ_PHONE_STATE");
        if (Build.VERSION.SDK_INT >= 33) {
            $this$requiredPermissions_u24lambda_u240.add("android.permission.POST_NOTIFICATIONS");
        }
        this.requiredPermissions = CollectionsKt.build($this$requiredPermissions_u24lambda_u240);
        this.permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda5
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                ConversationListActivity.permissionLauncher$lambda$2(ConversationListActivity.this, (Map) obj);
            }
        });
        this.roleRequestLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda6
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                ConversationListActivity.roleRequestLauncher$lambda$4(ConversationListActivity.this, (ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void permissionLauncher$lambda$2(ConversationListActivity this$0, Map result) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(result, "result");
        Iterable $this$all$iv = result.values();
        boolean z = true;
        if (!($this$all$iv instanceof Collection) || !((Collection) $this$all$iv).isEmpty()) {
            Iterator it = $this$all$iv.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object element$iv = it.next();
                boolean it2 = ((Boolean) element$iv).booleanValue();
                if (!it2) {
                    z = false;
                    break;
                }
            }
        }
        if (z && this$0.isDefaultSmsApp()) {
            ConversationListViewModel conversationListViewModel = this$0.viewModel;
            if (conversationListViewModel == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                conversationListViewModel = null;
            }
            conversationListViewModel.loadConversations();
        }
        this$0.refreshDefaultAppBanner();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void roleRequestLauncher$lambda$4(final ConversationListActivity this$0, ActivityResult it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.refreshDefaultAppBanner();
        ConversationListViewModel conversationListViewModel = this$0.viewModel;
        if (conversationListViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            conversationListViewModel = null;
        }
        conversationListViewModel.loadConversations();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ConversationListActivity.roleRequestLauncher$lambda$4$lambda$3(ConversationListActivity.this);
            }
        }, 1500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void roleRequestLauncher$lambda$4$lambda$3(ConversationListActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ConversationListViewModel conversationListViewModel = this$0.viewModel;
        if (conversationListViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            conversationListViewModel = null;
        }
        conversationListViewModel.loadConversations();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        this.pinManager = new PinManager(this);
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "getSharedPreferences(...)");
        this.prefs = sharedPreferences;
        this.viewModel = (ConversationListViewModel) new ViewModelProvider(this).get(ConversationListViewModel.class);
        setupToolbar();
        setupSearchBar();
        setupSelectionActionBar();
        setupRecyclerView();
        setupFab();
        setupDefaultAppBanner();
        ConversationListViewModel conversationListViewModel = this.viewModel;
        if (conversationListViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            conversationListViewModel = null;
        }
        conversationListViewModel.getConversations().observe(this, new ConversationListActivity$sam$androidx_lifecycle_Observer$0(new Function1<List<? extends ConversationSummary>, Unit>() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$onCreate$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends ConversationSummary> list) {
                invoke2((List<ConversationSummary>) list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<ConversationSummary> list) {
                ConversationAdapter conversationAdapter;
                conversationAdapter = ConversationListActivity.this.adapter;
                if (conversationAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    conversationAdapter = null;
                }
                conversationAdapter.submitList(list);
                ConversationListActivity.this.findViewById(R.id.emptyState).setVisibility(list.isEmpty() ? 0 : 8);
            }
        }));
        maybePromptDefaultAppOnFirstLaunch();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        ensurePermissionsThenLoad();
        refreshDefaultAppBanner();
        processScheduledAutoActions();
        SharedPreferences sharedPreferences = this.prefs;
        MaterialToolbar materialToolbar = null;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        String displayName = sharedPreferences.getString("display_name", null);
        MaterialToolbar materialToolbar2 = this.toolbar;
        if (materialToolbar2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolbar");
        } else {
            materialToolbar = materialToolbar2;
        }
        String str = displayName;
        materialToolbar.setTitle(!(str == null || StringsKt.isBlank(str)) ? displayName : getString(R.string.title_conversations));
    }

    private final void processScheduledAutoActions() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new ConversationListActivity$processScheduledAutoActions$1(this, null), 3, null);
        refreshDefaultAppBanner();
    }

    private final void setupToolbar() {
        View findViewById = findViewById(R.id.toolbar);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.toolbar = (MaterialToolbar) findViewById;
        MaterialToolbar materialToolbar = this.toolbar;
        MaterialToolbar materialToolbar2 = null;
        if (materialToolbar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolbar");
            materialToolbar = null;
        }
        setSupportActionBar(materialToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        boolean z = true;
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(true);
        }
        SharedPreferences sharedPreferences = this.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        String displayName = sharedPreferences.getString("display_name", null);
        MaterialToolbar materialToolbar3 = this.toolbar;
        if (materialToolbar3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolbar");
        } else {
            materialToolbar2 = materialToolbar3;
        }
        String str = displayName;
        if (str != null && !StringsKt.isBlank(str)) {
            z = false;
        }
        materialToolbar2.setTitle(!z ? displayName : getString(R.string.title_conversations));
    }

    private final void setupSearchBar() {
        View findViewById = findViewById(R.id.searchBarRow);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.searchBarRow = findViewById;
        View findViewById2 = findViewById(R.id.editSearch);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
        this.editSearch = (EditText) findViewById2;
        findViewById(R.id.btnCloseSearch).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConversationListActivity.setupSearchBar$lambda$5(ConversationListActivity.this, view);
            }
        });
        EditText editText = this.editSearch;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editSearch");
            editText = null;
        }
        editText.addTextChangedListener(new TextWatcher() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$setupSearchBar$2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                ConversationListViewModel conversationListViewModel;
                PinManager pinManager;
                EditText editText2;
                ConversationListViewModel conversationListViewModel2 = null;
                EditText editText3 = null;
                String obj = s != null ? s.toString() : null;
                if (obj == null) {
                    obj = "";
                }
                if (obj.length() >= 4) {
                    pinManager = ConversationListActivity.this.pinManager;
                    if (pinManager == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("pinManager");
                        pinManager = null;
                    }
                    if (pinManager.verifyPin(obj)) {
                        editText2 = ConversationListActivity.this.editSearch;
                        if (editText2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("editSearch");
                        } else {
                            editText3 = editText2;
                        }
                        editText3.setText("");
                        ConversationListActivity.this.exitSearchMode();
                        ConversationListActivity.this.openHiddenChatsVault();
                        return;
                    }
                }
                conversationListViewModel = ConversationListActivity.this.viewModel;
                if (conversationListViewModel == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                } else {
                    conversationListViewModel2 = conversationListViewModel;
                }
                conversationListViewModel2.setSearchQuery(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSearchBar$lambda$5(ConversationListActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.exitSearchMode();
    }

    private final void enterSearchMode() {
        this.searchModeActive = true;
        MaterialToolbar materialToolbar = this.toolbar;
        EditText editText = null;
        if (materialToolbar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolbar");
            materialToolbar = null;
        }
        materialToolbar.setVisibility(8);
        View view = this.searchBarRow;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchBarRow");
            view = null;
        }
        view.setVisibility(0);
        EditText editText2 = this.editSearch;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editSearch");
            editText2 = null;
        }
        editText2.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(InputMethodManager.class);
        EditText editText3 = this.editSearch;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editSearch");
        } else {
            editText = editText3;
        }
        imm.showSoftInput(editText, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void exitSearchMode() {
        this.searchModeActive = false;
        EditText editText = this.editSearch;
        MaterialToolbar materialToolbar = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editSearch");
            editText = null;
        }
        editText.setText("");
        ConversationListViewModel conversationListViewModel = this.viewModel;
        if (conversationListViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            conversationListViewModel = null;
        }
        conversationListViewModel.setSearchQuery("");
        View view = this.searchBarRow;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchBarRow");
            view = null;
        }
        view.setVisibility(8);
        MaterialToolbar materialToolbar2 = this.toolbar;
        if (materialToolbar2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolbar");
        } else {
            materialToolbar = materialToolbar2;
        }
        materialToolbar.setVisibility(0);
    }

    private final void setupSelectionActionBar() {
        View findViewById = findViewById(R.id.selectionActionBar);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.selectionActionBar = findViewById;
        View findViewById2 = findViewById(R.id.txtSelectionCount);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
        this.txtSelectionCount = (TextView) findViewById2;
        findViewById(R.id.btnCloseSelection).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConversationListActivity.setupSelectionActionBar$lambda$6(ConversationListActivity.this, view);
            }
        });
        findViewById(R.id.btnSelectionPin).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConversationListActivity.setupSelectionActionBar$lambda$7(ConversationListActivity.this, view);
            }
        });
        findViewById(R.id.btnSelectionArchive).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConversationListActivity.setupSelectionActionBar$lambda$8(ConversationListActivity.this, view);
            }
        });
        findViewById(R.id.btnSelectionDelete).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConversationListActivity.setupSelectionActionBar$lambda$10(ConversationListActivity.this, view);
            }
        });
        findViewById(R.id.btnSelectionMore).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConversationListActivity.setupSelectionActionBar$lambda$12(ConversationListActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSelectionActionBar$lambda$6(ConversationListActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ConversationAdapter conversationAdapter = this$0.adapter;
        if (conversationAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            conversationAdapter = null;
        }
        conversationAdapter.clearSelection();
        this$0.updateSelectionBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSelectionActionBar$lambda$7(ConversationListActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ConversationListViewModel conversationListViewModel = this$0.viewModel;
        ConversationAdapter conversationAdapter = null;
        if (conversationListViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            conversationListViewModel = null;
        }
        ConversationAdapter conversationAdapter2 = this$0.adapter;
        if (conversationAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            conversationAdapter2 = null;
        }
        conversationListViewModel.setPinned(conversationAdapter2.getSelectedThreadIds(), true);
        ConversationAdapter conversationAdapter3 = this$0.adapter;
        if (conversationAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            conversationAdapter = conversationAdapter3;
        }
        conversationAdapter.clearSelection();
        this$0.updateSelectionBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSelectionActionBar$lambda$8(ConversationListActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ConversationListViewModel conversationListViewModel = this$0.viewModel;
        ConversationAdapter conversationAdapter = null;
        if (conversationListViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            conversationListViewModel = null;
        }
        ConversationAdapter conversationAdapter2 = this$0.adapter;
        if (conversationAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            conversationAdapter2 = null;
        }
        conversationListViewModel.setArchived(conversationAdapter2.getSelectedThreadIds(), true);
        ConversationAdapter conversationAdapter3 = this$0.adapter;
        if (conversationAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            conversationAdapter = conversationAdapter3;
        }
        conversationAdapter.clearSelection();
        this$0.updateSelectionBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSelectionActionBar$lambda$10(ConversationListActivity this$0, View it) {
        String str;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ConversationAdapter conversationAdapter = this$0.adapter;
        Object obj = null;
        if (conversationAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            conversationAdapter = null;
        }
        Set ids = conversationAdapter.getSelectedThreadIds();
        int count = ids.size();
        if (count == 1) {
            ConversationAdapter conversationAdapter2 = this$0.adapter;
            if (conversationAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                conversationAdapter2 = null;
            }
            Iterable currentList = conversationAdapter2.getCurrentList();
            Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
            Iterable $this$firstOrNull$iv = currentList;
            Iterator it2 = $this$firstOrNull$iv.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Object element$iv = it2.next();
                ConversationSummary it3 = (ConversationSummary) element$iv;
                if (ids.contains(Long.valueOf(it3.getThreadId()))) {
                    obj = element$iv;
                    break;
                }
            }
            ConversationSummary conversationSummary = (ConversationSummary) obj;
            if (conversationSummary == null || (str = conversationSummary.getDisplayName()) == null) {
                str = "Chat";
            }
        } else {
            str = count + " chats";
        }
        String name = str;
        showMoveToTrashDialog$default(this$0, name, new ConversationListActivity$setupSelectionActionBar$4$1(this$0, ids, count), null, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSelectionActionBar$lambda$12(final ConversationListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        PopupMenu popup = new PopupMenu(this$0, view);
        popup.getMenuInflater().inflate(R.menu.menu_selection_more, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda0
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                boolean z;
                z = ConversationListActivity.setupSelectionActionBar$lambda$12$lambda$11(ConversationListActivity.this, menuItem);
                return z;
            }
        });
        popup.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupSelectionActionBar$lambda$12$lambda$11(ConversationListActivity this$0, MenuItem item) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ConversationAdapter conversationAdapter = this$0.adapter;
        ConversationAdapter conversationAdapter2 = null;
        if (conversationAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            conversationAdapter = null;
        }
        Set ids = conversationAdapter.getSelectedThreadIds();
        int itemId = item.getItemId();
        if (itemId == R.id.action_mark_read) {
            ConversationListViewModel conversationListViewModel = this$0.viewModel;
            if (conversationListViewModel == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                conversationListViewModel = null;
            }
            conversationListViewModel.markReadMultiple(ids);
            ConversationAdapter conversationAdapter3 = this$0.adapter;
            if (conversationAdapter3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
            } else {
                conversationAdapter2 = conversationAdapter3;
            }
            conversationAdapter2.clearSelection();
            this$0.updateSelectionBar();
            return true;
        }
        if (itemId == R.id.action_mark_unread) {
            ConversationListViewModel conversationListViewModel2 = this$0.viewModel;
            if (conversationListViewModel2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                conversationListViewModel2 = null;
            }
            conversationListViewModel2.markUnreadMultiple(ids);
            ConversationAdapter conversationAdapter4 = this$0.adapter;
            if (conversationAdapter4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
            } else {
                conversationAdapter2 = conversationAdapter4;
            }
            conversationAdapter2.clearSelection();
            this$0.updateSelectionBar();
            return true;
        }
        if (itemId == R.id.action_block_selected) {
            ConversationListViewModel conversationListViewModel3 = this$0.viewModel;
            if (conversationListViewModel3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                conversationListViewModel3 = null;
            }
            conversationListViewModel3.blockSelectedThreads(ids, this$0);
            ConversationAdapter conversationAdapter5 = this$0.adapter;
            if (conversationAdapter5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
            } else {
                conversationAdapter2 = conversationAdapter5;
            }
            conversationAdapter2.clearSelection();
            this$0.updateSelectionBar();
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.view.View] */
    public final void updateSelectionBar() {
        ConversationAdapter conversationAdapter = this.adapter;
        TextView textView = null;
        if (conversationAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            conversationAdapter = null;
        }
        int count = conversationAdapter.getSelectedCount();
        if (count > 0) {
            View view = this.selectionActionBar;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("selectionActionBar");
                view = null;
            }
            view.setVisibility(0);
            TextView textView2 = this.txtSelectionCount;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("txtSelectionCount");
            } else {
                textView = textView2;
            }
            textView.setText(String.valueOf(count));
            return;
        }
        android.widget.TextView r3 = this.selectionActionBar;
        if (r3 == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("selectionActionBar");
        } else {
            textView = r3;
        }
        textView.setVisibility(8);
    }

    private final void setupRecyclerView() {
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recyclerConversations);
        this.adapter = new ConversationAdapter(new Function1<ConversationSummary, Unit>() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$setupRecyclerView$1
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
            public final void invoke2(ConversationSummary conversation) {
                ConversationAdapter conversationAdapter;
                Intrinsics.checkNotNullParameter(conversation, "conversation");
                conversationAdapter = ConversationListActivity.this.adapter;
                if (conversationAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    conversationAdapter = null;
                }
                if (conversationAdapter.getIsSelectionMode()) {
                    ConversationListActivity.this.updateSelectionBar();
                } else {
                    ConversationListActivity.this.openConversation(conversation);
                }
            }
        }, new Function1<ConversationSummary, Unit>() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$setupRecyclerView$2
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
            public final void invoke2(ConversationSummary it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ConversationListActivity.this.updateSelectionBar();
            }
        }, false, 4, null);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        ConversationAdapter conversationAdapter = this.adapter;
        if (conversationAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            conversationAdapter = null;
        }
        recycler.setAdapter(conversationAdapter);
        SharedPreferences swipePrefs = getSharedPreferences(GeneralSettingsActivity.PREFS_NAME, 0);
        ConversationListActivity$setupRecyclerView$swipeCallback$1 swipeCallback = new ConversationListActivity$setupRecyclerView$swipeCallback$1(this, swipePrefs);
        new ItemTouchHelper(swipeCallback).attachToRecyclerView(recycler);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void showMoveToTrashDialog$default(ConversationListActivity conversationListActivity, String str, Function0 function0, Function0 function02, int i, Object obj) {
        if ((i & 4) != 0) {
            function02 = null;
        }
        conversationListActivity.showMoveToTrashDialog(str, function0, function02);
    }

    private final void showMoveToTrashDialog(String name, final Function0<Unit> onConfirm, final Function0<Unit> onCancel) {
        AlertDialog dialog = new MaterialAlertDialogBuilder(this, com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered).setTitle((CharSequence) "Delete conversation?").setMessage((CharSequence) "This conversation will be moved to Trash and can be restored before it is permanently deleted.").setNegativeButton((CharSequence) "Cancel", new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda14
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ConversationListActivity.showMoveToTrashDialog$lambda$13(Function0.this, dialogInterface, i);
            }
        }).setPositiveButton((CharSequence) "Move to Trash", new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda15
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ConversationListActivity.showMoveToTrashDialog$lambda$14(Function0.this, dialogInterface, i);
            }
        }).create();
        Intrinsics.checkNotNullExpressionValue(dialog, "create(...)");
        dialog.show();
        Button btn = dialog.getButton(-1);
        if (btn != null) {
            btn.setTextColor(getColor(R.color.origin_accent));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showMoveToTrashDialog$lambda$13(Function0 $onCancel, DialogInterface d, int i) {
        d.dismiss();
        if ($onCancel != null) {
            $onCancel.invoke();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showMoveToTrashDialog$lambda$14(Function0 onConfirm, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(onConfirm, "$onConfirm");
        onConfirm.invoke();
    }

    private final void setupFab() {
        final ExtendedFloatingActionButton fabNew = (ExtendedFloatingActionButton) findViewById(R.id.fabNewMessage);
        fabNew.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConversationListActivity.setupFab$lambda$16(ConversationListActivity.this, view);
            }
        });
        final FloatingActionButton fabScrollTop = (FloatingActionButton) findViewById(R.id.fabScrollTop);
        final RecyclerView recycler = (RecyclerView) findViewById(R.id.recyclerConversations);
        fabScrollTop.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RecyclerView.this.smoothScrollToPosition(0);
            }
        });
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$setupFab$3
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                Intrinsics.checkNotNullParameter(rv, "rv");
                RecyclerView.LayoutManager layoutManager = rv.getLayoutManager();
                LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
                int firstVisible = linearLayoutManager != null ? linearLayoutManager.findFirstVisibleItemPosition() : 0;
                FloatingActionButton.this.setVisibility(firstVisible <= 5 ? 8 : 0);
                if (dy > 10) {
                    fabNew.shrink();
                } else if (dy < -10 || firstVisible == 0) {
                    fabNew.extend();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFab$lambda$16(ConversationListActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) ComposeActivity.class));
    }

    private final void setupDefaultAppBanner() {
        setupOnboarding();
    }

    private final void setupOnboarding() {
        View banner = findViewById(R.id.defaultAppBanner);
        banner.setVisibility(8);
        ViewGroup container = (ViewGroup) findViewById(R.id.onboardingContainer);
        if (container == null) {
            return;
        }
        View findViewById = container.findViewById(R.id.btnSetDefaultOnboarding);
        if (findViewById != null) {
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ConversationListActivity.setupOnboarding$lambda$18(ConversationListActivity.this, view);
                }
            });
        }
        BlobAnimationView blobAnimationView = (BlobAnimationView) container.findViewById(R.id.blobBackground);
        if (blobAnimationView != null) {
            blobAnimationView.startAnimation();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupOnboarding$lambda$18(ConversationListActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.requestDefaultSmsRole();
    }

    private final void maybePromptDefaultAppOnFirstLaunch() {
        SharedPreferences sharedPreferences = this.prefs;
        SharedPreferences sharedPreferences2 = null;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        boolean alreadyPrompted = sharedPreferences.getBoolean(KEY_PROMPTED_DEFAULT, false);
        if (!alreadyPrompted && !isDefaultSmsApp()) {
            SharedPreferences sharedPreferences3 = this.prefs;
            if (sharedPreferences3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("prefs");
            } else {
                sharedPreferences2 = sharedPreferences3;
            }
            sharedPreferences2.edit().putBoolean(KEY_PROMPTED_DEFAULT, true).apply();
        }
    }

    private final void animatePulse(View view, long duration) {
        ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.08f, 1.0f), PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.08f, 1.0f));
        Intrinsics.checkNotNullExpressionValue(anim, "ofPropertyValuesHolder(...)");
        anim.setDuration(duration);
        anim.setRepeatCount(-1);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
    }

    private final void refreshDefaultAppBanner() {
        final View onboarding = findViewById(R.id.onboardingContainer);
        if (onboarding == null) {
            return;
        }
        View recycler = findViewById(R.id.recyclerConversations);
        View emptyState = findViewById(R.id.emptyState);
        findViewById(R.id.fabNewMessage);
        if (recycler != null) {
            ViewParent parent = recycler.getParent();
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup != null) {
                ViewGroup p = viewGroup;
                p.findViewById(R.id.fabScrollTop);
            }
        }
        if (isDefaultSmsApp()) {
            if (onboarding.getVisibility() == 0) {
                onboarding.animate().alpha(0.0f).setDuration(300L).withEndAction(new Runnable() { // from class: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        ConversationListActivity.refreshDefaultAppBanner$lambda$21(onboarding);
                    }
                }).start();
            }
            if (recycler != null) {
                recycler.setVisibility(0);
            }
            View findViewById = findViewById(R.id.fabWrapper);
            if (findViewById == null) {
                return;
            }
            findViewById.setVisibility(0);
            return;
        }
        if (recycler != null) {
            recycler.setVisibility(8);
        }
        if (emptyState != null) {
            emptyState.setVisibility(8);
        }
        View findViewById2 = findViewById(R.id.fabWrapper);
        if (findViewById2 != null) {
            findViewById2.setVisibility(8);
        }
        if (onboarding.getVisibility() != 0) {
            onboarding.setAlpha(0.0f);
            onboarding.setVisibility(0);
            onboarding.animate().alpha(1.0f).setDuration(400L).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void refreshDefaultAppBanner$lambda$21(View onboarding) {
        Intrinsics.checkNotNullParameter(onboarding, "$onboarding");
        onboarding.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x003f, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r0, kotlin.text.StringsKt.removeSuffix(r2, (java.lang.CharSequence) ".debug")) != false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean isDefaultSmsApp() {
        /*
            r4 = this;
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 29
            if (r0 < r1) goto L15
            java.lang.Class<android.app.role.RoleManager> r0 = android.app.role.RoleManager.class
            java.lang.Object r0 = r4.getSystemService(r0)
            android.app.role.RoleManager r0 = (android.app.role.RoleManager) r0
            java.lang.String r1 = "android.app.role.SMS"
            boolean r0 = r0.isRoleHeld(r1)
            goto L43
        L15:
            r0 = r4
            android.content.Context r0 = (android.content.Context) r0
            java.lang.String r0 = android.provider.Telephony.Sms.getDefaultSmsPackage(r0)
            r1 = 0
            if (r0 != 0) goto L20
            return r1
        L20:
            java.lang.String r2 = r4.getPackageName()
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r2)
            if (r2 != 0) goto L41
            java.lang.String r2 = r4.getPackageName()
            java.lang.String r3 = "getPackageName(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r3)
            java.lang.String r3 = ".debug"
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3
            java.lang.String r2 = kotlin.text.StringsKt.removeSuffix(r2, r3)
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r2)
            if (r2 == 0) goto L42
        L41:
            r1 = 1
        L42:
            r0 = r1
        L43:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.conversations.ConversationListActivity.isDefaultSmsApp():boolean");
    }

    private final void requestDefaultSmsRole() {
        if (Build.VERSION.SDK_INT >= 29) {
            RoleManager roleManager = (RoleManager) getSystemService(RoleManager.class);
            if (roleManager.isRoleAvailable("android.app.role.SMS") && !roleManager.isRoleHeld("android.app.role.SMS")) {
                Intent intent = roleManager.createRequestRoleIntent("android.app.role.SMS");
                Intrinsics.checkNotNullExpressionValue(intent, "createRequestRoleIntent(...)");
                this.roleRequestLauncher.launch(intent);
                return;
            }
            return;
        }
        Intent intent2 = new Intent("android.provider.Telephony.ACTION_CHANGE_DEFAULT");
        intent2.putExtra("package", getPackageName());
        this.roleRequestLauncher.launch(intent2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void ensurePermissionsThenLoad() {
        Iterable $this$filter$iv = this.requiredPermissions;
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            String it2 = (String) element$iv$iv;
            if (ContextCompat.checkSelfPermission(this, it2) != 0) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Collection missing = (List) destination$iv$iv;
        if (!missing.isEmpty()) {
            Collection $this$toTypedArray$iv = missing;
            this.permissionLauncher.launch($this$toTypedArray$iv.toArray(new String[0]));
        } else {
            if (!isDefaultSmsApp()) {
                refreshDefaultAppBanner();
                return;
            }
            ConversationListViewModel conversationListViewModel = this.viewModel;
            if (conversationListViewModel == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                conversationListViewModel = null;
            }
            conversationListViewModel.loadConversations();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openConversation(ConversationSummary conversation) {
        if (conversation.isLocked()) {
            Intent intent = new Intent(this, (Class<?>) LockUnlockActivity.class);
            intent.putExtra("extra_thread_id", conversation.getThreadId());
            intent.putExtra("extra_address", conversation.getAddress());
            intent.putExtra("extra_display_name", conversation.getDisplayName());
            startActivity(intent);
            return;
        }
        openThread(conversation);
    }

    private final void openThread(ConversationSummary conversation) {
        Intent intent = new Intent(this, (Class<?>) ThreadActivity.class);
        intent.putExtra("extra_thread_id", conversation.getThreadId());
        intent.putExtra("extra_address", conversation.getAddress());
        intent.putExtra("extra_display_name", conversation.getDisplayName());
        startActivity(intent);
        if (conversation.getUnreadCount() > 0) {
            ConversationListViewModel conversationListViewModel = this.viewModel;
            if (conversationListViewModel == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                conversationListViewModel = null;
            }
            conversationListViewModel.markRead(conversation.getThreadId());
        }
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        getMenuInflater().inflate(R.menu.menu_conversation_list, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        int itemId = item.getItemId();
        if (itemId == R.id.action_search) {
            enterSearchMode();
            return true;
        }
        if (itemId == R.id.action_settings) {
            startActivity(new Intent(this, (Class<?>) SettingsActivity.class));
            return true;
        }
        if (itemId == R.id.action_archived_chats) {
            openArchivedChats();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        ConversationAdapter conversationAdapter = this.adapter;
        ConversationAdapter conversationAdapter2 = null;
        if (conversationAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            conversationAdapter = null;
        }
        if (conversationAdapter.getSelectedCount() > 0) {
            ConversationAdapter conversationAdapter3 = this.adapter;
            if (conversationAdapter3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
            } else {
                conversationAdapter2 = conversationAdapter3;
            }
            conversationAdapter2.clearSelection();
            updateSelectionBar();
            return;
        }
        if (!this.searchModeActive) {
            super.onBackPressed();
        } else {
            exitSearchMode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openHiddenChatsVault() {
        Intent intent = new Intent(this, (Class<?>) LockUnlockActivity.class);
        intent.putExtra(LockUnlockActivity.EXTRA_UNLOCK_INTENT, LockUnlockActivity.INTENT_OPEN_VAULT);
        startActivity(intent);
    }

    private final void openArchivedChats() {
        startActivity(new Intent(this, (Class<?>) ArchivedChatsActivity.class));
    }
}
