package com.hgshkt.justchat.controllers

import com.google.firebase.database.DataSnapshot
import com.hgshkt.justchat.database.UserDatabase
import com.hgshkt.justchat.database.UserDatabaseImpl
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.*

class UserController {
    private val db: UserDatabase = UserDatabaseImpl()

    fun updateUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            db.addUser(user)
        }
    }

    fun addOnValueChangeListener(fid: String, event: (snapshot: DataSnapshot) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            db.addOnValueChangeListener(fid, event)
        }
    }

    fun getUserById(id: String): User? {
        val users = getAllUsers()
        users.forEach {
            if (it.id == id) return it
        }
        return null
    }

    fun getUserByFID(fid: String): User? {
        return runBlocking {
            db.getUserByFID(fid)
        }
    }

    fun getAllUsers(): List<User> {
        return runBlocking(Dispatchers.IO) {
            val list = mutableListOf<User>()
            val map = db.getAllUsers()
            if (map == null) return@runBlocking list
            list.addAll(map.values)
            return@runBlocking list
        }
    }
}