package com.meharenterprises.originsms.data.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes9.dex */
public final class OriginDatabase_Impl extends OriginDatabase {
    private volatile BlockedNumberDao _blockedNumberDao;
    private volatile DraftDao _draftDao;
    private volatile StarredMessageDao _starredMessageDao;
    private volatile ThreadLockDao _threadLockDao;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(final DatabaseConfiguration config) {
        SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(7) { // from class: com.meharenterprises.originsms.data.db.OriginDatabase_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(final SupportSQLiteDatabase db) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `starred_messages` (`messageId` INTEGER NOT NULL, `threadId` INTEGER NOT NULL, `address` TEXT NOT NULL, `body` TEXT NOT NULL, `dateMillis` INTEGER NOT NULL, `starredAtMillis` INTEGER NOT NULL, PRIMARY KEY(`messageId`))");
                db.execSQL("CREATE TABLE IF NOT EXISTS `thread_lock_state` (`threadId` INTEGER NOT NULL, `isLocked` INTEGER NOT NULL, `isHidden` INTEGER NOT NULL, `lockedAtMillis` INTEGER NOT NULL, `isMuted` INTEGER NOT NULL, `isArchived` INTEGER NOT NULL, `muteUntilMillis` INTEGER NOT NULL, `autoUnhideAtMillis` INTEGER NOT NULL, `dailyHideTimeMinutes` INTEGER NOT NULL, `deletedAtMillis` INTEGER NOT NULL, `newChatStartMillis` INTEGER NOT NULL, PRIMARY KEY(`threadId`))");
                db.execSQL("CREATE TABLE IF NOT EXISTS `blocked_numbers` (`normalizedNumber` TEXT NOT NULL, `displayNumber` TEXT NOT NULL, `blockedAtMillis` INTEGER NOT NULL, PRIMARY KEY(`normalizedNumber`))");
                db.execSQL("CREATE TABLE IF NOT EXISTS `drafts` (`threadId` INTEGER NOT NULL, `text` TEXT NOT NULL, `updatedAtMillis` INTEGER NOT NULL, PRIMARY KEY(`threadId`))");
                db.execSQL(RoomMasterTable.CREATE_QUERY);
                db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b21412a8ff03ed1d4f50b8f3553b60fa')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(final SupportSQLiteDatabase db) {
                db.execSQL("DROP TABLE IF EXISTS `starred_messages`");
                db.execSQL("DROP TABLE IF EXISTS `thread_lock_state`");
                db.execSQL("DROP TABLE IF EXISTS `blocked_numbers`");
                db.execSQL("DROP TABLE IF EXISTS `drafts`");
                List<? extends RoomDatabase.Callback> _callbacks = OriginDatabase_Impl.this.mCallbacks;
                if (_callbacks != null) {
                    for (RoomDatabase.Callback _callback : _callbacks) {
                        _callback.onDestructiveMigration(db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onCreate(final SupportSQLiteDatabase db) {
                List<? extends RoomDatabase.Callback> _callbacks = OriginDatabase_Impl.this.mCallbacks;
                if (_callbacks != null) {
                    for (RoomDatabase.Callback _callback : _callbacks) {
                        _callback.onCreate(db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(final SupportSQLiteDatabase db) {
                OriginDatabase_Impl.this.mDatabase = db;
                OriginDatabase_Impl.this.internalInitInvalidationTracker(db);
                List<? extends RoomDatabase.Callback> _callbacks = OriginDatabase_Impl.this.mCallbacks;
                if (_callbacks != null) {
                    for (RoomDatabase.Callback _callback : _callbacks) {
                        _callback.onOpen(db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPreMigrate(final SupportSQLiteDatabase db) {
                DBUtil.dropFtsSyncTriggers(db);
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(final SupportSQLiteDatabase db) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public RoomOpenHelper.ValidationResult onValidateSchema(final SupportSQLiteDatabase db) {
                HashMap<String, TableInfo.Column> _columnsStarredMessages = new HashMap<>(6);
                _columnsStarredMessages.put("messageId", new TableInfo.Column("messageId", "INTEGER", true, 1, null, 1));
                _columnsStarredMessages.put("threadId", new TableInfo.Column("threadId", "INTEGER", true, 0, null, 1));
                _columnsStarredMessages.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, 1));
                _columnsStarredMessages.put("body", new TableInfo.Column("body", "TEXT", true, 0, null, 1));
                _columnsStarredMessages.put("dateMillis", new TableInfo.Column("dateMillis", "INTEGER", true, 0, null, 1));
                _columnsStarredMessages.put("starredAtMillis", new TableInfo.Column("starredAtMillis", "INTEGER", true, 0, null, 1));
                HashSet<TableInfo.ForeignKey> _foreignKeysStarredMessages = new HashSet<>(0);
                HashSet<TableInfo.Index> _indicesStarredMessages = new HashSet<>(0);
                TableInfo _infoStarredMessages = new TableInfo("starred_messages", _columnsStarredMessages, _foreignKeysStarredMessages, _indicesStarredMessages);
                TableInfo _existingStarredMessages = TableInfo.read(db, "starred_messages");
                if (!_infoStarredMessages.equals(_existingStarredMessages)) {
                    return new RoomOpenHelper.ValidationResult(false, "starred_messages(com.meharenterprises.originsms.data.db.StarredMessageEntity).\n Expected:\n" + _infoStarredMessages + "\n Found:\n" + _existingStarredMessages);
                }
                HashMap<String, TableInfo.Column> _columnsThreadLockState = new HashMap<>(11);
                _columnsThreadLockState.put("threadId", new TableInfo.Column("threadId", "INTEGER", true, 1, null, 1));
                _columnsThreadLockState.put("isLocked", new TableInfo.Column("isLocked", "INTEGER", true, 0, null, 1));
                _columnsThreadLockState.put("isHidden", new TableInfo.Column("isHidden", "INTEGER", true, 0, null, 1));
                _columnsThreadLockState.put("lockedAtMillis", new TableInfo.Column("lockedAtMillis", "INTEGER", true, 0, null, 1));
                _columnsThreadLockState.put("isMuted", new TableInfo.Column("isMuted", "INTEGER", true, 0, null, 1));
                _columnsThreadLockState.put("isArchived", new TableInfo.Column("isArchived", "INTEGER", true, 0, null, 1));
                _columnsThreadLockState.put("muteUntilMillis", new TableInfo.Column("muteUntilMillis", "INTEGER", true, 0, null, 1));
                _columnsThreadLockState.put("autoUnhideAtMillis", new TableInfo.Column("autoUnhideAtMillis", "INTEGER", true, 0, null, 1));
                _columnsThreadLockState.put("dailyHideTimeMinutes", new TableInfo.Column("dailyHideTimeMinutes", "INTEGER", true, 0, null, 1));
                _columnsThreadLockState.put("deletedAtMillis", new TableInfo.Column("deletedAtMillis", "INTEGER", true, 0, null, 1));
                _columnsThreadLockState.put("newChatStartMillis", new TableInfo.Column("newChatStartMillis", "INTEGER", true, 0, null, 1));
                HashSet<TableInfo.ForeignKey> _foreignKeysThreadLockState = new HashSet<>(0);
                HashSet<TableInfo.Index> _indicesThreadLockState = new HashSet<>(0);
                TableInfo _infoThreadLockState = new TableInfo("thread_lock_state", _columnsThreadLockState, _foreignKeysThreadLockState, _indicesThreadLockState);
                TableInfo _existingThreadLockState = TableInfo.read(db, "thread_lock_state");
                if (!_infoThreadLockState.equals(_existingThreadLockState)) {
                    return new RoomOpenHelper.ValidationResult(false, "thread_lock_state(com.meharenterprises.originsms.data.db.ThreadLockEntity).\n Expected:\n" + _infoThreadLockState + "\n Found:\n" + _existingThreadLockState);
                }
                HashMap<String, TableInfo.Column> _columnsBlockedNumbers = new HashMap<>(3);
                _columnsBlockedNumbers.put("normalizedNumber", new TableInfo.Column("normalizedNumber", "TEXT", true, 1, null, 1));
                _columnsBlockedNumbers.put("displayNumber", new TableInfo.Column("displayNumber", "TEXT", true, 0, null, 1));
                _columnsBlockedNumbers.put("blockedAtMillis", new TableInfo.Column("blockedAtMillis", "INTEGER", true, 0, null, 1));
                HashSet<TableInfo.ForeignKey> _foreignKeysBlockedNumbers = new HashSet<>(0);
                HashSet<TableInfo.Index> _indicesBlockedNumbers = new HashSet<>(0);
                TableInfo _infoBlockedNumbers = new TableInfo("blocked_numbers", _columnsBlockedNumbers, _foreignKeysBlockedNumbers, _indicesBlockedNumbers);
                TableInfo _existingBlockedNumbers = TableInfo.read(db, "blocked_numbers");
                if (!_infoBlockedNumbers.equals(_existingBlockedNumbers)) {
                    return new RoomOpenHelper.ValidationResult(false, "blocked_numbers(com.meharenterprises.originsms.data.db.BlockedNumberEntity).\n Expected:\n" + _infoBlockedNumbers + "\n Found:\n" + _existingBlockedNumbers);
                }
                HashMap<String, TableInfo.Column> _columnsDrafts = new HashMap<>(3);
                _columnsDrafts.put("threadId", new TableInfo.Column("threadId", "INTEGER", true, 1, null, 1));
                _columnsDrafts.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, 1));
                _columnsDrafts.put("updatedAtMillis", new TableInfo.Column("updatedAtMillis", "INTEGER", true, 0, null, 1));
                HashSet<TableInfo.ForeignKey> _foreignKeysDrafts = new HashSet<>(0);
                HashSet<TableInfo.Index> _indicesDrafts = new HashSet<>(0);
                TableInfo _infoDrafts = new TableInfo("drafts", _columnsDrafts, _foreignKeysDrafts, _indicesDrafts);
                TableInfo _existingDrafts = TableInfo.read(db, "drafts");
                return !_infoDrafts.equals(_existingDrafts) ? new RoomOpenHelper.ValidationResult(false, "drafts(com.meharenterprises.originsms.data.db.DraftEntity).\n Expected:\n" + _infoDrafts + "\n Found:\n" + _existingDrafts) : new RoomOpenHelper.ValidationResult(true, null);
            }
        }, "b21412a8ff03ed1d4f50b8f3553b60fa", "3b8ff2dcb3169e1bb2ad60f8e75e8185");
        SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
        SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
        return _helper;
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        HashMap<String, String> _shadowTablesMap = new HashMap<>(0);
        HashMap<String, Set<String>> _viewTables = new HashMap<>(0);
        return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "starred_messages", "thread_lock_state", "blocked_numbers", "drafts");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            _db.execSQL("DELETE FROM `starred_messages`");
            _db.execSQL("DELETE FROM `thread_lock_state`");
            _db.execSQL("DELETE FROM `blocked_numbers`");
            _db.execSQL("DELETE FROM `drafts`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            _db.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!_db.inTransaction()) {
                _db.execSQL("VACUUM");
            }
        }
    }

    @Override // androidx.room.RoomDatabase
    protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
        HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<>();
        _typeConvertersMap.put(ThreadLockDao.class, ThreadLockDao_Impl.getRequiredConverters());
        _typeConvertersMap.put(BlockedNumberDao.class, BlockedNumberDao_Impl.getRequiredConverters());
        _typeConvertersMap.put(DraftDao.class, DraftDao_Impl.getRequiredConverters());
        _typeConvertersMap.put(StarredMessageDao.class, StarredMessageDao_Impl.getRequiredConverters());
        return _typeConvertersMap;
    }

    @Override // androidx.room.RoomDatabase
    public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
        HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<>();
        return _autoMigrationSpecsSet;
    }

    @Override // androidx.room.RoomDatabase
    public List<Migration> getAutoMigrations(final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
        List<Migration> _autoMigrations = new ArrayList<>();
        return _autoMigrations;
    }

    @Override // com.meharenterprises.originsms.data.db.OriginDatabase
    public ThreadLockDao threadLockDao() {
        ThreadLockDao threadLockDao;
        if (this._threadLockDao != null) {
            return this._threadLockDao;
        }
        synchronized (this) {
            if (this._threadLockDao == null) {
                this._threadLockDao = new ThreadLockDao_Impl(this);
            }
            threadLockDao = this._threadLockDao;
        }
        return threadLockDao;
    }

    @Override // com.meharenterprises.originsms.data.db.OriginDatabase
    public BlockedNumberDao blockedNumberDao() {
        BlockedNumberDao blockedNumberDao;
        if (this._blockedNumberDao != null) {
            return this._blockedNumberDao;
        }
        synchronized (this) {
            if (this._blockedNumberDao == null) {
                this._blockedNumberDao = new BlockedNumberDao_Impl(this);
            }
            blockedNumberDao = this._blockedNumberDao;
        }
        return blockedNumberDao;
    }

    @Override // com.meharenterprises.originsms.data.db.OriginDatabase
    public DraftDao draftDao() {
        DraftDao draftDao;
        if (this._draftDao != null) {
            return this._draftDao;
        }
        synchronized (this) {
            if (this._draftDao == null) {
                this._draftDao = new DraftDao_Impl(this);
            }
            draftDao = this._draftDao;
        }
        return draftDao;
    }

    @Override // com.meharenterprises.originsms.data.db.OriginDatabase
    public StarredMessageDao starredMessageDao() {
        StarredMessageDao starredMessageDao;
        if (this._starredMessageDao != null) {
            return this._starredMessageDao;
        }
        synchronized (this) {
            if (this._starredMessageDao == null) {
                this._starredMessageDao = new StarredMessageDao_Impl(this);
            }
            starredMessageDao = this._starredMessageDao;
        }
        return starredMessageDao;
    }
}
