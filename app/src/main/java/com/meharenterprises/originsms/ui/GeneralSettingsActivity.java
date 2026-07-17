package com.meharenterprises.originsms.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.meharenterprises.originsms.R;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* compiled from: GeneralSettingsActivity.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0012\u0010\r\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014J\b\u0010\u0010\u001a\u00020\u0006H\u0002J\b\u0010\u0011\u001a\u00020\u0006H\u0002J\b\u0010\u0012\u001a\u00020\u0006H\u0002J\b\u0010\u0013\u001a\u00020\u0006H\u0002J\b\u0010\u0014\u001a\u00020\u0006H\u0002J\b\u0010\u0015\u001a\u00020\u0006H\u0002J\b\u0010\u0016\u001a\u00020\u0006H\u0002J\b\u0010\u0017\u001a\u00020\u0006H\u0002J\u0016\u0010\u0018\u001a\u00020\u00062\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u001aH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/meharenterprises/originsms/ui/GeneralSettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "prefs", "Landroid/content/SharedPreferences;", "applyThemeToCard", "", "card", "Landroid/view/View;", "theme", "Lcom/meharenterprises/originsms/ui/ChatTheme;", "isSelected", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupAnimations", "setupBubbles", "setupFont", "setupKeyboardTheme", "setupPinchZoom", "setupSuggestions", "setupSwipeActions", "setupTheme", "showThemeCardGrid", "onChanged", "Lkotlin/Function0;", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class GeneralSettingsActivity extends AppCompatActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String[] FONTS = {"Default", "Serif (Elegant)", "Monospace (Code)", "Sans-Serif Condensed", "Cursive (Casual)"};
    private static final Typeface[] FONT_TYPEFACES = {Typeface.DEFAULT, Typeface.SERIF, Typeface.MONOSPACE, Typeface.create("sans-serif-condensed", 0), Typeface.create("cursive", 0)};
    public static final String KEY_ANIMATIONS = "animations_enabled";
    public static final String KEY_BUBBLES = "bubbles_enabled";
    public static final String KEY_FONT = "app_font";
    public static final String KEY_KEYBOARD_THEME = "keyboard_theme";
    public static final String KEY_PINCH_ZOOM = "pinch_zoom_enabled";
    public static final String KEY_SUGGESTIONS = "suggestions_enabled";
    public static final String KEY_SWIPE_LEFT = "swipe_left_action";
    public static final String KEY_SWIPE_RIGHT = "swipe_right_action";
    public static final String PREFS_NAME = "origin_sms_general_prefs";
    private SharedPreferences prefs;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings);
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "getSharedPreferences(...)");
        this.prefs = sharedPreferences;
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GeneralSettingsActivity.onCreate$lambda$0(GeneralSettingsActivity.this, view);
            }
        });
        setupBubbles();
        setupPinchZoom();
        setupAnimations();
        setupSuggestions();
        setupSwipeActions();
        setupTheme();
        setupFont();
        setupKeyboardTheme();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(GeneralSettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void setupBubbles() {
        Switch sw = (Switch) findViewById(R.id.switchBubbles);
        SharedPreferences sharedPreferences = this.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        sw.setChecked(sharedPreferences.getBoolean(KEY_BUBBLES, true));
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                GeneralSettingsActivity.setupBubbles$lambda$1(GeneralSettingsActivity.this, compoundButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupBubbles$lambda$1(GeneralSettingsActivity this$0, CompoundButton compoundButton, boolean c) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        SharedPreferences sharedPreferences = this$0.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putBoolean(KEY_BUBBLES, c).apply();
    }

    private final void setupPinchZoom() {
        Switch sw = (Switch) findViewById(R.id.switchPinchZoom);
        SharedPreferences sharedPreferences = this.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        sw.setChecked(sharedPreferences.getBoolean(KEY_PINCH_ZOOM, true));
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                GeneralSettingsActivity.setupPinchZoom$lambda$2(GeneralSettingsActivity.this, compoundButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupPinchZoom$lambda$2(GeneralSettingsActivity this$0, CompoundButton compoundButton, boolean c) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        SharedPreferences sharedPreferences = this$0.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putBoolean(KEY_PINCH_ZOOM, c).apply();
    }

    private final void setupAnimations() {
        Switch sw = (Switch) findViewById(R.id.switchAnimations);
        SharedPreferences sharedPreferences = this.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        sw.setChecked(sharedPreferences.getBoolean(KEY_ANIMATIONS, true));
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                GeneralSettingsActivity.setupAnimations$lambda$3(GeneralSettingsActivity.this, compoundButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupAnimations$lambda$3(GeneralSettingsActivity this$0, CompoundButton compoundButton, boolean c) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        SharedPreferences sharedPreferences = this$0.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putBoolean(KEY_ANIMATIONS, c).apply();
    }

    private final void setupSuggestions() {
        Switch sw = (Switch) findViewById(R.id.switchSuggestions);
        SharedPreferences sharedPreferences = this.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        sw.setChecked(sharedPreferences.getBoolean(KEY_SUGGESTIONS, true));
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda10
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                GeneralSettingsActivity.setupSuggestions$lambda$4(GeneralSettingsActivity.this, compoundButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSuggestions$lambda$4(GeneralSettingsActivity this$0, CompoundButton compoundButton, boolean c) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        SharedPreferences sharedPreferences = this$0.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putBoolean(KEY_SUGGESTIONS, c).apply();
    }

    private final void setupSwipeActions() {
        final TextView txtR = (TextView) findViewById(R.id.txtSwipeRightValue);
        final TextView txtL = (TextView) findViewById(R.id.txtSwipeLeftValue);
        final String[] opts = {"Archive", "Delete", "Mark as read", "None"};
        final Ref.IntRef curR = new Ref.IntRef();
        SharedPreferences sharedPreferences = this.prefs;
        SharedPreferences sharedPreferences2 = null;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        curR.element = sharedPreferences.getInt(KEY_SWIPE_RIGHT, 0);
        final Ref.IntRef curL = new Ref.IntRef();
        SharedPreferences sharedPreferences3 = this.prefs;
        if (sharedPreferences3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
        } else {
            sharedPreferences2 = sharedPreferences3;
        }
        curL.element = sharedPreferences2.getInt(KEY_SWIPE_LEFT, 1);
        txtR.setText(opts[curR.element]);
        txtL.setText(opts[curL.element]);
        findViewById(R.id.rowSwipeRight).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GeneralSettingsActivity.setupSwipeActions$lambda$6(GeneralSettingsActivity.this, opts, curR, txtR, view);
            }
        });
        findViewById(R.id.rowSwipeLeft).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GeneralSettingsActivity.setupSwipeActions$lambda$8(GeneralSettingsActivity.this, opts, curL, txtL, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSwipeActions$lambda$6(final GeneralSettingsActivity this$0, final String[] opts, final Ref.IntRef curR, final TextView $txtR, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(opts, "$opts");
        Intrinsics.checkNotNullParameter(curR, "$curR");
        new AlertDialog.Builder(this$0).setTitle("Swipe right").setSingleChoiceItems(opts, curR.element, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                GeneralSettingsActivity.setupSwipeActions$lambda$6$lambda$5(GeneralSettingsActivity.this, curR, $txtR, opts, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSwipeActions$lambda$6$lambda$5(GeneralSettingsActivity this$0, Ref.IntRef curR, TextView $txtR, String[] opts, DialogInterface d, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(curR, "$curR");
        Intrinsics.checkNotNullParameter(opts, "$opts");
        SharedPreferences sharedPreferences = this$0.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putInt(KEY_SWIPE_RIGHT, i).apply();
        curR.element = i;
        $txtR.setText(opts[i]);
        d.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSwipeActions$lambda$8(final GeneralSettingsActivity this$0, final String[] opts, final Ref.IntRef curL, final TextView $txtL, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(opts, "$opts");
        Intrinsics.checkNotNullParameter(curL, "$curL");
        new AlertDialog.Builder(this$0).setTitle("Swipe left").setSingleChoiceItems(opts, curL.element, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda13
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                GeneralSettingsActivity.setupSwipeActions$lambda$8$lambda$7(GeneralSettingsActivity.this, curL, $txtL, opts, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSwipeActions$lambda$8$lambda$7(GeneralSettingsActivity this$0, Ref.IntRef curL, TextView $txtL, String[] opts, DialogInterface d, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(curL, "$curL");
        Intrinsics.checkNotNullParameter(opts, "$opts");
        SharedPreferences sharedPreferences = this$0.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putInt(KEY_SWIPE_LEFT, i).apply();
        curL.element = i;
        $txtL.setText(opts[i]);
        d.dismiss();
    }

    private final void setupTheme() {
        final TextView txtVal = (TextView) findViewById(R.id.txtChatThemeValue);
        setupTheme$refresh(txtVal, this);
        findViewById(R.id.rowChatTheme).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GeneralSettingsActivity.setupTheme$lambda$9(GeneralSettingsActivity.this, txtVal, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupTheme$refresh(TextView txtVal, GeneralSettingsActivity this$0) {
        txtVal.setText(ChatThemeManager.INSTANCE.getThemes().get(ChatThemeManager.INSTANCE.getCurrentIndex(this$0)).getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupTheme$lambda$9(final GeneralSettingsActivity this$0, final TextView $txtVal, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showThemeCardGrid(new Function0<Unit>() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$setupTheme$1$1
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                GeneralSettingsActivity.setupTheme$refresh($txtVal, this$0);
            }
        });
    }

    private final void showThemeCardGrid(final Function0<Unit> onChanged) {
        List themes = ChatThemeManager.INSTANCE.getThemes();
        int currentIdx = ChatThemeManager.INSTANCE.getCurrentIndex(this);
        int dp = (int) getResources().getDisplayMetrics().density;
        GridLayout $this$showThemeCardGrid_u24lambda_u2410 = new GridLayout(this);
        $this$showThemeCardGrid_u24lambda_u2410.setColumnCount(2);
        $this$showThemeCardGrid_u24lambda_u2410.setPadding(dp * 8, dp * 8, dp * 8, dp * 8);
        AlertDialog.Builder title = new AlertDialog.Builder(this).setTitle("Choose Theme");
        ScrollView $this$showThemeCardGrid_u24lambda_u2411 = new ScrollView(this);
        $this$showThemeCardGrid_u24lambda_u2411.addView($this$showThemeCardGrid_u24lambda_u2410);
        ViewGroup viewGroup = null;
        final AlertDialog dialog = title.setView($this$showThemeCardGrid_u24lambda_u2411).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).create();
        Intrinsics.checkNotNullExpressionValue(dialog, "create(...)");
        List $this$forEachIndexed$iv = themes;
        int $i$f$forEachIndexed = 0;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            int index$iv2 = index$iv + 1;
            if (index$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            final ChatTheme theme = (ChatTheme) item$iv;
            final int i = index$iv;
            View card = LayoutInflater.from(this).inflate(R.layout.item_theme_card, viewGroup, false);
            GridLayout.LayoutParams $this$showThemeCardGrid_u24lambda_u2414_u24lambda_u2412 = new GridLayout.LayoutParams();
            $this$showThemeCardGrid_u24lambda_u2414_u24lambda_u2412.width = 0;
            List themes2 = themes;
            $this$showThemeCardGrid_u24lambda_u2414_u24lambda_u2412.columnSpec = GridLayout.spec(i % 2, 1.0f);
            Iterable $this$forEachIndexed$iv2 = $this$forEachIndexed$iv;
            int $i$f$forEachIndexed2 = $i$f$forEachIndexed;
            int $i$f$forEachIndexed3 = dp * 6;
            $this$showThemeCardGrid_u24lambda_u2414_u24lambda_u2412.setMargins(dp * 6, dp * 6, dp * 6, $i$f$forEachIndexed3);
            card.setLayoutParams($this$showThemeCardGrid_u24lambda_u2414_u24lambda_u2412);
            Intrinsics.checkNotNull(card);
            applyThemeToCard(card, theme, i == currentIdx);
            card.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GeneralSettingsActivity.showThemeCardGrid$lambda$14$lambda$13(GeneralSettingsActivity.this, i, dialog, onChanged, theme, view);
                }
            });
            $this$showThemeCardGrid_u24lambda_u2410.addView(card);
            index$iv = index$iv2;
            themes = themes2;
            $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
            $i$f$forEachIndexed = $i$f$forEachIndexed2;
            viewGroup = null;
        }
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showThemeCardGrid$lambda$14$lambda$13(GeneralSettingsActivity this$0, int $i, AlertDialog dialog, Function0 onChanged, ChatTheme theme, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(dialog, "$dialog");
        Intrinsics.checkNotNullParameter(onChanged, "$onChanged");
        Intrinsics.checkNotNullParameter(theme, "$theme");
        ChatThemeManager.INSTANCE.applyTheme(this$0, $i);
        dialog.dismiss();
        onChanged.invoke();
        Toast.makeText(this$0, "Theme: " + theme.getName(), 0).show();
    }

    private final void applyThemeToCard(View card, ChatTheme theme, boolean isSelected) {
        card.findViewById(R.id.cardBackground).setBackgroundColor(theme.getAppBackground());
        card.findViewById(R.id.miniToolbar).setBackgroundColor(theme.getAppBarBg());
        card.findViewById(R.id.miniInputBar).setBackgroundColor(theme.getAppBarBg());
        float cr = theme.getBubbleCornerRadius();
        TextView $this$applyThemeToCard_u24lambda_u2416 = (TextView) card.findViewById(R.id.bubbleSent);
        GradientDrawable $this$applyThemeToCard_u24lambda_u2416_u24lambda_u2415 = new GradientDrawable();
        $this$applyThemeToCard_u24lambda_u2416_u24lambda_u2415.setColor(theme.getOutgoingBubble());
        $this$applyThemeToCard_u24lambda_u2416_u24lambda_u2415.setCornerRadius(cr);
        $this$applyThemeToCard_u24lambda_u2416.setBackground($this$applyThemeToCard_u24lambda_u2416_u24lambda_u2415);
        $this$applyThemeToCard_u24lambda_u2416.setTextColor(theme.getOutgoingTextColor());
        TextView $this$applyThemeToCard_u24lambda_u2418 = (TextView) card.findViewById(R.id.bubbleReceived);
        GradientDrawable $this$applyThemeToCard_u24lambda_u2418_u24lambda_u2417 = new GradientDrawable();
        $this$applyThemeToCard_u24lambda_u2418_u24lambda_u2417.setColor(theme.getIncomingBubble());
        $this$applyThemeToCard_u24lambda_u2418_u24lambda_u2417.setCornerRadius(cr);
        $this$applyThemeToCard_u24lambda_u2418.setBackground($this$applyThemeToCard_u24lambda_u2418_u24lambda_u2417);
        $this$applyThemeToCard_u24lambda_u2418.setTextColor(theme.getIncomingTextColor());
        TextView $this$applyThemeToCard_u24lambda_u2420 = (TextView) card.findViewById(R.id.bubbleReceived2);
        GradientDrawable $this$applyThemeToCard_u24lambda_u2420_u24lambda_u2419 = new GradientDrawable();
        $this$applyThemeToCard_u24lambda_u2420_u24lambda_u2419.setColor(theme.getIncomingBubble());
        $this$applyThemeToCard_u24lambda_u2420_u24lambda_u2419.setCornerRadius(cr);
        $this$applyThemeToCard_u24lambda_u2420.setBackground($this$applyThemeToCard_u24lambda_u2420_u24lambda_u2419);
        $this$applyThemeToCard_u24lambda_u2420.setTextColor(theme.getIncomingTextColor());
        View findViewById = card.findViewById(R.id.miniSendBtn);
        GradientDrawable $this$applyThemeToCard_u24lambda_u2421 = new GradientDrawable();
        $this$applyThemeToCard_u24lambda_u2421.setShape(1);
        $this$applyThemeToCard_u24lambda_u2421.setColor(theme.getAccentColor());
        findViewById.setBackground($this$applyThemeToCard_u24lambda_u2421);
        TextView $this$applyThemeToCard_u24lambda_u2422 = (TextView) card.findViewById(R.id.txtThemeCardName);
        $this$applyThemeToCard_u24lambda_u2422.setText(theme.getName());
        $this$applyThemeToCard_u24lambda_u2422.setTextColor(isSelected ? theme.getAccentColor() : -7829368);
        if (isSelected) {
            $this$applyThemeToCard_u24lambda_u2422.setTypeface(null, 1);
        }
        card.findViewById(R.id.txtThemeSelected).setVisibility(isSelected ? 0 : 8);
        if (!isSelected) {
            card.setForeground(null);
            return;
        }
        GradientDrawable $this$applyThemeToCard_u24lambda_u2423 = new GradientDrawable();
        $this$applyThemeToCard_u24lambda_u2423.setColor(0);
        $this$applyThemeToCard_u24lambda_u2423.setStroke((int) (3 * getResources().getDisplayMetrics().density), theme.getAccentColor());
        $this$applyThemeToCard_u24lambda_u2423.setCornerRadius(14 * getResources().getDisplayMetrics().density);
        card.setForeground($this$applyThemeToCard_u24lambda_u2423);
    }

    private final void setupFont() {
        TextView txtCurrent = (TextView) findViewById(R.id.txtFontValue);
        SharedPreferences sharedPreferences = this.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        int currentIdx = sharedPreferences.getInt(KEY_FONT, 0);
        if (txtCurrent != null) {
            String[] strArr = FONTS;
            txtCurrent.setText((currentIdx < 0 || currentIdx > ArraysKt.getLastIndex(strArr)) ? "Default" : strArr[currentIdx]);
        }
        View findViewById = findViewById(R.id.rowFontStyle);
        if (findViewById != null) {
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GeneralSettingsActivity.setupFont$lambda$25(GeneralSettingsActivity.this, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFont$lambda$25(GeneralSettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) FontConverterActivity.class));
    }

    private final void setupKeyboardTheme() {
        final String[] themes = {"System default", "Light", "Dark"};
        final TextView txtVal = (TextView) findViewById(R.id.txtKeyboardThemeValue);
        SharedPreferences sharedPreferences = this.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        int current = sharedPreferences.getInt(KEY_KEYBOARD_THEME, 0);
        if (txtVal != null) {
            txtVal.setText(themes[current]);
        }
        View findViewById = findViewById(R.id.rowKeyboardTheme);
        if (findViewById != null) {
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda12
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GeneralSettingsActivity.setupKeyboardTheme$lambda$27(GeneralSettingsActivity.this, themes, txtVal, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupKeyboardTheme$lambda$27(final GeneralSettingsActivity this$0, final String[] themes, final TextView $txtVal, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(themes, "$themes");
        SharedPreferences sharedPreferences = this$0.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        int cur = sharedPreferences.getInt(KEY_KEYBOARD_THEME, 0);
        new AlertDialog.Builder(this$0).setTitle("Keyboard Theme").setSingleChoiceItems(themes, cur, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.GeneralSettingsActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                GeneralSettingsActivity.setupKeyboardTheme$lambda$27$lambda$26(GeneralSettingsActivity.this, $txtVal, themes, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupKeyboardTheme$lambda$27$lambda$26(GeneralSettingsActivity this$0, TextView $txtVal, String[] themes, DialogInterface dialog, int index) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(themes, "$themes");
        SharedPreferences sharedPreferences = this$0.prefs;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefs");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putInt(KEY_KEYBOARD_THEME, index).apply();
        if ($txtVal != null) {
            $txtVal.setText(themes[index]);
        }
        dialog.dismiss();
        Toast.makeText(this$0, "Keyboard theme: " + themes[index], 0).show();
    }

    /* compiled from: GeneralSettingsActivity.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aR\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R!\u0010\t\u001a\u0010\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n0\u0004¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/meharenterprises/originsms/ui/GeneralSettingsActivity$Companion;", "", "()V", "FONTS", "", "", "getFONTS", "()[Ljava/lang/String;", "[Ljava/lang/String;", "FONT_TYPEFACES", "Landroid/graphics/Typeface;", "kotlin.jvm.PlatformType", "getFONT_TYPEFACES", "()[Landroid/graphics/Typeface;", "[Landroid/graphics/Typeface;", "KEY_ANIMATIONS", "KEY_BUBBLES", "KEY_FONT", "KEY_KEYBOARD_THEME", "KEY_PINCH_ZOOM", "KEY_SUGGESTIONS", "KEY_SWIPE_LEFT", "KEY_SWIPE_RIGHT", "PREFS_NAME", "getSelectedFont", "context", "Landroid/content/Context;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String[] getFONTS() {
            return GeneralSettingsActivity.FONTS;
        }

        public final Typeface[] getFONT_TYPEFACES() {
            return GeneralSettingsActivity.FONT_TYPEFACES;
        }

        public final Typeface getSelectedFont(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            int idx = context.getSharedPreferences(GeneralSettingsActivity.PREFS_NAME, 0).getInt(GeneralSettingsActivity.KEY_FONT, 0);
            Typeface[] font_typefaces = getFONT_TYPEFACES();
            Typeface typeface = (idx < 0 || idx > ArraysKt.getLastIndex(font_typefaces)) ? Typeface.DEFAULT : font_typefaces[idx];
            Intrinsics.checkNotNullExpressionValue(typeface, "getOrElse(...)");
            return typeface;
        }
    }
}
