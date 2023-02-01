package com.hgshkt.justchat.database

import com.google.firebase.database.FirebaseDatabase
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.tasks.await

class ChatDatabaseImpl : ChatDatabase {
    private val path = "chats"
    var dbRef = FirebaseDatabase.getInstance().getReference(path)

    override suspend fun addChat(chat: Chat) {
        dbRef.child(chat.id).setValue(chat)
    }

    override suspend fun getChatById(id: String): Chat {
        return dbRef.child(id).get().await().getValue(Chat::class.java)!!
    }

    override suspend fun getChatList(idList: List<String>): List<Chat> {
        val list = mutableListOf<Chat>()
            for (id in idList) {
                val chat = dbRef.child(id).get().await().getValue(Chat::class.java)!!
                list.add(chat)
            }
        return list
    }
}