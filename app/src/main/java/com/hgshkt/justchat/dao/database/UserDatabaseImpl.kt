package com.hgshkt.justchat.dao.database

import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.tasks.await

class UserDatabaseImpl : UserDatabase {

    private val path = "users"
    private val chatIdListKey = "chatIdMap"
    private val sentInvitesKey = "sentInvites"
    private val gottenInvitesKey = "gottenInvites"
    private val friendListKey = "friendList"


    private var dbRef = FirebaseDatabase.getInstance().getReference(path)

    override suspend fun addUser(user: User) {
        dbRef.child(user.fid).setValue(user)
    }

    override suspend fun deleteUser(fid: String) {
        dbRef.child(fid).removeValue()
    }

    override suspend fun getUserByFID(fid: String): User? {
        return dbRef.child(fid).get().await().getValue(User::class.java)
    }

    override suspend fun getAllUsers(): HashMap<String, User>? {
        return dbRef.get().await().getValue<HashMap<String, User>>()
    }
    override suspend fun observeChatList(fid: String, event: (snapshot: DataSnapshot) -> Unit) {
        dbRef.child(fid).child(chatIdListKey).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                event(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun updateChatIdList(fid: String, chatList: List<String>) {
        dbRef.child(fid)
            .child(chatIdListKey)
            .setValue(chatList)
    }

    override suspend fun getSentInviteList(fid: String): List<String>? {
        return dbRef.child(fid)
            .child(sentInvitesKey).get().await().getValue<List<String>>()
    }

    override suspend fun updateSentInviteList(fid: String, sentInvites: List<String>) {
        dbRef.child(fid).child(sentInvitesKey).setValue(sentInvites)
    }

    override suspend fun getReceivedInviteList(fid: String): List<String>? {
        return dbRef.child(fid)
            .child(gottenInvitesKey).get().await().getValue<List<String>>()
    }

    override suspend fun updateReceivedInviteList(fid: String, receivedInvites: List<String>) {
        dbRef.child(fid).child(gottenInvitesKey).setValue(receivedInvites)
    }

    override suspend fun getFriendList(fid: String): List<String>? {
        return dbRef.child(fid)
            .child(friendListKey).get().await().getValue<List<String>>()
    }

    override suspend fun updateFriendList(fid: String, friendList: List<String>) {
        dbRef.child(fid).child(friendListKey).setValue(friendList)
    }

    override suspend fun addChatListChangeListener(
        fid: String,
        listener: ChildEventListener
    ) {
        dbRef.child(fid).child(chatIdListKey).addChildEventListener(listener)
    }

    override suspend fun updateChatLastMessageTime(fid: String, chatId: String, chatLastMessageTime: String) {
        dbRef.child(fid).child(chatIdListKey).child(chatId).setValue(chatLastMessageTime)
    }

    override suspend fun addUserChangeListener(fid: String, event: (snapshot: DataSnapshot) -> Unit) {
        dbRef.child(fid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                event(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}