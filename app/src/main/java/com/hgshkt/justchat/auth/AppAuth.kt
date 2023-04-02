package com.hgshkt.justchat.auth

import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.creators.UserCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppAuth {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val entered: Boolean
        get() {
            return auth.currentUser != null
        }

    val currentUserFID: String?
        get() = auth.currentUser?.uid

    fun login(
        email: String,
        password: String,
        event: (isEmailVerified: Boolean) -> Unit
    ) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                if (auth.currentUser!!.isEmailVerified) {
                    event(true)
                } else {
                    event(false)
                    signOut()
                }
            }
        }
    }

    fun registration(
        email: String,
        password: String,
        customId: String,
        name: String,
        event: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                auth.currentUser!!.sendEmailVerification().addOnSuccessListener {
                    CoroutineScope(Dispatchers.Main).launch {
                        auth.addAuthStateListener { firebaseAuth ->
                            val user = firebaseAuth.currentUser
                            if (user != null && user.isEmailVerified) {
                                val creator = UserCreator()
                                CoroutineScope(Dispatchers.IO).launch {
                                    creator.createUser(
                                        name = name,
                                        customId = customId,
                                        email = email,
                                        password = password,
                                        firebaseId = auth.currentUser!!.uid
                                    )
                                }
                            }
                        }
                    }

                    event()
                }
            }
        }
    }

    fun signOut() {
        auth.signOut()
    }
}