package com.hgshkt.justchat.controllers

import com.hgshkt.justchat.database.MessageDatabase
import com.hgshkt.justchat.database.MessageDatabaseImpl
import com.hgshkt.justchat.models.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageController {
    private val db: MessageDatabase = MessageDatabaseImpl()

    suspend fun getMessage(id: String): Message {
        return db.getMessage(id)
    }

    fun create(message: Message) {
        CoroutineScope(Dispatchers.IO).launch {
            db.addMessage(message)
        }
    }
}