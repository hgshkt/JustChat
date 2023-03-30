package com.hgshkt.justchat.managers

import com.hgshkt.justchat.dao.ChatDao
import com.hgshkt.justchat.dao.MessageDao
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.Message

class ChatManager(
    var id: String
) {
    private val messageDao = MessageDao()
    private val chatDao = ChatDao()

    fun sendMessage(message: Message) {
        messageDao.create(message)
        chatDao.addMessageToChat(
            chatId = id,
            messageId = message.id,
            time = message.date.toString()
        )
        chatDao.updateChatLastMessageTime(id, message.date.toString())
    }
}