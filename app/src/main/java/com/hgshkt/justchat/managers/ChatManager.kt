package com.hgshkt.justchat.managers

import com.hgshkt.justchat.dao.ChatDao
import com.hgshkt.justchat.dao.MessageDao
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.Message

class ChatManager(
    var chat: Chat
) {
    private val messageDao = MessageDao()
    private val chatDao = ChatDao()
    private val userDao = UserDao()

    fun sendMessage(message: Message) {
        messageDao.create(message)
        chatDao.addMessageToChat(
            chatId = chat.id,
            messageId = message.id,
            time = message.date.toString()
        )

        chat.membersFid.forEach {
            val user = userDao.getUserByFID(it)!!
            user.chatIdMap.remove(chat.lastMessageTime)
            user.chatIdMap[message.date.toString()] = chat.id
            userDao.updateUser(user)
        }

        chatDao.updateChatLastMessage(chat.id, message)
    }
}