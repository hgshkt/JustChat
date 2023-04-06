package com.hgshkt.justchat.database

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.User


/**
 * UserDatabase is the application's user database control system.
 */
interface UserDatabase {

    /**
     * Adds a user to database
     * @param user - user which must be added to database
     */
    suspend fun addUser(user: User)

    /**
     * Returns user from database by its id
     * @param fid is user id
     */
    suspend fun getUserByFID(fid: String): User?

    suspend fun addOnValueChangeListener(fid: String, event: (snapshot: DataSnapshot) -> Unit)

    /**
     * Returns HashMap of all users. Value is user, keys contains id
     */
    suspend fun getAllUsers(): HashMap<String, User>?


    /**
     * Adds chat id to user chat list
     * @param fid is user id
     * @param chat is chat whose id must be added to user chat list
     */
    suspend fun addChatToUserChatList(fid: String, chat: Chat)


    suspend fun updateSentInvites(fid: String, sentInvites: List<String>)


    suspend fun updateReceivedInvites(fid: String, receivedInvites: List<String>)


    suspend fun updateFriendList(fid: String, friendList: List<String>)

    /**
     * Returns HashMap of user's sent invites. Values is empty. Keys contains id
     * @param fid is user's id whose list must be gotten
     */
    suspend fun getSentInviteList(fid: String): List<String>?

    /**
     * Returns HashMap of user's received invites. Values is empty. Keys contains id
     * @param fid is user's id whose list must be gotten
     */
    suspend fun getReceivedInviteList(fid: String): List<String>?

    /**
     * Returns HashMap of user's friend list. Values is empty. Keys contains id
     * @param fid is user's id whose list must be gotten
     */
    suspend fun getFriendList(fid: String): List<String>?

    suspend fun addChatListChangeListener(fid: String, listener: ChildEventListener)
}