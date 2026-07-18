package com.meharenterprises.originsms.ui.thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;
import com.meharenterprises.originsms.core.ContactsHelper;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ThreadActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.thread.ThreadActivity$setupToolbar$4", f = "ThreadActivity.kt", i = {}, l = {176}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes10.dex */
public final class ThreadActivity$setupToolbar$4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ImageView $imgAvatar;
    int label;
    final /* synthetic */ ThreadActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadActivity$setupToolbar$4(ThreadActivity threadActivity, ImageView imageView, Continuation<? super ThreadActivity$setupToolbar$4> continuation) {
        super(2, continuation);
        this.this$0 = threadActivity;
        this.$imgAvatar = imageView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThreadActivity$setupToolbar$4(this.this$0, this.$imgAvatar, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ThreadActivity$setupToolbar$4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ThreadActivity$setupToolbar$4 threadActivity$setupToolbar$4;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                threadActivity$setupToolbar$4 = this;
                threadActivity$setupToolbar$4.label = 1;
                Object withContext = BuildersKt.withContext(Dispatchers.getIO(), new ThreadActivity$setupToolbar$4$contactInfo$1(threadActivity$setupToolbar$4.this$0, null), threadActivity$setupToolbar$4);
                if (withContext != coroutine_suspended) {
                    $result = withContext;
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                threadActivity$setupToolbar$4 = this;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ContactsHelper.ContactInfo contactInfo = (ContactsHelper.ContactInfo) $result;
        if (contactInfo.getPhotoUri() != null) {
            try {
                InputStream openInputStream = threadActivity$setupToolbar$4.this$0.getContentResolver().openInputStream(Uri.parse(contactInfo.getPhotoUri()));
                if (openInputStream != null) {
                    InputStream inputStream = openInputStream;
                    ImageView imageView = threadActivity$setupToolbar$4.$imgAvatar;
                    try {
                        InputStream stream = inputStream;
                        Bitmap bitmap = BitmapFactory.decodeStream(stream);
                        if (bitmap != null && imageView != null) {
                            imageView.setImageBitmap(bitmap);
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            imageView.setPadding(0, 0, 0, 0);
                            imageView.setClipToOutline(true);
                        }
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(inputStream, null);
                    } finally {
                    }
                }
            } catch (Exception e) {
            }
        }
        return Unit.INSTANCE;
    }
}
