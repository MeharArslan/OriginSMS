package com.meharenterprises.originsms.ui;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.data.db.OriginDatabase;
import com.meharenterprises.originsms.data.db.ThreadLockDao;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SettingsActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/meharenterprises/originsms/data/db/ThreadLockDao;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.SettingsActivity$showDailyHideTimePicker$timePicker$1$1$dao$1", f = "SettingsActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
public final class SettingsActivity$showDailyHideTimePicker$timePicker$1$1$dao$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super ThreadLockDao>, Object> {
    int label;
    final /* synthetic */ SettingsActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsActivity$showDailyHideTimePicker$timePicker$1$1$dao$1(SettingsActivity settingsActivity, Continuation<? super SettingsActivity$showDailyHideTimePicker$timePicker$1$1$dao$1> continuation) {
        super(2, continuation);
        this.this$0 = settingsActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new SettingsActivity$showDailyHideTimePicker$timePicker$1$1$dao$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super ThreadLockDao> continuation) {
        return ((SettingsActivity$showDailyHideTimePicker$timePicker$1$1$dao$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                return OriginDatabase.INSTANCE.getInstance(this.this$0).threadLockDao();
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
