package com.hgshkt.justchat.creators

import com.hgshkt.justchat.database.MessageDatabaseImpl
import com.hgshkt.justchat.models.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageCreator {

    private val db = MessageDatabaseImpl()

    fun create(message: Message) {
        CoroutineScope(Dispatchers.IO).launch {
            db.addMessage(message)
        }
    }
}