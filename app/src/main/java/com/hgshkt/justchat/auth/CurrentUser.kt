package com.hgshkt.justchat.auth

import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.database.UserDatabase
import com.hgshkt.justchat.database.UserDatabaseImpl
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object CurrentUser {

    private val db: UserDatabase = UserDatabaseImpl()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var instance: User? = null

    suspend fun get(): User? {
        if (auth.currentUser == null) return null

        val job = CoroutineScope(Dispatchers.IO).launch {
            val id = auth.currentUser!!.uid
            instance = async { db.getUserById(id)}.await()
        }
        job.join()
        return instance
    }
}