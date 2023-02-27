package com.hgshkt.justchat.database

import com.hgshkt.justchat.models.Chat

interface ChatDatabase {

    suspend fun addChat(chat: Chat)

    suspend fun getChatById(id: String): Chat?

    suspend fun addMessageToChat(chatId: String, messageId: String, time: String)
}