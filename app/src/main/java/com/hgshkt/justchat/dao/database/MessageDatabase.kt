package com.hgshkt.justchat.dao.database

import com.hgshkt.justchat.models.Message

interface MessageDatabase {

    suspend fun addMessage(message: Message)

    suspend fun getMessage(id: String): Message
}