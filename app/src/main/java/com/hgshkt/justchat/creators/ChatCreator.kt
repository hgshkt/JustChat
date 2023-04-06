package com.hgshkt.justchat.creators

import android.net.Uri
import com.hgshkt.justchat.database.ChatDatabase
import com.hgshkt.justchat.database.ChatDatabaseImpl
import com.hgshkt.justchat.database.UserDatabase
import com.hgshkt.justchat.database.UserDatabaseImpl
import com.hgshkt.justchat.loaders.uploadChatAvatar
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class ChatCreator {

    private val chatDatabase: ChatDatabase = ChatDatabaseImpl()
    private val userDatabase: UserDatabase = UserDatabaseImpl()

    suspend fun createChat(chatName: String, avatarUri: Uri?, membersFID: List<String>): Chat {
        val chat = Chat(chatName, membersFID)

        chat.avatarUri = avatarUri.toString()

        withContext(Dispatchers.IO) {
            chatDatabase.addChat(chat)
            if (avatarUri != null) {
                uploadChatAvatar(chat.id, avatarUri)
            }

            val memberUpdates = membersFID.map {
                async {
                    userDatabase.addChatToUserChatList(it, chat)
                }
            }
            memberUpdates.awaitAll()
        }
        return chat
    }
}