package com.hgshkt.justchat.auth

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ChatAuth {

    private val auth = FirebaseAuth.getInstance()

    fun createUser(user: User, context: Context) {
        if(user.email.isNotEmpty() && user.password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    auth.createUserWithEmailAndPassword(user.email, user.password)
                } catch (exception: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}