package com.meharenterprises.originsms.data.db;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: DraftDao.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH§@¢\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\fH§@¢\u0006\u0002\u0010\rJ\u0018\u0010\u000e\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0004\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u0006J\u0016\u0010\u000f\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH§@¢\u0006\u0002\u0010\n¨\u0006\u0010"}, d2 = {"Lcom/meharenterprises/originsms/data/db/DraftDao;", "", "clearDraft", "", "threadId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteDraft", "entity", "Lcom/meharenterprises/originsms/data/db/DraftEntity;", "(Lcom/meharenterprises/originsms/data/db/DraftEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllDrafts", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDraft", "saveDraft", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes9.dex */
public interface DraftDao {
    Object clearDraft(long j, Continuation<? super Unit> continuation);

    Object deleteDraft(DraftEntity draftEntity, Continuation<? super Unit> continuation);

    Object getAllDrafts(Continuation<? super List<DraftEntity>> continuation);

    Object getDraft(long j, Continuation<? super DraftEntity> continuation);

    Object saveDraft(DraftEntity draftEntity, Continuation<? super Unit> continuation);
}
