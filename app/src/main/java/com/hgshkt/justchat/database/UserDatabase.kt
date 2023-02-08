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

    /**
     * Adds recipient id to sender's sending invites
     * @param senderId is id of user who sends invite
     * @param recipientId is user's id to which the invite was sent
     */
    suspend fun sendInvite(senderId: String, recipientId: String)


    /**
     * Adds sender id to recipient's received invites
     * @param senderId is id of user who sends invite
     * @param recipientId is user's id to which the invite was sent
     */
    suspend fun getInvite(senderId: String, recipientId: String)

    /**
     * Removes user id from sent invite list
     * @param senderId is id of user from whose sent invite list the recipient id will be removed
     * @param recipientId is user's id whose id will be removed from sender sent invite list
     */
    suspend fun removeUserFromSentInvites(senderId: String, recipientId: String)

    /**
     * Removes user id from gotten invite list
     * @param senderId is id of user from whose sent invite list the recipient id will be removed
     * @param recipientId is user's id whose id will be removed from sender sent invite list
     */
    suspend fun removeUserFromGottenInvites(senderId: String, recipientId: String)

    /**
     * Adds id to user's friend list
     * @param userId is id of user, in whose friend list must be added friend id
     * @param friendId is id of user who be added to friend list
     */
    suspend fun addFriendToFriendList(userId: String, friendId: String)

    /**
     * Returns HashMap of user's sent invites. Values is empty. Keys contains id
     * @param userId is user's id whose list must be gotten
     */
    suspend fun getSentInviteList(userId: String): HashMap<String, String>?

    /**
     * Returns HashMap of user's received invites. Values is empty. Keys contains id
     * @param userId is user's id whose list must be gotten
     */
    suspend fun getReceivedInviteList(userId: String): HashMap<String, String>?

    /**
     * Returns HashMap of user's friend list. Values is empty. Keys contains id
     * @param userId is user's id whose list must be gotten
     */
    suspend fun getFriendList(userId: String): HashMap<String, String>?

    /**
     * Removes user id from friend list
     * @param ownerId is user id from whose friend list the id must be removed
     * @param removedId is id to be removed
     */
    suspend fun removeFromFriendList(ownerId: String, removedId: String)
}