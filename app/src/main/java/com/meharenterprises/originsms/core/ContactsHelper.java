package com.meharenterprises.originsms.core;

import android.content.Context;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ContactsHelper.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \u000e2\u00020\u0001:\u0003\u000e\u000f\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/meharenterprises/originsms/core/ContactsHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "clearCache", "", "getAllContactsWithNumbers", "", "Lcom/meharenterprises/originsms/core/ContactsHelper$PickableContact;", "resolve", "Lcom/meharenterprises/originsms/core/ContactsHelper$ContactInfo;", "rawNumber", "", "Companion", "ContactInfo", "PickableContact", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ContactsHelper {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ConcurrentHashMap<String, ContactInfo> staticCache = new ConcurrentHashMap<>();
    private final Context context;

    public ContactsHelper(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    /* compiled from: ContactsHelper.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/meharenterprises/originsms/core/ContactsHelper$ContactInfo;", "", "displayName", "", "photoUri", "(Ljava/lang/String;Ljava/lang/String;)V", "getDisplayName", "()Ljava/lang/String;", "getPhotoUri", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes8.dex */
    public static final /* data */ class ContactInfo {
        private final String displayName;
        private final String photoUri;

        public static /* synthetic */ ContactInfo copy$default(ContactInfo contactInfo, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = contactInfo.displayName;
            }
            if ((i & 2) != 0) {
                str2 = contactInfo.photoUri;
            }
            return contactInfo.copy(str, str2);
        }

        /* renamed from: component1, reason: from getter */
        public final String getDisplayName() {
            return this.displayName;
        }

        /* renamed from: component2, reason: from getter */
        public final String getPhotoUri() {
            return this.photoUri;
        }

        public final ContactInfo copy(String displayName, String photoUri) {
            Intrinsics.checkNotNullParameter(displayName, "displayName");
            return new ContactInfo(displayName, photoUri);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ContactInfo)) {
                return false;
            }
            ContactInfo contactInfo = (ContactInfo) other;
            return Intrinsics.areEqual(this.displayName, contactInfo.displayName) && Intrinsics.areEqual(this.photoUri, contactInfo.photoUri);
        }

        public int hashCode() {
            return (this.displayName.hashCode() * 31) + (this.photoUri == null ? 0 : this.photoUri.hashCode());
        }

        public String toString() {
            return "ContactInfo(displayName=" + this.displayName + ", photoUri=" + this.photoUri + ")";
        }

        public ContactInfo(String displayName, String photoUri) {
            Intrinsics.checkNotNullParameter(displayName, "displayName");
            this.displayName = displayName;
            this.photoUri = photoUri;
        }

        public final String getDisplayName() {
            return this.displayName;
        }

        public final String getPhotoUri() {
            return this.photoUri;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0068 A[Catch: all -> 0x0078, TryCatch #1 {all -> 0x0078, blocks: (B:12:0x0044, B:14:0x004e, B:16:0x005a, B:19:0x0061, B:21:0x0068, B:22:0x006e), top: B:11:0x0044, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.meharenterprises.originsms.core.ContactsHelper.ContactInfo resolve(java.lang.String r13) {
        /*
            r12 = this;
            java.lang.String r0 = "rawNumber"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r13, r0)
            com.meharenterprises.originsms.core.ContactsHelper$Companion r0 = com.meharenterprises.originsms.core.ContactsHelper.INSTANCE
            java.lang.String r0 = r0.normalize(r13)
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.meharenterprises.originsms.core.ContactsHelper$ContactInfo> r1 = com.meharenterprises.originsms.core.ContactsHelper.staticCache
            java.lang.Object r1 = r1.get(r0)
            com.meharenterprises.originsms.core.ContactsHelper$ContactInfo r1 = (com.meharenterprises.originsms.core.ContactsHelper.ContactInfo) r1
            if (r1 == 0) goto L17
            r2 = 0
            return r1
        L17:
            android.net.Uri r1 = android.provider.ContactsContract.PhoneLookup.CONTENT_FILTER_URI
            java.lang.String r2 = android.net.Uri.encode(r13)
            android.net.Uri r1 = android.net.Uri.withAppendedPath(r1, r2)
            r2 = 2
            java.lang.String[] r5 = new java.lang.String[r2]
            r2 = 0
            java.lang.String r9 = "display_name"
            r5[r2] = r9
            r2 = 1
            java.lang.String r10 = "photo_uri"
            r5[r2] = r10
            r2 = 0
            android.content.Context r3 = r12.context     // Catch: java.lang.SecurityException -> L81
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch: java.lang.SecurityException -> L81
            r7 = 0
            r8 = 0
            r6 = 0
            r4 = r1
            android.database.Cursor r3 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.SecurityException -> L81
            if (r3 == 0) goto L7f
            java.io.Closeable r3 = (java.io.Closeable) r3     // Catch: java.lang.SecurityException -> L81
            r4 = r3
            android.database.Cursor r4 = (android.database.Cursor) r4     // Catch: java.lang.Throwable -> L78
            r6 = 0
            boolean r7 = r4.moveToFirst()     // Catch: java.lang.Throwable -> L78
            if (r7 == 0) goto L72
            int r7 = r4.getColumnIndex(r9)     // Catch: java.lang.Throwable -> L78
            int r8 = r4.getColumnIndex(r10)     // Catch: java.lang.Throwable -> L78
            com.meharenterprises.originsms.core.ContactsHelper$ContactInfo r9 = new com.meharenterprises.originsms.core.ContactsHelper$ContactInfo     // Catch: java.lang.Throwable -> L78
            if (r7 < 0) goto L65
            java.lang.String r10 = r4.getString(r7)     // Catch: java.lang.Throwable -> L78
            if (r10 != 0) goto L61
            goto L65
        L61:
            kotlin.jvm.internal.Intrinsics.checkNotNull(r10)     // Catch: java.lang.Throwable -> L78
            goto L66
        L65:
            r10 = r13
        L66:
            if (r8 < 0) goto L6d
            java.lang.String r11 = r4.getString(r8)     // Catch: java.lang.Throwable -> L78
            goto L6e
        L6d:
            r11 = r2
        L6e:
            r9.<init>(r10, r11)     // Catch: java.lang.Throwable -> L78
            goto L73
        L72:
            r9 = r2
        L73:
            kotlin.io.CloseableKt.closeFinally(r3, r2)     // Catch: java.lang.SecurityException -> L81
            goto L83
        L78:
            r4 = move-exception
            throw r4     // Catch: java.lang.Throwable -> L7a
        L7a:
            r6 = move-exception
            kotlin.io.CloseableKt.closeFinally(r3, r4)     // Catch: java.lang.SecurityException -> L81
            throw r6     // Catch: java.lang.SecurityException -> L81
        L7f:
            r9 = r2
            goto L83
        L81:
            r3 = move-exception
            r9 = r2
        L83:
            if (r9 != 0) goto L8a
            com.meharenterprises.originsms.core.ContactsHelper$ContactInfo r9 = new com.meharenterprises.originsms.core.ContactsHelper$ContactInfo
            r9.<init>(r13, r2)
        L8a:
            r2 = r9
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.meharenterprises.originsms.core.ContactsHelper$ContactInfo> r3 = com.meharenterprises.originsms.core.ContactsHelper.staticCache
            java.util.Map r3 = (java.util.Map) r3
            r3.put(r0, r2)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.core.ContactsHelper.resolve(java.lang.String):com.meharenterprises.originsms.core.ContactsHelper$ContactInfo");
    }

    public final void clearCache() {
        staticCache.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x007a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0051 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.List<com.meharenterprises.originsms.core.ContactsHelper.PickableContact> getAllContactsWithNumbers() {
        /*
            r21 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = r0
            java.util.List r1 = (java.util.List) r1
            r0 = 4
            java.lang.String[] r4 = new java.lang.String[r0]
            r0 = 0
            java.lang.String r8 = "display_name"
            r4[r0] = r8
            r9 = 1
            java.lang.String r10 = "data1"
            r4[r9] = r10
            r2 = 2
            java.lang.String r11 = "photo_uri"
            r4[r2] = r11
            r2 = 3
            java.lang.String r12 = "contact_id"
            r4[r2] = r12
            r13 = r21
            android.content.Context r2 = r13.context     // Catch: java.lang.SecurityException -> Laf
            android.content.ContentResolver r2 = r2.getContentResolver()     // Catch: java.lang.SecurityException -> Laf
            android.net.Uri r3 = android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI     // Catch: java.lang.SecurityException -> Laf
            java.lang.String r7 = "display_name ASC"
            r5 = 0
            r6 = 0
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.SecurityException -> Laf
            if (r2 == 0) goto Lb0
            java.io.Closeable r2 = (java.io.Closeable) r2     // Catch: java.lang.SecurityException -> Laf
            r3 = r2
            android.database.Cursor r3 = (android.database.Cursor) r3     // Catch: java.lang.Throwable -> La6
            r5 = 0
            int r6 = r3.getColumnIndex(r8)     // Catch: java.lang.Throwable -> La6
            int r7 = r3.getColumnIndex(r10)     // Catch: java.lang.Throwable -> La6
            int r8 = r3.getColumnIndex(r11)     // Catch: java.lang.Throwable -> La6
            int r10 = r3.getColumnIndex(r12)     // Catch: java.lang.Throwable -> La6
        L51:
            boolean r11 = r3.moveToNext()     // Catch: java.lang.Throwable -> La6
            r12 = 0
            if (r11 == 0) goto L9f
            if (r6 < 0) goto L5f
            java.lang.String r11 = r3.getString(r6)     // Catch: java.lang.Throwable -> La6
            goto L60
        L5f:
            r11 = r12
        L60:
            if (r7 < 0) goto L67
            java.lang.String r14 = r3.getString(r7)     // Catch: java.lang.Throwable -> La6
            goto L68
        L67:
            r14 = r12
        L68:
            r15 = r14
            java.lang.CharSequence r15 = (java.lang.CharSequence) r15     // Catch: java.lang.Throwable -> La6
            if (r15 == 0) goto L76
            boolean r15 = kotlin.text.StringsKt.isBlank(r15)     // Catch: java.lang.Throwable -> La6
            if (r15 == 0) goto L74
            goto L76
        L74:
            r15 = r0
            goto L77
        L76:
            r15 = r9
        L77:
            if (r15 != 0) goto L51
        L7a:
            com.meharenterprises.originsms.core.ContactsHelper$PickableContact r15 = new com.meharenterprises.originsms.core.ContactsHelper$PickableContact     // Catch: java.lang.Throwable -> La6
            if (r10 < 0) goto L83
            long r16 = r3.getLong(r10)     // Catch: java.lang.Throwable -> La6
            goto L85
        L83:
            r16 = -1
        L85:
            if (r11 != 0) goto L8a
            r18 = r14
            goto L8c
        L8a:
            r18 = r11
        L8c:
            if (r8 < 0) goto L93
            java.lang.String r12 = r3.getString(r8)     // Catch: java.lang.Throwable -> La6
        L93:
            r20 = r12
            r12 = r15
            r19 = r14
            r15.<init>(r16, r18, r19, r20)     // Catch: java.lang.Throwable -> La6
            r1.add(r12)     // Catch: java.lang.Throwable -> La6
            goto L51
        L9f:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> La6
            kotlin.io.CloseableKt.closeFinally(r2, r12)     // Catch: java.lang.SecurityException -> Laf
            goto Lb0
        La6:
            r0 = move-exception
            r3 = r0
            throw r3     // Catch: java.lang.Throwable -> La9
        La9:
            r0 = move-exception
            r5 = r0
            kotlin.io.CloseableKt.closeFinally(r2, r3)     // Catch: java.lang.SecurityException -> Laf
            throw r5     // Catch: java.lang.SecurityException -> Laf
        Laf:
            r0 = move-exception
        Lb0:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meharenterprises.originsms.core.ContactsHelper.getAllContactsWithNumbers():java.util.List");
    }

    /* compiled from: ContactsHelper.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0005HÆ\u0003J3\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f¨\u0006\u001a"}, d2 = {"Lcom/meharenterprises/originsms/core/ContactsHelper$PickableContact;", "", "contactId", "", "displayName", "", "phoneNumber", "photoUri", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getContactId", "()J", "getDisplayName", "()Ljava/lang/String;", "getPhoneNumber", "getPhotoUri", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes8.dex */
    public static final /* data */ class PickableContact {
        private final long contactId;
        private final String displayName;
        private final String phoneNumber;
        private final String photoUri;

        public static /* synthetic */ PickableContact copy$default(PickableContact pickableContact, long j, String str, String str2, String str3, int i, Object obj) {
            if ((i & 1) != 0) {
                j = pickableContact.contactId;
            }
            long j2 = j;
            if ((i & 2) != 0) {
                str = pickableContact.displayName;
            }
            String str4 = str;
            if ((i & 4) != 0) {
                str2 = pickableContact.phoneNumber;
            }
            String str5 = str2;
            if ((i & 8) != 0) {
                str3 = pickableContact.photoUri;
            }
            return pickableContact.copy(j2, str4, str5, str3);
        }

        /* renamed from: component1, reason: from getter */
        public final long getContactId() {
            return this.contactId;
        }

        /* renamed from: component2, reason: from getter */
        public final String getDisplayName() {
            return this.displayName;
        }

        /* renamed from: component3, reason: from getter */
        public final String getPhoneNumber() {
            return this.phoneNumber;
        }

        /* renamed from: component4, reason: from getter */
        public final String getPhotoUri() {
            return this.photoUri;
        }

        public final PickableContact copy(long contactId, String displayName, String phoneNumber, String photoUri) {
            Intrinsics.checkNotNullParameter(displayName, "displayName");
            Intrinsics.checkNotNullParameter(phoneNumber, "phoneNumber");
            return new PickableContact(contactId, displayName, phoneNumber, photoUri);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof PickableContact)) {
                return false;
            }
            PickableContact pickableContact = (PickableContact) other;
            return this.contactId == pickableContact.contactId && Intrinsics.areEqual(this.displayName, pickableContact.displayName) && Intrinsics.areEqual(this.phoneNumber, pickableContact.phoneNumber) && Intrinsics.areEqual(this.photoUri, pickableContact.photoUri);
        }

        public int hashCode() {
            return (((((Long.hashCode(this.contactId) * 31) + this.displayName.hashCode()) * 31) + this.phoneNumber.hashCode()) * 31) + (this.photoUri == null ? 0 : this.photoUri.hashCode());
        }

        public String toString() {
            return "PickableContact(contactId=" + this.contactId + ", displayName=" + this.displayName + ", phoneNumber=" + this.phoneNumber + ", photoUri=" + this.photoUri + ")";
        }

        public PickableContact(long contactId, String displayName, String phoneNumber, String photoUri) {
            Intrinsics.checkNotNullParameter(displayName, "displayName");
            Intrinsics.checkNotNullParameter(phoneNumber, "phoneNumber");
            this.contactId = contactId;
            this.displayName = displayName;
            this.phoneNumber = phoneNumber;
            this.photoUri = photoUri;
        }

        public final long getContactId() {
            return this.contactId;
        }

        public final String getDisplayName() {
            return this.displayName;
        }

        public final String getPhoneNumber() {
            return this.phoneNumber;
        }

        public final String getPhotoUri() {
            return this.photoUri;
        }
    }

    /* compiled from: ContactsHelper.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/meharenterprises/originsms/core/ContactsHelper$Companion;", "", "()V", "staticCache", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lcom/meharenterprises/originsms/core/ContactsHelper$ContactInfo;", "normalize", "number", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes8.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String normalize(String number) {
            Intrinsics.checkNotNullParameter(number, "number");
            String $this$filterTo$iv$iv = number;
            Appendable destination$iv$iv = new StringBuilder();
            int length = $this$filterTo$iv$iv.length();
            for (int index$iv$iv = 0; index$iv$iv < length; index$iv$iv++) {
                char element$iv$iv = $this$filterTo$iv$iv.charAt(index$iv$iv);
                if (Character.isDigit(element$iv$iv) || element$iv$iv == '+') {
                    destination$iv$iv.append(element$iv$iv);
                }
            }
            String sb = ((StringBuilder) destination$iv$iv).toString();
            Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
            return sb;
        }
    }
}
