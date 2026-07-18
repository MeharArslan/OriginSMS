package com.meharenterprises.originsms.ui

import android.content.Context
import android.graphics.Color

data class ChatTheme(
    val name: String,
    val description: String,
    val appBackground: Int,
    val appBarBg: Int,
    val incomingBubble: Int,
    val outgoingBubble: Int,
    val incomingTextColor: Int,
    val outgoingTextColor: Int,
    val textPrimary: Int,
    val textSecondary: Int,
    val accentColor: Int,
    val bubbleCornerRadius: Float,
    val statusBarColor: Int
)

object ChatThemeManager {

    val themes = listOf(

        // 1. Neon Grid (Cyber)
        ChatTheme("Neon Grid (Cyber)", "Dark grid, neon pink & cyan",
            appBackground     = Color.parseColor("#0A0A1A"),
            appBarBg          = Color.parseColor("#0D0D2B"),
            incomingBubble    = Color.parseColor("#1A0A3D"),
            outgoingBubble    = Color.parseColor("#FF007F"),
            incomingTextColor = Color.parseColor("#00FFFF"),
            outgoingTextColor = Color.parseColor("#FFFFFF"),
            textPrimary       = Color.parseColor("#FFFFFF"),
            textSecondary     = Color.parseColor("#00FFFF"),
            accentColor       = Color.parseColor("#FF007F"),
            bubbleCornerRadius = 10f,
            statusBarColor    = Color.parseColor("#0D0D2B")),

        // 2. Ancient Vellum
        ChatTheme("Ancient Vellum", "Parchment beige, warm tones",
            appBackground     = Color.parseColor("#F5E6C8"),
            appBarBg          = Color.parseColor("#C8A96E"),
            incomingBubble    = Color.parseColor("#EDD9A3"),
            outgoingBubble    = Color.parseColor("#A0785A"),
            incomingTextColor = Color.parseColor("#3D2B1A"),
            outgoingTextColor = Color.parseColor("#FFF8EC"),
            textPrimary       = Color.parseColor("#3D2B1A"),
            textSecondary     = Color.parseColor("#7A5C3A"),
            accentColor       = Color.parseColor("#C8862A"),
            bubbleCornerRadius = 14f,
            statusBarColor    = Color.parseColor("#C8A96E")),

        // 3. Constellation Night
        ChatTheme("Constellation Night", "Deep space, star patterns",
            appBackground     = Color.parseColor("#050D1F"),
            appBarBg          = Color.parseColor("#0A1535"),
            incomingBubble    = Color.parseColor("#0F2050"),
            outgoingBubble    = Color.parseColor("#7B3FE4"),
            incomingTextColor = Color.parseColor("#B8D4FF"),
            outgoingTextColor = Color.parseColor("#FFFFFF"),
            textPrimary       = Color.parseColor("#E8F0FF"),
            textSecondary     = Color.parseColor("#7B9FCC"),
            accentColor       = Color.parseColor("#7B3FE4"),
            bubbleCornerRadius = 18f,
            statusBarColor    = Color.parseColor("#0A1535")),

        // 4. Herbalist Garden
        ChatTheme("Herbalist Garden", "Natural green, botanical",
            appBackground     = Color.parseColor("#E8F5E0"),
            appBarBg          = Color.parseColor("#4A7C3F"),
            incomingBubble    = Color.parseColor("#C8E6B8"),
            outgoingBubble    = Color.parseColor("#2E6B28"),
            incomingTextColor = Color.parseColor("#1A3D15"),
            outgoingTextColor = Color.parseColor("#E8FFE0"),
            textPrimary       = Color.parseColor("#1A3D15"),
            textSecondary     = Color.parseColor("#4A7C3F"),
            accentColor       = Color.parseColor("#5DAE4C"),
            bubbleCornerRadius = 20f,
            statusBarColor    = Color.parseColor("#4A7C3F")),

        // 5. 8-Bit Adventure
        ChatTheme("8-Bit Adventure", "Pixel art, retro game style",
            appBackground     = Color.parseColor("#001830"),
            appBarBg          = Color.parseColor("#003060"),
            incomingBubble    = Color.parseColor("#004080"),
            outgoingBubble    = Color.parseColor("#00C060"),
            incomingTextColor = Color.parseColor("#00FF80"),
            outgoingTextColor = Color.parseColor("#001830"),
            textPrimary       = Color.parseColor("#00FF80"),
            textSecondary     = Color.parseColor("#0080FF"),
            accentColor       = Color.parseColor("#FF6000"),
            bubbleCornerRadius = 2f,
            statusBarColor    = Color.parseColor("#003060")),

        // 6. Retro Sci-Fi
        ChatTheme("Retro Sci-Fi", "CRT monitor, green on black",
            appBackground     = Color.parseColor("#0C0C0C"),
            appBarBg          = Color.parseColor("#101010"),
            incomingBubble    = Color.parseColor("#001800"),
            outgoingBubble    = Color.parseColor("#003300"),
            incomingTextColor = Color.parseColor("#00FF41"),
            outgoingTextColor = Color.parseColor("#AAFFAA"),
            textPrimary       = Color.parseColor("#00FF41"),
            textSecondary     = Color.parseColor("#00AA2A"),
            accentColor       = Color.parseColor("#00FF41"),
            bubbleCornerRadius = 3f,
            statusBarColor    = Color.parseColor("#101010")),

        // 7. Bamboo Zen (Neon Grid variant)
        ChatTheme("Bamboo Zen", "Soft green, bamboo texture",
            appBackground     = Color.parseColor("#F0F5E8"),
            appBarBg          = Color.parseColor("#5A7A3A"),
            incomingBubble    = Color.parseColor("#D8ECC0"),
            outgoingBubble    = Color.parseColor("#7AB050"),
            incomingTextColor = Color.parseColor("#2A4020"),
            outgoingTextColor = Color.parseColor("#F0FFE0"),
            textPrimary       = Color.parseColor("#2A4020"),
            textSecondary     = Color.parseColor("#5A7A3A"),
            accentColor       = Color.parseColor("#7AB050"),
            bubbleCornerRadius = 22f,
            statusBarColor    = Color.parseColor("#5A7A3A")),

        // 8. Street Mural
        ChatTheme("Street Mural", "Urban graffiti, bold colors",
            appBackground     = Color.parseColor("#1A1A1A"),
            appBarBg          = Color.parseColor("#2D2D2D"),
            incomingBubble    = Color.parseColor("#2A2A2A"),
            outgoingBubble    = Color.parseColor("#E83030"),
            incomingTextColor = Color.parseColor("#FFE040"),
            outgoingTextColor = Color.parseColor("#FFFFFF"),
            textPrimary       = Color.parseColor("#FFFFFF"),
            textSecondary     = Color.parseColor("#FFE040"),
            accentColor       = Color.parseColor("#E83030"),
            bubbleCornerRadius = 8f,
            statusBarColor    = Color.parseColor("#2D2D2D")),

        // 9. Inkwell Dark
        ChatTheme("Inkwell Dark", "Deep black, orange accent",
            appBackground     = Color.parseColor("#111111"),
            appBarBg          = Color.parseColor("#1A1A1A"),
            incomingBubble    = Color.parseColor("#222222"),
            outgoingBubble    = Color.parseColor("#E86820"),
            incomingTextColor = Color.parseColor("#CCCCCC"),
            outgoingTextColor = Color.parseColor("#FFFFFF"),
            textPrimary       = Color.parseColor("#EEEEEE"),
            textSecondary     = Color.parseColor("#888888"),
            accentColor       = Color.parseColor("#E86820"),
            bubbleCornerRadius = 16f,
            statusBarColor    = Color.parseColor("#1A1A1A")),

        // 10. Sand & Gold Gradient
        ChatTheme("Sand & Gold", "Desert sand, golden tones",
            appBackground     = Color.parseColor("#FDF3DC"),
            appBarBg          = Color.parseColor("#C8922A"),
            incomingBubble    = Color.parseColor("#F0D898"),
            outgoingBubble    = Color.parseColor("#C07828"),
            incomingTextColor = Color.parseColor("#4A3010"),
            outgoingTextColor = Color.parseColor("#FFF8E0"),
            textPrimary       = Color.parseColor("#3D2A08"),
            textSecondary     = Color.parseColor("#8A6020"),
            accentColor       = Color.parseColor("#E8A020"),
            bubbleCornerRadius = 18f,
            statusBarColor    = Color.parseColor("#C8922A")),

        // 11. Aquarium
        ChatTheme("Aquarium", "Ocean blue, fish & bubbles",
            appBackground     = Color.parseColor("#0A2040"),
            appBarBg          = Color.parseColor("#0D2D5A"),
            incomingBubble    = Color.parseColor("#0F3D6A"),
            outgoingBubble    = Color.parseColor("#1A7DC0"),
            incomingTextColor = Color.parseColor("#A8E0FF"),
            outgoingTextColor = Color.parseColor("#FFFFFF"),
            textPrimary       = Color.parseColor("#C8EEFF"),
            textSecondary     = Color.parseColor("#6AB8E0"),
            accentColor       = Color.parseColor("#00BFFF"),
            bubbleCornerRadius = 20f,
            statusBarColor    = Color.parseColor("#0D2D5A")),

        // 12. Onyx & Opal Marble
        ChatTheme("Onyx & Opal Marble", "Black marble, gold veins",
            appBackground     = Color.parseColor("#0F0F0F"),
            appBarBg          = Color.parseColor("#1A1A1A"),
            incomingBubble    = Color.parseColor("#1E1E1E"),
            outgoingBubble    = Color.parseColor("#8B1A1A"),
            incomingTextColor = Color.parseColor("#D4AF37"),
            outgoingTextColor = Color.parseColor("#FFE8C0"),
            textPrimary       = Color.parseColor("#E8E8E8"),
            textSecondary     = Color.parseColor("#D4AF37"),
            accentColor       = Color.parseColor("#D4AF37"),
            bubbleCornerRadius = 16f,
            statusBarColor    = Color.parseColor("#1A1A1A"))
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
