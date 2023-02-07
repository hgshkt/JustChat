package com.hgshkt.justchat.controllers

import com.hgshkt.justchat.database.UserDatabase
import com.hgshkt.justchat.database.UserDatabaseImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FriendController {
    private val db: UserDatabase = UserDatabaseImpl()

    private fun makeFriends(firstUserID: String, secondUserId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.addFriendToFriendList(firstUserID, secondUserId)
        }
    }

    fun sendInvite(senderId: String, receiverId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.sendInvite(senderId, receiverId)
            db.getInvite(senderId, receiverId)
        }
    }

    fun acceptInvite(senderId: String, receiverId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.removeUserFromSentInvites(senderId, receiverId)
            db.removeUserFromGottenInvites(senderId, receiverId)
        }
        makeFriends(senderId, receiverId)
    }

    suspend fun getSentInviteList(userId: String): List<String> {
        val list = mutableListOf<String>()
        val map = db.getSentInviteList(userId)
        if (map == null) return list
        map.keys.forEach {
            list.add(it)
        }
        return list
    }

    suspend fun getReceivedInviteList(userId: String): List<String> {
        val list = mutableListOf<String>()
        val map = db.getReceivedInviteList(userId)
        if (map == null) return list
        map.keys.forEach {
            list.add(it)
        }
        return list
    }

    suspend fun idInSentInviteList(senderId: String, receiverId: String): Boolean {
        val list = getSentInviteList(senderId)
        if (list.contains(receiverId)) {
            return true
        }
        return false
    }

    suspend fun idInGottenInviteList(senderId: String, receiverId: String): Boolean {
        val list = getReceivedInviteList(senderId)
        if (list.contains(receiverId)) {
            return true
        }
        return false
    }

    suspend fun areFriends(firstId: String, secondId: String): Boolean {
        val friendList = db.getFriendList(firstId)
        if (friendList == null) return false

        val firstHasSecond = friendList.contains(secondId)
        val secondHasFirst = friendList.contains(firstId)
        return firstHasSecond && secondHasFirst
    }

    fun stopFriendship(stopperId: String, senderId: String) {
        runBlocking {
            if (areFriends(stopperId, senderId)) {
                db.removeFromFriendList(stopperId, senderId)
                db.removeFromFriendList(senderId, stopperId)

                db.sendInvite(senderId, stopperId)
            }
        }
    }

    fun cancelInviting(senderId: String, recipientId: String) {
        runBlocking {
            val senderHasRecipient = idInSentInviteList(senderId, recipientId)
            val recipientHasSender = idInGottenInviteList(senderId, recipientId)
            if (senderHasRecipient && recipientHasSender) {
                db.removeUserFromGottenInvites(senderId, recipientId)
                db.removeUserFromSentInvites(senderId, recipientId)
            }
        }
    }
}