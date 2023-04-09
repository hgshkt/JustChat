package com.hgshkt.justchat.dao

import com.hgshkt.justchat.database.MessageDatabase
import com.hgshkt.justchat.database.MessageDatabaseImpl
import com.hgshkt.justchat.models.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MessageDao {
    private val db: MessageDatabase = MessageDatabaseImpl()

    fun getMessage(id: String): Message = runBlocking(Dispatchers.IO) {
        return@runBlocking db.getMessage(id)
    }

    fun create(message: Message) {
        CoroutineScope(Dispatchers.IO).launch {
            db.addMessage(message)
        }
    }
}