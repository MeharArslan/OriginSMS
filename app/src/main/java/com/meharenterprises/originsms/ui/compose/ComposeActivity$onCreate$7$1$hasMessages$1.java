package com.meharenterprises.originsms.ui.compose;

import android.database.Cursor;
import android.provider.Telephony;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ComposeActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.compose.ComposeActivity$onCreate$7$1$hasMessages$1", f = "ComposeActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
public final class ComposeActivity$onCreate$7$1$hasMessages$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    final /* synthetic */ long $existingThreadId;
    int label;
    final /* synthetic */ ComposeActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComposeActivity$onCreate$7$1$hasMessages$1(ComposeActivity composeActivity, long j, Continuation<? super ComposeActivity$onCreate$7$1$hasMessages$1> continuation) {
        super(2, continuation);
        this.this$0 = composeActivity;
        this.$existingThreadId = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ComposeActivity$onCreate$7$1$hasMessages$1(this.this$0, this.$existingThreadId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
        return ((ComposeActivity$onCreate$7$1$hasMessages$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                boolean z = false;
                try {
                    Cursor cursor = this.this$0.getContentResolver().query(Telephony.Sms.CONTENT_URI, new String[]{"_id"}, "thread_id = ?", new String[]{String.valueOf(this.$existingThreadId)}, null);
                    int count = cursor != null ? cursor.getCount() : 0;
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (count > 0) {
                        z = true;
                    }
                } catch (Exception e) {
                }
                return Boxing.boxBoolean(z);
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
