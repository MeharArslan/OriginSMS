package com.meharenterprises.originsms.core

import android.content.Context
import android.net.Uri
import java.io.ByteArrayOutputStream

/**
 * Builds a binary MMS PDU (M-Send.req, per OMA-WAP-MMS-ENC specification) from
 * a destination address, optional text body, and a list of attachment content
 * URIs. The resulting byte array is what SmsManager.sendMultimediaMessage
 * expects to find at the file URI passed to it — this is the same wire format
 * every default Android messaging app constructs internally.
 *
 * This is a focused, dependency-free encoder covering exactly the fields
 * needed for outgoing MMS in this app. It implements the well-known constant
 * set of headers and part-header fields required to produce a valid M-Send.req.
 */
object MmsPduBuilder {

    private const val MMS_MESSAGE_TYPE = 0x8C
    private const val MMS_TRANSACTION_ID = 0x98
    private const val MMS_VERSION = 0x8D
    private const val MMS_FROM = 0x89
    private const val MMS_TO = 0x97
    private const val MMS_CONTENT_TYPE = 0x84
    private const val MMS_MESSAGE_CLASS = 0x8A
    private const val MMS_DATE = 0x85
    private const val MMS_PRIORITY = 0x8F

    private const val MESSAGE_TYPE_SEND_REQ = 0x80
    private const val MMS_VERSION_1_3 = 0x93
    private const val MESSAGE_CLASS_PERSONAL = 0x80
    private const val PRIORITY_NORMAL = 0x80

    fun buildSendRequest(context: Context, destinationAddress: String, text: String, attachmentUris: List<Uri>): ByteArray {
        val output = ByteArrayOutputStream()

        writeHeader(output, MMS_MESSAGE_TYPE, MESSAGE_TYPE_SEND_REQ)
        writeTransactionId(output)
        writeHeader(output, MMS_VERSION, MMS_VERSION_1_3)
        writeDateHeader(output)
        writeEncodedStringHeader(output, MMS_FROM, "insert-address-token")
        writeEncodedStringHeader(output, MMS_TO, destinationAddress)
        writeHeader(output, MMS_MESSAGE_CLASS, MESSAGE_CLASS_PERSONAL)
        writeHeader(output, MMS_PRIORITY, PRIORITY_NORMAL)

        val parts = buildParts(context, text, attachmentUris)
        writeContentTypeMultipartMixed(output)
        writeBody(output, parts)

        return output.toByteArray()
    }

    private fun writeHeader(output: ByteArrayOutputStream, fieldCode: Int, value: Int) {
        output.write(fieldCode)
        output.write(value)
    }

    private fun writeTransactionId(output: ByteArrayOutputStream) {
        output.write(MMS_TRANSACTION_ID)
        val txId = "T${System.currentTimeMillis()}"
        writeNullTerminatedString(output, txId)
    }

    private fun writeDateHeader(output: ByteArrayOutputStream) {
        output.write(MMS_DATE)
        val seconds = System.currentTimeMillis() / 1000L
        val bytes = longToMinimalBytes(seconds)
        output.write(bytes.size)
        output.write(bytes)
    }

    private fun writeEncodedStringHeader(output: ByteArrayOutputStream, fieldCode: Int, value: String) {
        output.write(fieldCode)
        writeNullTerminatedString(output, value)
    }

    private fun writeContentTypeMultipartMixed(output: ByteArrayOutputStream) {
        output.write(MMS_CONTENT_TYPE)
        val contentType = "application/vnd.wap.multipart.mixed"
        writeNullTerminatedString(output, contentType)
    }

    private fun writeNullTerminatedString(output: ByteArrayOutputStream, value: String) {
        output.write(value.toByteArray(Charsets.UTF_8))
        output.write(0x00)
    }

    private fun longToMinimalBytes(value: Long): ByteArray {
        if (value == 0L) return byteArrayOf(0)
        var v = value
        val bytes = mutableListOf<Byte>()
        while (v > 0) {
            bytes.add(0, (v and 0xFF).toByte())
            v = v ushr 8
        }
        return bytes.toByteArray()
    }

    private fun writeUintVar(output: ByteArrayOutputStream, value: Int) {
        var v = value
        val chunks = mutableListOf<Int>()
        chunks.add(v and 0x7F)
        v = v ushr 7
        while (v > 0) {
            chunks.add(0, (v and 0x7F) or 0x80)
            v = v ushr 7
        }
        for (i in chunks.indices) {
            val isLast = i == chunks.size - 1
            output.write(if (isLast) chunks[i] else chunks[i] or 0x80)
        }
    }

    private data class MmsPartData(val contentType: String, val fileName: String, val bytes: ByteArray)

    private fun buildParts(context: Context, text: String, attachmentUris: List<Uri>): List<MmsPartData> {
        val parts = mutableListOf<MmsPartData>()

        if (text.isNotBlank()) {
            parts.add(MmsPartData("text/plain", "text_0.txt", text.toByteArray(Charsets.UTF_8)))
        }

        attachmentUris.forEachIndexed { index, uri ->
            val contentType = context.contentResolver.getType(uri) ?: "application/octet-stream"
            val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() } ?: ByteArray(0)
            val extension = contentType.substringAfter("/", "bin")
            parts.add(MmsPartData(contentType, "media_$index.$extension", bytes))
        }

        return parts
    }

    private fun writeBody(output: ByteArrayOutputStream, parts: List<MmsPartData>) {
        writeUintVar(output, parts.size)

        for (part in parts) {
            val headerBytes = ByteArrayOutputStream().apply {
                write(part.contentType.toByteArray(Charsets.UTF_8))
                write(0x00)
                write(0x83)
                writeNullTerminatedStringTo(this, part.fileName)
            }.toByteArray()

            val headerLengthBytes = ByteArrayOutputStream().also { writeUintVar(it, headerBytes.size) }.toByteArray()
            val dataLengthBytes = ByteArrayOutputStream().also { writeUintVar(it, part.bytes.size) }.toByteArray()

            output.write(headerLengthBytes)
            output.write(dataLengthBytes)
            output.write(headerBytes)
            output.write(part.bytes)
        }
    }

    private fun writeNullTerminatedStringTo(output: ByteArrayOutputStream, value: String) {
        output.write(value.toByteArray(Charsets.UTF_8))
        output.write(0x00)
    }
}
