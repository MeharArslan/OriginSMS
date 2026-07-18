package com.meharenterprises.originsms.ui;

import android.content.Intent;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.meharenterprises.originsms.core.ContactsHelper;
import com.meharenterprises.originsms.data.db.OriginDatabase;
import com.meharenterprises.originsms.data.db.StarredMessageEntity;
import com.meharenterprises.originsms.ui.StarredMessagesActivity;
import com.meharenterprises.originsms.ui.thread.ThreadActivity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: StarredMessagesActivity.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.meharenterprises.originsms.ui.StarredMessagesActivity$onCreate$2", f = "StarredMessagesActivity.kt", i = {}, l = {44}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class StarredMessagesActivity$onCreate$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ TextView $empty;
    final /* synthetic */ long $filterThreadId;
    final /* synthetic */ RecyclerView $recycler;
    int label;
    final /* synthetic */ StarredMessagesActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StarredMessagesActivity$onCreate$2(StarredMessagesActivity starredMessagesActivity, long j, TextView textView, RecyclerView recyclerView, Continuation<? super StarredMessagesActivity$onCreate$2> continuation) {
        super(2, continuation);
        this.this$0 = starredMessagesActivity;
        this.$filterThreadId = j;
        this.$empty = textView;
        this.$recycler = recyclerView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new StarredMessagesActivity$onCreate$2(this.this$0, this.$filterThreadId, this.$empty, this.$recycler, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((StarredMessagesActivity$onCreate$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        StarredMessagesActivity$onCreate$2 starredMessagesActivity$onCreate$2;
        List starred;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                starredMessagesActivity$onCreate$2 = this;
                starredMessagesActivity$onCreate$2.label = 1;
                Object all = OriginDatabase.INSTANCE.getInstance(starredMessagesActivity$onCreate$2.this$0).starredMessageDao().getAll(starredMessagesActivity$onCreate$2);
                if (all == coroutine_suspended) {
                    return coroutine_suspended;
                }
                $result = all;
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                starredMessagesActivity$onCreate$2 = this;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        List allStarred = (List) $result;
        if (starredMessagesActivity$onCreate$2.$filterThreadId != -1) {
            List $this$filterTo$iv$iv = allStarred;
            long j = starredMessagesActivity$onCreate$2.$filterThreadId;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                StarredMessageEntity it = (StarredMessageEntity) element$iv$iv;
                StarredMessageEntity it2 = it.getThreadId() == j ? 1 : null;
                if (it2 != null) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            starred = (List) destination$iv$iv;
        } else {
            starred = allStarred;
        }
        if (starred.isEmpty()) {
            starredMessagesActivity$onCreate$2.$empty.setVisibility(0);
            starredMessagesActivity$onCreate$2.$recycler.setVisibility(8);
        } else {
            starredMessagesActivity$onCreate$2.$empty.setVisibility(8);
            starredMessagesActivity$onCreate$2.$recycler.setVisibility(0);
            ContactsHelper contactsHelper = new ContactsHelper(starredMessagesActivity$onCreate$2.this$0);
            final Map nameCache = new LinkedHashMap();
            RecyclerView recyclerView = starredMessagesActivity$onCreate$2.$recycler;
            StarredMessagesActivity starredMessagesActivity = starredMessagesActivity$onCreate$2.this$0;
            final StarredMessagesActivity starredMessagesActivity2 = starredMessagesActivity$onCreate$2.this$0;
            recyclerView.setAdapter(new StarredMessagesActivity.StarredAdapter(starredMessagesActivity, starred, contactsHelper, nameCache, new Function1<StarredMessageEntity, Unit>() { // from class: com.meharenterprises.originsms.ui.StarredMessagesActivity$onCreate$2.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(StarredMessageEntity starredMessageEntity) {
                    invoke2(starredMessageEntity);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(StarredMessageEntity item) {
                    Intrinsics.checkNotNullParameter(item, "item");
                    Intent intent = new Intent(StarredMessagesActivity.this, (Class<?>) ThreadActivity.class);
                    Map<String, String> map = nameCache;
                    intent.putExtra("extra_thread_id", item.getThreadId());
                    intent.putExtra("extra_address", item.getAddress());
                    String str = map.get(item.getAddress());
                    if (str == null) {
                        str = item.getAddress();
                    }
                    intent.putExtra("extra_display_name", str);
                    intent.putExtra("HIGHLIGHT_MESSAGE_ID", item.getMessageId());
                    StarredMessagesActivity.this.startActivity(intent);
                }
            }));
        }
        return Unit.INSTANCE;
    }
}
