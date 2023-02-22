package com.hgshkt.justchat.auth

import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.controllers.UserController
import com.hgshkt.justchat.models.User

object CurrentUser {

    private val controller: UserController = UserController()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var instance: User? = null


    fun get(): User? {
        if (auth.currentUser == null) return null

        val fid = auth.currentUser!!.uid
        instance = controller.getUserByFID(fid)

        return instance
    }
}