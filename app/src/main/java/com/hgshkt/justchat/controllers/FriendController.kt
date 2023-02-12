package com.hgshkt.justchat.controllers

import com.hgshkt.justchat.database.UserDatabase
import com.hgshkt.justchat.database.UserDatabaseImpl
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FriendController {
    private val db: UserDatabase = UserDatabaseImpl()

    private fun makeFriends(user1: User, user2: User) {
        (user1.friendList as MutableList).add(user2.firebaseId)
        (user2.friendList as MutableList).add(user1.firebaseId)
        CoroutineScope(Dispatchers.IO).launch {
            db.updateFriendList(user1.firebaseId, user1.friendList)
            db.updateFriendList(user2.firebaseId, user2.friendList)
        }
    }

    fun sendInvite(sender: User, receiver: User) {
        (sender.sentInvites as MutableList).add(receiver.firebaseId)
        (receiver.gottenInvites as MutableList).add(sender.firebaseId)
        CoroutineScope(Dispatchers.IO).launch {
            db.updateSentInvites(sender.firebaseId, sender.sentInvites)
            db.updateReceivedInvites(receiver.firebaseId, receiver.gottenInvites)
        }
    }

    fun acceptInvite(sender: User, receiver: User) {
        (sender.sentInvites as MutableList).remove(receiver.firebaseId)
        (receiver.gottenInvites as MutableList).remove(sender.firebaseId)
        CoroutineScope(Dispatchers.IO).launch {
            db.updateSentInvites(sender.firebaseId, sender.sentInvites)
            db.updateReceivedInvites(receiver.firebaseId, receiver.gottenInvites)
        }
        makeFriends(sender, receiver)
    }

    suspend fun getSentInviteList(userId: String): List<String>? {
        return db.getSentInviteList(userId)
    }

    suspend fun getReceivedInviteList(userId: String): List<String>? {
        return db.getReceivedInviteList(userId)
    }

    suspend fun idInSentInviteList(senderId: String, receiverId: String): Boolean {
        val list = getSentInviteList(senderId) ?: return false
        if (list.contains(receiverId)) {
            return true
        }
        return false
    }

    suspend fun idInGottenInviteList(senderId: String, receiverId: String): Boolean {
        val list = getReceivedInviteList(receiverId) ?: return false
        if (list.contains(senderId)) {
            return true
        }
        return false
    }

    suspend fun areFriends(firstId: String, secondId: String): Boolean {
        val friendList = db.getFriendList(firstId) ?: return false

        val firstHasSecond = friendList.contains(secondId)
        val secondHasFirst = friendList.contains(firstId)
        return firstHasSecond && secondHasFirst
    }

    fun stopFriendship(user1: User, user2: User) {
        runBlocking {
            if (areFriends(user1.firebaseId, user2.firebaseId)) {
                (user1.friendList as MutableList).remove(user2.firebaseId)
                (user2.friendList as MutableList).remove(user1.firebaseId)
                db.updateFriendList(user1.firebaseId, user1.friendList)
                db.updateFriendList(user1.firebaseId, user2.friendList)
            }
        }
    }

    fun cancelInviting(sender: User, receiver: User) {
        runBlocking {
            val senderHasRecipient = idInSentInviteList(sender.firebaseId, receiver.firebaseId)
            val recipientHasSender = idInGottenInviteList(sender.firebaseId, receiver.firebaseId)
            if (senderHasRecipient && recipientHasSender) {
                (sender.sentInvites as MutableList).remove(receiver.firebaseId)
                (receiver.gottenInvites as MutableList).remove(sender.firebaseId)
                db.updateSentInvites(sender.firebaseId, sender.sentInvites)
                db.updateReceivedInvites(receiver.firebaseId, receiver.gottenInvites)
            }
        }
    }
}