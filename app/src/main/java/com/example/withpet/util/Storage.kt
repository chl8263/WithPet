package com.example.withpet.util

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.InputStream

object Storage {

    private val storageRef: StorageReference

    init {
        val fireBaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
        storageRef = fireBaseStorage.reference
    }

    fun uploadStream(path: String, stream: InputStream, onSuccessListener: OnSuccessListener<UploadTask.TaskSnapshot>, onFailureListener: OnFailureListener) {
        val uploadRef = storageRef.child(path)
        val uploadTask = uploadRef.putStream(stream)
        uploadTask.addOnSuccessListener(onSuccessListener)
        uploadTask.addOnFailureListener(onFailureListener)
    }

}