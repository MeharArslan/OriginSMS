package com.meharenterprises.originsms.ui.thread;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.PickVisualMediaRequestKt;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.util.Consumer;
import androidx.core.view.ViewCompat;
import androidx.emoji2.emojipicker.EmojiPickerView;
import androidx.emoji2.emojipicker.EmojiViewItem;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.meharenterprises.originsms.OriginSmsApp;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.Message;
import com.meharenterprises.originsms.core.MessageBox;
import com.meharenterprises.originsms.core.MessageType;
import com.meharenterprises.originsms.services.NotificationHelper;
import com.meharenterprises.originsms.ui.ChatTheme;
import com.meharenterprises.originsms.ui.ChatThemeManager;
import com.meharenterprises.originsms.ui.StarredMessagesActivity;
import com.meharenterprises.originsms.ui.compose.ComposeActivity;
import com.meharenterprises.originsms.ui.thread.ThreadViewModel;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: ThreadActivity.kt */
@Metadata(d1 = {"\u0000Ê\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b!\u0018\u0000 q2\u00020\u0001:\u0001qB\u0005¢\u0006\u0002\u0010\u0002J \u00101\u001a\u00020\u001f2\u0006\u00102\u001a\u00020\u001f2\u0006\u00103\u001a\u00020\u001f2\u0006\u00104\u001a\u000205H\u0002J\b\u00106\u001a\u00020 H\u0002J\b\u00107\u001a\u00020 H\u0002J\u0010\u00108\u001a\u00020 2\u0006\u00109\u001a\u00020:H\u0002J\u0010\u0010;\u001a\u00020 2\u0006\u0010<\u001a\u00020\u0006H\u0002J\u000e\u0010=\u001a\b\u0012\u0004\u0012\u00020:0>H\u0002J\u0010\u0010?\u001a\u00020 2\u0006\u00109\u001a\u00020:H\u0002J\b\u0010@\u001a\u00020 H\u0002J\u0010\u0010A\u001a\u00020\u00172\u0006\u0010B\u001a\u00020\u001fH\u0002J\b\u0010C\u001a\u00020 H\u0002J$\u0010D\u001a\u00020 2\f\u0010E\u001a\b\u0012\u0004\u0012\u00020:0>2\f\u0010F\u001a\b\u0012\u0004\u0012\u00020G0>H\u0002J\b\u0010H\u001a\u00020 H\u0016J\u0012\u0010I\u001a\u00020 2\b\u0010J\u001a\u0004\u0018\u00010KH\u0014J\u0010\u0010L\u001a\u00020\u00172\u0006\u0010M\u001a\u00020NH\u0016J\u0010\u0010O\u001a\u00020\u00172\u0006\u0010P\u001a\u00020QH\u0016J\b\u0010R\u001a\u00020 H\u0014J\u0010\u0010S\u001a\u00020\u00172\u0006\u0010M\u001a\u00020NH\u0016J\b\u0010T\u001a\u00020 H\u0014J\u0016\u0010U\u001a\u00020 2\f\u0010V\u001a\b\u0012\u0004\u0012\u00020\u000f0>H\u0002J\b\u0010W\u001a\u00020 H\u0002J\b\u0010X\u001a\u00020 H\u0002J\u0018\u0010Y\u001a\u00020 2\u0006\u0010<\u001a\u00020\u00062\u0006\u0010Z\u001a\u00020\u001aH\u0002J\b\u0010[\u001a\u00020 H\u0002J\b\u0010\\\u001a\u00020 H\u0002J\b\u0010]\u001a\u00020 H\u0002J\b\u0010^\u001a\u00020 H\u0002J\b\u0010_\u001a\u00020 H\u0002J\b\u0010`\u001a\u00020 H\u0002J\u0016\u0010a\u001a\u00020 2\f\u0010E\u001a\b\u0012\u0004\u0012\u00020:0>H\u0002J\b\u0010b\u001a\u00020 H\u0002J\u0010\u0010c\u001a\u00020 2\u0006\u0010d\u001a\u00020\u0006H\u0002J\b\u0010e\u001a\u00020 H\u0002J\b\u0010f\u001a\u00020 H\u0002J\b\u0010g\u001a\u00020 H\u0002J\u0010\u0010h\u001a\u00020 2\u0006\u0010d\u001a\u00020\u0006H\u0002J\u0010\u0010i\u001a\u00020 2\u0006\u0010j\u001a\u00020:H\u0002J\b\u0010k\u001a\u00020 H\u0002J\b\u0010l\u001a\u00020 H\u0002J\b\u0010m\u001a\u00020 H\u0002J\u0010\u0010n\u001a\u00020 2\u0006\u00109\u001a\u00020:H\u0002J\u0014\u0010o\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010p\u001a\u000205H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.¢\u0006\u0002\n\u0000R\u001c\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 \u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082.¢\u0006\u0002\n\u0000R&\u0010%\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020(0'0&X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082.¢\u0006\u0002\n\u0000¨\u0006r"}, d2 = {"Lcom/meharenterprises/originsms/ui/thread/ThreadActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/meharenterprises/originsms/ui/thread/MessageAdapter;", "address", "", "attachmentContainer", "Landroid/widget/LinearLayout;", "attachmentPickerLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroidx/activity/result/PickVisualMediaRequest;", "attachmentScroll", "Landroid/widget/HorizontalScrollView;", "cameraLauncher", "Landroid/net/Uri;", "cameraPermissionLauncher", "displayName", "editMessage", "Landroid/widget/EditText;", "emojiPicker", "Landroidx/emoji2/emojipicker/EmojiPickerView;", "emojiPickerVisible", "", "filePickerLauncher", "highlightMessageId", "", "messageSelectionBar", "Landroid/view/View;", "newMessageArrived", "Lkotlin/Function1;", "", "", "openedFromTrash", "pendingCameraUri", "recycler", "Landroidx/recyclerview/widget/RecyclerView;", "scheduledMessages", "", "Lkotlin/Pair;", "Landroid/os/Handler;", "threadId", "toolbar", "Lcom/google/android/material/appbar/MaterialToolbar;", "txtMessageSelectionCount", "Landroid/widget/TextView;", "unreadNewCount", "viewModel", "Lcom/meharenterprises/originsms/ui/thread/ThreadViewModel;", "blendColor", "base", "overlay", "alpha", "", "confirmDeleteThread", "confirmHideChat", "confirmRetry", "message", "Lcom/meharenterprises/originsms/core/Message;", "copyToClipboard", "text", "currentSelectedMessages", "", "forwardMessage", "hideEmojiPicker", "isDarkColor", TypedValues.Custom.S_COLOR, "launchCamera", "mergeAndSubmit", "messages", "scheduled", "Lcom/meharenterprises/originsms/ui/thread/ThreadViewModel$ScheduledEntry;", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onPause", "onPrepareOptionsMenu", "onResume", "renderAttachmentPreviews", "uris", "requestCameraThenLaunch", "restoreDraft", "scheduleMessage", "scheduledAtMillis", "setupComposeBar", "setupEmojiPicker", "setupMessageSelectionBar", "setupRecyclerView", "setupSimIndicator", "setupToolbar", "shareMessages", "showAttachmentSheet", "showDateTimePicker", "messageText", "showEmojiPicker", "showFloatingStarAnimation", "showInChatThemePicker", "showScheduleSendDialog", "showScheduledOptions", "fakeMessage", "toggleMute", "updateMessageSelectionBar", "viewContact", "viewMessageDetails", "dp", "density", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes10.dex */
public final class ThreadActivity extends AppCompatActivity {
    public static final String EXTRA_ADDRESS = "extra_address";
    public static final String EXTRA_DISPLAY_NAME = "extra_display_name";
    public static final String EXTRA_FROM_TRASH = "extra_from_trash";
    public static final String EXTRA_PREFILL_BODY = "extra_prefill_body";
    public static final String EXTRA_THREAD_ID = "extra_thread_id";
    private static final int MAX_ATTACHMENTS = 5;
    private MessageAdapter adapter;
    private LinearLayout attachmentContainer;
    private HorizontalScrollView attachmentScroll;
    private EditText editMessage;
    private EmojiPickerView emojiPicker;
    private boolean emojiPickerVisible;
    private View messageSelectionBar;
    private Function1<? super Integer, Unit> newMessageArrived;
    private boolean openedFromTrash;
    private Uri pendingCameraUri;
    private RecyclerView recycler;
    private MaterialToolbar toolbar;
    private TextView txtMessageSelectionCount;
    private int unreadNewCount;
    private ThreadViewModel viewModel;
    private long threadId = -1;
    private String address = "";
    private String displayName = "";
    private long highlightMessageId = -1;
    private final ActivityResultLauncher<PickVisualMediaRequest> attachmentPickerLauncher = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(5), new ActivityResultCallback() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda2
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            ThreadActivity.attachmentPickerLauncher$lambda$1(ThreadActivity.this, (List) obj);
        }
    });
    private final ActivityResultLauncher<String> filePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetMultipleContents(), new ActivityResultCallback() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda3
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            ThreadActivity.filePickerLauncher$lambda$3(ThreadActivity.this, (List) obj);
        }
    });
    private final ActivityResultLauncher<Uri> cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda4
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            ThreadActivity.cameraLauncher$lambda$5(ThreadActivity.this, ((Boolean) obj).booleanValue());
        }
    });
    private final ActivityResultLauncher<String> cameraPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda5
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            ThreadActivity.cameraPermissionLauncher$lambda$6(ThreadActivity.this, ((Boolean) obj).booleanValue());
        }
    });
    private final Map<String, Pair<Long, Handler>> scheduledMessages = new LinkedHashMap();

    /* JADX INFO: Access modifiers changed from: private */
    public static final void attachmentPickerLauncher$lambda$1(ThreadActivity this$0, List uris) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(uris, "uris");
        List $this$forEach$iv = uris;
        for (Object element$iv : $this$forEach$iv) {
            Uri uri = (Uri) element$iv;
            try {
                this$0.getContentResolver().takePersistableUriPermission(uri, 1);
            } catch (SecurityException e) {
            }
            ThreadViewModel threadViewModel = this$0.viewModel;
            if (threadViewModel == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                threadViewModel = null;
            }
            threadViewModel.addPendingAttachment(uri);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void filePickerLauncher$lambda$3(ThreadActivity this$0, List uris) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(uris, "uris");
        List $this$forEach$iv = uris;
        for (Object element$iv : $this$forEach$iv) {
            Uri uri = (Uri) element$iv;
            try {
                this$0.getContentResolver().takePersistableUriPermission(uri, 1);
            } catch (SecurityException e) {
            }
            ThreadViewModel threadViewModel = this$0.viewModel;
            if (threadViewModel == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                threadViewModel = null;
            }
            threadViewModel.addPendingAttachment(uri);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void cameraLauncher$lambda$5(ThreadActivity this$0, boolean success) {
        Uri it;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (success && (it = this$0.pendingCameraUri) != null) {
            ThreadViewModel threadViewModel = this$0.viewModel;
            if (threadViewModel == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                threadViewModel = null;
            }
            threadViewModel.addPendingAttachment(it);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void cameraPermissionLauncher$lambda$6(ThreadActivity this$0, boolean granted) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (granted) {
            this$0.launchCamera();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        this.threadId = getIntent().getLongExtra("extra_thread_id", -1L);
        String stringExtra = getIntent().getStringExtra("extra_address");
        if (stringExtra == null) {
            stringExtra = "";
        }
        this.address = stringExtra;
        String stringExtra2 = getIntent().getStringExtra("extra_display_name");
        String str = stringExtra2 != null ? stringExtra2 : "";
        if (StringsKt.isBlank(str)) {
            str = this.address;
        }
        this.displayName = str;
        this.openedFromTrash = getIntent().getBooleanExtra(EXTRA_FROM_TRASH, false);
        this.highlightMessageId = getIntent().getLongExtra("HIGHLIGHT_MESSAGE_ID", -1L);
        this.viewModel = (ThreadViewModel) new ViewModelProvider(this).get(ThreadViewModel.class);
        setupToolbar();
        setupMessageSelectionBar();
        setupRecyclerView();
        setupComposeBar();
        setupEmojiPicker();
        setupSimIndicator();
        if (this.openedFromTrash) {
            View findViewById = findViewById(R.id.composeBarRoot);
            if (findViewById != null) {
                findViewById.setVisibility(8);
            }
            View findViewById2 = findViewById(R.id.scheduledMsgBanner);
            if (findViewById2 != null) {
                findViewById2.setVisibility(8);
            }
            TextView trashBanner = (TextView) findViewById(R.id.txtTrashViewBanner);
            if (trashBanner != null) {
                trashBanner.setVisibility(0);
                trashBanner.setText("📂 Trash — Go back to restore or permanently delete");
            }
        }
        ThreadViewModel threadViewModel = this.viewModel;
        EditText editText = null;
        if (threadViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel = null;
        }
        threadViewModel.bind(this.threadId, this.address);
        ThreadViewModel threadViewModel2 = this.viewModel;
        if (threadViewModel2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel2 = null;
        }
        threadViewModel2.getMessages().observe(this, new ThreadActivity$sam$androidx_lifecycle_Observer$0(new Function1<List<? extends Message>, Unit>() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$onCreate$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends Message> list) {
                invoke2((List<Message>) list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<Message> list) {
                ThreadViewModel threadViewModel3;
                ThreadActivity threadActivity = ThreadActivity.this;
                Intrinsics.checkNotNull(list);
                threadViewModel3 = ThreadActivity.this.viewModel;
                if (threadViewModel3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                    threadViewModel3 = null;
                }
                List<ThreadViewModel.ScheduledEntry> value = threadViewModel3.getScheduledEntries().getValue();
                if (value == null) {
                    value = CollectionsKt.emptyList();
                }
                threadActivity.mergeAndSubmit(list, value);
            }
        }));
        ThreadViewModel threadViewModel3 = this.viewModel;
        if (threadViewModel3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel3 = null;
        }
        threadViewModel3.getPendingAttachments().observe(this, new ThreadActivity$sam$androidx_lifecycle_Observer$0(new Function1<List<? extends Uri>, Unit>() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$onCreate$3
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends Uri> list) {
                invoke2(list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<? extends Uri> list) {
                ThreadActivity threadActivity = ThreadActivity.this;
                Intrinsics.checkNotNull(list);
                threadActivity.renderAttachmentPreviews(list);
            }
        }));
        ThreadViewModel threadViewModel4 = this.viewModel;
        if (threadViewModel4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel4 = null;
        }
        threadViewModel4.getScheduledEntries().observe(this, new ThreadActivity$sam$androidx_lifecycle_Observer$0(new Function1<List<? extends ThreadViewModel.ScheduledEntry>, Unit>() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$onCreate$4
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends ThreadViewModel.ScheduledEntry> list) {
                invoke2((List<ThreadViewModel.ScheduledEntry>) list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<ThreadViewModel.ScheduledEntry> list) {
                ThreadViewModel threadViewModel5;
                ThreadActivity threadActivity = ThreadActivity.this;
                threadViewModel5 = ThreadActivity.this.viewModel;
                if (threadViewModel5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                    threadViewModel5 = null;
                }
                List<Message> value = threadViewModel5.getMessages().getValue();
                if (value == null) {
                    value = CollectionsKt.emptyList();
                }
                Intrinsics.checkNotNull(list);
                threadActivity.mergeAndSubmit(value, list);
            }
        }));
        restoreDraft();
        String prefill = getIntent().getStringExtra("extra_prefill_body");
        if (prefill != null) {
            EditText editText2 = this.editMessage;
            if (editText2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("editMessage");
                editText2 = null;
            }
            Editable text = editText2.getText();
            String obj = text != null ? text.toString() : null;
            if (obj == null || StringsKt.isBlank(obj)) {
                EditText editText3 = this.editMessage;
                if (editText3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("editMessage");
                    editText3 = null;
                }
                editText3.setText(prefill);
                EditText editText4 = this.editMessage;
                if (editText4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("editMessage");
                } else {
                    editText = editText4;
                }
                editText.setSelection(prefill.length());
            }
        }
    }

    private final void setupToolbar() {
        View findViewById = findViewById(R.id.toolbar);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.toolbar = (MaterialToolbar) findViewById;
        MaterialToolbar materialToolbar = this.toolbar;
        if (materialToolbar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolbar");
            materialToolbar = null;
        }
        setSupportActionBar(materialToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        MaterialToolbar materialToolbar2 = this.toolbar;
        if (materialToolbar2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolbar");
            materialToolbar2 = null;
        }
        materialToolbar2.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda46
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.setupToolbar$lambda$9(ThreadActivity.this, view);
            }
        });
        MaterialToolbar materialToolbar3 = this.toolbar;
        if (materialToolbar3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolbar");
            materialToolbar3 = null;
        }
        TextView txtTitle = (TextView) materialToolbar3.findViewById(R.id.txtThreadTitle);
        if (txtTitle != null) {
            txtTitle.setText(this.displayName);
        }
        if (txtTitle != null) {
            txtTitle.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda47
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ThreadActivity.setupToolbar$lambda$10(ThreadActivity.this, view);
                }
            });
        }
        ChatTheme theme = ChatThemeManager.INSTANCE.getCurrentTheme(this);
        MaterialToolbar materialToolbar4 = this.toolbar;
        if (materialToolbar4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolbar");
            materialToolbar4 = null;
        }
        materialToolbar4.setBackgroundColor(theme.getAppBarBg());
        getWindow().setStatusBarColor(theme.getStatusBarColor());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerMessages);
        recyclerView.setBackground(ContextCompat.getDrawable(this, theme.getChatBackgroundDrawable()));
        findViewById(android.R.id.content).setBackgroundColor(theme.getAppBackground());
        MaterialToolbar materialToolbar5 = this.toolbar;
        if (materialToolbar5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolbar");
            materialToolbar5 = null;
        }
        ImageView imgAvatar = (ImageView) materialToolbar5.findViewById(R.id.imgThreadAvatar);
        if (imgAvatar != null) {
            imgAvatar.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda48
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ThreadActivity.setupToolbar$lambda$11(ThreadActivity.this, view);
                }
            });
        }
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new ThreadActivity$setupToolbar$4(this, imgAvatar, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupToolbar$lambda$9(ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupToolbar$lambda$10(ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.viewContact();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupToolbar$lambda$11(ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.viewContact();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        getMenuInflater().inflate(R.menu.menu_thread, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new ThreadActivity$onPrepareOptionsMenu$1(this, menu, null), 3, null);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        int itemId = item.getItemId();
        if (itemId == R.id.action_call) {
            Intent dialIntent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + Uri.encode(this.address)));
            startActivity(dialIntent);
            return true;
        }
        if (itemId == R.id.action_starred_messages) {
            Intent $this$onOptionsItemSelected_u24lambda_u2412 = new Intent(this, (Class<?>) StarredMessagesActivity.class);
            if (this.threadId != -1) {
                $this$onOptionsItemSelected_u24lambda_u2412.putExtra("FILTER_THREAD_ID", this.threadId);
            }
            startActivity($this$onOptionsItemSelected_u24lambda_u2412);
            return true;
        }
        if (itemId == R.id.action_change_theme) {
            showInChatThemePicker();
            return true;
        }
        if (itemId == R.id.action_mute_chat) {
            toggleMute();
            return true;
        }
        if (itemId == R.id.action_hide_chat) {
            confirmHideChat();
            return true;
        }
        if (itemId == R.id.action_view_contact) {
            viewContact();
            return true;
        }
        if (itemId == R.id.action_archive_chat) {
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new ThreadActivity$onOptionsItemSelected$2(this, null), 3, null);
            return true;
        }
        if (itemId == R.id.action_block_number) {
            new AlertDialog.Builder(this).setTitle(R.string.action_block_number).setMessage(this.address).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda30
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    ThreadActivity.onOptionsItemSelected$lambda$13(ThreadActivity.this, dialogInterface, i);
                }
            }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
            return true;
        }
        if (itemId == R.id.action_delete_thread) {
            confirmDeleteThread();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onOptionsItemSelected$lambda$13(ThreadActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new ThreadActivity$onOptionsItemSelected$3$1(this$0, null), 3, null);
    }

    private final void toggleMute() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new ThreadActivity$toggleMute$1(this, null), 3, null);
    }

    private final void confirmHideChat() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new ThreadActivity$confirmHideChat$1(this, null), 3, null);
    }

    private final void viewContact() {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(this.address));
        String[] projection = {"_id", "lookup"};
        Cursor query = getContentResolver().query(uri, projection, null, null, null);
        if (query != null) {
            Cursor cursor = query;
            try {
                Cursor cursor2 = cursor;
                if (cursor2.moveToFirst()) {
                    int idIdx = cursor2.getColumnIndex("_id");
                    int keyIdx = cursor2.getColumnIndex("lookup");
                    if (idIdx >= 0 && keyIdx >= 0) {
                        long contactId = cursor2.getLong(idIdx);
                        String lookupKey = cursor2.getString(keyIdx);
                        Uri contactUri = ContactsContract.Contacts.getLookupUri(contactId, lookupKey);
                        startActivity(new Intent("android.intent.action.VIEW", contactUri));
                        CloseableKt.closeFinally(cursor, null);
                        return;
                    }
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(cursor, null);
            } finally {
            }
        }
        Intent addIntent = new Intent("android.intent.action.INSERT");
        addIntent.setType("vnd.android.cursor.dir/raw_contact");
        addIntent.putExtra("phone", this.address);
        startActivity(addIntent);
    }

    private final void confirmDeleteThread() {
        new AlertDialog.Builder(this).setTitle(R.string.menu_delete_chat).setMessage(this.displayName).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ThreadActivity.confirmDeleteThread$lambda$16(ThreadActivity.this, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmDeleteThread$lambda$16(ThreadActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new ThreadActivity$confirmDeleteThread$1$1(this$0, null), 3, null);
    }

    private final void setupMessageSelectionBar() {
        View findViewById = findViewById(R.id.messageSelectionBar);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.messageSelectionBar = findViewById;
        View findViewById2 = findViewById(R.id.txtMessageSelectionCount);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
        this.txtMessageSelectionCount = (TextView) findViewById2;
        findViewById(R.id.btnCloseMessageSelection).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda35
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.setupMessageSelectionBar$lambda$17(ThreadActivity.this, view);
            }
        });
        findViewById(R.id.btnMessageCopy).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda36
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.setupMessageSelectionBar$lambda$18(ThreadActivity.this, view);
            }
        });
        findViewById(R.id.btnMessageStar).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda37
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.setupMessageSelectionBar$lambda$21(ThreadActivity.this, view);
            }
        });
        findViewById(R.id.btnMessageDelete).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda38
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.setupMessageSelectionBar$lambda$23(ThreadActivity.this, view);
            }
        });
        findViewById(R.id.btnMessageMore).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda39
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.setupMessageSelectionBar$lambda$25(ThreadActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupMessageSelectionBar$lambda$17(ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MessageAdapter messageAdapter = this$0.adapter;
        if (messageAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            messageAdapter = null;
        }
        messageAdapter.clearSelection();
        this$0.updateMessageSelectionBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupMessageSelectionBar$lambda$18(ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        List selected = this$0.currentSelectedMessages();
        String combined = CollectionsKt.joinToString$default(selected, "\n", null, null, 0, null, new Function1<Message, CharSequence>() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$setupMessageSelectionBar$2$combined$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(Message it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return it2.getBody();
            }
        }, 30, null);
        this$0.copyToClipboard(combined);
        MessageAdapter messageAdapter = this$0.adapter;
        if (messageAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            messageAdapter = null;
        }
        messageAdapter.clearSelection();
        this$0.updateMessageSelectionBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupMessageSelectionBar$lambda$21(ThreadActivity this$0, View view) {
        MessageAdapter messageAdapter;
        String str;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        List<Message> currentSelectedMessages = this$0.currentSelectedMessages();
        List<Message> list = currentSelectedMessages;
        char c = 1;
        c = 1;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (!((Message) it.next()).isStarred()) {
                    c = 0;
                    break;
                }
            }
        }
        char c2 = c;
        Iterator<T> it2 = currentSelectedMessages.iterator();
        while (true) {
            messageAdapter = null;
            ThreadViewModel threadViewModel = null;
            if (!it2.hasNext()) {
                break;
            }
            Message message = (Message) it2.next();
            ThreadViewModel threadViewModel2 = this$0.viewModel;
            if (threadViewModel2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            } else {
                threadViewModel = threadViewModel2;
            }
            threadViewModel.toggleStar(message);
        }
        view.startAnimation(AnimationUtils.loadAnimation(this$0, R.anim.star_pop));
        if (c2 == null) {
            this$0.showFloatingStarAnimation();
        }
        MessageAdapter messageAdapter2 = this$0.adapter;
        if (messageAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            messageAdapter = messageAdapter2;
        }
        messageAdapter.clearSelection();
        this$0.updateMessageSelectionBar();
        ThreadActivity threadActivity = this$0;
        if (c2 != null) {
            str = "Unstarred " + currentSelectedMessages.size() + " message(s)";
        } else {
            str = "⭐ Starred!";
        }
        Toast.makeText(threadActivity, str, 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupMessageSelectionBar$lambda$23(final ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MessageAdapter messageAdapter = this$0.adapter;
        if (messageAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            messageAdapter = null;
        }
        final Set ids = messageAdapter.getSelectedIds();
        int count = ids.size();
        new AlertDialog.Builder(this$0).setTitle("Delete " + (count == 1 ? "this message" : count + " messages") + "?").setMessage("This action cannot be undone.").setPositiveButton("Delete", new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda40
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ThreadActivity.setupMessageSelectionBar$lambda$23$lambda$22(ThreadActivity.this, ids, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupMessageSelectionBar$lambda$23$lambda$22(ThreadActivity this$0, Set ids, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(ids, "$ids");
        ThreadViewModel threadViewModel = this$0.viewModel;
        MessageAdapter messageAdapter = null;
        if (threadViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel = null;
        }
        threadViewModel.deleteMessages(ids);
        MessageAdapter messageAdapter2 = this$0.adapter;
        if (messageAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            messageAdapter = messageAdapter2;
        }
        messageAdapter.clearSelection();
        this$0.updateMessageSelectionBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupMessageSelectionBar$lambda$25(final ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        final List selected = this$0.currentSelectedMessages();
        if (selected.isEmpty()) {
            return;
        }
        String[] options = {this$0.getString(R.string.action_forward), this$0.getString(R.string.action_share), this$0.getString(R.string.action_view_details)};
        new AlertDialog.Builder(this$0).setItems(options, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda32
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ThreadActivity.setupMessageSelectionBar$lambda$25$lambda$24(selected, this$0, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupMessageSelectionBar$lambda$25$lambda$24(List selected, ThreadActivity this$0, DialogInterface dialogInterface, int index) {
        Intrinsics.checkNotNullParameter(selected, "$selected");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        switch (index) {
            case 0:
                if (selected.size() == 1) {
                    this$0.forwardMessage((Message) CollectionsKt.first(selected));
                    return;
                }
                return;
            case 1:
                this$0.shareMessages(selected);
                return;
            case 2:
                this$0.viewMessageDetails((Message) CollectionsKt.first(selected));
                return;
            default:
                return;
        }
    }

    private final List<Message> currentSelectedMessages() {
        MessageAdapter messageAdapter = this.adapter;
        ThreadViewModel threadViewModel = null;
        if (messageAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            messageAdapter = null;
        }
        Set ids = messageAdapter.getSelectedIds();
        ThreadViewModel threadViewModel2 = this.viewModel;
        if (threadViewModel2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
        } else {
            threadViewModel = threadViewModel2;
        }
        Iterable iterable = (List) threadViewModel.getMessages().getValue();
        if (iterable == null) {
            iterable = CollectionsKt.emptyList();
        }
        Iterable $this$filter$iv = iterable;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Message it = (Message) element$iv$iv;
            if (ids.contains(Long.valueOf(it.getId()))) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateMessageSelectionBar() {
        MessageAdapter messageAdapter = this.adapter;
        MaterialToolbar materialToolbar = null;
        TextView textView = null;
        if (messageAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            messageAdapter = null;
        }
        int selectedCount = messageAdapter.getSelectedCount();
        if (selectedCount > 0) {
            MaterialToolbar materialToolbar2 = this.toolbar;
            if (materialToolbar2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("toolbar");
                materialToolbar2 = null;
            }
            materialToolbar2.setVisibility(8);
            View view = this.messageSelectionBar;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("messageSelectionBar");
                view = null;
            }
            view.setVisibility(0);
            TextView textView2 = this.txtMessageSelectionCount;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("txtMessageSelectionCount");
            } else {
                textView = textView2;
            }
            textView.setText(String.valueOf(selectedCount));
            return;
        }
        View view2 = this.messageSelectionBar;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("messageSelectionBar");
            view2 = null;
        }
        view2.setVisibility(8);
        MaterialToolbar materialToolbar3 = this.toolbar;
        if (materialToolbar3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolbar");
        } else {
            materialToolbar = materialToolbar3;
        }
        materialToolbar.setVisibility(0);
    }

    private final void showInChatThemePicker() {
        ViewGroup viewGroup;
        List themes = ChatThemeManager.INSTANCE.getThemes();
        final int currentIdx = ChatThemeManager.INSTANCE.getCurrentIndex(this);
        int dp = (int) getResources().getDisplayMetrics().density;
        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(2);
        grid.setPadding(dp * 8, dp * 8, dp * 8, dp * 8);
        AlertDialog.Builder title = new AlertDialog.Builder(this).setTitle("Chat Theme");
        ScrollView $this$showInChatThemePicker_u24lambda_u2428 = new ScrollView(this);
        $this$showInChatThemePicker_u24lambda_u2428.addView(grid);
        ViewGroup viewGroup2 = null;
        final AlertDialog dialog = title.setView($this$showInChatThemePicker_u24lambda_u2428).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).create();
        Intrinsics.checkNotNullExpressionValue(dialog, "create(...)");
        List $this$forEachIndexed$iv = themes;
        int $i$f$forEachIndexed = 0;
        final int index$iv = 0;
        Iterator it = $this$forEachIndexed$iv.iterator();
        while (it.hasNext()) {
            Object item$iv = it.next();
            int index$iv2 = index$iv + 1;
            if (index$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ChatTheme theme = (ChatTheme) item$iv;
            List themes2 = themes;
            Iterable $this$forEachIndexed$iv2 = $this$forEachIndexed$iv;
            View card = LayoutInflater.from(this).inflate(R.layout.item_theme_card, viewGroup2, false);
            GridLayout.LayoutParams $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2429 = new GridLayout.LayoutParams();
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2429.width = 0;
            int $i$f$forEachIndexed2 = $i$f$forEachIndexed;
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2429.columnSpec = GridLayout.spec(index$iv % 2, 1.0f);
            Iterator it2 = it;
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2429.setMargins(dp * 6, dp * 6, dp * 6, dp * 6);
            card.setLayoutParams($this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2429);
            card.findViewById(R.id.cardBackground).setBackgroundColor(theme.getAppBackground());
            card.findViewById(R.id.miniToolbar).setBackgroundColor(theme.getAppBarBg());
            card.findViewById(R.id.miniInputBar).setBackgroundColor(theme.getAppBarBg());
            float cr = theme.getBubbleCornerRadius();
            TextView $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2431 = (TextView) card.findViewById(R.id.bubbleSent);
            GradientDrawable $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2431_u24lambda_u2430 = new GradientDrawable();
            int dp2 = dp;
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2431_u24lambda_u2430.setColor(theme.getOutgoingBubble());
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2431_u24lambda_u2430.setCornerRadius(cr);
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2431.setBackground($this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2431_u24lambda_u2430);
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2431.setTextColor(theme.getOutgoingTextColor());
            TextView $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2433 = (TextView) card.findViewById(R.id.bubbleReceived);
            GradientDrawable $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2433_u24lambda_u2432 = new GradientDrawable();
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2433_u24lambda_u2432.setColor(theme.getIncomingBubble());
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2433_u24lambda_u2432.setCornerRadius(cr);
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2433.setBackground($this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2433_u24lambda_u2432);
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2433.setTextColor(theme.getIncomingTextColor());
            TextView $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2435 = (TextView) card.findViewById(R.id.bubbleReceived2);
            GradientDrawable $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2435_u24lambda_u2434 = new GradientDrawable();
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2435_u24lambda_u2434.setColor(theme.getIncomingBubble());
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2435_u24lambda_u2434.setCornerRadius(cr);
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2435.setBackground($this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2435_u24lambda_u2434);
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2435.setTextColor(theme.getIncomingTextColor());
            View findViewById = card.findViewById(R.id.miniSendBtn);
            GradientDrawable $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2436 = new GradientDrawable();
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2436.setShape(1);
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2436.setColor(theme.getAccentColor());
            findViewById.setBackground($this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2436);
            TextView $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2437 = (TextView) card.findViewById(R.id.txtThemeCardName);
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2437.setText(theme.getName());
            $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2437.setTextColor(index$iv == currentIdx ? theme.getAccentColor() : -7829368);
            if (index$iv == currentIdx) {
                viewGroup = null;
                $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2437.setTypeface(null, 1);
            } else {
                viewGroup = null;
            }
            card.findViewById(R.id.txtThemeSelected).setVisibility(index$iv == currentIdx ? 0 : 8);
            if (index$iv == currentIdx) {
                GradientDrawable $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2438 = new GradientDrawable();
                $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2438.setColor(0);
                $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2438.setStroke((int) (3 * getResources().getDisplayMetrics().density), theme.getAccentColor());
                $this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2438.setCornerRadius(14 * getResources().getDisplayMetrics().density);
                card.setForeground($this$showInChatThemePicker_u24lambda_u2440_u24lambda_u2438);
            }
            card.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda27
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ThreadActivity.showInChatThemePicker$lambda$40$lambda$39(ThreadActivity.this, index$iv, dialog, view);
                }
            });
            grid.addView(card);
            viewGroup2 = viewGroup;
            index$iv = index$iv2;
            themes = themes2;
            $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
            $i$f$forEachIndexed = $i$f$forEachIndexed2;
            it = it2;
            dp = dp2;
        }
        dialog.show();
        grid.post(new Runnable() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                ThreadActivity.showInChatThemePicker$lambda$42(ThreadActivity.this, currentIdx, dialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showInChatThemePicker$lambda$40$lambda$39(ThreadActivity this$0, int $i, AlertDialog dialog, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(dialog, "$dialog");
        ChatThemeManager.INSTANCE.applyTheme(this$0, $i);
        dialog.dismiss();
        this$0.recreate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showInChatThemePicker$lambda$42(ThreadActivity this$0, int $currentIdx, AlertDialog dialog) {
        View decorView;
        ScrollView showInChatThemePicker$lambda$42$findScrollView;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(dialog, "$dialog");
        int cardHeight = (int) (256 * this$0.getResources().getDisplayMetrics().density);
        int row = $currentIdx / 2;
        int target = row * cardHeight;
        Window window = dialog.getWindow();
        if (window == null || (decorView = window.getDecorView()) == null || (showInChatThemePicker$lambda$42$findScrollView = showInChatThemePicker$lambda$42$findScrollView(decorView)) == null) {
            return;
        }
        showInChatThemePicker$lambda$42$findScrollView.scrollTo(0, target);
    }

    private static final ScrollView showInChatThemePicker$lambda$42$findScrollView(View $this$showInChatThemePicker_u24lambda_u2442_u24findScrollView) {
        if ($this$showInChatThemePicker_u24lambda_u2442_u24findScrollView instanceof ScrollView) {
            return (ScrollView) $this$showInChatThemePicker_u24lambda_u2442_u24findScrollView;
        }
        if ($this$showInChatThemePicker_u24lambda_u2442_u24findScrollView instanceof ViewGroup) {
            int childCount = ((ViewGroup) $this$showInChatThemePicker_u24lambda_u2442_u24findScrollView).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = ((ViewGroup) $this$showInChatThemePicker_u24lambda_u2442_u24findScrollView).getChildAt(i);
                Intrinsics.checkNotNullExpressionValue(childAt, "getChildAt(...)");
                ScrollView it = showInChatThemePicker$lambda$42$findScrollView(childAt);
                if (it != null) {
                    return it;
                }
            }
            return null;
        }
        return null;
    }

    private final int dp(int $this$dp, float density) {
        return (int) ($this$dp * density);
    }

    private final boolean isDarkColor(int color) {
        double r = ((color >> 16) & 255) / 255.0d;
        double g = ((color >> 8) & 255) / 255.0d;
        double b = (color & 255) / 255.0d;
        return ((0.2126d * r) + (0.7152d * g)) + (0.0722d * b) < 0.5d;
    }

    private final int blendColor(int base, int overlay, float alpha) {
        float a = RangesKt.coerceIn(alpha, 0.0f, 1.0f);
        float f = 1;
        int r = (int) ((((base >> 16) & 255) * (f - a)) + (((overlay >> 16) & 255) * a));
        int g = (int) ((((base >> 8) & 255) * (f - a)) + (((overlay >> 8) & 255) * a));
        int b = (int) (((base & 255) * (f - a)) + ((overlay & 255) * a));
        return (-16777216) | (r << 16) | (g << 8) | b;
    }

    private final void shareMessages(List<Message> messages) {
        String text = CollectionsKt.joinToString$default(messages, "\n", null, null, 0, null, new Function1<Message, CharSequence>() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$shareMessages$text$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(Message it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getBody();
            }
        }, 30, null);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", text);
        startActivity(Intent.createChooser(intent, getString(R.string.action_share)));
    }

    private final void viewMessageDetails(Message message) {
        String time = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a", Locale.getDefault()).format(new Date(message.getDateMillis()));
        new AlertDialog.Builder(this).setTitle(R.string.action_view_details).setMessage("From: " + this.address + "\nTo: (me)\nDate: " + time + "\nType: SMS").setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).show();
    }

    private final void forwardMessage(Message message) {
        Intent intent = new Intent(this, (Class<?>) ComposeActivity.class);
        intent.putExtra("extra_prefill_body", message.getBody());
        startActivity(intent);
    }

    private final void setupRecyclerView() {
        View findViewById = findViewById(R.id.recyclerMessages);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.recycler = (RecyclerView) findViewById;
        this.adapter = new MessageAdapter(new Function1<Message, Unit>() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$setupRecyclerView$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Message message) {
                invoke2(message);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Message it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ThreadActivity.this.updateMessageSelectionBar();
            }
        }, new Function1<Message, Unit>() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$setupRecyclerView$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Message message) {
                invoke2(message);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Message it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ThreadActivity.this.updateMessageSelectionBar();
            }
        }, new Function1<Message, Unit>() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$setupRecyclerView$3
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Message message) {
                invoke2(message);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Message message) {
                Intrinsics.checkNotNullParameter(message, "message");
                ThreadActivity.this.confirmRetry(message);
            }
        }, new Function1<Message, Unit>() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$setupRecyclerView$4
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Message message) {
                invoke2(message);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Message message) {
                Intrinsics.checkNotNullParameter(message, "message");
                ThreadActivity.this.showScheduledOptions(message);
            }
        });
        LinearLayoutManager $this$setupRecyclerView_u24lambda_u2445 = new LinearLayoutManager(this);
        $this$setupRecyclerView_u24lambda_u2445.setStackFromEnd(true);
        RecyclerView recyclerView = this.recycler;
        RecyclerView recyclerView2 = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
            recyclerView = null;
        }
        recyclerView.setLayoutManager($this$setupRecyclerView_u24lambda_u2445);
        RecyclerView recyclerView3 = this.recycler;
        if (recyclerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
            recyclerView3 = null;
        }
        MessageAdapter messageAdapter = this.adapter;
        if (messageAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            messageAdapter = null;
        }
        recyclerView3.setAdapter(messageAdapter);
        if (!ChatThemeManager.INSTANCE.isAnimationsEnabled(this)) {
            RecyclerView recyclerView4 = this.recycler;
            if (recyclerView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recycler");
                recyclerView4 = null;
            }
            recyclerView4.setItemAnimator(null);
        }
        this.unreadNewCount = 0;
        final View fabWrapper = findViewById(R.id.fabScrollDownWrapper);
        FloatingActionButton fabDown = (FloatingActionButton) findViewById(R.id.fabScrollDown);
        final TextView txtNewCount = (TextView) findViewById(R.id.txtNewMessageCount);
        RecyclerView recyclerView5 = this.recycler;
        if (recyclerView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
            recyclerView5 = null;
        }
        recyclerView5.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$setupRecyclerView$5
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                MessageAdapter messageAdapter2;
                MessageAdapter messageAdapter3;
                Intrinsics.checkNotNullParameter(rv, "rv");
                RecyclerView.LayoutManager layoutManager = rv.getLayoutManager();
                MessageAdapter messageAdapter4 = null;
                LinearLayoutManager lm2 = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
                if (lm2 == null) {
                    return;
                }
                int findLastCompletelyVisibleItemPosition = lm2.findLastCompletelyVisibleItemPosition();
                messageAdapter2 = ThreadActivity.this.adapter;
                if (messageAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    messageAdapter2 = null;
                }
                boolean atBottom = findLastCompletelyVisibleItemPosition >= messageAdapter2.getItemCount() + (-2);
                if (atBottom) {
                    ThreadActivity.this.unreadNewCount = 0;
                    ThreadActivity.setupRecyclerView$updateNewMsgLabel(ThreadActivity.this, txtNewCount);
                    ThreadActivity.setupRecyclerView$hideFab(fabWrapper);
                    return;
                }
                messageAdapter3 = ThreadActivity.this.adapter;
                if (messageAdapter3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                } else {
                    messageAdapter4 = messageAdapter3;
                }
                if (messageAdapter4.getItemCount() > 0) {
                    ThreadActivity.setupRecyclerView$showFab(fabWrapper);
                }
            }
        });
        if (fabDown != null) {
            fabDown.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda24
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ThreadActivity.setupRecyclerView$lambda$47(ThreadActivity.this, txtNewCount, view);
                }
            });
        }
        if (fabWrapper != null) {
            fabWrapper.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda25
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ThreadActivity.setupRecyclerView$lambda$48(ThreadActivity.this, txtNewCount, view);
                }
            });
        }
        this.newMessageArrived = new Function1<Integer, Unit>() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$setupRecyclerView$8
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke(num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int delta) {
                RecyclerView recyclerView6;
                MessageAdapter messageAdapter2;
                int i;
                recyclerView6 = ThreadActivity.this.recycler;
                MessageAdapter messageAdapter3 = null;
                if (recyclerView6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("recycler");
                    recyclerView6 = null;
                }
                RecyclerView.LayoutManager layoutManager = recyclerView6.getLayoutManager();
                LinearLayoutManager lm2 = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
                int findLastCompletelyVisibleItemPosition = lm2 != null ? lm2.findLastCompletelyVisibleItemPosition() : 0;
                messageAdapter2 = ThreadActivity.this.adapter;
                if (messageAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                } else {
                    messageAdapter3 = messageAdapter2;
                }
                boolean atBottom = findLastCompletelyVisibleItemPosition >= messageAdapter3.getItemCount() + (-2);
                if (!atBottom) {
                    ThreadActivity threadActivity = ThreadActivity.this;
                    i = threadActivity.unreadNewCount;
                    threadActivity.unreadNewCount = i + delta;
                    ThreadActivity.setupRecyclerView$updateNewMsgLabel(ThreadActivity.this, txtNewCount);
                    ThreadActivity.setupRecyclerView$showFab(fabWrapper);
                }
            }
        };
        if (ChatThemeManager.INSTANCE.isPinchZoomEnabled(this)) {
            final Ref.FloatRef scaleFactor = new Ref.FloatRef();
            scaleFactor.element = 1.0f;
            final ScaleGestureDetector scaleDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$setupRecyclerView$scaleDetector$1
                @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
                public boolean onScale(ScaleGestureDetector detector) {
                    MessageAdapter messageAdapter2;
                    Intrinsics.checkNotNullParameter(detector, "detector");
                    Ref.FloatRef.this.element *= detector.getScaleFactor();
                    Ref.FloatRef.this.element = RangesKt.coerceIn(Ref.FloatRef.this.element, 0.7f, 1.8f);
                    messageAdapter2 = this.adapter;
                    if (messageAdapter2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                        messageAdapter2 = null;
                    }
                    messageAdapter2.setTextScale(Ref.FloatRef.this.element);
                    return true;
                }
            });
            RecyclerView recyclerView6 = this.recycler;
            if (recyclerView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recycler");
            } else {
                recyclerView2 = recyclerView6;
            }
            recyclerView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda26
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    boolean z;
                    z = ThreadActivity.setupRecyclerView$lambda$49(scaleDetector, view, motionEvent);
                    return z;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupRecyclerView$showFab(View fabWrapper) {
        ViewPropertyAnimator animate;
        ViewPropertyAnimator alpha;
        ViewPropertyAnimator duration;
        if (!(fabWrapper != null && fabWrapper.getVisibility() == 0)) {
            if (fabWrapper != null) {
                fabWrapper.setAlpha(0.0f);
            }
            if (fabWrapper != null) {
                fabWrapper.setVisibility(0);
            }
            if (fabWrapper == null || (animate = fabWrapper.animate()) == null || (alpha = animate.alpha(1.0f)) == null || (duration = alpha.setDuration(200L)) == null) {
                return;
            }
            duration.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupRecyclerView$hideFab(final View fabWrapper) {
        ViewPropertyAnimator animate;
        ViewPropertyAnimator alpha;
        ViewPropertyAnimator duration;
        ViewPropertyAnimator withEndAction;
        boolean z = false;
        if (fabWrapper != null && fabWrapper.getVisibility() == 0) {
            z = true;
        }
        if (!z || fabWrapper == null || (animate = fabWrapper.animate()) == null || (alpha = animate.alpha(0.0f)) == null || (duration = alpha.setDuration(150L)) == null || (withEndAction = duration.withEndAction(new Runnable() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ThreadActivity.setupRecyclerView$hideFab$lambda$46(fabWrapper);
            }
        })) == null) {
            return;
        }
        withEndAction.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupRecyclerView$hideFab$lambda$46(View $fabWrapper) {
        if ($fabWrapper == null) {
            return;
        }
        $fabWrapper.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupRecyclerView$updateNewMsgLabel(ThreadActivity this$0, TextView txtNewCount) {
        String label;
        if (this$0.unreadNewCount == 1) {
            label = "1 New message";
        } else if (this$0.unreadNewCount > 1) {
            label = this$0.unreadNewCount + " New messages";
        } else {
            label = null;
        }
        if (label != null) {
            if (txtNewCount != null) {
                txtNewCount.setText(label);
            }
            if (txtNewCount == null) {
                return;
            }
            txtNewCount.setVisibility(0);
            return;
        }
        if (txtNewCount == null) {
            return;
        }
        txtNewCount.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupRecyclerView$lambda$47(ThreadActivity this$0, TextView $txtNewCount, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.unreadNewCount = 0;
        setupRecyclerView$updateNewMsgLabel(this$0, $txtNewCount);
        RecyclerView recyclerView = this$0.recycler;
        MessageAdapter messageAdapter = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
            recyclerView = null;
        }
        MessageAdapter messageAdapter2 = this$0.adapter;
        if (messageAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            messageAdapter = messageAdapter2;
        }
        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupRecyclerView$lambda$48(ThreadActivity this$0, TextView $txtNewCount, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.unreadNewCount = 0;
        setupRecyclerView$updateNewMsgLabel(this$0, $txtNewCount);
        RecyclerView recyclerView = this$0.recycler;
        MessageAdapter messageAdapter = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
            recyclerView = null;
        }
        MessageAdapter messageAdapter2 = this$0.adapter;
        if (messageAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            messageAdapter = messageAdapter2;
        }
        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupRecyclerView$lambda$49(ScaleGestureDetector scaleDetector, View v, MotionEvent event) {
        Intrinsics.checkNotNullParameter(scaleDetector, "$scaleDetector");
        scaleDetector.onTouchEvent(event);
        if (scaleDetector.isInProgress()) {
            return true;
        }
        v.performClick();
        return false;
    }

    private final void showFloatingStarAnimation() {
        final ViewGroup rootLayout = (ViewGroup) findViewById(android.R.id.content);
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int i2 = i;
            final TextView star = new TextView(this);
            star.setText("⭐");
            star.setTextSize(random.nextInt(12) + 14);
            star.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            rootLayout.addView(star);
            star.setX((screenWidth * 0.2f) + random.nextInt((int) (screenWidth * 0.6f)));
            star.setY((screenHeight * 0.5f) + random.nextInt((int) (screenHeight * 0.2f)));
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.star_float);
            anim.setStartOffset(i2 * 120);
            anim.setAnimationListener(new Animation.AnimationListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$showFloatingStarAnimation$1$1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation a) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation a) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation a) {
                    rootLayout.removeView(star);
                }
            });
            star.startAnimation(anim);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void confirmRetry(final Message message) {
        new AlertDialog.Builder(this).setMessage(R.string.status_failed).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda45
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ThreadActivity.confirmRetry$lambda$52(ThreadActivity.this, message, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmRetry$lambda$52(ThreadActivity this$0, Message message, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(message, "$message");
        ThreadViewModel threadViewModel = this$0.viewModel;
        if (threadViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel = null;
        }
        threadViewModel.retryMessage(message);
    }

    private final void setupComposeBar() {
        int inputFieldColor;
        View findViewById = findViewById(R.id.editMessage);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.editMessage = (EditText) findViewById;
        View findViewById2 = findViewById(R.id.attachmentPreviewScroll);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
        this.attachmentScroll = (HorizontalScrollView) findViewById2;
        View findViewById3 = findViewById(R.id.attachmentPreviewContainer);
        Intrinsics.checkNotNullExpressionValue(findViewById3, "findViewById(...)");
        this.attachmentContainer = (LinearLayout) findViewById3;
        ChatTheme theme = ChatThemeManager.INSTANCE.getCurrentTheme(this);
        View composeBar = findViewById(R.id.composeBarRoot);
        if (composeBar != null) {
            composeBar.setBackgroundColor(theme.getAppBarBg());
        }
        EditText editText = this.editMessage;
        EditText editText2 = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
            editText = null;
        }
        editText.setTextColor(theme.getTextPrimary());
        EditText editText3 = this.editMessage;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
            editText3 = null;
        }
        editText3.setHintTextColor(theme.getTextSecondary());
        if (isDarkColor(theme.getAppBarBg())) {
            inputFieldColor = blendColor(theme.getAppBarBg(), -1, 0.15f);
        } else {
            inputFieldColor = blendColor(theme.getAppBarBg(), ViewCompat.MEASURED_STATE_MASK, 0.08f);
        }
        GradientDrawable $this$setupComposeBar_u24lambda_u2453 = new GradientDrawable();
        $this$setupComposeBar_u24lambda_u2453.setColor(inputFieldColor);
        $this$setupComposeBar_u24lambda_u2453.setCornerRadius(getResources().getDisplayMetrics().density * 24.0f);
        EditText editText4 = this.editMessage;
        if (editText4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
        } else {
            editText2 = editText4;
        }
        editText2.setBackground($this$setupComposeBar_u24lambda_u2453);
        ImageButton btnSend = (ImageButton) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda22
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.setupComposeBar$lambda$54(ThreadActivity.this, view);
            }
        });
        btnSend.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda33
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                boolean z;
                z = ThreadActivity.setupComposeBar$lambda$55(ThreadActivity.this, view);
                return z;
            }
        });
        ((ImageButton) findViewById(R.id.btnAttach)).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda43
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.setupComposeBar$lambda$56(ThreadActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupComposeBar$lambda$54(ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EditText editText = this$0.editMessage;
        EditText editText2 = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
            editText = null;
        }
        Editable text = editText.getText();
        String text2 = text != null ? text.toString() : null;
        if (text2 == null) {
            text2 = "";
        }
        ThreadViewModel threadViewModel = this$0.viewModel;
        if (threadViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel = null;
        }
        List<Uri> value = threadViewModel.getPendingAttachments().getValue();
        boolean hasAttachments = !(value == null || value.isEmpty());
        if ((true ^ StringsKt.isBlank(text2)) || hasAttachments) {
            ThreadViewModel threadViewModel2 = this$0.viewModel;
            if (threadViewModel2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                threadViewModel2 = null;
            }
            threadViewModel2.sendMessage(text2);
            EditText editText3 = this$0.editMessage;
            if (editText3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("editMessage");
            } else {
                editText2 = editText3;
            }
            editText2.setText("");
            this$0.hideEmojiPicker();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupComposeBar$lambda$55(ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EditText editText = this$0.editMessage;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
            editText = null;
        }
        Editable text = editText.getText();
        String obj = text != null ? text.toString() : null;
        if (obj == null) {
            obj = "";
        }
        String text2 = obj;
        if (!StringsKt.isBlank(text2)) {
            this$0.showScheduleSendDialog(text2);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupComposeBar$lambda$56(ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showAttachmentSheet();
    }

    private final void showScheduleSendDialog(final String messageText) {
        final BottomSheetDialog sheet = new BottomSheetDialog(this);
        View v = getLayoutInflater().inflate(R.layout.sheet_schedule_send, (ViewGroup) null);
        sheet.setContentView(v);
        SimpleDateFormat timeFmt = new SimpleDateFormat("EEE, MMM d · h:mm a", Locale.getDefault());
        final long t8 = showScheduleSendDialog$tomorrowAt(8, 0);
        final long t13 = showScheduleSendDialog$tomorrowAt(13, 0);
        final long t18 = showScheduleSendDialog$tomorrowAt(18, 0);
        ((TextView) v.findViewById(R.id.txt8amTime)).setText(timeFmt.format(Long.valueOf(t8)));
        ((TextView) v.findViewById(R.id.txt1pmTime)).setText(timeFmt.format(Long.valueOf(t13)));
        ((TextView) v.findViewById(R.id.txt6pmTime)).setText(timeFmt.format(Long.valueOf(t18)));
        v.findViewById(R.id.opt8am).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.showScheduleSendDialog$lambda$57(BottomSheetDialog.this, this, messageText, t8, view);
            }
        });
        v.findViewById(R.id.opt1pm).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.showScheduleSendDialog$lambda$58(BottomSheetDialog.this, this, messageText, t13, view);
            }
        });
        v.findViewById(R.id.opt6pm).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.showScheduleSendDialog$lambda$59(BottomSheetDialog.this, this, messageText, t18, view);
            }
        });
        v.findViewById(R.id.optCustom).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.showScheduleSendDialog$lambda$60(BottomSheetDialog.this, this, messageText, view);
            }
        });
        sheet.show();
    }

    private static final long showScheduleSendDialog$tomorrowAt(int h, int m) {
        Calendar c = Calendar.getInstance();
        c.add(6, 1);
        c.set(11, h);
        c.set(12, m);
        c.set(13, 0);
        return c.getTimeInMillis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showScheduleSendDialog$lambda$57(BottomSheetDialog sheet, ThreadActivity this$0, String messageText, long $t8, View it) {
        Intrinsics.checkNotNullParameter(sheet, "$sheet");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(messageText, "$messageText");
        sheet.dismiss();
        this$0.scheduleMessage(messageText, $t8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showScheduleSendDialog$lambda$58(BottomSheetDialog sheet, ThreadActivity this$0, String messageText, long $t13, View it) {
        Intrinsics.checkNotNullParameter(sheet, "$sheet");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(messageText, "$messageText");
        sheet.dismiss();
        this$0.scheduleMessage(messageText, $t13);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showScheduleSendDialog$lambda$59(BottomSheetDialog sheet, ThreadActivity this$0, String messageText, long $t18, View it) {
        Intrinsics.checkNotNullParameter(sheet, "$sheet");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(messageText, "$messageText");
        sheet.dismiss();
        this$0.scheduleMessage(messageText, $t18);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showScheduleSendDialog$lambda$60(BottomSheetDialog sheet, ThreadActivity this$0, String messageText, View it) {
        Intrinsics.checkNotNullParameter(sheet, "$sheet");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(messageText, "$messageText");
        sheet.dismiss();
        this$0.showDateTimePicker(messageText);
    }

    private final void showDateTimePicker(final String messageText) {
        final Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda41
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public final void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                ThreadActivity.showDateTimePicker$lambda$63(ThreadActivity.this, cal, messageText, datePicker, i, i2, i3);
            }
        }, cal.get(1), cal.get(2), cal.get(5)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDateTimePicker$lambda$63(final ThreadActivity this$0, Calendar $cal, final String messageText, DatePicker datePicker, final int year, final int month, final int day) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(messageText, "$messageText");
        new TimePickerDialog(this$0, new TimePickerDialog.OnTimeSetListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda42
            @Override // android.app.TimePickerDialog.OnTimeSetListener
            public final void onTimeSet(TimePicker timePicker, int i, int i2) {
                ThreadActivity.showDateTimePicker$lambda$63$lambda$62(ThreadActivity.this, messageText, year, month, day, timePicker, i, i2);
            }
        }, $cal.get(11), $cal.get(12), false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDateTimePicker$lambda$63$lambda$62(ThreadActivity this$0, String messageText, int $year, int $month, int $day, TimePicker timePicker, int hour, int minute) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(messageText, "$messageText");
        Calendar scheduled = Calendar.getInstance();
        scheduled.set($year, $month, $day, hour, minute, 0);
        this$0.scheduleMessage(messageText, scheduled.getTimeInMillis());
    }

    private final void scheduleMessage(final String text, long scheduledAtMillis) {
        long delay = scheduledAtMillis - System.currentTimeMillis();
        if (delay <= 0) {
            Toast.makeText(this, "Please choose a future time", 0).show();
            return;
        }
        EditText editText = this.editMessage;
        ThreadViewModel threadViewModel = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
            editText = null;
        }
        editText.setText("");
        SimpleDateFormat timeFmt = new SimpleDateFormat("MMM d, h:mm a", Locale.getDefault());
        String timeStr = timeFmt.format(new Date(scheduledAtMillis));
        final String pendingKey = "sched_" + System.currentTimeMillis();
        Handler handler = new Handler(Looper.getMainLooper());
        this.scheduledMessages.put(pendingKey, new Pair<>(Long.valueOf(scheduledAtMillis), handler));
        ThreadViewModel threadViewModel2 = this.viewModel;
        if (threadViewModel2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
        } else {
            threadViewModel = threadViewModel2;
        }
        threadViewModel.sendScheduledPlaceholder(text, scheduledAtMillis, pendingKey);
        handler.postDelayed(new Runnable() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                ThreadActivity.scheduleMessage$lambda$64(ThreadActivity.this, pendingKey, text);
            }
        }, delay);
        Toast.makeText(this, "⏰ Message scheduled for " + timeStr + "\nTap the message bubble to edit or cancel", 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scheduleMessage$lambda$64(ThreadActivity this$0, String pendingKey, String text) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(pendingKey, "$pendingKey");
        Intrinsics.checkNotNullParameter(text, "$text");
        this$0.scheduledMessages.remove(pendingKey);
        ThreadViewModel threadViewModel = this$0.viewModel;
        ThreadViewModel threadViewModel2 = null;
        if (threadViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel = null;
        }
        threadViewModel.deleteScheduledPlaceholder(pendingKey);
        ThreadViewModel threadViewModel3 = this$0.viewModel;
        if (threadViewModel3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
        } else {
            threadViewModel2 = threadViewModel3;
        }
        threadViewModel2.sendMessage(text);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showScheduledOptions(Message fakeMessage) {
        final String realText = fakeMessage.getBody();
        ThreadViewModel threadViewModel = this.viewModel;
        Object obj = null;
        if (threadViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel = null;
        }
        Iterable iterable = (List) threadViewModel.getScheduledEntries().getValue();
        if (iterable == null) {
            return;
        }
        Iterable $this$firstOrNull$iv = iterable;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object element$iv = it.next();
            ThreadViewModel.ScheduledEntry it2 = (ThreadViewModel.ScheduledEntry) element$iv;
            ThreadViewModel.ScheduledEntry it3 = it2.getScheduledAtMillis() == fakeMessage.getDateMillis() ? 1 : null;
            if (it3 != null) {
                obj = element$iv;
                break;
            }
        }
        final ThreadViewModel.ScheduledEntry entry = (ThreadViewModel.ScheduledEntry) obj;
        if (entry == null) {
            return;
        }
        SimpleDateFormat timeFmt = new SimpleDateFormat("MMM d, h:mm a", Locale.getDefault());
        String timeStr = timeFmt.format(new Date(entry.getScheduledAtMillis()));
        new AlertDialog.Builder(this).setTitle("Scheduled: " + timeStr).setItems(new String[]{"✏️  Edit scheduled message", "▶️  Send now", "🗑  Delete scheduled message"}, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda17
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ThreadActivity.showScheduledOptions$lambda$69(ThreadActivity.this, entry, realText, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showScheduledOptions$lambda$69(final ThreadActivity this$0, ThreadViewModel.ScheduledEntry entry, final String realText, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(entry, "$entry");
        Intrinsics.checkNotNullParameter(realText, "$realText");
        Pair<Long, Handler> pair = this$0.scheduledMessages.get(entry.getKey());
        ThreadViewModel threadViewModel = null;
        Handler handler = pair != null ? pair.getSecond() : null;
        switch (i) {
            case 0:
                final long originalTime = entry.getScheduledAtMillis();
                String originalKey = entry.getKey();
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                }
                this$0.scheduledMessages.remove(originalKey);
                ThreadViewModel threadViewModel2 = this$0.viewModel;
                if (threadViewModel2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                } else {
                    threadViewModel = threadViewModel2;
                }
                threadViewModel.deleteScheduledPlaceholder(originalKey);
                final EditText $this$showScheduledOptions_u24lambda_u2469_u24lambda_u2466 = new EditText(this$0);
                $this$showScheduledOptions_u24lambda_u2469_u24lambda_u2466.setText(realText);
                $this$showScheduledOptions_u24lambda_u2469_u24lambda_u2466.setSelection(realText.length());
                $this$showScheduledOptions_u24lambda_u2469_u24lambda_u2466.setPadding(48, 32, 48, 32);
                SimpleDateFormat timeFmtEdit = new SimpleDateFormat("MMM d, h:mm a", Locale.getDefault());
                new AlertDialog.Builder(this$0).setTitle("Edit message").setMessage("⏰ Scheduled: " + timeFmtEdit.format(new Date(originalTime))).setView($this$showScheduledOptions_u24lambda_u2469_u24lambda_u2466).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda7
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface2, int i2) {
                        ThreadActivity.showScheduledOptions$lambda$69$lambda$67($this$showScheduledOptions_u24lambda_u2469_u24lambda_u2466, this$0, originalTime, dialogInterface2, i2);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda8
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface2, int i2) {
                        ThreadActivity.showScheduledOptions$lambda$69$lambda$68(ThreadActivity.this, realText, originalTime, dialogInterface2, i2);
                    }
                }).show();
                return;
            case 1:
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                }
                this$0.scheduledMessages.remove(entry.getKey());
                ThreadViewModel threadViewModel3 = this$0.viewModel;
                if (threadViewModel3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                    threadViewModel3 = null;
                }
                threadViewModel3.deleteScheduledPlaceholder(entry.getKey());
                ThreadViewModel threadViewModel4 = this$0.viewModel;
                if (threadViewModel4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                } else {
                    threadViewModel = threadViewModel4;
                }
                threadViewModel.sendMessage(realText);
                return;
            case 2:
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                }
                this$0.scheduledMessages.remove(entry.getKey());
                ThreadViewModel threadViewModel5 = this$0.viewModel;
                if (threadViewModel5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                } else {
                    threadViewModel = threadViewModel5;
                }
                threadViewModel.deleteScheduledPlaceholder(entry.getKey());
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showScheduledOptions$lambda$69$lambda$67(EditText inputEdit, ThreadActivity this$0, long $originalTime, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(inputEdit, "$inputEdit");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String newText = StringsKt.trim((CharSequence) inputEdit.getText().toString()).toString();
        if (!StringsKt.isBlank(newText)) {
            this$0.scheduleMessage(newText, $originalTime);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showScheduledOptions$lambda$69$lambda$68(ThreadActivity this$0, String realText, long $originalTime, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(realText, "$realText");
        this$0.scheduleMessage(realText, $originalTime);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void mergeAndSubmit(List<Message> messages, List<ThreadViewModel.ScheduledEntry> scheduled) {
        SimpleDateFormat timeFmt = new SimpleDateFormat("MMM d, h:mm a", Locale.getDefault());
        List<ThreadViewModel.ScheduledEntry> $this$mapIndexed$iv = scheduled;
        int $i$f$mapIndexed = 0;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexed$iv) {
            int index$iv$iv2 = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ThreadViewModel.ScheduledEntry entry = (ThreadViewModel.ScheduledEntry) item$iv$iv;
            destination$iv$iv.add(new Message(-(index$iv$iv + 1), this.threadId, this.address, entry.getText(), entry.getScheduledAtMillis(), entry.getScheduledAtMillis(), MessageType.SMS, MessageBox.QUEUED, true, null, false, 1536, null));
            index$iv$iv = index$iv$iv2;
            timeFmt = timeFmt;
            $this$mapIndexed$iv = $this$mapIndexed$iv;
            $i$f$mapIndexed = $i$f$mapIndexed;
        }
        List fakeScheduled = (List) destination$iv$iv;
        Iterable $this$sortedBy$iv = CollectionsKt.plus((Collection) messages, (Iterable) fakeScheduled);
        final List combined = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$mergeAndSubmit$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                Message it = (Message) t;
                Message it2 = (Message) t2;
                return ComparisonsKt.compareValues(Long.valueOf(it.getDateMillis()), Long.valueOf(it2.getDateMillis()));
            }
        });
        MessageAdapter messageAdapter = this.adapter;
        MessageAdapter messageAdapter2 = null;
        if (messageAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            messageAdapter = null;
        }
        final int prevCount = messageAdapter.getItemCount();
        RecyclerView recyclerView = this.recycler;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
            recyclerView = null;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        LinearLayoutManager lm = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
        int lastVisible = lm != null ? lm.findLastCompletelyVisibleItemPosition() : 0;
        final boolean wasAtBottom = prevCount == 0 || lastVisible >= prevCount + (-2);
        MessageAdapter messageAdapter3 = this.adapter;
        if (messageAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            messageAdapter2 = messageAdapter3;
        }
        messageAdapter2.submitList(combined, new Runnable() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda31
            @Override // java.lang.Runnable
            public final void run() {
                ThreadActivity.mergeAndSubmit$lambda$76(combined, this, wasAtBottom, prevCount);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mergeAndSubmit$lambda$76(List combined, final ThreadActivity this$0, boolean $wasAtBottom, int $prevCount) {
        Function1<? super Integer, Unit> function1;
        Intrinsics.checkNotNullParameter(combined, "$combined");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!combined.isEmpty()) {
            long highlightId = this$0.getIntent().getLongExtra("HIGHLIGHT_MESSAGE_ID", -1L);
            RecyclerView recyclerView = null;
            if (highlightId != -1) {
                int index$iv = 0;
                Iterator it = combined.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object item$iv = it.next();
                        Message it2 = (Message) item$iv;
                        Message it3 = it2.getId() == highlightId ? 1 : null;
                        if (it3 != null) {
                            break;
                        } else {
                            index$iv++;
                        }
                    } else {
                        index$iv = -1;
                        break;
                    }
                }
                final int idx = index$iv;
                if (idx >= 0) {
                    RecyclerView recyclerView2 = this$0.recycler;
                    if (recyclerView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("recycler");
                        recyclerView2 = null;
                    }
                    recyclerView2.scrollToPosition(idx);
                    this$0.getIntent().removeExtra("HIGHLIGHT_MESSAGE_ID");
                    RecyclerView recyclerView3 = this$0.recycler;
                    if (recyclerView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("recycler");
                    } else {
                        recyclerView = recyclerView3;
                    }
                    recyclerView.postDelayed(new Runnable() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            ThreadActivity.mergeAndSubmit$lambda$76$lambda$75(ThreadActivity.this, idx);
                        }
                    }, 400L);
                    return;
                }
            }
            Message lastMsg = (Message) CollectionsKt.last(combined);
            boolean justSent = lastMsg.getBox() == MessageBox.SENT && System.currentTimeMillis() - lastMsg.getDateMillis() < 3000;
            if ($wasAtBottom || justSent) {
                if (justSent) {
                    this$0.unreadNewCount = 0;
                }
                RecyclerView recyclerView4 = this$0.recycler;
                if (recyclerView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("recycler");
                } else {
                    recyclerView = recyclerView4;
                }
                recyclerView.scrollToPosition(combined.size() - 1);
                return;
            }
            int newCount = combined.size() - $prevCount;
            if (newCount <= 0 || (function1 = this$0.newMessageArrived) == null) {
                return;
            }
            function1.invoke(Integer.valueOf(newCount));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mergeAndSubmit$lambda$76$lambda$75(ThreadActivity this$0, int $idx) {
        final View v;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RecyclerView recyclerView = this$0.recycler;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recycler");
            recyclerView = null;
        }
        RecyclerView.ViewHolder vh = recyclerView.findViewHolderForAdapterPosition($idx);
        if (vh != null && (v = vh.itemView) != null) {
            v.animate().alpha(0.2f).setDuration(200L).withEndAction(new Runnable() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ThreadActivity.mergeAndSubmit$lambda$76$lambda$75$lambda$74$lambda$73(v);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mergeAndSubmit$lambda$76$lambda$75$lambda$74$lambda$73(View v) {
        Intrinsics.checkNotNullParameter(v, "$v");
        v.animate().alpha(1.0f).setDuration(500L).start();
    }

    private final void showAttachmentSheet() {
        final BottomSheetDialog sheet = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.sheet_attachment_options, (ViewGroup) null);
        sheet.setContentView(view);
        view.findViewById(R.id.optionCamera).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ThreadActivity.showAttachmentSheet$lambda$77(BottomSheetDialog.this, this, view2);
            }
        });
        view.findViewById(R.id.optionGallery).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ThreadActivity.showAttachmentSheet$lambda$78(BottomSheetDialog.this, this, view2);
            }
        });
        view.findViewById(R.id.optionFile).setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ThreadActivity.showAttachmentSheet$lambda$79(BottomSheetDialog.this, this, view2);
            }
        });
        sheet.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAttachmentSheet$lambda$77(BottomSheetDialog sheet, ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(sheet, "$sheet");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        sheet.dismiss();
        this$0.requestCameraThenLaunch();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAttachmentSheet$lambda$78(BottomSheetDialog sheet, ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(sheet, "$sheet");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        sheet.dismiss();
        this$0.attachmentPickerLauncher.launch(PickVisualMediaRequestKt.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAttachmentSheet$lambda$79(BottomSheetDialog sheet, ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(sheet, "$sheet");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        sheet.dismiss();
        this$0.filePickerLauncher.launch("*/*");
    }

    private final void requestCameraThenLaunch() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0) {
            launchCamera();
        } else {
            this.cameraPermissionLauncher.launch("android.permission.CAMERA");
        }
    }

    private final void launchCamera() {
        File photoFile = File.createTempFile("origin_camera_", ".jpg", getCacheDir());
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
        this.pendingCameraUri = uri;
        ActivityResultLauncher<Uri> activityResultLauncher = this.cameraLauncher;
        Intrinsics.checkNotNull(uri);
        activityResultLauncher.launch(uri);
    }

    private final void setupEmojiPicker() {
        View findViewById = findViewById(R.id.emojiPickerView);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
        this.emojiPicker = (EmojiPickerView) findViewById;
        ImageButton btnEmoji = (ImageButton) findViewById(R.id.btnEmoji);
        EmojiPickerView emojiPickerView = this.emojiPicker;
        EditText editText = null;
        if (emojiPickerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emojiPicker");
            emojiPickerView = null;
        }
        emojiPickerView.setOnEmojiPickedListener(new Consumer() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda19
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ThreadActivity.setupEmojiPicker$lambda$80(ThreadActivity.this, (EmojiViewItem) obj);
            }
        });
        btnEmoji.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda20
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.setupEmojiPicker$lambda$81(ThreadActivity.this, view);
            }
        });
        EditText editText2 = this.editMessage;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
        } else {
            editText = editText2;
        }
        editText.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda21
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThreadActivity.setupEmojiPicker$lambda$82(ThreadActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupEmojiPicker$lambda$80(ThreadActivity this$0, EmojiViewItem emojiViewItem) {
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
    public static final void setupEmojiPicker$lambda$81(ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.emojiPickerVisible) {
            this$0.hideEmojiPicker();
        } else {
            this$0.showEmojiPicker();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupEmojiPicker$lambda$82(ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.emojiPickerVisible) {
            this$0.hideEmojiPicker();
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

    private final void setupSimIndicator() {
        try {
            SubscriptionManager subManager = (SubscriptionManager) getSystemService(SubscriptionManager.class);
            final List activeSubscriptions = subManager.getActiveSubscriptionInfoList();
            if (activeSubscriptions != null && activeSubscriptions.size() > 1) {
                int defaultSubId = SubscriptionManager.getDefaultSmsSubscriptionId();
                ThreadViewModel threadViewModel = this.viewModel;
                EditText editText = null;
                if (threadViewModel == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                    threadViewModel = null;
                }
                threadViewModel.setSelectedSubscriptionId(Integer.valueOf(defaultSubId));
                EditText editText2 = this.editMessage;
                if (editText2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("editMessage");
                } else {
                    editText = editText2;
                }
                editText.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda34
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        boolean z;
                        z = ThreadActivity.setupSimIndicator$lambda$86(activeSubscriptions, this, view);
                        return z;
                    }
                });
            }
        } catch (SecurityException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupSimIndicator$lambda$86(final List $activeSubscriptions, final ThreadActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNull($activeSubscriptions);
        List $this$mapIndexed$iv = $activeSubscriptions;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexed$iv) {
            int index$iv$iv2 = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            SubscriptionInfo info = (SubscriptionInfo) item$iv$iv;
            destination$iv$iv.add("SIM " + (index$iv$iv + 1) + " · " + ((Object) info.getDisplayName()));
            index$iv$iv = index$iv$iv2;
            $this$mapIndexed$iv = $this$mapIndexed$iv;
        }
        Collection $this$toTypedArray$iv = (List) destination$iv$iv;
        String[] options = (String[]) $this$toTypedArray$iv.toArray(new String[0]);
        int index$iv = 0;
        Iterator it2 = $activeSubscriptions.iterator();
        while (true) {
            if (it2.hasNext()) {
                Object item$iv = it2.next();
                SubscriptionInfo it3 = (SubscriptionInfo) item$iv;
                int subscriptionId = it3.getSubscriptionId();
                ThreadViewModel threadViewModel = this$0.viewModel;
                if (threadViewModel == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                    threadViewModel = null;
                }
                Integer selectedSubscriptionId = threadViewModel.getSelectedSubscriptionId();
                SubscriptionInfo it4 = (selectedSubscriptionId != null && subscriptionId == selectedSubscriptionId.intValue()) ? 1 : null;
                if (it4 != null) {
                    break;
                }
                index$iv++;
            } else {
                index$iv = -1;
                break;
            }
        }
        int currentIdx = index$iv;
        new AlertDialog.Builder(this$0).setTitle("Send from SIM").setSingleChoiceItems(options, currentIdx, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda18
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ThreadActivity.setupSimIndicator$lambda$86$lambda$85(ThreadActivity.this, $activeSubscriptions, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSimIndicator$lambda$86$lambda$85(ThreadActivity this$0, List $activeSubscriptions, DialogInterface dialog, int idx) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ThreadViewModel threadViewModel = this$0.viewModel;
        if (threadViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel = null;
        }
        threadViewModel.setSelectedSubscriptionId(Integer.valueOf(((SubscriptionInfo) $activeSubscriptions.get(idx)).getSubscriptionId()));
        CharSequence simName = ((SubscriptionInfo) $activeSubscriptions.get(idx)).getDisplayName();
        Toast.makeText(this$0, "Sending from " + ((Object) simName), 0).show();
        dialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderAttachmentPreviews(List<? extends Uri> uris) {
        LinearLayout linearLayout = this.attachmentContainer;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("attachmentContainer");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        HorizontalScrollView horizontalScrollView = this.attachmentScroll;
        if (horizontalScrollView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("attachmentScroll");
            horizontalScrollView = null;
        }
        horizontalScrollView.setVisibility(uris.isEmpty() ? 8 : 0);
        LayoutInflater inflater = LayoutInflater.from(this);
        for (final Uri uri : uris) {
            int i = R.layout.item_attachment_preview;
            LinearLayout linearLayout2 = this.attachmentContainer;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("attachmentContainer");
                linearLayout2 = null;
            }
            View chip = inflater.inflate(i, (ViewGroup) linearLayout2, false);
            ImageView thumbnail = (ImageView) chip.findViewById(R.id.imgThumbnail);
            ImageButton removeButton = (ImageButton) chip.findViewById(R.id.btnRemoveAttachment);
            try {
                InputStream openInputStream = getContentResolver().openInputStream(uri);
                if (openInputStream != null) {
                    InputStream inputStream = openInputStream;
                    try {
                        InputStream stream = inputStream;
                        Bitmap bitmap = BitmapFactory.decodeStream(stream);
                        if (bitmap != null) {
                            thumbnail.setImageBitmap(bitmap);
                        }
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(inputStream, null);
                    } catch (Throwable th) {
                        try {
                            throw th;
                            break;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(inputStream, th);
                            throw th2;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                thumbnail.setImageResource(R.drawable.ic_attach);
            }
            removeButton.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda23
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ThreadActivity.renderAttachmentPreviews$lambda$88(ThreadActivity.this, uri, view);
                }
            });
            LinearLayout linearLayout3 = this.attachmentContainer;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("attachmentContainer");
                linearLayout3 = null;
            }
            linearLayout3.addView(chip);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void renderAttachmentPreviews$lambda$88(ThreadActivity this$0, Uri uri, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(uri, "$uri");
        ThreadViewModel threadViewModel = this$0.viewModel;
        if (threadViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel = null;
        }
        threadViewModel.removePendingAttachment(uri);
    }

    private final void restoreDraft() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new ThreadActivity$restoreDraft$1(this, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        ThreadViewModel threadViewModel = this.viewModel;
        if (threadViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel = null;
        }
        EditText editText = this.editMessage;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editMessage");
            editText = null;
        }
        Editable text = editText.getText();
        String obj = text != null ? text.toString() : null;
        if (obj == null) {
            obj = "";
        }
        threadViewModel.saveDraft(obj);
        Application application = getApplication();
        OriginSmsApp originSmsApp = application instanceof OriginSmsApp ? (OriginSmsApp) application : null;
        if (originSmsApp != null) {
            originSmsApp.setActiveThreadId(-1L);
        }
        if (this.threadId != -1) {
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new ThreadActivity$onPause$1(this, null), 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        ThreadViewModel threadViewModel = this.viewModel;
        if (threadViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel = null;
        }
        threadViewModel.loadMessages();
        if (this.threadId != -1) {
            Application application = getApplication();
            OriginSmsApp originSmsApp = application instanceof OriginSmsApp ? (OriginSmsApp) application : null;
            if (originSmsApp != null) {
                originSmsApp.setActiveThreadId(this.threadId);
            }
            new NotificationHelper(this).cancel(this.threadId);
        }
        ThreadViewModel threadViewModel2 = this.viewModel;
        if (threadViewModel2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel2 = null;
        }
        Iterable iterable = (List) threadViewModel2.getScheduledEntries().getValue();
        if (iterable != null) {
            Iterable $this$forEach$iv = iterable;
            for (Object element$iv : $this$forEach$iv) {
                final ThreadViewModel.ScheduledEntry entry = (ThreadViewModel.ScheduledEntry) element$iv;
                if (!this.scheduledMessages.containsKey(entry.getKey())) {
                    long delay = entry.getScheduledAtMillis() - System.currentTimeMillis();
                    if (delay > 0) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        this.scheduledMessages.put(entry.getKey(), new Pair<>(Long.valueOf(entry.getScheduledAtMillis()), handler));
                        handler.postDelayed(new Runnable() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$$ExternalSyntheticLambda44
                            @Override // java.lang.Runnable
                            public final void run() {
                                ThreadActivity.onResume$lambda$90$lambda$89(ThreadActivity.this, entry);
                            }
                        }, delay);
                    }
                }
            }
        }
        if (this.threadId != -1) {
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new ThreadActivity$onResume$2(this, null), 3, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onResume$lambda$90$lambda$89(ThreadActivity this$0, ThreadViewModel.ScheduledEntry entry) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(entry, "$entry");
        this$0.scheduledMessages.remove(entry.getKey());
        ThreadViewModel threadViewModel = this$0.viewModel;
        ThreadViewModel threadViewModel2 = null;
        if (threadViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
            threadViewModel = null;
        }
        threadViewModel.deleteScheduledPlaceholder(entry.getKey());
        ThreadViewModel threadViewModel3 = this$0.viewModel;
        if (threadViewModel3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewModel");
        } else {
            threadViewModel2 = threadViewModel3;
        }
        threadViewModel2.sendMessage(entry.getText());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        MessageAdapter messageAdapter = this.adapter;
        MessageAdapter messageAdapter2 = null;
        if (messageAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            messageAdapter = null;
        }
        if (messageAdapter.getSelectedCount() > 0) {
            MessageAdapter messageAdapter3 = this.adapter;
            if (messageAdapter3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
            } else {
                messageAdapter2 = messageAdapter3;
            }
            messageAdapter2.clearSelection();
            updateMessageSelectionBar();
            return;
        }
        super.onBackPressed();
    }

    private final void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(ClipboardManager.class);
        clipboard.setPrimaryClip(ClipData.newPlainText("message", text));
    }
}
