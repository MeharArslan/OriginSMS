package com.meharenterprises.originsms.core;

import android.content.Context;
import android.net.Uri;
import androidx.exifinterface.media.ExifInterface;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

/* compiled from: MmsPduBuilder.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001.B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0012H\u0002J,\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0012J\u0010\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u001e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J\u0010\u0010%\u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002J\u0010\u0010&\u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002J \u0010'\u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010(\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u0017H\u0002J \u0010)\u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010(\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u0004H\u0002J\u0018\u0010*\u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u0017H\u0002J\u0018\u0010+\u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u0017H\u0002J\u0010\u0010,\u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002J\u0018\u0010-\u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lcom/meharenterprises/originsms/core/MmsPduBuilder;", "", "()V", "MESSAGE_CLASS_PERSONAL", "", "MESSAGE_TYPE_SEND_REQ", "MMS_CONTENT_TYPE", "MMS_DATE", "MMS_FROM", "MMS_MESSAGE_CLASS", "MMS_MESSAGE_TYPE", "MMS_PRIORITY", "MMS_TO", "MMS_TRANSACTION_ID", "MMS_VERSION", "MMS_VERSION_1_3", "PRIORITY_NORMAL", "buildParts", "", "Lcom/meharenterprises/originsms/core/MmsPduBuilder$MmsPartData;", "context", "Landroid/content/Context;", "text", "", "attachmentUris", "Landroid/net/Uri;", "buildSendRequest", "", "destinationAddress", "longToMinimalBytes", "value", "", "writeBody", "", "output", "Ljava/io/ByteArrayOutputStream;", "parts", "writeContentTypeMultipartMixed", "writeDateHeader", "writeEncodedStringHeader", "fieldCode", "writeHeader", "writeNullTerminatedString", "writeNullTerminatedStringTo", "writeTransactionId", "writeUintVar", "MmsPartData", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class MmsPduBuilder {
    public static final MmsPduBuilder INSTANCE = new MmsPduBuilder();
    private static final int MESSAGE_CLASS_PERSONAL = 128;
    private static final int MESSAGE_TYPE_SEND_REQ = 128;
    private static final int MMS_CONTENT_TYPE = 132;
    private static final int MMS_DATE = 133;
    private static final int MMS_FROM = 137;
    private static final int MMS_MESSAGE_CLASS = 138;
    private static final int MMS_MESSAGE_TYPE = 140;
    private static final int MMS_PRIORITY = 143;
    private static final int MMS_TO = 151;
    private static final int MMS_TRANSACTION_ID = 152;
    private static final int MMS_VERSION = 141;
    private static final int MMS_VERSION_1_3 = 147;
    private static final int PRIORITY_NORMAL = 128;

    private MmsPduBuilder() {
    }

    public final byte[] buildSendRequest(Context context, String destinationAddress, String text, List<? extends Uri> attachmentUris) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(destinationAddress, "destinationAddress");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(attachmentUris, "attachmentUris");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        writeHeader(output, MMS_MESSAGE_TYPE, 128);
        writeTransactionId(output);
        writeHeader(output, MMS_VERSION, MMS_VERSION_1_3);
        writeDateHeader(output);
        writeEncodedStringHeader(output, MMS_FROM, "insert-address-token");
        writeEncodedStringHeader(output, MMS_TO, destinationAddress);
        writeHeader(output, MMS_MESSAGE_CLASS, 128);
        writeHeader(output, MMS_PRIORITY, 128);
        List parts = buildParts(context, text, attachmentUris);
        writeContentTypeMultipartMixed(output);
        writeBody(output, parts);
        byte[] byteArray = output.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
        return byteArray;
    }

    private final void writeHeader(ByteArrayOutputStream output, int fieldCode, int value) {
        output.write(fieldCode);
        output.write(value);
    }

    private final void writeTransactionId(ByteArrayOutputStream output) {
        output.write(MMS_TRANSACTION_ID);
        String txId = ExifInterface.GPS_DIRECTION_TRUE + System.currentTimeMillis();
        writeNullTerminatedString(output, txId);
    }

    private final void writeDateHeader(ByteArrayOutputStream output) {
        output.write(MMS_DATE);
        long seconds = System.currentTimeMillis() / 1000;
        byte[] bytes = longToMinimalBytes(seconds);
        output.write(bytes.length);
        output.write(bytes);
    }

    private final void writeEncodedStringHeader(ByteArrayOutputStream output, int fieldCode, String value) {
        output.write(fieldCode);
        writeNullTerminatedString(output, value);
    }

    private final void writeContentTypeMultipartMixed(ByteArrayOutputStream output) {
        output.write(MMS_CONTENT_TYPE);
        writeNullTerminatedString(output, "application/vnd.wap.multipart.mixed");
    }

    private final void writeNullTerminatedString(ByteArrayOutputStream output, String value) {
        byte[] bytes = value.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        output.write(bytes);
        output.write(0);
    }

    private final byte[] longToMinimalBytes(long value) {
        if (value == 0) {
            return new byte[]{0};
        }
        List bytes = new ArrayList();
        for (long v = value; v > 0; v >>>= 8) {
            bytes.add(0, Byte.valueOf((byte) (255 & v)));
        }
        return CollectionsKt.toByteArray(bytes);
    }

    private final void writeUintVar(ByteArrayOutputStream output, int value) {
        int v = value;
        List chunks = new ArrayList();
        chunks.add(Integer.valueOf(v & 127));
        while (true) {
            v >>>= 7;
            if (v <= 0) {
                break;
            } else {
                chunks.add(0, Integer.valueOf((v & 127) | 128));
            }
        }
        int i = 0;
        int size = chunks.size();
        while (i < size) {
            boolean isLast = i == chunks.size() - 1;
            int intValue = ((Number) chunks.get(i)).intValue();
            if (!isLast) {
                intValue |= 128;
            }
            output.write(intValue);
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: MmsPduBuilder.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0017"}, d2 = {"Lcom/meharenterprises/originsms/core/MmsPduBuilder$MmsPartData;", "", "contentType", "", "fileName", "bytes", "", "(Ljava/lang/String;Ljava/lang/String;[B)V", "getBytes", "()[B", "getContentType", "()Ljava/lang/String;", "getFileName", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes8.dex */
    public static final /* data */ class MmsPartData {
        private final byte[] bytes;
        private final String contentType;
        private final String fileName;

        public static /* synthetic */ MmsPartData copy$default(MmsPartData mmsPartData, String str, String str2, byte[] bArr, int i, Object obj) {
            if ((i & 1) != 0) {
                str = mmsPartData.contentType;
            }
            if ((i & 2) != 0) {
                str2 = mmsPartData.fileName;
            }
            if ((i & 4) != 0) {
                bArr = mmsPartData.bytes;
            }
            return mmsPartData.copy(str, str2, bArr);
        }

        /* renamed from: component1, reason: from getter */
        public final String getContentType() {
            return this.contentType;
        }

        /* renamed from: component2, reason: from getter */
        public final String getFileName() {
            return this.fileName;
        }

        /* renamed from: component3, reason: from getter */
        public final byte[] getBytes() {
            return this.bytes;
        }

        public final MmsPartData copy(String contentType, String fileName, byte[] bytes) {
            Intrinsics.checkNotNullParameter(contentType, "contentType");
            Intrinsics.checkNotNullParameter(fileName, "fileName");
            Intrinsics.checkNotNullParameter(bytes, "bytes");
            return new MmsPartData(contentType, fileName, bytes);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof MmsPartData)) {
                return false;
            }
            MmsPartData mmsPartData = (MmsPartData) other;
            return Intrinsics.areEqual(this.contentType, mmsPartData.contentType) && Intrinsics.areEqual(this.fileName, mmsPartData.fileName) && Intrinsics.areEqual(this.bytes, mmsPartData.bytes);
        }

        public int hashCode() {
            return (((this.contentType.hashCode() * 31) + this.fileName.hashCode()) * 31) + Arrays.hashCode(this.bytes);
        }

        public String toString() {
            return "MmsPartData(contentType=" + this.contentType + ", fileName=" + this.fileName + ", bytes=" + Arrays.toString(this.bytes) + ")";
        }

        public MmsPartData(String contentType, String fileName, byte[] bytes) {
            Intrinsics.checkNotNullParameter(contentType, "contentType");
            Intrinsics.checkNotNullParameter(fileName, "fileName");
            Intrinsics.checkNotNullParameter(bytes, "bytes");
            this.contentType = contentType;
            this.fileName = fileName;
            this.bytes = bytes;
        }

        public final byte[] getBytes() {
            return this.bytes;
        }

        public final String getContentType() {
            return this.contentType;
        }

        public final String getFileName() {
            return this.fileName;
        }
    }

    private final List<MmsPartData> buildParts(Context context, String text, List<? extends Uri> attachmentUris) {
        byte[] bArr;
        List parts = new ArrayList();
        if (!StringsKt.isBlank(text)) {
            byte[] bytes = text.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            parts.add(new MmsPartData("text/plain", "text_0.txt", bytes));
        }
        List<? extends Uri> $this$forEachIndexed$iv = attachmentUris;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            int index$iv2 = index$iv + 1;
            if (index$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Uri uri = (Uri) item$iv;
            int index = index$iv;
            String type = context.getContentResolver().getType(uri);
            if (type == null) {
                type = "application/octet-stream";
            }
            Intrinsics.checkNotNull(type);
            String contentType = type;
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            if (openInputStream != null) {
                InputStream inputStream = openInputStream;
                try {
                    InputStream it = inputStream;
                    Intrinsics.checkNotNull(it);
                    bArr = ByteStreamsKt.readBytes(it);
                    CloseableKt.closeFinally(inputStream, null);
                    if (bArr != null) {
                        byte[] bytes2 = bArr;
                        String extension = StringsKt.substringAfter(contentType, "/", "bin");
                        parts.add(new MmsPartData(contentType, "media_" + index + "." + extension, bytes2));
                        index$iv = index$iv2;
                    }
                } finally {
                }
            }
            bArr = new byte[0];
            byte[] bytes22 = bArr;
            String extension2 = StringsKt.substringAfter(contentType, "/", "bin");
            parts.add(new MmsPartData(contentType, "media_" + index + "." + extension2, bytes22));
            index$iv = index$iv2;
        }
        return parts;
    }

    private final void writeBody(ByteArrayOutputStream output, List<MmsPartData> parts) {
        writeUintVar(output, parts.size());
        for (MmsPartData part : parts) {
            ByteArrayOutputStream $this$writeBody_u24lambda_u242 = new ByteArrayOutputStream();
            byte[] bytes = part.getContentType().getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            $this$writeBody_u24lambda_u242.write(bytes);
            $this$writeBody_u24lambda_u242.write(0);
            $this$writeBody_u24lambda_u242.write(131);
            INSTANCE.writeNullTerminatedStringTo($this$writeBody_u24lambda_u242, part.getFileName());
            byte[] headerBytes = $this$writeBody_u24lambda_u242.toByteArray();
            ByteArrayOutputStream it = new ByteArrayOutputStream();
            INSTANCE.writeUintVar(it, headerBytes.length);
            byte[] headerLengthBytes = it.toByteArray();
            ByteArrayOutputStream it2 = new ByteArrayOutputStream();
            INSTANCE.writeUintVar(it2, part.getBytes().length);
            byte[] dataLengthBytes = it2.toByteArray();
            output.write(headerLengthBytes);
            output.write(dataLengthBytes);
            output.write(headerBytes);
            output.write(part.getBytes());
        }
    }

    private final void writeNullTerminatedStringTo(ByteArrayOutputStream output, String value) {
        byte[] bytes = value.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        output.write(bytes);
        output.write(0);
    }
}
