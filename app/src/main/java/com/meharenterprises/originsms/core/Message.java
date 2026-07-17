package com.meharenterprises.originsms.core;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Message.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001Bg\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u000f¢\u0006\u0002\u0010\u0014J\t\u0010$\u001a\u00020\u0003HÆ\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011HÆ\u0003J\t\u0010&\u001a\u00020\u000fHÆ\u0003J\t\u0010'\u001a\u00020\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0006HÆ\u0003J\t\u0010)\u001a\u00020\u0006HÆ\u0003J\t\u0010*\u001a\u00020\u0003HÆ\u0003J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\t\u0010,\u001a\u00020\u000bHÆ\u0003J\t\u0010-\u001a\u00020\rHÆ\u0003J\t\u0010.\u001a\u00020\u000fHÆ\u0003J}\u0010/\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\b\b\u0002\u0010\u0013\u001a\u00020\u000fHÆ\u0001J\u0013\u00100\u001a\u00020\u000f2\b\u00101\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00102\u001a\u000203HÖ\u0001J\u0006\u00104\u001a\u00020\u0003J\t\u00105\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001dR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010 R\u0011\u0010\u0013\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010 R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#¨\u00066"}, d2 = {"Lcom/meharenterprises/originsms/core/Message;", "", "id", "", "threadId", "address", "", "body", "dateMillis", "dateSentMillis", "type", "Lcom/meharenterprises/originsms/core/MessageType;", "box", "Lcom/meharenterprises/originsms/core/MessageBox;", "isRead", "", "attachments", "", "Lcom/meharenterprises/originsms/core/Attachment;", "isStarred", "(JJLjava/lang/String;Ljava/lang/String;JJLcom/meharenterprises/originsms/core/MessageType;Lcom/meharenterprises/originsms/core/MessageBox;ZLjava/util/List;Z)V", "getAddress", "()Ljava/lang/String;", "getAttachments", "()Ljava/util/List;", "getBody", "getBox", "()Lcom/meharenterprises/originsms/core/MessageBox;", "getDateMillis", "()J", "getDateSentMillis", "getId", "()Z", "getThreadId", "getType", "()Lcom/meharenterprises/originsms/core/MessageType;", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "scheduledTimeOrDate", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes8.dex */
public final /* data */ class Message {
    private final String address;
    private final List<Attachment> attachments;
    private final String body;
    private final MessageBox box;
    private final long dateMillis;
    private final long dateSentMillis;
    private final long id;
    private final boolean isRead;
    private final boolean isStarred;
    private final long threadId;
    private final MessageType type;

    /* renamed from: component1, reason: from getter */
    public final long getId() {
        return this.id;
    }

    public final List<Attachment> component10() {
        return this.attachments;
    }

    /* renamed from: component11, reason: from getter */
    public final boolean getIsStarred() {
        return this.isStarred;
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
    public final long getDateSentMillis() {
        return this.dateSentMillis;
    }

    /* renamed from: component7, reason: from getter */
    public final MessageType getType() {
        return this.type;
    }

    /* renamed from: component8, reason: from getter */
    public final MessageBox getBox() {
        return this.box;
    }

    /* renamed from: component9, reason: from getter */
    public final boolean getIsRead() {
        return this.isRead;
    }

    public final Message copy(long id, long threadId, String address, String body, long dateMillis, long dateSentMillis, MessageType type, MessageBox box, boolean isRead, List<Attachment> attachments, boolean isStarred) {
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(box, "box");
        Intrinsics.checkNotNullParameter(attachments, "attachments");
        return new Message(id, threadId, address, body, dateMillis, dateSentMillis, type, box, isRead, attachments, isStarred);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Message)) {
            return false;
        }
        Message message = (Message) other;
        return this.id == message.id && this.threadId == message.threadId && Intrinsics.areEqual(this.address, message.address) && Intrinsics.areEqual(this.body, message.body) && this.dateMillis == message.dateMillis && this.dateSentMillis == message.dateSentMillis && this.type == message.type && this.box == message.box && this.isRead == message.isRead && Intrinsics.areEqual(this.attachments, message.attachments) && this.isStarred == message.isStarred;
    }

    public int hashCode() {
        return (((((((((((((((((((Long.hashCode(this.id) * 31) + Long.hashCode(this.threadId)) * 31) + this.address.hashCode()) * 31) + this.body.hashCode()) * 31) + Long.hashCode(this.dateMillis)) * 31) + Long.hashCode(this.dateSentMillis)) * 31) + this.type.hashCode()) * 31) + this.box.hashCode()) * 31) + Boolean.hashCode(this.isRead)) * 31) + this.attachments.hashCode()) * 31) + Boolean.hashCode(this.isStarred);
    }

    public String toString() {
        return "Message(id=" + this.id + ", threadId=" + this.threadId + ", address=" + this.address + ", body=" + this.body + ", dateMillis=" + this.dateMillis + ", dateSentMillis=" + this.dateSentMillis + ", type=" + this.type + ", box=" + this.box + ", isRead=" + this.isRead + ", attachments=" + this.attachments + ", isStarred=" + this.isStarred + ")";
    }

    public Message(long id, long threadId, String address, String body, long dateMillis, long dateSentMillis, MessageType type, MessageBox box, boolean isRead, List<Attachment> attachments, boolean isStarred) {
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(box, "box");
        Intrinsics.checkNotNullParameter(attachments, "attachments");
        this.id = id;
        this.threadId = threadId;
        this.address = address;
        this.body = body;
        this.dateMillis = dateMillis;
        this.dateSentMillis = dateSentMillis;
        this.type = type;
        this.box = box;
        this.isRead = isRead;
        this.attachments = attachments;
        this.isStarred = isStarred;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ Message(long r19, long r21, java.lang.String r23, java.lang.String r24, long r25, long r27, com.meharenterprises.originsms.core.MessageType r29, com.meharenterprises.originsms.core.MessageBox r30, boolean r31, java.util.List r32, boolean r33, int r34, kotlin.jvm.internal.DefaultConstructorMarker r35) {
        /*
            r18 = this;
            r0 = r34
            r1 = r0 & 512(0x200, float:7.17E-43)
            if (r1 == 0) goto Ld
            java.util.List r1 = kotlin.collections.CollectionsKt.emptyList()
            r16 = r1
            goto Lf
        Ld:
            r16 = r32
        Lf:
            r0 = r0 & 1024(0x400, float:1.435E-42)
            if (r0 == 0) goto L17
            r0 = 0
            r17 = r0
            goto L19
        L17:
            r17 = r33
        L19:
            r2 = r18
            r3 = r19
            r5 = r21
            r7 = r23
            r8 = r24
            r9 = r25
            r11 = r27
            r13 = r29
            r14 = r30
            r15 = r31
            r2.<init>(r3, r5, r7, r8, r9, r11, r13, r14, r15, r16, r17)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.core.Message.<init>(long, long, java.lang.String, java.lang.String, long, long, com.meharenterprises.originsms.core.MessageType, com.meharenterprises.originsms.core.MessageBox, boolean, java.util.List, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final long getId() {
        return this.id;
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

    public final long getDateSentMillis() {
        return this.dateSentMillis;
    }

    public final MessageType getType() {
        return this.type;
    }

    public final MessageBox getBox() {
        return this.box;
    }

    public final boolean isRead() {
        return this.isRead;
    }

    public final List<Attachment> getAttachments() {
        return this.attachments;
    }

    public final boolean isStarred() {
        return this.isStarred;
    }

    public final long scheduledTimeOrDate() {
        return this.dateMillis;
    }
}
