package com.meharenterprises.originsms.core;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SmsRepository.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "Lcom/meharenterprises/originsms/core/ConversationSummary;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.core.SmsRepository$getConversations$2", f = "SmsRepository.kt", i = {0, 0, 1, 1, 1}, l = {72, 77}, m = "invokeSuspend", n = {"results", "projection", "results", "projection", "allLockStates"}, s = {"L$0", "L$1", "L$0", "L$1", "L$2"})
/* loaded from: classes8.dex */
public final class SmsRepository$getConversations$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<ConversationSummary>>, Object> {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ SmsRepository this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmsRepository$getConversations$2(SmsRepository smsRepository, Continuation<? super SmsRepository$getConversations$2> continuation) {
        super(2, continuation);
        this.this$0 = smsRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new SmsRepository$getConversations$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super List<ConversationSummary>> continuation) {
        return ((SmsRepository$getConversations$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:3:0x0019. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:131:0x033b A[Catch: all -> 0x03d8, TryCatch #4 {all -> 0x03d8, blocks: (B:57:0x01e2, B:59:0x01ea, B:60:0x01f0, B:61:0x0202, B:68:0x020d, B:70:0x0213, B:72:0x021d, B:75:0x0224, B:77:0x0233, B:78:0x0237, B:90:0x026e, B:91:0x0280, B:93:0x0286, B:95:0x029f, B:98:0x02a7, B:105:0x02b1, B:107:0x02b8, B:110:0x02c3, B:113:0x02e3, B:115:0x0302, B:119:0x0310, B:123:0x031e, B:127:0x032c, B:131:0x033b, B:136:0x034d, B:137:0x035a, B:139:0x0366, B:140:0x036f, B:142:0x037f, B:144:0x0385, B:149:0x039b, B:154:0x03ab, B:33:0x03cf), top: B:56:0x01e2 }] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0346  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x034d A[Catch: all -> 0x03d8, TryCatch #4 {all -> 0x03d8, blocks: (B:57:0x01e2, B:59:0x01ea, B:60:0x01f0, B:61:0x0202, B:68:0x020d, B:70:0x0213, B:72:0x021d, B:75:0x0224, B:77:0x0233, B:78:0x0237, B:90:0x026e, B:91:0x0280, B:93:0x0286, B:95:0x029f, B:98:0x02a7, B:105:0x02b1, B:107:0x02b8, B:110:0x02c3, B:113:0x02e3, B:115:0x0302, B:119:0x0310, B:123:0x031e, B:127:0x032c, B:131:0x033b, B:136:0x034d, B:137:0x035a, B:139:0x0366, B:140:0x036f, B:142:0x037f, B:144:0x0385, B:149:0x039b, B:154:0x03ab, B:33:0x03cf), top: B:56:0x01e2 }] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0366 A[Catch: all -> 0x03d8, TryCatch #4 {all -> 0x03d8, blocks: (B:57:0x01e2, B:59:0x01ea, B:60:0x01f0, B:61:0x0202, B:68:0x020d, B:70:0x0213, B:72:0x021d, B:75:0x0224, B:77:0x0233, B:78:0x0237, B:90:0x026e, B:91:0x0280, B:93:0x0286, B:95:0x029f, B:98:0x02a7, B:105:0x02b1, B:107:0x02b8, B:110:0x02c3, B:113:0x02e3, B:115:0x0302, B:119:0x0310, B:123:0x031e, B:127:0x032c, B:131:0x033b, B:136:0x034d, B:137:0x035a, B:139:0x0366, B:140:0x036f, B:142:0x037f, B:144:0x0385, B:149:0x039b, B:154:0x03ab, B:33:0x03cf), top: B:56:0x01e2 }] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0391  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0396  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0133 A[Catch: Exception -> 0x014f, TRY_LEAVE, TryCatch #1 {Exception -> 0x014f, blocks: (B:11:0x010b, B:12:0x012d, B:14:0x0133, B:207:0x00ec), top: B:206:0x00ec }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x039f  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x03a6  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x03a9  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x03a2  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0393  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x036d  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0358  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0349  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0340  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x00d0 A[Catch: Exception -> 0x03ff, TRY_LEAVE, TryCatch #0 {Exception -> 0x03ff, blocks: (B:199:0x00a7, B:200:0x00ca, B:202:0x00d0), top: B:198:0x00a7 }] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0108 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x03ee  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x017c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0233 A[Catch: all -> 0x03d8, TryCatch #4 {all -> 0x03d8, blocks: (B:57:0x01e2, B:59:0x01ea, B:60:0x01f0, B:61:0x0202, B:68:0x020d, B:70:0x0213, B:72:0x021d, B:75:0x0224, B:77:0x0233, B:78:0x0237, B:90:0x026e, B:91:0x0280, B:93:0x0286, B:95:0x029f, B:98:0x02a7, B:105:0x02b1, B:107:0x02b8, B:110:0x02c3, B:113:0x02e3, B:115:0x0302, B:119:0x0310, B:123:0x031e, B:127:0x032c, B:131:0x033b, B:136:0x034d, B:137:0x035a, B:139:0x0366, B:140:0x036f, B:142:0x037f, B:144:0x0385, B:149:0x039b, B:154:0x03ab, B:33:0x03cf), top: B:56:0x01e2 }] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r50) {
        /*
            Method dump skipped, instructions count: 1052
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.core.SmsRepository$getConversations$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
