package com.meharenterprises.originsms.data.db;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

/* compiled from: BlockedNumberDao.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH§@¢\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\tH§@¢\u0006\u0002\u0010\nJ\u0014\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000f0\u000eH'J\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH§@¢\u0006\u0002\u0010\n¨\u0006\u0011"}, d2 = {"Lcom/meharenterprises/originsms/data/db/BlockedNumberDao;", "", "block", "", "entity", "Lcom/meharenterprises/originsms/data/db/BlockedNumberEntity;", "(Lcom/meharenterprises/originsms/data/db/BlockedNumberEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByNumber", "number", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isBlocked", "", "observeAll", "Lkotlinx/coroutines/flow/Flow;", "", "unblock", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes9.dex */
public interface BlockedNumberDao {
    Object block(BlockedNumberEntity blockedNumberEntity, Continuation<? super Unit> continuation);

    Object getByNumber(String str, Continuation<? super BlockedNumberEntity> continuation);

    Object isBlocked(String str, Continuation<? super Boolean> continuation);

    Flow<List<BlockedNumberEntity>> observeAll();

    Object unblock(String str, Continuation<? super Unit> continuation);
}
