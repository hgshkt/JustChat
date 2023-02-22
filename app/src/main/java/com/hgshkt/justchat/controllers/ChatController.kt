package com.hgshkt.justchat.controllers

import com.hgshkt.justchat.database.ChatDatabase
import com.hgshkt.justchat.database.ChatDatabaseImpl
import com.hgshkt.justchat.models.Chat

class ChatController {

    val chatDB: ChatDatabase = ChatDatabaseImpl()

    suspend fun getChatListByIdList(idList: List<String>): List<Chat> {
        val list = mutableListOf<Chat>()
        for (id in idList) {
            val chat = chatDB.getChatById(id)
            list.add(chat)
        }
        return list
    }
}