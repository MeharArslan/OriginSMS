package com.meharenterprises.originsms.ui.compose;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: ShareTargetActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.compose.ShareTargetActivity$onCreate$1", f = "ShareTargetActivity.kt", i = {1}, l = {38, 41}, m = "invokeSuspend", n = {"threadId"}, s = {"J$0"})
/* loaded from: classes7.dex */
final class ShareTargetActivity$onCreate$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $destinationNumber;
    final /* synthetic */ String $sharedText;
    long J$0;
    int label;
    final /* synthetic */ ShareTargetActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShareTargetActivity$onCreate$1(ShareTargetActivity shareTargetActivity, String str, String str2, Continuation<? super ShareTargetActivity$onCreate$1> continuation) {
        super(2, continuation);
        this.this$0 = shareTargetActivity;
        this.$destinationNumber = str;
        this.$sharedText = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ShareTargetActivity$onCreate$1(this.this$0, this.$destinationNumber, this.$sharedText, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ShareTargetActivity$onCreate$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x006c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006d  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r12.label
            r2 = 1
            r3 = 0
            switch(r1) {
                case 0: goto L22;
                case 1: goto L1b;
                case 2: goto L13;
                default: goto Lb;
            }
        Lb:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r0)
            throw r13
        L13:
            r0 = r12
            long r3 = r0.J$0
            kotlin.ResultKt.throwOnFailure(r13)
            r1 = r13
            goto L6f
        L1b:
            r1 = r12
            kotlin.ResultKt.throwOnFailure(r13)
            r4 = r1
            r1 = r13
            goto L47
        L22:
            kotlin.ResultKt.throwOnFailure(r13)
            r1 = r12
            kotlinx.coroutines.CoroutineDispatcher r4 = kotlinx.coroutines.Dispatchers.getIO()
            kotlin.coroutines.CoroutineContext r4 = (kotlin.coroutines.CoroutineContext) r4
            com.meharenterprises.originsms.ui.compose.ShareTargetActivity$onCreate$1$threadId$1 r5 = new com.meharenterprises.originsms.ui.compose.ShareTargetActivity$onCreate$1$threadId$1
            com.meharenterprises.originsms.ui.compose.ShareTargetActivity r6 = r1.this$0
            java.lang.String r7 = r1.$destinationNumber
            r5.<init>(r6, r7, r3)
            kotlin.jvm.functions.Function2 r5 = (kotlin.jvm.functions.Function2) r5
            r6 = r1
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r1.label = r2
            java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r6)
            if (r4 != r0) goto L43
            return r0
        L43:
            r11 = r1
            r1 = r13
            r13 = r4
            r4 = r11
        L47:
            java.lang.Number r13 = (java.lang.Number) r13
            long r5 = r13.longValue()
            kotlinx.coroutines.CoroutineDispatcher r13 = kotlinx.coroutines.Dispatchers.getIO()
            kotlin.coroutines.CoroutineContext r13 = (kotlin.coroutines.CoroutineContext) r13
            com.meharenterprises.originsms.ui.compose.ShareTargetActivity$onCreate$1$displayName$1 r7 = new com.meharenterprises.originsms.ui.compose.ShareTargetActivity$onCreate$1$displayName$1
            com.meharenterprises.originsms.ui.compose.ShareTargetActivity r8 = r4.this$0
            java.lang.String r9 = r4.$destinationNumber
            r7.<init>(r8, r9, r3)
            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7
            r3 = r4
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r4.J$0 = r5
            r8 = 2
            r4.label = r8
            java.lang.Object r13 = kotlinx.coroutines.BuildersKt.withContext(r13, r7, r3)
            if (r13 != r0) goto L6d
            return r0
        L6d:
            r0 = r4
            r3 = r5
        L6f:
            java.lang.String r13 = (java.lang.String) r13
            android.content.Intent r5 = new android.content.Intent
            com.meharenterprises.originsms.ui.compose.ShareTargetActivity r6 = r0.this$0
            android.content.Context r6 = (android.content.Context) r6
            java.lang.Class<com.meharenterprises.originsms.ui.thread.ThreadActivity> r7 = com.meharenterprises.originsms.ui.thread.ThreadActivity.class
            r5.<init>(r6, r7)
            java.lang.String r6 = r0.$destinationNumber
            java.lang.String r7 = r0.$sharedText
            r8 = r5
            r9 = 0
            java.lang.String r10 = "extra_thread_id"
            r8.putExtra(r10, r3)
            java.lang.String r3 = "extra_address"
            r8.putExtra(r3, r6)
            java.lang.String r3 = "extra_display_name"
            r8.putExtra(r3, r13)
            r13 = r7
            java.lang.CharSequence r13 = (java.lang.CharSequence) r13
            if (r13 == 0) goto L9e
            boolean r13 = kotlin.text.StringsKt.isBlank(r13)
            if (r13 == 0) goto L9d
            goto L9e
        L9d:
            r2 = 0
        L9e:
            if (r2 != 0) goto La5
            java.lang.String r13 = "extra_prefill_body"
            r8.putExtra(r13, r7)
        La5:
            r13 = r5
            com.meharenterprises.originsms.ui.compose.ShareTargetActivity r2 = r0.this$0
            r2.startActivity(r13)
            com.meharenterprises.originsms.ui.compose.ShareTargetActivity r2 = r0.this$0
            r2.finish()
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.compose.ShareTargetActivity$onCreate$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
