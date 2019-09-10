package com.example.withpet.ui.walk.adapter

import android.view.View
import androidx.databinding.BindingAdapter

object WalkBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:onBack")
    fun onBack(view: View, isFinished: Boolean) {
//        if (isFinished) {
//            val target = view.context
//            if (target is ContextWrapper && target.baseContext is MainActivity) {
//                (target.baseContext as MainActivity).onBackPressed()
//            }
//        }
    }

}