package com.hgshkt.justchat.loaders

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.dao.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private val ref = FirebaseStorage.getInstance().reference.child("avatars")
private val userDao = UserDao()

fun uploadAvatar(uri: Uri) {
    val currentUser = CurrentUser.get()

    val name = buildString {
        append(System.currentTimeMillis())
        append('-')
        append(currentUser.id)
    }

    ref.child(name).putFile(uri)
        .continueWithTask { ref.child(name).downloadUrl }

        .addOnSuccessListener {
            currentUser.avatarUri = it.toString()
            CoroutineScope(Dispatchers.IO).launch {
                userDao.updateUser(currentUser)
            }
        }
}