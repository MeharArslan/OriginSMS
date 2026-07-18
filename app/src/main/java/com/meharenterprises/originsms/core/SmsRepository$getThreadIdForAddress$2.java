package com.meharenterprises.originsms.core;

import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.data.db.OriginDatabase;
import com.meharenterprises.originsms.data.db.ThreadLockEntity;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SmsRepository.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.core.SmsRepository$getThreadIdForAddress$2", f = "SmsRepository.kt", i = {0}, l = {ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_TAG}, m = "invokeSuspend", n = {"threadId"}, s = {"J$0"})
/* loaded from: classes8.dex */
public final class SmsRepository$getThreadIdForAddress$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Long>, Object> {
    final /* synthetic */ String $address;
    long J$0;
    int label;
    final /* synthetic */ SmsRepository this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmsRepository$getThreadIdForAddress$2(String str, SmsRepository smsRepository, Continuation<? super SmsRepository$getThreadIdForAddress$2> continuation) {
        super(2, continuation);
        this.$address = str;
        this.this$0 = smsRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new SmsRepository$getThreadIdForAddress$2(this.$address, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Long> continuation) {
        return ((SmsRepository$getThreadIdForAddress$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Context context;
        long j;
        long threadId;
        Context context2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                String normalized = ContactsHelper.INSTANCE.normalize(this.$address);
                context = this.this$0.context;
                Cursor query = context.getContentResolver().query(Telephony.Sms.CONTENT_URI, new String[]{"thread_id", "address"}, "address = ?", new String[]{normalized}, "date DESC LIMIT 1");
                if (query != null) {
                    Cursor cursor = query;
                    try {
                        Cursor cursor2 = cursor;
                        j = cursor2.moveToFirst() ? cursor2.getLong(cursor2.getColumnIndexOrThrow("thread_id")) : -1L;
                        CloseableKt.closeFinally(cursor, null);
                    } finally {
                    }
                } else {
                    j = -1;
                }
                threadId = j;
                if (threadId != -1) {
                    OriginDatabase.Companion companion = OriginDatabase.INSTANCE;
                    context2 = this.this$0.context;
                    this.J$0 = threadId;
                    this.label = 1;
                    Object forThread = companion.getInstance(context2).threadLockDao().getForThread(threadId, this);
                    if (forThread != coroutine_suspended) {
                        $result = forThread;
                        break;
                    } else {
                        return coroutine_suspended;
                    }
                } else {
                    return Boxing.boxLong(-1L);
                }
            case 1:
                threadId = this.J$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ThreadLockEntity lockState = (ThreadLockEntity) $result;
        return Boxing.boxLong((lockState != null ? lockState.getDeletedAtMillis() : 0L) <= 0 ? threadId : -1L);
    }
}
