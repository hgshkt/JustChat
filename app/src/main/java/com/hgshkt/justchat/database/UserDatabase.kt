package com.hgshkt.justchat.database

import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.User

interface UserDatabase {
    suspend fun addUser(user: User)

    suspend fun getUserById(id: String): User

    suspend fun addChatToUserChatList(userId: String, chat: Chat)
}