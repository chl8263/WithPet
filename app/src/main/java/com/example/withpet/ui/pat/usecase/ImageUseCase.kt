package com.example.withpet.ui.pat.usecase

import com.example.withpet.util.Log
import com.example.withpet.util.Storage
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.UploadTask
import io.reactivex.Single
import java.io.InputStream

interface ImageUseCase {
    fun upload(path: String, stream: InputStream): Single<Boolean>
}

class ImageUseCaseImpl : ImageUseCase {
    override fun upload(path: String, stream: InputStream): Single<Boolean> {
        return Single.create { emitter ->
            Storage.uploadStream(path, stream, OnSuccessListener {
                Log.i("uri : ${it.uploadSessionUri}")
                emitter.onSuccess(true)
            }, OnFailureListener {
                emitter.onSuccess(false)
            })
        }
    }

}