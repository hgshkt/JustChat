package com.hgshkt.justchat.database

import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.tasks.await

class UserDatabaseImpl : UserDatabase {

    private val path = "users"
    private val chatIdMapKey = "chatIdMap"
    private val sentInvitesKey = "sentInvites"
    private val gottenInvitesKey = "gottenInvites"
    private val friendListKey = "friendList"


    private var dbRef = FirebaseDatabase.getInstance().getReference(path)

    override suspend fun addUser(user: User) {
        dbRef.child(user.fid).setValue(user)
    }

    override suspend fun getUserByFID(fid: String): User? {
        return dbRef.child(fid).get().await().getValue(User::class.java)
    }

    override suspend fun addOnValueChangeListener(fid: String, event: (snapshot: DataSnapshot) -> Unit) {
        dbRef.child(fid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                event(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override suspend fun getAllUsers(): HashMap<String, User>? {
        return dbRef.get().await().getValue<HashMap<String, User>>()
    }

    override suspend fun addChatToUserChatList(fid: String, chat: Chat) {
        dbRef.child(fid)
            .child(chatIdMapKey)
            .child(chat.lastMessageTime)
            .setValue(chat.id)
    }

    override suspend fun updateSentInvites(fid: String, sentInvites: List<String>) {
        dbRef.child(fid).child(sentInvitesKey).setValue(sentInvites)
    }

    override suspend fun updateReceivedInvites(fid: String, receivedInvites: List<String>) {
        dbRef.child(fid).child(gottenInvitesKey).setValue(receivedInvites)
    }

    override suspend fun updateFriendList(fid: String, friendList: List<String>) {
        dbRef.child(fid).child(friendListKey).setValue(friendList)
    }

    override suspend fun getSentInviteList(fid: String): List<String>? {
        return dbRef.child(fid)
            .child(sentInvitesKey).get().await().getValue<List<String>>()
    }

    override suspend fun getReceivedInviteList(fid: String): List<String>? {
        return dbRef.child(fid)
            .child(gottenInvitesKey).get().await().getValue<List<String>>()
    }

    override suspend fun getFriendList(fid: String): List<String>? {
        return dbRef.child(fid)
            .child(friendListKey).get().await().getValue<List<String>>()
    }

    override suspend fun addChatListChangeListener(
        fid: String,
        listener: ChildEventListener
    ) {
        dbRef.child(fid).child(chatIdMapKey).addChildEventListener(listener)
    }
}