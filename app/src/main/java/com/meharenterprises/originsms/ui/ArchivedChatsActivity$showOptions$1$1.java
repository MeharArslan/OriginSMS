package com.meharenterprises.originsms.ui;

import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
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
/* compiled from: ArchivedChatsActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.ArchivedChatsActivity$showOptions$1$1", f = "ArchivedChatsActivity.kt", i = {}, l = {86}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
public final class ArchivedChatsActivity$showOptions$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ConversationSummary $conv;
    int label;
    final /* synthetic */ ArchivedChatsActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArchivedChatsActivity$showOptions$1$1(ArchivedChatsActivity archivedChatsActivity, ConversationSummary conversationSummary, Continuation<? super ArchivedChatsActivity$showOptions$1$1> continuation) {
        super(2, continuation);
        this.this$0 = archivedChatsActivity;
        this.$conv = conversationSummary;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ArchivedChatsActivity$showOptions$1$1(this.this$0, this.$conv, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ArchivedChatsActivity$showOptions$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ArchivedChatsActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.meharenterprises.originsms.ui.ArchivedChatsActivity$showOptions$1$1$1", f = "ArchivedChatsActivity.kt", i = {0}, l = {88, 89}, m = "invokeSuspend", n = {"dao"}, s = {"L$0"})
    /* renamed from: com.meharenterprises.originsms.ui.ArchivedChatsActivity$showOptions$1$1$1, reason: invalid class name */
    /* loaded from: classes3.dex */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ConversationSummary $conv;
        Object L$0;
        int label;
        final /* synthetic */ ArchivedChatsActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(ArchivedChatsActivity archivedChatsActivity, ConversationSummary conversationSummary, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = archivedChatsActivity;
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

        /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0056  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r27) {
            /*
                r26 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                r1 = r26
                int r2 = r1.label
                switch(r2) {
                    case 0: goto L28;
                    case 1: goto L1b;
                    case 2: goto L13;
                    default: goto Lb;
                }
            Lb:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r2)
                throw r0
            L13:
                r0 = r26
                r2 = r27
                kotlin.ResultKt.throwOnFailure(r2)
                goto L83
            L1b:
                r2 = r26
                r3 = r27
                java.lang.Object r4 = r2.L$0
                com.meharenterprises.originsms.data.db.ThreadLockDao r4 = (com.meharenterprises.originsms.data.db.ThreadLockDao) r4
                kotlin.ResultKt.throwOnFailure(r3)
                r5 = r3
                goto L52
            L28:
                kotlin.ResultKt.throwOnFailure(r27)
                r2 = r26
                r3 = r27
                com.meharenterprises.originsms.data.db.OriginDatabase$Companion r4 = com.meharenterprises.originsms.data.db.OriginDatabase.INSTANCE
                com.meharenterprises.originsms.ui.ArchivedChatsActivity r5 = r2.this$0
                android.content.Context r5 = (android.content.Context) r5
                com.meharenterprises.originsms.data.db.OriginDatabase r4 = r4.getInstance(r5)
                com.meharenterprises.originsms.data.db.ThreadLockDao r4 = r4.threadLockDao()
                com.meharenterprises.originsms.core.ConversationSummary r5 = r2.$conv
                long r5 = r5.getThreadId()
                r7 = r2
                kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
                r2.L$0 = r4
                r8 = 1
                r2.label = r8
                java.lang.Object r5 = r4.getForThread(r5, r7)
                if (r5 != r0) goto L52
                return r0
            L52:
                com.meharenterprises.originsms.data.db.ThreadLockEntity r5 = (com.meharenterprises.originsms.data.db.ThreadLockEntity) r5
                if (r5 == 0) goto L85
                r24 = 2015(0x7df, float:2.824E-42)
                r25 = 0
                r7 = 0
                r9 = 0
                r10 = 0
                r11 = 0
                r13 = 0
                r14 = 0
                r15 = 0
                r17 = 0
                r19 = 0
                r20 = 0
                r22 = 0
                r6 = r5
                com.meharenterprises.originsms.data.db.ThreadLockEntity r6 = com.meharenterprises.originsms.data.db.ThreadLockEntity.copy$default(r6, r7, r9, r10, r11, r13, r14, r15, r17, r19, r20, r22, r24, r25)
                r7 = r2
                kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
                r8 = 0
                r2.L$0 = r8
                r8 = 2
                r2.label = r8
                java.lang.Object r4 = r4.upsert(r6, r7)
                if (r4 != r0) goto L81
                return r0
            L81:
                r0 = r2
                r2 = r3
            L83:
                r3 = r2
                r2 = r0
            L85:
                kotlin.Unit r0 = kotlin.Unit.INSTANCE
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.ArchivedChatsActivity$showOptions$1$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ArchivedChatsActivity$showOptions$1$1 archivedChatsActivity$showOptions$1$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass1(this.this$0, this.$conv, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                archivedChatsActivity$showOptions$1$1 = this;
                break;
            case 1:
                archivedChatsActivity$showOptions$1$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Toast.makeText(archivedChatsActivity$showOptions$1$1.this$0, archivedChatsActivity$showOptions$1$1.$conv.getDisplayName() + " unarchived", 0).show();
        archivedChatsActivity$showOptions$1$1.this$0.loadArchived();
        return Unit.INSTANCE;
    }
}
