package com.meharenterprises.originsms.ui;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.core.ConversationSummary;
import com.meharenterprises.originsms.core.SmsRepository;
import java.util.ArrayList;
import java.util.Collection;
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
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* compiled from: ArchivedChatsActivity.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "Lcom/meharenterprises/originsms/core/ConversationSummary;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.ArchivedChatsActivity$loadArchived$1$archived$1", f = "ArchivedChatsActivity.kt", i = {}, l = {LockFreeTaskQueueCore.CLOSED_SHIFT}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class ArchivedChatsActivity$loadArchived$1$archived$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<? extends ConversationSummary>>, Object> {
    int label;
    final /* synthetic */ ArchivedChatsActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArchivedChatsActivity$loadArchived$1$archived$1(ArchivedChatsActivity archivedChatsActivity, Continuation<? super ArchivedChatsActivity$loadArchived$1$archived$1> continuation) {
        super(2, continuation);
        this.this$0 = archivedChatsActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ArchivedChatsActivity$loadArchived$1$archived$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super List<? extends ConversationSummary>> continuation) {
        return invoke2(coroutineScope, (Continuation<? super List<ConversationSummary>>) continuation);
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super List<ConversationSummary>> continuation) {
        return ((ArchivedChatsActivity$loadArchived$1$archived$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        SmsRepository smsRepository;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                smsRepository = this.this$0.repository;
                if (smsRepository == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("repository");
                    smsRepository = null;
                }
                this.label = 1;
                Object conversations = smsRepository.getConversations(this);
                if (conversations == coroutine_suspended) {
                    return coroutine_suspended;
                }
                $result = conversations;
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Iterable $this$filterTo$iv$iv = (Iterable) $result;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            ConversationSummary it = (ConversationSummary) element$iv$iv;
            if (((!it.isArchived() || it.isHidden() || it.isDeleted()) ? null : 1) != null) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }
}
