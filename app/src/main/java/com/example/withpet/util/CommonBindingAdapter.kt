package com.example.withpet.util

import android.graphics.Paint
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter

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

}