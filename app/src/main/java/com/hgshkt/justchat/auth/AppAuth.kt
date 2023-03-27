package com.hgshkt.justchat.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.creators.UserCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class AppAuth {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val entered: Boolean
        get() {
            return auth.currentUser != null
        }

    val currentUserFID: String?
        get() = auth.currentUser?.uid

    fun login(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            runBlocking(Dispatchers.IO) {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                } catch (exception: Exception) {
                    Log.i("AppAuth", exception.message.toString())
                }
            }
        }
    }

    suspend fun registration(
        email: String,
        password: String,
        name: String,
        id: String
    ) {
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password).await()
        auth.signInWithEmailAndPassword(email, password).await()

        val creator = UserCreator()
        creator.createUser(
            name = name,
            customId = id,
            email = email,
            password = password,
            firebaseId = auth.currentUser!!.uid
        )
    }

    fun signOut() {
        auth.signOut()
    }
}