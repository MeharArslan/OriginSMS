package com.meharenterprises.originsms.ui

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

/**
 * Stores the user's explicit theme choice (Light / Dark / Follow system) and
 * applies it via AppCompatDelegate.setDefaultNightMode, which AppCompat then
 * propagates to every activity in the app immediately — no per-activity
 * theme-switching code is needed elsewhere.
 */
class ThemePreferenceManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    enum class ThemeMode { LIGHT, DARK, SYSTEM }

    fun getCurrentMode(): ThemeMode {
        return when (prefs.getString(KEY_THEME_MODE, ThemeMode.SYSTEM.name)) {
            ThemeMode.LIGHT.name -> ThemeMode.LIGHT
            ThemeMode.DARK.name -> ThemeMode.DARK
            else -> ThemeMode.SYSTEM
        }
    }

    fun setMode(mode: ThemeMode) {
        prefs.edit().putString(KEY_THEME_MODE, mode.name).apply()
        applyMode(mode)
    }

    /**
     * Applies whatever mode is currently stored. Safe to call on every app
     * startup (e.g. from Application.onCreate) so the chosen theme persists
     * across process restarts.
     */
    fun applyStoredMode() {
        applyMode(getCurrentMode())
    }

    private fun applyMode(mode: ThemeMode) {
        val nightMode = when (mode) {
            ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeMode.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    companion object {
        private const val PREFS_NAME = "origin_sms_theme_prefs"
        private const val KEY_THEME_MODE = "theme_mode"
    }
}
