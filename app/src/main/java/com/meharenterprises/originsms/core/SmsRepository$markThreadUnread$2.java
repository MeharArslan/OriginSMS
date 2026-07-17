package com.meharenterprises.originsms.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;
import androidx.constraintlayout.widget.ConstraintLayout;
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
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.core.SmsRepository$markThreadUnread$2", f = "SmsRepository.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes8.dex */
public final class SmsRepository$markThreadUnread$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ long $threadId;
    int label;
    final /* synthetic */ SmsRepository this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmsRepository$markThreadUnread$2(SmsRepository smsRepository, long j, Continuation<? super SmsRepository$markThreadUnread$2> continuation) {
        super(2, continuation);
        this.this$0 = smsRepository;
        this.$threadId = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new SmsRepository$markThreadUnread$2(this.this$0, this.$threadId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((SmsRepository$markThreadUnread$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Context context;
        Context context2;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                context = this.this$0.context;
                Cursor query = context.getContentResolver().query(Telephony.Sms.CONTENT_URI, new String[]{"_id"}, "thread_id = ?", new String[]{String.valueOf(this.$threadId)}, "date DESC LIMIT 1");
                Long l = null;
                if (query != null) {
                    Cursor cursor = query;
                    try {
                        Cursor c = cursor;
                        Long boxLong = c.moveToFirst() ? Boxing.boxLong(c.getLong(c.getColumnIndex("_id"))) : null;
                        CloseableKt.closeFinally(cursor, null);
                        l = boxLong;
                    } finally {
                    }
                }
                Long latestId = l;
                if (latestId != null) {
                    ContentValues values = new ContentValues();
                    values.put("read", Boxing.boxInt(0));
                    context2 = this.this$0.context;
                    context2.getContentResolver().update(Telephony.Sms.CONTENT_URI, values, "_id = ?", new String[]{latestId.toString()});
                }
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
