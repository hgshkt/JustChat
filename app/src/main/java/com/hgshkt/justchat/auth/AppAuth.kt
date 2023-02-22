package com.hgshkt.justchat.auth

import com.hgshkt.justchat.database.UserDatabase
import com.hgshkt.justchat.database.UserDatabaseImpl
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppAuth {

    fun createUser(
        name: String,
        customId: String,
        email: String,
        password: String,
        firebaseId: String
    ) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                val createdUser = User(
                    name = name,
                    id = customId,
                    email = email,
                    password = password,
                    fid = firebaseId
                )

                val db : UserDatabase = UserDatabaseImpl()
                db.addUser(createdUser)
            }
        }
    }
}