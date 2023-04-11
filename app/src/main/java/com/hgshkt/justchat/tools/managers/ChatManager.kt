package com.hgshkt.justchat.tools.managers

import com.hgshkt.justchat.tools.auth.currentUser
import com.hgshkt.justchat.dao.ChatDao
import com.hgshkt.justchat.dao.MessageDao
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        chat.membersFid.forEach {
            CoroutineScope(Dispatchers.IO).launch {
                userDao.updateChatLastMessageTime(it, chat.id, chat.lastMessageTime)
            }
        }
    }

    fun leave() {
        val user = currentUser!!
        user.chatIdMap.remove(chat.id)
        userDao.updateUser(user)

        chat.membersFid.remove(user.fid)
        chatDao.updateChat(chat)
    }
}