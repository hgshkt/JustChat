package com.hgshkt.justchat.creators

import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.database.ChatDatabase
import com.hgshkt.justchat.database.ChatDatabaseImpl
import com.hgshkt.justchat.database.UserDatabase
import com.hgshkt.justchat.database.UserDatabaseImpl
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatCreator {

    private val chatDatabase: ChatDatabase = ChatDatabaseImpl()
    private val userDatabase: UserDatabase = UserDatabaseImpl()

    fun createChat(chatName: String, membersId: List<String>): Chat {
        val chat = Chat(chatName, membersId)

        CoroutineScope(Dispatchers.IO).launch {
            launch {
                val currentUser = CurrentUser.get()
                userDatabase.addChatToUserChatList(currentUser.fid, chat)
            }
            chatDatabase.addChat(chat)
        }
        CoroutineScope(Dispatchers.Default).launch {
            for (userId in membersId) {
                CoroutineScope(Dispatchers.IO).launch {
                    userDatabase.addChatToUserChatList(userId, chat)
                }
            }
        }
        return chat
    }
}