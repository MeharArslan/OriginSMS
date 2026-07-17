package com.meharenterprises.originsms.ui.conversations;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.data.db.OriginDatabase;
import com.meharenterprises.originsms.data.db.ThreadLockDao;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: ConversationListViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.conversations.ConversationListViewModel$setMuted$1", f = "ConversationListViewModel.kt", i = {}, l = {TypedValues.TYPE_TARGET}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes11.dex */
final class ConversationListViewModel$setMuted$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ boolean $muted;
    final /* synthetic */ Set<Long> $threadIds;
    Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ ConversationListViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConversationListViewModel$setMuted$1(Set<Long> set, ConversationListViewModel conversationListViewModel, boolean z, Continuation<? super ConversationListViewModel$setMuted$1> continuation) {
        super(2, continuation);
        this.$threadIds = set;
        this.this$0 = conversationListViewModel;
        this.$muted = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ConversationListViewModel$setMuted$1(this.$threadIds, this.this$0, this.$muted, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ConversationListViewModel$setMuted$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ConversationListViewModel$setMuted$1 conversationListViewModel$setMuted$1;
        boolean z;
        Iterator it;
        ConversationListViewModel conversationListViewModel;
        OriginDatabase originDatabase;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                conversationListViewModel$setMuted$1 = this;
                Iterable $this$forEach$iv = conversationListViewModel$setMuted$1.$threadIds;
                ConversationListViewModel conversationListViewModel2 = conversationListViewModel$setMuted$1.this$0;
                z = conversationListViewModel$setMuted$1.$muted;
                it = $this$forEach$iv.iterator();
                conversationListViewModel = conversationListViewModel2;
                break;
            case 1:
                conversationListViewModel$setMuted$1 = this;
                z = conversationListViewModel$setMuted$1.Z$0;
                it = (Iterator) conversationListViewModel$setMuted$1.L$1;
                conversationListViewModel = (ConversationListViewModel) conversationListViewModel$setMuted$1.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        while (it.hasNext()) {
            Object element$iv = it.next();
            long longValue = ((Number) element$iv).longValue();
            originDatabase = conversationListViewModel.database;
            ThreadLockDao threadLockDao = originDatabase.threadLockDao();
            boolean z2 = z;
            conversationListViewModel$setMuted$1.L$0 = conversationListViewModel;
            conversationListViewModel$setMuted$1.L$1 = it;
            conversationListViewModel$setMuted$1.Z$0 = z;
            conversationListViewModel$setMuted$1.label = 1;
            if (threadLockDao.setMuted(longValue, z2, conversationListViewModel$setMuted$1) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        conversationListViewModel$setMuted$1.this$0.loadConversations();
        return Unit.INSTANCE;
    }
}
