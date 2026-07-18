package com.meharenterprises.originsms.ui.thread;

import android.view.Menu;
import android.view.MenuItem;
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
import kotlinx.coroutines.CoroutineScope;

/* compiled from: ThreadActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.thread.ThreadActivity$onPrepareOptionsMenu$1", f = "ThreadActivity.kt", i = {}, l = {202}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes10.dex */
final class ThreadActivity$onPrepareOptionsMenu$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Menu $menu;
    int label;
    final /* synthetic */ ThreadActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadActivity$onPrepareOptionsMenu$1(ThreadActivity threadActivity, Menu menu, Continuation<? super ThreadActivity$onPrepareOptionsMenu$1> continuation) {
        super(2, continuation);
        this.this$0 = threadActivity;
        this.$menu = menu;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThreadActivity$onPrepareOptionsMenu$1(this.this$0, this.$menu, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ThreadActivity$onPrepareOptionsMenu$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        ThreadActivity$onPrepareOptionsMenu$1 threadActivity$onPrepareOptionsMenu$1;
        long j;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                threadActivity$onPrepareOptionsMenu$1 = this;
                ThreadLockDao threadLockDao = OriginDatabase.INSTANCE.getInstance(threadActivity$onPrepareOptionsMenu$1.this$0).threadLockDao();
                j = threadActivity$onPrepareOptionsMenu$1.this$0.threadId;
                threadActivity$onPrepareOptionsMenu$1.label = 1;
                Object forThread = threadLockDao.getForThread(j, threadActivity$onPrepareOptionsMenu$1);
                if (forThread != coroutine_suspended) {
                    $result = forThread;
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                threadActivity$onPrepareOptionsMenu$1 = this;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ThreadLockEntity lockState = (ThreadLockEntity) $result;
        boolean muted = (lockState != null && lockState.isMuted()) && (lockState.getMuteUntilMillis() == -1 || lockState.getMuteUntilMillis() > System.currentTimeMillis());
        MenuItem findItem = threadActivity$onPrepareOptionsMenu$1.$menu.findItem(R.id.action_mute_chat);
        if (findItem != null) {
            findItem.setTitle(threadActivity$onPrepareOptionsMenu$1.this$0.getString(muted ? R.string.action_unmute_chat : R.string.action_mute_chat));
        }
        MenuItem findItem2 = threadActivity$onPrepareOptionsMenu$1.$menu.findItem(R.id.action_hide_chat);
        if (findItem2 != null) {
            findItem2.setTitle(lockState != null && lockState.isHidden() ? threadActivity$onPrepareOptionsMenu$1.this$0.getString(R.string.menu_unhide_chat) : threadActivity$onPrepareOptionsMenu$1.this$0.getString(R.string.menu_hide_chat));
        }
        return Unit.INSTANCE;
    }
}
