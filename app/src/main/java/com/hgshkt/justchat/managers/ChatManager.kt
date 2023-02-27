package com.hgshkt.justchat.managers

import android.content.Context
import com.hgshkt.justchat.adapters.MessagesAdapter
import com.hgshkt.justchat.controllers.MessageController
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class ChatManager(val chat: Chat, val context: Context) {

    var adapter: MessagesAdapter
    private lateinit var messageList: List<Message>

    private val messageNumber = 10
    private val messageController = MessageController()

    init {
        runBlocking(Dispatchers.IO) {
            loadMessages()
            adapter = MessagesAdapter(context, messageList)
        }
    }

    fun sendMessage(message: Message) {
        messageController.create(message)
    }

    private suspend fun loadMessages() {
        messageList = mutableListOf()
        if (chat.messagesId.isEmpty()) return

        val messagesId = chat.messagesId
        for (i in 0 until messageNumber) {
            val message = messageController.getMessage(messagesId[i])
            (messageList as MutableList).add(message)
        }
    }
}