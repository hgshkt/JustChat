package com.hgshkt.justchat.controllers

import com.hgshkt.justchat.database.UserDatabase
import com.hgshkt.justchat.database.UserDatabaseImpl
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.runBlocking

class UserController {
    private val db: UserDatabase = UserDatabaseImpl()

    fun getUserById(id: String): User? {
        val users = getAllUsers()
        users.forEach {
            if (it.id == id) return it
        }
        return null
    }

    fun getUserByFirebaseId(id: String) : User? {
        return runBlocking {
            db.getUserById(id)
        }
    }

    fun getAllUsers(): List<User> {
        return runBlocking {
            val list = mutableListOf<User>()
            val map = db.getAllUsers()
            if (map == null) return@runBlocking list
            list.addAll(map.values)
            return@runBlocking list
        }
    }
}