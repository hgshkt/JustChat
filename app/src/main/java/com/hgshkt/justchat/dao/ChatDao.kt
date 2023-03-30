package com.hgshkt.justchat.dao

import com.google.firebase.database.ValueEventListener
import com.hgshkt.justchat.database.ChatDatabase
import com.hgshkt.justchat.database.ChatDatabaseImpl
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ChatDao {

    private val db: ChatDatabase = ChatDatabaseImpl()

    fun getChatList(idList: List<String>): List<Chat> = runBlocking {
        val list = mutableListOf<Chat>()
        for (id in idList) {
            val chat = db.getChatById(id)
            if (chat != null)
                list.add(chat)
        }
        return@runBlocking list
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

    fun getChat(id: String): Chat? = runBlocking(Dispatchers.IO) {
        return@runBlocking db.getChatById(id)
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