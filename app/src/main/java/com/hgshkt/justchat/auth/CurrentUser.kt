package com.hgshkt.justchat.auth

import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

private val dao: UserDao = UserDao()


var currentUser: User? = null
    private set
    get() {
        return runBlocking(Dispatchers.IO) {
            dao.getUserByFID(currentUserFID!!)!!
        }
    }

fun onCurrentUserChange(event: (user: User) -> Unit) {
    dao.addOnValueChangeListener(currentUserFID!!) {
        event(it.getValue(User::class.java)!!)
    }
}