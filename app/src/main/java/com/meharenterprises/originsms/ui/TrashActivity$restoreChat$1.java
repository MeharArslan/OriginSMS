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
/* compiled from: TrashActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.TrashActivity$restoreChat$1", f = "TrashActivity.kt", i = {}, l = {243}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
public final class TrashActivity$restoreChat$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ConversationSummary $conv;
    int label;
    final /* synthetic */ TrashActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TrashActivity$restoreChat$1(TrashActivity trashActivity, ConversationSummary conversationSummary, Continuation<? super TrashActivity$restoreChat$1> continuation) {
        super(2, continuation);
        this.this$0 = trashActivity;
        this.$conv = conversationSummary;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new TrashActivity$restoreChat$1(this.this$0, this.$conv, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((TrashActivity$restoreChat$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TrashActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.meharenterprises.originsms.ui.TrashActivity$restoreChat$1$1", f = "TrashActivity.kt", i = {}, l = {244, 246}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.meharenterprises.originsms.ui.TrashActivity$restoreChat$1$1, reason: invalid class name */
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

        /* JADX WARN: Failed to find 'out' block for switch in B:2:0x000b. Please report as an issue. */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0057  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r28) {
            /*
                r27 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                r1 = r27
                int r2 = r1.label
                r3 = 0
                java.lang.String r4 = "database"
                switch(r2) {
                    case 0: goto L28;
                    case 1: goto L1f;
                    case 2: goto L16;
                    default: goto Le;
                }
            Le:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r2)
                throw r0
            L16:
                r0 = r27
                r2 = r28
                kotlin.ResultKt.throwOnFailure(r2)
                goto L94
            L1f:
                r2 = r27
                r5 = r28
                kotlin.ResultKt.throwOnFailure(r5)
                r6 = r5
                goto L52
            L28:
                kotlin.ResultKt.throwOnFailure(r28)
                r2 = r27
                r5 = r28
                com.meharenterprises.originsms.ui.TrashActivity r6 = r2.this$0
                com.meharenterprises.originsms.data.db.OriginDatabase r6 = com.meharenterprises.originsms.ui.TrashActivity.access$getDatabase$p(r6)
                if (r6 != 0) goto L3b
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
                r6 = r3
            L3b:
                com.meharenterprises.originsms.data.db.ThreadLockDao r6 = r6.threadLockDao()
                com.meharenterprises.originsms.core.ConversationSummary r7 = r2.$conv
                long r7 = r7.getThreadId()
                r9 = r2
                kotlin.coroutines.Continuation r9 = (kotlin.coroutines.Continuation) r9
                r10 = 1
                r2.label = r10
                java.lang.Object r6 = r6.getForThread(r7, r9)
                if (r6 != r0) goto L52
                return r0
            L52:
                r7 = r6
                com.meharenterprises.originsms.data.db.ThreadLockEntity r7 = (com.meharenterprises.originsms.data.db.ThreadLockEntity) r7
                if (r7 == 0) goto L96
                com.meharenterprises.originsms.ui.TrashActivity r6 = r2.this$0
                com.meharenterprises.originsms.data.db.OriginDatabase r6 = com.meharenterprises.originsms.ui.TrashActivity.access$getDatabase$p(r6)
                if (r6 != 0) goto L63
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
                goto L64
            L63:
                r3 = r6
            L64:
                com.meharenterprises.originsms.data.db.ThreadLockDao r3 = r3.threadLockDao()
                r25 = 511(0x1ff, float:7.16E-43)
                r26 = 0
                r8 = 0
                r10 = 0
                r11 = 0
                r12 = 0
                r14 = 0
                r15 = 0
                r16 = 0
                r18 = 0
                r20 = 0
                r21 = 0
                r23 = 0
                com.meharenterprises.originsms.data.db.ThreadLockEntity r4 = com.meharenterprises.originsms.data.db.ThreadLockEntity.copy$default(r7, r8, r10, r11, r12, r14, r15, r16, r18, r20, r21, r23, r25, r26)
                r6 = r2
                kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
                r7 = 2
                r2.label = r7
                java.lang.Object r3 = r3.upsert(r4, r6)
                if (r3 != r0) goto L92
                return r0
            L92:
                r0 = r2
                r2 = r5
            L94:
                r5 = r2
                r2 = r0
            L96:
                com.meharenterprises.originsms.ui.TrashActivity r0 = r2.this$0
                java.lang.String r3 = "new_chat_start"
                r4 = 0
                android.content.SharedPreferences r0 = r0.getSharedPreferences(r3, r4)
                android.content.SharedPreferences$Editor r0 = r0.edit()
                com.meharenterprises.originsms.core.ConversationSummary r3 = r2.$conv
                long r3 = r3.getThreadId()
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "ts_thread_"
                java.lang.StringBuilder r6 = r6.append(r7)
                java.lang.StringBuilder r3 = r6.append(r3)
                java.lang.String r3 = r3.toString()
                android.content.SharedPreferences$Editor r0 = r0.remove(r3)
                com.meharenterprises.originsms.core.ConversationSummary r3 = r2.$conv
                java.lang.String r3 = r3.getAddress()
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r6 = "ts_"
                java.lang.StringBuilder r4 = r4.append(r6)
                java.lang.StringBuilder r3 = r4.append(r3)
                java.lang.String r3 = r3.toString()
                android.content.SharedPreferences$Editor r0 = r0.remove(r3)
                r0.apply()
                kotlin.Unit r0 = kotlin.Unit.INSTANCE
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.TrashActivity$restoreChat$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        TrashActivity$restoreChat$1 trashActivity$restoreChat$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass1(this.this$0, this.$conv, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                trashActivity$restoreChat$1 = this;
                break;
            case 1:
                trashActivity$restoreChat$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Toast.makeText(trashActivity$restoreChat$1.this$0, trashActivity$restoreChat$1.$conv.getDisplayName() + " restored", 0).show();
        trashActivity$restoreChat$1.this$0.loadTrash();
        return Unit.INSTANCE;
    }
}
