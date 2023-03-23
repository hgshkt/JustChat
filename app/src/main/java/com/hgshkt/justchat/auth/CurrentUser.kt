package com.hgshkt.justchat.auth

import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.controllers.UserController
import com.hgshkt.justchat.models.User

object CurrentUser {

    private val controller: UserController = UserController()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    var instance: User? = null
        private set


    init {
        controller.addOnValueChangeListener(auth.currentUser!!.uid) {
            instance = it.getValue(User::class.java)
        }
    }
}