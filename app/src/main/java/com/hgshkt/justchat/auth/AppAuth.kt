package com.hgshkt.justchat.auth

import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.creators.UserCreator
import kotlinx.coroutines.*

class AppAuth {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var authStateListener: FirebaseAuth.AuthStateListener? = null

    val entered: Boolean
        get() {
            return auth.currentUser != null
        }

    val emailVerified: Boolean
        get() {
            return auth.currentUser?.isEmailVerified ?: false
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
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { verificationTask ->
                            if (verificationTask.isSuccessful) {
                                authStateListener = FirebaseAuth.AuthStateListener { auth ->
                                    val creator = UserCreator()
                                    CoroutineScope(Dispatchers.IO).launch {
                                        creator.createUser(
                                            name = name,
                                            email = email,
                                            password = password,
                                            customId = customId,
                                            firebaseId = auth.currentUser!!.uid
                                        )
                                        withContext(Dispatchers.Main) {
                                            event()
                                        }
                                    }
                                }
                            }
                            auth.addAuthStateListener(authStateListener!!)
                        }
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }
}