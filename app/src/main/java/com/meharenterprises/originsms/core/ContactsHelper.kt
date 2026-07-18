package com.meharenterprises.originsms.core

import android.content.Context
import android.provider.ContactsContract

/**
 * Resolves a phone number to a display name / photo URI via the system Contacts provider.
 * Falls back to the raw number when no contact match is found.
 */
class ContactsHelper(private val context: Context) {

    data class ContactInfo(val displayName: String, val photoUri: String?)

    fun resolve(rawNumber: String): ContactInfo {
        val key = normalize(rawNumber)
        // Check process-wide static cache first — avoids repeated ContentProvider
        // queries when multiple ContactsHelper instances are created (e.g. once
        // per Repository instance which was once per ViewModel init).
        staticCache[key]?.let { return it }

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

        staticCache[key] = info
        return info
    }

    fun clearCache() = staticCache.clear()

    /**
     * Returns every phone-number entry in the device's Contacts provider,
     * sorted by display name. Used by the new-message contact picker.
     * Each contact with multiple numbers produces one entry per number,
     * since a conversation thread is keyed by a single address.
     */
    fun getAllContactsWithNumbers(): List<PickableContact> {
        val results = mutableListOf<PickableContact>()
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        )

        try {
            context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null, null,
                "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"
            )?.use { cursor ->
                val nameIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val numberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val photoIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
                val idIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)

                while (cursor.moveToNext()) {
                    val name = if (nameIdx >= 0) cursor.getString(nameIdx) else null
                    val number = if (numberIdx >= 0) cursor.getString(numberIdx) else null
                    if (number.isNullOrBlank()) continue

                    results.add(
                        PickableContact(
                            contactId = if (idIdx >= 0) cursor.getLong(idIdx) else -1L,
                            displayName = name ?: number,
                            phoneNumber = number,
                            photoUri = if (photoIdx >= 0) cursor.getString(photoIdx) else null
                        )
                    )
                }
            }
        } catch (_: SecurityException) {
            // No READ_CONTACTS permission — caller gets an empty list and
            // falls back to manual number entry only.
        }
        return results
    }

    data class PickableContact(
        val contactId: Long,
        val displayName: String,
        val phoneNumber: String,
        val photoUri: String?
    )

    companion object {
        // Process-wide cache — survives across ContactsHelper instances so
        // repeated number lookups hit memory instead of ContentProvider.
        private val staticCache = java.util.concurrent.ConcurrentHashMap<String, ContactInfo>()

        fun normalize(number: String): String = number.filter { it.isDigit() || it == '+' }
    }
}
