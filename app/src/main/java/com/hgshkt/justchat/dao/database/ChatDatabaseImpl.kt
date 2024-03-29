package com.hgshkt.justchat.dao.database

import com.google.firebase.database.*
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.tasks.await

class ChatDatabaseImpl : ChatDatabase {
    private val path = "chats"

    private val messagesKey = "messagesHashMap"
    private val lastMessageTimeKey = "lastMessageTime"
    private val lastMessageAuthorKey = "lastMessageAuthorFid"
    private val avatarUriKey = "avatarUri"
    private val lastMessageTextKey = "lastMessageText"

    private var dbRef = FirebaseDatabase.getInstance().getReference(path)

    override suspend fun addChat(chat: Chat) {
        dbRef.child(chat.id).setValue(chat)
    }

    override suspend fun getChatById(id: String): Chat {
        return dbRef.child(id).get().await().getValue(Chat::class.java)!!
    }

    override suspend fun getChat(chatId: String, event: (chat: Chat?) -> Unit) {
        dbRef.child(chatId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                event(snapshot.getValue(Chat::class.java))
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override suspend fun addMessageIdToChat(chatId: String, messageId: String, time: String) {
        dbRef.child(chatId).child(messagesKey).child(time).setValue(messageId)
    }

    override fun updateLastMessageTime(id: String, time: String) {
        dbRef.child(id).child(lastMessageTimeKey).setValue(time)
    }

    override fun updateLastMessageAuthorFid(id: String, fid: String) {
        dbRef.child(id).child(lastMessageAuthorKey).setValue(fid)
    }

    override fun updateLastMessageText(id: String, lastMessageText: String) {
        dbRef.child(id).child(lastMessageTextKey).setValue(lastMessageText)
    }

    override suspend fun addChatMessagesChangeListener(
        chatId: String,
        listener: ChildEventListener
    ) {
        dbRef.child(chatId).child(messagesKey).addChildEventListener(listener)
    }
    override suspend fun updateChatAvatar(chatId: String, avatarUri: String) {
        dbRef.child(chatId).child(avatarUriKey).setValue(avatarUri)
    }
}