package com.hgshkt.justchat.auth

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppAuth {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val entered: Boolean
        get() {
            if (auth.currentUser == null)
                return false
            return true
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

    fun signOut() {
        auth.signOut()
    }
}