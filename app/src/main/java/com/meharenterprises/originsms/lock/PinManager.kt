package com.meharenterprises.originsms.lock

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.MessageDigest
import java.security.SecureRandom

/**
 * Handles all PIN storage/verification for the chat-lock feature.
 *
 * The PIN itself is never stored — only a salted SHA-256 hash, so that even if
 * the underlying EncryptedSharedPreferences file were somehow extracted, the
 * original PIN cannot be recovered directly from it. EncryptedSharedPreferences
 * additionally wraps the file with a Keystore-backed AES key, so the hash/salt
 * are encrypted at rest too — this is a deliberate two-layer approach.
 *
 * Brute-force throttling: after 5 consecutive wrong attempts, verification is
 * locked out for a cooldown window that doubles with each subsequent failure
 * streak (30s, 60s, 120s, capped at 10 minutes).
 */
class PinManager(context: Context) {

    private val prefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            PREFS_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun hasPinConfigured(): Boolean = prefs.contains(KEY_HASH)

    fun setPin(pin: String) {
        val salt = generateSalt()
        val hash = hashPin(pin, salt)
        prefs.edit()
            .putString(KEY_SALT, salt)
            .putString(KEY_HASH, hash)
            .putInt(KEY_FAILED_ATTEMPTS, 0)
            .putLong(KEY_LOCKOUT_UNTIL, 0L)
            .apply()
    }

    fun verifyPin(candidate: String): Boolean {
        val salt = prefs.getString(KEY_SALT, null) ?: return false
        val storedHash = prefs.getString(KEY_HASH, null) ?: return false
        val candidateHash = hashPin(candidate, salt)
        val matches = constantTimeEquals(candidateHash, storedHash)

        if (matches) {
            prefs.edit().putInt(KEY_FAILED_ATTEMPTS, 0).putLong(KEY_LOCKOUT_UNTIL, 0L).apply()
        } else {
            registerFailedAttempt()
        }
        return matches
    }

    fun isBiometricEnabled(): Boolean = prefs.getBoolean(KEY_BIOMETRIC_ENABLED, false)

    fun setBiometricEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_BIOMETRIC_ENABLED, enabled).apply()
    }

    fun clearPin() {
        prefs.edit().clear().apply()
    }

    /**
     * Returns remaining lockout seconds, or 0 if not currently locked out.
     */
    fun getLockoutSecondsRemaining(): Long {
        val until = prefs.getLong(KEY_LOCKOUT_UNTIL, 0L)
        val remainingMillis = until - System.currentTimeMillis()
        return if (remainingMillis > 0) (remainingMillis / 1000) + 1 else 0L
    }

    private fun registerFailedAttempt() {
        val attempts = prefs.getInt(KEY_FAILED_ATTEMPTS, 0) + 1
        val editor = prefs.edit().putInt(KEY_FAILED_ATTEMPTS, attempts)

        if (attempts >= MAX_ATTEMPTS_BEFORE_LOCKOUT) {
            val lockoutStreak = attempts - MAX_ATTEMPTS_BEFORE_LOCKOUT
            val cooldownSeconds = (BASE_COOLDOWN_SECONDS shl lockoutStreak.coerceAtMost(5))
                .coerceAtMost(MAX_COOLDOWN_SECONDS)
            editor.putLong(KEY_LOCKOUT_UNTIL, System.currentTimeMillis() + cooldownSeconds * 1000L)
        }
        editor.apply()
    }

    private fun generateSalt(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun hashPin(pin: String, salt: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(salt.toByteArray(Charsets.UTF_8))
        val hashBytes = digest.digest(pin.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    private fun constantTimeEquals(a: String, b: String): Boolean {
        if (a.length != b.length) return false
        var result = 0
        for (i in a.indices) {
            result = result or (a[i].code xor b[i].code)
        }
        return result == 0
    }

    companion object {
        private const val PREFS_FILE_NAME = "origin_sms_secure_prefs"
        private const val KEY_SALT = "pin_salt"
        private const val KEY_HASH = "pin_hash"
        private const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"
        private const val KEY_FAILED_ATTEMPTS = "failed_attempts"
        private const val KEY_LOCKOUT_UNTIL = "lockout_until"

        private const val MAX_ATTEMPTS_BEFORE_LOCKOUT = 5
        private const val BASE_COOLDOWN_SECONDS = 30L
        private const val MAX_COOLDOWN_SECONDS = 600L
    }
}
