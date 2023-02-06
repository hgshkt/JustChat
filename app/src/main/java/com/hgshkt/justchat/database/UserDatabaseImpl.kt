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


    var dbRef = FirebaseDatabase.getInstance().getReference(path)

    override suspend fun addUser(user: User) {
        dbRef.child(user.firebaseId).setValue(user)
    }

    override suspend fun getUserById(id: String): User {
        return dbRef.child(id).get().await().getValue(User::class.java)!!
    }

    override suspend fun addChatToUserChatList(userId: String, chat: Chat) {
        dbRef.child(userId)
            .child(chatIdListKey)
            .child(chat.lastMessageTime)
            .setValue(chat.id)
    }

    override suspend fun sendInvite(senderId: String, recipientId: String) {
        dbRef.child(senderId)
            .child(sentInvitesKey)
            .child(recipientId)
            .setValue("")
    }

    override suspend fun getInvite(senderId: String, recipientId: String) {
        dbRef.child(recipientId)
            .child(gottenInvitesKey)
            .child(senderId)
            .setValue("")
    }

    override suspend fun removeUserFromSentInvites(senderId: String, recipientId: String) {
        dbRef.child(senderId)
            .child(sentInvitesKey)
            .child(recipientId)
            .removeValue()
    }

    override suspend fun removeUserFromGottenInvites(senderId: String, recipientId: String) {
        dbRef.child(recipientId)
            .child(gottenInvitesKey)
            .child(senderId)
            .removeValue()
    }

    override suspend fun addFriendToFriendList(userId: String, friendId: String) {
        dbRef.child(userId)
            .child(friendListKey)
            .child(friendId)
            .setValue("")
    }

    override suspend fun getSentInviteList(userId: String): HashMap<String, String> {
        return dbRef.child(userId)
            .child(sentInvitesKey).get().await().getValue<HashMap<String, String>>()!!
    }

    override suspend fun getReceivedInviteList(userId: String): HashMap<String, String> {
        return dbRef.child(userId)
            .child(gottenInvitesKey).get().await().getValue<HashMap<String, String>>()!!
    }

    override suspend fun getFriendList(userId: String): HashMap<String, String> {
        return dbRef.child(userId)
            .child(friendListKey).get().await().getValue<HashMap<String, String>>()!!
    }
}