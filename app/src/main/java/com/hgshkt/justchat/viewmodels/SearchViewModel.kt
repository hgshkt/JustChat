package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.hgshkt.justchat.auth.onCurrentUserChange
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User
import com.hgshkt.justchat.ui.navigation.Screen
import kotlinx.coroutines.launch

class SearchViewModel(
    private val navController: NavController
) : ViewModel() {

    private val userDao: UserDao = UserDao()
    var userList: MutableList<User> = mutableStateListOf()
    val text = mutableStateOf("")

    init {
        viewModelScope.launch {
            onCurrentUserChange {
                userList.clear()
                it.gottenInvites.forEach {
                    viewModelScope.launch {
                        val user = userDao.getUserByFID(it)!!
                        userList.add(user)
                    }
                }
            }
        }
    }

    fun openProfile() {
        viewModelScope.launch {
            val user = UserDao().getUserById(text.value)!!
            navController.navigate(Screen.ProfileScreen.withArg("userFID", user.fid))
        }
    }

    fun openProfile(fid: String) {
        navController.navigate(Screen.ProfileScreen.withArg("userFID", fid))
    }
}