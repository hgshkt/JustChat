package com.hgshkt.justchat.dao

import com.google.firebase.database.ChildEventListener
import com.hgshkt.justchat.database.ChatDatabase
import com.hgshkt.justchat.database.ChatDatabaseImpl
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatDao {

    private val db: ChatDatabase = ChatDatabaseImpl()

    fun updateChat(chat: Chat) {
        CoroutineScope(Dispatchers.IO).launch {
            db.addChat(chat)
        }
    }

    suspend fun getChat(id: String): Chat? {
        return db.getChatById(id)
    }

    suspend fun getChat(
        id: String,
        event: (chat: Chat?) -> Unit
    ) {
        db.getChat(id) {
            event(it)
        }
    }


    fun addMessageToChat(chatId: String, messageId: String, time: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.addMessageIdToChat(chatId, messageId, time)
        }
    }

    suspend fun addChatMessagesChangeListener(
        chatId: String,
        listener: ChildEventListener
    ) {
        db.addChatMessagesChangeListener(chatId, listener)
    }

    fun updateChatAvatar(chatId: String, avatarUri: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.updateChatAvatar(chatId, avatarUri)
        }
    }
}