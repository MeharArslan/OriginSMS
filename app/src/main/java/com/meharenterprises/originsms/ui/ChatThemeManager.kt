package com.meharenterprises.originsms.ui

import android.content.Context
import android.graphics.Color
import com.meharenterprises.originsms.R

data class ChatTheme(
    val name: String,
    val description: String,
    val appBackground: Int,       // color
    val appBarBg: Int,            // color
    val incomingBubble: Int,      // color (fallback)
    val outgoingBubble: Int,      // color (fallback)
    val incomingTextColor: Int,
    val outgoingTextColor: Int,
    val textPrimary: Int,
    val textSecondary: Int,
    val accentColor: Int,
    val bubbleCornerRadius: Float,
    val statusBarColor: Int,
    val sentBubbleDrawable: Int,   // R.drawable.*
    val recvBubbleDrawable: Int,   // R.drawable.*
    val chatBackgroundDrawable: Int // R.drawable.*
)

object ChatThemeManager {

    val themes by lazy { buildThemes() }

    private fun buildThemes() = listOf(

        ChatTheme("Default (Original)", "App's original indigo & coral style",
            Color.parseColor("#F4F1FF"), Color.parseColor("#3B3486"),
            Color.parseColor("#6B64C4"), Color.parseColor("#5C55B3"),
            Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"),
            Color.parseColor("#FFFFFF"), Color.parseColor("#C8C4FF"),
            Color.parseColor("#FF6F59"), 18f, Color.parseColor("#262060"),
            R.drawable.bg_bubble_sent, R.drawable.bg_bubble_received,
            R.drawable.bg_chat_default),

        ChatTheme("Neon Grid (Cyber)", "Dark grid, neon pink & cyan",
            Color.parseColor("#0A0A1A"), Color.parseColor("#0D0D2B"),
            Color.parseColor("#1A0A3D"), Color.parseColor("#FF007F"),
            Color.parseColor("#00FFFF"), Color.parseColor("#FFFFFF"),
            Color.parseColor("#FFFFFF"), Color.parseColor("#00FFFF"),
            Color.parseColor("#FF007F"), 6f, Color.parseColor("#0D0D2B"),
            R.drawable.bubble_sent_neon_grid, R.drawable.bubble_recv_neon_grid,
            R.drawable.bg_chat_neon_grid),

        ChatTheme("Ancient Vellum", "Parchment beige, warm tones",
            Color.parseColor("#F5E6C8"), Color.parseColor("#C8A96E"),
            Color.parseColor("#EDD9A3"), Color.parseColor("#A0785A"),
            Color.parseColor("#3D2B1A"), Color.parseColor("#FFF8EC"),
            Color.parseColor("#3D2B1A"), Color.parseColor("#7A5C3A"),
            Color.parseColor("#C8862A"), 18f, Color.parseColor("#C8A96E"),
            R.drawable.bubble_sent_vellum, R.drawable.bubble_recv_vellum,
            R.drawable.bg_chat_vellum),

        ChatTheme("Constellation Night", "Deep space, star patterns",
            Color.parseColor("#050D1F"), Color.parseColor("#0A1535"),
            Color.parseColor("#0F2050"), Color.parseColor("#7B3FE4"),
            Color.parseColor("#B8D4FF"), Color.parseColor("#FFFFFF"),
            Color.parseColor("#E8F0FF"), Color.parseColor("#7B9FCC"),
            Color.parseColor("#7B3FE4"), 20f, Color.parseColor("#0A1535"),
            R.drawable.bubble_sent_constellation, R.drawable.bubble_recv_constellation,
            R.drawable.bg_chat_constellation),

        ChatTheme("Herbalist Garden", "Natural green, botanical",
            Color.parseColor("#E8F5E0"), Color.parseColor("#4A7C3F"),
            Color.parseColor("#C8E6B8"), Color.parseColor("#2E6B28"),
            Color.parseColor("#1A3D15"), Color.parseColor("#E8FFE0"),
            Color.parseColor("#1A3D15"), Color.parseColor("#4A7C3F"),
            Color.parseColor("#5DAE4C"), 24f, Color.parseColor("#4A7C3F"),
            R.drawable.bubble_sent_herbalist, R.drawable.bubble_recv_herbalist,
            R.drawable.bg_chat_herbalist),

        ChatTheme("8-Bit Adventure", "Pixel art, retro game style",
            Color.parseColor("#001830"), Color.parseColor("#003060"),
            Color.parseColor("#004080"), Color.parseColor("#00C060"),
            Color.parseColor("#00FF80"), Color.parseColor("#001830"),
            Color.parseColor("#00FF80"), Color.parseColor("#0080FF"),
            Color.parseColor("#FF6000"), 2f, Color.parseColor("#003060"),
            R.drawable.bubble_sent_8bit, R.drawable.bubble_recv_8bit,
            R.drawable.bg_chat_8bit),

        ChatTheme("Deep Ocean", "Deep blue gradient, modern pro",
            Color.parseColor("#0A1628"), Color.parseColor("#1565C0"),
            Color.parseColor("#1A3A6B"), Color.parseColor("#0D47A1"),
            Color.parseColor("#B3D9FF"), Color.parseColor("#FFFFFF"),
            Color.parseColor("#E3F2FD"), Color.parseColor("#82B1FF"),
            Color.parseColor("#40C4FF"), 20f, Color.parseColor("#1565C0"),
            R.drawable.bubble_sent_ocean, R.drawable.bubble_recv_ocean,
            R.drawable.bg_chat_ocean),

        // Rose Gold Premium — wow factor theme
        ChatTheme("Rose Gold Premium ✨", "Luxurious rose gold gradient",
            Color.parseColor("#1A0E0E"),
            Color.parseColor("#3D1A1A"),
            Color.parseColor("#2D1515"),
            Color.parseColor("#8B2252"),
            Color.parseColor("#FFD4E8"),
            Color.parseColor("#FFFFFF"),
            Color.parseColor("#FFE8F0"),
            Color.parseColor("#C4637A"),
            Color.parseColor("#E8507A"),
            22f,
            Color.parseColor("#3D1A1A"),
            R.drawable.bubble_sent_rosegold,
            R.drawable.bubble_recv_rosegold,
            R.drawable.bg_chat_rosegold),

        ChatTheme("Street Mural", "Urban graffiti, bold colors",
            Color.parseColor("#1A1A1A"), Color.parseColor("#2D2D2D"),
            Color.parseColor("#2A2A2A"), Color.parseColor("#E83030"),
            Color.parseColor("#FFE040"), Color.parseColor("#FFFFFF"),
            Color.parseColor("#FFFFFF"), Color.parseColor("#FFE040"),
            Color.parseColor("#E83030"), 12f, Color.parseColor("#2D2D2D"),
            R.drawable.bubble_sent_street, R.drawable.bubble_recv_street,
            R.drawable.bg_chat_street),

        ChatTheme("Inkwell Dark", "Deep black, orange accent",
            Color.parseColor("#111111"), Color.parseColor("#1A1A1A"),
            Color.parseColor("#222222"), Color.parseColor("#E86820"),
            Color.parseColor("#CCCCCC"), Color.parseColor("#FFFFFF"),
            Color.parseColor("#EEEEEE"), Color.parseColor("#888888"),
            Color.parseColor("#E86820"), 18f, Color.parseColor("#1A1A1A"),
            R.drawable.bubble_sent_inkwell, R.drawable.bubble_recv_inkwell,
            R.drawable.bg_chat_inkwell),

        ChatTheme("Sand & Gold", "Desert sand, golden tones",
            Color.parseColor("#FDF3DC"), Color.parseColor("#C8922A"),
            Color.parseColor("#F0D898"), Color.parseColor("#C07828"),
            Color.parseColor("#4A3010"), Color.parseColor("#FFF8E0"),
            Color.parseColor("#3D2A08"), Color.parseColor("#8A6020"),
            Color.parseColor("#E8A020"), 20f, Color.parseColor("#C8922A"),
            R.drawable.bubble_sent_sand, R.drawable.bubble_recv_sand,
            R.drawable.bg_chat_sand),

        ChatTheme("Aquarium", "Ocean blue, underwater feel",
            Color.parseColor("#0A2040"), Color.parseColor("#0D2D5A"),
            Color.parseColor("#0F3D6A"), Color.parseColor("#1A7DC0"),
            Color.parseColor("#A8E0FF"), Color.parseColor("#FFFFFF"),
            Color.parseColor("#C8EEFF"), Color.parseColor("#6AB8E0"),
            Color.parseColor("#00BFFF"), 22f, Color.parseColor("#0D2D5A"),
            R.drawable.bubble_sent_aquarium, R.drawable.bubble_recv_aquarium,
            R.drawable.bg_chat_aquarium),

        // WhatsApp style
        ChatTheme("WhatsApp", "Classic green, familiar chat UI",
            android.graphics.Color.parseColor("#ECE5DD"),
            android.graphics.Color.parseColor("#075E54"),
            android.graphics.Color.parseColor("#FFFFFF"),
            android.graphics.Color.parseColor("#DCF8C6"),
            android.graphics.Color.parseColor("#111111"),
            android.graphics.Color.parseColor("#111111"),
            android.graphics.Color.parseColor("#111111"),
            android.graphics.Color.parseColor("#667781"),
            android.graphics.Color.parseColor("#25D366"),
            18f,
            android.graphics.Color.parseColor("#075E54"),
            R.drawable.bubble_sent_whatsapp,
            R.drawable.bubble_recv_whatsapp,
            R.drawable.bg_chat_whatsapp),

        // Hacker/Matrix style
        ChatTheme("Hacker Terminal", "Matrix green, code feel",
            android.graphics.Color.parseColor("#000000"),
            android.graphics.Color.parseColor("#001100"),
            android.graphics.Color.parseColor("#001800"),
            android.graphics.Color.parseColor("#003300"),
            android.graphics.Color.parseColor("#00FF41"),
            android.graphics.Color.parseColor("#00AA2A"),
            android.graphics.Color.parseColor("#00FF41"),
            android.graphics.Color.parseColor("#007A1A"),
            android.graphics.Color.parseColor("#00FF41"),
            2f,
            android.graphics.Color.parseColor("#001100"),
            R.drawable.bubble_sent_hacker,
            R.drawable.bubble_recv_hacker,
            R.drawable.bg_chat_hacker),

        // Midnight Aurora — Premium gradient theme
        ChatTheme("Midnight Aurora ✨", "Premium deep space gradient",
            android.graphics.Color.parseColor("#0D0D1A"),  // appBackground
            android.graphics.Color.parseColor("#1A0A2E"),  // appBarBg
            android.graphics.Color.parseColor("#1E1040"),  // incomingBubble
            android.graphics.Color.parseColor("#2D1B69"),  // outgoingBubble
            android.graphics.Color.parseColor("#E8E0FF"),  // incomingText - light lavender
            android.graphics.Color.parseColor("#FFFFFF"),  // outgoingText - white
            android.graphics.Color.parseColor("#F0EEFF"),  // textPrimary - bright (was dark!)
            android.graphics.Color.parseColor("#9D8FD4"),  // textSecondary
            android.graphics.Color.parseColor("#C77DFF"),  // accentColor
            24f,
            android.graphics.Color.parseColor("#1A0A2E"),  // statusBarColor
            R.drawable.bubble_sent_aurora,
            R.drawable.bubble_recv_aurora,
            R.drawable.bg_chat_aurora),

        ChatTheme("Onyx & Opal Marble", "Black marble, gold veins",
            Color.parseColor("#0F0F0F"), Color.parseColor("#1A1A1A"),
            Color.parseColor("#1E1E1E"), Color.parseColor("#8B1A1A"),
            Color.parseColor("#D4AF37"), Color.parseColor("#FFE8C0"),
            Color.parseColor("#E8E8E8"), Color.parseColor("#D4AF37"),
            Color.parseColor("#D4AF37"), 16f, Color.parseColor("#1A1A1A"),
            R.drawable.bubble_sent_marble, R.drawable.bubble_recv_marble,
            R.drawable.bg_chat_marble)
    )

    fun applyTheme(context: Context, index: Int) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putInt(KEY_THEME, index).apply()
    }

    fun getCurrentTheme(context: Context): ChatTheme {
        val idx = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getInt(KEY_THEME, 0)
        return themes.getOrElse(idx) { themes[0] }
    }

    fun getCurrentIndex(context: Context): Int =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getInt(KEY_THEME, 0)

    fun isBubblesEnabled(context: Context) =
        context.getSharedPreferences(GeneralSettingsActivity.PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(GeneralSettingsActivity.KEY_BUBBLES, true)

    fun isPinchZoomEnabled(context: Context) =
        context.getSharedPreferences(GeneralSettingsActivity.PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(GeneralSettingsActivity.KEY_PINCH_ZOOM, true)

    fun isAnimationsEnabled(context: Context) =
        context.getSharedPreferences(GeneralSettingsActivity.PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(GeneralSettingsActivity.KEY_ANIMATIONS, true)

    const val PREFS = "origin_sms_theme_runtime"
    const val KEY_THEME = "selected_theme"
}
