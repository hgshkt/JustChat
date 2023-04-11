package com.hgshkt.justchat.creators

import android.net.Uri
import com.hgshkt.justchat.dao.ChatDao
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.tools.loaders.uploadChatAvatar
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatCreator {

    private val userDao: UserDao = UserDao()
    private val chatDao: ChatDao = ChatDao()

    suspend fun createChat(chatName: String, avatarUri: Uri?, membersFID: List<String>): Chat {
        val chat = Chat(
            name = chatName,
            membersFid = membersFID.toMutableList(),
            avatarUri = avatarUri.toString()
        )

        withContext(Dispatchers.IO) {
            chatDao.updateChat(chat)
            if (avatarUri != null) {
                uploadChatAvatar(chat.id, avatarUri)
            }

            membersFID.forEach {
                withContext(Dispatchers.IO) {
                    val user = userDao.getUserByFID(it)
                    user!!.chatIdMap[chat.id] = chat.lastMessageTime
                    userDao.updateUser(user)
                }
            }
        }
        return chat
    }
}