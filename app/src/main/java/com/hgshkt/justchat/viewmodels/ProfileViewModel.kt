package com.hgshkt.justchat.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.controllers.UserController
import com.hgshkt.justchat.models.User

class ProfileViewModel(private val fid: String?) : ViewModel() {

    private val controller: UserController = UserController()

    fun getUser() : User {
        if (fid == null) return CurrentUser.instance!!

        return controller.getUserByFID(fid)!!
    }
}