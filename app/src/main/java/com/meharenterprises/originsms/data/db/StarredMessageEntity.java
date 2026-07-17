package com.meharenterprises.originsms.data.db;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StarredMessageEntity.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003JE\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\t\u0010\u001f\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000f¨\u0006 "}, d2 = {"Lcom/meharenterprises/originsms/data/db/StarredMessageEntity;", "", "messageId", "", "threadId", "address", "", "body", "dateMillis", "starredAtMillis", "(JJLjava/lang/String;Ljava/lang/String;JJ)V", "getAddress", "()Ljava/lang/String;", "getBody", "getDateMillis", "()J", "getMessageId", "getStarredAtMillis", "getThreadId", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes9.dex */
public final /* data */ class StarredMessageEntity {
    private final String address;
    private final String body;
    private final long dateMillis;
    private final long messageId;
    private final long starredAtMillis;
    private final long threadId;

    /* renamed from: component1, reason: from getter */
    public final long getMessageId() {
        return this.messageId;
    }

    /* renamed from: component2, reason: from getter */
    public final long getThreadId() {
        return this.threadId;
    }

    /* renamed from: component3, reason: from getter */
    public final String getAddress() {
        return this.address;
    }

    /* renamed from: component4, reason: from getter */
    public final String getBody() {
        return this.body;
    }

    /* renamed from: component5, reason: from getter */
    public final long getDateMillis() {
        return this.dateMillis;
    }

    /* renamed from: component6, reason: from getter */
    public final long getStarredAtMillis() {
        return this.starredAtMillis;
    }

    public final StarredMessageEntity copy(long messageId, long threadId, String address, String body, long dateMillis, long starredAtMillis) {
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(body, "body");
        return new StarredMessageEntity(messageId, threadId, address, body, dateMillis, starredAtMillis);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StarredMessageEntity)) {
            return false;
        }
        StarredMessageEntity starredMessageEntity = (StarredMessageEntity) other;
        return this.messageId == starredMessageEntity.messageId && this.threadId == starredMessageEntity.threadId && Intrinsics.areEqual(this.address, starredMessageEntity.address) && Intrinsics.areEqual(this.body, starredMessageEntity.body) && this.dateMillis == starredMessageEntity.dateMillis && this.starredAtMillis == starredMessageEntity.starredAtMillis;
    }

    public int hashCode() {
        return (((((((((Long.hashCode(this.messageId) * 31) + Long.hashCode(this.threadId)) * 31) + this.address.hashCode()) * 31) + this.body.hashCode()) * 31) + Long.hashCode(this.dateMillis)) * 31) + Long.hashCode(this.starredAtMillis);
    }

    public String toString() {
        return "StarredMessageEntity(messageId=" + this.messageId + ", threadId=" + this.threadId + ", address=" + this.address + ", body=" + this.body + ", dateMillis=" + this.dateMillis + ", starredAtMillis=" + this.starredAtMillis + ")";
    }

    public StarredMessageEntity(long messageId, long threadId, String address, String body, long dateMillis, long starredAtMillis) {
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(body, "body");
        this.messageId = messageId;
        this.threadId = threadId;
        this.address = address;
        this.body = body;
        this.dateMillis = dateMillis;
        this.starredAtMillis = starredAtMillis;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ StarredMessageEntity(long r14, long r16, java.lang.String r18, java.lang.String r19, long r20, long r22, int r24, kotlin.jvm.internal.DefaultConstructorMarker r25) {
        /*
            r13 = this;
            r0 = r24 & 32
            if (r0 == 0) goto La
            long r0 = java.lang.System.currentTimeMillis()
            r11 = r0
            goto Lc
        La:
            r11 = r22
        Lc:
            r2 = r13
            r3 = r14
            r5 = r16
            r7 = r18
            r8 = r19
            r9 = r20
            r2.<init>(r3, r5, r7, r8, r9, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.data.db.StarredMessageEntity.<init>(long, long, java.lang.String, java.lang.String, long, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final long getMessageId() {
        return this.messageId;
    }

    public final long getThreadId() {
        return this.threadId;
    }

    public final String getAddress() {
        return this.address;
    }

    public final String getBody() {
        return this.body;
    }

    public final long getDateMillis() {
        return this.dateMillis;
    }

    public final long getStarredAtMillis() {
        return this.starredAtMillis;
    }
}
