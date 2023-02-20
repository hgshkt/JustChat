package com.hgshkt.justchat.loaders

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage

class AvatarLoader {
    private val ref = FirebaseStorage.getInstance().reference.child("avatars")

    fun upload(uri: Uri, name: String) : Task<Uri?> {
        val task = ref.child(name).putFile(uri)
        return task.continueWithTask { ref.child(name).downloadUrl }
    }
}