package com.example.withpet.ui.pet.usecase

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.withpet.util.Gallery
import com.example.withpet.util.Storage
import io.reactivex.Single
import java.io.InputStream

interface ImageUseCase {
    fun upload(path: String, stream: InputStream): Single<String>

    fun getBitmapFromUrl(url: String): Single<Bitmap>

    fun getRealPath(uri: Uri): String?
}


class ImageUseCaseImpl(private val ap: Application) : ImageUseCase {
    override fun getRealPath(uri: Uri): String? {
        return Gallery.getRealPathFromURI(ap, uri)
    }

    override fun getBitmapFromUrl(url: String): Single<Bitmap> {
        return Single.create { emitter ->
            Glide.with(ap)
                    .asBitmap()
                    .load(url)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onLoadCleared(placeholder: Drawable?) {}
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            emitter.onSuccess(resource)
                        }
                    })
        }
    }

    override fun upload(path: String, stream: InputStream): Single<String> {
        return Single.create { emitter ->
            Storage.uploadStream(path, stream,
                    { downloadUrl -> emitter.onSuccess(downloadUrl) },
                    { exception -> throw exception })
        }
    }

}