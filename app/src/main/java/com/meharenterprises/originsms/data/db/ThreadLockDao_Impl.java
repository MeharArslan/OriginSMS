package com.meharenterprises.originsms.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.EntityUpsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes9.dex */
public final class ThreadLockDao_Impl implements ThreadLockDao {
    private final RoomDatabase __db;
    private final SharedSQLiteStatement __preparedStmtOfClear;
    private final SharedSQLiteStatement __preparedStmtOfSetArchived;
    private final SharedSQLiteStatement __preparedStmtOfSetAutoUnhideAt;
    private final SharedSQLiteStatement __preparedStmtOfSetDailyHideTime;
    private final SharedSQLiteStatement __preparedStmtOfSetDeletedAt;
    private final SharedSQLiteStatement __preparedStmtOfSetHidden;
    private final SharedSQLiteStatement __preparedStmtOfSetLocked;
    private final SharedSQLiteStatement __preparedStmtOfSetMuted;
    private final SharedSQLiteStatement __preparedStmtOfSetMutedUntil;
    private final EntityUpsertionAdapter<ThreadLockEntity> __upsertionAdapterOfThreadLockEntity;

    public ThreadLockDao_Impl(final RoomDatabase __db) {
        this.__db = __db;
        this.__preparedStmtOfSetLocked = new SharedSQLiteStatement(__db) { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE thread_lock_state SET isLocked = ?, lockedAtMillis = ? WHERE threadId = ?";
            }
        };
        this.__preparedStmtOfSetHidden = new SharedSQLiteStatement(__db) { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE thread_lock_state SET isHidden = ? WHERE threadId = ?";
            }
        };
        this.__preparedStmtOfSetMuted = new SharedSQLiteStatement(__db) { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE thread_lock_state SET isMuted = ? WHERE threadId = ?";
            }
        };
        this.__preparedStmtOfSetMutedUntil = new SharedSQLiteStatement(__db) { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE thread_lock_state SET isMuted = ?, muteUntilMillis = ? WHERE threadId = ?";
            }
        };
        this.__preparedStmtOfSetArchived = new SharedSQLiteStatement(__db) { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.5
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE thread_lock_state SET isArchived = ? WHERE threadId = ?";
            }
        };
        this.__preparedStmtOfSetAutoUnhideAt = new SharedSQLiteStatement(__db) { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.6
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE thread_lock_state SET autoUnhideAtMillis = ? WHERE threadId = ?";
            }
        };
        this.__preparedStmtOfSetDeletedAt = new SharedSQLiteStatement(__db) { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.7
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE thread_lock_state SET deletedAtMillis = ? WHERE threadId = ?";
            }
        };
        this.__preparedStmtOfSetDailyHideTime = new SharedSQLiteStatement(__db) { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.8
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE thread_lock_state SET dailyHideTimeMinutes = ? WHERE threadId = ?";
            }
        };
        this.__preparedStmtOfClear = new SharedSQLiteStatement(__db) { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.9
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM thread_lock_state WHERE threadId = ?";
            }
        };
        this.__upsertionAdapterOfThreadLockEntity = new EntityUpsertionAdapter<>(new EntityInsertionAdapter<ThreadLockEntity>(__db) { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.10
            @Override // androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "INSERT INTO `thread_lock_state` (`threadId`,`isLocked`,`isHidden`,`lockedAtMillis`,`isMuted`,`isArchived`,`muteUntilMillis`,`autoUnhideAtMillis`,`dailyHideTimeMinutes`,`deletedAtMillis`,`newChatStartMillis`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, ThreadLockEntity threadLockEntity) {
                supportSQLiteStatement.bindLong(1, threadLockEntity.getThreadId());
                supportSQLiteStatement.bindLong(2, threadLockEntity.isLocked() ? 1L : 0L);
                supportSQLiteStatement.bindLong(3, threadLockEntity.isHidden() ? 1L : 0L);
                supportSQLiteStatement.bindLong(4, threadLockEntity.getLockedAtMillis());
                supportSQLiteStatement.bindLong(5, threadLockEntity.isMuted() ? 1L : 0L);
                supportSQLiteStatement.bindLong(6, threadLockEntity.isArchived() ? 1L : 0L);
                supportSQLiteStatement.bindLong(7, threadLockEntity.getMuteUntilMillis());
                supportSQLiteStatement.bindLong(8, threadLockEntity.getAutoUnhideAtMillis());
                supportSQLiteStatement.bindLong(9, threadLockEntity.getDailyHideTimeMinutes());
                supportSQLiteStatement.bindLong(10, threadLockEntity.getDeletedAtMillis());
                supportSQLiteStatement.bindLong(11, threadLockEntity.getNewChatStartMillis());
            }
        }, new EntityDeletionOrUpdateAdapter<ThreadLockEntity>(__db) { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.11
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "UPDATE `thread_lock_state` SET `threadId` = ?,`isLocked` = ?,`isHidden` = ?,`lockedAtMillis` = ?,`isMuted` = ?,`isArchived` = ?,`muteUntilMillis` = ?,`autoUnhideAtMillis` = ?,`dailyHideTimeMinutes` = ?,`deletedAtMillis` = ?,`newChatStartMillis` = ? WHERE `threadId` = ?";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, ThreadLockEntity threadLockEntity) {
                supportSQLiteStatement.bindLong(1, threadLockEntity.getThreadId());
                supportSQLiteStatement.bindLong(2, threadLockEntity.isLocked() ? 1L : 0L);
                supportSQLiteStatement.bindLong(3, threadLockEntity.isHidden() ? 1L : 0L);
                supportSQLiteStatement.bindLong(4, threadLockEntity.getLockedAtMillis());
                supportSQLiteStatement.bindLong(5, threadLockEntity.isMuted() ? 1L : 0L);
                supportSQLiteStatement.bindLong(6, threadLockEntity.isArchived() ? 1L : 0L);
                supportSQLiteStatement.bindLong(7, threadLockEntity.getMuteUntilMillis());
                supportSQLiteStatement.bindLong(8, threadLockEntity.getAutoUnhideAtMillis());
                supportSQLiteStatement.bindLong(9, threadLockEntity.getDailyHideTimeMinutes());
                supportSQLiteStatement.bindLong(10, threadLockEntity.getDeletedAtMillis());
                supportSQLiteStatement.bindLong(11, threadLockEntity.getNewChatStartMillis());
                supportSQLiteStatement.bindLong(12, threadLockEntity.getThreadId());
            }
        });
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object setLocked(final long threadId, final boolean locked, final long ts, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.12
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement acquire = ThreadLockDao_Impl.this.__preparedStmtOfSetLocked.acquire();
                acquire.bindLong(1, locked ? 1L : 0L);
                acquire.bindLong(2, ts);
                acquire.bindLong(3, threadId);
                try {
                    ThreadLockDao_Impl.this.__db.beginTransaction();
                    try {
                        acquire.executeUpdateDelete();
                        ThreadLockDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        ThreadLockDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    ThreadLockDao_Impl.this.__preparedStmtOfSetLocked.release(acquire);
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object setHidden(final long threadId, final boolean hidden, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.13
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement acquire = ThreadLockDao_Impl.this.__preparedStmtOfSetHidden.acquire();
                acquire.bindLong(1, hidden ? 1L : 0L);
                acquire.bindLong(2, threadId);
                try {
                    ThreadLockDao_Impl.this.__db.beginTransaction();
                    try {
                        acquire.executeUpdateDelete();
                        ThreadLockDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        ThreadLockDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    ThreadLockDao_Impl.this.__preparedStmtOfSetHidden.release(acquire);
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object setMuted(final long threadId, final boolean muted, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.14
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement acquire = ThreadLockDao_Impl.this.__preparedStmtOfSetMuted.acquire();
                acquire.bindLong(1, muted ? 1L : 0L);
                acquire.bindLong(2, threadId);
                try {
                    ThreadLockDao_Impl.this.__db.beginTransaction();
                    try {
                        acquire.executeUpdateDelete();
                        ThreadLockDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        ThreadLockDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    ThreadLockDao_Impl.this.__preparedStmtOfSetMuted.release(acquire);
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object setMutedUntil(final long threadId, final boolean muted, final long muteUntilMillis, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.15
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement acquire = ThreadLockDao_Impl.this.__preparedStmtOfSetMutedUntil.acquire();
                acquire.bindLong(1, muted ? 1L : 0L);
                acquire.bindLong(2, muteUntilMillis);
                acquire.bindLong(3, threadId);
                try {
                    ThreadLockDao_Impl.this.__db.beginTransaction();
                    try {
                        acquire.executeUpdateDelete();
                        ThreadLockDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        ThreadLockDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    ThreadLockDao_Impl.this.__preparedStmtOfSetMutedUntil.release(acquire);
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object setArchived(final long threadId, final boolean archived, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.16
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement acquire = ThreadLockDao_Impl.this.__preparedStmtOfSetArchived.acquire();
                acquire.bindLong(1, archived ? 1L : 0L);
                acquire.bindLong(2, threadId);
                try {
                    ThreadLockDao_Impl.this.__db.beginTransaction();
                    try {
                        acquire.executeUpdateDelete();
                        ThreadLockDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        ThreadLockDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    ThreadLockDao_Impl.this.__preparedStmtOfSetArchived.release(acquire);
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object setAutoUnhideAt(final long threadId, final long timestamp, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.17
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement _stmt = ThreadLockDao_Impl.this.__preparedStmtOfSetAutoUnhideAt.acquire();
                _stmt.bindLong(1, timestamp);
                _stmt.bindLong(2, threadId);
                try {
                    ThreadLockDao_Impl.this.__db.beginTransaction();
                    try {
                        _stmt.executeUpdateDelete();
                        ThreadLockDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        ThreadLockDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    ThreadLockDao_Impl.this.__preparedStmtOfSetAutoUnhideAt.release(_stmt);
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object setDeletedAt(final long threadId, final long millis, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.18
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement _stmt = ThreadLockDao_Impl.this.__preparedStmtOfSetDeletedAt.acquire();
                _stmt.bindLong(1, millis);
                _stmt.bindLong(2, threadId);
                try {
                    ThreadLockDao_Impl.this.__db.beginTransaction();
                    try {
                        _stmt.executeUpdateDelete();
                        ThreadLockDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        ThreadLockDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    ThreadLockDao_Impl.this.__preparedStmtOfSetDeletedAt.release(_stmt);
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object setDailyHideTime(final long threadId, final int minutes, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.19
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement _stmt = ThreadLockDao_Impl.this.__preparedStmtOfSetDailyHideTime.acquire();
                _stmt.bindLong(1, minutes);
                _stmt.bindLong(2, threadId);
                try {
                    ThreadLockDao_Impl.this.__db.beginTransaction();
                    try {
                        _stmt.executeUpdateDelete();
                        ThreadLockDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        ThreadLockDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    ThreadLockDao_Impl.this.__preparedStmtOfSetDailyHideTime.release(_stmt);
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object clear(final long threadId, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.20
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement _stmt = ThreadLockDao_Impl.this.__preparedStmtOfClear.acquire();
                _stmt.bindLong(1, threadId);
                try {
                    ThreadLockDao_Impl.this.__db.beginTransaction();
                    try {
                        _stmt.executeUpdateDelete();
                        ThreadLockDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        ThreadLockDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    ThreadLockDao_Impl.this.__preparedStmtOfClear.release(_stmt);
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object upsert(final ThreadLockEntity entity, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.21
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                ThreadLockDao_Impl.this.__db.beginTransaction();
                try {
                    ThreadLockDao_Impl.this.__upsertionAdapterOfThreadLockEntity.upsert((EntityUpsertionAdapter) entity);
                    ThreadLockDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    ThreadLockDao_Impl.this.__db.endTransaction();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object getForThread(final long threadId, final Continuation<? super ThreadLockEntity> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM thread_lock_state WHERE threadId = ?", 1);
        _statement.bindLong(1, threadId);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<ThreadLockEntity>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.22
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public ThreadLockEntity call() throws Exception {
                ThreadLockEntity _result;
                Cursor _cursor = DBUtil.query(ThreadLockDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfIsLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocked");
                    int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
                    int _cursorIndexOfLockedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lockedAtMillis");
                    int _cursorIndexOfIsMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "isMuted");
                    int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
                    int _cursorIndexOfMuteUntilMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "muteUntilMillis");
                    int _cursorIndexOfAutoUnhideAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "autoUnhideAtMillis");
                    int _cursorIndexOfDailyHideTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyHideTimeMinutes");
                    int _cursorIndexOfDeletedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAtMillis");
                    int _cursorIndexOfNewChatStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "newChatStartMillis");
                    if (_cursor.moveToFirst()) {
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsLocked);
                        boolean _tmpIsLocked = _tmp != 0;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsHidden);
                        boolean _tmpIsHidden = _tmp_1 != 0;
                        long _tmpLockedAtMillis = _cursor.getLong(_cursorIndexOfLockedAtMillis);
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsMuted);
                        boolean _tmpIsMuted = _tmp_2 != 0;
                        int _tmp_3 = _cursor.getInt(_cursorIndexOfIsArchived);
                        boolean _tmpIsArchived = _tmp_3 != 0;
                        long _tmpMuteUntilMillis = _cursor.getLong(_cursorIndexOfMuteUntilMillis);
                        long _tmpAutoUnhideAtMillis = _cursor.getLong(_cursorIndexOfAutoUnhideAtMillis);
                        int _tmpDailyHideTimeMinutes = _cursor.getInt(_cursorIndexOfDailyHideTimeMinutes);
                        long _tmpDeletedAtMillis = _cursor.getLong(_cursorIndexOfDeletedAtMillis);
                        long _tmpNewChatStartMillis = _cursor.getLong(_cursorIndexOfNewChatStartMillis);
                        _result = new ThreadLockEntity(_tmpThreadId, _tmpIsLocked, _tmpIsHidden, _tmpLockedAtMillis, _tmpIsMuted, _tmpIsArchived, _tmpMuteUntilMillis, _tmpAutoUnhideAtMillis, _tmpDailyHideTimeMinutes, _tmpDeletedAtMillis, _tmpNewChatStartMillis);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object getAllLockStates(final Continuation<? super List<ThreadLockEntity>> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM thread_lock_state", 0);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<List<ThreadLockEntity>>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.23
            @Override // java.util.concurrent.Callable
            public List<ThreadLockEntity> call() throws Exception {
                boolean z = false;
                Cursor _cursor = DBUtil.query(ThreadLockDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfIsLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocked");
                    int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
                    int _cursorIndexOfLockedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lockedAtMillis");
                    int _cursorIndexOfIsMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "isMuted");
                    int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
                    int _cursorIndexOfMuteUntilMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "muteUntilMillis");
                    int _cursorIndexOfAutoUnhideAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "autoUnhideAtMillis");
                    int _cursorIndexOfDailyHideTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyHideTimeMinutes");
                    int _cursorIndexOfDeletedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAtMillis");
                    int _cursorIndexOfNewChatStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "newChatStartMillis");
                    List<ThreadLockEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsLocked);
                        boolean _tmpIsLocked = _tmp != 0 ? true : z;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsHidden);
                        boolean _tmpIsHidden = _tmp_1 != 0 ? true : z;
                        long _tmpLockedAtMillis = _cursor.getLong(_cursorIndexOfLockedAtMillis);
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsMuted);
                        boolean _tmpIsMuted = _tmp_2 != 0 ? true : z;
                        int _tmp_3 = _cursor.getInt(_cursorIndexOfIsArchived);
                        boolean _tmpIsArchived = _tmp_3 != 0 ? true : z;
                        long _tmpMuteUntilMillis = _cursor.getLong(_cursorIndexOfMuteUntilMillis);
                        long _tmpAutoUnhideAtMillis = _cursor.getLong(_cursorIndexOfAutoUnhideAtMillis);
                        int _tmpDailyHideTimeMinutes = _cursor.getInt(_cursorIndexOfDailyHideTimeMinutes);
                        long _tmpDeletedAtMillis = _cursor.getLong(_cursorIndexOfDeletedAtMillis);
                        long _tmpNewChatStartMillis = _cursor.getLong(_cursorIndexOfNewChatStartMillis);
                        ThreadLockEntity _item = new ThreadLockEntity(_tmpThreadId, _tmpIsLocked, _tmpIsHidden, _tmpLockedAtMillis, _tmpIsMuted, _tmpIsArchived, _tmpMuteUntilMillis, _tmpAutoUnhideAtMillis, _tmpDailyHideTimeMinutes, _tmpDeletedAtMillis, _tmpNewChatStartMillis);
                        _result.add(_item);
                        z = false;
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Flow<List<ThreadLockEntity>> observeAll() {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM thread_lock_state", 0);
        return CoroutinesRoom.createFlow(this.__db, false, new String[]{"thread_lock_state"}, new Callable<List<ThreadLockEntity>>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.24
            @Override // java.util.concurrent.Callable
            public List<ThreadLockEntity> call() throws Exception {
                boolean z = false;
                Cursor _cursor = DBUtil.query(ThreadLockDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfIsLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocked");
                    int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
                    int _cursorIndexOfLockedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lockedAtMillis");
                    int _cursorIndexOfIsMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "isMuted");
                    int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
                    int _cursorIndexOfMuteUntilMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "muteUntilMillis");
                    int _cursorIndexOfAutoUnhideAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "autoUnhideAtMillis");
                    int _cursorIndexOfDailyHideTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyHideTimeMinutes");
                    int _cursorIndexOfDeletedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAtMillis");
                    int _cursorIndexOfNewChatStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "newChatStartMillis");
                    List<ThreadLockEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsLocked);
                        boolean _tmpIsLocked = _tmp != 0 ? true : z;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsHidden);
                        boolean _tmpIsHidden = _tmp_1 != 0 ? true : z;
                        long _tmpLockedAtMillis = _cursor.getLong(_cursorIndexOfLockedAtMillis);
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsMuted);
                        boolean _tmpIsMuted = _tmp_2 != 0 ? true : z;
                        int _tmp_3 = _cursor.getInt(_cursorIndexOfIsArchived);
                        boolean _tmpIsArchived = _tmp_3 != 0 ? true : z;
                        long _tmpMuteUntilMillis = _cursor.getLong(_cursorIndexOfMuteUntilMillis);
                        long _tmpAutoUnhideAtMillis = _cursor.getLong(_cursorIndexOfAutoUnhideAtMillis);
                        int _tmpDailyHideTimeMinutes = _cursor.getInt(_cursorIndexOfDailyHideTimeMinutes);
                        long _tmpDeletedAtMillis = _cursor.getLong(_cursorIndexOfDeletedAtMillis);
                        long _tmpNewChatStartMillis = _cursor.getLong(_cursorIndexOfNewChatStartMillis);
                        ThreadLockEntity _item = new ThreadLockEntity(_tmpThreadId, _tmpIsLocked, _tmpIsHidden, _tmpLockedAtMillis, _tmpIsMuted, _tmpIsArchived, _tmpMuteUntilMillis, _tmpAutoUnhideAtMillis, _tmpDailyHideTimeMinutes, _tmpDeletedAtMillis, _tmpNewChatStartMillis);
                        _result.add(_item);
                        z = false;
                    }
                    return _result;
                } finally {
                    _cursor.close();
                }
            }

            protected void finalize() {
                _statement.release();
            }
        });
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object getAllLocked(final Continuation<? super List<ThreadLockEntity>> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM thread_lock_state WHERE isLocked = 1", 0);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<List<ThreadLockEntity>>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.25
            @Override // java.util.concurrent.Callable
            public List<ThreadLockEntity> call() throws Exception {
                boolean z = false;
                Cursor _cursor = DBUtil.query(ThreadLockDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfIsLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocked");
                    int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
                    int _cursorIndexOfLockedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lockedAtMillis");
                    int _cursorIndexOfIsMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "isMuted");
                    int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
                    int _cursorIndexOfMuteUntilMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "muteUntilMillis");
                    int _cursorIndexOfAutoUnhideAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "autoUnhideAtMillis");
                    int _cursorIndexOfDailyHideTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyHideTimeMinutes");
                    int _cursorIndexOfDeletedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAtMillis");
                    int _cursorIndexOfNewChatStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "newChatStartMillis");
                    List<ThreadLockEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsLocked);
                        boolean _tmpIsLocked = _tmp != 0 ? true : z;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsHidden);
                        boolean _tmpIsHidden = _tmp_1 != 0 ? true : z;
                        long _tmpLockedAtMillis = _cursor.getLong(_cursorIndexOfLockedAtMillis);
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsMuted);
                        boolean _tmpIsMuted = _tmp_2 != 0 ? true : z;
                        int _tmp_3 = _cursor.getInt(_cursorIndexOfIsArchived);
                        boolean _tmpIsArchived = _tmp_3 != 0 ? true : z;
                        long _tmpMuteUntilMillis = _cursor.getLong(_cursorIndexOfMuteUntilMillis);
                        long _tmpAutoUnhideAtMillis = _cursor.getLong(_cursorIndexOfAutoUnhideAtMillis);
                        int _tmpDailyHideTimeMinutes = _cursor.getInt(_cursorIndexOfDailyHideTimeMinutes);
                        long _tmpDeletedAtMillis = _cursor.getLong(_cursorIndexOfDeletedAtMillis);
                        long _tmpNewChatStartMillis = _cursor.getLong(_cursorIndexOfNewChatStartMillis);
                        ThreadLockEntity _item = new ThreadLockEntity(_tmpThreadId, _tmpIsLocked, _tmpIsHidden, _tmpLockedAtMillis, _tmpIsMuted, _tmpIsArchived, _tmpMuteUntilMillis, _tmpAutoUnhideAtMillis, _tmpDailyHideTimeMinutes, _tmpDeletedAtMillis, _tmpNewChatStartMillis);
                        _result.add(_item);
                        z = false;
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object getAllHidden(final Continuation<? super List<ThreadLockEntity>> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM thread_lock_state WHERE isHidden = 1", 0);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<List<ThreadLockEntity>>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.26
            @Override // java.util.concurrent.Callable
            public List<ThreadLockEntity> call() throws Exception {
                boolean z = false;
                Cursor _cursor = DBUtil.query(ThreadLockDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfIsLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocked");
                    int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
                    int _cursorIndexOfLockedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lockedAtMillis");
                    int _cursorIndexOfIsMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "isMuted");
                    int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
                    int _cursorIndexOfMuteUntilMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "muteUntilMillis");
                    int _cursorIndexOfAutoUnhideAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "autoUnhideAtMillis");
                    int _cursorIndexOfDailyHideTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyHideTimeMinutes");
                    int _cursorIndexOfDeletedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAtMillis");
                    int _cursorIndexOfNewChatStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "newChatStartMillis");
                    List<ThreadLockEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsLocked);
                        boolean _tmpIsLocked = _tmp != 0 ? true : z;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsHidden);
                        boolean _tmpIsHidden = _tmp_1 != 0 ? true : z;
                        long _tmpLockedAtMillis = _cursor.getLong(_cursorIndexOfLockedAtMillis);
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsMuted);
                        boolean _tmpIsMuted = _tmp_2 != 0 ? true : z;
                        int _tmp_3 = _cursor.getInt(_cursorIndexOfIsArchived);
                        boolean _tmpIsArchived = _tmp_3 != 0 ? true : z;
                        long _tmpMuteUntilMillis = _cursor.getLong(_cursorIndexOfMuteUntilMillis);
                        long _tmpAutoUnhideAtMillis = _cursor.getLong(_cursorIndexOfAutoUnhideAtMillis);
                        int _tmpDailyHideTimeMinutes = _cursor.getInt(_cursorIndexOfDailyHideTimeMinutes);
                        long _tmpDeletedAtMillis = _cursor.getLong(_cursorIndexOfDeletedAtMillis);
                        long _tmpNewChatStartMillis = _cursor.getLong(_cursorIndexOfNewChatStartMillis);
                        ThreadLockEntity _item = new ThreadLockEntity(_tmpThreadId, _tmpIsLocked, _tmpIsHidden, _tmpLockedAtMillis, _tmpIsMuted, _tmpIsArchived, _tmpMuteUntilMillis, _tmpAutoUnhideAtMillis, _tmpDailyHideTimeMinutes, _tmpDeletedAtMillis, _tmpNewChatStartMillis);
                        _result.add(_item);
                        z = false;
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object getTrashedThreads(final Continuation<? super List<ThreadLockEntity>> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM thread_lock_state WHERE deletedAtMillis > 0 ORDER BY deletedAtMillis DESC", 0);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<List<ThreadLockEntity>>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.27
            @Override // java.util.concurrent.Callable
            public List<ThreadLockEntity> call() throws Exception {
                boolean z = false;
                Cursor _cursor = DBUtil.query(ThreadLockDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfIsLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocked");
                    int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
                    int _cursorIndexOfLockedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lockedAtMillis");
                    int _cursorIndexOfIsMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "isMuted");
                    int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
                    int _cursorIndexOfMuteUntilMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "muteUntilMillis");
                    int _cursorIndexOfAutoUnhideAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "autoUnhideAtMillis");
                    int _cursorIndexOfDailyHideTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyHideTimeMinutes");
                    int _cursorIndexOfDeletedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAtMillis");
                    int _cursorIndexOfNewChatStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "newChatStartMillis");
                    List<ThreadLockEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsLocked);
                        boolean _tmpIsLocked = _tmp != 0 ? true : z;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsHidden);
                        boolean _tmpIsHidden = _tmp_1 != 0 ? true : z;
                        long _tmpLockedAtMillis = _cursor.getLong(_cursorIndexOfLockedAtMillis);
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsMuted);
                        boolean _tmpIsMuted = _tmp_2 != 0 ? true : z;
                        int _tmp_3 = _cursor.getInt(_cursorIndexOfIsArchived);
                        boolean _tmpIsArchived = _tmp_3 != 0 ? true : z;
                        long _tmpMuteUntilMillis = _cursor.getLong(_cursorIndexOfMuteUntilMillis);
                        long _tmpAutoUnhideAtMillis = _cursor.getLong(_cursorIndexOfAutoUnhideAtMillis);
                        int _tmpDailyHideTimeMinutes = _cursor.getInt(_cursorIndexOfDailyHideTimeMinutes);
                        long _tmpDeletedAtMillis = _cursor.getLong(_cursorIndexOfDeletedAtMillis);
                        long _tmpNewChatStartMillis = _cursor.getLong(_cursorIndexOfNewChatStartMillis);
                        ThreadLockEntity _item = new ThreadLockEntity(_tmpThreadId, _tmpIsLocked, _tmpIsHidden, _tmpLockedAtMillis, _tmpIsMuted, _tmpIsArchived, _tmpMuteUntilMillis, _tmpAutoUnhideAtMillis, _tmpDailyHideTimeMinutes, _tmpDeletedAtMillis, _tmpNewChatStartMillis);
                        _result.add(_item);
                        z = false;
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object getThreadsDueForPermanentDeletion(final long cutoff, final Continuation<? super List<ThreadLockEntity>> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM thread_lock_state WHERE deletedAtMillis > 0 AND deletedAtMillis <= ?", 1);
        _statement.bindLong(1, cutoff);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<List<ThreadLockEntity>>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.28
            @Override // java.util.concurrent.Callable
            public List<ThreadLockEntity> call() throws Exception {
                boolean z = false;
                Cursor _cursor = DBUtil.query(ThreadLockDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfIsLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocked");
                    int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
                    int _cursorIndexOfLockedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lockedAtMillis");
                    int _cursorIndexOfIsMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "isMuted");
                    int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
                    int _cursorIndexOfMuteUntilMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "muteUntilMillis");
                    int _cursorIndexOfAutoUnhideAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "autoUnhideAtMillis");
                    int _cursorIndexOfDailyHideTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyHideTimeMinutes");
                    int _cursorIndexOfDeletedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAtMillis");
                    int _cursorIndexOfNewChatStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "newChatStartMillis");
                    List<ThreadLockEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsLocked);
                        boolean _tmpIsLocked = _tmp != 0 ? true : z;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsHidden);
                        boolean _tmpIsHidden = _tmp_1 != 0 ? true : z;
                        long _tmpLockedAtMillis = _cursor.getLong(_cursorIndexOfLockedAtMillis);
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsMuted);
                        boolean _tmpIsMuted = _tmp_2 != 0 ? true : z;
                        int _tmp_3 = _cursor.getInt(_cursorIndexOfIsArchived);
                        boolean _tmpIsArchived = _tmp_3 != 0 ? true : z;
                        long _tmpMuteUntilMillis = _cursor.getLong(_cursorIndexOfMuteUntilMillis);
                        long _tmpAutoUnhideAtMillis = _cursor.getLong(_cursorIndexOfAutoUnhideAtMillis);
                        int _tmpDailyHideTimeMinutes = _cursor.getInt(_cursorIndexOfDailyHideTimeMinutes);
                        long _tmpDeletedAtMillis = _cursor.getLong(_cursorIndexOfDeletedAtMillis);
                        long _tmpNewChatStartMillis = _cursor.getLong(_cursorIndexOfNewChatStartMillis);
                        ThreadLockEntity _item = new ThreadLockEntity(_tmpThreadId, _tmpIsLocked, _tmpIsHidden, _tmpLockedAtMillis, _tmpIsMuted, _tmpIsArchived, _tmpMuteUntilMillis, _tmpAutoUnhideAtMillis, _tmpDailyHideTimeMinutes, _tmpDeletedAtMillis, _tmpNewChatStartMillis);
                        _result.add(_item);
                        z = false;
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object getThreadsWithDailyHide(final Continuation<? super List<ThreadLockEntity>> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM thread_lock_state WHERE dailyHideTimeMinutes >= 0", 0);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<List<ThreadLockEntity>>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.29
            @Override // java.util.concurrent.Callable
            public List<ThreadLockEntity> call() throws Exception {
                boolean z = false;
                Cursor _cursor = DBUtil.query(ThreadLockDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfIsLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocked");
                    int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
                    int _cursorIndexOfLockedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lockedAtMillis");
                    int _cursorIndexOfIsMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "isMuted");
                    int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
                    int _cursorIndexOfMuteUntilMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "muteUntilMillis");
                    int _cursorIndexOfAutoUnhideAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "autoUnhideAtMillis");
                    int _cursorIndexOfDailyHideTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyHideTimeMinutes");
                    int _cursorIndexOfDeletedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAtMillis");
                    int _cursorIndexOfNewChatStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "newChatStartMillis");
                    List<ThreadLockEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsLocked);
                        boolean _tmpIsLocked = _tmp != 0 ? true : z;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsHidden);
                        boolean _tmpIsHidden = _tmp_1 != 0 ? true : z;
                        long _tmpLockedAtMillis = _cursor.getLong(_cursorIndexOfLockedAtMillis);
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsMuted);
                        boolean _tmpIsMuted = _tmp_2 != 0 ? true : z;
                        int _tmp_3 = _cursor.getInt(_cursorIndexOfIsArchived);
                        boolean _tmpIsArchived = _tmp_3 != 0 ? true : z;
                        long _tmpMuteUntilMillis = _cursor.getLong(_cursorIndexOfMuteUntilMillis);
                        long _tmpAutoUnhideAtMillis = _cursor.getLong(_cursorIndexOfAutoUnhideAtMillis);
                        int _tmpDailyHideTimeMinutes = _cursor.getInt(_cursorIndexOfDailyHideTimeMinutes);
                        long _tmpDeletedAtMillis = _cursor.getLong(_cursorIndexOfDeletedAtMillis);
                        long _tmpNewChatStartMillis = _cursor.getLong(_cursorIndexOfNewChatStartMillis);
                        ThreadLockEntity _item = new ThreadLockEntity(_tmpThreadId, _tmpIsLocked, _tmpIsHidden, _tmpLockedAtMillis, _tmpIsMuted, _tmpIsArchived, _tmpMuteUntilMillis, _tmpAutoUnhideAtMillis, _tmpDailyHideTimeMinutes, _tmpDeletedAtMillis, _tmpNewChatStartMillis);
                        _result.add(_item);
                        z = false;
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object getThreadsDueForAutoHide(final long now, final Continuation<? super List<ThreadLockEntity>> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM thread_lock_state WHERE isHidden = 0 AND autoUnhideAtMillis > 0 AND autoUnhideAtMillis <= ?", 1);
        _statement.bindLong(1, now);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<List<ThreadLockEntity>>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.30
            @Override // java.util.concurrent.Callable
            public List<ThreadLockEntity> call() throws Exception {
                boolean z = false;
                Cursor _cursor = DBUtil.query(ThreadLockDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfIsLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocked");
                    int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
                    int _cursorIndexOfLockedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lockedAtMillis");
                    int _cursorIndexOfIsMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "isMuted");
                    int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
                    int _cursorIndexOfMuteUntilMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "muteUntilMillis");
                    int _cursorIndexOfAutoUnhideAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "autoUnhideAtMillis");
                    int _cursorIndexOfDailyHideTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyHideTimeMinutes");
                    int _cursorIndexOfDeletedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAtMillis");
                    int _cursorIndexOfNewChatStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "newChatStartMillis");
                    List<ThreadLockEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsLocked);
                        boolean _tmpIsLocked = _tmp != 0 ? true : z;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsHidden);
                        boolean _tmpIsHidden = _tmp_1 != 0 ? true : z;
                        long _tmpLockedAtMillis = _cursor.getLong(_cursorIndexOfLockedAtMillis);
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsMuted);
                        boolean _tmpIsMuted = _tmp_2 != 0 ? true : z;
                        int _tmp_3 = _cursor.getInt(_cursorIndexOfIsArchived);
                        boolean _tmpIsArchived = _tmp_3 != 0 ? true : z;
                        long _tmpMuteUntilMillis = _cursor.getLong(_cursorIndexOfMuteUntilMillis);
                        long _tmpAutoUnhideAtMillis = _cursor.getLong(_cursorIndexOfAutoUnhideAtMillis);
                        int _tmpDailyHideTimeMinutes = _cursor.getInt(_cursorIndexOfDailyHideTimeMinutes);
                        long _tmpDeletedAtMillis = _cursor.getLong(_cursorIndexOfDeletedAtMillis);
                        long _tmpNewChatStartMillis = _cursor.getLong(_cursorIndexOfNewChatStartMillis);
                        ThreadLockEntity _item = new ThreadLockEntity(_tmpThreadId, _tmpIsLocked, _tmpIsHidden, _tmpLockedAtMillis, _tmpIsMuted, _tmpIsArchived, _tmpMuteUntilMillis, _tmpAutoUnhideAtMillis, _tmpDailyHideTimeMinutes, _tmpDeletedAtMillis, _tmpNewChatStartMillis);
                        _result.add(_item);
                        z = false;
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object getThreadsDueForAutoUnhide(final long now, final Continuation<? super List<ThreadLockEntity>> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM thread_lock_state WHERE isHidden = 1 AND autoUnhideAtMillis > 0 AND autoUnhideAtMillis <= ?", 1);
        _statement.bindLong(1, now);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<List<ThreadLockEntity>>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.31
            @Override // java.util.concurrent.Callable
            public List<ThreadLockEntity> call() throws Exception {
                boolean z = false;
                Cursor _cursor = DBUtil.query(ThreadLockDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfIsLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocked");
                    int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
                    int _cursorIndexOfLockedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lockedAtMillis");
                    int _cursorIndexOfIsMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "isMuted");
                    int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
                    int _cursorIndexOfMuteUntilMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "muteUntilMillis");
                    int _cursorIndexOfAutoUnhideAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "autoUnhideAtMillis");
                    int _cursorIndexOfDailyHideTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyHideTimeMinutes");
                    int _cursorIndexOfDeletedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAtMillis");
                    int _cursorIndexOfNewChatStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "newChatStartMillis");
                    List<ThreadLockEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsLocked);
                        boolean _tmpIsLocked = _tmp != 0 ? true : z;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsHidden);
                        boolean _tmpIsHidden = _tmp_1 != 0 ? true : z;
                        long _tmpLockedAtMillis = _cursor.getLong(_cursorIndexOfLockedAtMillis);
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsMuted);
                        boolean _tmpIsMuted = _tmp_2 != 0 ? true : z;
                        int _tmp_3 = _cursor.getInt(_cursorIndexOfIsArchived);
                        boolean _tmpIsArchived = _tmp_3 != 0 ? true : z;
                        long _tmpMuteUntilMillis = _cursor.getLong(_cursorIndexOfMuteUntilMillis);
                        long _tmpAutoUnhideAtMillis = _cursor.getLong(_cursorIndexOfAutoUnhideAtMillis);
                        int _tmpDailyHideTimeMinutes = _cursor.getInt(_cursorIndexOfDailyHideTimeMinutes);
                        long _tmpDeletedAtMillis = _cursor.getLong(_cursorIndexOfDeletedAtMillis);
                        long _tmpNewChatStartMillis = _cursor.getLong(_cursorIndexOfNewChatStartMillis);
                        ThreadLockEntity _item = new ThreadLockEntity(_tmpThreadId, _tmpIsLocked, _tmpIsHidden, _tmpLockedAtMillis, _tmpIsMuted, _tmpIsArchived, _tmpMuteUntilMillis, _tmpAutoUnhideAtMillis, _tmpDailyHideTimeMinutes, _tmpDeletedAtMillis, _tmpNewChatStartMillis);
                        _result.add(_item);
                        z = false;
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.ThreadLockDao
    public Object getThreadsDueForAutoUnmute(final long now, final Continuation<? super List<ThreadLockEntity>> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM thread_lock_state WHERE isMuted = 1 AND muteUntilMillis > 0 AND muteUntilMillis <= ?", 1);
        _statement.bindLong(1, now);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<List<ThreadLockEntity>>() { // from class: com.meharenterprises.originsms.data.db.ThreadLockDao_Impl.32
            @Override // java.util.concurrent.Callable
            public List<ThreadLockEntity> call() throws Exception {
                boolean z = false;
                Cursor _cursor = DBUtil.query(ThreadLockDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfIsLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocked");
                    int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
                    int _cursorIndexOfLockedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lockedAtMillis");
                    int _cursorIndexOfIsMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "isMuted");
                    int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
                    int _cursorIndexOfMuteUntilMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "muteUntilMillis");
                    int _cursorIndexOfAutoUnhideAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "autoUnhideAtMillis");
                    int _cursorIndexOfDailyHideTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyHideTimeMinutes");
                    int _cursorIndexOfDeletedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAtMillis");
                    int _cursorIndexOfNewChatStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "newChatStartMillis");
                    List<ThreadLockEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsLocked);
                        boolean _tmpIsLocked = _tmp != 0 ? true : z;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsHidden);
                        boolean _tmpIsHidden = _tmp_1 != 0 ? true : z;
                        long _tmpLockedAtMillis = _cursor.getLong(_cursorIndexOfLockedAtMillis);
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsMuted);
                        boolean _tmpIsMuted = _tmp_2 != 0 ? true : z;
                        int _tmp_3 = _cursor.getInt(_cursorIndexOfIsArchived);
                        boolean _tmpIsArchived = _tmp_3 != 0 ? true : z;
                        long _tmpMuteUntilMillis = _cursor.getLong(_cursorIndexOfMuteUntilMillis);
                        long _tmpAutoUnhideAtMillis = _cursor.getLong(_cursorIndexOfAutoUnhideAtMillis);
                        int _tmpDailyHideTimeMinutes = _cursor.getInt(_cursorIndexOfDailyHideTimeMinutes);
                        long _tmpDeletedAtMillis = _cursor.getLong(_cursorIndexOfDeletedAtMillis);
                        long _tmpNewChatStartMillis = _cursor.getLong(_cursorIndexOfNewChatStartMillis);
                        ThreadLockEntity _item = new ThreadLockEntity(_tmpThreadId, _tmpIsLocked, _tmpIsHidden, _tmpLockedAtMillis, _tmpIsMuted, _tmpIsArchived, _tmpMuteUntilMillis, _tmpAutoUnhideAtMillis, _tmpDailyHideTimeMinutes, _tmpDeletedAtMillis, _tmpNewChatStartMillis);
                        _result.add(_item);
                        z = false;
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
