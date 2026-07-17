package com.meharenterprises.originsms.data.db;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: ThreadLockEntity.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u001d\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001Bq\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003¢\u0006\u0002\u0010\u0010J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0005HÆ\u0003J\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0005HÆ\u0003J\t\u0010\"\u001a\u00020\u0005HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0003HÆ\u0003J\t\u0010%\u001a\u00020\rHÆ\u0003Jw\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u0003HÆ\u0001J\u0013\u0010'\u001a\u00020\u00052\b\u0010(\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010)\u001a\u00020\rHÖ\u0001J\t\u0010*\u001a\u00020+HÖ\u0001R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u000e\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0016R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012¨\u0006,"}, d2 = {"Lcom/meharenterprises/originsms/data/db/ThreadLockEntity;", "", "threadId", "", "isLocked", "", "isHidden", "lockedAtMillis", "isMuted", "isArchived", "muteUntilMillis", "autoUnhideAtMillis", "dailyHideTimeMinutes", "", "deletedAtMillis", "newChatStartMillis", "(JZZJZZJJIJJ)V", "getAutoUnhideAtMillis", "()J", "getDailyHideTimeMinutes", "()I", "getDeletedAtMillis", "()Z", "getLockedAtMillis", "getMuteUntilMillis", "getNewChatStartMillis", "getThreadId", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes9.dex */
public final /* data */ class ThreadLockEntity {
    private final long autoUnhideAtMillis;
    private final int dailyHideTimeMinutes;
    private final long deletedAtMillis;
    private final boolean isArchived;
    private final boolean isHidden;
    private final boolean isLocked;
    private final boolean isMuted;
    private final long lockedAtMillis;
    private final long muteUntilMillis;
    private final long newChatStartMillis;
    private final long threadId;

    /* renamed from: component1, reason: from getter */
    public final long getThreadId() {
        return this.threadId;
    }

    /* renamed from: component10, reason: from getter */
    public final long getDeletedAtMillis() {
        return this.deletedAtMillis;
    }

    /* renamed from: component11, reason: from getter */
    public final long getNewChatStartMillis() {
        return this.newChatStartMillis;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getIsLocked() {
        return this.isLocked;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getIsHidden() {
        return this.isHidden;
    }

    /* renamed from: component4, reason: from getter */
    public final long getLockedAtMillis() {
        return this.lockedAtMillis;
    }

    /* renamed from: component5, reason: from getter */
    public final boolean getIsMuted() {
        return this.isMuted;
    }

    /* renamed from: component6, reason: from getter */
    public final boolean getIsArchived() {
        return this.isArchived;
    }

    /* renamed from: component7, reason: from getter */
    public final long getMuteUntilMillis() {
        return this.muteUntilMillis;
    }

    /* renamed from: component8, reason: from getter */
    public final long getAutoUnhideAtMillis() {
        return this.autoUnhideAtMillis;
    }

    /* renamed from: component9, reason: from getter */
    public final int getDailyHideTimeMinutes() {
        return this.dailyHideTimeMinutes;
    }

    public final ThreadLockEntity copy(long threadId, boolean isLocked, boolean isHidden, long lockedAtMillis, boolean isMuted, boolean isArchived, long muteUntilMillis, long autoUnhideAtMillis, int dailyHideTimeMinutes, long deletedAtMillis, long newChatStartMillis) {
        return new ThreadLockEntity(threadId, isLocked, isHidden, lockedAtMillis, isMuted, isArchived, muteUntilMillis, autoUnhideAtMillis, dailyHideTimeMinutes, deletedAtMillis, newChatStartMillis);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ThreadLockEntity)) {
            return false;
        }
        ThreadLockEntity threadLockEntity = (ThreadLockEntity) other;
        return this.threadId == threadLockEntity.threadId && this.isLocked == threadLockEntity.isLocked && this.isHidden == threadLockEntity.isHidden && this.lockedAtMillis == threadLockEntity.lockedAtMillis && this.isMuted == threadLockEntity.isMuted && this.isArchived == threadLockEntity.isArchived && this.muteUntilMillis == threadLockEntity.muteUntilMillis && this.autoUnhideAtMillis == threadLockEntity.autoUnhideAtMillis && this.dailyHideTimeMinutes == threadLockEntity.dailyHideTimeMinutes && this.deletedAtMillis == threadLockEntity.deletedAtMillis && this.newChatStartMillis == threadLockEntity.newChatStartMillis;
    }

    public int hashCode() {
        return (((((((((((((((((((Long.hashCode(this.threadId) * 31) + Boolean.hashCode(this.isLocked)) * 31) + Boolean.hashCode(this.isHidden)) * 31) + Long.hashCode(this.lockedAtMillis)) * 31) + Boolean.hashCode(this.isMuted)) * 31) + Boolean.hashCode(this.isArchived)) * 31) + Long.hashCode(this.muteUntilMillis)) * 31) + Long.hashCode(this.autoUnhideAtMillis)) * 31) + Integer.hashCode(this.dailyHideTimeMinutes)) * 31) + Long.hashCode(this.deletedAtMillis)) * 31) + Long.hashCode(this.newChatStartMillis);
    }

    public String toString() {
        return "ThreadLockEntity(threadId=" + this.threadId + ", isLocked=" + this.isLocked + ", isHidden=" + this.isHidden + ", lockedAtMillis=" + this.lockedAtMillis + ", isMuted=" + this.isMuted + ", isArchived=" + this.isArchived + ", muteUntilMillis=" + this.muteUntilMillis + ", autoUnhideAtMillis=" + this.autoUnhideAtMillis + ", dailyHideTimeMinutes=" + this.dailyHideTimeMinutes + ", deletedAtMillis=" + this.deletedAtMillis + ", newChatStartMillis=" + this.newChatStartMillis + ")";
    }

    public ThreadLockEntity(long threadId, boolean isLocked, boolean isHidden, long lockedAtMillis, boolean isMuted, boolean isArchived, long muteUntilMillis, long autoUnhideAtMillis, int dailyHideTimeMinutes, long deletedAtMillis, long newChatStartMillis) {
        this.threadId = threadId;
        this.isLocked = isLocked;
        this.isHidden = isHidden;
        this.lockedAtMillis = lockedAtMillis;
        this.isMuted = isMuted;
        this.isArchived = isArchived;
        this.muteUntilMillis = muteUntilMillis;
        this.autoUnhideAtMillis = autoUnhideAtMillis;
        this.dailyHideTimeMinutes = dailyHideTimeMinutes;
        this.deletedAtMillis = deletedAtMillis;
        this.newChatStartMillis = newChatStartMillis;
    }

    public /* synthetic */ ThreadLockEntity(long j, boolean z, boolean z2, long j2, boolean z3, boolean z4, long j3, long j4, int i, long j5, long j6, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, (i2 & 2) != 0 ? false : z, (i2 & 4) != 0 ? false : z2, (i2 & 8) != 0 ? 0L : j2, (i2 & 16) != 0 ? false : z3, (i2 & 32) != 0 ? false : z4, (i2 & 64) != 0 ? 0L : j3, (i2 & 128) != 0 ? 0L : j4, (i2 & 256) != 0 ? -1 : i, (i2 & 512) != 0 ? 0L : j5, (i2 & 1024) != 0 ? 0L : j6);
    }

    public final long getThreadId() {
        return this.threadId;
    }

    public final boolean isLocked() {
        return this.isLocked;
    }

    public final boolean isHidden() {
        return this.isHidden;
    }

    public final long getLockedAtMillis() {
        return this.lockedAtMillis;
    }

    public final boolean isMuted() {
        return this.isMuted;
    }

    public final boolean isArchived() {
        return this.isArchived;
    }

    public final long getMuteUntilMillis() {
        return this.muteUntilMillis;
    }

    public final long getAutoUnhideAtMillis() {
        return this.autoUnhideAtMillis;
    }

    public final int getDailyHideTimeMinutes() {
        return this.dailyHideTimeMinutes;
    }

    public final long getDeletedAtMillis() {
        return this.deletedAtMillis;
    }

    public final long getNewChatStartMillis() {
        return this.newChatStartMillis;
    }
}
