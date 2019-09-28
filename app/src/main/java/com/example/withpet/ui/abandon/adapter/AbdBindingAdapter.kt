package com.example.withpet.ui.abandon.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object AbdBindingAdapter {
    val TOP = -1
    val BOTTOM = 1

    @JvmStatic
    @BindingAdapter("app:scrollBottom")
    fun scrollBottom(recyclerView: RecyclerView, function: () -> Unit) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(BOTTOM)) {
                    function.invoke()
                }
            }
        })
    }

}