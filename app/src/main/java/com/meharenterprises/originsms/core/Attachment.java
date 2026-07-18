package com.meharenterprises.originsms.core;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Message.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0005HÆ\u0003J3\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001a"}, d2 = {"Lcom/meharenterprises/originsms/core/Attachment;", "", "partId", "", "contentType", "", "uri", "fileName", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getContentType", "()Ljava/lang/String;", "getFileName", "getPartId", "()J", "getUri", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes8.dex */
public final /* data */ class Attachment {
    private final String contentType;
    private final String fileName;
    private final long partId;
    private final String uri;

    public static /* synthetic */ Attachment copy$default(Attachment attachment, long j, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            j = attachment.partId;
        }
        long j2 = j;
        if ((i & 2) != 0) {
            str = attachment.contentType;
        }
        String str4 = str;
        if ((i & 4) != 0) {
            str2 = attachment.uri;
        }
        String str5 = str2;
        if ((i & 8) != 0) {
            str3 = attachment.fileName;
        }
        return attachment.copy(j2, str4, str5, str3);
    }

    /* renamed from: component1, reason: from getter */
    public final long getPartId() {
        return this.partId;
    }

    /* renamed from: component2, reason: from getter */
    public final String getContentType() {
        return this.contentType;
    }

    /* renamed from: component3, reason: from getter */
    public final String getUri() {
        return this.uri;
    }

    /* renamed from: component4, reason: from getter */
    public final String getFileName() {
        return this.fileName;
    }

    public final Attachment copy(long partId, String contentType, String uri, String fileName) {
        Intrinsics.checkNotNullParameter(contentType, "contentType");
        Intrinsics.checkNotNullParameter(uri, "uri");
        return new Attachment(partId, contentType, uri, fileName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Attachment)) {
            return false;
        }
        Attachment attachment = (Attachment) other;
        return this.partId == attachment.partId && Intrinsics.areEqual(this.contentType, attachment.contentType) && Intrinsics.areEqual(this.uri, attachment.uri) && Intrinsics.areEqual(this.fileName, attachment.fileName);
    }

    public int hashCode() {
        return (((((Long.hashCode(this.partId) * 31) + this.contentType.hashCode()) * 31) + this.uri.hashCode()) * 31) + (this.fileName == null ? 0 : this.fileName.hashCode());
    }

    public String toString() {
        return "Attachment(partId=" + this.partId + ", contentType=" + this.contentType + ", uri=" + this.uri + ", fileName=" + this.fileName + ")";
    }

    public Attachment(long partId, String contentType, String uri, String fileName) {
        Intrinsics.checkNotNullParameter(contentType, "contentType");
        Intrinsics.checkNotNullParameter(uri, "uri");
        this.partId = partId;
        this.contentType = contentType;
        this.uri = uri;
        this.fileName = fileName;
    }

    public final long getPartId() {
        return this.partId;
    }

    public final String getContentType() {
        return this.contentType;
    }

    public final String getUri() {
        return this.uri;
    }

    public final String getFileName() {
        return this.fileName;
    }
}
