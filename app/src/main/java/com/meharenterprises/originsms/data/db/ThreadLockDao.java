package com.meharenterprises.originsms.data.db;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

/* compiled from: ThreadLockDao.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0011\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH§@¢\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\bH§@¢\u0006\u0002\u0010\nJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\bH§@¢\u0006\u0002\u0010\nJ\u0018\u0010\r\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0004\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u0006J\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u000f\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u0006J\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u000f\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u0006J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u000f\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u0006J\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0013\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u0006J\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\bH§@¢\u0006\u0002\u0010\nJ\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\bH§@¢\u0006\u0002\u0010\nJ\u0014\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0017H'J\u001e\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001aH§@¢\u0006\u0002\u0010\u001bJ\u001e\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u001eJ\u001e\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010 \u001a\u00020!H§@¢\u0006\u0002\u0010\"J\u001e\u0010#\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u001eJ\u001e\u0010%\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u001aH§@¢\u0006\u0002\u0010\u001bJ&\u0010'\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020\u0005H§@¢\u0006\u0002\u0010*J\u001e\u0010+\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010,\u001a\u00020\u001aH§@¢\u0006\u0002\u0010\u001bJ&\u0010-\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010,\u001a\u00020\u001a2\u0006\u0010.\u001a\u00020\u0005H§@¢\u0006\u0002\u0010*J\u0016\u0010/\u001a\u00020\u00032\u0006\u00100\u001a\u00020\tH§@¢\u0006\u0002\u00101¨\u00062"}, d2 = {"Lcom/meharenterprises/originsms/data/db/ThreadLockDao;", "", "clear", "", "threadId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllHidden", "", "Lcom/meharenterprises/originsms/data/db/ThreadLockEntity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllLockStates", "getAllLocked", "getForThread", "getThreadsDueForAutoHide", "now", "getThreadsDueForAutoUnhide", "getThreadsDueForAutoUnmute", "getThreadsDueForPermanentDeletion", "cutoff", "getThreadsWithDailyHide", "getTrashedThreads", "observeAll", "Lkotlinx/coroutines/flow/Flow;", "setArchived", "archived", "", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setAutoUnhideAt", "timestamp", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setDailyHideTime", "minutes", "", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setDeletedAt", "millis", "setHidden", "hidden", "setLocked", "locked", "ts", "(JZJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setMuted", "muted", "setMutedUntil", "muteUntilMillis", "upsert", "entity", "(Lcom/meharenterprises/originsms/data/db/ThreadLockEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes9.dex */
public interface ThreadLockDao {
    Object clear(long j, Continuation<? super Unit> continuation);

    Object getAllHidden(Continuation<? super List<ThreadLockEntity>> continuation);

    Object getAllLockStates(Continuation<? super List<ThreadLockEntity>> continuation);

    Object getAllLocked(Continuation<? super List<ThreadLockEntity>> continuation);

    Object getForThread(long j, Continuation<? super ThreadLockEntity> continuation);

    Object getThreadsDueForAutoHide(long j, Continuation<? super List<ThreadLockEntity>> continuation);

    Object getThreadsDueForAutoUnhide(long j, Continuation<? super List<ThreadLockEntity>> continuation);

    Object getThreadsDueForAutoUnmute(long j, Continuation<? super List<ThreadLockEntity>> continuation);

    Object getThreadsDueForPermanentDeletion(long j, Continuation<? super List<ThreadLockEntity>> continuation);

    Object getThreadsWithDailyHide(Continuation<? super List<ThreadLockEntity>> continuation);

    Object getTrashedThreads(Continuation<? super List<ThreadLockEntity>> continuation);

    Flow<List<ThreadLockEntity>> observeAll();

    Object setArchived(long j, boolean z, Continuation<? super Unit> continuation);

    Object setAutoUnhideAt(long j, long j2, Continuation<? super Unit> continuation);

    Object setDailyHideTime(long j, int i, Continuation<? super Unit> continuation);

    Object setDeletedAt(long j, long j2, Continuation<? super Unit> continuation);

    Object setHidden(long j, boolean z, Continuation<? super Unit> continuation);

    Object setLocked(long j, boolean z, long j2, Continuation<? super Unit> continuation);

    Object setMuted(long j, boolean z, Continuation<? super Unit> continuation);

    Object setMutedUntil(long j, boolean z, long j2, Continuation<? super Unit> continuation);

    Object upsert(ThreadLockEntity threadLockEntity, Continuation<? super Unit> continuation);
}
