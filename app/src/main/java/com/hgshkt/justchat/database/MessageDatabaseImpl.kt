package com.hgshkt.justchat.database

import com.google.firebase.database.FirebaseDatabase
import com.hgshkt.justchat.models.Message
import kotlinx.coroutines.tasks.await

class MessageDatabaseImpl : MessageDatabase {

    private val path = "messages"
    var dbRef = FirebaseDatabase.getInstance().getReference(path)

    override suspend fun addMessage(message: Message) {
        dbRef.child(message.id).setValue(message)
    }

    override suspend fun getMessage(id: String): Message {
        return dbRef.child(id).get().await().getValue(Message::class.java)!!
    }
}