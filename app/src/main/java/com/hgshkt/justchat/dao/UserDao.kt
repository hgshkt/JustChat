package com.hgshkt.justchat.dao

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.hgshkt.justchat.tools.auth.currentUserFID
import com.hgshkt.justchat.dao.database.UserDatabase
import com.hgshkt.justchat.dao.database.UserDatabaseImpl
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDao {
    private val db: UserDatabase = UserDatabaseImpl()

    fun updateUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            db.addUser(user)
        }
    }

    suspend fun getUserByFID(fid: String): User? {
        return db.getUserByFID(fid)
    }

    suspend fun getUserById(id: String): User? {
        getAllUsers().forEach {
            if (it.id == id) return it
        }
        return null
    }

    suspend fun getAllUsers(): List<User> {
        return db.getAllUsers()?.values?.toList() ?: emptyList()
    }


    suspend fun getFriendList(fid: String): List<String> {
        return db.getFriendList(fid) ?: emptyList()
    }
    suspend fun updateSentInviteList(fid: String, friendList: List<String>) {
        db.updateSentInviteList(fid, friendList)
    }

    suspend fun getSentInviteList(fid: String): List<String> {
        return db.getSentInviteList(fid) ?: emptyList()
    }

    suspend fun updateFriendList(fid: String, friendList: List<String>) {
        db.updateFriendList(fid, friendList)
    }

    suspend fun getReceivedInviteList(fid: String): List<String> {
        return db.getReceivedInviteList(fid) ?: emptyList()
    }

    suspend fun updateReceivedInviteList(fid: String, friendList: List<String>) {
        db.updateReceivedInviteList(fid, friendList)
    }

    suspend fun observeChatMap(fid: String, event: (chatIdList: MutableMap<String, String>) -> Unit) {
        db.observeChatList(fid) {
            val list = it.value as MutableMap<String, String>?
            event(list ?: mutableMapOf())
        }
    }

    suspend fun updateChatLastMessageTime(fid: String, chatId: String, chatLastMessageTime: String) {
        db.updateChatLastMessageTime(fid, chatId, chatLastMessageTime)
    }

    fun updateChatList(fid: String, chatIdList: List<String>) {
        db.updateChatIdList(fid, chatIdList)
    }

    fun addOnValueChangeListener(fid: String, event: (snapshot: DataSnapshot) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            db.addUserChangeListener(fid, event)
        }
    }

    suspend fun addChatListChangeListener(
        listener: ChildEventListener
    ) {
        val fid = currentUserFID
        db.addChatListChangeListener(fid!!, listener)
    }
}