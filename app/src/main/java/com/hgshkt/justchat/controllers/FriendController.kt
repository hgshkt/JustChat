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

    suspend fun getSentInviteList(userFID: String): List<String>? {
        return db.getSentInviteList(userFID)
    }

    suspend fun getReceivedInviteList(userFID: String): List<String>? {
        return db.getReceivedInviteList(userFID)
    }

    suspend fun idInSentInviteList(senderFID: String, receiverFID: String): Boolean {
        val list = getSentInviteList(senderFID) ?: return false
        if (list.contains(receiverFID)) {
            return true
        }
        return false
    }

    suspend fun idInGottenInviteList(senderFID: String, receiverFID: String): Boolean {
        val list = getReceivedInviteList(receiverFID) ?: return false
        if (list.contains(senderFID)) {
            return true
        }
        return false
    }

    suspend fun areFriends(firstFID: String, secondFID: String): Boolean {
        val firstFriendList = db.getFriendList(firstFID) ?: return false
        val secondFriendList = db.getFriendList(secondFID) ?: return false

        val firstHasSecond = firstFriendList.contains(secondFID)
        val secondHasFirst = secondFriendList.contains(firstFID)
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