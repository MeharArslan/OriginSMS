package com.meharenterprises.originsms.ui;

import android.app.TimePickerDialog;
import android.app.role.RoleManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwnerKt;
import com.google.android.material.appbar.MaterialToolbar;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ConversationSummary;
import com.meharenterprises.originsms.lock.LockSetupActivity;
import com.meharenterprises.originsms.lock.LockUnlockActivity;
import com.meharenterprises.originsms.lock.PinManager;
import com.meharenterprises.originsms.ui.ThemePreferenceManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;

/* compiled from: SettingsActivity.kt */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 32\u00020\u0001:\u00013B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\"\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\b\u0010\u001a\u001a\u0004\u0018\u00010\bH\u0015J\u0012\u0010\u001b\u001a\u00020\u00162\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\b\u0010\u001e\u001a\u00020\u0016H\u0014J\b\u0010\u001f\u001a\u00020\u0016H\u0002J\b\u0010 \u001a\u00020\u0016H\u0002J\b\u0010!\u001a\u00020\u0016H\u0002J\b\u0010\"\u001a\u00020\u0016H\u0002J\b\u0010#\u001a\u00020\u0016H\u0002J\b\u0010$\u001a\u00020\u0016H\u0002J\b\u0010%\u001a\u00020\u0016H\u0002J\b\u0010&\u001a\u00020\u0016H\u0002J\b\u0010'\u001a\u00020\u0016H\u0002J\b\u0010(\u001a\u00020\u0016H\u0002J\b\u0010)\u001a\u00020\u0016H\u0002J\b\u0010*\u001a\u00020\u0016H\u0002J\b\u0010+\u001a\u00020\u0016H\u0002J\b\u0010,\u001a\u00020\u0016H\u0002J\u0010\u0010-\u001a\u00020\u00162\u0006\u0010.\u001a\u00020\u0005H\u0002J\u0010\u0010/\u001a\u0002002\u0006\u00101\u001a\u000202H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lcom/meharenterprises/originsms/ui/SettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "cachedConversations", "", "Lcom/meharenterprises/originsms/core/ConversationSummary;", "defaultAppRoleLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "pinManager", "Lcom/meharenterprises/originsms/lock/PinManager;", "switchBiometric", "Landroid/widget/Switch;", "themeManager", "Lcom/meharenterprises/originsms/ui/ThemePreferenceManager;", "txtChatLockStatus", "Landroid/widget/TextView;", "txtDefaultAppStatus", "txtThemeStatus", "isDefaultSmsApp", "", "onActivityResult", "", "requestCode", "", "resultCode", "data", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "preloadConversations", "refreshStatuses", "requestDefaultSmsRole", "setupAutoHideTimerRow", "setupBiometricRow", "setupBlockedNumbersRow", "setupChatLockRow", "setupDefaultAppRow", "setupDisplayNameRow", "setupGeneralSettingsRow", "setupResetPinRow", "setupThemeRow", "setupTrashRow", "showAutoHideContactPicker", "showDailyHideTimePicker", "conversation", "themeLabel", "", "mode", "Lcom/meharenterprises/originsms/ui/ThemePreferenceManager$ThemeMode;", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class SettingsActivity extends AppCompatActivity {
    public static final String KEY_DISPLAY_NAME = "display_name";
    private static final int REQUEST_RESET_PIN = 1001;
    public static final long RESET_PIN_SENTINEL_THREAD_ID = -2;
    private List<ConversationSummary> cachedConversations = CollectionsKt.emptyList();
    private final ActivityResultLauncher<Intent> defaultAppRoleLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda18
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            SettingsActivity.defaultAppRoleLauncher$lambda$9(SettingsActivity.this, (ActivityResult) obj);
        }
    });
    private PinManager pinManager;
    private Switch switchBiometric;
    private ThemePreferenceManager themeManager;
    private TextView txtChatLockStatus;
    private TextView txtDefaultAppStatus;
    private TextView txtThemeStatus;

    /* compiled from: SettingsActivity.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ThemePreferenceManager.ThemeMode.values().length];
            try {
                iArr[ThemePreferenceManager.ThemeMode.LIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[ThemePreferenceManager.ThemeMode.DARK.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[ThemePreferenceManager.ThemeMode.SYSTEM.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.pinManager = new PinManager(this);
        this.themeManager = new ThemePreferenceManager(this);
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.onCreate$lambda$0(SettingsActivity.this, view);
            }
        });
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("Settings");
        }
        ActionBar supportActionBar2 = getSupportActionBar();
        if (supportActionBar2 != null) {
            supportActionBar2.setDisplayShowTitleEnabled(true);
        }
        View findViewById = findViewById(R.id.switchBiometric);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.switchBiometric = (Switch) findViewById;
        View findViewById2 = findViewById(R.id.txtChatLockStatus);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
        this.txtChatLockStatus = (TextView) findViewById2;
        View findViewById3 = findViewById(R.id.txtDefaultAppStatus);
        Intrinsics.checkNotNullExpressionValue(findViewById3, "findViewById(...)");
        this.txtDefaultAppStatus = (TextView) findViewById3;
        View findViewById4 = findViewById(R.id.txtThemeStatus);
        Intrinsics.checkNotNullExpressionValue(findViewById4, "findViewById(...)");
        this.txtThemeStatus = (TextView) findViewById4;
        setupChatLockRow();
        setupBiometricRow();
        setupResetPinRow();
        setupDefaultAppRow();
        setupThemeRow();
        setupAutoHideTimerRow();
        setupTrashRow();
        setupGeneralSettingsRow();
        setupBlockedNumbersRow();
        setupDisplayNameRow();
        preloadConversations();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(SettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void preloadConversations() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new SettingsActivity$preloadConversations$1(this, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        refreshStatuses();
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void refreshStatuses() {
        /*
            r8 = this;
            android.widget.TextView r0 = r8.txtChatLockStatus
            r1 = 0
            if (r0 != 0) goto Lb
            java.lang.String r0 = "txtChatLockStatus"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            r0 = r1
        Lb:
            com.meharenterprises.originsms.lock.PinManager r2 = r8.pinManager
            java.lang.String r3 = "pinManager"
            if (r2 != 0) goto L15
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r3)
            r2 = r1
        L15:
            boolean r2 = r2.hasPinConfigured()
            if (r2 == 0) goto L1e
            java.lang.String r2 = "Enabled"
            goto L20
        L1e:
            java.lang.String r2 = "Not set up"
        L20:
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            r0.setText(r2)
            android.widget.TextView r0 = r8.txtDefaultAppStatus
            if (r0 != 0) goto L2f
            java.lang.String r0 = "txtDefaultAppStatus"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            r0 = r1
        L2f:
            boolean r2 = r8.isDefaultSmsApp()
            if (r2 == 0) goto L38
            java.lang.String r2 = "Yes"
            goto L3a
        L38:
            java.lang.String r2 = "No — tap to set"
        L3a:
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            r0.setText(r2)
            android.widget.TextView r0 = r8.txtThemeStatus
            if (r0 != 0) goto L49
            java.lang.String r0 = "txtThemeStatus"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            r0 = r1
        L49:
            com.meharenterprises.originsms.ui.ThemePreferenceManager r2 = r8.themeManager
            if (r2 != 0) goto L53
            java.lang.String r2 = "themeManager"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            r2 = r1
        L53:
            com.meharenterprises.originsms.ui.ThemePreferenceManager$ThemeMode r2 = r2.getCurrentMode()
            java.lang.String r2 = r8.themeLabel(r2)
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            r0.setText(r2)
            r0 = r8
            android.content.Context r0 = (android.content.Context) r0
            androidx.biometric.BiometricManager r0 = androidx.biometric.BiometricManager.from(r0)
            r2 = 15
            int r0 = r0.canAuthenticate(r2)
            r2 = 1
            r4 = 0
            if (r0 != 0) goto L73
            r0 = r2
            goto L74
        L73:
            r0 = r4
        L74:
            android.widget.Switch r5 = r8.switchBiometric
            java.lang.String r6 = "switchBiometric"
            if (r5 != 0) goto L7e
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r6)
            r5 = r1
        L7e:
            if (r0 == 0) goto L90
            com.meharenterprises.originsms.lock.PinManager r7 = r8.pinManager
            if (r7 != 0) goto L88
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r3)
            r7 = r1
        L88:
            boolean r7 = r7.hasPinConfigured()
            if (r7 == 0) goto L90
            r7 = r2
            goto L91
        L90:
            r7 = r4
        L91:
            r5.setEnabled(r7)
            android.widget.Switch r5 = r8.switchBiometric
            if (r5 != 0) goto L9c
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r6)
            r5 = r1
        L9c:
            com.meharenterprises.originsms.lock.PinManager r6 = r8.pinManager
            if (r6 != 0) goto La4
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r3)
            goto La5
        La4:
            r1 = r6
        La5:
            boolean r1 = r1.isBiometricEnabled()
            if (r1 == 0) goto Lae
            if (r0 == 0) goto Lae
            goto Laf
        Lae:
            r2 = r4
        Laf:
            r5.setChecked(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.SettingsActivity.refreshStatuses():void");
    }

    private final String themeLabel(ThemePreferenceManager.ThemeMode mode) {
        switch (WhenMappings.$EnumSwitchMapping$0[mode.ordinal()]) {
            case 1:
                return "Light";
            case 2:
                return "Dark";
            case 3:
                return "System default";
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final void setupThemeRow() {
        findViewById(R.id.rowTheme).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupThemeRow$lambda$2(SettingsActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupThemeRow$lambda$2(final SettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        final String[] options = {"Light", "Dark", "System default"};
        final ThemePreferenceManager.ThemeMode[] modes = {ThemePreferenceManager.ThemeMode.LIGHT, ThemePreferenceManager.ThemeMode.DARK, ThemePreferenceManager.ThemeMode.SYSTEM};
        ThemePreferenceManager themePreferenceManager = this$0.themeManager;
        if (themePreferenceManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("themeManager");
            themePreferenceManager = null;
        }
        int currentIndex = ArraysKt.indexOf(modes, themePreferenceManager.getCurrentMode());
        new AlertDialog.Builder(this$0).setTitle(R.string.settings_theme).setSingleChoiceItems(options, currentIndex, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.setupThemeRow$lambda$2$lambda$1(SettingsActivity.this, modes, options, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupThemeRow$lambda$2$lambda$1(SettingsActivity this$0, ThemePreferenceManager.ThemeMode[] modes, String[] options, DialogInterface dialog, int index) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(modes, "$modes");
        Intrinsics.checkNotNullParameter(options, "$options");
        ThemePreferenceManager themePreferenceManager = this$0.themeManager;
        TextView textView = null;
        if (themePreferenceManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("themeManager");
            themePreferenceManager = null;
        }
        themePreferenceManager.setMode(modes[index]);
        TextView textView2 = this$0.txtThemeStatus;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("txtThemeStatus");
        } else {
            textView = textView2;
        }
        textView.setText(options[index]);
        dialog.dismiss();
    }

    private final void setupChatLockRow() {
        findViewById(R.id.rowChatLock).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupChatLockRow$lambda$3(SettingsActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupChatLockRow$lambda$3(SettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        PinManager pinManager = this$0.pinManager;
        if (pinManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pinManager");
            pinManager = null;
        }
        if (!pinManager.hasPinConfigured()) {
            this$0.startActivity(new Intent(this$0, (Class<?>) LockSetupActivity.class));
        } else {
            new AlertDialog.Builder(this$0).setTitle(R.string.settings_chat_lock).setMessage("Chat lock is active. You can change your PIN from \"Forgot PIN?\" below.").setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).show();
        }
    }

    private final void setupBiometricRow() {
        findViewById(R.id.rowBiometric).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupBiometricRow$lambda$4(SettingsActivity.this, view);
            }
        });
        Switch r0 = this.switchBiometric;
        if (r0 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("switchBiometric");
            r0 = null;
        }
        r0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda11
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsActivity.setupBiometricRow$lambda$5(SettingsActivity.this, compoundButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupBiometricRow$lambda$4(SettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Switch r0 = this$0.switchBiometric;
        Switch r1 = null;
        if (r0 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("switchBiometric");
            r0 = null;
        }
        if (r0.isEnabled()) {
            Switch r02 = this$0.switchBiometric;
            if (r02 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("switchBiometric");
                r02 = null;
            }
            Switch r3 = this$0.switchBiometric;
            if (r3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("switchBiometric");
            } else {
                r1 = r3;
            }
            r02.setChecked(!r1.isChecked());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupBiometricRow$lambda$5(SettingsActivity this$0, CompoundButton compoundButton, boolean isChecked) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        PinManager pinManager = this$0.pinManager;
        if (pinManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pinManager");
            pinManager = null;
        }
        pinManager.setBiometricEnabled(isChecked);
    }

    private final void setupResetPinRow() {
        findViewById(R.id.rowResetPin).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupResetPinRow$lambda$8(SettingsActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupResetPinRow$lambda$8(final SettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        PinManager pinManager = this$0.pinManager;
        if (pinManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pinManager");
            pinManager = null;
        }
        if (!pinManager.hasPinConfigured()) {
            this$0.startActivity(new Intent(this$0, (Class<?>) LockSetupActivity.class));
        } else {
            new AlertDialog.Builder(this$0).setTitle(R.string.lock_forgot_pin).setMessage(R.string.lock_reset_warning).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    SettingsActivity.setupResetPinRow$lambda$8$lambda$7(SettingsActivity.this, dialogInterface, i);
                }
            }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupResetPinRow$lambda$8$lambda$7(SettingsActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent intent = new Intent(this$0, (Class<?>) LockUnlockActivity.class);
        intent.putExtra(LockUnlockActivity.EXTRA_UNLOCK_INTENT, LockUnlockActivity.INTENT_REMOVE_LOCK);
        intent.putExtra("extra_thread_id", -2L);
        this$0.startActivityForResult(intent, 1001);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    @Deprecated(message = "Deprecated in Java")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == -1) {
            PinManager pinManager = this.pinManager;
            if (pinManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pinManager");
                pinManager = null;
            }
            pinManager.clearPin();
            startActivity(new Intent(this, (Class<?>) LockSetupActivity.class));
            refreshStatuses();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void defaultAppRoleLauncher$lambda$9(SettingsActivity this$0, ActivityResult it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.refreshStatuses();
    }

    private final void setupDefaultAppRow() {
        findViewById(R.id.rowDefaultApp).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupDefaultAppRow$lambda$10(SettingsActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupDefaultAppRow$lambda$10(SettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.requestDefaultSmsRole();
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x003f, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r0, kotlin.text.StringsKt.removeSuffix(r2, (java.lang.CharSequence) ".debug")) != false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final boolean isDefaultSmsApp() {
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
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.SettingsActivity.isDefaultSmsApp():boolean");
    }

    private final void requestDefaultSmsRole() {
        if (isDefaultSmsApp()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            RoleManager roleManager = (RoleManager) getSystemService(RoleManager.class);
            if (roleManager.isRoleAvailable("android.app.role.SMS")) {
                ActivityResultLauncher<Intent> activityResultLauncher = this.defaultAppRoleLauncher;
                Intent createRequestRoleIntent = roleManager.createRequestRoleIntent("android.app.role.SMS");
                Intrinsics.checkNotNullExpressionValue(createRequestRoleIntent, "createRequestRoleIntent(...)");
                activityResultLauncher.launch(createRequestRoleIntent);
                return;
            }
            return;
        }
        Intent intent = new Intent("android.provider.Telephony.ACTION_CHANGE_DEFAULT");
        intent.putExtra("package", getPackageName());
        this.defaultAppRoleLauncher.launch(intent);
    }

    private final void setupAutoHideTimerRow() {
        findViewById(R.id.rowAutoHideTimer).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupAutoHideTimerRow$lambda$12(SettingsActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupAutoHideTimerRow$lambda$12(SettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showAutoHideContactPicker();
    }

    /* JADX WARN: Type inference failed for: r6v6, types: [com.meharenterprises.originsms.ui.SettingsActivity$showAutoHideContactPicker$adapter$1] */
    /* JADX WARN: Type inference failed for: r8v6, types: [java.util.List, T] */
    private final void showAutoHideContactPicker() {
        final List conversations = this.cachedConversations;
        if (conversations.isEmpty()) {
            new AlertDialog.Builder(this).setTitle(R.string.auto_hide_timer_title).setMessage("No chats found. Make sure OriginSMS is set as your default SMS app.").setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).show();
            return;
        }
        LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_1, (ViewGroup) null, false);
        EditText searchInput = new EditText(this);
        searchInput.setHint("Search contacts...");
        searchInput.setSingleLine();
        ListView listView = new ListView(this);
        LinearLayout $this$showAutoHideContactPicker_u24lambda_u2414 = new LinearLayout(this);
        $this$showAutoHideContactPicker_u24lambda_u2414.setOrientation(1);
        int pad = (int) (12 * $this$showAutoHideContactPicker_u24lambda_u2414.getResources().getDisplayMetrics().density);
        $this$showAutoHideContactPicker_u24lambda_u2414.setPadding(pad, pad, pad, 0);
        $this$showAutoHideContactPicker_u24lambda_u2414.addView(searchInput);
        $this$showAutoHideContactPicker_u24lambda_u2414.addView(listView);
        List $this$map$iv = conversations;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            ConversationSummary it = (ConversationSummary) item$iv$iv;
            destination$iv$iv.add(it.getDisplayName());
        }
        final ArrayList arrayList = (List) destination$iv$iv;
        final ?? r6 = new ArrayAdapter<String>(this, arrayList) { // from class: com.meharenterprises.originsms.ui.SettingsActivity$showAutoHideContactPicker$adapter$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                SettingsActivity settingsActivity = this;
            }

            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int position, View convertView, ViewGroup parent) {
                Object obj;
                String str;
                String displayName;
                Intrinsics.checkNotNullParameter(parent, "parent");
                View view = super.getView(position, convertView, parent);
                Intrinsics.checkNotNullExpressionValue(view, "getView(...)");
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                String conv = getItem(position);
                Iterator<T> it2 = conversations.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        obj = null;
                        break;
                    }
                    obj = it2.next();
                    ConversationSummary it3 = (ConversationSummary) obj;
                    if (Intrinsics.areEqual(it3.getDisplayName(), conv) || Intrinsics.areEqual(it3.getAddress(), conv)) {
                        break;
                    }
                }
                ConversationSummary original = (ConversationSummary) obj;
                if (text1 != null) {
                    text1.setText((original == null || (displayName = original.getDisplayName()) == null) ? conv : displayName);
                }
                if (text2 != null) {
                    if (original == null || (str = original.getAddress()) == null) {
                        str = "";
                    }
                    text2.setText(str);
                }
                if (text2 != null) {
                    text2.setTextColor(-7829368);
                }
                return view;
            }
        };
        final Ref.ObjectRef filteredConversations = new Ref.ObjectRef();
        filteredConversations.element = CollectionsKt.toMutableList((Collection) conversations);
        listView.setAdapter((ListAdapter) r6);
        searchInput.addTextChangedListener(new TextWatcher() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$showAutoHideContactPicker$1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                T t;
                String obj = s != null ? s.toString() : null;
                if (obj == null) {
                    obj = "";
                }
                String lowerCase = obj.toLowerCase(Locale.ROOT);
                String str = "toLowerCase(...)";
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                Ref.ObjectRef<List<ConversationSummary>> objectRef = filteredConversations;
                if (StringsKt.isBlank(lowerCase)) {
                    t = CollectionsKt.toMutableList((Collection) conversations);
                } else {
                    List<ConversationSummary> list = conversations;
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj2 : list) {
                        ConversationSummary conversationSummary = (ConversationSummary) obj2;
                        String lowerCase2 = conversationSummary.getDisplayName().toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase2, str);
                        String str2 = str;
                        List<ConversationSummary> list2 = list;
                        if (StringsKt.contains$default((CharSequence) lowerCase2, (CharSequence) lowerCase, false, 2, (Object) null) || StringsKt.contains$default((CharSequence) conversationSummary.getAddress(), (CharSequence) lowerCase, false, 2, (Object) null)) {
                            arrayList2.add(obj2);
                        }
                        str = str2;
                        list = list2;
                    }
                    t = CollectionsKt.toMutableList((Collection) arrayList2);
                }
                objectRef.element = t;
                clear();
                SettingsActivity$showAutoHideContactPicker$adapter$1 settingsActivity$showAutoHideContactPicker$adapter$1 = r6;
                List<ConversationSummary> list3 = filteredConversations.element;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                Iterator<T> it2 = list3.iterator();
                while (it2.hasNext()) {
                    arrayList3.add(((ConversationSummary) it2.next()).getDisplayName());
                }
                settingsActivity$showAutoHideContactPicker$adapter$1.addAll(arrayList3);
                notifyDataSetChanged();
            }
        });
        final AlertDialog dialog = new AlertDialog.Builder(this).setTitle(R.string.auto_hide_timer_title).setView($this$showAutoHideContactPicker_u24lambda_u2414).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).create();
        Intrinsics.checkNotNullExpressionValue(dialog, "create(...)");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda12
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                SettingsActivity.showAutoHideContactPicker$lambda$16(AlertDialog.this, this, filteredConversations, adapterView, view, i, j);
            }
        });
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAutoHideContactPicker$lambda$16(AlertDialog dialog, SettingsActivity this$0, Ref.ObjectRef filteredConversations, AdapterView adapterView, View view, int position, long j) {
        Intrinsics.checkNotNullParameter(dialog, "$dialog");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(filteredConversations, "$filteredConversations");
        dialog.dismiss();
        this$0.showDailyHideTimePicker((ConversationSummary) ((List) filteredConversations.element).get(position));
    }

    private final void showDailyHideTimePicker(final ConversationSummary conversation) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda7
            @Override // android.app.TimePickerDialog.OnTimeSetListener
            public final void onTimeSet(TimePicker timePicker2, int i, int i2) {
                SettingsActivity.showDailyHideTimePicker$lambda$17(SettingsActivity.this, conversation, timePicker2, i, i2);
            }
        }, now.get(11), now.get(12), false);
        timePicker.setButton(-3, "Disable", new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.showDailyHideTimePicker$lambda$18(SettingsActivity.this, conversation, dialogInterface, i);
            }
        });
        timePicker.setTitle("Daily auto-hide time for " + conversation.getDisplayName());
        timePicker.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDailyHideTimePicker$lambda$17(SettingsActivity this$0, ConversationSummary conversation, TimePicker timePicker, int hour, int minute) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(conversation, "$conversation");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new SettingsActivity$showDailyHideTimePicker$timePicker$1$1(hour, minute, this$0, conversation, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDailyHideTimePicker$lambda$18(SettingsActivity this$0, ConversationSummary conversation, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(conversation, "$conversation");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new SettingsActivity$showDailyHideTimePicker$1$1(this$0, conversation, null), 3, null);
    }

    private final void setupGeneralSettingsRow() {
        findViewById(R.id.rowGeneralSettings).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupGeneralSettingsRow$lambda$19(SettingsActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupGeneralSettingsRow$lambda$19(SettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) GeneralSettingsActivity.class));
    }

    private final void setupTrashRow() {
        findViewById(R.id.rowTrash).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupTrashRow$lambda$20(SettingsActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupTrashRow$lambda$20(SettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) TrashActivity.class));
    }

    private final void setupBlockedNumbersRow() {
        findViewById(R.id.rowBlockedNumbers).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupBlockedNumbersRow$lambda$21(SettingsActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupBlockedNumbersRow$lambda$21(SettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) BlockedNumbersActivity.class));
    }

    private final void setupDisplayNameRow() {
        final SharedPreferences prefs = getSharedPreferences("origin_sms_app_prefs", 0);
        final TextView txtValue = (TextView) findViewById(R.id.txtDisplayNameValue);
        txtValue.setText(prefs.getString("display_name", getString(R.string.title_conversations)));
        findViewById(R.id.rowDisplayName).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.setupDisplayNameRow$lambda$26(SettingsActivity.this, prefs, txtValue, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupDisplayNameRow$lambda$26(final SettingsActivity this$0, final SharedPreferences $prefs, final TextView $txtValue, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        final EditText $this$setupDisplayNameRow_u24lambda_u2426_u24lambda_u2422 = new EditText(this$0);
        $this$setupDisplayNameRow_u24lambda_u2426_u24lambda_u2422.setHint(this$0.getString(R.string.settings_display_name_hint));
        $this$setupDisplayNameRow_u24lambda_u2426_u24lambda_u2422.setText($prefs.getString("display_name", ""));
        FrameLayout $this$setupDisplayNameRow_u24lambda_u2426_u24lambda_u2423 = new FrameLayout(this$0);
        int pad = (int) (16 * $this$setupDisplayNameRow_u24lambda_u2426_u24lambda_u2423.getResources().getDisplayMetrics().density);
        $this$setupDisplayNameRow_u24lambda_u2426_u24lambda_u2423.setPadding(pad, pad, pad, 0);
        $this$setupDisplayNameRow_u24lambda_u2426_u24lambda_u2423.addView($this$setupDisplayNameRow_u24lambda_u2426_u24lambda_u2422);
        new AlertDialog.Builder(this$0).setTitle(R.string.settings_display_name).setView($this$setupDisplayNameRow_u24lambda_u2426_u24lambda_u2423).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.SettingsActivity$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.setupDisplayNameRow$lambda$26$lambda$25($this$setupDisplayNameRow_u24lambda_u2426_u24lambda_u2422, $prefs, $txtValue, this$0, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupDisplayNameRow$lambda$26$lambda$25(EditText editText, SharedPreferences $prefs, TextView $txtValue, SettingsActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(editText, "$editText");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String name = StringsKt.trim((CharSequence) editText.getText().toString()).toString();
        $prefs.edit().putString("display_name", name).apply();
        String str = name;
        if (StringsKt.isBlank(str)) {
            String string = this$0.getString(R.string.title_conversations);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            str = string;
        }
        $txtValue.setText(str);
    }
}
