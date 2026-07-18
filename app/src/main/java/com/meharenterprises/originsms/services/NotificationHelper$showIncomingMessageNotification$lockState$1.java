package com.meharenterprises.originsms.services;

import android.content.Context;
import com.meharenterprises.originsms.data.db.OriginDatabase;
import com.meharenterprises.originsms.data.db.ThreadLockEntity;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NotificationHelper.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/meharenterprises/originsms/data/db/ThreadLockEntity;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.services.NotificationHelper$showIncomingMessageNotification$lockState$1", f = "NotificationHelper.kt", i = {}, l = {41}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes5.dex */
public final class NotificationHelper$showIncomingMessageNotification$lockState$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super ThreadLockEntity>, Object> {
    final /* synthetic */ long $threadId;
    int label;
    final /* synthetic */ NotificationHelper this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationHelper$showIncomingMessageNotification$lockState$1(NotificationHelper notificationHelper, long j, Continuation<? super NotificationHelper$showIncomingMessageNotification$lockState$1> continuation) {
        super(2, continuation);
        this.this$0 = notificationHelper;
        this.$threadId = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NotificationHelper$showIncomingMessageNotification$lockState$1(this.this$0, this.$threadId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super ThreadLockEntity> continuation) {
        return ((NotificationHelper$showIncomingMessageNotification$lockState$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Context context;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                OriginDatabase.Companion companion = OriginDatabase.INSTANCE;
                context = this.this$0.context;
                this.label = 1;
                Object forThread = companion.getInstance(context).threadLockDao().getForThread(this.$threadId, this);
                return forThread == coroutine_suspended ? coroutine_suspended : forThread;
            case 1:
                ResultKt.throwOnFailure($result);
                return $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
