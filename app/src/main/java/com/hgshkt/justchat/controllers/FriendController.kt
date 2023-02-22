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
        (user1.friendList as MutableList).add(user2.fid)
        (user2.friendList as MutableList).add(user1.fid)
        CoroutineScope(Dispatchers.IO).launch {
            db.updateFriendList(user1.fid, user1.friendList)
            db.updateFriendList(user2.fid, user2.friendList)
        }
    }

    fun sendInvite(sender: User, receiver: User) {
        (sender.sentInvites as MutableList).add(receiver.fid)
        (receiver.gottenInvites as MutableList).add(sender.fid)
        CoroutineScope(Dispatchers.IO).launch {
            db.updateSentInvites(sender.fid, sender.sentInvites)
            db.updateReceivedInvites(receiver.fid, receiver.gottenInvites)
        }
    }

    fun acceptInvite(sender: User, receiver: User) {
        (sender.sentInvites as MutableList).remove(receiver.fid)
        (receiver.gottenInvites as MutableList).remove(sender.fid)
        CoroutineScope(Dispatchers.IO).launch {
            db.updateSentInvites(sender.fid, sender.sentInvites)
            db.updateReceivedInvites(receiver.fid, receiver.gottenInvites)
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
        val firstFriendList = db.getFriendList(firstId) ?: return false
        val secondFriendList = db.getFriendList(secondId) ?: return false

        val firstHasSecond = firstFriendList.contains(secondId)
        val secondHasFirst = secondFriendList.contains(firstId)
        return firstHasSecond && secondHasFirst
    }

    fun stopFriendship(user1: User, user2: User) {
        runBlocking {
            if (areFriends(user1.fid, user2.fid)) {
                (user1.friendList as MutableList).remove(user2.fid)
                (user2.friendList as MutableList).remove(user1.fid)
                db.updateFriendList(user1.fid, user1.friendList)
                db.updateFriendList(user1.fid, user2.friendList)
            }
        }
    }

    fun cancelInviting(sender: User, receiver: User) {
        runBlocking {
            val senderHasRecipient = idInSentInviteList(sender.fid, receiver.fid)
            val recipientHasSender = idInGottenInviteList(sender.fid, receiver.fid)
            if (senderHasRecipient && recipientHasSender) {
                (sender.sentInvites as MutableList).remove(receiver.fid)
                (receiver.gottenInvites as MutableList).remove(sender.fid)
                db.updateSentInvites(sender.fid, sender.sentInvites)
                db.updateReceivedInvites(receiver.fid, receiver.gottenInvites)
            }
        }
    }
}