package com.meharenterprises.originsms.core;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ConversationSummary.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b(\b\u0086\b\u0018\u00002\u00020\u0001B\u008f\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n\u0012\b\b\u0002\u0010\r\u001a\u00020\n\u0012\b\b\u0002\u0010\u000e\u001a\u00020\n\u0012\b\b\u0002\u0010\u000f\u001a\u00020\n\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0015J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\nHÆ\u0003J\t\u0010'\u001a\u00020\nHÆ\u0003J\u0010\u0010(\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001cJ\t\u0010)\u001a\u00020\u0012HÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010,\u001a\u00020\u0005HÆ\u0003J\t\u0010-\u001a\u00020\u0005HÆ\u0003J\t\u0010.\u001a\u00020\u0005HÆ\u0003J\t\u0010/\u001a\u00020\u0003HÆ\u0003J\t\u00100\u001a\u00020\nHÆ\u0003J\t\u00101\u001a\u00020\nHÆ\u0003J\t\u00102\u001a\u00020\nHÆ\u0003J\t\u00103\u001a\u00020\nHÆ\u0003Jª\u0001\u00104\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\b\b\u0002\u0010\r\u001a\u00020\n2\b\b\u0002\u0010\u000e\u001a\u00020\n2\b\b\u0002\u0010\u000f\u001a\u00020\n2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0005HÆ\u0001¢\u0006\u0002\u00105J\u0013\u00106\u001a\u00020\n2\b\u00107\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00108\u001a\u00020\u0012HÖ\u0001J\t\u00109\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0017R\u0011\u0010\u000e\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010 R\u0011\u0010\u000f\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010 R\u0011\u0010\f\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010 R\u0011\u0010\u000b\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010 R\u0011\u0010\r\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010 R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010 R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001aR\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$¨\u0006:"}, d2 = {"Lcom/meharenterprises/originsms/core/ConversationSummary;", "", "threadId", "", "address", "", "displayName", "snippet", "dateMillis", "isRead", "", "isLocked", "isHidden", "isMuted", "isArchived", "isDeleted", "deletedAtMillis", "unreadCount", "", "contactPhotoUri", "draftText", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;JZZZZZZLjava/lang/Long;ILjava/lang/String;Ljava/lang/String;)V", "getAddress", "()Ljava/lang/String;", "getContactPhotoUri", "getDateMillis", "()J", "getDeletedAtMillis", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getDisplayName", "getDraftText", "()Z", "getSnippet", "getThreadId", "getUnreadCount", "()I", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;JZZZZZZLjava/lang/Long;ILjava/lang/String;Ljava/lang/String;)Lcom/meharenterprises/originsms/core/ConversationSummary;", "equals", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes8.dex */
public final /* data */ class ConversationSummary {
    private final String address;
    private final String contactPhotoUri;
    private final long dateMillis;
    private final Long deletedAtMillis;
    private final String displayName;
    private final String draftText;
    private final boolean isArchived;
    private final boolean isDeleted;
    private final boolean isHidden;
    private final boolean isLocked;
    private final boolean isMuted;
    private final boolean isRead;
    private final String snippet;
    private final long threadId;
    private final int unreadCount;

    /* renamed from: component1, reason: from getter */
    public final long getThreadId() {
        return this.threadId;
    }

    /* renamed from: component10, reason: from getter */
    public final boolean getIsArchived() {
        return this.isArchived;
    }

    /* renamed from: component11, reason: from getter */
    public final boolean getIsDeleted() {
        return this.isDeleted;
    }

    /* renamed from: component12, reason: from getter */
    public final Long getDeletedAtMillis() {
        return this.deletedAtMillis;
    }

    /* renamed from: component13, reason: from getter */
    public final int getUnreadCount() {
        return this.unreadCount;
    }

    /* renamed from: component14, reason: from getter */
    public final String getContactPhotoUri() {
        return this.contactPhotoUri;
    }

    /* renamed from: component15, reason: from getter */
    public final String getDraftText() {
        return this.draftText;
    }

    /* renamed from: component2, reason: from getter */
    public final String getAddress() {
        return this.address;
    }

    /* renamed from: component3, reason: from getter */
    public final String getDisplayName() {
        return this.displayName;
    }

    /* renamed from: component4, reason: from getter */
    public final String getSnippet() {
        return this.snippet;
    }

    /* renamed from: component5, reason: from getter */
    public final long getDateMillis() {
        return this.dateMillis;
    }

    /* renamed from: component6, reason: from getter */
    public final boolean getIsRead() {
        return this.isRead;
    }

    /* renamed from: component7, reason: from getter */
    public final boolean getIsLocked() {
        return this.isLocked;
    }

    /* renamed from: component8, reason: from getter */
    public final boolean getIsHidden() {
        return this.isHidden;
    }

    /* renamed from: component9, reason: from getter */
    public final boolean getIsMuted() {
        return this.isMuted;
    }

    public final ConversationSummary copy(long threadId, String address, String displayName, String snippet, long dateMillis, boolean isRead, boolean isLocked, boolean isHidden, boolean isMuted, boolean isArchived, boolean isDeleted, Long deletedAtMillis, int unreadCount, String contactPhotoUri, String draftText) {
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        Intrinsics.checkNotNullParameter(snippet, "snippet");
        return new ConversationSummary(threadId, address, displayName, snippet, dateMillis, isRead, isLocked, isHidden, isMuted, isArchived, isDeleted, deletedAtMillis, unreadCount, contactPhotoUri, draftText);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConversationSummary)) {
            return false;
        }
        ConversationSummary conversationSummary = (ConversationSummary) other;
        return this.threadId == conversationSummary.threadId && Intrinsics.areEqual(this.address, conversationSummary.address) && Intrinsics.areEqual(this.displayName, conversationSummary.displayName) && Intrinsics.areEqual(this.snippet, conversationSummary.snippet) && this.dateMillis == conversationSummary.dateMillis && this.isRead == conversationSummary.isRead && this.isLocked == conversationSummary.isLocked && this.isHidden == conversationSummary.isHidden && this.isMuted == conversationSummary.isMuted && this.isArchived == conversationSummary.isArchived && this.isDeleted == conversationSummary.isDeleted && Intrinsics.areEqual(this.deletedAtMillis, conversationSummary.deletedAtMillis) && this.unreadCount == conversationSummary.unreadCount && Intrinsics.areEqual(this.contactPhotoUri, conversationSummary.contactPhotoUri) && Intrinsics.areEqual(this.draftText, conversationSummary.draftText);
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((Long.hashCode(this.threadId) * 31) + this.address.hashCode()) * 31) + this.displayName.hashCode()) * 31) + this.snippet.hashCode()) * 31) + Long.hashCode(this.dateMillis)) * 31) + Boolean.hashCode(this.isRead)) * 31) + Boolean.hashCode(this.isLocked)) * 31) + Boolean.hashCode(this.isHidden)) * 31) + Boolean.hashCode(this.isMuted)) * 31) + Boolean.hashCode(this.isArchived)) * 31) + Boolean.hashCode(this.isDeleted)) * 31) + (this.deletedAtMillis == null ? 0 : this.deletedAtMillis.hashCode())) * 31) + Integer.hashCode(this.unreadCount)) * 31) + (this.contactPhotoUri == null ? 0 : this.contactPhotoUri.hashCode())) * 31) + (this.draftText != null ? this.draftText.hashCode() : 0);
    }

    public String toString() {
        return "ConversationSummary(threadId=" + this.threadId + ", address=" + this.address + ", displayName=" + this.displayName + ", snippet=" + this.snippet + ", dateMillis=" + this.dateMillis + ", isRead=" + this.isRead + ", isLocked=" + this.isLocked + ", isHidden=" + this.isHidden + ", isMuted=" + this.isMuted + ", isArchived=" + this.isArchived + ", isDeleted=" + this.isDeleted + ", deletedAtMillis=" + this.deletedAtMillis + ", unreadCount=" + this.unreadCount + ", contactPhotoUri=" + this.contactPhotoUri + ", draftText=" + this.draftText + ")";
    }

    public ConversationSummary(long threadId, String address, String displayName, String snippet, long dateMillis, boolean isRead, boolean isLocked, boolean isHidden, boolean isMuted, boolean isArchived, boolean isDeleted, Long deletedAtMillis, int unreadCount, String contactPhotoUri, String draftText) {
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        Intrinsics.checkNotNullParameter(snippet, "snippet");
        this.threadId = threadId;
        this.address = address;
        this.displayName = displayName;
        this.snippet = snippet;
        this.dateMillis = dateMillis;
        this.isRead = isRead;
        this.isLocked = isLocked;
        this.isHidden = isHidden;
        this.isMuted = isMuted;
        this.isArchived = isArchived;
        this.isDeleted = isDeleted;
        this.deletedAtMillis = deletedAtMillis;
        this.unreadCount = unreadCount;
        this.contactPhotoUri = contactPhotoUri;
        this.draftText = draftText;
    }

    public /* synthetic */ ConversationSummary(long j, String str, String str2, String str3, long j2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, Long l, int i, String str4, String str5, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, str, str2, str3, j2, z, z2, z3, (i2 & 256) != 0 ? false : z4, (i2 & 512) != 0 ? false : z5, (i2 & 1024) != 0 ? false : z6, (i2 & 2048) != 0 ? null : l, i, (i2 & 8192) != 0 ? null : str4, (i2 & 16384) != 0 ? null : str5);
    }

    public final long getThreadId() {
        return this.threadId;
    }

    public final String getAddress() {
        return this.address;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final String getSnippet() {
        return this.snippet;
    }

    public final long getDateMillis() {
        return this.dateMillis;
    }

    public final boolean isRead() {
        return this.isRead;
    }

    public final boolean isLocked() {
        return this.isLocked;
    }

    public final boolean isHidden() {
        return this.isHidden;
    }

    public final boolean isMuted() {
        return this.isMuted;
    }

    public final boolean isArchived() {
        return this.isArchived;
    }

    public final boolean isDeleted() {
        return this.isDeleted;
    }

    public final Long getDeletedAtMillis() {
        return this.deletedAtMillis;
    }

    public final int getUnreadCount() {
        return this.unreadCount;
    }

    public final String getContactPhotoUri() {
        return this.contactPhotoUri;
    }

    public final String getDraftText() {
        return this.draftText;
    }
}
