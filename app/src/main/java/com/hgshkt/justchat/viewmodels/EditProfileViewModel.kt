package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User
import com.hgshkt.justchat.ui.navigation.Screen

class EditProfileViewModel(
    private val fid: String,
    private val navController: NavController
) : ViewModel() {
    private val userDao: UserDao = UserDao()

    var user = User()
    var name = mutableStateOf("")
    var id = mutableStateOf("")
    var bio = mutableStateOf("")

    init {
        user = userDao.getUserByFID(fid)!!
        name.value = user.name
        id.value = user.id
        bio.value = user.bio
    }

    fun save() {
        user.name = name.value
        user.id = id.value
        user.bio = bio.value
        userDao.updateUser(user)

        navController.navigate(Screen.ProfileScreen.withArg("userFID", fid))
    }
}