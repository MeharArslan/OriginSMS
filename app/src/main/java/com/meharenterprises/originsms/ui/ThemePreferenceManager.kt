package com.meharenterprises.originsms.ui

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class ThemePreferenceManager(private val context: Context) {
    private val prefs = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

    enum class ThemeMode { LIGHT, DARK, SYSTEM }

    fun applyStoredMode() {
        AppCompatDelegate.setDefaultNightMode(toDelegate(getCurrentMode()))
    }

    fun setMode(mode: ThemeMode) {
        prefs.edit().putString("theme_mode", mode.name).apply()
        AppCompatDelegate.setDefaultNightMode(toDelegate(mode))
    }

    fun getCurrentMode(): ThemeMode {
        val saved = prefs.getString("theme_mode", ThemeMode.SYSTEM.name)
        return try { ThemeMode.valueOf(saved ?: ThemeMode.SYSTEM.name) }
        catch (_: Exception) { ThemeMode.SYSTEM }
    }

    private fun toDelegate(mode: ThemeMode) = when (mode) {
        ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
        ThemeMode.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }
}
