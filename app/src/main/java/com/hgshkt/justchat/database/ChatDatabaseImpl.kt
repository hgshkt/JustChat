package com.hgshkt.justchat.database

import com.google.firebase.database.FirebaseDatabase
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.tasks.await

class ChatDatabaseImpl : ChatDatabase {
    private val path = "chats"
    private val messagesKey = "messagesId"
    var dbRef = FirebaseDatabase.getInstance().getReference(path)

    override suspend fun addChat(chat: Chat) {
        dbRef.child(chat.id).setValue(chat)
    }

    override suspend fun getChatById(id: String): Chat {
        return dbRef.child(id).get().await().getValue(Chat::class.java)!!
    }

    override suspend fun addMessageToChat(chatId: String, messageId: String, time: String) {
        dbRef.child(chatId).child(messagesKey).child(time).setValue(messageId)
    }
}