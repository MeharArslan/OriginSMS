package com.meharenterprises.originsms.data.db;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BlockedNumberEntity.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0017"}, d2 = {"Lcom/meharenterprises/originsms/data/db/BlockedNumberEntity;", "", "normalizedNumber", "", "displayNumber", "blockedAtMillis", "", "(Ljava/lang/String;Ljava/lang/String;J)V", "getBlockedAtMillis", "()J", "getDisplayNumber", "()Ljava/lang/String;", "getNormalizedNumber", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes9.dex */
public final /* data */ class BlockedNumberEntity {
    private final long blockedAtMillis;
    private final String displayNumber;
    private final String normalizedNumber;

    public static /* synthetic */ BlockedNumberEntity copy$default(BlockedNumberEntity blockedNumberEntity, String str, String str2, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            str = blockedNumberEntity.normalizedNumber;
        }
        if ((i & 2) != 0) {
            str2 = blockedNumberEntity.displayNumber;
        }
        if ((i & 4) != 0) {
            j = blockedNumberEntity.blockedAtMillis;
        }
        return blockedNumberEntity.copy(str, str2, j);
    }

    /* renamed from: component1, reason: from getter */
    public final String getNormalizedNumber() {
        return this.normalizedNumber;
    }

    /* renamed from: component2, reason: from getter */
    public final String getDisplayNumber() {
        return this.displayNumber;
    }

    /* renamed from: component3, reason: from getter */
    public final long getBlockedAtMillis() {
        return this.blockedAtMillis;
    }

    public final BlockedNumberEntity copy(String normalizedNumber, String displayNumber, long blockedAtMillis) {
        Intrinsics.checkNotNullParameter(normalizedNumber, "normalizedNumber");
        Intrinsics.checkNotNullParameter(displayNumber, "displayNumber");
        return new BlockedNumberEntity(normalizedNumber, displayNumber, blockedAtMillis);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BlockedNumberEntity)) {
            return false;
        }
        BlockedNumberEntity blockedNumberEntity = (BlockedNumberEntity) other;
        return Intrinsics.areEqual(this.normalizedNumber, blockedNumberEntity.normalizedNumber) && Intrinsics.areEqual(this.displayNumber, blockedNumberEntity.displayNumber) && this.blockedAtMillis == blockedNumberEntity.blockedAtMillis;
    }

    public int hashCode() {
        return (((this.normalizedNumber.hashCode() * 31) + this.displayNumber.hashCode()) * 31) + Long.hashCode(this.blockedAtMillis);
    }

    public String toString() {
        return "BlockedNumberEntity(normalizedNumber=" + this.normalizedNumber + ", displayNumber=" + this.displayNumber + ", blockedAtMillis=" + this.blockedAtMillis + ")";
    }

    public BlockedNumberEntity(String normalizedNumber, String displayNumber, long blockedAtMillis) {
        Intrinsics.checkNotNullParameter(normalizedNumber, "normalizedNumber");
        Intrinsics.checkNotNullParameter(displayNumber, "displayNumber");
        this.normalizedNumber = normalizedNumber;
        this.displayNumber = displayNumber;
        this.blockedAtMillis = blockedAtMillis;
    }

    public final String getNormalizedNumber() {
        return this.normalizedNumber;
    }

    public final String getDisplayNumber() {
        return this.displayNumber;
    }

    public final long getBlockedAtMillis() {
        return this.blockedAtMillis;
    }
}
