package com.hgshkt.justchat.controllers

import com.google.firebase.database.ValueEventListener
import com.hgshkt.justchat.database.ChatDatabase
import com.hgshkt.justchat.database.ChatDatabaseImpl
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ChatController {

    private val db: ChatDatabase = ChatDatabaseImpl()

    suspend fun getChatList(idList: List<String>): List<Chat> {
        val list = mutableListOf<Chat>()
        for (id in idList) {
            val chat = db.getChatById(id)
            if (chat != null)
                list.add(chat)
        }
        return list
    }

    fun addMessagesChangedListener(chatId: String, listener: ValueEventListener) {
        runBlocking(Dispatchers.IO) {
            db.addChatMessagesChangeListener(chatId, listener)
        }
    }

    fun updateChatLastMessageTime(chatId: String, time: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.updateLastMessageTime(chatId, time)
        }
    }

    suspend fun getChat(id: String): Chat? {
        return db.getChatById(id)
    }

    fun addMessageToChat(chatId: String, messageId: String, time: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.addMessageToChat(chatId, messageId, time)
        }
    }
}