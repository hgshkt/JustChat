package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User

class FriendListViewModel : ViewModel() {
    private val dao: UserDao = UserDao()
    private var idList: List<String> = mutableListOf()

    var userList = mutableStateListOf<User>()
        private set

    init {
        CurrentUser.addValueChangeListener {
            idList = it.friendList
            userList.clear()
            idList.forEach { fid ->
                val user = dao.getUserByFID(fid)!!
                userList.add(user)
            }
        }
    }
}