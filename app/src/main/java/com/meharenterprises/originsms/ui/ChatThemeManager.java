package com.meharenterprises.originsms.ui;

import android.content.Context;
import android.graphics.Color;
import com.meharenterprises.originsms.R;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChatThemeManager.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0002J\u000e\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0015\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R!\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n¨\u0006\u001a"}, d2 = {"Lcom/meharenterprises/originsms/ui/ChatThemeManager;", "", "()V", "KEY_THEME", "", "PREFS", "themes", "", "Lcom/meharenterprises/originsms/ui/ChatTheme;", "getThemes", "()Ljava/util/List;", "themes$delegate", "Lkotlin/Lazy;", "applyTheme", "", "context", "Landroid/content/Context;", "index", "", "buildThemes", "getCurrentIndex", "getCurrentTheme", "isAnimationsEnabled", "", "isBubblesEnabled", "isPinchZoomEnabled", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ChatThemeManager {
    public static final String KEY_THEME = "selected_theme";
    public static final String PREFS = "origin_sms_theme_runtime";
    public static final ChatThemeManager INSTANCE = new ChatThemeManager();

    /* renamed from: themes$delegate, reason: from kotlin metadata */
    private static final Lazy themes = LazyKt.lazy(new Function0<List<? extends ChatTheme>>() { // from class: com.meharenterprises.originsms.ui.ChatThemeManager$themes$2
        @Override // kotlin.jvm.functions.Function0
        public final List<? extends ChatTheme> invoke() {
            List<? extends ChatTheme> buildThemes;
            buildThemes = ChatThemeManager.INSTANCE.buildThemes();
            return buildThemes;
        }
    });

    private ChatThemeManager() {
    }

    public final List<ChatTheme> getThemes() {
        return (List) themes.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<ChatTheme> buildThemes() {
        return CollectionsKt.listOf((Object[]) new ChatTheme[]{new ChatTheme("Default (Original)", "App's original indigo & coral style", Color.parseColor("#F4F1FF"), Color.parseColor("#3B3486"), Color.parseColor("#6B64C4"), Color.parseColor("#5C55B3"), Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"), Color.parseColor("#C8C4FF"), Color.parseColor("#FF6F59"), 18.0f, Color.parseColor("#262060"), R.drawable.bg_bubble_sent, R.drawable.bg_bubble_received, R.drawable.bg_chat_default), new ChatTheme("Neon Grid (Cyber)", "Dark grid, neon pink & cyan", Color.parseColor("#0A0A1A"), Color.parseColor("#0D0D2B"), Color.parseColor("#1A0A3D"), Color.parseColor("#FF007F"), Color.parseColor("#00FFFF"), Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"), Color.parseColor("#00FFFF"), Color.parseColor("#FF007F"), 6.0f, Color.parseColor("#0D0D2B"), R.drawable.bubble_sent_neon_grid, R.drawable.bubble_recv_neon_grid, R.drawable.bg_chat_neon_grid), new ChatTheme("Ancient Vellum", "Parchment beige, warm tones", Color.parseColor("#F5E6C8"), Color.parseColor("#C8A96E"), Color.parseColor("#EDD9A3"), Color.parseColor("#A0785A"), Color.parseColor("#3D2B1A"), Color.parseColor("#FFF8EC"), Color.parseColor("#3D2B1A"), Color.parseColor("#7A5C3A"), Color.parseColor("#C8862A"), 18.0f, Color.parseColor("#C8A96E"), R.drawable.bubble_sent_vellum, R.drawable.bubble_recv_vellum, R.drawable.bg_chat_vellum), new ChatTheme("Constellation Night", "Deep space, star patterns", Color.parseColor("#050D1F"), Color.parseColor("#0A1535"), Color.parseColor("#0F2050"), Color.parseColor("#7B3FE4"), Color.parseColor("#B8D4FF"), Color.parseColor("#FFFFFF"), Color.parseColor("#E8F0FF"), Color.parseColor("#7B9FCC"), Color.parseColor("#7B3FE4"), 20.0f, Color.parseColor("#0A1535"), R.drawable.bubble_sent_constellation, R.drawable.bubble_recv_constellation, R.drawable.bg_chat_constellation), new ChatTheme("Herbalist Garden", "Natural green, botanical", Color.parseColor("#E8F5E0"), Color.parseColor("#4A7C3F"), Color.parseColor("#C8E6B8"), Color.parseColor("#2E6B28"), Color.parseColor("#1A3D15"), Color.parseColor("#E8FFE0"), Color.parseColor("#1A3D15"), Color.parseColor("#4A7C3F"), Color.parseColor("#5DAE4C"), 24.0f, Color.parseColor("#4A7C3F"), R.drawable.bubble_sent_herbalist, R.drawable.bubble_recv_herbalist, R.drawable.bg_chat_herbalist), new ChatTheme("8-Bit Adventure", "Pixel art, retro game style", Color.parseColor("#001830"), Color.parseColor("#003060"), Color.parseColor("#004080"), Color.parseColor("#00C060"), Color.parseColor("#00FF80"), Color.parseColor("#001830"), Color.parseColor("#00FF80"), Color.parseColor("#0080FF"), Color.parseColor("#FF6000"), 2.0f, Color.parseColor("#003060"), R.drawable.bubble_sent_8bit, R.drawable.bubble_recv_8bit, R.drawable.bg_chat_8bit), new ChatTheme("Deep Ocean", "Deep blue gradient, modern pro", Color.parseColor("#0A1628"), Color.parseColor("#1565C0"), Color.parseColor("#1A3A6B"), Color.parseColor("#0D47A1"), Color.parseColor("#B3D9FF"), Color.parseColor("#FFFFFF"), Color.parseColor("#E3F2FD"), Color.parseColor("#82B1FF"), Color.parseColor("#40C4FF"), 20.0f, Color.parseColor("#1565C0"), R.drawable.bubble_sent_ocean, R.drawable.bubble_recv_ocean, R.drawable.bg_chat_ocean), new ChatTheme("Rose Gold Premium ✨", "Luxurious rose gold gradient", Color.parseColor("#1A0E0E"), Color.parseColor("#3D1A1A"), Color.parseColor("#2D1515"), Color.parseColor("#8B2252"), Color.parseColor("#FFD4E8"), Color.parseColor("#FFFFFF"), Color.parseColor("#FFE8F0"), Color.parseColor("#C4637A"), Color.parseColor("#E8507A"), 22.0f, Color.parseColor("#3D1A1A"), R.drawable.bubble_sent_rosegold, R.drawable.bubble_recv_rosegold, R.drawable.bg_chat_rosegold), new ChatTheme("Street Mural", "Urban graffiti, bold colors", Color.parseColor("#1A1A1A"), Color.parseColor("#2D2D2D"), Color.parseColor("#2A2A2A"), Color.parseColor("#E83030"), Color.parseColor("#FFE040"), Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"), Color.parseColor("#FFE040"), Color.parseColor("#E83030"), 12.0f, Color.parseColor("#2D2D2D"), R.drawable.bubble_sent_street, R.drawable.bubble_recv_street, R.drawable.bg_chat_street), new ChatTheme("Inkwell Dark", "Deep black, orange accent", Color.parseColor("#111111"), Color.parseColor("#1A1A1A"), Color.parseColor("#222222"), Color.parseColor("#E86820"), Color.parseColor("#CCCCCC"), Color.parseColor("#FFFFFF"), Color.parseColor("#EEEEEE"), Color.parseColor("#888888"), Color.parseColor("#E86820"), 18.0f, Color.parseColor("#1A1A1A"), R.drawable.bubble_sent_inkwell, R.drawable.bubble_recv_inkwell, R.drawable.bg_chat_inkwell), new ChatTheme("Sand & Gold", "Desert sand, golden tones", Color.parseColor("#FDF3DC"), Color.parseColor("#C8922A"), Color.parseColor("#F0D898"), Color.parseColor("#C07828"), Color.parseColor("#4A3010"), Color.parseColor("#FFF8E0"), Color.parseColor("#3D2A08"), Color.parseColor("#8A6020"), Color.parseColor("#E8A020"), 20.0f, Color.parseColor("#C8922A"), R.drawable.bubble_sent_sand, R.drawable.bubble_recv_sand, R.drawable.bg_chat_sand), new ChatTheme("Aquarium", "Ocean blue, underwater feel", Color.parseColor("#0A2040"), Color.parseColor("#0D2D5A"), Color.parseColor("#0F3D6A"), Color.parseColor("#1A7DC0"), Color.parseColor("#A8E0FF"), Color.parseColor("#FFFFFF"), Color.parseColor("#C8EEFF"), Color.parseColor("#6AB8E0"), Color.parseColor("#00BFFF"), 22.0f, Color.parseColor("#0D2D5A"), R.drawable.bubble_sent_aquarium, R.drawable.bubble_recv_aquarium, R.drawable.bg_chat_aquarium), new ChatTheme("WhatsApp", "Classic green, familiar chat UI", Color.parseColor("#ECE5DD"), Color.parseColor("#075E54"), Color.parseColor("#FFFFFF"), Color.parseColor("#DCF8C6"), Color.parseColor("#111111"), Color.parseColor("#111111"), Color.parseColor("#111111"), Color.parseColor("#667781"), Color.parseColor("#25D366"), 18.0f, Color.parseColor("#075E54"), R.drawable.bubble_sent_whatsapp, R.drawable.bubble_recv_whatsapp, R.drawable.bg_chat_whatsapp), new ChatTheme("Hacker Terminal", "Matrix green, code feel", Color.parseColor("#000000"), Color.parseColor("#001100"), Color.parseColor("#001800"), Color.parseColor("#003300"), Color.parseColor("#00FF41"), Color.parseColor("#00AA2A"), Color.parseColor("#00FF41"), Color.parseColor("#007A1A"), Color.parseColor("#00FF41"), 2.0f, Color.parseColor("#001100"), R.drawable.bubble_sent_hacker, R.drawable.bubble_recv_hacker, R.drawable.bg_chat_hacker), new ChatTheme("Midnight Aurora ✨", "Premium deep space gradient", Color.parseColor("#0D0D1A"), Color.parseColor("#1A0A2E"), Color.parseColor("#1E1040"), Color.parseColor("#2D1B69"), Color.parseColor("#E8E0FF"), Color.parseColor("#FFFFFF"), Color.parseColor("#F0EEFF"), Color.parseColor("#9D8FD4"), Color.parseColor("#C77DFF"), 24.0f, Color.parseColor("#1A0A2E"), R.drawable.bubble_sent_aurora, R.drawable.bubble_recv_aurora, R.drawable.bg_chat_aurora), new ChatTheme("Onyx & Opal Marble", "Black marble, gold veins", Color.parseColor("#0F0F0F"), Color.parseColor("#1A1A1A"), Color.parseColor("#1E1E1E"), Color.parseColor("#8B1A1A"), Color.parseColor("#D4AF37"), Color.parseColor("#FFE8C0"), Color.parseColor("#E8E8E8"), Color.parseColor("#D4AF37"), Color.parseColor("#D4AF37"), 16.0f, Color.parseColor("#1A1A1A"), R.drawable.bubble_sent_marble, R.drawable.bubble_recv_marble, R.drawable.bg_chat_marble)});
    }

    public final void applyTheme(Context context, int index) {
        Intrinsics.checkNotNullParameter(context, "context");
        context.getSharedPreferences(PREFS, 0).edit().putInt(KEY_THEME, index).apply();
    }

    public final ChatTheme getCurrentTheme(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int idx = context.getSharedPreferences(PREFS, 0).getInt(KEY_THEME, 0);
        List<ChatTheme> themes2 = getThemes();
        return (idx < 0 || idx > CollectionsKt.getLastIndex(themes2)) ? INSTANCE.getThemes().get(0) : themes2.get(idx);
    }

    public final int getCurrentIndex(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getSharedPreferences(PREFS, 0).getInt(KEY_THEME, 0);
    }

    public final boolean isBubblesEnabled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getSharedPreferences(GeneralSettingsActivity.PREFS_NAME, 0).getBoolean(GeneralSettingsActivity.KEY_BUBBLES, true);
    }

    public final boolean isPinchZoomEnabled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getSharedPreferences(GeneralSettingsActivity.PREFS_NAME, 0).getBoolean(GeneralSettingsActivity.KEY_PINCH_ZOOM, true);
    }

    public final boolean isAnimationsEnabled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getSharedPreferences(GeneralSettingsActivity.PREFS_NAME, 0).getBoolean(GeneralSettingsActivity.KEY_ANIMATIONS, true);
    }
}
