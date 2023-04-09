package com.hgshkt.justchat.managers

import com.hgshkt.justchat.auth.currentUser
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

        chatDao.updateChatValues(
            chatId = chat.id,
            lastMessageTime = message.time,
            lastMessageAuthorFid = message.authorFid,
            lastMessageText = message.text
        )
        chatDao.addMessageToChat(chat.id, message.id, message.time)
    }

    fun leave() {
        val user = currentUser!!
        user.chatIdList.remove(chat.id)
        userDao.updateUser(user)

        chat.membersFid.remove(user.fid)
        chatDao.updateChat(chat)
    }
}