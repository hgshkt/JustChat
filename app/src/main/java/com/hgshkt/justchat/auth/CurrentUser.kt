package com.hgshkt.justchat.auth

import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User

object CurrentUser {

    private val dao: UserDao = UserDao()
    private val auth: AppAuth = AppAuth()

    fun get() : User {
        return dao.getUserByFID(auth.currentUserFID!!)!!
    }
    fun addValueChangeListener(event: (user: User) -> Unit) {
        dao.addOnValueChangeListener(auth.currentUserFID!!) {
            event(it.getValue(User::class.java)!!)
        }
    }
}