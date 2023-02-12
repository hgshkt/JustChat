package com.hgshkt.justchat.database

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.tasks.await

class UserDatabaseImpl : UserDatabase {

    private val path = "users"
    private val chatIdListKey = "chatIdList"
    private val sentInvitesKey = "sentInvites"
    private val gottenInvitesKey = "gottenInvites"
    private val friendListKey = "friendList"


    private var dbRef = FirebaseDatabase.getInstance().getReference(path)

    override suspend fun addUser(user: User) {
        dbRef.child(user.firebaseId).setValue(user)
    }

    override suspend fun getUserById(id: String): User? {
        return dbRef.child(id).get().await().getValue(User::class.java)
    }

    override suspend fun getAllUsers(): HashMap<String, User>? {
        return dbRef.get().await().getValue<HashMap<String, User>>()
    }

    override suspend fun addChatToUserChatList(userId: String, chat: Chat) {
        dbRef.child(userId)
            .child(chatIdListKey)
            .child(chat.lastMessageTime)
            .setValue(chat.id)
    }

    override suspend fun updateSentInvites(userId: String, sentInvites: List<String>) {
        dbRef.child(userId).child(sentInvitesKey).setValue(sentInvites)
    }

    override suspend fun updateReceivedInvites(userId: String, receivedInvites: List<String>) {
        dbRef.child(userId).child(gottenInvitesKey).setValue(receivedInvites)
    }

    override suspend fun updateFriendList(userId: String, friendList: List<String>) {
        dbRef.child(userId).child(friendListKey).setValue(friendList)
    }

    override suspend fun getSentInviteList(userId: String): List<String>? {
        return dbRef.child(userId)
            .child(sentInvitesKey).get().await().getValue<List<String>>()
    }

    override suspend fun getReceivedInviteList(userId: String): List<String>? {
        return dbRef.child(userId)
            .child(gottenInvitesKey).get().await().getValue<List<String>>()
    }

    override suspend fun getFriendList(userId: String): List<String>? {
        return dbRef.child(userId)
            .child(friendListKey).get().await().getValue<List<String>>()
    }
}