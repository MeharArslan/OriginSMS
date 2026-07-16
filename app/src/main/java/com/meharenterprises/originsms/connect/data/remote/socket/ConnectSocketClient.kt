package com.meharenterprises.originsms.connect.data.remote.socket

import android.util.Log
import com.google.gson.Gson
import com.meharenterprises.originsms.connect.data.remote.api.MessageDto
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

enum class SocketState { CONNECTING, CONNECTED, DISCONNECTED, ERROR }

data class TypingEvent(val conversationId: String, val userId: String, val isTyping: Boolean)
data class OnlineEvent(val userId: String, val isOnline: Boolean, val lastSeen: Long)
data class MessageStatusEvent(val messageId: String, val status: String)

@Singleton
class ConnectSocketClient @Inject constructor(
    private val gson: Gson
) {
    private var socket: Socket? = null

    private val _state = MutableStateFlow(SocketState.DISCONNECTED)
    val state: StateFlow<SocketState> = _state

    private val _newMessage = MutableSharedFlow<MessageDto>(extraBufferCapacity = 64)
    val newMessage: SharedFlow<MessageDto> = _newMessage

    private val _typing = MutableSharedFlow<TypingEvent>(extraBufferCapacity = 32)
    val typing: SharedFlow<TypingEvent> = _typing

    private val _online = MutableSharedFlow<OnlineEvent>(extraBufferCapacity = 32)
    val online: SharedFlow<OnlineEvent> = _online

    private val _messageStatus = MutableSharedFlow<MessageStatusEvent>(extraBufferCapacity = 32)
    val messageStatus: SharedFlow<MessageStatusEvent> = _messageStatus

    fun connect(serverUrl: String, token: String) {
        if (socket?.connected() == true) return
        try {
            val opts = IO.Options.builder()
                .setAuth(mapOf("token" to token))
                .setReconnection(true)
                .setReconnectionAttempts(Int.MAX_VALUE)
                .setReconnectionDelay(2000)
                .build()

            socket = IO.socket(URI.create(serverUrl), opts).apply {
                on(Socket.EVENT_CONNECT) {
                    _state.value = SocketState.CONNECTED
                    Log.d("ConnectSocket", "Connected")
                }
                on(Socket.EVENT_DISCONNECT) {
                    _state.value = SocketState.DISCONNECTED
                    Log.d("ConnectSocket", "Disconnected")
                }
                on(Socket.EVENT_CONNECT_ERROR) {
                    _state.value = SocketState.ERROR
                    Log.e("ConnectSocket", "Error: ${it.firstOrNull()}")
                }
                on("message:new") { args ->
                    try {
                        val json = args.firstOrNull() as? JSONObject ?: return@on
                        val msg = gson.fromJson(json.toString(), MessageDto::class.java)
                        _newMessage.tryEmit(msg)
                    } catch (e: Exception) { Log.e("ConnectSocket", "message:new parse error", e) }
                }
                on("typing") { args ->
                    try {
                        val json = args.firstOrNull() as? JSONObject ?: return@on
                        _typing.tryEmit(TypingEvent(
                            conversationId = json.getString("conversationId"),
                            userId = json.getString("userId"),
                            isTyping = json.getBoolean("isTyping")
                        ))
                    } catch (_: Exception) {}
                }
                on("user:online") { args ->
                    try {
                        val json = args.firstOrNull() as? JSONObject ?: return@on
                        _online.tryEmit(OnlineEvent(
                            userId = json.getString("userId"),
                            isOnline = json.getBoolean("isOnline"),
                            lastSeen = json.optLong("lastSeen", 0L)
                        ))
                    } catch (_: Exception) {}
                }
                on("message:status") { args ->
                    try {
                        val json = args.firstOrNull() as? JSONObject ?: return@on
                        _messageStatus.tryEmit(MessageStatusEvent(
                            messageId = json.getString("messageId"),
                            status = json.getString("status")
                        ))
                    } catch (_: Exception) {}
                }
                connect()
            }
            _state.value = SocketState.CONNECTING
        } catch (e: Exception) {
            Log.e("ConnectSocket", "Failed to create socket", e)
            _state.value = SocketState.ERROR
        }
    }

    fun sendTyping(conversationId: String, isTyping: Boolean) {
        socket?.emit("typing", JSONObject(mapOf(
            "conversationId" to conversationId,
            "isTyping" to isTyping
        )))
    }

    fun joinConversation(conversationId: String) {
        socket?.emit("conversation:join", conversationId)
    }

    fun leaveConversation(conversationId: String) {
        socket?.emit("conversation:leave", conversationId)
    }

    fun disconnect() {
        socket?.disconnect()
        socket = null
        _state.value = SocketState.DISCONNECTED
    }

    fun isConnected() = socket?.connected() == true
}
