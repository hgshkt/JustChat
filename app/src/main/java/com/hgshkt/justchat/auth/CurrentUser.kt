package com.hgshkt.justchat.auth

import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

private val dao: UserDao = UserDao()
private val auth: AppAuth = AppAuth()


var currentUser: User? = null
    private set
    get() {
        return runBlocking(Dispatchers.IO) {
            dao.getUserByFID(auth.currentUserFID!!)!!
        }
    }

fun onCurrentUserChange(event: (user: User) -> Unit) {
    dao.addOnValueChangeListener(auth.currentUserFID!!) {
        event(it.getValue(User::class.java)!!)
    }
}