package com.meharenterprises.originsms.lock;

import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.core.ConversationSummary;
import com.meharenterprises.originsms.data.db.OriginDatabase;
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
/* compiled from: LockUnlockActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.lock.LockUnlockActivity$showScheduleUnhideDialog$1$1", f = "LockUnlockActivity.kt", i = {}, l = {297}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes8.dex */
public final class LockUnlockActivity$showScheduleUnhideDialog$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ConversationSummary $conversation;
    final /* synthetic */ long $unhideAt;
    int label;
    final /* synthetic */ LockUnlockActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockUnlockActivity$showScheduleUnhideDialog$1$1(LockUnlockActivity lockUnlockActivity, ConversationSummary conversationSummary, long j, Continuation<? super LockUnlockActivity$showScheduleUnhideDialog$1$1> continuation) {
        super(2, continuation);
        this.this$0 = lockUnlockActivity;
        this.$conversation = conversationSummary;
        this.$unhideAt = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new LockUnlockActivity$showScheduleUnhideDialog$1$1(this.this$0, this.$conversation, this.$unhideAt, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((LockUnlockActivity$showScheduleUnhideDialog$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        LockUnlockActivity$showScheduleUnhideDialog$1$1 lockUnlockActivity$showScheduleUnhideDialog$1$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                if (OriginDatabase.INSTANCE.getInstance(this.this$0).threadLockDao().setAutoUnhideAt(this.$conversation.getThreadId(), this.$unhideAt, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                lockUnlockActivity$showScheduleUnhideDialog$1$1 = this;
                break;
            case 1:
                lockUnlockActivity$showScheduleUnhideDialog$1$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Toast.makeText(lockUnlockActivity$showScheduleUnhideDialog$1$1.this$0, lockUnlockActivity$showScheduleUnhideDialog$1$1.this$0.getString(R.string.schedule_auto_unhide_confirmed), 0).show();
        return Unit.INSTANCE;
    }
}
