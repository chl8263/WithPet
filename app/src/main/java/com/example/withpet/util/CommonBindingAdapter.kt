package com.example.withpet.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.InputStream

object CommonBindingAdapter {


    @JvmStatic
    @BindingAdapter("app:isUnderLine")
    fun textViewUnderLine(view: TextView, isUnderLine: Boolean?) {
        isUnderLine?.let {
            if (it)
                view.paintFlags = view.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
    }


    @JvmStatic
    @BindingAdapter("app:isSelection")
    fun buttonSelection(view: Button, isSelection: Boolean?) {
        isSelection?.let {
            view.isEnabled = it
        }
    }

    @JvmStatic
    @BindingAdapter("app:inputStreamBitmap")
    fun setInputStreamToBitmap(view: ImageView, inputStream: InputStream?) {
        inputStream?.let {
            val imageBitmap = BitmapFactory.decodeStream(it)
            view.setImageBitmap(imageBitmap)
        }
    }

    @JvmStatic
    @BindingAdapter("app:bitmap")
    fun setBitmapImage(view: ImageView, bitmap: Bitmap?) {
        bitmap?.let {
            view.setImageBitmap(bitmap)
        }
    }

    @JvmStatic
    @BindingAdapter("app:glideImage")
    fun setGlideImage(view: ImageView, downloadUrl: String?) {
        downloadUrl?.let {
            val context = view.context
            Glide.with(context).load(it).into(view)
        }
    }
}