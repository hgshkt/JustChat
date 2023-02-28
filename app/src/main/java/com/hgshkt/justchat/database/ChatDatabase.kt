package com.hgshkt.justchat.database

import com.google.firebase.database.ValueEventListener
import com.hgshkt.justchat.models.Chat

interface ChatDatabase {

    suspend fun addChat(chat: Chat)

    suspend fun getChatById(id: String): Chat?

    suspend fun addMessageToChat(chatId: String, messageId: String, time: String)

    suspend fun addChatMessagesChangeListener(chatId: String, listener: ValueEventListener)
}