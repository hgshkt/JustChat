package com.hgshkt.justchat.controllers

import com.hgshkt.justchat.tools.auth.currentUser
import com.hgshkt.justchat.tools.auth.currentUserFID
import com.hgshkt.justchat.dao.UserDao

class FriendController {
    private val userDao: UserDao = UserDao()
    
    suspend fun areFriends(firstFID: String, secondFID: String): Boolean {
        val firstFriendList = userDao.getFriendList(firstFID)
        val secondFriendList = userDao.getFriendList(secondFID)

        val firstHasSecond = firstFriendList.contains(secondFID)
        val secondHasFirst = secondFriendList.contains(firstFID)

        return firstHasSecond && secondHasFirst
    }



    suspend fun gottenInvite(userFid: String): Boolean {
        val currentUserFid = currentUserFID!!

        val gottenInviteList = userDao.getReceivedInviteList(userFid)
        val sentInviteList = userDao.getSentInviteList(currentUserFid)

        return gottenInviteList.contains(currentUserFid) && sentInviteList.contains(userFid)
    }

    suspend fun sentInvite(userFid: String): Boolean {
        val currentUserFid = currentUserFID!!

        val gottenInviteList = userDao.getReceivedInviteList(currentUserFid)
        val sentInviteList = userDao.getSentInviteList(userFid)

        return gottenInviteList.contains(userFid) && sentInviteList.contains(currentUserFid)
    }

    suspend fun sendInviteTo(fid: String) {
        val sender = currentUser!!
        val recipient = userDao.getUserByFID(fid)!!

        sender.sentInvites.add(recipient.fid)
        recipient.gottenInvites.add(sender.fid)

        userDao.updateSentInviteList(sender.fid, sender.sentInvites)
        userDao.updateReceivedInviteList(recipient.fid, recipient.gottenInvites)
    }

    suspend fun cancelInviting(fid: String) {
        val currentUser = currentUser!!
        val recipient = userDao.getUserByFID(fid)!!

        currentUser.sentInvites.remove(recipient.fid)
        recipient.gottenInvites.remove(currentUser.fid)

        userDao.updateSentInviteList(currentUser.fid, currentUser.sentInvites)
        userDao.updateReceivedInviteList(recipient.fid, recipient.gottenInvites)
    }

    suspend fun acceptInvite(fid: String) {
        val currentUser = currentUser!!
        val sender = userDao.getUserByFID(fid)!!

        sender.sentInvites.remove(currentUser.fid)
        sender.friendList.add(currentUser.fid)
        userDao.updateUser(sender)

        currentUser.gottenInvites.remove(sender.fid)
        currentUser.friendList.add(sender.fid)
        userDao.updateUser(currentUser)
    }

    suspend fun stopFriendship(fid: String) {
        val currentUser = userDao.getUserByFID(currentUserFID!!)!!
        val friend = userDao.getUserByFID(fid)!!

        currentUser.friendList.remove(friend.fid)
        friend.friendList.remove(currentUser.fid)

        userDao.updateFriendList(currentUser.fid, currentUser.friendList)
        userDao.updateFriendList(friend.fid, friend.friendList)
    }
}