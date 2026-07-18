package com.meharenterprises.originsms.ui.thread;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwnerKt;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.data.db.OriginDatabase;
import com.meharenterprises.originsms.data.db.ThreadLockDao;
import com.meharenterprises.originsms.data.db.ThreadLockEntity;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ThreadActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.thread.ThreadActivity$confirmHideChat$1", f = "ThreadActivity.kt", i = {0}, l = {332}, m = "invokeSuspend", n = {"dao"}, s = {"L$0"})
/* loaded from: classes10.dex */
public final class ThreadActivity$confirmHideChat$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    int label;
    final /* synthetic */ ThreadActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadActivity$confirmHideChat$1(ThreadActivity threadActivity, Continuation<? super ThreadActivity$confirmHideChat$1> continuation) {
        super(2, continuation);
        this.this$0 = threadActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThreadActivity$confirmHideChat$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ThreadActivity$confirmHideChat$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ThreadActivity$confirmHideChat$1 threadActivity$confirmHideChat$1;
        final ThreadLockDao dao;
        long j;
        String str;
        String str2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                threadActivity$confirmHideChat$1 = this;
                dao = OriginDatabase.INSTANCE.getInstance(threadActivity$confirmHideChat$1.this$0).threadLockDao();
                j = threadActivity$confirmHideChat$1.this$0.threadId;
                threadActivity$confirmHideChat$1.L$0 = dao;
                threadActivity$confirmHideChat$1.label = 1;
                Object forThread = dao.getForThread(j, threadActivity$confirmHideChat$1);
                if (forThread != coroutine_suspended) {
                    $result = forThread;
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ThreadLockDao dao2 = (ThreadLockDao) this.L$0;
                ResultKt.throwOnFailure($result);
                dao = dao2;
                threadActivity$confirmHideChat$1 = this;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        final ThreadLockEntity existing = (ThreadLockEntity) $result;
        boolean isCurrentlyHidden = existing != null && existing.isHidden();
        if (isCurrentlyHidden) {
            AlertDialog.Builder title = new AlertDialog.Builder(threadActivity$confirmHideChat$1.this$0).setTitle(R.string.menu_unhide_chat);
            str2 = threadActivity$confirmHideChat$1.this$0.displayName;
            AlertDialog.Builder message = title.setMessage(str2);
            final ThreadActivity threadActivity = threadActivity$confirmHideChat$1.this$0;
            final ThreadActivity threadActivity2 = threadActivity$confirmHideChat$1.this$0;
            message.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$confirmHideChat$1$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    ThreadActivity$confirmHideChat$1.invokeSuspend$lambda$0(ThreadActivity.this, dao, existing, dialogInterface, i);
                }
            }).setNegativeButton("Keep timer", new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$confirmHideChat$1$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    ThreadActivity$confirmHideChat$1.invokeSuspend$lambda$1(ThreadActivity.this, dao, existing, dialogInterface, i);
                }
            }).show();
        } else {
            AlertDialog.Builder title2 = new AlertDialog.Builder(threadActivity$confirmHideChat$1.this$0).setTitle(R.string.menu_hide_chat);
            str = threadActivity$confirmHideChat$1.this$0.displayName;
            AlertDialog.Builder message2 = title2.setMessage(str);
            final ThreadActivity threadActivity3 = threadActivity$confirmHideChat$1.this$0;
            message2.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.meharenterprises.originsms.ui.thread.ThreadActivity$confirmHideChat$1$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    ThreadActivity$confirmHideChat$1.invokeSuspend$lambda$2(ThreadActivity.this, dao, existing, dialogInterface, i);
                }
            }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invokeSuspend$lambda$0(ThreadActivity this$0, ThreadLockDao $dao, ThreadLockEntity $existing, DialogInterface dialogInterface, int i) {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new ThreadActivity$confirmHideChat$1$1$1($dao, $existing, this$0, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invokeSuspend$lambda$1(ThreadActivity this$0, ThreadLockDao $dao, ThreadLockEntity $existing, DialogInterface dialogInterface, int i) {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new ThreadActivity$confirmHideChat$1$2$1($dao, $existing, this$0, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invokeSuspend$lambda$2(ThreadActivity this$0, ThreadLockDao $dao, ThreadLockEntity $existing, DialogInterface dialogInterface, int i) {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), null, null, new ThreadActivity$confirmHideChat$1$3$1($dao, $existing, this$0, null), 3, null);
    }
}
