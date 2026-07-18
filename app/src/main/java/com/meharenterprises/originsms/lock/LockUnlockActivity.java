package com.meharenterprises.originsms.lock;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ConversationSummary;
import com.meharenterprises.originsms.ui.thread.ThreadActivity;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;

/* compiled from: LockUnlockActivity.kt */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 /2\u00020\u0001:\u0001/B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0013H\u0002J\b\u0010\u001d\u001a\u00020\u001bH\u0002J\b\u0010\u001e\u001a\u00020\u001bH\u0002J\b\u0010\u001f\u001a\u00020\u001bH\u0002J\u0010\u0010 \u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020\u001bH\u0002J\u0012\u0010&\u001a\u00020\u001b2\b\u0010'\u001a\u0004\u0018\u00010(H\u0014J\u0010\u0010)\u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010*\u001a\u00020\u001bH\u0002J\u0010\u0010+\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020\u0004H\u0002J\u0010\u0010-\u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010.\u001a\u00020\u001bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lcom/meharenterprises/originsms/lock/LockUnlockActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "address", "", "btnUnlock", "Landroid/widget/Button;", "btnUseBiometric", "displayName", "editPin", "Landroid/widget/EditText;", "mode", "pinContainer", "Landroid/view/View;", "pinManager", "Lcom/meharenterprises/originsms/lock/PinManager;", "recyclerVault", "Landroidx/recyclerview/widget/RecyclerView;", "threadId", "", "txtDisplayName", "Landroid/widget/TextView;", "txtError", "txtPrompt", "txtVaultEmpty", "vaultContainer", "applyLockoutUi", "", "secondsRemaining", "attemptPinUnlock", "bindViews", "configureForMode", "confirmUnhide", "conversation", "Lcom/meharenterprises/originsms/core/ConversationSummary;", "isBiometricAvailable", "", "onAuthenticated", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openHiddenThread", "showBiometricPrompt", "showError", "message", "showScheduleUnhideDialog", "showVault", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class LockUnlockActivity extends AppCompatActivity {
    public static final String EXTRA_ADDRESS = "extra_address";
    public static final String EXTRA_DISPLAY_NAME = "extra_display_name";
    public static final String EXTRA_THREAD_ID = "extra_thread_id";
    public static final String EXTRA_UNLOCK_INTENT = "extra_unlock_intent";
    public static final String INTENT_OPEN_THREAD = "open_thread";
    public static final String INTENT_OPEN_VAULT = "open_vault";
    public static final String INTENT_REMOVE_LOCK = "remove_lock";
    private Button btnUnlock;
    private Button btnUseBiometric;
    private EditText editPin;
    private View pinContainer;
    private PinManager pinManager;
    private RecyclerView recyclerVault;
    private TextView txtDisplayName;
    private TextView txtError;
    private TextView txtPrompt;
    private TextView txtVaultEmpty;
    private View vaultContainer;
    private String mode = INTENT_OPEN_THREAD;
    private long threadId = -1;
    private String address = "";
    private String displayName = "";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_unlock);
        this.pinManager = new PinManager(this);
        String stringExtra = getIntent().getStringExtra(EXTRA_UNLOCK_INTENT);
        if (stringExtra == null) {
            stringExtra = INTENT_OPEN_THREAD;
        }
        this.mode = stringExtra;
        this.threadId = getIntent().getLongExtra("extra_thread_id", -1L);
        String stringExtra2 = getIntent().getStringExtra("extra_address");
        if (stringExtra2 == null) {
            stringExtra2 = "";
        }
        this.address = stringExtra2;
        String stringExtra3 = getIntent().getStringExtra("extra_display_name");
        String str = stringExtra3 != null ? stringExtra3 : "";
        if (StringsKt.isBlank(str)) {
            str = this.address;
        }
        this.displayName = str;
        bindViews();
        configureForMode();
        Button button = this.btnUnlock;
        PinManager pinManager = null;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnUnlock");
            button = null;
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.lock.LockUnlockActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LockUnlockActivity.onCreate$lambda$1(LockUnlockActivity.this, view);
            }
        });
        Button button2 = this.btnUseBiometric;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnUseBiometric");
            button2 = null;
        }
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.lock.LockUnlockActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LockUnlockActivity.onCreate$lambda$2(LockUnlockActivity.this, view);
            }
        });
        PinManager pinManager2 = this.pinManager;
        if (pinManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pinManager");
        } else {
            pinManager = pinManager2;
        }
        if (pinManager.isBiometricEnabled() && isBiometricAvailable()) {
            showBiometricPrompt();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(LockUnlockActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.attemptPinUnlock();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(LockUnlockActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showBiometricPrompt();
    }

    private final void bindViews() {
        View findViewById = findViewById(R.id.editPin);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.editPin = (EditText) findViewById;
        View findViewById2 = findViewById(R.id.txtError);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
        this.txtError = (TextView) findViewById2;
        View findViewById3 = findViewById(R.id.txtPrompt);
        Intrinsics.checkNotNullExpressionValue(findViewById3, "findViewById(...)");
        this.txtPrompt = (TextView) findViewById3;
        View findViewById4 = findViewById(R.id.txtDisplayName);
        Intrinsics.checkNotNullExpressionValue(findViewById4, "findViewById(...)");
        this.txtDisplayName = (TextView) findViewById4;
        View findViewById5 = findViewById(R.id.btnUnlock);
        Intrinsics.checkNotNullExpressionValue(findViewById5, "findViewById(...)");
        this.btnUnlock = (Button) findViewById5;
        View findViewById6 = findViewById(R.id.btnUseBiometric);
        Intrinsics.checkNotNullExpressionValue(findViewById6, "findViewById(...)");
        this.btnUseBiometric = (Button) findViewById6;
        View findViewById7 = findViewById(R.id.recyclerVault);
        Intrinsics.checkNotNullExpressionValue(findViewById7, "findViewById(...)");
        this.recyclerVault = (RecyclerView) findViewById7;
        View findViewById8 = findViewById(R.id.vaultContainer);
        Intrinsics.checkNotNullExpressionValue(findViewById8, "findViewById(...)");
        this.vaultContainer = findViewById8;
        View findViewById9 = findViewById(R.id.pinContainer);
        Intrinsics.checkNotNullExpressionValue(findViewById9, "findViewById(...)");
        this.pinContainer = findViewById9;
        View findViewById10 = findViewById(R.id.txtVaultEmpty);
        Intrinsics.checkNotNullExpressionValue(findViewById10, "findViewById(...)");
        this.txtVaultEmpty = (TextView) findViewById10;
        ((MaterialToolbar) findViewById(R.id.vaultToolbar)).setNavigationOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.lock.LockUnlockActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LockUnlockActivity.bindViews$lambda$3(LockUnlockActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindViews$lambda$3(LockUnlockActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void configureForMode() {
        String str = this.mode;
        PinManager pinManager = null;
        if (Intrinsics.areEqual(str, INTENT_REMOVE_LOCK)) {
            TextView textView = this.txtDisplayName;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("txtDisplayName");
                textView = null;
            }
            textView.setText(this.displayName);
            TextView textView2 = this.txtPrompt;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("txtPrompt");
                textView2 = null;
            }
            textView2.setText(getString(R.string.menu_unlock_chat));
        } else if (Intrinsics.areEqual(str, INTENT_OPEN_VAULT)) {
            TextView textView3 = this.txtDisplayName;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("txtDisplayName");
                textView3 = null;
            }
            textView3.setText(getString(R.string.section_hidden_chats));
            TextView textView4 = this.txtPrompt;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("txtPrompt");
                textView4 = null;
            }
            textView4.setText(getString(R.string.lock_enter_pin));
        } else {
            TextView textView5 = this.txtDisplayName;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("txtDisplayName");
                textView5 = null;
            }
            textView5.setText(this.displayName);
            TextView textView6 = this.txtPrompt;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("txtPrompt");
                textView6 = null;
            }
            textView6.setText(getString(R.string.lock_enter_pin));
        }
        Button button = this.btnUseBiometric;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnUseBiometric");
            button = null;
        }
        button.setVisibility(isBiometricAvailable() ? 0 : 8);
        PinManager pinManager2 = this.pinManager;
        if (pinManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pinManager");
        } else {
            pinManager = pinManager2;
        }
        long remainingLockout = pinManager.getLockoutSecondsRemaining();
        if (remainingLockout > 0) {
            applyLockoutUi(remainingLockout);
        }
    }

    private final boolean isBiometricAvailable() {
        return BiometricManager.from(this).canAuthenticate(15) == 0;
    }

    private final void attemptPinUnlock() {
        PinManager pinManager = this.pinManager;
        EditText editText = null;
        if (pinManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pinManager");
            pinManager = null;
        }
        long remainingLockout = pinManager.getLockoutSecondsRemaining();
        if (remainingLockout > 0) {
            applyLockoutUi(remainingLockout);
            return;
        }
        EditText editText2 = this.editPin;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editPin");
            editText2 = null;
        }
        Editable text = editText2.getText();
        String candidate = text != null ? text.toString() : null;
        if (candidate == null) {
            candidate = "";
        }
        if (StringsKt.isBlank(candidate)) {
            return;
        }
        PinManager pinManager2 = this.pinManager;
        if (pinManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pinManager");
            pinManager2 = null;
        }
        if (pinManager2.verifyPin(candidate)) {
            onAuthenticated();
            return;
        }
        PinManager pinManager3 = this.pinManager;
        if (pinManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pinManager");
            pinManager3 = null;
        }
        long newLockout = pinManager3.getLockoutSecondsRemaining();
        if (newLockout > 0) {
            applyLockoutUi(newLockout);
        } else {
            String string = getString(R.string.lock_wrong_pin);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            showError(string);
        }
        EditText editText3 = this.editPin;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editPin");
        } else {
            editText = editText3;
        }
        editText.setText("");
    }

    private final void applyLockoutUi(long secondsRemaining) {
        String string = getString(R.string.lock_too_many_attempts, new Object[]{Long.valueOf(secondsRemaining)});
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        showError(string);
        Button button = this.btnUnlock;
        EditText editText = null;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnUnlock");
            button = null;
        }
        button.setEnabled(false);
        Button button2 = this.btnUseBiometric;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnUseBiometric");
            button2 = null;
        }
        button2.setEnabled(false);
        EditText editText2 = this.editPin;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editPin");
        } else {
            editText = editText2;
        }
        editText.postDelayed(new Runnable() { // from class: com.meharenterprises.originsms.lock.LockUnlockActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LockUnlockActivity.applyLockoutUi$lambda$4(LockUnlockActivity.this);
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void applyLockoutUi$lambda$4(LockUnlockActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        PinManager pinManager = this$0.pinManager;
        TextView textView = null;
        if (pinManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pinManager");
            pinManager = null;
        }
        long stillRemaining = pinManager.getLockoutSecondsRemaining();
        if (stillRemaining <= 0) {
            Button button = this$0.btnUnlock;
            if (button == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnUnlock");
                button = null;
            }
            button.setEnabled(true);
            Button button2 = this$0.btnUseBiometric;
            if (button2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnUseBiometric");
                button2 = null;
            }
            button2.setEnabled(true);
            TextView textView2 = this$0.txtError;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("txtError");
            } else {
                textView = textView2;
            }
            textView.setVisibility(8);
            return;
        }
        this$0.applyLockoutUi(stillRemaining);
    }

    private final void showBiometricPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);
        Intrinsics.checkNotNullExpressionValue(executor, "getMainExecutor(...)");
        BiometricPrompt prompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() { // from class: com.meharenterprises.originsms.lock.LockUnlockActivity$showBiometricPrompt$callback$1
            @Override // androidx.biometric.BiometricPrompt.AuthenticationCallback
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                Intrinsics.checkNotNullParameter(result, "result");
                super.onAuthenticationSucceeded(result);
                LockUnlockActivity.this.onAuthenticated();
            }

            @Override // androidx.biometric.BiometricPrompt.AuthenticationCallback
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                Intrinsics.checkNotNullParameter(errString, "errString");
                super.onAuthenticationError(errorCode, errString);
            }
        });
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle(getString(R.string.lock_biometric_title)).setSubtitle(getString(R.string.lock_biometric_subtitle)).setNegativeButtonText(getString(android.R.string.cancel)).setAllowedAuthenticators(15).build();
        Intrinsics.checkNotNullExpressionValue(promptInfo, "build(...)");
        prompt.authenticate(promptInfo);
    }

    private final void showError(String message) {
        TextView textView = this.txtError;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("txtError");
            textView = null;
        }
        textView.setText(message);
        TextView textView3 = this.txtError;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("txtError");
        } else {
            textView2 = textView3;
        }
        textView2.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAuthenticated() {
        String str = this.mode;
        if (Intrinsics.areEqual(str, INTENT_REMOVE_LOCK)) {
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new LockUnlockActivity$onAuthenticated$1(this, null), 3, null);
            return;
        }
        if (Intrinsics.areEqual(str, INTENT_OPEN_VAULT)) {
            showVault();
            return;
        }
        Intent intent = new Intent(this, (Class<?>) ThreadActivity.class);
        intent.putExtra("extra_thread_id", this.threadId);
        intent.putExtra("extra_address", this.address);
        intent.putExtra("extra_display_name", this.displayName);
        startActivity(intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showVault() {
        View view = this.pinContainer;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pinContainer");
            view = null;
        }
        view.setVisibility(8);
        View view2 = this.vaultContainer;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vaultContainer");
            view2 = null;
        }
        view2.setVisibility(0);
        RecyclerView recyclerView = this.recyclerVault;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerVault");
            recyclerView = null;
        }
        recyclerView.setVisibility(8);
        TextView textView = this.txtVaultEmpty;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("txtVaultEmpty");
            textView = null;
        }
        textView.setVisibility(0);
        TextView textView2 = this.txtVaultEmpty;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("txtVaultEmpty");
            textView2 = null;
        }
        textView2.setText("Loading hidden chats...");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new LockUnlockActivity$showVault$1(this, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openHiddenThread(ConversationSummary conversation) {
        Intent intent = new Intent(this, (Class<?>) ThreadActivity.class);
        intent.putExtra("extra_thread_id", conversation.getThreadId());
        intent.putExtra("extra_address", conversation.getAddress());
        intent.putExtra("extra_display_name", conversation.getDisplayName());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void confirmUnhide(final ConversationSummary conversation) {
        String[] options = {getString(R.string.menu_unhide_chat), getString(R.string.schedule_auto_unhide)};
        new AlertDialog.Builder(this).setTitle(conversation.getDisplayName()).setItems(options, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.lock.LockUnlockActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LockUnlockActivity.confirmUnhide$lambda$7(LockUnlockActivity.this, conversation, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmUnhide$lambda$7(LockUnlockActivity this$0, ConversationSummary conversation, DialogInterface dialogInterface, int index) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(conversation, "$conversation");
        if (index == 0) {
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new LockUnlockActivity$confirmUnhide$1$1(this$0, conversation, null), 3, null);
        } else {
            this$0.showScheduleUnhideDialog(conversation);
        }
    }

    private final void showScheduleUnhideDialog(final ConversationSummary conversation) {
        String[] options = {getString(R.string.schedule_1_hour), getString(R.string.schedule_6_hours), getString(R.string.schedule_24_hours), getString(R.string.schedule_7_days)};
        final long[] hoursValues = {1, 6, 24, 168};
        new AlertDialog.Builder(this).setTitle(R.string.schedule_auto_unhide).setItems(options, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.lock.LockUnlockActivity$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LockUnlockActivity.showScheduleUnhideDialog$lambda$8(hoursValues, this, conversation, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showScheduleUnhideDialog$lambda$8(long[] hoursValues, LockUnlockActivity this$0, ConversationSummary conversation, DialogInterface dialogInterface, int index) {
        Intrinsics.checkNotNullParameter(hoursValues, "$hoursValues");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(conversation, "$conversation");
        long j = 60;
        long unhideAt = System.currentTimeMillis() + (hoursValues[index] * j * j * 1000);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new LockUnlockActivity$showScheduleUnhideDialog$1$1(this$0, conversation, unhideAt, null), 3, null);
    }
}
