package com.hgshkt.justchat.auth

import com.hgshkt.justchat.controllers.UserController
import com.hgshkt.justchat.models.User

object CurrentUser {

    private val controller: UserController = UserController()
    private val auth: AppAuth = AppAuth()

    fun get() : User {
        return controller.getUserByFID(auth.currentUserFID!!)!!
    }
    fun addValueChangeListener(event: (user: User) -> Unit) {
        controller.addOnValueChangeListener(auth.currentUserFID!!) {
            event(it.getValue(User::class.java)!!)
        }
    }
}