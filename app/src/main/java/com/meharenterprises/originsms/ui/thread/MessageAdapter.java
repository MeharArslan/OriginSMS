package com.meharenterprises.originsms.ui.thread;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.common.net.HttpHeaders;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.Attachment;
import com.meharenterprises.originsms.core.Message;
import com.meharenterprises.originsms.core.MessageBox;
import com.meharenterprises.originsms.ui.ChatTheme;
import com.meharenterprises.originsms.ui.ChatThemeManager;
import com.meharenterprises.originsms.ui.thread.MessageAdapter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;

/* compiled from: MessageAdapter.kt */
@Metadata(d1 = {"\u0000\u009d\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b*\u0001\u001a\u0018\u0000 O2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0004OPQRB[\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\nJD\u0010\u001f\u001a\u00020\u00062\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010%2\b\u0010&\u001a\u0004\u0018\u00010%2\b\u0010'\u001a\u0004\u0018\u00010%2\b\u0010(\u001a\u0004\u0018\u00010)H\u0002J\u001a\u0010*\u001a\u00020\u00062\u0006\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010.H\u0002J\u0018\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020!2\u0006\u00101\u001a\u00020\u0002H\u0002JB\u00102\u001a\u00020\u00062\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010%2\b\u0010&\u001a\u0004\u0018\u00010%2\b\u0010'\u001a\u0004\u0018\u00010%2\u0006\u00103\u001a\u000204H\u0002J\u0006\u00105\u001a\u00020\u0006J\u0012\u00106\u001a\u0004\u0018\u00010)2\u0006\u00107\u001a\u000204H\u0002J\u0010\u00108\u001a\u0002042\u0006\u00109\u001a\u00020\u0012H\u0002J\u0010\u0010:\u001a\u0002042\u0006\u00109\u001a\u00020\u0012H\u0002J\u0010\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020<H\u0016J\u0006\u0010>\u001a\u00020<J\f\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00120@J\u0018\u0010A\u001a\u00020\u00142\u0006\u0010B\u001a\u00020\u00122\u0006\u0010C\u001a\u00020\u0012H\u0002J\u0018\u0010D\u001a\u00020\u00062\u0006\u0010E\u001a\u00020\u00032\u0006\u0010=\u001a\u00020<H\u0016J\u0018\u0010F\u001a\u00020\u00032\u0006\u0010G\u001a\u00020H2\u0006\u0010I\u001a\u00020<H\u0016J\u000e\u0010J\u001a\u00020\u00062\u0006\u0010K\u001a\u00020\u001eJ\u0018\u0010L\u001a\u00020\u00062\u0006\u0010E\u001a\u00020\u00032\u0006\u0010=\u001a\u00020<H\u0002J\u0010\u0010M\u001a\u00020\u00062\u0006\u0010N\u001a\u00020\u0012H\u0002R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0014@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001bR\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006S"}, d2 = {"Lcom/meharenterprises/originsms/ui/thread/MessageAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/meharenterprises/originsms/core/Message;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "onLongPress", "Lkotlin/Function1;", "", "onTapWhileSelecting", "onTapFailedRetry", "onTapScheduled", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "URL_REGEX", "Lkotlin/text/Regex;", "bgExecutor", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "expandedTimestampIds", "", "", "<set-?>", "", "isSelectionMode", "()Z", "mainHandler", "Landroid/os/Handler;", "previewCache", "com/meharenterprises/originsms/ui/thread/MessageAdapter$previewCache$1", "Lcom/meharenterprises/originsms/ui/thread/MessageAdapter$previewCache$1;", "selectedIds", "textScaleFactor", "", "applyPreviewData", "card", "Landroid/view/View;", "img", "Landroid/widget/ImageView;", "txtTitle", "Landroid/widget/TextView;", "txtDesc", "txtUrl", "data", "Lcom/meharenterprises/originsms/ui/thread/MessageAdapter$LinkPreviewData;", "bindAttachment", "imageView", "Lcom/google/android/material/imageview/ShapeableImageView;", "attachment", "Lcom/meharenterprises/originsms/core/Attachment;", "bindClickHandlers", "itemView", "message", "bindLinkPreview", "body", "", "clearSelection", "fetchOgData", "url", "formatDateHeader", "millis", "formatTime", "getItemViewType", "", "position", "getSelectedCount", "getSelectedIds", "", "isSameDay", "m1", "m2", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setTextScale", "scale", "showDateSeparatorIfNeeded", "toggleSelection", "messageId", "Companion", "LinkPreviewData", "ReceivedViewHolder", "SentViewHolder", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes10.dex */
public final class MessageAdapter extends ListAdapter<Message, RecyclerView.ViewHolder> {
    private static final MessageAdapter$Companion$DIFF$1 DIFF = new DiffUtil.ItemCallback<Message>() { // from class: com.meharenterprises.originsms.ui.thread.MessageAdapter$Companion$DIFF$1
        @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
        public boolean areItemsTheSame(Message oldItem, Message newItem) {
            Intrinsics.checkNotNullParameter(oldItem, "oldItem");
            Intrinsics.checkNotNullParameter(newItem, "newItem");
            return oldItem.getId() == newItem.getId();
        }

        @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
        public boolean areContentsTheSame(Message oldItem, Message newItem) {
            Intrinsics.checkNotNullParameter(oldItem, "oldItem");
            Intrinsics.checkNotNullParameter(newItem, "newItem");
            return Intrinsics.areEqual(oldItem, newItem);
        }
    };
    private static final int TYPE_DATE_HEADER = 0;
    private static final int TYPE_RECEIVED = 2;
    private static final int TYPE_SENT = 1;
    private final Regex URL_REGEX;
    private final ExecutorService bgExecutor;
    private final Set<Long> expandedTimestampIds;
    private boolean isSelectionMode;
    private final Handler mainHandler;
    private final Function1<Message, Unit> onLongPress;
    private final Function1<Message, Unit> onTapFailedRetry;
    private final Function1<Message, Unit> onTapScheduled;
    private final Function1<Message, Unit> onTapWhileSelecting;
    private final MessageAdapter$previewCache$1 previewCache;
    private final Set<Long> selectedIds;
    private float textScaleFactor;

    /* compiled from: MessageAdapter.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes10.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MessageBox.values().length];
            try {
                iArr[MessageBox.FAILED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[MessageBox.OUTBOX.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[MessageBox.QUEUED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ MessageAdapter(Function1 function1, AnonymousClass1 anonymousClass1, AnonymousClass2 anonymousClass2, AnonymousClass3 anonymousClass3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(function1, (i & 2) != 0 ? new Function1<Message, Unit>() { // from class: com.meharenterprises.originsms.ui.thread.MessageAdapter.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Message message) {
                invoke2(message);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Message it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }
        } : anonymousClass1, (i & 4) != 0 ? new Function1<Message, Unit>() { // from class: com.meharenterprises.originsms.ui.thread.MessageAdapter.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Message message) {
                invoke2(message);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Message it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }
        } : anonymousClass2, (i & 8) != 0 ? new Function1<Message, Unit>() { // from class: com.meharenterprises.originsms.ui.thread.MessageAdapter.3
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Message message) {
                invoke2(message);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Message it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }
        } : anonymousClass3);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public MessageAdapter(Function1<? super Message, Unit> onLongPress, Function1<? super Message, Unit> onTapWhileSelecting, Function1<? super Message, Unit> onTapFailedRetry, Function1<? super Message, Unit> onTapScheduled) {
        super(DIFF);
        Intrinsics.checkNotNullParameter(onLongPress, "onLongPress");
        Intrinsics.checkNotNullParameter(onTapWhileSelecting, "onTapWhileSelecting");
        Intrinsics.checkNotNullParameter(onTapFailedRetry, "onTapFailedRetry");
        Intrinsics.checkNotNullParameter(onTapScheduled, "onTapScheduled");
        this.onLongPress = onLongPress;
        this.onTapWhileSelecting = onTapWhileSelecting;
        this.onTapFailedRetry = onTapFailedRetry;
        this.onTapScheduled = onTapScheduled;
        this.selectedIds = new LinkedHashSet();
        this.expandedTimestampIds = new LinkedHashSet();
        this.textScaleFactor = 1.0f;
        this.previewCache = new MessageAdapter$previewCache$1();
        this.URL_REGEX = new Regex("https?://[^\\s\\u200B-\\u200D\\uFEFF<>\"{}|\\\\^`\\[\\]]+", RegexOption.IGNORE_CASE);
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.bgExecutor = Executors.newCachedThreadPool();
    }

    /* renamed from: isSelectionMode, reason: from getter */
    public final boolean getIsSelectionMode() {
        return this.isSelectionMode;
    }

    /* compiled from: MessageAdapter.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0013\u0010\u001a\u001a\u0004\u0018\u00010\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0011\u0010\u001c\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015¨\u0006\u001e"}, d2 = {"Lcom/meharenterprises/originsms/ui/thread/MessageAdapter$SentViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/meharenterprises/originsms/ui/thread/MessageAdapter;Landroid/view/View;)V", "cardLinkPreview", "getCardLinkPreview", "()Landroid/view/View;", "imgAttachment", "Lcom/google/android/material/imageview/ShapeableImageView;", "getImgAttachment", "()Lcom/google/android/material/imageview/ShapeableImageView;", "imgLinkPreview", "Landroid/widget/ImageView;", "getImgLinkPreview", "()Landroid/widget/ImageView;", "imgStarIndicator", "getImgStarIndicator", "txtBody", "Landroid/widget/TextView;", "getTxtBody", "()Landroid/widget/TextView;", "txtLinkDesc", "getTxtLinkDesc", "txtLinkTitle", "getTxtLinkTitle", "txtLinkUrl", "getTxtLinkUrl", "txtStatus", "getTxtStatus", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes10.dex */
    public final class SentViewHolder extends RecyclerView.ViewHolder {
        private final View cardLinkPreview;
        private final ShapeableImageView imgAttachment;
        private final ImageView imgLinkPreview;
        private final ImageView imgStarIndicator;
        final /* synthetic */ MessageAdapter this$0;
        private final TextView txtBody;
        private final TextView txtLinkDesc;
        private final TextView txtLinkTitle;
        private final TextView txtLinkUrl;
        private final TextView txtStatus;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SentViewHolder(MessageAdapter this$0, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = this$0;
            View findViewById = itemView.findViewById(R.id.txtMessageBody);
            Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
            this.txtBody = (TextView) findViewById;
            View findViewById2 = itemView.findViewById(R.id.txtMessageStatus);
            Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
            this.txtStatus = (TextView) findViewById2;
            this.imgStarIndicator = (ImageView) itemView.findViewById(R.id.imgStarIndicator);
            View findViewById3 = itemView.findViewById(R.id.imgAttachment);
            Intrinsics.checkNotNullExpressionValue(findViewById3, "findViewById(...)");
            this.imgAttachment = (ShapeableImageView) findViewById3;
            this.cardLinkPreview = itemView.findViewById(R.id.cardLinkPreview);
            this.imgLinkPreview = (ImageView) itemView.findViewById(R.id.imgLinkPreview);
            this.txtLinkTitle = (TextView) itemView.findViewById(R.id.txtLinkTitle);
            this.txtLinkDesc = (TextView) itemView.findViewById(R.id.txtLinkDesc);
            this.txtLinkUrl = (TextView) itemView.findViewById(R.id.txtLinkUrl);
        }

        public final TextView getTxtBody() {
            return this.txtBody;
        }

        public final TextView getTxtStatus() {
            return this.txtStatus;
        }

        public final ImageView getImgStarIndicator() {
            return this.imgStarIndicator;
        }

        public final ShapeableImageView getImgAttachment() {
            return this.imgAttachment;
        }

        public final View getCardLinkPreview() {
            return this.cardLinkPreview;
        }

        public final ImageView getImgLinkPreview() {
            return this.imgLinkPreview;
        }

        public final TextView getTxtLinkTitle() {
            return this.txtLinkTitle;
        }

        public final TextView getTxtLinkDesc() {
            return this.txtLinkDesc;
        }

        public final TextView getTxtLinkUrl() {
            return this.txtLinkUrl;
        }
    }

    /* compiled from: MessageAdapter.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0013\u0010\u001a\u001a\u0004\u0018\u00010\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0011\u0010\u001c\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015¨\u0006\u001e"}, d2 = {"Lcom/meharenterprises/originsms/ui/thread/MessageAdapter$ReceivedViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/meharenterprises/originsms/ui/thread/MessageAdapter;Landroid/view/View;)V", "cardLinkPreview", "getCardLinkPreview", "()Landroid/view/View;", "imgAttachment", "Lcom/google/android/material/imageview/ShapeableImageView;", "getImgAttachment", "()Lcom/google/android/material/imageview/ShapeableImageView;", "imgLinkPreview", "Landroid/widget/ImageView;", "getImgLinkPreview", "()Landroid/widget/ImageView;", "imgStarIndicator", "getImgStarIndicator", "txtBody", "Landroid/widget/TextView;", "getTxtBody", "()Landroid/widget/TextView;", "txtLinkDesc", "getTxtLinkDesc", "txtLinkTitle", "getTxtLinkTitle", "txtLinkUrl", "getTxtLinkUrl", "txtStatus", "getTxtStatus", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes10.dex */
    public final class ReceivedViewHolder extends RecyclerView.ViewHolder {
        private final View cardLinkPreview;
        private final ShapeableImageView imgAttachment;
        private final ImageView imgLinkPreview;
        private final ImageView imgStarIndicator;
        final /* synthetic */ MessageAdapter this$0;
        private final TextView txtBody;
        private final TextView txtLinkDesc;
        private final TextView txtLinkTitle;
        private final TextView txtLinkUrl;
        private final TextView txtStatus;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ReceivedViewHolder(MessageAdapter this$0, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = this$0;
            View findViewById = itemView.findViewById(R.id.txtMessageBody);
            Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(...)");
            this.txtBody = (TextView) findViewById;
            View findViewById2 = itemView.findViewById(R.id.txtMessageStatus);
            Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(...)");
            this.txtStatus = (TextView) findViewById2;
            this.imgStarIndicator = (ImageView) itemView.findViewById(R.id.imgStarIndicator);
            View findViewById3 = itemView.findViewById(R.id.imgAttachment);
            Intrinsics.checkNotNullExpressionValue(findViewById3, "findViewById(...)");
            this.imgAttachment = (ShapeableImageView) findViewById3;
            this.cardLinkPreview = itemView.findViewById(R.id.cardLinkPreview);
            this.imgLinkPreview = (ImageView) itemView.findViewById(R.id.imgLinkPreview);
            this.txtLinkTitle = (TextView) itemView.findViewById(R.id.txtLinkTitle);
            this.txtLinkDesc = (TextView) itemView.findViewById(R.id.txtLinkDesc);
            this.txtLinkUrl = (TextView) itemView.findViewById(R.id.txtLinkUrl);
        }

        public final TextView getTxtBody() {
            return this.txtBody;
        }

        public final TextView getTxtStatus() {
            return this.txtStatus;
        }

        public final ImageView getImgStarIndicator() {
            return this.imgStarIndicator;
        }

        public final ShapeableImageView getImgAttachment() {
            return this.imgAttachment;
        }

        public final View getCardLinkPreview() {
            return this.cardLinkPreview;
        }

        public final ImageView getImgLinkPreview() {
            return this.imgLinkPreview;
        }

        public final TextView getTxtLinkTitle() {
            return this.txtLinkTitle;
        }

        public final TextView getTxtLinkDesc() {
            return this.txtLinkDesc;
        }

        public final TextView getTxtLinkUrl() {
            return this.txtLinkUrl;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return (getItem(position).getBox() == MessageBox.SENT || getItem(position).getBox() == MessageBox.OUTBOX || getItem(position).getBox() == MessageBox.FAILED || getItem(position).getBox() == MessageBox.QUEUED) ? 1 : 2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            Intrinsics.checkNotNull(view);
            return new SentViewHolder(this, view);
        }
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
        Intrinsics.checkNotNull(view2);
        return new ReceivedViewHolder(this, view2);
    }

    private final void showDateSeparatorIfNeeded(RecyclerView.ViewHolder holder, int position) {
        TextView txtDate = (TextView) holder.itemView.findViewWithTag("dateSeparator");
        if (txtDate != null) {
            Message msg = getItem(position);
            boolean showDate = position == 0 || !isSameDay(msg.getDateMillis(), getItem(position + (-1)).getDateMillis());
            if (showDate) {
                txtDate.setText(formatDateHeader(msg.getDateMillis()));
                txtDate.setVisibility(0);
            } else {
                txtDate.setVisibility(8);
            }
        }
    }

    private final boolean isSameDay(long m1, long m2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(m1);
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(m2);
        return c1.get(1) == c2.get(1) && c1.get(6) == c2.get(6);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object element$iv;
        Drawable baseBg;
        boolean z;
        String sentStatusText;
        boolean z2;
        Intrinsics.checkNotNullParameter(holder, "holder");
        showDateSeparatorIfNeeded(holder, position);
        Message message = getItem(position);
        String time = formatTime(message.getDateMillis());
        boolean isSelected = this.selectedIds.contains(Long.valueOf(message.getId()));
        boolean isLast = position == getItemCount() - 1;
        boolean isTimestampExpanded = this.expandedTimestampIds.contains(Long.valueOf(message.getId()));
        Iterable $this$firstOrNull$iv = message.getAttachments();
        int $i$f$firstOrNull = 0;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            Attachment it2 = (Attachment) element$iv;
            Iterable $this$firstOrNull$iv2 = $this$firstOrNull$iv;
            int $i$f$firstOrNull2 = $i$f$firstOrNull;
            if (StringsKt.startsWith$default(it2.getContentType(), "image/", false, 2, (Object) null) || StringsKt.startsWith$default(it2.getContentType(), "video/", false, 2, (Object) null)) {
                break;
            }
            $this$firstOrNull$iv = $this$firstOrNull$iv2;
            $i$f$firstOrNull = $i$f$firstOrNull2;
        }
        Attachment firstImageOrVideoAttachment = (Attachment) element$iv;
        holder.itemView.setBackgroundColor(0);
        holder.itemView.setAlpha(1.0f);
        if (this.isSelectionMode && !isSelected) {
            holder.itemView.setAlpha(0.5f);
        }
        if (!(holder instanceof SentViewHolder)) {
            if (holder instanceof ReceivedViewHolder) {
                ChatThemeManager chatThemeManager = ChatThemeManager.INSTANCE;
                Context context = holder.itemView.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                ChatTheme theme = chatThemeManager.getCurrentTheme(context);
                Context ctx = holder.itemView.getContext();
                holder.itemView.setBackgroundColor(0);
                Drawable drawable = ContextCompat.getDrawable(ctx, theme.getRecvBubbleDrawable());
                baseBg = drawable != null ? drawable.mutate() : null;
                if (isSelected) {
                    GradientDrawable $this$onBindViewHolder_u24lambda_u244 = new GradientDrawable();
                    $this$onBindViewHolder_u24lambda_u244.setColor(theme.getIncomingBubble());
                    $this$onBindViewHolder_u24lambda_u244.setStroke(4, theme.getAccentColor());
                    $this$onBindViewHolder_u24lambda_u244.setCornerRadius(theme.getBubbleCornerRadius());
                    ((ReceivedViewHolder) holder).getTxtBody().setBackground($this$onBindViewHolder_u24lambda_u244);
                } else {
                    ((ReceivedViewHolder) holder).getTxtBody().setBackground(baseBg);
                }
                ((ReceivedViewHolder) holder).getTxtBody().setTextColor(theme.getIncomingTextColor());
                bindAttachment(((ReceivedViewHolder) holder).getImgAttachment(), firstImageOrVideoAttachment);
                bindLinkPreview(((ReceivedViewHolder) holder).getCardLinkPreview(), ((ReceivedViewHolder) holder).getImgLinkPreview(), ((ReceivedViewHolder) holder).getTxtLinkTitle(), ((ReceivedViewHolder) holder).getTxtLinkDesc(), ((ReceivedViewHolder) holder).getTxtLinkUrl(), message.getBody());
                ((ReceivedViewHolder) holder).getTxtBody().setTextSize(this.textScaleFactor * 15.0f);
                ((ReceivedViewHolder) holder).getTxtBody().setVisibility(StringsKt.isBlank(message.getBody()) ? 8 : 0);
                ((ReceivedViewHolder) holder).getTxtBody().setText(message.getBody());
                ImageView imgStarIndicator = ((ReceivedViewHolder) holder).getImgStarIndicator();
                if (imgStarIndicator != null) {
                    imgStarIndicator.setVisibility(message.isStarred() ? 0 : 8);
                }
                ((ReceivedViewHolder) holder).getTxtStatus().setText(time);
                boolean isLastHiddenRecv = isLast && this.expandedTimestampIds.contains(Long.valueOf(message.getId()));
                if (isLastHiddenRecv) {
                    z = false;
                } else if (isLast) {
                    z = true;
                } else {
                    z = isTimestampExpanded;
                }
                boolean showRecvStatus = z;
                ((ReceivedViewHolder) holder).getTxtStatus().setVisibility(showRecvStatus ? 0 : 8);
                View itemView = holder.itemView;
                Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
                Intrinsics.checkNotNull(message);
                bindClickHandlers(itemView, message);
                return;
            }
            return;
        }
        ChatThemeManager chatThemeManager2 = ChatThemeManager.INSTANCE;
        Context context2 = holder.itemView.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        ChatTheme theme2 = chatThemeManager2.getCurrentTheme(context2);
        Context ctx2 = holder.itemView.getContext();
        holder.itemView.setBackgroundColor(0);
        Drawable drawable2 = ContextCompat.getDrawable(ctx2, theme2.getSentBubbleDrawable());
        baseBg = drawable2 != null ? drawable2.mutate() : null;
        if (isSelected) {
            GradientDrawable $this$onBindViewHolder_u24lambda_u243 = new GradientDrawable();
            $this$onBindViewHolder_u24lambda_u243.setColor(theme2.getOutgoingBubble());
            $this$onBindViewHolder_u24lambda_u243.setStroke(4, theme2.getAccentColor());
            $this$onBindViewHolder_u24lambda_u243.setCornerRadius(theme2.getBubbleCornerRadius());
            ((SentViewHolder) holder).getTxtBody().setBackground($this$onBindViewHolder_u24lambda_u243);
        } else {
            ((SentViewHolder) holder).getTxtBody().setBackground(baseBg);
        }
        ((SentViewHolder) holder).getTxtBody().setTextColor(theme2.getOutgoingTextColor());
        bindAttachment(((SentViewHolder) holder).getImgAttachment(), firstImageOrVideoAttachment);
        bindLinkPreview(((SentViewHolder) holder).getCardLinkPreview(), ((SentViewHolder) holder).getImgLinkPreview(), ((SentViewHolder) holder).getTxtLinkTitle(), ((SentViewHolder) holder).getTxtLinkDesc(), ((SentViewHolder) holder).getTxtLinkUrl(), message.getBody());
        ((SentViewHolder) holder).getTxtBody().setTextSize(this.textScaleFactor * 15.0f);
        ((SentViewHolder) holder).getTxtBody().setVisibility(StringsKt.isBlank(message.getBody()) ? 8 : 0);
        ((SentViewHolder) holder).getTxtBody().setText(message.getBody());
        ImageView imgStarIndicator2 = ((SentViewHolder) holder).getImgStarIndicator();
        if (imgStarIndicator2 != null) {
            imgStarIndicator2.setVisibility(message.isStarred() ? 0 : 8);
        }
        switch (WhenMappings.$EnumSwitchMapping$0[message.getBox().ordinal()]) {
            case 1:
                sentStatusText = holder.itemView.getContext().getString(R.string.status_failed);
                break;
            case 2:
                sentStatusText = holder.itemView.getContext().getString(R.string.status_sending);
                break;
            case 3:
                if (message.getId() >= 0) {
                    sentStatusText = holder.itemView.getContext().getString(R.string.status_sending);
                    Intrinsics.checkNotNull(sentStatusText);
                    break;
                } else {
                    sentStatusText = "⏰ " + new SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()).format(new Date(message.scheduledTimeOrDate()));
                    break;
                }
            default:
                sentStatusText = holder.itemView.getContext().getString(R.string.status_sent) + " · " + time;
                break;
        }
        Intrinsics.checkNotNull(sentStatusText);
        ((SentViewHolder) holder).getTxtStatus().setText(sentStatusText);
        boolean isLastHidden = isLast && this.expandedTimestampIds.contains(Long.valueOf(message.getId()));
        if (message.getBox() == MessageBox.FAILED || message.getBox() == MessageBox.OUTBOX || message.getBox() == MessageBox.QUEUED) {
            z2 = true;
        } else if (isLastHidden) {
            z2 = false;
        } else if (isLast) {
            z2 = true;
        } else {
            z2 = isTimestampExpanded;
        }
        boolean showStatus = z2;
        if (showStatus) {
            ((SentViewHolder) holder).getTxtStatus().setVisibility(0);
            ((SentViewHolder) holder).getTxtStatus().setAlpha(1.0f);
        } else {
            ((SentViewHolder) holder).getTxtStatus().setVisibility(8);
            ((SentViewHolder) holder).getTxtStatus().setAlpha(0.0f);
        }
        View itemView2 = holder.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView2, "itemView");
        Intrinsics.checkNotNull(message);
        bindClickHandlers(itemView2, message);
    }

    private final void bindClickHandlers(View itemView, final Message message) {
        itemView.setLongClickable(true);
        itemView.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.MessageAdapter$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MessageAdapter.bindClickHandlers$lambda$9(MessageAdapter.this, message, view);
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.meharenterprises.originsms.ui.thread.MessageAdapter$$ExternalSyntheticLambda6
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                boolean bindClickHandlers$lambda$10;
                bindClickHandlers$lambda$10 = MessageAdapter.bindClickHandlers$lambda$10(Message.this, this, view);
                return bindClickHandlers$lambda$10;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindClickHandlers$lambda$9(final MessageAdapter this$0, Message message, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(message, "$message");
        if (this$0.isSelectionMode) {
            if (message.getId() >= 0) {
                this$0.toggleSelection(message.getId());
            }
            this$0.onTapWhileSelecting.invoke(message);
            return;
        }
        if (message.getId() < 0 && message.getBox() == MessageBox.QUEUED) {
            this$0.onTapScheduled.invoke(message);
            return;
        }
        if (message.getBox() != MessageBox.FAILED) {
            final long msgId = message.getId();
            int i = -1;
            if (this$0.expandedTimestampIds.contains(Long.valueOf(msgId))) {
                this$0.expandedTimestampIds.remove(Long.valueOf(msgId));
                List $this$indexOfFirst$iv = this$0.getCurrentList();
                Intrinsics.checkNotNullExpressionValue($this$indexOfFirst$iv, "getCurrentList(...)");
                int index$iv = 0;
                Iterator<Message> it2 = $this$indexOfFirst$iv.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    Object item$iv = it2.next();
                    Message it3 = (Message) item$iv;
                    Message it4 = it3.getId() == msgId ? 1 : null;
                    if (it4 != null) {
                        i = index$iv;
                        break;
                    }
                    index$iv++;
                }
                int pos = i;
                if (pos >= 0) {
                    this$0.notifyItemChanged(pos);
                    return;
                }
                return;
            }
            this$0.expandedTimestampIds.add(Long.valueOf(msgId));
            List $this$indexOfFirst$iv2 = this$0.getCurrentList();
            Intrinsics.checkNotNullExpressionValue($this$indexOfFirst$iv2, "getCurrentList(...)");
            int index$iv2 = 0;
            Iterator<Message> it5 = $this$indexOfFirst$iv2.iterator();
            while (true) {
                if (!it5.hasNext()) {
                    break;
                }
                Object item$iv2 = it5.next();
                Message it6 = (Message) item$iv2;
                Message it7 = it6.getId() == msgId ? 1 : null;
                if (it7 != null) {
                    i = index$iv2;
                    break;
                }
                index$iv2++;
            }
            int pos2 = i;
            if (pos2 >= 0) {
                this$0.notifyItemChanged(pos2);
            }
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.meharenterprises.originsms.ui.thread.MessageAdapter$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    MessageAdapter.bindClickHandlers$lambda$9$lambda$8(MessageAdapter.this, msgId);
                }
            }, 3000L);
            return;
        }
        this$0.onTapFailedRetry.invoke(message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindClickHandlers$lambda$9$lambda$8(MessageAdapter this$0, long $msgId) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.expandedTimestampIds.contains(Long.valueOf($msgId))) {
            this$0.expandedTimestampIds.remove(Long.valueOf($msgId));
            List $this$indexOfFirst$iv = this$0.getCurrentList();
            Intrinsics.checkNotNullExpressionValue($this$indexOfFirst$iv, "getCurrentList(...)");
            int index$iv = 0;
            Iterator<Message> it = $this$indexOfFirst$iv.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object item$iv = it.next();
                    Message it2 = (Message) item$iv;
                    if (it2.getId() == $msgId) {
                        break;
                    } else {
                        index$iv++;
                    }
                } else {
                    index$iv = -1;
                    break;
                }
            }
            int p = index$iv;
            if (p >= 0) {
                this$0.notifyItemChanged(p);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean bindClickHandlers$lambda$10(Message message, MessageAdapter this$0, View it) {
        Intrinsics.checkNotNullParameter(message, "$message");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (message.getId() < 0) {
            return true;
        }
        if (!this$0.isSelectionMode) {
            this$0.isSelectionMode = true;
            this$0.selectedIds.add(Long.valueOf(message.getId()));
            this$0.notifyDataSetChanged();
        }
        this$0.onLongPress.invoke(message);
        return true;
    }

    private final void toggleSelection(long messageId) {
        if (this.selectedIds.contains(Long.valueOf(messageId))) {
            this.selectedIds.remove(Long.valueOf(messageId));
        } else {
            this.selectedIds.add(Long.valueOf(messageId));
        }
        if (this.selectedIds.isEmpty()) {
            this.isSelectionMode = false;
        }
        notifyDataSetChanged();
    }

    public final Set<Long> getSelectedIds() {
        return CollectionsKt.toSet(this.selectedIds);
    }

    public final void setTextScale(float scale) {
        this.textScaleFactor = scale;
        notifyDataSetChanged();
    }

    public final int getSelectedCount() {
        return this.selectedIds.size();
    }

    public final void clearSelection() {
        this.selectedIds.clear();
        this.isSelectionMode = false;
        notifyDataSetChanged();
    }

    /* compiled from: MessageAdapter.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J3\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/meharenterprises/originsms/ui/thread/MessageAdapter$LinkPreviewData;", "", "title", "", "description", "imageUrl", "url", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "getImageUrl", "getTitle", "getUrl", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes10.dex */
    public static final /* data */ class LinkPreviewData {
        private final String description;
        private final String imageUrl;
        private final String title;
        private final String url;

        public static /* synthetic */ LinkPreviewData copy$default(LinkPreviewData linkPreviewData, String str, String str2, String str3, String str4, int i, Object obj) {
            if ((i & 1) != 0) {
                str = linkPreviewData.title;
            }
            if ((i & 2) != 0) {
                str2 = linkPreviewData.description;
            }
            if ((i & 4) != 0) {
                str3 = linkPreviewData.imageUrl;
            }
            if ((i & 8) != 0) {
                str4 = linkPreviewData.url;
            }
            return linkPreviewData.copy(str, str2, str3, str4);
        }

        /* renamed from: component1, reason: from getter */
        public final String getTitle() {
            return this.title;
        }

        /* renamed from: component2, reason: from getter */
        public final String getDescription() {
            return this.description;
        }

        /* renamed from: component3, reason: from getter */
        public final String getImageUrl() {
            return this.imageUrl;
        }

        /* renamed from: component4, reason: from getter */
        public final String getUrl() {
            return this.url;
        }

        public final LinkPreviewData copy(String title, String description, String imageUrl, String url) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(description, "description");
            Intrinsics.checkNotNullParameter(url, "url");
            return new LinkPreviewData(title, description, imageUrl, url);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LinkPreviewData)) {
                return false;
            }
            LinkPreviewData linkPreviewData = (LinkPreviewData) other;
            return Intrinsics.areEqual(this.title, linkPreviewData.title) && Intrinsics.areEqual(this.description, linkPreviewData.description) && Intrinsics.areEqual(this.imageUrl, linkPreviewData.imageUrl) && Intrinsics.areEqual(this.url, linkPreviewData.url);
        }

        public int hashCode() {
            return (((((this.title.hashCode() * 31) + this.description.hashCode()) * 31) + (this.imageUrl == null ? 0 : this.imageUrl.hashCode())) * 31) + this.url.hashCode();
        }

        public String toString() {
            return "LinkPreviewData(title=" + this.title + ", description=" + this.description + ", imageUrl=" + this.imageUrl + ", url=" + this.url + ")";
        }

        public LinkPreviewData(String title, String description, String imageUrl, String url) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(description, "description");
            Intrinsics.checkNotNullParameter(url, "url");
            this.title = title;
            this.description = description;
            this.imageUrl = imageUrl;
            this.url = url;
        }

        public final String getTitle() {
            return this.title;
        }

        public final String getDescription() {
            return this.description;
        }

        public final String getImageUrl() {
            return this.imageUrl;
        }

        public final String getUrl() {
            return this.url;
        }
    }

    private final void bindLinkPreview(final View card, final ImageView img, final TextView txtTitle, final TextView txtDesc, final TextView txtUrl, String body) {
        if (card == null) {
            return;
        }
        MatchResult find$default = Regex.find$default(this.URL_REGEX, body, 0, 2, null);
        final String url = find$default != null ? find$default.getValue() : null;
        if (url == null) {
            card.setVisibility(8);
            return;
        }
        card.setVisibility(0);
        if (txtTitle != null) {
            txtTitle.setText("");
        }
        if (txtDesc != null) {
            txtDesc.setText("");
        }
        if (txtUrl != null) {
            txtUrl.setText(StringsKt.substringBefore$default(StringsKt.removePrefix(StringsKt.removePrefix(url, (CharSequence) "https://"), (CharSequence) "http://"), "/", (String) null, 2, (Object) null));
        }
        if (img != null) {
            img.setImageDrawable(null);
        }
        if (img != null) {
            img.setVisibility(8);
        }
        card.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.MessageAdapter$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MessageAdapter.bindLinkPreview$lambda$12(url, view);
            }
        });
        if (this.previewCache.containsKey((Object) url)) {
            applyPreviewData(card, img, txtTitle, txtDesc, txtUrl, (LinkPreviewData) this.previewCache.get((Object) url));
        } else {
            this.bgExecutor.execute(new Runnable() { // from class: com.meharenterprises.originsms.ui.thread.MessageAdapter$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    MessageAdapter.bindLinkPreview$lambda$14(MessageAdapter.this, url, card, img, txtTitle, txtDesc, txtUrl);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindLinkPreview$lambda$12(String $url, View it) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse($url));
            intent.addFlags(268435456);
            it.getContext().startActivity(intent);
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindLinkPreview$lambda$14(final MessageAdapter this$0, String $url, final View $card, final ImageView $img, final TextView $txtTitle, final TextView $txtDesc, final TextView $txtUrl) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        final LinkPreviewData data = this$0.fetchOgData($url);
        this$0.previewCache.put($url, data);
        this$0.mainHandler.post(new Runnable() { // from class: com.meharenterprises.originsms.ui.thread.MessageAdapter$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MessageAdapter.bindLinkPreview$lambda$14$lambda$13(MessageAdapter.this, $card, $img, $txtTitle, $txtDesc, $txtUrl, data);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindLinkPreview$lambda$14$lambda$13(MessageAdapter this$0, View $card, ImageView $img, TextView $txtTitle, TextView $txtDesc, TextView $txtUrl, LinkPreviewData $data) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.applyPreviewData($card, $img, $txtTitle, $txtDesc, $txtUrl, $data);
    }

    private final void applyPreviewData(View card, final ImageView img, TextView txtTitle, TextView txtDesc, TextView txtUrl, final LinkPreviewData data) {
        if (data == null || card == null) {
            return;
        }
        if (txtTitle != null) {
            String title = data.getTitle();
            if (StringsKt.isBlank(title)) {
                title = data.getUrl();
            }
            txtTitle.setText(title);
        }
        if (txtDesc != null) {
            txtDesc.setText(data.getDescription());
        }
        if (txtDesc != null) {
            txtDesc.setVisibility(StringsKt.isBlank(data.getDescription()) ? 8 : 0);
        }
        if (txtUrl != null) {
            txtUrl.setText(StringsKt.substringBefore$default(StringsKt.removePrefix(StringsKt.removePrefix(data.getUrl(), (CharSequence) "https://"), (CharSequence) "http://"), "/", (String) null, 2, (Object) null));
        }
        String imageUrl = data.getImageUrl();
        if (!(imageUrl == null || StringsKt.isBlank(imageUrl))) {
            if (img != null) {
                img.setVisibility(0);
            }
            this.bgExecutor.execute(new Runnable() { // from class: com.meharenterprises.originsms.ui.thread.MessageAdapter$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MessageAdapter.applyPreviewData$lambda$17(MessageAdapter.LinkPreviewData.this, this, img);
                }
            });
        } else if (img != null) {
            img.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void applyPreviewData$lambda$17(LinkPreviewData $data, MessageAdapter this$0, final ImageView $img) {
        Bitmap bitmap;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            URLConnection openConnection = new URL($data.getImageUrl()).openConnection();
            Intrinsics.checkNotNull(openConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection conn = (HttpURLConnection) openConnection;
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (Exception e) {
            bitmap = null;
        }
        final Bitmap bmp = bitmap;
        this$0.mainHandler.post(new Runnable() { // from class: com.meharenterprises.originsms.ui.thread.MessageAdapter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MessageAdapter.applyPreviewData$lambda$17$lambda$16(bmp, $img);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void applyPreviewData$lambda$17$lambda$16(Bitmap $bmp, ImageView $img) {
        if ($bmp != null) {
            if ($img != null) {
                $img.setImageBitmap($bmp);
            }
        } else {
            if ($img == null) {
                return;
            }
            $img.setVisibility(8);
        }
    }

    private final LinkPreviewData fetchOgData(String url) {
        String str;
        List<String> groupValues;
        try {
            URLConnection openConnection = new URL(url).openConnection();
            Intrinsics.checkNotNull(openConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection conn = (HttpURLConnection) openConnection;
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty(HttpHeaders.USER_AGENT, "Mozilla/5.0 (compatible; OriginSMS)");
            InputStream inputStream = conn.getInputStream();
            Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
            Reader inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
            BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
            try {
                BufferedReader it = bufferedReader;
                String html = StringsKt.take(TextStreamsKt.readText(it), 50000);
                CloseableKt.closeFinally(bufferedReader, null);
                conn.disconnect();
                String fetchOgData$ogTag = fetchOgData$ogTag(html, "title");
                if (StringsKt.isBlank(fetchOgData$ogTag)) {
                    MatchResult find$default = Regex.find$default(new Regex("<title>([^<]+)</title>", RegexOption.IGNORE_CASE), html, 0, 2, null);
                    if (find$default == null || (groupValues = find$default.getGroupValues()) == null || (str = groupValues.get(1)) == null) {
                        str = "";
                    }
                    fetchOgData$ogTag = str;
                }
                String title = fetchOgData$ogTag;
                String desc = fetchOgData$ogTag(html, "description");
                String image = fetchOgData$ogTag(html, "image");
                return new LinkPreviewData(StringsKt.take(StringsKt.trim((CharSequence) title).toString(), 100), StringsKt.take(StringsKt.trim((CharSequence) desc).toString(), 200), StringsKt.startsWith$default(image, "http", false, 2, (Object) null) ? image : null, url);
            } finally {
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static final String fetchOgData$ogTag(String html, String prop) {
        String str;
        List<String> groupValues;
        List patterns = CollectionsKt.listOf((Object[]) new Regex[]{new Regex("<meta[^>]+property=[\"']og:" + prop + "[\"'][^>]+content=[\"']([^\"']+)[\"']", RegexOption.IGNORE_CASE), new Regex("<meta[^>]+content=[\"']([^\"']+)[\"'][^>]+property=[\"']og:" + prop + "[\"']", RegexOption.IGNORE_CASE), new Regex("<meta[^>]+name=[\"']" + prop + "[\"'][^>]+content=[\"']([^\"']+)[\"']", RegexOption.IGNORE_CASE)});
        Iterator it = patterns.iterator();
        do {
            str = null;
            if (!it.hasNext()) {
                break;
            }
            Regex it2 = (Regex) it.next();
            MatchResult find$default = Regex.find$default(it2, html, 0, 2, null);
            if (find$default != null && (groupValues = find$default.getGroupValues()) != null) {
                str = groupValues.get(1);
            }
        } while (str == null);
        return str == null ? "" : str;
    }

    private final void bindAttachment(ShapeableImageView imageView, Attachment attachment) {
        if (attachment == null) {
            imageView.setVisibility(8);
            return;
        }
        imageView.setVisibility(0);
        ShapeableImageView $this$load_u24default$iv = imageView;
        Object data$iv = Uri.parse(attachment.getUri());
        Context $this$imageLoader$iv$iv = $this$load_u24default$iv.getContext();
        ImageLoader imageLoader$iv = Coil.imageLoader($this$imageLoader$iv$iv);
        ImageRequest.Builder $this$bindAttachment_u24lambda_u2421 = new ImageRequest.Builder($this$load_u24default$iv.getContext()).data(data$iv).target($this$load_u24default$iv);
        $this$bindAttachment_u24lambda_u2421.crossfade(true);
        $this$bindAttachment_u24lambda_u2421.placeholder(R.drawable.ic_attach);
        $this$bindAttachment_u24lambda_u2421.error(R.drawable.ic_attach);
        ImageRequest request$iv = $this$bindAttachment_u24lambda_u2421.build();
        imageLoader$iv.enqueue(request$iv);
    }

    private final String formatDateHeader(long millis) {
        String dayName;
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        then.setTimeInMillis(millis);
        SimpleDateFormat dayFmt = new SimpleDateFormat("EEEE", Locale.getDefault());
        SimpleDateFormat dateFmt = new SimpleDateFormat("d-MMM-yyyy", Locale.getDefault());
        SimpleDateFormat timeFmt = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        if (now.get(6) == then.get(6) && now.get(1) == then.get(1)) {
            dayName = "Today";
        } else {
            dayName = (now.get(6) - then.get(6) == 1 && now.get(1) == then.get(1)) ? "Yesterday" : dayFmt.format(then.getTime());
        }
        return dayName + " " + dateFmt.format(then.getTime()) + " " + timeFmt.format(then.getTime());
    }

    private final String formatTime(long millis) {
        if (millis == 0) {
            return "";
        }
        String format = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(Long.valueOf(millis));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }
}
