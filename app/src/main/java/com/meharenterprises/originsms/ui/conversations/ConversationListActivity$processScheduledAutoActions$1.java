package com.meharenterprises.originsms.ui.conversations;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ConversationListActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.conversations.ConversationListActivity$processScheduledAutoActions$1", f = "ConversationListActivity.kt", i = {0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6, 7, 7, 8, 9}, l = {145, 147, 148, 152, 157, 162, 163, 164, 167, 168}, m = "invokeSuspend", n = {"dao", "now", "currentDayMinutes", "dao", "entry", "now", "currentDayMinutes", "dao", "now", "currentDayMinutes", "dao", "now", "currentDayMinutes", "dao", "now", "currentDayMinutes", "dao", "now", "dao", "entry", "now", "dao", "now", "dao", "dao"}, s = {"L$0", "J$0", "I$0", "L$0", "L$3", "J$0", "I$0", "L$0", "J$0", "I$0", "L$0", "J$0", "I$0", "L$0", "J$0", "I$0", "L$0", "J$0", "L$0", "L$2", "J$0", "L$0", "J$0", "L$0", "L$0"})
/* loaded from: classes11.dex */
public final class ConversationListActivity$processScheduledAutoActions$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int I$0;
    long J$0;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    final /* synthetic */ ConversationListActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConversationListActivity$processScheduledAutoActions$1(ConversationListActivity conversationListActivity, Continuation<? super ConversationListActivity$processScheduledAutoActions$1> continuation) {
        super(2, continuation);
        this.this$0 = conversationListActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ConversationListActivity$processScheduledAutoActions$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ConversationListActivity$processScheduledAutoActions$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:10:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x031b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x02b8 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x02b9  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0257 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01b4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01c1  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x02b9 -> B:25:0x02c1). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:56:0x0238 -> B:44:0x023f). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:74:0x01b5 -> B:64:0x01bc). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r39) {
        /*
            Method dump skipped, instructions count: 844
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.conversations.ConversationListActivity$processScheduledAutoActions$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
