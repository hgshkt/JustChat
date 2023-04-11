package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.hgshkt.justchat.tools.auth.onCurrentUserChange
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User
import com.hgshkt.justchat.ui.navigation.Screen
import kotlinx.coroutines.launch

class FriendListViewModel(
    private val navController: NavController
) : ViewModel() {
    private val dao: UserDao = UserDao()
    private var idList: List<String> = mutableListOf()

    var userList = mutableStateListOf<User>()
        private set

    init {
        onCurrentUserChange {
            idList = it.friendList
            userList.clear()
            idList.forEach { fid ->
                viewModelScope.launch {
                    val user = dao.getUserByFID(fid)!!
                    userList.add(user)
                }
            }
        }
    }

    fun openProfile(fid: String) {
        navController.navigate(Screen.ProfileScreen.withArg("userFID", fid))
    }
}