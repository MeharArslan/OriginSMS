package com.meharenterprises.originsms.core

import android.content.Context
import android.provider.ContactsContract

/**
 * Resolves a phone number to a display name / photo URI via the system Contacts provider.
 * Falls back to the raw number when no contact match is found.
 */
class ContactsHelper(private val context: Context) {

    data class ContactInfo(val displayName: String, val photoUri: String?)

    private val cache = HashMap<String, ContactInfo>()

    fun resolve(rawNumber: String): ContactInfo {
        val key = normalize(rawNumber)
        cache[key]?.let { return it }

        val uri = android.net.Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            android.net.Uri.encode(rawNumber)
        )
        val projection = arrayOf(
            ContactsContract.PhoneLookup.DISPLAY_NAME,
            ContactsContract.PhoneLookup.PHOTO_URI
        )

        val info = try {
            context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIdx = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)
                    val photoIdx = cursor.getColumnIndex(ContactsContract.PhoneLookup.PHOTO_URI)
                    ContactInfo(
                        displayName = if (nameIdx >= 0) cursor.getString(nameIdx) ?: rawNumber else rawNumber,
                        photoUri = if (photoIdx >= 0) cursor.getString(photoIdx) else null
                    )
                } else null
            }
        } catch (_: SecurityException) {
            null
        } ?: ContactInfo(displayName = rawNumber, photoUri = null)

        cache[key] = info
        return info
    }

    fun clearCache() = cache.clear()

    companion object {
        fun normalize(number: String): String = number.filter { it.isDigit() || it == '+' }
    }
}
