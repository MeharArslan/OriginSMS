package com.meharenterprises.originsms.ui;

import android.widget.Toast;
import com.meharenterprises.originsms.core.ConversationSummary;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TrashActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.TrashActivity$confirmPermanentDelete$1$1", f = "TrashActivity.kt", i = {}, l = {272}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
public final class TrashActivity$confirmPermanentDelete$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ConversationSummary $conv;
    int label;
    final /* synthetic */ TrashActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TrashActivity$confirmPermanentDelete$1$1(TrashActivity trashActivity, ConversationSummary conversationSummary, Continuation<? super TrashActivity$confirmPermanentDelete$1$1> continuation) {
        super(2, continuation);
        this.this$0 = trashActivity;
        this.$conv = conversationSummary;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new TrashActivity$confirmPermanentDelete$1$1(this.this$0, this.$conv, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((TrashActivity$confirmPermanentDelete$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TrashActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.meharenterprises.originsms.ui.TrashActivity$confirmPermanentDelete$1$1$1", f = "TrashActivity.kt", i = {}, l = {273, 274}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.meharenterprises.originsms.ui.TrashActivity$confirmPermanentDelete$1$1$1, reason: invalid class name */
    /* loaded from: classes3.dex */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ConversationSummary $conv;
        int label;
        final /* synthetic */ TrashActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(TrashActivity trashActivity, ConversationSummary conversationSummary, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = trashActivity;
            this.$conv = conversationSummary;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.this$0, this.$conv, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0007. Please report as an issue. */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0049  */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0066 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0067  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x004f  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                r2 = 0
                switch(r1) {
                    case 0: goto L1c;
                    case 1: goto L17;
                    case 2: goto L12;
                    default: goto La;
                }
            La:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L12:
                r0 = r8
                kotlin.ResultKt.throwOnFailure(r9)
                goto L68
            L17:
                r1 = r8
                kotlin.ResultKt.throwOnFailure(r9)
                goto L41
            L1c:
                kotlin.ResultKt.throwOnFailure(r9)
                r1 = r8
                com.meharenterprises.originsms.ui.TrashActivity r3 = r1.this$0
                com.meharenterprises.originsms.core.SmsRepository r3 = com.meharenterprises.originsms.ui.TrashActivity.access$getRepository$p(r3)
                if (r3 != 0) goto L2e
                java.lang.String r3 = "repository"
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r3)
                r3 = r2
            L2e:
                com.meharenterprises.originsms.core.ConversationSummary r4 = r1.$conv
                long r4 = r4.getThreadId()
                r6 = r1
                kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
                r7 = 1
                r1.label = r7
                java.lang.Object r3 = r3.deleteThread(r4, r6)
                if (r3 != r0) goto L41
                return r0
            L41:
                com.meharenterprises.originsms.ui.TrashActivity r3 = r1.this$0
                com.meharenterprises.originsms.data.db.OriginDatabase r3 = com.meharenterprises.originsms.ui.TrashActivity.access$getDatabase$p(r3)
                if (r3 != 0) goto L4f
                java.lang.String r3 = "database"
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r3)
                goto L50
            L4f:
                r2 = r3
            L50:
                com.meharenterprises.originsms.data.db.ThreadLockDao r2 = r2.threadLockDao()
                com.meharenterprises.originsms.core.ConversationSummary r3 = r1.$conv
                long r3 = r3.getThreadId()
                r5 = r1
                kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                r6 = 2
                r1.label = r6
                java.lang.Object r2 = r2.clear(r3, r5)
                if (r2 != r0) goto L67
                return r0
            L67:
                r0 = r1
            L68:
                kotlin.Unit r1 = kotlin.Unit.INSTANCE
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.TrashActivity$confirmPermanentDelete$1$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        TrashActivity$confirmPermanentDelete$1$1 trashActivity$confirmPermanentDelete$1$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass1(this.this$0, this.$conv, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                trashActivity$confirmPermanentDelete$1$1 = this;
                break;
            case 1:
                trashActivity$confirmPermanentDelete$1$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Toast.makeText(trashActivity$confirmPermanentDelete$1$1.this$0, "Permanently deleted", 0).show();
        trashActivity$confirmPermanentDelete$1$1.this$0.loadTrash();
        return Unit.INSTANCE;
    }
}
