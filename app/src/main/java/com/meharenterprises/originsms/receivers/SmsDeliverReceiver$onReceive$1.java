package com.meharenterprises.originsms.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: SmsDeliverReceiver.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.receivers.SmsDeliverReceiver$onReceive$1", f = "SmsDeliverReceiver.kt", i = {}, l = {43}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes4.dex */
final class SmsDeliverReceiver$onReceive$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Context $context;
    final /* synthetic */ String $fullBody;
    final /* synthetic */ BroadcastReceiver.PendingResult $pendingResult;
    final /* synthetic */ String $sender;
    final /* synthetic */ long $timestamp;
    int label;
    final /* synthetic */ SmsDeliverReceiver this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmsDeliverReceiver$onReceive$1(String str, Context context, String str2, SmsDeliverReceiver smsDeliverReceiver, BroadcastReceiver.PendingResult pendingResult, long j, Continuation<? super SmsDeliverReceiver$onReceive$1> continuation) {
        super(2, continuation);
        this.$sender = str;
        this.$context = context;
        this.$fullBody = str2;
        this.this$0 = smsDeliverReceiver;
        this.$pendingResult = pendingResult;
        this.$timestamp = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new SmsDeliverReceiver$onReceive$1(this.$sender, this.$context, this.$fullBody, this.this$0, this.$pendingResult, this.$timestamp, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((SmsDeliverReceiver$onReceive$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0007. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004b A[Catch: all -> 0x00c6, TRY_LEAVE, TryCatch #0 {all -> 0x00c6, blocks: (B:11:0x0043, B:13:0x004b), top: B:10:0x0043 }] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r11.label
            r2 = 1
            switch(r1) {
                case 0: goto L1c;
                case 1: goto L12;
                default: goto La;
            }
        La:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r0)
            throw r12
        L12:
            r0 = r11
            kotlin.ResultKt.throwOnFailure(r12)     // Catch: java.lang.Throwable -> L19
            r1 = r0
            r0 = r12
            goto L43
        L19:
            r1 = move-exception
            goto Ld0
        L1c:
            kotlin.ResultKt.throwOnFailure(r12)
            r1 = r11
            com.meharenterprises.originsms.core.ContactsHelper$Companion r3 = com.meharenterprises.originsms.core.ContactsHelper.INSTANCE     // Catch: java.lang.Throwable -> Lcc
            java.lang.String r4 = r1.$sender     // Catch: java.lang.Throwable -> Lcc
            java.lang.String r3 = r3.normalize(r4)     // Catch: java.lang.Throwable -> Lcc
            com.meharenterprises.originsms.data.db.OriginDatabase$Companion r4 = com.meharenterprises.originsms.data.db.OriginDatabase.INSTANCE     // Catch: java.lang.Throwable -> Lcc
            android.content.Context r5 = r1.$context     // Catch: java.lang.Throwable -> Lcc
            com.meharenterprises.originsms.data.db.OriginDatabase r4 = r4.getInstance(r5)     // Catch: java.lang.Throwable -> Lcc
            com.meharenterprises.originsms.data.db.BlockedNumberDao r4 = r4.blockedNumberDao()     // Catch: java.lang.Throwable -> Lcc
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5     // Catch: java.lang.Throwable -> Lcc
            r1.label = r2     // Catch: java.lang.Throwable -> Lcc
            java.lang.Object r4 = r4.isBlocked(r3, r5)     // Catch: java.lang.Throwable -> Lcc
            if (r4 != r0) goto L41
            return r0
        L41:
            r0 = r12
            r12 = r4
        L43:
            java.lang.Boolean r12 = (java.lang.Boolean) r12     // Catch: java.lang.Throwable -> Lc6
            boolean r12 = r12.booleanValue()     // Catch: java.lang.Throwable -> Lc6
            if (r12 != 0) goto Lbd
            android.content.ContentValues r12 = new android.content.ContentValues     // Catch: java.lang.Throwable -> Lc6
            r12.<init>()     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r3 = r1.$sender     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r4 = r1.$fullBody     // Catch: java.lang.Throwable -> Lc6
            long r5 = r1.$timestamp     // Catch: java.lang.Throwable -> Lc6
            r7 = r12
            r8 = 0
            java.lang.String r9 = "address"
            r7.put(r9, r3)     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r3 = "body"
            r7.put(r3, r4)     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r3 = "date"
            java.lang.Long r4 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r5)     // Catch: java.lang.Throwable -> Lc6
            r7.put(r3, r4)     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r3 = "read"
            r4 = 0
            java.lang.Integer r5 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r4)     // Catch: java.lang.Throwable -> Lc6
            r7.put(r3, r5)     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r3 = "seen"
            java.lang.Integer r4 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r4)     // Catch: java.lang.Throwable -> Lc6
            r7.put(r3, r4)     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r3 = "type"
            java.lang.Integer r2 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r2)     // Catch: java.lang.Throwable -> Lc6
            r7.put(r3, r2)     // Catch: java.lang.Throwable -> Lc6
            android.content.Context r2 = r1.$context     // Catch: java.lang.Throwable -> Lc6
            android.content.ContentResolver r2 = r2.getContentResolver()     // Catch: java.lang.Throwable -> Lc6
            android.net.Uri r3 = android.provider.Telephony.Sms.CONTENT_URI     // Catch: java.lang.Throwable -> Lc6
            android.net.Uri r2 = r2.insert(r3, r12)     // Catch: java.lang.Throwable -> Lc6
            com.meharenterprises.originsms.core.ContactsHelper r3 = new com.meharenterprises.originsms.core.ContactsHelper     // Catch: java.lang.Throwable -> Lc6
            android.content.Context r4 = r1.$context     // Catch: java.lang.Throwable -> Lc6
            r3.<init>(r4)     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r4 = r1.$sender     // Catch: java.lang.Throwable -> Lc6
            com.meharenterprises.originsms.core.ContactsHelper$ContactInfo r3 = r3.resolve(r4)     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r6 = r3.getDisplayName()     // Catch: java.lang.Throwable -> Lc6
            com.meharenterprises.originsms.services.NotificationHelper r4 = new com.meharenterprises.originsms.services.NotificationHelper     // Catch: java.lang.Throwable -> Lc6
            android.content.Context r3 = r1.$context     // Catch: java.lang.Throwable -> Lc6
            r4.<init>(r3)     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r5 = r1.$sender     // Catch: java.lang.Throwable -> Lc6
            java.lang.String r7 = r1.$fullBody     // Catch: java.lang.Throwable -> Lc6
            com.meharenterprises.originsms.receivers.SmsDeliverReceiver r3 = r1.this$0     // Catch: java.lang.Throwable -> Lc6
            android.content.Context r8 = r1.$context     // Catch: java.lang.Throwable -> Lc6
            long r8 = com.meharenterprises.originsms.receivers.SmsDeliverReceiver.access$extractThreadId(r3, r2, r8)     // Catch: java.lang.Throwable -> Lc6
            r4.showIncomingMessageNotification(r5, r6, r7, r8)     // Catch: java.lang.Throwable -> Lc6
        Lbd:
            android.content.BroadcastReceiver$PendingResult r12 = r1.$pendingResult
            r12.finish()
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        Lc6:
            r12 = move-exception
            r10 = r1
            r1 = r12
            r12 = r0
            r0 = r10
            goto Ld0
        Lcc:
            r0 = move-exception
            r10 = r1
            r1 = r0
            r0 = r10
        Ld0:
            android.content.BroadcastReceiver$PendingResult r2 = r0.$pendingResult
            r2.finish()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.receivers.SmsDeliverReceiver$onReceive$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
