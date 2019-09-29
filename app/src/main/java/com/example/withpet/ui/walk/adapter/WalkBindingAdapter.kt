package com.example.withpet.ui.walk.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager

object WalkBindingAdapter {

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