package com.meharenterprises.originsms.ui;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ThemePreferenceManager.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u000f2\u00020\u0001:\u0002\u000f\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0006\u0010\f\u001a\u00020\tJ\u0006\u0010\r\u001a\u00020\u000bJ\u000e\u0010\u000e\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/meharenterprises/originsms/ui/ThemePreferenceManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "applyMode", "", "mode", "Lcom/meharenterprises/originsms/ui/ThemePreferenceManager$ThemeMode;", "applyStoredMode", "getCurrentMode", "setMode", "Companion", "ThemeMode", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class ThemePreferenceManager {
    private static final String KEY_THEME_MODE = "theme_mode";
    private static final String PREFS_NAME = "origin_sms_theme_prefs";
    private final SharedPreferences prefs;

    /* compiled from: ThemePreferenceManager.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lcom/meharenterprises/originsms/ui/ThemePreferenceManager$ThemeMode;", "", "(Ljava/lang/String;I)V", "LIGHT", "DARK", "SYSTEM", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public enum ThemeMode {
        LIGHT,
        DARK,
        SYSTEM;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

        public static EnumEntries<ThemeMode> getEntries() {
            return $ENTRIES;
        }
    }

    /* compiled from: ThemePreferenceManager.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ThemeMode.values().length];
            try {
                iArr[ThemeMode.LIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[ThemeMode.DARK.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[ThemeMode.SYSTEM.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ThemePreferenceManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.prefs = context.getSharedPreferences(PREFS_NAME, 0);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:4:0x0010. Please report as an issue. */
    public final ThemeMode getCurrentMode() {
        String string = this.prefs.getString(KEY_THEME_MODE, "SYSTEM");
        if (string != null) {
            switch (string.hashCode()) {
                case 2090870:
                    if (string.equals("DARK")) {
                        return ThemeMode.DARK;
                    }
                    break;
                case 72432886:
                    if (string.equals("LIGHT")) {
                        return ThemeMode.LIGHT;
                    }
                    break;
            }
        }
        return ThemeMode.SYSTEM;
    }

    public final void setMode(ThemeMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.prefs.edit().putString(KEY_THEME_MODE, mode.name()).apply();
        applyMode(mode);
    }

    public final void applyStoredMode() {
        applyMode(getCurrentMode());
    }

    private final void applyMode(ThemeMode mode) {
        int nightMode;
        switch (WhenMappings.$EnumSwitchMapping$0[mode.ordinal()]) {
            case 1:
                nightMode = 1;
                break;
            case 2:
                nightMode = 2;
                break;
            case 3:
                nightMode = -1;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }
}
