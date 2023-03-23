package com.hgshkt.justchat.auth

import com.hgshkt.justchat.controllers.UserController
import com.hgshkt.justchat.models.User

object CurrentUser {

    private val controller: UserController = UserController()
    private val auth: AppAuth = AppAuth()

    var instance: User? = null
        private set
        get() {
            if (field == null) {
                controller.addOnValueChangeListener(auth.currentUserFID!!) {
                    instance = it.getValue(User::class.java)
                }
            }
            return controller.getUserByFID(AppAuth().currentUserFID!!)
        }
}