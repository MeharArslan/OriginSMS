package com.meharenterprises.originsms.ui.thread;

import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.R;
import com.meharenterprises.originsms.data.db.BlockedNumberDao;
import com.meharenterprises.originsms.data.db.BlockedNumberEntity;
import com.meharenterprises.originsms.data.db.OriginDatabase;
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

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ThreadActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.thread.ThreadActivity$onOptionsItemSelected$3$1", f = "ThreadActivity.kt", i = {}, l = {266}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes10.dex */
public final class ThreadActivity$onOptionsItemSelected$3$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ ThreadActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadActivity$onOptionsItemSelected$3$1(ThreadActivity threadActivity, Continuation<? super ThreadActivity$onOptionsItemSelected$3$1> continuation) {
        super(2, continuation);
        this.this$0 = threadActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThreadActivity$onOptionsItemSelected$3$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ThreadActivity$onOptionsItemSelected$3$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        CharSequence $this$filter$iv;
        String str;
        ThreadActivity$onOptionsItemSelected$3$1 threadActivity$onOptionsItemSelected$3$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                BlockedNumberDao blockedNumberDao = OriginDatabase.INSTANCE.getInstance(this.this$0).blockedNumberDao();
                $this$filter$iv = this.this$0.address;
                CharSequence $this$filterTo$iv$iv = $this$filter$iv;
                Appendable destination$iv$iv = new StringBuilder();
                int index$iv$iv = 0;
                int length = $this$filterTo$iv$iv.length();
                while (true) {
                    boolean z = true;
                    if (index$iv$iv >= length) {
                        String sb = ((StringBuilder) destination$iv$iv).toString();
                        Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
                        str = this.this$0.address;
                        this.label = 1;
                        if (blockedNumberDao.block(new BlockedNumberEntity(sb, str, System.currentTimeMillis()), this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        threadActivity$onOptionsItemSelected$3$1 = this;
                        break;
                    } else {
                        char element$iv$iv = $this$filterTo$iv$iv.charAt(index$iv$iv);
                        char it = element$iv$iv;
                        if (!Character.isDigit(it) && it != '+') {
                            z = false;
                        }
                        if (z) {
                            destination$iv$iv.append(element$iv$iv);
                        }
                        index$iv$iv++;
                    }
                }
                break;
            case 1:
                threadActivity$onOptionsItemSelected$3$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Toast.makeText(threadActivity$onOptionsItemSelected$3$1.this$0, threadActivity$onOptionsItemSelected$3$1.this$0.getString(R.string.action_block_number), 0).show();
        threadActivity$onOptionsItemSelected$3$1.this$0.finish();
        return Unit.INSTANCE;
    }
}
