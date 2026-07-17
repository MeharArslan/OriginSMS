package com.meharenterprises.originsms.ui.thread;

import android.widget.EditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ThreadActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.thread.ThreadActivity$restoreDraft$1", f = "ThreadActivity.kt", i = {}, l = {1172}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes10.dex */
public final class ThreadActivity$restoreDraft$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ ThreadActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadActivity$restoreDraft$1(ThreadActivity threadActivity, Continuation<? super ThreadActivity$restoreDraft$1> continuation) {
        super(2, continuation);
        this.this$0 = threadActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThreadActivity$restoreDraft$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ThreadActivity$restoreDraft$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ThreadActivity$restoreDraft$1 threadActivity$restoreDraft$1;
        ThreadViewModel threadViewModel;
        long j;
        EditText editText;
        EditText editText2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        boolean z = true;
        EditText editText3 = null;
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                threadActivity$restoreDraft$1 = this;
                threadViewModel = threadActivity$restoreDraft$1.this$0.viewModel;
                if (threadViewModel == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewModel");
                    threadViewModel = null;
                }
                j = threadActivity$restoreDraft$1.this$0.threadId;
                threadActivity$restoreDraft$1.label = 1;
                Object draft = threadViewModel.getDraft(j, threadActivity$restoreDraft$1);
                if (draft != coroutine_suspended) {
                    $result = draft;
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                threadActivity$restoreDraft$1 = this;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        String draft2 = (String) $result;
        String str = draft2;
        if (str != null && !StringsKt.isBlank(str)) {
            z = false;
        }
        if (!z) {
            editText = threadActivity$restoreDraft$1.this$0.editMessage;
            if (editText == null) {
                Intrinsics.throwUninitializedPropertyAccessException("editMessage");
                editText = null;
            }
            editText.setText(draft2);
            editText2 = threadActivity$restoreDraft$1.this$0.editMessage;
            if (editText2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("editMessage");
            } else {
                editText3 = editText2;
            }
            editText3.setSelection(draft2.length());
        }
        return Unit.INSTANCE;
    }
}
