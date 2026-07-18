package com.meharenterprises.originsms.ui.compose;

import com.meharenterprises.originsms.core.ContactsHelper;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: ComposeActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/meharenterprises/originsms/core/ContactsHelper$ContactInfo;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.compose.ComposeActivity$attemptSend$1$contactInfo$1", f = "ComposeActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
final class ComposeActivity$attemptSend$1$contactInfo$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super ContactsHelper.ContactInfo>, Object> {
    final /* synthetic */ String $destination;
    int label;
    final /* synthetic */ ComposeActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComposeActivity$attemptSend$1$contactInfo$1(ComposeActivity composeActivity, String str, Continuation<? super ComposeActivity$attemptSend$1$contactInfo$1> continuation) {
        super(2, continuation);
        this.this$0 = composeActivity;
        this.$destination = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ComposeActivity$attemptSend$1$contactInfo$1(this.this$0, this.$destination, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super ContactsHelper.ContactInfo> continuation) {
        return ((ComposeActivity$attemptSend$1$contactInfo$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ContactsHelper contactsHelper;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                contactsHelper = this.this$0.contactsHelper;
                if (contactsHelper == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("contactsHelper");
                    contactsHelper = null;
                }
                return contactsHelper.resolve(this.$destination);
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
