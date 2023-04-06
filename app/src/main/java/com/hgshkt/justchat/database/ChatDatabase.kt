package com.hgshkt.justchat.database

import com.google.firebase.database.ChildEventListener
import com.hgshkt.justchat.models.Chat

interface ChatDatabase {

    suspend fun addChat(chat: Chat)

    suspend fun getChatById(id: String): Chat?

    suspend fun getChat(chatId: String, event: (chat: Chat?) -> Unit)

    suspend fun addMessageToChat(chatId: String, messageId: String, time: String)

    suspend fun updateLastMessageTime(id: String, time: String)

    suspend fun addChatMessagesChangeListener(chatId: String, listener: ChildEventListener)

    suspend fun updateChatAvatar(chatId: String, avatarUri: String)
}