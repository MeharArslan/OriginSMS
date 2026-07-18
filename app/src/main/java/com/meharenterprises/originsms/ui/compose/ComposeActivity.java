package com.meharenterprises.originsms.ui.compose;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.PickVisualMediaRequestKt;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import androidx.emoji2.emojipicker.EmojiPickerView;
import androidx.emoji2.emojipicker.EmojiViewItem;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ContactsHelper;
import com.meharenterprises.originsms.core.Message;
import com.meharenterprises.originsms.core.SmsRepository;
import com.meharenterprises.originsms.ui.thread.MessageAdapter;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: ComposeActivity.kt */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 &2\u00020\u0001:\u0001&B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u001dH\u0002J\u0012\u0010\u001f\u001a\u00020\u001d2\b\u0010 \u001a\u0004\u0018\u00010!H\u0014J\b\u0010\"\u001a\u00020\u001dH\u0002J\b\u0010#\u001a\u00020\u001dH\u0002J\b\u0010$\u001a\u00020\u001dH\u0002J\b\u0010%\u001a\u00020\u001dH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lcom/meharenterprises/originsms/ui/compose/ComposeActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "attachmentPickerLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroidx/activity/result/PickVisualMediaRequest;", "contactPickerAdapter", "Lcom/meharenterprises/originsms/ui/compose/ContactPickerAdapter;", "contactsHelper", "Lcom/meharenterprises/originsms/core/ContactsHelper;", "editMessage", "Landroid/widget/EditText;", "editRecipient", "emojiPicker", "Landroidx/emoji2/emojipicker/EmojiPickerView;", "emojiPickerVisible", "", "isProgrammaticTextChange", "pendingAttachments", "", "Landroid/net/Uri;", "recyclerContactPicker", "Landroidx/recyclerview/widget/RecyclerView;", "recyclerMessages", "repository", "Lcom/meharenterprises/originsms/core/SmsRepository;", "selectedAddress", "", "attemptSend", "", "hideEmojiPicker", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupEmojiPicker", "showContactMode", "showEmojiPicker", "showMessageMode", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ComposeActivity extends AppCompatActivity {
    public static final String EXTRA_PREFILL_BODY = "extra_prefill_body";
    private ContactPickerAdapter contactPickerAdapter;
    private ContactsHelper contactsHelper;
    private EditText editMessage;
    private EditText editRecipient;
    private EmojiPickerView emojiPicker;
    private boolean emojiPickerVisible;
    private boolean isProgrammaticTextChange;
    private RecyclerView recyclerContactPicker;
    private RecyclerView recyclerMessages;
    private SmsRepository repository;
    private String selectedAddress;
    private final List<Uri> pendingAttachments = new ArrayList();
    private final ActivityResultLauncher<PickVisualMediaRequest> attachmentPickerLauncher = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(5), new ActivityResultCallback() { // from class: com.meharenterprises.originsms.ui.compose.ComposeActivity$$ExternalSyntheticLambda9
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            ComposeActivity.attachmentPickerLauncher$lambda$1(ComposeActivity.this, (List) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static final void attachmentPickerLauncher$lambda$1(ComposeActivity this$0, List uris) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(uris, "uris");
        List $this$forEach$iv = uris;
        for (Object element$iv : $this$forEach$iv) {
            Uri uri = (Uri) element$iv;
            try {
                this$0.getContentResolver().takePersistableUriPermission(uri, 1);
            } catch (SecurityException e) {
            }
            this$0.pendingAttachments.add(uri);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.repository = new SmsRepository(applicationContext);
        this.contactsHelper = new ContactsHelper(this);
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.compose.ComposeActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ComposeActivity.onCreate$lambda$2(ComposeActivity.this, view);
            }
        });
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("Choose Contact");
        }
        ActionBar supportActionBar2 = getSupportActionBar();
        if (supportActionBar2 != null) {
            supportActionBar2.setDisplayShowTitleEnabled(true);
        }
        View findViewById = findViewById(R.id.editRecipient);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.editRecipient = (EditText) findViewById;
        View findViewById2 = findViewById(R.id.editMessage);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
        this.editMessage = (EditText) findViewById2;
        View findViewById3 = findViewById(R.id.recyclerContactPicker);
        Intrinsics.checkNotNullExpressionValue(findViewById3, "findViewById(...)");
        this.recyclerContactPicker = (RecyclerView) findViewById3;
        View findViewById4 = findViewById(R.id.recyclerMessages);
        Intrinsics.checkNotNullExpressionValue(findViewById4, "findViewById(...)");
        this.recyclerMessages = (RecyclerView) findViewById4;
        View findViewById5 = findViewById(R.id.emojiPickerView);
        Intrinsics.checkNotNullExpressionValue(findViewById5, "findViewById(...)");
        this.emojiPicker = (EmojiPickerView) findViewById5;
        EditText editText = this.editRecipient;
        EditText editText2 = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editRecipient");
            editText = null;
        }
        editText.post(new Runnable() { // from class: com.meharenterprises.originsms.ui.compose.ComposeActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ComposeActivity.onCreate$lambda$3(ComposeActivity.this);
            }
        });
        ImageView btnClear = (ImageView) findViewById(R.id.btnClearRecipient);
        if (btnClear != null) {
            btnClear.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.compose.ComposeActivity$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ComposeActivity.onCreate$lambda$4(ComposeActivity.this, view);
                }
            });
        }
        String it = getIntent().getStringExtra("extra_prefill_body");
        if (it != null) {
            EditText editText3 = this.editMessage;
            if (editText3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("editMessage");
                editText3 = null;
            }
            editText3.setText(it);
        }
        RecyclerView recyclerView = this.recyclerMessages;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerMessages");
            recyclerView = null;
        }
        LinearLayoutManager $this$onCreate_u24lambda_u246 = new LinearLayoutManager(this);
        $this$onCreate_u24lambda_u246.setStackFromEnd(true);
        recyclerView.setLayoutManager($this$onCreate_u24lambda_u246);
        RecyclerView recyclerView2 = this.recyclerMessages;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerMessages");
            recyclerView2 = null;
        }
        recyclerView2.setAdapter(new MessageAdapter(new Function1<Message, Unit>() { // from class: com.meharenterprises.originsms.ui.compose.ComposeActivity$onCreate$6
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Message message) {
                invoke2(message);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Message it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
            }
        }, null, null, null, 14, null));
        this.contactPickerAdapter = new ContactPickerAdapter(new Function1<ContactsHelper.PickableContact, Unit>() { // from class: com.meharenterprises.originsms.ui.compose.ComposeActivity$onCreate$7
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ContactsHelper.PickableContact pickableContact) {
                invoke2(pickableContact);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ContactsHelper.PickableContact contact) {
                EditText editText4;
                EditText editText5;
                Intrinsics.checkNotNullParameter(contact, "contact");
                ComposeActivity.this.selectedAddress = ContactsHelper.INSTANCE.normalize(contact.getPhoneNumber());
                ComposeActivity.this.isProgrammaticTextChange = true;
                editText4 = ComposeActivity.this.editRecipient;
                if (editText4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("editRecipient");
                    editText4 = null;
                }
                editText4.setText(contact.getDisplayName());
                ComposeActivity.this.isProgrammaticTextChange = false;
                editText5 = ComposeActivity.this.editRecipient;
                if (editText5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("editRecipient");
                    editText5 = null;
                }
                editText5.clearFocus();
                BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(ComposeActivity.this), null, null, new AnonymousClass1(ComposeActivity.this, null), 3, null);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            /* compiled from: ComposeActivity.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
            @DebugMetadata(c = "com.meharenterprises.originsms.ui.compose.ComposeActivity$onCreate$7$1", f = "ComposeActivity.kt", i = {1, 2, 3}, l = {107, 116, 133, 140}, m = "invokeSuspend", n = {"existingThreadId", "existingThreadId", "existingThreadId"}, s = {"J$0", "J$0", "J$0"})
            /* renamed from: com.meharenterprises.originsms.ui.compose.ComposeActivity$onCreate$7$1, reason: invalid class name */
            /* loaded from: classes7.dex */
            public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                long J$0;
                int label;
                final /* synthetic */ ComposeActivity this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass1(ComposeActivity composeActivity, Continuation<? super AnonymousClass1> continuation) {
                    super(2, continuation);
                    this.this$0 = composeActivity;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    return new AnonymousClass1(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0007. Please report as an issue. */
                /* JADX WARN: Removed duplicated region for block: B:16:0x00b4  */
                /* JADX WARN: Removed duplicated region for block: B:20:0x0116  */
                /* JADX WARN: Removed duplicated region for block: B:24:0x0088  */
                /* JADX WARN: Removed duplicated region for block: B:33:0x007f A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:9:0x0103  */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r11) {
                    /*
                        Method dump skipped, instructions count: 306
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.compose.ComposeActivity$onCreate$7.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }
        });
        RecyclerView recyclerView3 = this.recyclerContactPicker;
        if (recyclerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerContactPicker");
            recyclerView3 = null;
        }
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView4 = this.recyclerContactPicker;
        if (recyclerView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerContactPicker");
            recyclerView4 = null;
        }
        ContactPickerAdapter contactPickerAdapter = this.contactPickerAdapter;
        if (contactPickerAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("contactPickerAdapter");
            contactPickerAdapter = null;
        }
        recyclerView4.setAdapter(contactPickerAdapter);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new ComposeActivity$onCreate$8(this, null), 3, null);
        EditText editText4 = this.editRecipient;
        if (editText4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editRecipient");
            editText4 = null;
        }
        editText4.addTextChangedListener(new TextWatcher() { // from class: com.meharenterprises.originsms.ui.compose.ComposeActivity$onCreate$9
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                boolean z;
                String str;
                ContactPickerAdapter contactPickerAdapter2;
                RecyclerView recyclerView5;
                z = ComposeActivity.this.isProgrammaticTextChange;
                if (z) {
                    return;
                }
                RecyclerView recyclerView6 = null;
                String query = s != null ? s.toString() : null;
                if (query == null) {
                    query = "";
                }
                ImageView imageView = (ImageView) ComposeActivity.this.findViewById(R.id.btnClearRecipient);
                if (imageView != null) {
                    imageView.setVisibility(query.length() > 0 ? 0 : 8);
                }
                str = ComposeActivity.this.selectedAddress;
                if (str != null) {
                    ComposeActivity.this.selectedAddress = null;
                }
                contactPickerAdapter2 = ComposeActivity.this.contactPickerAdapter;
                if (contactPickerAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("contactPickerAdapter");
                    contactPickerAdapter2 = null;
                }
                contactPickerAdapter2.filter(query);
                recyclerView5 = ComposeActivity.this.recyclerMessages;
                if (recyclerView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("recyclerMessages");
                } else {
                    recyclerView6 = recyclerView5;
                }
                if (recyclerView6.getVisibility() == 0 && (!StringsKt.isBlank(query))) {
                    ComposeActivity.this.showContactMode();
                }
            }
        });
        EditText editText5 = this.editRecipient;
        if (editText5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editRecipient");
            editText5 = null;
        }
        editText5.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.meharenterprises.originsms.ui.compose.ComposeActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                ComposeActivity.onCreate$lambda$7(ComposeActivity.this, view, z);
            }
        });
        EditText editText6 = this.editMessage;
        if (editText6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
        } else {
            editText2 = editText6;
        }
        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.meharenterprises.originsms.ui.compose.ComposeActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                ComposeActivity.onCreate$lambda$8(ComposeActivity.this, view, z);
            }
        });
        ((ImageButton) findViewById(R.id.btnSend)).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.compose.ComposeActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ComposeActivity.onCreate$lambda$9(ComposeActivity.this, view);
            }
        });
        ((ImageButton) findViewById(R.id.btnAttach)).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.compose.ComposeActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ComposeActivity.onCreate$lambda$10(ComposeActivity.this, view);
            }
        });
        setupEmojiPicker();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(ComposeActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(ComposeActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EditText editText = this$0.editRecipient;
        EditText editText2 = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editRecipient");
            editText = null;
        }
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) this$0.getSystemService(InputMethodManager.class);
        if (imm != null) {
            EditText editText3 = this$0.editRecipient;
            if (editText3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("editRecipient");
            } else {
                editText2 = editText3;
            }
            imm.showSoftInput(editText2, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$4(ComposeActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EditText editText = this$0.editRecipient;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editRecipient");
            editText = null;
        }
        editText.setText("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$7(ComposeActivity this$0, View view, boolean hasFocus) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (hasFocus) {
            this$0.showContactMode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$8(ComposeActivity this$0, View view, boolean hasFocus) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (hasFocus && this$0.selectedAddress == null) {
            EditText editText = this$0.editRecipient;
            if (editText == null) {
                Intrinsics.throwUninitializedPropertyAccessException("editRecipient");
                editText = null;
            }
            Editable text = editText.getText();
            String obj = text != null ? text.toString() : null;
            if (obj == null) {
                obj = "";
            }
            String raw = StringsKt.trim((CharSequence) obj).toString();
            if (!StringsKt.isBlank(raw)) {
                this$0.selectedAddress = ContactsHelper.INSTANCE.normalize(raw);
                this$0.showMessageMode();
            }
        }
        if (!hasFocus || !this$0.emojiPickerVisible) {
            return;
        }
        this$0.hideEmojiPicker();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$9(ComposeActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.attemptSend();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$10(ComposeActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.attachmentPickerLauncher.launch(PickVisualMediaRequestKt.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE));
    }

    private final void setupEmojiPicker() {
        ImageButton btnEmoji = (ImageButton) findViewById(R.id.btnEmoji);
        EmojiPickerView emojiPickerView = this.emojiPicker;
        if (emojiPickerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emojiPicker");
            emojiPickerView = null;
        }
        emojiPickerView.setOnEmojiPickedListener(new Consumer() { // from class: com.meharenterprises.originsms.ui.compose.ComposeActivity$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ComposeActivity.setupEmojiPicker$lambda$11(ComposeActivity.this, (EmojiViewItem) obj);
            }
        });
        btnEmoji.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.compose.ComposeActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ComposeActivity.setupEmojiPicker$lambda$12(ComposeActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupEmojiPicker$lambda$11(ComposeActivity this$0, EmojiViewItem emojiViewItem) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(emojiViewItem, "emojiViewItem");
        String emoji = emojiViewItem.getEmoji();
        EditText editText = this$0.editMessage;
        EditText editText2 = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
            editText = null;
        }
        int start = RangesKt.coerceAtLeast(editText.getSelectionStart(), 0);
        EditText editText3 = this$0.editMessage;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
            editText3 = null;
        }
        int end = RangesKt.coerceAtLeast(editText3.getSelectionEnd(), 0);
        EditText editText4 = this$0.editMessage;
        if (editText4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
        } else {
            editText2 = editText4;
        }
        Editable text = editText2.getText();
        if (text != null) {
            text.replace(Math.min(start, end), Math.max(start, end), emoji);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupEmojiPicker$lambda$12(ComposeActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.emojiPickerVisible) {
            this$0.hideEmojiPicker();
        } else {
            this$0.showEmojiPicker();
        }
    }

    private final void showEmojiPicker() {
        this.emojiPickerVisible = true;
        EmojiPickerView emojiPickerView = this.emojiPicker;
        EditText editText = null;
        if (emojiPickerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emojiPicker");
            emojiPickerView = null;
        }
        emojiPickerView.setVisibility(0);
        InputMethodManager imm = (InputMethodManager) getSystemService(InputMethodManager.class);
        EditText editText2 = this.editMessage;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
        } else {
            editText = editText2;
        }
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private final void hideEmojiPicker() {
        this.emojiPickerVisible = false;
        EmojiPickerView emojiPickerView = this.emojiPicker;
        if (emojiPickerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emojiPicker");
            emojiPickerView = null;
        }
        emojiPickerView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showContactMode() {
        RecyclerView recyclerView = this.recyclerContactPicker;
        RecyclerView recyclerView2 = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerContactPicker");
            recyclerView = null;
        }
        recyclerView.setVisibility(0);
        RecyclerView recyclerView3 = this.recyclerMessages;
        if (recyclerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerMessages");
        } else {
            recyclerView2 = recyclerView3;
        }
        recyclerView2.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showMessageMode() {
        RecyclerView recyclerView = this.recyclerContactPicker;
        EditText editText = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerContactPicker");
            recyclerView = null;
        }
        recyclerView.setVisibility(8);
        RecyclerView recyclerView2 = this.recyclerMessages;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerMessages");
            recyclerView2 = null;
        }
        recyclerView2.setVisibility(0);
        EditText editText2 = this.editMessage;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
            editText2 = null;
        }
        editText2.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(InputMethodManager.class);
        EditText editText3 = this.editMessage;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
        } else {
            editText = editText3;
        }
        imm.showSoftInput(editText, 1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x002f, code lost:
    
        if (r3 == null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void attemptSend() {
        /*
            r10 = this;
            android.widget.EditText r0 = r10.editMessage
            r1 = 0
            if (r0 != 0) goto Lb
            java.lang.String r0 = "editMessage"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            r0 = r1
        Lb:
            android.text.Editable r0 = r0.getText()
            if (r0 == 0) goto L16
            java.lang.String r0 = r0.toString()
            goto L17
        L16:
            r0 = r1
        L17:
            java.lang.String r2 = ""
            if (r0 != 0) goto L1c
            r0 = r2
        L1c:
            java.lang.String r3 = r10.selectedAddress
            if (r3 == 0) goto L31
            r4 = r3
            r5 = 0
            r6 = r4
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6
            boolean r6 = kotlin.text.StringsKt.isBlank(r6)
            r6 = r6 ^ 1
            if (r6 == 0) goto L2e
            goto L2f
        L2e:
            r3 = r1
        L2f:
            if (r3 != 0) goto L51
        L31:
            com.meharenterprises.originsms.core.ContactsHelper$Companion r3 = com.meharenterprises.originsms.core.ContactsHelper.INSTANCE
            android.widget.EditText r4 = r10.editRecipient
            if (r4 != 0) goto L3d
            java.lang.String r4 = "editRecipient"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            r4 = r1
        L3d:
            android.text.Editable r4 = r4.getText()
            if (r4 == 0) goto L48
            java.lang.String r4 = r4.toString()
            goto L49
        L48:
            r4 = r1
        L49:
            if (r4 != 0) goto L4c
            goto L4d
        L4c:
            r2 = r4
        L4d:
            java.lang.String r3 = r3.normalize(r2)
        L51:
            r2 = r3
            r3 = r2
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3
            boolean r3 = kotlin.text.StringsKt.isBlank(r3)
            if (r3 == 0) goto L5c
            return
        L5c:
            r3 = r0
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3
            boolean r3 = kotlin.text.StringsKt.isBlank(r3)
            if (r3 == 0) goto L6e
            java.util.List<android.net.Uri> r3 = r10.pendingAttachments
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L6e
            return
        L6e:
            r3 = r10
            androidx.lifecycle.LifecycleOwner r3 = (androidx.lifecycle.LifecycleOwner) r3
            androidx.lifecycle.LifecycleCoroutineScope r3 = androidx.lifecycle.LifecycleOwnerKt.getLifecycleScope(r3)
            r4 = r3
            kotlinx.coroutines.CoroutineScope r4 = (kotlinx.coroutines.CoroutineScope) r4
            com.meharenterprises.originsms.ui.compose.ComposeActivity$attemptSend$1 r3 = new com.meharenterprises.originsms.ui.compose.ComposeActivity$attemptSend$1
            r3.<init>(r10, r2, r0, r1)
            r7 = r3
            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7
            r8 = 3
            r9 = 0
            r5 = 0
            r6 = 0
            kotlinx.coroutines.BuildersKt.launch$default(r4, r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.compose.ComposeActivity.attemptSend():void");
    }
}
