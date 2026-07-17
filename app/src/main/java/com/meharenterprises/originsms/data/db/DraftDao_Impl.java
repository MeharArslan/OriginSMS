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

/* loaded from: classes9.dex */
public final class DraftDao_Impl implements DraftDao {
    private final RoomDatabase __db;
    private final EntityDeletionOrUpdateAdapter<DraftEntity> __deletionAdapterOfDraftEntity;
    private final SharedSQLiteStatement __preparedStmtOfClearDraft;
    private final EntityUpsertionAdapter<DraftEntity> __upsertionAdapterOfDraftEntity;

    public DraftDao_Impl(final RoomDatabase __db) {
        this.__db = __db;
        this.__deletionAdapterOfDraftEntity = new EntityDeletionOrUpdateAdapter<DraftEntity>(__db) { // from class: com.meharenterprises.originsms.data.db.DraftDao_Impl.1
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "DELETE FROM `drafts` WHERE `threadId` = ?";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(final SupportSQLiteStatement statement, final DraftEntity entity) {
                statement.bindLong(1, entity.getThreadId());
            }
        };
        this.__preparedStmtOfClearDraft = new SharedSQLiteStatement(__db) { // from class: com.meharenterprises.originsms.data.db.DraftDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM drafts WHERE threadId = ?";
            }
        };
        this.__upsertionAdapterOfDraftEntity = new EntityUpsertionAdapter<>(new EntityInsertionAdapter<DraftEntity>(__db) { // from class: com.meharenterprises.originsms.data.db.DraftDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "INSERT INTO `drafts` (`threadId`,`text`,`updatedAtMillis`) VALUES (?,?,?)";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityInsertionAdapter
            public void bind(final SupportSQLiteStatement statement, final DraftEntity entity) {
                statement.bindLong(1, entity.getThreadId());
                statement.bindString(2, entity.getText());
                statement.bindLong(3, entity.getUpdatedAtMillis());
            }
        }, new EntityDeletionOrUpdateAdapter<DraftEntity>(__db) { // from class: com.meharenterprises.originsms.data.db.DraftDao_Impl.4
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "UPDATE `drafts` SET `threadId` = ?,`text` = ?,`updatedAtMillis` = ? WHERE `threadId` = ?";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(final SupportSQLiteStatement statement, final DraftEntity entity) {
                statement.bindLong(1, entity.getThreadId());
                statement.bindString(2, entity.getText());
                statement.bindLong(3, entity.getUpdatedAtMillis());
                statement.bindLong(4, entity.getThreadId());
            }
        });
    }

    @Override // com.meharenterprises.originsms.data.db.DraftDao
    public Object deleteDraft(final DraftEntity entity, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.DraftDao_Impl.5
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                DraftDao_Impl.this.__db.beginTransaction();
                try {
                    DraftDao_Impl.this.__deletionAdapterOfDraftEntity.handle(entity);
                    DraftDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    DraftDao_Impl.this.__db.endTransaction();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.DraftDao
    public Object clearDraft(final long threadId, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.DraftDao_Impl.6
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement _stmt = DraftDao_Impl.this.__preparedStmtOfClearDraft.acquire();
                _stmt.bindLong(1, threadId);
                try {
                    DraftDao_Impl.this.__db.beginTransaction();
                    try {
                        _stmt.executeUpdateDelete();
                        DraftDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        DraftDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    DraftDao_Impl.this.__preparedStmtOfClearDraft.release(_stmt);
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.DraftDao
    public Object saveDraft(final DraftEntity entity, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.DraftDao_Impl.7
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                DraftDao_Impl.this.__db.beginTransaction();
                try {
                    DraftDao_Impl.this.__upsertionAdapterOfDraftEntity.upsert((EntityUpsertionAdapter) entity);
                    DraftDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    DraftDao_Impl.this.__db.endTransaction();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.DraftDao
    public Object getDraft(final long threadId, final Continuation<? super DraftEntity> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM drafts WHERE threadId = ?", 1);
        _statement.bindLong(1, threadId);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<DraftEntity>() { // from class: com.meharenterprises.originsms.data.db.DraftDao_Impl.8
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public DraftEntity call() throws Exception {
                DraftEntity _result;
                Cursor _cursor = DBUtil.query(DraftDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
                    int _cursorIndexOfUpdatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAtMillis");
                    if (_cursor.moveToFirst()) {
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        String _tmpText = _cursor.getString(_cursorIndexOfText);
                        long _tmpUpdatedAtMillis = _cursor.getLong(_cursorIndexOfUpdatedAtMillis);
                        _result = new DraftEntity(_tmpThreadId, _tmpText, _tmpUpdatedAtMillis);
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

    @Override // com.meharenterprises.originsms.data.db.DraftDao
    public Object getAllDrafts(final Continuation<? super List<DraftEntity>> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM drafts", 0);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<List<DraftEntity>>() { // from class: com.meharenterprises.originsms.data.db.DraftDao_Impl.9
            @Override // java.util.concurrent.Callable
            public List<DraftEntity> call() throws Exception {
                Cursor _cursor = DBUtil.query(DraftDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfThreadId = CursorUtil.getColumnIndexOrThrow(_cursor, "threadId");
                    int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
                    int _cursorIndexOfUpdatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAtMillis");
                    List<DraftEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        long _tmpThreadId = _cursor.getLong(_cursorIndexOfThreadId);
                        String _tmpText = _cursor.getString(_cursorIndexOfText);
                        long _tmpUpdatedAtMillis = _cursor.getLong(_cursorIndexOfUpdatedAtMillis);
                        DraftEntity _item = new DraftEntity(_tmpThreadId, _tmpText, _tmpUpdatedAtMillis);
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

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
