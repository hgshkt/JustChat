package com.hgshkt.justchat.dao.database

import com.google.firebase.database.ChildEventListener
import com.hgshkt.justchat.models.Chat

interface ChatDatabase {

    suspend fun addChat(chat: Chat)

    suspend fun getChatById(id: String): Chat?

    suspend fun getChat(chatId: String, event: (chat: Chat?) -> Unit)

    suspend fun addMessageIdToChat(chatId: String, messageId: String, time: String)

    fun updateLastMessageTime(id: String, time: String)

    fun updateLastMessageAuthorFid(id: String, fid: String)

    fun updateLastMessageText(id: String, lastMessageText: String)

    suspend fun addChatMessagesChangeListener(chatId: String, listener: ChildEventListener)

    suspend fun updateChatAvatar(chatId: String, avatarUri: String)
}