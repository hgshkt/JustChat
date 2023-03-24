package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.controllers.UserController
import com.hgshkt.justchat.models.User

class ProfileViewModel(private val fid: String?) : ViewModel() {

    private val controller: UserController = UserController()
    val user = mutableStateOf(User())

    init {
        if (fid == null)
            CurrentUser.addValueChangeListener {
                user.value = it
            }
        else
            controller.addOnValueChangeListener(fid) {
                user.value = it.getValue(User::class.java)!!
            }
    }
}