package com.hgshkt.justchat.controllers

import com.hgshkt.justchat.database.ChatDatabase
import com.hgshkt.justchat.database.ChatDatabaseImpl
import com.hgshkt.justchat.models.Chat

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

    suspend fun getChat(id: String): Chat? {
        return db.getChatById(id)
    }
}