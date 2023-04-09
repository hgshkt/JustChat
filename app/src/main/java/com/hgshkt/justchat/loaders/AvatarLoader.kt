package com.hgshkt.justchat.loaders

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.hgshkt.justchat.auth.currentUser
import com.hgshkt.justchat.dao.ChatDao
import com.hgshkt.justchat.dao.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private val ref = FirebaseStorage.getInstance().reference.child("avatars")
private val userDao = UserDao()
private val chatDao = ChatDao()

fun uploadUserAvatar(uri: Uri) {
    val name = buildString {
        append(System.currentTimeMillis())
        append('-')
        append(currentUser!!.id)
    }

    ref.child(name).putFile(uri)
        .continueWithTask { ref.child(name).downloadUrl }

        .addOnSuccessListener {
            currentUser!!.avatarUri = it.toString()
            CoroutineScope(Dispatchers.IO).launch {
                userDao.updateUser(currentUser!!)
            }
        }
}

fun uploadChatAvatar(chatId: String, uri: Uri) {
    val name = buildString {
        append(System.currentTimeMillis())
        append('-')
        append(chatId)
    }

    ref.child(name).putFile(uri)
        .continueWithTask { ref.child(name).downloadUrl }

        .addOnSuccessListener {
            CoroutineScope(Dispatchers.IO).launch {
                chatDao.updateChatAvatar(chatId, it.toString())
            }
        }
}