package com.meharenterprises.originsms.ui

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.meharenterprises.originsms.R

class GeneralSettingsActivity : AppCompatActivity() {

    private lateinit var prefs: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_settings)
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        setupBubbles()
        setupPinchZoom()
        setupAnimations()
        setupSuggestions()
        setupSwipeActions()
        setupTheme()
        setupFont()
        setupKeyboardTheme()
    }

    private fun setupBubbles() {
        val sw = findViewById<Switch>(R.id.switchBubbles)
        sw.isChecked = prefs.getBoolean(KEY_BUBBLES, true)
        sw.setOnCheckedChangeListener { _, c -> prefs.edit().putBoolean(KEY_BUBBLES, c).apply() }
    }

    private fun setupPinchZoom() {
        val sw = findViewById<Switch>(R.id.switchPinchZoom)
        sw.isChecked = prefs.getBoolean(KEY_PINCH_ZOOM, true)
        sw.setOnCheckedChangeListener { _, c -> prefs.edit().putBoolean(KEY_PINCH_ZOOM, c).apply() }
    }

    private fun setupAnimations() {
        val sw = findViewById<Switch>(R.id.switchAnimations)
        sw.isChecked = prefs.getBoolean(KEY_ANIMATIONS, true)
        sw.setOnCheckedChangeListener { _, c -> prefs.edit().putBoolean(KEY_ANIMATIONS, c).apply() }
    }

    private fun setupSuggestions() {
        val sw = findViewById<Switch>(R.id.switchSuggestions)
        sw.isChecked = prefs.getBoolean(KEY_SUGGESTIONS, true)
        sw.setOnCheckedChangeListener { _, c -> prefs.edit().putBoolean(KEY_SUGGESTIONS, c).apply() }
    }

    private fun setupSwipeActions() {
        val txtR = findViewById<TextView>(R.id.txtSwipeRightValue)
        val txtL = findViewById<TextView>(R.id.txtSwipeLeftValue)
        val opts = arrayOf("Archive", "Delete", "Mark as read", "None")
        var curR = prefs.getInt(KEY_SWIPE_RIGHT, 0)
        var curL = prefs.getInt(KEY_SWIPE_LEFT, 1)
        txtR.text = opts[curR]; txtL.text = opts[curL]

        findViewById<View>(R.id.rowSwipeRight).setOnClickListener {
            AlertDialog.Builder(this).setTitle("Swipe right")
                .setSingleChoiceItems(opts, curR) { d, i ->
                    prefs.edit().putInt(KEY_SWIPE_RIGHT, i).apply()
                    curR = i; txtR.text = opts[i]; d.dismiss()
                }.show()
        }
        findViewById<View>(R.id.rowSwipeLeft).setOnClickListener {
            AlertDialog.Builder(this).setTitle("Swipe left")
                .setSingleChoiceItems(opts, curL) { d, i ->
                    prefs.edit().putInt(KEY_SWIPE_LEFT, i).apply()
                    curL = i; txtL.text = opts[i]; d.dismiss()
                }.show()
        }
    }

    private fun setupTheme() {
        val txtVal = findViewById<TextView>(R.id.txtChatThemeValue)
        fun refresh() { txtVal.text = ChatThemeManager.themes[ChatThemeManager.getCurrentIndex(this)].name }
        refresh()

        findViewById<View>(R.id.rowChatTheme).setOnClickListener {
            showThemeCardGrid { refresh() }
        }
    }

    private fun showThemeCardGrid(onChanged: () -> Unit) {
        val themes = ChatThemeManager.themes
        val currentIdx = ChatThemeManager.getCurrentIndex(this)
        val dp = resources.displayMetrics.density.toInt()

        val grid = android.widget.GridLayout(this).apply {
            columnCount = 2
            setPadding(8 * dp, 8 * dp, 8 * dp, 8 * dp)
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Choose Theme")
            .setView(android.widget.ScrollView(this).apply { addView(grid) })
            .setNegativeButton(android.R.string.cancel, null)
            .create()

        themes.forEachIndexed { i, theme ->
            val card = LayoutInflater.from(this).inflate(R.layout.item_theme_card, null, false)
            val params = android.widget.GridLayout.LayoutParams().apply {
                width = 0
                columnSpec = android.widget.GridLayout.spec(i % 2, 1f)
                setMargins(6 * dp, 6 * dp, 6 * dp, 6 * dp)
            }
            card.layoutParams = params

            applyThemeToCard(card, theme, i == currentIdx)

            card.setOnClickListener {
                ChatThemeManager.applyTheme(this, i)
                dialog.dismiss()
                onChanged()
                android.widget.Toast.makeText(this, "Theme: ${theme.name}", android.widget.Toast.LENGTH_SHORT).show()
            }
            grid.addView(card)
        }
        dialog.show()
    }

    private fun applyThemeToCard(card: View, theme: com.meharenterprises.originsms.ui.ChatTheme, isSelected: Boolean) {
        card.findViewById<View>(R.id.cardBackground).setBackgroundColor(theme.appBackground)
        card.findViewById<View>(R.id.miniToolbar).setBackgroundColor(theme.appBarBg)
        card.findViewById<View>(R.id.miniInputBar).setBackgroundColor(theme.appBarBg)

        val cr = theme.bubbleCornerRadius
        card.findViewById<android.widget.TextView>(R.id.bubbleSent).apply {
            background = GradientDrawable().apply { setColor(theme.outgoingBubble); cornerRadius = cr }
            setTextColor(theme.outgoingTextColor)
        }
        card.findViewById<android.widget.TextView>(R.id.bubbleReceived).apply {
            background = GradientDrawable().apply { setColor(theme.incomingBubble); cornerRadius = cr }
            setTextColor(theme.incomingTextColor)
        }
        card.findViewById<android.widget.TextView>(R.id.bubbleReceived2).apply {
            background = GradientDrawable().apply { setColor(theme.incomingBubble); cornerRadius = cr }
            setTextColor(theme.incomingTextColor)
        }
        card.findViewById<View>(R.id.miniSendBtn).background =
            GradientDrawable().apply { shape = GradientDrawable.OVAL; setColor(theme.accentColor) }

        card.findViewById<android.widget.TextView>(R.id.txtThemeCardName).apply {
            text = theme.name
            setTextColor(if (isSelected) theme.accentColor else android.graphics.Color.GRAY)
            if (isSelected) setTypeface(null, android.graphics.Typeface.BOLD)
        }
        card.findViewById<View>(R.id.txtThemeSelected).visibility =
            if (isSelected) View.VISIBLE else View.GONE

        if (isSelected) {
            card.foreground = GradientDrawable().apply {
                setColor(android.graphics.Color.TRANSPARENT)
                setStroke((3 * resources.displayMetrics.density).toInt(), theme.accentColor)
                cornerRadius = 14 * resources.displayMetrics.density
            }
        } else {
            card.foreground = null
        }
    }

    private fun setupFont() {
        val txtCurrent = findViewById<TextView?>(R.id.txtFontValue)
        val currentIdx = prefs.getInt(KEY_FONT, 0)
        txtCurrent?.text = GeneralSettingsActivity.FONTS.getOrElse(currentIdx) { "Default" }

        findViewById<View?>(R.id.rowFontStyle)?.setOnClickListener {
            startActivity(android.content.Intent(this, FontConverterActivity::class.java))
        }
    }

    private fun setupKeyboardTheme() {
        val themes = arrayOf("System default", "Light", "Dark")
        val txtVal = findViewById<android.widget.TextView?>(R.id.txtKeyboardThemeValue)
        val current = prefs.getInt(KEY_KEYBOARD_THEME, 0)
        txtVal?.text = themes[current]

        findViewById<View?>(R.id.rowKeyboardTheme)?.setOnClickListener {
            val cur = prefs.getInt(KEY_KEYBOARD_THEME, 0)
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Keyboard Theme")
                .setSingleChoiceItems(themes, cur) { dialog, index ->
                    prefs.edit().putInt(KEY_KEYBOARD_THEME, index).apply()
                    txtVal?.text = themes[index]
                    dialog.dismiss()
                    android.widget.Toast.makeText(this,
                        "Keyboard theme: ${themes[index]}", android.widget.Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
    }

    companion object {
        const val PREFS_NAME = "origin_sms_general_prefs"
        const val KEY_BUBBLES = "bubbles_enabled"
        const val KEY_PINCH_ZOOM = "pinch_zoom_enabled"
        const val KEY_ANIMATIONS = "animations_enabled"
        const val KEY_SUGGESTIONS = "suggestions_enabled"
        const val KEY_SWIPE_RIGHT = "swipe_right_action"
        const val KEY_SWIPE_LEFT = "swipe_left_action"
        const val KEY_FONT = "app_font"
        const val KEY_KEYBOARD_THEME = "keyboard_theme"

        val FONTS = arrayOf(
            "Default",
            "Serif (Elegant)",
            "Monospace (Code)",
            "Sans-Serif Condensed",
            "Cursive (Casual)"
        )

        val FONT_TYPEFACES = arrayOf(
            android.graphics.Typeface.DEFAULT,
            android.graphics.Typeface.SERIF,
            android.graphics.Typeface.MONOSPACE,
            android.graphics.Typeface.create("sans-serif-condensed", android.graphics.Typeface.NORMAL),
            android.graphics.Typeface.create("cursive", android.graphics.Typeface.NORMAL)
        )

        fun getSelectedFont(context: android.content.Context): android.graphics.Typeface {
            val idx = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
                .getInt(KEY_FONT, 0)
            return FONT_TYPEFACES.getOrElse(idx) { android.graphics.Typeface.DEFAULT }
        }
    }
}
