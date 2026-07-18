package com.meharenterprises.originsms.lock;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;

/* compiled from: PinManager.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\r\u0018\u0000  2\u00020\u0001:\u0001 B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\fJ\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0002J\b\u0010\u0012\u001a\u00020\u0010H\u0002J\u0006\u0010\u0013\u001a\u00020\u0014J\u0006\u0010\u0015\u001a\u00020\u000eJ\u0018\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0010H\u0002J\u0006\u0010\u0019\u001a\u00020\u000eJ\b\u0010\u001a\u001a\u00020\fH\u0002J\u000e\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u000eJ\u000e\u0010\u001d\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0010J\u000e\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u0010R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006!"}, d2 = {"Lcom/meharenterprises/originsms/lock/PinManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "getPrefs", "()Landroid/content/SharedPreferences;", "prefs$delegate", "Lkotlin/Lazy;", "clearPin", "", "constantTimeEquals", "", "a", "", "b", "generateSalt", "getLockoutSecondsRemaining", "", "hasPinConfigured", "hashPin", "pin", "salt", "isBiometricEnabled", "registerFailedAttempt", "setBiometricEnabled", "enabled", "setPin", "verifyPin", "candidate", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class PinManager {
    private static final long BASE_COOLDOWN_SECONDS = 30;
    private static final String KEY_BIOMETRIC_ENABLED = "biometric_enabled";
    private static final String KEY_FAILED_ATTEMPTS = "failed_attempts";
    private static final String KEY_HASH = "pin_hash";
    private static final String KEY_LOCKOUT_UNTIL = "lockout_until";
    private static final String KEY_SALT = "pin_salt";
    private static final int MAX_ATTEMPTS_BEFORE_LOCKOUT = 5;
    private static final long MAX_COOLDOWN_SECONDS = 600;
    private static final String PREFS_FILE_NAME = "origin_sms_secure_prefs";

    /* renamed from: prefs$delegate, reason: from kotlin metadata */
    private final Lazy prefs;

    public PinManager(final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.prefs = LazyKt.lazy(new Function0<SharedPreferences>() { // from class: com.meharenterprises.originsms.lock.PinManager$prefs$2
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final SharedPreferences invoke() {
                MasterKey masterKey = new MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();
                Intrinsics.checkNotNullExpressionValue(masterKey, "build(...)");
                return EncryptedSharedPreferences.create(context, "origin_sms_secure_prefs", masterKey, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            }
        });
    }

    private final SharedPreferences getPrefs() {
        return (SharedPreferences) this.prefs.getValue();
    }

    public final boolean hasPinConfigured() {
        return getPrefs().contains(KEY_HASH);
    }

    public final void setPin(String pin) {
        Intrinsics.checkNotNullParameter(pin, "pin");
        String salt = generateSalt();
        String hash = hashPin(pin, salt);
        getPrefs().edit().putString(KEY_SALT, salt).putString(KEY_HASH, hash).putInt(KEY_FAILED_ATTEMPTS, 0).putLong(KEY_LOCKOUT_UNTIL, 0L).apply();
    }

    public final boolean verifyPin(String candidate) {
        String storedHash;
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        String salt = getPrefs().getString(KEY_SALT, null);
        if (salt == null || (storedHash = getPrefs().getString(KEY_HASH, null)) == null) {
            return false;
        }
        String candidateHash = hashPin(candidate, salt);
        boolean matches = constantTimeEquals(candidateHash, storedHash);
        if (matches) {
            getPrefs().edit().putInt(KEY_FAILED_ATTEMPTS, 0).putLong(KEY_LOCKOUT_UNTIL, 0L).apply();
        } else {
            registerFailedAttempt();
        }
        return matches;
    }

    public final boolean isBiometricEnabled() {
        return getPrefs().getBoolean(KEY_BIOMETRIC_ENABLED, false);
    }

    public final void setBiometricEnabled(boolean enabled) {
        getPrefs().edit().putBoolean(KEY_BIOMETRIC_ENABLED, enabled).apply();
    }

    public final void clearPin() {
        getPrefs().edit().clear().apply();
    }

    public final long getLockoutSecondsRemaining() {
        long until = getPrefs().getLong(KEY_LOCKOUT_UNTIL, 0L);
        long remainingMillis = until - System.currentTimeMillis();
        if (remainingMillis > 0) {
            return (remainingMillis / 1000) + 1;
        }
        return 0L;
    }

    private final void registerFailedAttempt() {
        int attempts = getPrefs().getInt(KEY_FAILED_ATTEMPTS, 0) + 1;
        SharedPreferences.Editor editor = getPrefs().edit().putInt(KEY_FAILED_ATTEMPTS, attempts);
        if (attempts >= 5) {
            int lockoutStreak = attempts - 5;
            long cooldownSeconds = RangesKt.coerceAtMost(BASE_COOLDOWN_SECONDS << RangesKt.coerceAtMost(lockoutStreak, 5), MAX_COOLDOWN_SECONDS);
            editor.putLong(KEY_LOCKOUT_UNTIL, System.currentTimeMillis() + (1000 * cooldownSeconds));
        }
        editor.apply();
    }

    private final String generateSalt() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        return ArraysKt.joinToString$default(bytes, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) new Function1<Byte, CharSequence>() { // from class: com.meharenterprises.originsms.lock.PinManager$generateSalt$1
            public final CharSequence invoke(byte it) {
                String format = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(it)}, 1));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                return format;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Byte b) {
                return invoke(b.byteValue());
            }
        }, 30, (Object) null);
    }

    private final String hashPin(String pin, String salt) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = salt.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        digest.update(bytes);
        byte[] bytes2 = pin.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
        byte[] hashBytes = digest.digest(bytes2);
        Intrinsics.checkNotNull(hashBytes);
        return ArraysKt.joinToString$default(hashBytes, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) new Function1<Byte, CharSequence>() { // from class: com.meharenterprises.originsms.lock.PinManager$hashPin$1
            public final CharSequence invoke(byte it) {
                String format = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(it)}, 1));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                return format;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Byte b) {
                return invoke(b.byteValue());
            }
        }, 30, (Object) null);
    }

    private final boolean constantTimeEquals(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        int result = 0;
        int length = a.length();
        for (int i = 0; i < length; i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}
