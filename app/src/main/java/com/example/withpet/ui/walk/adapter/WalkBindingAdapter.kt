package com.example.withpet.ui.walk.adapter

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.withpet.ui.walk.WalkMainViewModel

object WalkBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:addTextWatcher")
    fun addTextWatcher(view: EditText, viewModel : WalkMainViewModel) {
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

    @JvmStatic
    @BindingAdapter("app:pageMargin")
    fun setPageMargin(pager : ViewPager, margin : Float){
        pager.pageMargin = margin.toInt()
    }

    @JvmStatic
    @BindingAdapter("app:addDecoration")
    fun addDecoration(recyclerView : RecyclerView, dummy : Int?){
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
    }


}