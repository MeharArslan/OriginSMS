package com.meharenterprises.originsms.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.meharenterprises.originsms.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FontConverterActivity.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/meharenterprises/originsms/ui/FontConverterActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/meharenterprises/originsms/ui/FontAdapter;", "editInput", "Landroid/widget/EditText;", "recycler", "Landroidx/recyclerview/widget/RecyclerView;", "selectedIndex", "", "copyText", "", "text", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class FontConverterActivity extends AppCompatActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String[] FONT_NAMES = {"𝓢𝓬𝓻𝓲𝓹𝓽 (Cursive Bold)", "𝕯𝖔𝖚𝖇𝖑𝖊-𝕾𝖙𝖗𝖚𝖐 𝕱𝖗𝖆𝖐𝖙𝖚𝖗", "𝔻𝕠𝕦𝕓𝕝𝕖-𝕊𝕥𝕣𝕦𝕔𝕜", "𝐒𝐞𝐫𝐢𝐟 𝐁𝐨𝐥𝐝", "𝑺𝒆𝒓𝒊𝒇 𝑩𝒐𝒍𝒅 𝑰𝒕𝒂𝒍𝒊𝒄", "𝘚𝘢𝘯𝘴 𝘐𝘵𝘢𝘭𝘪𝘤", "𝙎𝙖𝙣𝙨 𝘽𝙤𝙡𝙙 𝙄𝙩𝙖𝙡𝙞𝙘", "𝖲𝖺𝗇𝗌 𝖭𝗈𝗋𝗆𝖺𝗅", "𝗦𝗮𝗻𝘀 𝗕𝗼𝗹𝗱", "𝘚𝘤𝘳𝘪𝘱𝘵 𝘐𝘵𝘢𝘭𝘪𝘤", "ꜱᴍᴀʟʟ ᴄᴀᴘꜱ", "S P A C E D", "『ᗷOᒪᗪ ᗷOᑕK』", "【 ꜰᴀɴᴄʏ 】", "₣Ø₦₮ ₴₮ɎⱠɆ", "Ⓕⓞⓝⓣ Ⓢⓣⓨⓛⓔ", "ⒻⓄⓃⓉ ⓈⓉⓎⓁⒺ", "ᶠᵒⁿᵗ ˢᵗʸˡᵉ", "🅵🅾🅽🆃 🆂🆃🆈🅻🅴", "F̶o̶n̶t̶ ̶S̶t̶y̶l̶e̶", "F̲o̲n̲t̲ ̲S̲t̲y̲l̲e̲", "F̳o̳n̳t̳ ̳S̳t̳y̳l̳e̳", "F̈ö̈n̈ẗ S̈ẗÿl̈ë", "F҉o҉n҉t҉ ҉S҉t҉y҉l҉e҉", "Ｆｕｌｌｗｉｄｔｈ", "ᴲᴏᴎᴛ ꙅᴛʏʟᴲ", "fₒₙₜ ₛₜᵧₗₑ", "🄵🄾🄽🅃 🅂🅃🅈🄻🄴", "⒡⒪⒩⒯ ⒮⒯⒴⒧⒠", "🅕🅞🅝🅣 🅢🅣🅨🅛🅔", "ꦕꦺꦴꦤꦶꦏ", "𝓕𝓸𝓷𝓽 𝓢𝓽𝔂𝓵𝓮 ✨", "†Ϝ◎η† §†ɣℓ€†", "F̸o̸n̸t̸ ̸S̸t̸y̸l̸e̸", "F̴o̴n̴t̴ ̴S̴t̴y̴l̴e̴", "Fₒₙₜ 𝙎𝙩𝙮𝙡𝙚", "🆂🆄🅿🅴🆁", "⚡Ｆｏｎｔ Ｓｔｙｌｅ⚡", "✦ Font Style ✦", "꧁Font Style꧂", "彡Font Style彡", "「Font Style」", "》Font Style《", "◤Font Style◥", "★Font Style★", "♛Font Style♛", "✿Font Style✿", "♡Font Style♡", "⊱Font Style⊰", "⋆Font Style⋆"};
    public static final String KEY_FONT_TEXT_STYLE = "font_text_style_index";
    private FontAdapter adapter;
    private EditText editInput;
    private RecyclerView recycler;
    private int selectedIndex;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_converter);
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.FontConverterActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FontConverterActivity.onCreate$lambda$0(FontConverterActivity.this, view);
            }
        });
        View findViewById = findViewById(R.id.editFontInput);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.editInput = (EditText) findViewById;
        View findViewById2 = findViewById(R.id.recyclerFonts);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
        this.recycler = (RecyclerView) findViewById2;
        this.adapter = new FontAdapter(new Function1<String, Unit>() { // from class: com.meharenterprises.originsms.ui.FontConverterActivity$onCreate$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String text) {
                Intrinsics.checkNotNullParameter(text, "text");
                FontConverterActivity.this.copyText(text);
            }
        }, new Function1<String, Unit>() { // from class: com.meharenterprises.originsms.ui.FontConverterActivity$onCreate$3
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String text) {
                Intrinsics.checkNotNullParameter(text, "text");
                SharedPreferences prefs = FontConverterActivity.this.getSharedPreferences(GeneralSettingsActivity.PREFS_NAME, 0);
                prefs.edit().putString(FontConverterActivity.KEY_FONT_TEXT_STYLE, text).apply();
                Toast.makeText(FontConverterActivity.this, "Font style saved!", 0).show();
                FontConverterActivity.this.finish();
            }
        });
        RecyclerView recyclerView = this.recycler;
        EditText editText = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView2 = this.recycler;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
            recyclerView2 = null;
        }
        FontAdapter fontAdapter = this.adapter;
        if (fontAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            fontAdapter = null;
        }
        recyclerView2.setAdapter(fontAdapter);
        EditText editText2 = this.editInput;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editInput");
            editText2 = null;
        }
        editText2.setText("Font Style");
        FontAdapter fontAdapter2 = this.adapter;
        if (fontAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            fontAdapter2 = null;
        }
        fontAdapter2.updateText("Font Style");
        EditText editText3 = this.editInput;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editInput");
        } else {
            editText = editText3;
        }
        editText.addTextChangedListener(new TextWatcher() { // from class: com.meharenterprises.originsms.ui.FontConverterActivity$onCreate$4
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            /* JADX WARN: Code restructure failed: missing block: B:8:0x0018, code lost:
            
                if (r1 == null) goto L11;
             */
            @Override // android.text.TextWatcher
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public void afterTextChanged(android.text.Editable r6) {
                /*
                    r5 = this;
                    r0 = 0
                    if (r6 == 0) goto L1a
                    java.lang.String r1 = r6.toString()
                    if (r1 == 0) goto L1a
                    r2 = r1
                    r3 = 0
                    r4 = r2
                    java.lang.CharSequence r4 = (java.lang.CharSequence) r4
                    boolean r4 = kotlin.text.StringsKt.isBlank(r4)
                    r4 = r4 ^ 1
                    if (r4 == 0) goto L17
                    goto L18
                L17:
                    r1 = r0
                L18:
                    if (r1 != 0) goto L1c
                L1a:
                    java.lang.String r1 = "Font Style"
                L1c:
                    com.meharenterprises.originsms.ui.FontConverterActivity r2 = com.meharenterprises.originsms.ui.FontConverterActivity.this
                    com.meharenterprises.originsms.ui.FontAdapter r2 = com.meharenterprises.originsms.ui.FontConverterActivity.access$getAdapter$p(r2)
                    if (r2 != 0) goto L2a
                    java.lang.String r2 = "adapter"
                    kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
                    goto L2b
                L2a:
                    r0 = r2
                L2b:
                    r0.updateText(r1)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.FontConverterActivity$onCreate$4.afterTextChanged(android.text.Editable):void");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(FontConverterActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void copyText(String text) {
        ClipboardManager cm = (ClipboardManager) getSystemService(ClipboardManager.class);
        cm.setPrimaryClip(ClipData.newPlainText("font", text));
        Toast.makeText(this, "Copied!", 0).show();
    }

    /* compiled from: FontConverterActivity.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rR\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/meharenterprises/originsms/ui/FontConverterActivity$Companion;", "", "()V", "FONT_NAMES", "", "", "getFONT_NAMES", "()[Ljava/lang/String;", "[Ljava/lang/String;", "KEY_FONT_TEXT_STYLE", "convert", "text", "styleIndex", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String[] getFONT_NAMES() {
            return FontConverterActivity.FONT_NAMES;
        }

        /* JADX WARN: Removed duplicated region for block: B:293:0x0d11  */
        /* JADX WARN: Removed duplicated region for block: B:296:0x0d1c  */
        /* JADX WARN: Removed duplicated region for block: B:302:0x0d2d  */
        /* JADX WARN: Removed duplicated region for block: B:304:0x0d38  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.String convert(java.lang.String r37, int r38) {
            /*
                Method dump skipped, instructions count: 3504
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.FontConverterActivity.Companion.convert(java.lang.String, int):java.lang.String");
        }
    }
}
