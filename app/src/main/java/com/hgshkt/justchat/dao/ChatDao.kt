package com.hgshkt.justchat.dao

import com.hgshkt.justchat.database.ChatDatabase
import com.hgshkt.justchat.database.ChatDatabaseImpl
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatDao {

    private val db: ChatDatabase = ChatDatabaseImpl()

    fun updateChatLastMessageTime(chatId: String, time: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.updateLastMessageTime(chatId, time)
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
            db.addMessageToChat(chatId, messageId, time)
        }
    }
}