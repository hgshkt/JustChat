package com.hgshkt.justchat.controllers

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.vector.ImageVector
import com.hgshkt.justchat.controllers.Status.Companion.getStatusFor

enum class ProfileStatus {

    /**
     * Status of current user's profile
     */
    CURRENT  {
        override var status = Status.CURRENT
        override var icon = Icons.Default.Edit
        override var buttonText = "Edit"

        override suspend fun buttonClick(fid: String) {
            // TODO edit profile
        }
    },

    /**
     * Status of profile that received invite from current user
     */
    GOTTEN_INVITE  {
        override var status = Status.CURRENT
        override var icon = Icons.Default.Close
        override var buttonText = "Cancel inviting"

        override suspend fun buttonClick(fid: String) {
            FriendController().cancelInviting(fid)
        }

    },
    /**
     * Status of profile that sent invite to current user
     */
    SENT_INVITE  {
        override var status = Status.CURRENT
        override var icon = Icons.Default.Add
        override var buttonText = "Add"

        override suspend fun buttonClick(fid: String) {
            FriendController().acceptInvite(fid)
        }

    },
    /**
     * Status of current user's friend profile
     */
    FRIEND  {
        override var status = Status.CURRENT
        override var icon = Icons.Default.Clear
        override var buttonText = "Delete friend"

        override suspend fun buttonClick(fid: String) {
            FriendController().stopFriendship(fid)
        }

    },
    /**
     * Status of default profile
     */
    DEFAULT  {
        override var status = Status.CURRENT
        override var icon = Icons.Default.Add
        override var buttonText = "Send invite"

        override suspend fun buttonClick(fid: String) {
            FriendController().sendInviteTo(fid)
        }
    };

    abstract var status: Status
    abstract var icon: ImageVector
    abstract var buttonText: String

    abstract suspend fun buttonClick(fid: String)

    companion object {
        suspend fun getProfileStatusFor(fid: String): ProfileStatus {
            val status = getStatusFor(fid)
            for (profileStatus in values()) {
                if (profileStatus.status == status) {
                    return profileStatus
                }
            }
            return DEFAULT
        }
    }
}