package com.hgshkt.justchat.dao

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.database.UserDatabase
import com.hgshkt.justchat.database.UserDatabaseImpl
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserDao {
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
        getAllUsers().forEach {
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
            return@runBlocking db.getAllUsers()?.values?.toList() ?: emptyList()
        }
    }


    suspend fun addChatListChangeListener(
        listener: ChildEventListener
    ) {
        val fid = AppAuth().currentUserFID
        db.addChatListChangeListener(fid!!, listener)
    }
}