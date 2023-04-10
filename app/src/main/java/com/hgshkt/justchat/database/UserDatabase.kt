package com.hgshkt.justchat.database

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.hgshkt.justchat.models.User

interface UserDatabase {

    suspend fun addUser(user: User)

    suspend fun getUserByFID(fid: String): User?

    suspend fun getAllUsers(): HashMap<String, User>?

    suspend fun observeChatList(fid: String, event: (snapshot: DataSnapshot) -> Unit)

    fun updateChatIdList(fid: String, chatList: List<String>)

    suspend fun getSentInviteList(fid: String): List<String>?

    suspend fun updateSentInviteList(fid: String, sentInvites: List<String>)

    suspend fun getReceivedInviteList(fid: String): List<String>?

    suspend fun updateReceivedInviteList(fid: String, receivedInvites: List<String>)

    suspend fun getFriendList(fid: String): List<String>?

    suspend fun updateFriendList(fid: String, friendList: List<String>)

    suspend fun addUserChangeListener(fid: String, event: (snapshot: DataSnapshot) -> Unit)

    suspend fun addChatListChangeListener(fid: String, listener: ChildEventListener)

    suspend fun updateChatLastMessageTime(fid: String, chatId: String, chatLastMessageTime: String)
}