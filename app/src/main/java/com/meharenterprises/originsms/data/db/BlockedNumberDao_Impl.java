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
public final class BlockedNumberDao_Impl implements BlockedNumberDao {
    private final RoomDatabase __db;
    private final SharedSQLiteStatement __preparedStmtOfUnblock;
    private final EntityUpsertionAdapter<BlockedNumberEntity> __upsertionAdapterOfBlockedNumberEntity;

    public BlockedNumberDao_Impl(final RoomDatabase __db) {
        this.__db = __db;
        this.__preparedStmtOfUnblock = new SharedSQLiteStatement(__db) { // from class: com.meharenterprises.originsms.data.db.BlockedNumberDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM blocked_numbers WHERE normalizedNumber = ?";
            }
        };
        this.__upsertionAdapterOfBlockedNumberEntity = new EntityUpsertionAdapter<>(new EntityInsertionAdapter<BlockedNumberEntity>(__db) { // from class: com.meharenterprises.originsms.data.db.BlockedNumberDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "INSERT INTO `blocked_numbers` (`normalizedNumber`,`displayNumber`,`blockedAtMillis`) VALUES (?,?,?)";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityInsertionAdapter
            public void bind(final SupportSQLiteStatement statement, final BlockedNumberEntity entity) {
                statement.bindString(1, entity.getNormalizedNumber());
                statement.bindString(2, entity.getDisplayNumber());
                statement.bindLong(3, entity.getBlockedAtMillis());
            }
        }, new EntityDeletionOrUpdateAdapter<BlockedNumberEntity>(__db) { // from class: com.meharenterprises.originsms.data.db.BlockedNumberDao_Impl.3
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "UPDATE `blocked_numbers` SET `normalizedNumber` = ?,`displayNumber` = ?,`blockedAtMillis` = ? WHERE `normalizedNumber` = ?";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(final SupportSQLiteStatement statement, final BlockedNumberEntity entity) {
                statement.bindString(1, entity.getNormalizedNumber());
                statement.bindString(2, entity.getDisplayNumber());
                statement.bindLong(3, entity.getBlockedAtMillis());
                statement.bindString(4, entity.getNormalizedNumber());
            }
        });
    }

    @Override // com.meharenterprises.originsms.data.db.BlockedNumberDao
    public Object unblock(final String number, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.BlockedNumberDao_Impl.4
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement _stmt = BlockedNumberDao_Impl.this.__preparedStmtOfUnblock.acquire();
                _stmt.bindString(1, number);
                try {
                    BlockedNumberDao_Impl.this.__db.beginTransaction();
                    try {
                        _stmt.executeUpdateDelete();
                        BlockedNumberDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        BlockedNumberDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    BlockedNumberDao_Impl.this.__preparedStmtOfUnblock.release(_stmt);
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.BlockedNumberDao
    public Object block(final BlockedNumberEntity entity, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.meharenterprises.originsms.data.db.BlockedNumberDao_Impl.5
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                BlockedNumberDao_Impl.this.__db.beginTransaction();
                try {
                    BlockedNumberDao_Impl.this.__upsertionAdapterOfBlockedNumberEntity.upsert((EntityUpsertionAdapter) entity);
                    BlockedNumberDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    BlockedNumberDao_Impl.this.__db.endTransaction();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.BlockedNumberDao
    public Object isBlocked(final String number, final Continuation<? super Boolean> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT EXISTS(SELECT 1 FROM blocked_numbers WHERE normalizedNumber = ?)", 1);
        _statement.bindString(1, number);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<Boolean>() { // from class: com.meharenterprises.originsms.data.db.BlockedNumberDao_Impl.6
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Boolean call() throws Exception {
                Boolean _result;
                Cursor _cursor = DBUtil.query(BlockedNumberDao_Impl.this.__db, _statement, false, null);
                try {
                    if (_cursor.moveToFirst()) {
                        int _tmp = _cursor.getInt(0);
                        _result = Boolean.valueOf(_tmp != 0);
                    } else {
                        _result = false;
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    @Override // com.meharenterprises.originsms.data.db.BlockedNumberDao
    public Flow<List<BlockedNumberEntity>> observeAll() {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM blocked_numbers ORDER BY blockedAtMillis DESC", 0);
        return CoroutinesRoom.createFlow(this.__db, false, new String[]{"blocked_numbers"}, new Callable<List<BlockedNumberEntity>>() { // from class: com.meharenterprises.originsms.data.db.BlockedNumberDao_Impl.7
            @Override // java.util.concurrent.Callable
            public List<BlockedNumberEntity> call() throws Exception {
                Cursor _cursor = DBUtil.query(BlockedNumberDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfNormalizedNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "normalizedNumber");
                    int _cursorIndexOfDisplayNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "displayNumber");
                    int _cursorIndexOfBlockedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "blockedAtMillis");
                    List<BlockedNumberEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        String _tmpNormalizedNumber = _cursor.getString(_cursorIndexOfNormalizedNumber);
                        String _tmpDisplayNumber = _cursor.getString(_cursorIndexOfDisplayNumber);
                        long _tmpBlockedAtMillis = _cursor.getLong(_cursorIndexOfBlockedAtMillis);
                        BlockedNumberEntity _item = new BlockedNumberEntity(_tmpNormalizedNumber, _tmpDisplayNumber, _tmpBlockedAtMillis);
                        _result.add(_item);
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

    @Override // com.meharenterprises.originsms.data.db.BlockedNumberDao
    public Object getByNumber(final String number, final Continuation<? super BlockedNumberEntity> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM blocked_numbers WHERE normalizedNumber = ? LIMIT 1", 1);
        _statement.bindString(1, number);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<BlockedNumberEntity>() { // from class: com.meharenterprises.originsms.data.db.BlockedNumberDao_Impl.8
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public BlockedNumberEntity call() throws Exception {
                BlockedNumberEntity _result;
                Cursor _cursor = DBUtil.query(BlockedNumberDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfNormalizedNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "normalizedNumber");
                    int _cursorIndexOfDisplayNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "displayNumber");
                    int _cursorIndexOfBlockedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "blockedAtMillis");
                    if (_cursor.moveToFirst()) {
                        String _tmpNormalizedNumber = _cursor.getString(_cursorIndexOfNormalizedNumber);
                        String _tmpDisplayNumber = _cursor.getString(_cursorIndexOfDisplayNumber);
                        long _tmpBlockedAtMillis = _cursor.getLong(_cursorIndexOfBlockedAtMillis);
                        _result = new BlockedNumberEntity(_tmpNormalizedNumber, _tmpDisplayNumber, _tmpBlockedAtMillis);
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

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
