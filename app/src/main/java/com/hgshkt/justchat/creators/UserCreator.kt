package com.hgshkt.justchat.creators

import com.hgshkt.justchat.database.UserDatabase
import com.hgshkt.justchat.database.UserDatabaseImpl
import com.hgshkt.justchat.models.User

class UserCreator {

    suspend fun createUser(
        name: String,
        customId: String,
        email: String,
        password: String,
        firebaseId: String
    ) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            val createdUser = User(
                name = name,
                id = customId,
                email = email,
                password = password,
                fid = firebaseId
            )

            val db: UserDatabase = UserDatabaseImpl()
            db.addUser(createdUser)
        }
    }
}