package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.hgshkt.justchat.auth.currentUserFID
import com.hgshkt.justchat.controllers.ProfileStatus
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User
import com.hgshkt.justchat.ui.navigation.Screen
import kotlinx.coroutines.launch

class ProfileViewModel(
    private var fid: String?,
    private val navController: NavController
) : ViewModel() {

    val user = mutableStateOf(User())
    val status = mutableStateOf(ProfileStatus.DEFAULT)

    private val dao: UserDao = UserDao()

    init {
        dao.addOnValueChangeListener(fid ?: currentUserFID!!) {
            user.value = it.getValue(User::class.java)!!

            viewModelScope.launch {
                status.value = ProfileStatus.getProfileStatusFor(fid!!)
            }
        }
    }

    fun buttonClick() {
        if (status.value == ProfileStatus.CURRENT) {
            openEditProfileScreen()
        } else {
            viewModelScope.launch {
                status.value.buttonClick(fid!!)
            }
        }
    }

    private fun openEditProfileScreen() {
        navController.navigate(
            Screen.EditProfileScreen.withArg(
                argName = "userFID",
                argValue = fid ?: currentUserFID!!
            )
        )
    }
}