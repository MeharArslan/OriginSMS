package com.meharenterprises.originsms.ui.compose;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* compiled from: ComposeActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.compose.ComposeActivity$onCreate$8", f = "ComposeActivity.kt", i = {}, l = {163}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
final class ComposeActivity$onCreate$8 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ ComposeActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComposeActivity$onCreate$8(ComposeActivity composeActivity, Continuation<? super ComposeActivity$onCreate$8> continuation) {
        super(2, continuation);
        this.this$0 = composeActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ComposeActivity$onCreate$8(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ComposeActivity$onCreate$8) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ComposeActivity$onCreate$8 composeActivity$onCreate$8;
        ContactPickerAdapter contactPickerAdapter;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        ContactPickerAdapter contactPickerAdapter2 = null;
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                composeActivity$onCreate$8 = this;
                composeActivity$onCreate$8.label = 1;
                Object withContext = BuildersKt.withContext(Dispatchers.getIO(), new ComposeActivity$onCreate$8$contacts$1(composeActivity$onCreate$8.this$0, null), composeActivity$onCreate$8);
                if (withContext != coroutine_suspended) {
                    $result = withContext;
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                composeActivity$onCreate$8 = this;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        List contacts = (List) $result;
        contactPickerAdapter = composeActivity$onCreate$8.this$0.contactPickerAdapter;
        if (contactPickerAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("contactPickerAdapter");
        } else {
            contactPickerAdapter2 = contactPickerAdapter;
        }
        contactPickerAdapter2.submitContacts(contacts);
        return Unit.INSTANCE;
    }
}
