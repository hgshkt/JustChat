package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.hgshkt.justchat.dao.ChatDao
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User
import com.hgshkt.justchat.ui.navigation.Screen
import kotlinx.coroutines.launch

class ChatMembersViewModel(
    private val id: String,
    private val navController: NavController
) : ViewModel() {
    private val dao: UserDao = UserDao()
    private val chatDao: ChatDao = ChatDao()
    private var idList: List<String> = mutableListOf()

    var userList = mutableStateListOf<User>()
        private set

    init {
        viewModelScope.launch {
            chatDao.getChat(id) {
                idList = it?.membersFid ?: mutableListOf()
                userList.clear()
                idList.forEach { fid ->
                    val user = dao.getUserByFID(fid)!!
                    userList.add(user)
                }
            }
        }
    }

    fun openProfile(user: User) {
        navController.navigate(Screen.ProfileScreen.withArg("userFID", user.fid))
    }
}