package com.hgshkt.justchat.managers

import com.hgshkt.justchat.auth.currentUser
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
        chatDao.addMessageToChat(
            chatId = chat.id,
            messageId = message.id,
            time = message.date.toString()
        )

        chat.membersFid.forEach {
            CoroutineScope(Dispatchers.IO).launch {
                val user = userDao.getUserByFID(it)!!
                user.chatIdMap.remove(chat.lastMessageTime)
                user.chatIdMap[message.date.toString()] = chat.id
                userDao.updateUser(user)
            }
        }

        chatDao.updateChatLastMessage(chat.id, message)
    }

    fun leave() {
        val user = currentUser!!
        user.chatIdMap.entries.removeIf { it.value == chat.id }
        userDao.updateUser(user)

        chat.membersFid -= user.fid
        chatDao.updateChat(chat)
    }
}