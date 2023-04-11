package com.hgshkt.justchat.controllers

import com.hgshkt.justchat.tools.auth.currentUserFID

enum class Status {
    /**
     * Current user
     */
    CURRENT  {
        override suspend fun condition(id: String): Boolean {
            return id == currentUserFID
        }
    },

    /**
     * Status of user that received invite from current user
     */
    GOTTEN_INVITE  {
        private val controller = FriendController()

        override suspend fun condition(id: String): Boolean {
            return controller.gottenInvite(id)
        }
    },
    /**
     * Status of user that sent invite to current user
     */
    SENT_INVITE  {
        private val controller = FriendController()

        override suspend fun condition(id: String): Boolean {
            return controller.sentInvite(id)
        }
    },
    /**
     * Status of current user's friend
     */
    FRIEND  {
        private val controller = FriendController()

        override suspend fun condition(id: String): Boolean {
            return controller.areFriends(currentUserFID!!, id)
        }
    },
    /**
     * Status of default user
     */
    DEFAULT  {
        override suspend fun condition(id: String) = true
    };

    abstract suspend fun condition(id: String): Boolean

    companion object {
        suspend fun getStatusFor(id: String): Status {
            for (status in values()) {
                if (status.condition(id))
                    return status
            }
            return DEFAULT
        }
    }
}
