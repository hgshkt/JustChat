package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User
import com.hgshkt.justchat.ui.navigation.Screen

class FriendListViewModel(
    private val navController: NavController
) : ViewModel() {
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

    fun openProfile(user: User) {
        navController.navigate(Screen.ProfileScreen.withArg("userFID", user.fid))
    }
}