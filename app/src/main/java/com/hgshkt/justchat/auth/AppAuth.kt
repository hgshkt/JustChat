package com.hgshkt.justchat.auth

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.creators.UserCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AppAuth {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val entered: Boolean
        get() {
            return auth.currentUser != null
        }

    fun login(email: String, password: String, context: Context) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password)
                } catch (exception: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    suspend fun registration(
        email: String,
        password: String,
        name:String,
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