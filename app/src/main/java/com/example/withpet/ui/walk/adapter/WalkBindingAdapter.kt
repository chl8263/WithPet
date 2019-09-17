package com.example.withpet.ui.walk.adapter

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.example.withpet.ui.walk.WalkViewModel
import com.example.withpet.util.SimpleTextWatcher

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

    @JvmStatic
    @BindingAdapter("app:addTextWatcher")
    fun addTextWatcher(view: EditText, viewModel : WalkViewModel) {
//        view.addTextChangedListener(SimpleTextWatcher().apply {
//            onAfterTextChanged = {
//                it?.let{ keyword ->
//                    viewModel.searchBicycleList(keyword.toString())
//                }
//            }
//        })
//        if (isFinished) {
//            val target = view.context
//            if (target is ContextWrapper && target.baseContext is MainActivity) {
//                (target.baseContext as MainActivity).onBackPressed()
//            }
//        }
    }

}