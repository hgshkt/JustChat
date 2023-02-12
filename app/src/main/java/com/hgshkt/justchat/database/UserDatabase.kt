package com.hgshkt.justchat.database

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
     * @param id is user id
     */
    suspend fun getUserById(id: String): User?

    /**
     * Returns HashMap of all users. Value is user, keys contains id
     */
    suspend fun getAllUsers(): HashMap<String, User>?


    /**
     * Adds chat id to user chat list
     * @param userId is user id
     * @param chat is chat whose id must be added to user chat list
     */
    suspend fun addChatToUserChatList(userId: String, chat: Chat)


    suspend fun updateSentInvites(userId: String, sentInvites: List<String>)


    suspend fun updateReceivedInvites(userId: String, receivedInvites: List<String>)


    suspend fun updateFriendList(userId: String, friendList: List<String>)

    /**
     * Returns HashMap of user's sent invites. Values is empty. Keys contains id
     * @param userId is user's id whose list must be gotten
     */
    suspend fun getSentInviteList(userId: String): List<String>?

    /**
     * Returns HashMap of user's received invites. Values is empty. Keys contains id
     * @param userId is user's id whose list must be gotten
     */
    suspend fun getReceivedInviteList(userId: String): List<String>?

    /**
     * Returns HashMap of user's friend list. Values is empty. Keys contains id
     * @param userId is user's id whose list must be gotten
     */
    suspend fun getFriendList(userId: String): List<String>?
}