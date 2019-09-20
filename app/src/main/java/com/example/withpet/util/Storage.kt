package com.example.withpet.util

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
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

    fun uploadStream(path: String, stream: InputStream, onComplete: ((downloadUrl: String) -> Unit), onError: ((exception: Exception) -> Unit)) {
        val uploadRef = storageRef.child(path)
        val uploadTask = uploadRef.putStream(stream)

        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    Log.e(it)
                    it.printStackTrace()

                    Log.e("error")
                    onError.invoke(it)
                }
            }
            return@Continuation uploadRef.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                downloadUri?.let {
                    onComplete.invoke(it.toString())
                    Log.i("download url : ${it.toString()}")
                }
            } else {
                Log.e("error")
                onError.invoke(Exception("업로드가 실패하였습니다."))
            }
        }
    }


}