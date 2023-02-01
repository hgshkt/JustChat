package com.hgshkt.justchat.database

import com.google.firebase.database.FirebaseDatabase
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.tasks.await

class UserDatabaseImpl : UserDatabase {

    private val path = "users"
    var dbRef = FirebaseDatabase.getInstance().getReference(path)

    override suspend fun addUser(user: User) {
        dbRef.child(user.firebaseId).setValue(user)
    }

    override suspend fun getUserById(id: String): User {
        return dbRef.child(id).get().await().getValue(User::class.java)!!
    }
}