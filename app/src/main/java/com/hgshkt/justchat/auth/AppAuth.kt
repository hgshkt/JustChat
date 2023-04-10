package com.hgshkt.justchat.auth

import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.creators.UserCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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

fun appAuthLogin(
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
            }
        }
    }
}

fun appAuthRegistration(
    email: String,
    password: String,
    customId: String,
    name: String,
    whenUserCreated: (auth: FirebaseAuth) -> Unit
) {
    // TODO remove current user from db and auth
    if (auth.currentUser == null) auth.signOut()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sendEmailVerification { auth ->
                    val creator = UserCreator()
                    CoroutineScope(Dispatchers.IO).launch {
                        creator.createUser(
                            name = name,
                            email = email,
                            password = password,
                            customId = customId,
                            firebaseId = auth.currentUser!!.uid
                        )
                    }
                    whenUserCreated(auth)
                }
            }
        }
}

fun sendEmailVerification(onVerificationSent: (auth: FirebaseAuth) -> Unit = {}) {
    val currentUser = auth.currentUser
    currentUser?.sendEmailVerification()
        ?.addOnCompleteListener { verificationTask ->
            if (verificationTask.isSuccessful) {
                onVerificationSent(auth)
            }
        }
}

fun signOut() {
    auth.signOut()
}