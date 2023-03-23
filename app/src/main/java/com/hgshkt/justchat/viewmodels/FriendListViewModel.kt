package com.hgshkt.justchat.viewmodels

import androidx.lifecycle.ViewModel
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.controllers.UserController
import com.hgshkt.justchat.models.User

class FriendListViewModel : ViewModel() {
    var idList: List<String> = mutableListOf()
        private set

    var userList: MutableList<User> = mutableListOf()
        private set

    init {
        idList = CurrentUser.get().friendList
        idList.forEach { fid ->
            val user = UserController().getUserByFID(fid)!!
            userList.add(user)
        }
    }
}