package com.meharenterprises.originsms.data.db;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DraftEntity.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0017"}, d2 = {"Lcom/meharenterprises/originsms/data/db/DraftEntity;", "", "threadId", "", "text", "", "updatedAtMillis", "(JLjava/lang/String;J)V", "getText", "()Ljava/lang/String;", "getThreadId", "()J", "getUpdatedAtMillis", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes9.dex */
public final /* data */ class DraftEntity {
    private final String text;
    private final long threadId;
    private final long updatedAtMillis;

    public static /* synthetic */ DraftEntity copy$default(DraftEntity draftEntity, long j, String str, long j2, int i, Object obj) {
        if ((i & 1) != 0) {
            j = draftEntity.threadId;
        }
        long j3 = j;
        if ((i & 2) != 0) {
            str = draftEntity.text;
        }
        String str2 = str;
        if ((i & 4) != 0) {
            j2 = draftEntity.updatedAtMillis;
        }
        return draftEntity.copy(j3, str2, j2);
    }

    /* renamed from: component1, reason: from getter */
    public final long getThreadId() {
        return this.threadId;
    }

    /* renamed from: component2, reason: from getter */
    public final String getText() {
        return this.text;
    }

    /* renamed from: component3, reason: from getter */
    public final long getUpdatedAtMillis() {
        return this.updatedAtMillis;
    }

    public final DraftEntity copy(long threadId, String text, long updatedAtMillis) {
        Intrinsics.checkNotNullParameter(text, "text");
        return new DraftEntity(threadId, text, updatedAtMillis);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DraftEntity)) {
            return false;
        }
        DraftEntity draftEntity = (DraftEntity) other;
        return this.threadId == draftEntity.threadId && Intrinsics.areEqual(this.text, draftEntity.text) && this.updatedAtMillis == draftEntity.updatedAtMillis;
    }

    public int hashCode() {
        return (((Long.hashCode(this.threadId) * 31) + this.text.hashCode()) * 31) + Long.hashCode(this.updatedAtMillis);
    }

    public String toString() {
        return "DraftEntity(threadId=" + this.threadId + ", text=" + this.text + ", updatedAtMillis=" + this.updatedAtMillis + ")";
    }

    public DraftEntity(long threadId, String text, long updatedAtMillis) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.threadId = threadId;
        this.text = text;
        this.updatedAtMillis = updatedAtMillis;
    }

    public final long getThreadId() {
        return this.threadId;
    }

    public final String getText() {
        return this.text;
    }

    public final long getUpdatedAtMillis() {
        return this.updatedAtMillis;
    }
}
