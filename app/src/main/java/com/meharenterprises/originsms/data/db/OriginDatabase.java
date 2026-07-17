package com.meharenterprises.originsms.data.db;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OriginDatabase.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&¨\u0006\f"}, d2 = {"Lcom/meharenterprises/originsms/data/db/OriginDatabase;", "Landroidx/room/RoomDatabase;", "()V", "blockedNumberDao", "Lcom/meharenterprises/originsms/data/db/BlockedNumberDao;", "draftDao", "Lcom/meharenterprises/originsms/data/db/DraftDao;", "starredMessageDao", "Lcom/meharenterprises/originsms/data/db/StarredMessageDao;", "threadLockDao", "Lcom/meharenterprises/originsms/data/db/ThreadLockDao;", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes9.dex */
public abstract class OriginDatabase extends RoomDatabase {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static volatile OriginDatabase instance;

    public abstract BlockedNumberDao blockedNumberDao();

    public abstract DraftDao draftDao();

    public abstract StarredMessageDao starredMessageDao();

    public abstract ThreadLockDao threadLockDao();

    /* compiled from: OriginDatabase.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/meharenterprises/originsms/data/db/OriginDatabase$Companion;", "", "()V", "instance", "Lcom/meharenterprises/originsms/data/db/OriginDatabase;", "getInstance", "context", "Landroid/content/Context;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes9.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final OriginDatabase getInstance(Context context) {
            OriginDatabase originDatabase;
            Intrinsics.checkNotNullParameter(context, "context");
            OriginDatabase originDatabase2 = OriginDatabase.instance;
            if (originDatabase2 == null) {
                synchronized (this) {
                    originDatabase = OriginDatabase.instance;
                    if (originDatabase == null) {
                        Context applicationContext = context.getApplicationContext();
                        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                        RoomDatabase build = Room.databaseBuilder(applicationContext, OriginDatabase.class, "origin_sms.db").fallbackToDestructiveMigration().build();
                        OriginDatabase it = (OriginDatabase) build;
                        Companion companion = OriginDatabase.INSTANCE;
                        OriginDatabase.instance = it;
                        originDatabase = (OriginDatabase) build;
                    }
                }
                return originDatabase;
            }
            return originDatabase2;
        }
    }
}
