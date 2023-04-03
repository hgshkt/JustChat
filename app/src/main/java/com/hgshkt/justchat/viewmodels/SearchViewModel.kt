package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.ui.navigation.Screen

class SearchViewModel(
    private val navController: NavController
): ViewModel() {

    val text = mutableStateOf("")

    fun openProfile() {
        val user = UserDao().getUserById(text.value)!!
        navController.navigate(Screen.ProfileScreen.withArg("userFID", user.fid))
    }
}