package com.meharenterprises.originsms.ui;

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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SettingsActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.SettingsActivity$showDailyHideTimePicker$1$1", f = "SettingsActivity.kt", i = {}, l = {332}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
public final class SettingsActivity$showDailyHideTimePicker$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ConversationSummary $conversation;
    int label;
    final /* synthetic */ SettingsActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsActivity$showDailyHideTimePicker$1$1(SettingsActivity settingsActivity, ConversationSummary conversationSummary, Continuation<? super SettingsActivity$showDailyHideTimePicker$1$1> continuation) {
        super(2, continuation);
        this.this$0 = settingsActivity;
        this.$conversation = conversationSummary;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new SettingsActivity$showDailyHideTimePicker$1$1(this.this$0, this.$conversation, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((SettingsActivity$showDailyHideTimePicker$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SettingsActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.meharenterprises.originsms.ui.SettingsActivity$showDailyHideTimePicker$1$1$1", f = "SettingsActivity.kt", i = {}, l = {336}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.meharenterprises.originsms.ui.SettingsActivity$showDailyHideTimePicker$1$1$1, reason: invalid class name */
    /* loaded from: classes3.dex */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ConversationSummary $conversation;
        int label;
        final /* synthetic */ SettingsActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(SettingsActivity settingsActivity, ConversationSummary conversationSummary, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = settingsActivity;
            this.$conversation = conversationSummary;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.this$0, this.$conversation, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object $result) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    this.label = 1;
                    if (OriginDatabase.INSTANCE.getInstance(this.this$0).threadLockDao().setDailyHideTime(this.$conversation.getThreadId(), -1, this) != coroutine_suspended) {
                        break;
                    } else {
                        return coroutine_suspended;
                    }
                case 1:
                    ResultKt.throwOnFailure($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return Unit.INSTANCE;
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        SettingsActivity$showDailyHideTimePicker$1$1 settingsActivity$showDailyHideTimePicker$1$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass1(this.this$0, this.$conversation, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                settingsActivity$showDailyHideTimePicker$1$1 = this;
                break;
            case 1:
                settingsActivity$showDailyHideTimePicker$1$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Toast.makeText(settingsActivity$showDailyHideTimePicker$1$1.this$0, settingsActivity$showDailyHideTimePicker$1$1.this$0.getString(R.string.auto_hide_timer_disabled), 0).show();
        return Unit.INSTANCE;
    }
}
