package com.meharenterprises.originsms.ui;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.core.ConversationSummary;
import com.meharenterprises.originsms.data.db.ThreadLockDao;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SettingsActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.SettingsActivity$showDailyHideTimePicker$timePicker$1$1", f = "SettingsActivity.kt", i = {}, l = {302, 307}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
public final class SettingsActivity$showDailyHideTimePicker$timePicker$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ConversationSummary $conversation;
    final /* synthetic */ int $hour;
    final /* synthetic */ int $minute;
    int label;
    final /* synthetic */ SettingsActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsActivity$showDailyHideTimePicker$timePicker$1$1(int i, int i2, SettingsActivity settingsActivity, ConversationSummary conversationSummary, Continuation<? super SettingsActivity$showDailyHideTimePicker$timePicker$1$1> continuation) {
        super(2, continuation);
        this.$hour = i;
        this.$minute = i2;
        this.this$0 = settingsActivity;
        this.$conversation = conversationSummary;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new SettingsActivity$showDailyHideTimePicker$timePicker$1$1(this.$hour, this.$minute, this.this$0, this.$conversation, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((SettingsActivity$showDailyHideTimePicker$timePicker$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0009. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0066 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0067  */
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
            r2 = 2
            r3 = 1
            r4 = 0
            switch(r1) {
                case 0: goto L20;
                case 1: goto L19;
                case 2: goto L14;
                default: goto Lc;
            }
        Lc:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r0)
            throw r12
        L14:
            r0 = r11
            kotlin.ResultKt.throwOnFailure(r12)
            goto L69
        L19:
            r1 = r11
            kotlin.ResultKt.throwOnFailure(r12)
            r5 = r1
            r1 = r12
            goto L43
        L20:
            kotlin.ResultKt.throwOnFailure(r12)
            r1 = r11
            kotlinx.coroutines.CoroutineDispatcher r5 = kotlinx.coroutines.Dispatchers.getIO()
            kotlin.coroutines.CoroutineContext r5 = (kotlin.coroutines.CoroutineContext) r5
            com.meharenterprises.originsms.ui.SettingsActivity$showDailyHideTimePicker$timePicker$1$1$dao$1 r6 = new com.meharenterprises.originsms.ui.SettingsActivity$showDailyHideTimePicker$timePicker$1$1$dao$1
            com.meharenterprises.originsms.ui.SettingsActivity r7 = r1.this$0
            r6.<init>(r7, r4)
            kotlin.jvm.functions.Function2 r6 = (kotlin.jvm.functions.Function2) r6
            r7 = r1
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r1.label = r3
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r5, r6, r7)
            if (r5 != r0) goto L3f
            return r0
        L3f:
            r10 = r1
            r1 = r12
            r12 = r5
            r5 = r10
        L43:
            com.meharenterprises.originsms.data.db.ThreadLockDao r12 = (com.meharenterprises.originsms.data.db.ThreadLockDao) r12
            int r6 = r5.$hour
            int r6 = r6 * 60
            int r7 = r5.$minute
            int r6 = r6 + r7
            kotlinx.coroutines.CoroutineDispatcher r7 = kotlinx.coroutines.Dispatchers.getIO()
            kotlin.coroutines.CoroutineContext r7 = (kotlin.coroutines.CoroutineContext) r7
            com.meharenterprises.originsms.ui.SettingsActivity$showDailyHideTimePicker$timePicker$1$1$1 r8 = new com.meharenterprises.originsms.ui.SettingsActivity$showDailyHideTimePicker$timePicker$1$1$1
            com.meharenterprises.originsms.core.ConversationSummary r9 = r5.$conversation
            r8.<init>(r12, r9, r6, r4)
            kotlin.jvm.functions.Function2 r8 = (kotlin.jvm.functions.Function2) r8
            r4 = r5
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r5.label = r2
            java.lang.Object r12 = kotlinx.coroutines.BuildersKt.withContext(r7, r8, r4)
            if (r12 != r0) goto L67
            return r0
        L67:
            r12 = r1
            r0 = r5
        L69:
            kotlin.jvm.internal.StringCompanionObject r1 = kotlin.jvm.internal.StringCompanionObject.INSTANCE
            int r1 = r0.$hour
            java.lang.Integer r1 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r1)
            int r4 = r0.$minute
            java.lang.Integer r4 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r4)
            java.lang.Object[] r1 = new java.lang.Object[]{r1, r4}
            java.lang.Object[] r1 = java.util.Arrays.copyOf(r1, r2)
            java.lang.String r2 = "%02d:%02d"
            java.lang.String r1 = java.lang.String.format(r2, r1)
            java.lang.String r2 = "format(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            com.meharenterprises.originsms.ui.SettingsActivity r2 = r0.this$0
            android.content.Context r2 = (android.content.Context) r2
            com.meharenterprises.originsms.core.ConversationSummary r4 = r0.$conversation
            java.lang.String r4 = r4.getDisplayName()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.String r5 = " will auto-hide daily at "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r1)
            java.lang.String r4 = r4.toString()
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4
            android.widget.Toast r2 = android.widget.Toast.makeText(r2, r4, r3)
            r2.show()
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.SettingsActivity$showDailyHideTimePicker$timePicker$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SettingsActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.meharenterprises.originsms.ui.SettingsActivity$showDailyHideTimePicker$timePicker$1$1$1", f = "SettingsActivity.kt", i = {}, l = {308, 309}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.meharenterprises.originsms.ui.SettingsActivity$showDailyHideTimePicker$timePicker$1$1$1, reason: invalid class name */
    /* loaded from: classes3.dex */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ConversationSummary $conversation;
        final /* synthetic */ ThreadLockDao $dao;
        final /* synthetic */ int $totalMinutes;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(ThreadLockDao threadLockDao, ConversationSummary conversationSummary, int i, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$dao = threadLockDao;
            this.$conversation = conversationSummary;
            this.$totalMinutes = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$dao, this.$conversation, this.$totalMinutes, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0047  */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0098 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0099  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x006a  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r47) {
            /*
                r46 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                r1 = r46
                int r2 = r1.label
                switch(r2) {
                    case 0: goto L25;
                    case 1: goto L1c;
                    case 2: goto L13;
                    default: goto Lb;
                }
            Lb:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r2)
                throw r0
            L13:
                r0 = r46
                r2 = r47
                kotlin.ResultKt.throwOnFailure(r2)
                goto L9b
            L1c:
                r2 = r46
                r3 = r47
                kotlin.ResultKt.throwOnFailure(r3)
                r4 = r3
                goto L41
            L25:
                kotlin.ResultKt.throwOnFailure(r47)
                r2 = r46
                r3 = r47
                com.meharenterprises.originsms.data.db.ThreadLockDao r4 = r2.$dao
                com.meharenterprises.originsms.core.ConversationSummary r5 = r2.$conversation
                long r5 = r5.getThreadId()
                r7 = r2
                kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
                r8 = 1
                r2.label = r8
                java.lang.Object r4 = r4.getForThread(r5, r7)
                if (r4 != r0) goto L41
                return r0
            L41:
                com.meharenterprises.originsms.data.db.ThreadLockEntity r4 = (com.meharenterprises.originsms.data.db.ThreadLockEntity) r4
                com.meharenterprises.originsms.data.db.ThreadLockDao r5 = r2.$dao
                if (r4 != 0) goto L6a
                com.meharenterprises.originsms.data.db.ThreadLockEntity r4 = new com.meharenterprises.originsms.data.db.ThreadLockEntity
                r6 = r4
                com.meharenterprises.originsms.core.ConversationSummary r7 = r2.$conversation
                long r7 = r7.getThreadId()
                r24 = 2046(0x7fe, float:2.867E-42)
                r25 = 0
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
                r6.<init>(r7, r9, r10, r11, r13, r14, r15, r17, r19, r20, r22, r24, r25)
                r26 = r4
                goto L6c
            L6a:
                r26 = r4
            L6c:
                int r4 = r2.$totalMinutes
                r39 = r4
                r44 = 1791(0x6ff, float:2.51E-42)
                r45 = 0
                r27 = 0
                r29 = 0
                r30 = 0
                r31 = 0
                r33 = 0
                r34 = 0
                r35 = 0
                r37 = 0
                r40 = 0
                r42 = 0
                com.meharenterprises.originsms.data.db.ThreadLockEntity r4 = com.meharenterprises.originsms.data.db.ThreadLockEntity.copy$default(r26, r27, r29, r30, r31, r33, r34, r35, r37, r39, r40, r42, r44, r45)
                r6 = r2
                kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
                r7 = 2
                r2.label = r7
                java.lang.Object r4 = r5.upsert(r4, r6)
                if (r4 != r0) goto L99
                return r0
            L99:
                r0 = r2
                r2 = r3
            L9b:
                kotlin.Unit r3 = kotlin.Unit.INSTANCE
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.ui.SettingsActivity$showDailyHideTimePicker$timePicker$1$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }
}
