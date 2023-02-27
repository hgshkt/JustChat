package com.hgshkt.justchat.managers

import android.content.Context
import com.hgshkt.justchat.adapters.MessagesAdapter
import com.hgshkt.justchat.controllers.ChatController
import com.hgshkt.justchat.controllers.MessageController
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class ChatManager(val chat: Chat, val context: Context) {

    var adapter: MessagesAdapter
    private lateinit var messageList: List<Message>

    private var messageNumber = 0
    private val messageNumberIncrease = 10
    private val messageController = MessageController()
    private val chatController = ChatController()

    init {
        runBlocking(Dispatchers.IO) {
            loadMessages()
            adapter = MessagesAdapter(context, messageList)
        }
    }

    fun sendMessage(message: Message) {
        messageController.create(message)
        chatController.addMessageToChat(
            chatId = chat.id,
            messageId = message.id,
            time = message.date.toString()
        )
    }

    private suspend fun loadMessages() {
        messageNumber += messageNumberIncrease
        messageList = mutableListOf()
        if (chat.messagesId.isEmpty()) return

        val messagesId = chat.messagesId.toSortedMap()
        val keys = messagesId.keys.toMutableList()
        for (i in 0 until messageNumber) { // messageNumber = 10; 0<=i<=9
            if (keys.size < messageNumber-1 && i >= keys.size) break // 0<=size<=9

            val time = keys[i]
            val id = messagesId[time]
            val message = messageController.getMessage(id!!)
            (messageList as MutableList).add(message)
        }
    }

    fun updateAdapter() {
        adapter.notifyItemInserted(messageList.lastIndex)
    }
}