package com.meharenterprises.originsms.data.db;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: StarredMessageDao.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H§@¢\u0006\u0002\u0010\u0005J\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H§@¢\u0006\u0002\u0010\u0005J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H§@¢\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004H§@¢\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u0007H§@¢\u0006\u0002\u0010\u000b¨\u0006\u0011"}, d2 = {"Lcom/meharenterprises/originsms/data/db/StarredMessageDao;", "", "getAll", "", "Lcom/meharenterprises/originsms/data/db/StarredMessageEntity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllIds", "", "isStarred", "", "messageId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "star", "", "entity", "(Lcom/meharenterprises/originsms/data/db/StarredMessageEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unstar", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes9.dex */
public interface StarredMessageDao {
    Object getAll(Continuation<? super List<StarredMessageEntity>> continuation);

    Object getAllIds(Continuation<? super List<Long>> continuation);

    Object isStarred(long j, Continuation<? super Integer> continuation);

    Object star(StarredMessageEntity starredMessageEntity, Continuation<? super Unit> continuation);

    Object unstar(long j, Continuation<? super Unit> continuation);
}
