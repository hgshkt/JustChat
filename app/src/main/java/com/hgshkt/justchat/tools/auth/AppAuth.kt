package com.hgshkt.justchat.tools.auth

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.creators.UserCreator
import com.hgshkt.justchat.dao.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private val auth: FirebaseAuth = FirebaseAuth.getInstance()
private val userDao: UserDao = UserDao()

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
    context: Context,
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
        }.addOnFailureListener {
            toast(
                context,
                "Email and password do not match"
            )
        }
    } else {
        toast(
            context,
            "Enter email and password"
        )
    }
}

fun appAuthRegistration(
    context: Context,
    email: String,
    password: String,
    customId: String,
    name: String,
    whenUserCreated: (auth: FirebaseAuth) -> Unit
) {
    if (auth.currentUser == null) auth.signOut()
    else {
        userDao.delete(auth.currentUser!!.uid)
        auth.currentUser!!.delete()
    }
    if (email.trim().isEmpty()) {
        toast(
            context,
            "Input email"
        )
        return
    }

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sendEmailVerification(context) { auth ->
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
        .addOnFailureListener {
            toast(
                context,
                "User not created. Exception: $it"
            )
        }
}

fun sendEmailVerification(
    context: Context,
    email: String = "",
    onVerificationSent: (auth: FirebaseAuth) -> Unit = {}
) {
    if (entered) {
        val currentUser = auth.currentUser
        currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { verificationTask ->
                if (verificationTask.isSuccessful) {
                    onVerificationSent(auth)
                } else {
                    toast(
                        context,
                        "Verification task not successful"
                    )
                }
            }
            ?.addOnFailureListener {
                toast(
                    context,
                    "Verification task not successful"
                )
            }
    } else {
        CoroutineScope(Dispatchers.IO).launch {
            val user = userDao.getUserByEmail(email)!!
            userDao.delete(user.fid)
            auth.signInWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener {
                    auth.currentUser!!.delete()
                }
                .addOnFailureListener {
                    toast(
                        context,
                        "Sign in was fail"
                    )
                }
        }
    }
}

fun signOut() {
    auth.signOut()
}

private fun toast(context: Context, text: String) {
    Toast.makeText(
        context,
        text,
        Toast.LENGTH_LONG
    ).show()
}