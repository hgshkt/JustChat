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

        chat.lastMessageTime = message.time
        chat.lastMessageAuthorFid = message.authorFid
        chat.lastMessageText = message.text
        chat.messagesHashMap[message.time] = message.id
        chatDao.updateChat(chat)
    }

    fun leave() {
        val user = currentUser!!
        user.chatIdList.remove(chat.id)
        userDao.updateUser(user)

        chat.membersFid.remove(user.fid)
        chatDao.updateChat(chat)
    }
}