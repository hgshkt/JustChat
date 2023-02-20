package com.hgshkt.justchat.loaders

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class ImageLoader {
    val ref = FirebaseStorage.getInstance().reference

    fun upload(uri: Uri, name: String): UploadTask {
        return ref.child("avatars/$name").putFile(uri)
    }
}