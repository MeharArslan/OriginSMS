package com.meharenterprises.originsms.ui.compose;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ComposeActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.compose.ComposeActivity$attemptSend$1", f = "ComposeActivity.kt", i = {1}, l = {261, 264}, m = "invokeSuspend", n = {"resolvedThreadId"}, s = {"J$0"})
/* loaded from: classes7.dex */
public final class ComposeActivity$attemptSend$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $body;
    final /* synthetic */ String $destination;
    long J$0;
    int label;
    final /* synthetic */ ComposeActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComposeActivity$attemptSend$1(ComposeActivity composeActivity, String str, String str2, Continuation<? super ComposeActivity$attemptSend$1> continuation) {
        super(2, continuation);
        this.this$0 = composeActivity;
        this.$destination = str;
        this.$body = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ComposeActivity$attemptSend$1(this.this$0, this.$destination, this.$body, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ComposeActivity$attemptSend$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0007. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00b7 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00b8  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 246
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.compose.ComposeActivity$attemptSend$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
