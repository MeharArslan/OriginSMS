package com.meharenterprises.originsms.lock;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.meharenterprises.originsms.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: LockSetupActivity.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\nH\u0002J\b\u0010\u0019\u001a\u00020\u0017H\u0002J\u0012\u0010\u001a\u001a\u00020\u00172\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0014J\u0010\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/meharenterprises/originsms/lock/LockSetupActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "alsoHide", "", "btnContinue", "Landroid/widget/Button;", "editPin", "Landroid/widget/EditText;", "firstEntry", "", "pinManager", "Lcom/meharenterprises/originsms/lock/PinManager;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "switchBiometric", "Landroid/widget/Switch;", "threadIdToLock", "", "txtError", "Landroid/widget/TextView;", "txtStepLabel", "finalizeSetup", "", "pin", "onContinueClicked", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "showError", "message", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class LockSetupActivity extends AppCompatActivity {
    public static final String EXTRA_ALSO_HIDE = "extra_also_hide";
    public static final String EXTRA_THREAD_ID_TO_LOCK = "extra_thread_id_to_lock";
    private boolean alsoHide;
    private Button btnContinue;
    private EditText editPin;
    private String firstEntry;
    private PinManager pinManager;
    private Switch switchBiometric;
    private TextView txtError;
    private TextView txtStepLabel;
    private long threadIdToLock = -1;
    private final CoroutineScope scope = CoroutineScopeKt.MainScope();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_setup);
        this.pinManager = new PinManager(this);
        this.threadIdToLock = getIntent().getLongExtra(EXTRA_THREAD_ID_TO_LOCK, -1L);
        this.alsoHide = getIntent().getBooleanExtra(EXTRA_ALSO_HIDE, false);
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.lock.LockSetupActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LockSetupActivity.onCreate$lambda$0(LockSetupActivity.this, view);
            }
        });
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("Chat Lock");
        }
        ActionBar supportActionBar2 = getSupportActionBar();
        if (supportActionBar2 != null) {
            supportActionBar2.setDisplayShowTitleEnabled(true);
        }
        View findViewById = findViewById(R.id.editPin);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.editPin = (EditText) findViewById;
        View findViewById2 = findViewById(R.id.txtStepLabel);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
        this.txtStepLabel = (TextView) findViewById2;
        View findViewById3 = findViewById(R.id.txtError);
        Intrinsics.checkNotNullExpressionValue(findViewById3, "findViewById(...)");
        this.txtError = (TextView) findViewById3;
        View findViewById4 = findViewById(R.id.btnContinue);
        Intrinsics.checkNotNullExpressionValue(findViewById4, "findViewById(...)");
        this.btnContinue = (Button) findViewById4;
        View findViewById5 = findViewById(R.id.switchBiometric);
        Intrinsics.checkNotNullExpressionValue(findViewById5, "findViewById(...)");
        this.switchBiometric = (Switch) findViewById5;
        boolean biometricAvailable = BiometricManager.from(this).canAuthenticate(15) == 0;
        Switch r3 = this.switchBiometric;
        Button button = null;
        if (r3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("switchBiometric");
            r3 = null;
        }
        r3.setEnabled(biometricAvailable);
        if (!biometricAvailable) {
            Switch r32 = this.switchBiometric;
            if (r32 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("switchBiometric");
                r32 = null;
            }
            r32.setChecked(false);
        }
        Button button2 = this.btnContinue;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnContinue");
        } else {
            button = button2;
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.lock.LockSetupActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LockSetupActivity.onCreate$lambda$1(LockSetupActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(LockSetupActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(LockSetupActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onContinueClicked();
    }

    private final void onContinueClicked() {
        EditText editText = this.editPin;
        EditText editText2 = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editPin");
            editText = null;
        }
        Editable text = editText.getText();
        String value = text != null ? text.toString() : null;
        if (value == null) {
            value = "";
        }
        TextView textView = this.txtError;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("txtError");
            textView = null;
        }
        textView.setVisibility(8);
        if (value.length() < 4) {
            String string = getString(R.string.lock_pin_too_short);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            showError(string);
            return;
        }
        if (this.firstEntry == null) {
            this.firstEntry = value;
            TextView textView2 = this.txtStepLabel;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("txtStepLabel");
                textView2 = null;
            }
            textView2.setText(getString(R.string.lock_confirm_pin));
            EditText editText3 = this.editPin;
            if (editText3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("editPin");
            } else {
                editText2 = editText3;
            }
            editText2.setText("");
            return;
        }
        if (Intrinsics.areEqual(value, this.firstEntry)) {
            finalizeSetup(value);
            return;
        }
        String string2 = getString(R.string.lock_pin_mismatch);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        showError(string2);
        this.firstEntry = null;
        TextView textView3 = this.txtStepLabel;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("txtStepLabel");
            textView3 = null;
        }
        textView3.setText(getString(R.string.lock_create_pin));
        EditText editText4 = this.editPin;
        if (editText4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editPin");
        } else {
            editText2 = editText4;
        }
        editText2.setText("");
    }

    private final void finalizeSetup(String pin) {
        PinManager pinManager = this.pinManager;
        if (pinManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pinManager");
            pinManager = null;
        }
        pinManager.setPin(pin);
        PinManager pinManager2 = this.pinManager;
        if (pinManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pinManager");
            pinManager2 = null;
        }
        Switch r1 = this.switchBiometric;
        if (r1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("switchBiometric");
            r1 = null;
        }
        pinManager2.setBiometricEnabled(r1.isChecked());
        if (this.threadIdToLock != -1) {
            BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new LockSetupActivity$finalizeSetup$1(this, null), 3, null);
        } else {
            finish();
        }
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
}
