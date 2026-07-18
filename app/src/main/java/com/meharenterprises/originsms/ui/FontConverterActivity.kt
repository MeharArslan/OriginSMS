package com.meharenterprises.originsms.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.meharenterprises.originsms.R

class FontConverterActivity : AppCompatActivity() {

    private lateinit var editInput: EditText
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: FontAdapter
    private var selectedIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_font_converter)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        editInput = findViewById(R.id.editFontInput)
        recycler = findViewById(R.id.recyclerFonts)

        adapter = FontAdapter(
            onCopy = { text -> copyText(text) },
            onApply = { text ->
                val prefs = getSharedPreferences(GeneralSettingsActivity.PREFS_NAME, Context.MODE_PRIVATE)
                prefs.edit().putString(KEY_FONT_TEXT_STYLE, text).apply()
                Toast.makeText(this, "Font style saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
        )
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val previewText = "Font Style"
        editInput.setText(previewText)
        adapter.updateText(previewText)

        editInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) = Unit
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                val text = s?.toString()?.takeIf { it.isNotBlank() } ?: "Font Style"
                adapter.updateText(text)
            }
        })
    }

    private fun copyText(text: String) {
        val cm = getSystemService(ClipboardManager::class.java)
        cm.setPrimaryClip(ClipData.newPlainText("font", text))
        Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val KEY_FONT_TEXT_STYLE = "font_text_style_index"

        // 50+ Unicode font converters
        val FONT_NAMES = arrayOf(
            "𝓢𝓬𝓻𝓲𝓹𝓽 (Cursive Bold)",
            "𝕯𝖔𝖚𝖇𝖑𝖊-𝕾𝖙𝖗𝖚𝖐 𝕱𝖗𝖆𝖐𝖙𝖚𝖗",
            "𝔻𝕠𝕦𝕓𝕝𝕖-𝕊𝕥𝕣𝕦𝕔𝕜",
            "𝐒𝐞𝐫𝐢𝐟 𝐁𝐨𝐥𝐝",
            "𝑺𝒆𝒓𝒊𝒇 𝑩𝒐𝒍𝒅 𝑰𝒕𝒂𝒍𝒊𝒄",
            "𝘚𝘢𝘯𝘴 𝘐𝘵𝘢𝘭𝘪𝘤",
            "𝙎𝙖𝙣𝙨 𝘽𝙤𝙡𝙙 𝙄𝙩𝙖𝙡𝙞𝙘",
            "𝖲𝖺𝗇𝗌 𝖭𝗈𝗋𝗆𝖺𝗅",
            "𝗦𝗮𝗻𝘀 𝗕𝗼𝗹𝗱",
            "𝘚𝘤𝘳𝘪𝘱𝘵 𝘐𝘵𝘢𝘭𝘪𝘤",
            "ꜱᴍᴀʟʟ ᴄᴀᴘꜱ",
            "S P A C E D",
            "『ᗷOᒪᗪ ᗷOᑕK』",
            "【 ꜰᴀɴᴄʏ 】",
            "₣Ø₦₮ ₴₮ɎⱠɆ",
            "Ⓕⓞⓝⓣ Ⓢⓣⓨⓛⓔ",
            "ⒻⓄⓃⓉ ⓈⓉⓎⓁⒺ",
            "ᶠᵒⁿᵗ ˢᵗʸˡᵉ",
            "🅵🅾🅽🆃 🆂🆃🆈🅻🅴",
            "F̶o̶n̶t̶ ̶S̶t̶y̶l̶e̶",
            "F̲o̲n̲t̲ ̲S̲t̲y̲l̲e̲",
            "F̳o̳n̳t̳ ̳S̳t̳y̳l̳e̳",
            "F̈ö̈n̈ẗ S̈ẗÿl̈ë",
            "F҉o҉n҉t҉ ҉S҉t҉y҉l҉e҉",
            "Ｆｕｌｌｗｉｄｔｈ",
            "ᴲᴏᴎᴛ ꙅᴛʏʟᴲ",
            "fₒₙₜ ₛₜᵧₗₑ",
            "🄵🄾🄽🅃 🅂🅃🅈🄻🄴",
            "⒡⒪⒩⒯ ⒮⒯⒴⒧⒠",
            "🅕🅞🅝🅣 🅢🅣🅨🅛🅔",
            "ꦕꦺꦴꦤꦶꦏ",
            "𝓕𝓸𝓷𝓽 𝓢𝓽𝔂𝓵𝓮 ✨",
            "†Ϝ◎η† §†ɣℓ€†",
            "F̸o̸n̸t̸ ̸S̸t̸y̸l̸e̸",
            "F̴o̴n̴t̴ ̴S̴t̴y̴l̴e̴",
            "Fₒₙₜ 𝙎𝙩𝙮𝙡𝙚",
            "🆂🆄🅿🅴🆁",
            "⚡Ｆｏｎｔ Ｓｔｙｌｅ⚡",
            "✦ Font Style ✦",
            "꧁Font Style꧂",
            "彡Font Style彡",
            "「Font Style」",
            "》Font Style《",
            "◤Font Style◥",
            "★Font Style★",
            "♛Font Style♛",
            "✿Font Style✿",
            "♡Font Style♡",
            "⊱Font Style⊰",
            "⋆Font Style⋆"
        )

        fun convert(text: String, styleIndex: Int): String {
            val normalLower = "abcdefghijklmnopqrstuvwxyz"
            val normalUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            val normalDigits = "0123456789"

            // Unicode character maps for each style
            val styles = arrayOf(
                // 0: Script Bold (Cursive)
                "𝓪𝓫𝓬𝓭𝓮𝓯𝓰𝓱𝓲𝓳𝓴𝓵𝓶𝓷𝓸𝓹𝓺𝓻𝓼𝓽𝓾𝓿𝔀𝔁𝔂𝔃" to "𝓐𝓑𝓒𝓓𝓔𝓕𝓖𝓗𝓘𝓙𝓚𝓛𝓜𝓝𝓞𝓟𝓠𝓡𝓢𝓣𝓤𝓥𝓦𝓧𝓨𝓩",
                // 1: Fraktur Bold
                "𝖆𝖇𝖈𝖉𝖊𝖋𝖌𝖍𝖎𝖏𝖐𝖑𝖒𝖓𝖔𝖕𝖖𝖗𝖘𝖙𝖚𝖛𝖜𝖝𝖞𝖟" to "𝕬𝕭𝕮𝕯𝕰𝕱𝕲𝕳𝕴𝕵𝕶𝕷𝕸𝕹𝕺𝕻𝕼𝕽𝕾𝕿𝖀𝖁𝖂𝖃𝖄𝖅",
                // 2: Double-struck
                "𝕒𝕓𝕔𝕕𝕖𝕗𝕘𝕙𝕚𝕛𝕜𝕝𝕞𝕟𝕠𝕡𝕢𝕣𝕤𝕥𝕦𝕧𝕨𝕩𝕪𝕫" to "𝔸𝔹ℂ𝔻𝔼𝔽𝔾ℍ𝕀𝕁𝕂𝕃𝕄ℕ𝕆ℙℚℝ𝕊𝕋𝕌𝕍𝕎𝕏𝕐ℤ",
                // 3: Serif Bold
                "𝐚𝐛𝐜𝐝𝐞𝐟𝐠𝐡𝐢𝐣𝐤𝐥𝐦𝐧𝐨𝐩𝐪𝐫𝐬𝐭𝐮𝐯𝐰𝐱𝐲𝐳" to "𝐀𝐁𝐂𝐃𝐄𝐅𝐆𝐇𝐈𝐉𝐊𝐋𝐌𝐍𝐎𝐏𝐐𝐑𝐒𝐓𝐔𝐕𝐖𝐗𝐘𝐙",
                // 4: Serif Bold Italic
                "𝒂𝒃𝒄𝒅𝒆𝒇𝒈𝒉𝒊𝒋𝒌𝒍𝒎𝒏𝒐𝒑𝒒𝒓𝒔𝒕𝒖𝒗𝒘𝒙𝒚𝒛" to "𝑨𝑩𝑪𝑫𝑬𝑭𝑮𝑯𝑰𝑱𝑲𝑳𝑴𝑵𝑶𝑷𝑸𝑹𝑺𝑻𝑼𝑽𝑾𝑿𝒀𝒁",
                // 5: Sans Italic
                "𝘢𝘣𝘤𝘥𝘦𝘧𝘨𝘩𝘪𝘫𝘬𝘭𝘮𝘯𝘰𝘱𝘲𝘳𝘴𝘵𝘶𝘷𝘸𝘹𝘺𝘻" to "𝘈𝘉𝘊𝘋𝘌𝘍𝘎𝘏𝘐𝘑𝘒𝘓𝘔𝘕𝘖𝘗𝘘𝘙𝘚𝘛𝘜𝘝𝘞𝘟𝘠𝘡",
                // 6: Sans Bold Italic
                "𝙖𝙗𝙘𝙙𝙚𝙛𝙜𝙝𝙞𝙟𝙠𝙡𝙢𝙣𝙤𝙥𝙦𝙧𝙨𝙩𝙪𝙫𝙬𝙭𝙮𝙯" to "𝘼𝘽𝘾𝘿𝙀𝙁𝙂𝙃𝙄𝙅𝙆𝙇𝙈𝙉𝙊𝙋𝙌𝙍𝙎𝙏𝙐𝙑𝙒𝙓𝙔𝙕",
                // 7: Sans Normal
                "𝖺𝖻𝖼𝖽𝖾𝖿𝗀𝗁𝗂𝗃𝗄𝗅𝗆𝗇𝗈𝗉𝗊𝗋𝗌𝗍𝗎𝗏𝗐𝗑𝗒𝗓" to "𝖠𝖡𝖢𝖣𝖤𝖥𝖦𝖧𝖨𝖩𝖪𝖫𝖬𝖭𝖮𝖯𝖰𝖱𝖲𝖳𝖴𝖵𝖶𝖷𝖸𝖹",
                // 8: Sans Bold
                "𝗮𝗯𝗰𝗱𝗲𝗳𝗴𝗵𝗶𝗷𝗸𝗹𝗺𝗻𝗼𝗽𝗾𝗿𝘀𝘁𝘂𝘃𝘄𝘅𝘆𝘇" to "𝗔𝗕𝗖𝗗𝗘𝗙𝗚𝗛𝗜𝗝𝗞𝗟𝗠𝗡𝗢𝗣𝗤𝗥𝗦𝗧𝗨𝗩𝗪𝗫𝗬𝗭",
                // 9: Script Italic
                "𝓪𝓫𝓬𝓭𝓮𝓯𝓰𝓱𝓲𝓳𝓴𝓵𝓶𝓷𝓸𝓹𝓺𝓻𝓼𝓽𝓾𝓿𝔀𝔁𝔂𝔃" to "𝒜ℬ𝒞𝒟ℰℱ𝒢ℋℐ𝒥𝒦ℒℳ𝒩𝒪𝒫𝒬ℛ𝒮𝒯𝒰𝒱𝒲𝒳𝒴𝒵"
            )

            if (styleIndex < styles.size) {
                val (lower, upper) = styles[styleIndex]
                val lowerChars = lower.codePoints().toArray()
                val upperChars = upper.codePoints().toArray()
                return text.map { c ->
                    when {
                        c in 'a'..'z' && lowerChars.size > c - 'a' ->
                            String(Character.toChars(lowerChars[c - 'a']))
                        c in 'A'..'Z' && upperChars.size > c - 'A' ->
                            String(Character.toChars(upperChars[c - 'A']))
                        else -> c.toString()
                    }
                }.joinToString("")
            }

            // Styles 10+ are decorative (just apply decorators)
            return when (styleIndex) {
                10 -> text.map { c ->
                    val small = "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀꜱᴛᴜᴠᴡxʏᴢ"
                    if (c in 'a'..'z') small[c - 'a'].toString()
                    else if (c in 'A'..'Z') small[c - 'A'].toString()
                    else c.toString()
                }.joinToString("")
                11 -> text.map { "$it " }.joinToString("").trim()
                12 -> "『${text.uppercase()}』"
                13 -> "【 ${text} 】"
                14 -> text.map { c ->
                    val map = mapOf('A' to '₳','B' to 'Ƀ','C' to '₵','D' to 'Đ','E' to 'Ɇ',
                        'F' to '₣','G' to 'Ǥ','H' to 'Ħ','I' to 'ł','J' to 'J','K' to 'Ꝁ',
                        'L' to 'Ł','M' to 'M','N' to '₦','O' to 'Ø','P' to 'P','Q' to 'Q',
                        'R' to 'Ɽ','S' to '₴','T' to '₮','U' to 'Ʉ','V' to 'V','W' to 'W',
                        'X' to '₭','Y' to 'Ɏ','Z' to 'Ƶ')
                    map[c.uppercaseChar()]?.toString() ?: c.toString()
                }.joinToString("")
                15 -> text.map { c ->
                    val circled = "ⓐⓑⓒⓓⓔⓕⓖⓗⓘⓙⓚⓛⓜⓝⓞⓟⓠⓡⓢⓣⓤⓥⓦⓧⓨⓩ"
                    if (c in 'a'..'z') circled[c - 'a'].toString()
                    else if (c in 'A'..'Z') circled[c - 'A'].toString()
                    else c.toString()
                }.joinToString("")
                16 -> text.map { c ->
                    val neg = "🅐🅑🅒🅓🅔🅕🅖🅗🅘🅙🅚🅛🅜🅝🅞🅟🅠🅡🅢🅣🅤🅥🅦🅧🅨🅩"
                    if (c in 'a'..'z') neg[c - 'a'].toString()
                    else if (c in 'A'..'Z') neg[c - 'A'].toString()
                    else c.toString()
                }.joinToString("")
                17 -> text.map { c ->
                    val sup = mapOf('a' to 'ᵃ','b' to 'ᵇ','c' to 'ᶜ','d' to 'ᵈ','e' to 'ᵉ',
                        'f' to 'ᶠ','g' to 'ᵍ','h' to 'ʰ','i' to 'ⁱ','j' to 'ʲ','k' to 'ᵏ',
                        'l' to 'ˡ','m' to 'ᵐ','n' to 'ⁿ','o' to 'ᵒ','p' to 'ᵖ','q' to 'q',
                        'r' to 'ʳ','s' to 'ˢ','t' to 'ᵗ','u' to 'ᵘ','v' to 'ᵛ','w' to 'ʷ',
                        'x' to 'ˣ','y' to 'ʸ','z' to 'ᶻ')
                    sup[c.lowercaseChar()]?.toString() ?: c.toString()
                }.joinToString("")
                18 -> text.map { c ->
                    val box = "🅰🅱🅲🅳🅴🅵🅶🅷🅸🅹🅺🅻🅼🅽🅾🅿🆀🆁🆂🆃🆄🆅🆆🆇🆈🆉"
                    if (c in 'a'..'z') box[c - 'a'].toString()
                    else if (c in 'A'..'Z') box[c - 'A'].toString()
                    else c.toString()
                }.joinToString("")
                19 -> text.map { "${it}\u0336" }.joinToString("")  // strikethrough
                20 -> text.map { "${it}\u0332" }.joinToString("")  // underline
                21 -> text.map { "${it}\u20E8" }.joinToString("")  // underline triple
                22 -> text.map { c -> if (c == ' ') " " else "${c}\u0308" }.joinToString("") // umlaut
                23 -> text.map { c -> if (c == ' ') " " else "${c}\u0489" }.joinToString("") // combining mark
                24 -> text.map { c ->
                    val fw = if (c in 'A'..'Z') (c - 'A' + 0xFF21).toChar()
                        else if (c in 'a'..'z') (c - 'a' + 0xFF41).toChar()
                        else c
                    fw.toString()
                }.joinToString("")
                25 -> "꧁${text}꧂"
                26 -> "彡${text}彡"
                27 -> "「${text}」"
                28 -> "》${text}《"
                29 -> "◤${text}◥"
                30 -> "★ ${text} ★"
                31 -> "♛ ${text} ♛"
                32 -> "✿ ${text} ✿"
                33 -> "♡ ${text} ♡"
                34 -> "⊱ ${text} ⊰"
                35 -> "⋆ ${text} ⋆"
                36 -> "⚡ ${text} ⚡"
                37 -> "✦ ${text} ✦"
                38 -> "🌟 ${text} 🌟"
                39 -> "🔥 ${text} 🔥"
                40 -> "💫 ${text} 💫"
                41 -> "🎯 ${text} 🎯"
                42 -> "👑 ${text} 👑"
                43 -> "🌸 ${text} 🌸"
                44 -> "💎 ${text} 💎"
                45 -> "🌈 ${text} 🌈"
                46 -> text.uppercase()
                47 -> text.lowercase()
                48 -> text.split(" ").joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
                49 -> text.chunked(1).joinToString("-")
                else -> text
            }
        }
    }
}

class FontAdapter(
    private val onCopy: (String) -> Unit,
    private val onApply: (String) -> Unit
) : RecyclerView.Adapter<FontAdapter.FontVH>() {

    private var inputText = "Font Style"
    private var selectedIndex = 0

    fun updateText(text: String) {
        inputText = text
        notifyDataSetChanged()
    }

    override fun getItemCount() = FontConverterActivity.FONT_NAMES.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FontVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_font_style, parent, false)
        return FontVH(view)
    }

    override fun onBindViewHolder(holder: FontVH, position: Int) {
        val converted = FontConverterActivity.convert(inputText, position)
        holder.txtFontName.text = FontConverterActivity.FONT_NAMES[position]
        holder.txtFontPreview.text = converted

        if (position == selectedIndex) {
            holder.itemView.setBackgroundColor(0x220066FF)
            holder.txtSelected.visibility = View.VISIBLE
        } else {
            holder.itemView.setBackgroundColor(0x00000000)
            holder.txtSelected.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            selectedIndex = position
            notifyDataSetChanged()
        }
        holder.btnCopy.setOnClickListener { onCopy(converted) }
        holder.btnApply.setOnClickListener {
            selectedIndex = position
            onApply(converted)
        }
    }

    inner class FontVH(v: View) : RecyclerView.ViewHolder(v) {
        val txtFontName: TextView = v.findViewById(R.id.txtFontStyleName)
        val txtFontPreview: TextView = v.findViewById(R.id.txtFontPreview)
        val btnCopy: View = v.findViewById(R.id.btnFontCopy)
        val btnApply: View = v.findViewById(R.id.btnFontApply)
        val txtSelected: TextView = v.findViewById(R.id.txtFontSelected)
    }
}
