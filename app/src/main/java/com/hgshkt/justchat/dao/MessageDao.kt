package com.hgshkt.justchat.dao

import com.hgshkt.justchat.database.MessageDatabase
import com.hgshkt.justchat.database.MessageDatabaseImpl
import com.hgshkt.justchat.models.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageDao {
    private val db: MessageDatabase = MessageDatabaseImpl()

    suspend fun getMessage(id: String): Message {
        return db.getMessage(id)
    }

    fun create(message: Message) {
        CoroutineScope(Dispatchers.IO).launch {
            db.addMessage(message)
        }
    }

    suspend fun getMessages(messagesIdList: List<String>): List<Message> {
        val list = mutableListOf<Message>()
        messagesIdList.forEach {
            val massage = db.getMessage(it)
            list.add(massage)
        }
        return list
    }
}