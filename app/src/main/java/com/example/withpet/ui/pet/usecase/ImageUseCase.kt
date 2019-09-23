package com.example.withpet.ui.pet.usecase

import com.example.withpet.util.Storage
import io.reactivex.Single
import java.io.InputStream

interface ImageUseCase {
    fun upload(path: String, stream: InputStream): Single<String>
}


class ImageUseCaseImpl : ImageUseCase {
    override fun upload(path: String, stream: InputStream): Single<String> {
        return Single.create { emitter ->
            Storage.uploadStream(path, stream,
                    { downloadUrl -> emitter.onSuccess(downloadUrl) },
                    { exception -> throw exception })
        }
    }

}