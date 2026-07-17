package com.meharenterprises.originsms.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
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

/* loaded from: classes9.dex */
public final class StarredMessageDao_Impl implements StarredMessageDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<StarredMessageEntity> __insertionAdapterOfStarredMessageEntity;
    private final SharedSQLiteStatement __preparedStmtOfUnstar;

    public StarredMessageDao_Impl(final RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfStarredMessageEntity = new EntityInsertionAdapter<StarredMessageEntity>(__db) { // from class: com.meharenterprises.originsms.data.db.StarredMessageDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "INSERT OR REPLACE INTO `starred_messages` (`messageId`,`threadId`,`address`,`body`,`dateMillis`,`starredAtMillis`) VALUES (?,?,?,?,?,?)";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityInsertionAdapter
            public void bind(final SupportSQLiteStatement statement, final StarredMessageEntity entity) {
                statement.bindLong(1, entity.getMessageId());
                statement.bindLong(2, entity.getThreadId());
                statement.bindString(3, entity.getAddress());
                statement.bindString(4, entity.getBody());
                statement.bindLong(5, entity.getDateMillis());
                statement.bindLong(6, entity.getStarredAtMillis());
            }
        };
        this.__preparedStmtOfUnstar = new SharedSQLiteStatement(__db) { // from class: com.meharenterprises.originsms.data.db.StarredMessageDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM starred_messages WHERE messageId = ?";
            }
        };
    }

    @Override // com.meharenterprises.originsms.data.db.StarredMessageDao
    public Object star(final StarredMessageEntity entity, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.StarredMessageDao_Impl.3
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                StarredMessageDao_Impl.this.__db.beginTransaction();
                try {
                    StarredMessageDao_Impl.this.__insertionAdapterOfStarredMessageEntity.insert((EntityInsertionAdapter) entity);
                    StarredMessageDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    StarredMessageDao_Impl.this.__db.endTransaction();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.StarredMessageDao
    public Object unstar(final long messageId, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.StarredMessageDao_Impl.4
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement _stmt = StarredMessageDao_Impl.this.__preparedStmtOfUnstar.acquire();
                _stmt.bindLong(1, messageId);
                try {
                    StarredMessageDao_Impl.this.__db.beginTransaction();
                    try {
                        _stmt.executeUpdateDelete();
                        StarredMessageDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        StarredMessageDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    StarredMessageDao_Impl.this.__preparedStmtOfUnstar.release(_stmt);
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.StarredMessageDao
    public Object getAll(final Continuation<? super List<StarredMessageEntity>> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM starred_messages ORDER BY dateMillis DESC", 0);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<List<StarredMessageEntity>>() { // from class: com.meharenterprises.originsms.data.db.StarredMessageDao_Impl.5
            @Override // java.util.concurrent.Callable
            public List<StarredMessageEntity> call() throws Exception {
                Cursor _cursor = DBUtil.query(StarredMessageDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "messageId");
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
                    int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
                    int _cursorIndexOfDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateMillis");
                    int _cursorIndexOfStarredAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "starredAtMillis");
                    List<StarredMessageEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        long _tmpMessageId = _cursor.getLong(_cursorIndexOfMessageId);
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        String _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
                        String _tmpBody = _cursor.getString(_cursorIndexOfBody);
                        long _tmpDateMillis = _cursor.getLong(_cursorIndexOfDateMillis);
                        long _tmpStarredAtMillis = _cursor.getLong(_cursorIndexOfStarredAtMillis);
                        StarredMessageEntity _item = new StarredMessageEntity(_tmpMessageId, _tmpThreadId, _tmpAddress, _tmpBody, _tmpDateMillis, _tmpStarredAtMillis);
                        _result.add(_item);
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.StarredMessageDao
    public Object getAllIds(final Continuation<? super List<Long>> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT messageId FROM starred_messages", 0);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<List<Long>>() { // from class: com.meharenterprises.originsms.data.db.StarredMessageDao_Impl.6
            @Override // java.util.concurrent.Callable
            public List<Long> call() throws Exception {
                Cursor _cursor = DBUtil.query(StarredMessageDao_Impl.this.__db, _statement, false, null);
                try {
                    List<Long> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        Long _item = Long.valueOf(_cursor.getLong(0));
                        _result.add(_item);
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.StarredMessageDao
    public Object isStarred(final long messageId, final Continuation<? super Integer> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT COUNT(*) FROM starred_messages WHERE messageId = ?", 1);
        _statement.bindLong(1, messageId);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<Integer>() { // from class: com.meharenterprises.originsms.data.db.StarredMessageDao_Impl.7
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Integer call() throws Exception {
                Integer _result;
                Cursor _cursor = DBUtil.query(StarredMessageDao_Impl.this.__db, _statement, false, null);
                try {
                    if (_cursor.moveToFirst()) {
                        int _tmp = _cursor.getInt(0);
                        _result = Integer.valueOf(_tmp);
                    } else {
                        _result = 0;
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
